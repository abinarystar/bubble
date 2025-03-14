package com.abinarystar.bubble.command.user;

import com.abinarystar.bubble.repository.model.User;
import com.abinarystar.bubble.repository.model.UserEmail;
import com.abinarystar.bubble.service.UserService;
import com.abinarystar.bubble.web.model.user.CreateUserWebRequest;
import com.abinarystar.bubble.web.model.user.CreateUserWebResponse;
import com.abinarystar.core.command.Command;
import com.abinarystar.core.mail.MailService;
import com.abinarystar.core.mail.TemplateMailRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserCommand implements Command<CreateUserWebRequest, CreateUserWebResponse> {

  private final MailService mailService;
  private final UserService userService;

  @Override
  public CreateUserWebResponse execute(CreateUserWebRequest request) {
    User user = createUser(request);
    userService.save(user);
    sendEmail(user);
    return toResponse(user);
  }

  private User createUser(CreateUserWebRequest request) {
    User user = User.builder()
        .name(request.getName())
        .build();
    List<UserEmail> userEmails = Optional.ofNullable(request.getEmails())
        .stream()
        .flatMap(Collection::stream)
        .map(email -> createEmail(user, email))
        .toList();
    user.setEmails(userEmails);
    return user;
  }

  private UserEmail createEmail(User user, String email) {
    return UserEmail.builder()
        .user(user)
        .email(email)
        .build();
  }

  private CreateUserWebResponse toResponse(User user) {
    return CreateUserWebResponse.builder()
        .id(user.getId())
        .build();
  }

  private void sendEmail(User user) {
    for (UserEmail email : user.getEmails()) {
      Map<String, Object> params = Map.of(
          "userId", user.getId(),
          "userName", user.getName()
      );
      TemplateMailRequest request = TemplateMailRequest.builder()
          .to(email.getEmail())
          .subject("User Created")
          .templateName("user-created.html")
          .templateParams(params)
          .build();
      mailService.send(request);
    }
  }
}
