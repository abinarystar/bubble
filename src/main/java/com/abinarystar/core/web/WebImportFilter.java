package com.abinarystar.core.web;

import java.util.Set;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;

public class WebImportFilter implements AutoConfigurationImportFilter {

  private static final Set<String> EXCLUDED_CLASSES = Set.of(
      "org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration"
  );

  @Override
  public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
    boolean[] matches = new boolean[autoConfigurationClasses.length];
    for (int i = 0; i < autoConfigurationClasses.length; i++) {
      String autoConfigurationClass = autoConfigurationClasses[i];
      matches[i] = autoConfigurationClass == null || !EXCLUDED_CLASSES.contains(autoConfigurationClass);
    }
    return matches;
  }
}
