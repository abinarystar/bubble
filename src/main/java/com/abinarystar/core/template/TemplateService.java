package com.abinarystar.core.template;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

@RequiredArgsConstructor
public class TemplateService {

  private final TemplateProperties templateProperties;

  public String generate(String fileName, Map<String, Object> context) {
    StringWriter writer = new StringWriter();
    String templateName = String.join("/", templateProperties.getPath(), fileName);
    Velocity.mergeTemplate(
        templateName,
        StandardCharsets.UTF_8.name(),
        new VelocityContext(context),
        writer
    );
    return writer.toString();
  }
}
