package com.abinarystar.core.data;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ConditionalOnProperty("abinarystar.core.data.audit.enabled")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DataAuditingConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public AuditorAware<String> auditorAware() {
    return new DefaultAuditorAware();
  }
}
