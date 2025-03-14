package com.abinarystar.core.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("abinarystar.core.mail")
@Data
public class AbinarystarMailProperties {

  private boolean enabled = false;
}
