package com.abinarystar.core.mail;

import com.abinarystar.core.template.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@RequiredArgsConstructor
public class SimpleMailService implements MailService {

  private final JavaMailSender mailSender;
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
    mailSender.send(mimeMessage -> {
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      message.setTo(mailRequest.getTo());
      message.setSubject(mailRequest.getSubject());
      message.setText(content, true);
    });
  }
}
