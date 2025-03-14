package com.abinarystar.core.mail;

import com.abinarystar.core.template.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
  public MailService mailService(
      AbinarystarMailProperties mailProperties,
      @Autowired(required = false) JavaMailSender mailSender,
      TemplateService templateService
  ) {
    if (mailProperties.isEnabled()) {
      return new SimpleMailService(mailSender, templateService);
    }
    return new DummyMailService(templateService);
  }
}
