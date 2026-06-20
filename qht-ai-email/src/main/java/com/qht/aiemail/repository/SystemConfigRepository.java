package com.qht.aiemail.repository;

import com.qht.aiemail.model.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {

    Optional<SystemConfig> findByKey(String key);
}
