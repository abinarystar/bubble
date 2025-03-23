package com.abinarystar.core.command;

import com.abinarystar.core.validation.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class CommandExecutor {

  private final ApplicationContext applicationContext;
  private final Validator validator;

  public <T, R> R execute(Class<? extends Command<T, R>> commandClass, T request) {
    validateRequest(request);
    Command<T, R> command = applicationContext.getBean(commandClass);
    return command.execute(request);
  }

  private void validateRequest(Object request) {
    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
    if (!constraintViolations.isEmpty()) {
      throw new ValidationException(constraintViolations);
    }
  }
}
