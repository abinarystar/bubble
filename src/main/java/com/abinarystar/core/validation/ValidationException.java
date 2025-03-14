package com.abinarystar.core.validation;

import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ValidationException extends RuntimeException {

  private String code;
  private Map<String, List<String>> errors;

  public ValidationException(String code) {
    this.code = code;
  }

  public ValidationException(String key, String message) {
    this.errors = Map.of(key, List.of(message));
  }

  public ValidationException(Set<ConstraintViolation<Object>> constraintViolations) {
    this.errors = Errors.toMap(constraintViolations);
  }

  @Override
  public String getMessage() {
    if (this.code != null) {
      return this.code;
    }
    return this.errors.toString();
  }
}
