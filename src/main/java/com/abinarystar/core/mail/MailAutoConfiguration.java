package com.abinarystar.core.mail;

import com.abinarystar.core.template.TemplateService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;

@AutoConfiguration
@EnableConfigurationProperties(AbinarystarMailProperties.class)
@PropertySource("classpath:mail.properties")
public class MailAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(name = "abinarystar.core.mail.enabled", havingValue = "false", matchIfMissing = true)
  public MailService dummyMailService(TemplateService templateService) {
    return new DummyMailService(templateService);
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty("abinarystar.core.mail.enabled")
  public MailService simpleMailService(JavaMailSender mailSender, TemplateService templateService) {
    return new SimpleMailService(mailSender, templateService);
  }
}
