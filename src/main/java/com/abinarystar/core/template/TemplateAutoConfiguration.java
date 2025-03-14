package com.abinarystar.core.template;

import java.util.Properties;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@AutoConfiguration
@EnableConfigurationProperties(TemplateProperties.class)
@PropertySource("classpath:template.properties")
public class TemplateAutoConfiguration {

  public TemplateAutoConfiguration() {
    Properties properties = new Properties();
    properties.put(RuntimeConstants.RESOURCE_LOADERS, "classpath");
    properties.put("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
    Velocity.init(properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public TemplateService templateService(TemplateProperties templateProperties) {
    return new TemplateService(templateProperties);
  }
}
