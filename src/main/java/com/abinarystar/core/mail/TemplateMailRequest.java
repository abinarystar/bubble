package com.abinarystar.core.mail;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateMailRequest {

  private String to;
  private String subject;
  private String templateName;
  private Map<String, Object> templateParams;
}
