package com.abinarystar.core.command;

import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CommandAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public CommandExecutor commandExecutor(ApplicationContext applicationContext, Validator validator) {
    return new CommandExecutor(applicationContext, validator);
  }
}
