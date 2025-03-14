package com.abinarystar.core.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validators {

  public static void validate(boolean expression, String errorCode) {
    if (!expression) {
      throw new ValidationException(errorCode);
    }
  }

  public static void validate(boolean expression, String errorKey, String errorMessage) {
    if (!expression) {
      throw new ValidationException(errorKey, errorMessage);
    }
  }

  public static ValidationException error(String errorCode) {
    throw new ValidationException(errorCode);
  }
}
