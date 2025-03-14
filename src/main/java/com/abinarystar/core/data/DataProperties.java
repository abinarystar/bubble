package com.abinarystar.core.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("abinarystar.core.data")
@Data
public class DataProperties {

  @NestedConfigurationProperty
  private Audit audit = new Audit();

  @Data
  public static class Audit {

    private boolean enabled = true;
  }
}
