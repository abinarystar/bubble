package com.abinarystar.core.mail;

public interface MailService {

  void send(SimpleMailRequest mailRequest);

  void send(TemplateMailRequest mailRequest);
}
