package com.abinarystar.core.mail;

import com.abinarystar.core.template.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DummyMailService implements MailService {

  private final TemplateService templateService;

  @Override
  public void send(SimpleMailRequest mailRequest) {
    log(mailRequest.getTo(), mailRequest.getSubject(), mailRequest.getContent());
  }

  @Override
  public void send(TemplateMailRequest mailRequest) {
    String content = templateService.generate(mailRequest.getTemplateName(), mailRequest.getTemplateParams());
    log(mailRequest.getTo(), mailRequest.getSubject(), content);
  }

  private void log(String to, String subject, String content) {
    log.info("Send email | to: {} | subject: {} | content: {}", to, subject, content);
  }
}
