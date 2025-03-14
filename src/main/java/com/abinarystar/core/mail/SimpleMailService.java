package com.abinarystar.core.mail;

import com.abinarystar.core.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@RequiredArgsConstructor
public class SimpleMailService implements MailService {

  private final MailSender mailSender;
  private final TemplateService templateService;

  @Override
  public void send(SimpleMailRequest mailRequest) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailRequest.getTo());
    message.setSubject(mailRequest.getSubject());
    message.setText(mailRequest.getContent());
    mailSender.send(message);
  }

  @Override
  public void send(TemplateMailRequest mailRequest) {
    String content = templateService.generate(mailRequest.getTemplateName(), mailRequest.getTemplateParams());
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailRequest.getTo());
    message.setSubject(mailRequest.getSubject());
    message.setText(content);
    mailSender.send(message);
  }
}
