# QHT Clinic CRM — Deployment Guide (self-hosted)

Single self-hosted server. Stack: **Ubuntu 22.04/24.04 + PostgreSQL 16 (pgvector) + NGINX + systemd**.
(Recommended over Docker because FreePBX/Asterisk telephony will run on the same host and needs host-level RTP/SIP/AMI.)

---

## 0. Architecture (what runs where)

| Service | Tech | Port | DB |
|---|---|---|---|
| CRM backend | Spring Boot (Java 17) | 8080 (8081 http) | `mylinehub_crm` |
| Frontend | Angular 14 → static `dist/` via NGINX | 443 | — |
| AI email (optional) | Spring Boot (Java 17) | 9090 | `mylinehub_email` |
| Voicebridge (optional, AI voice) | Spring Boot (Java 17) | 8082 | `mylinehub_voicebridge` |
| PostgreSQL 16 + pgvector | — | 5432 (localhost only) | all three |

NGINX terminates TLS on 443 → serves the Angular app + reverse-proxies `/api`, `/chat`, etc. to 8080.

---

## 1. ⚠️ BEFORE `git init` (do this first)

The repo is **not git-ready** as-is. The new `.gitignore` already excludes keystores, `target/`, `*.jar`, `node_modules/`, `.angular/`, logs, IDE files, and the local `start-*.sh` scripts. Still do:

1. **Verify nothing sensitive will be staged:**
   ```bash
   git init && git add -A
   git status --porcelain | grep -Ei '\.jks$|target/|\.jar$|\.log$'   # must print NOTHING
   ```
2. **Rotate these secrets** (treat as compromised — they were in the working tree):
   - `mylinehub-crm/keystore.jks` and `mylinehub-voicebridge/keystore.jks` → **regenerate** (now gitignored; keep only on the server).
   - `api.key=super-secret-api-key` → new random value.
   - `jwt.private-key` → new long random string (≥32 chars).
   - DB password `root` (all 3 backends) → real password.
   - `spring.ssh.password=root`, onboarding password `mylinehub@123`.
   - Hardcoded tokens in Java source: `MYLINEHUB100010001`, `mylinehub10101001`.
   - The DeepVue/IDFY `client_secret` inside `Deployment details/5. *.txt` → scrub before commit.
3. Private repo (e.g. self-hosted **Gitea**) recommended over a public one.

---

## 2. Server prerequisites

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk postgresql-16 postgresql-16-pgvector nginx
# Node 18 + Angular CLI 14 (NOT Node 14 / CLI 11 — those cannot build this app)
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install -y nodejs
sudo npm install -g @angular/cli@14
```

## 3. Database

```bash
sudo -u postgres psql -c "ALTER USER postgres PASSWORD '<STRONG_DB_PASSWORD>';"
sudo -u postgres createdb mylinehub_crm
sudo -u postgres createdb mylinehub_email          # only if running ai-email
sudo -u postgres createdb mylinehub_voicebridge    # only if running voicebridge
sudo -u postgres psql -d mylinehub_crm -c "CREATE EXTENSION IF NOT EXISTS vector;"
```
Schema auto-creates on first boot (`ddl-auto=update`). Keep 5432 bound to localhost (do **not** expose to the internet).

## 4. Configure secrets (before building)

Edit `mylinehub-crm/src/main/resources/application.properties` — set the real values (or override at runtime via env vars / `SPRING_*`):
- `spring.datasource.password`, `jwt.private-key`, `api.key`, `app.auth.tokenSecret`
- `spring.domain` = your domain, remove the `localhost:8080/oauth2/redirect`
- `openai.api-key` (only for RAG/AI), mail creds, etc.

**Harden for prod** (recommended):
- `management.endpoints.web.exposure.include=health,info,metrics,prometheus` (not `*`)
- `management.endpoint.shutdown.enabled=false`
- `server.error.include-message=never`
- `spring.jpa.hibernate.ddl-auto=validate` (after first successful boot creates schema)

## 5. Frontend domain (REQUIRED before building)

Edit **`mylinehub-crm-frontend/src/environments/environment.prod.ts`** — set your real domain (single source of truth, already wired into the app):
```ts
apiBaseUrl: 'https://crm.qhtclinic.com/',
wsSockUrl:  'https://crm.qhtclinic.com/chat',
wsBrokerUrl:'wss://crm.qhtclinic.com/chat',
```

## 6. Build

```bash
# Backend
cd mylinehub-crm && mvn clean package -Dmaven.test.skip=true        # -> target/crm.jar

