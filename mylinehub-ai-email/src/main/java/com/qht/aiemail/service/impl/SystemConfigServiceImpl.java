package com.qht.aiemail.service.impl;

import com.qht.aiemail.model.SystemConfig;
import com.qht.aiemail.repository.SystemConfigRepository;
import com.qht.aiemail.service.SystemConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Simple service that fetches config values from DB.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemConfigServiceImpl implements SystemConfigService {

    private final SystemConfigRepository repo;

    @Override
    public String getRequired(String key) {
        return repo.findByKey(key)
                .map(SystemConfig::getValue)
                .orElseThrow(() -> new IllegalStateException("Missing required config key: " + key));
    }

    @Override
    public String getOptional(String key, String defaultValue) {
        return repo.findByKey(key)
                .map(SystemConfig::getValue)
                .orElse(defaultValue);
    }
}
