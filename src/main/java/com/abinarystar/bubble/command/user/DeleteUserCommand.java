package com.abinarystar.bubble.command.user;

import com.abinarystar.bubble.repository.model.User;
import com.abinarystar.bubble.service.UserService;
import com.abinarystar.core.command.Command;
import com.abinarystar.core.validation.Validators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserCommand implements Command<String, Void> {

  private final UserService userService;

  @Override
  public Void execute(String id) {
    User user = userService.getById(id)
        .orElseThrow(() -> Validators.error("USER_NOT_FOUND"));
    userService.delete(user);
    return null;
  }
}
