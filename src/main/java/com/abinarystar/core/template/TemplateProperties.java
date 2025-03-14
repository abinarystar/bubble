package com.abinarystar.core.template;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("abinarystar.core.template")
@Data
public class TemplateProperties {

  private String path = "velocity-templates";
}
