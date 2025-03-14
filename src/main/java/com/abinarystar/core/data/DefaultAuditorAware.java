package com.abinarystar.core.data;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class DefaultAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // TODO create security module and replace hardcoded value here with security principal
    return Optional.of("SYSTEM");
  }
}
