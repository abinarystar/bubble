package com.abinarystar.bubble.command.user;

import com.abinarystar.bubble.repository.model.User;
import com.abinarystar.bubble.repository.model.UserEmail;
import com.abinarystar.bubble.service.UserService;
import com.abinarystar.bubble.web.model.user.GetUserWebResponse;
import com.abinarystar.core.command.Command;
import com.abinarystar.core.validation.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserCommand implements Command<String, GetUserWebResponse> {

  private final UserService userService;

  @Override
  public GetUserWebResponse execute(String id) {
    User user = userService.getById(id)
        .orElseThrow(() -> Validators.error("USER_NOT_FOUND"));
    return toResponse(user);
  }

  private GetUserWebResponse toResponse(User user) {
    return GetUserWebResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmails()
            .stream()
            .map(UserEmail::getEmail)
            .toList())
        .isDeleted(user.getIsDeleted())
        .build();
  }
}