# Frontend (prod, points at the domain set in step 5)
cd ../mylinehub-crm-frontend && npm ci && npm run build:prod        # -> dist/
sudo cp -r dist/* /var/www/qht-crm/

# (optional) AI email
cd ../mylinehub-ai-email && mvn clean package -Dmaven.test.skip=true   # -> target/ai-email-1.0.0.jar
# (optional) Voicebridge — needs linux webrtc classifier
cd ../mylinehub-voicebridge && mvn clean package -Pmylinehub -Dmaven.test.skip=true -Dwebrtc.classifier=linux-x86_64
```
**Verify** the localhost defaults didn't leak into the prod bundle:
```bash
grep -c 'localhost:8080' /var/www/qht-crm/main*.js     # must be 0
```

## 7. Run as systemd services

`/etc/systemd/system/qht-crm.service`:
```ini
[Unit]
Description=QHT Clinic CRM backend
After=network.target postgresql.service
[Service]
User=qht
WorkingDirectory=/opt/qht/mylinehub-crm
ExecStart=/usr/bin/java -Xms2g -Xmx4g -jar /opt/qht/mylinehub-crm/target/crm.jar
SuccessExitStatus=143
Restart=on-failure
[Install]
WantedBy=multi-user.target
```
```bash
sudo systemctl daemon-reload && sudo systemctl enable --now qht-crm
journalctl -u qht-crm -f          # watch logs
```
(Repeat for `qht-ai-email` :9090 and `qht-voicebridge` :8082 if used — note: the repo docs have units for backend & voicebridge but NOT ai-email; add one.)

## 8. NGINX + SSL

```bash
sudo certbot --nginx -d crm.qhtclinic.com
```
NGINX server block: serve `/var/www/qht-crm` on 443 with SPA fallback `try_files $uri $uri/ /index.html;`, gzip on, and reverse-proxy the backend:
```nginx
location /api/      { proxy_pass http://127.0.0.1:8080; }
location /login     { proxy_pass http://127.0.0.1:8080; }
location /chat      { proxy_pass http://127.0.0.1:8080; proxy_http_version 1.1;
                      proxy_set_header Upgrade $http_upgrade; proxy_set_header Connection "upgrade"; }
```
(Adjust paths to match the endpoints the app calls. WebSocket `/chat` needs the Upgrade headers.)

## 9. First login

DB is empty after first boot. Create an admin org via the onboarding endpoint (internal token), or seed directly. See the chat history / ask for the exact `createOrgFromBackendWithoutGST` call. Default user password is `spring.onboarding.initialpassword`.

## 10. Firewall

```bash
sudo ufw allow 80 && sudo ufw allow 443 && sudo ufw enable
```
Keep 5432 / 8080 / 8081 / 9090 / 8082 **internal only** (behind NGINX). For telephony later: open Asterisk SIP/WSS (8089), AMI 5038 (internal), and the RTP UDP range.

---

## Known gaps to address later (not deploy blockers for a private instance)
- Spring Boot 2.4.0 is EOL — plan an upgrade + dependency CVE scan before any public exposure.
- `pom.xml` java.version 1.17 vs compiler 1.8 mismatch — align to 17.
- `ddl-auto=update` mutates schema on boot — switch to `validate` + migrations long-term.
- Voicebridge Dockerfile is broken (missing `mvnw`) — only matters if you containerize.
- Telephony (FreePBX + GSM gateway) — separate setup; see the telephony plan discussed in chat.
