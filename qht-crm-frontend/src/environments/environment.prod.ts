/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
export const environment = {
  production: true,

  // ===== Backend endpoints (PRODUCTION) =====
  // >>> CHANGE THESE to your real domain before running `npm run build:prod` <<<
  // Use https/wss (NGINX terminates TLS); no port needed when served on 443.
  apiBaseUrl: 'https://telephony.hairmedindia.com/',
  wsSockUrl: 'https://telephony.hairmedindia.com/chat',
  wsBrokerUrl: 'wss://telephony.hairmedindia.com/chat',
};
