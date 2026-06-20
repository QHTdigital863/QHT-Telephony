/*
 * File: src/main/java/com/qht/voicebridge/config/StasisAppConfigRepository.java
 */
package com.qht.voicebridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.qht.voicebridge.models.StasisAppConfig;

import java.util.List;
import java.util.Optional;

public interface StasisAppConfigRepository extends JpaRepository<StasisAppConfig, String> {

  List<StasisAppConfig> findByActiveTrue();

  @Query("select c from StasisAppConfig c where c.stasis_app_name = :name and c.active = true")
  Optional<StasisAppConfig> findActiveByName(@Param("name") String name);
  
}
