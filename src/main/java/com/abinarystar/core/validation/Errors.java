package com.abinarystar.core.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Errors {

  private static final String LIST_ELEMENT = ".<list element>";

  public static Map<String, List<String>> toMap(Set<ConstraintViolation<Object>> constraintViolations) {
    return Optional.ofNullable(constraintViolations)
        .orElseGet(Collections::emptySet)
        .stream()
        .filter(constraintViolation -> StringUtils.isNotBlank(constraintViolation.getMessage()))
        .collect(Collectors.groupingBy(
            Errors::getPropertyPath,
            Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
        ));
  }

  private static <T> String getPropertyPath(ConstraintViolation<T> violation) {
    return Optional.ofNullable(violation.getPropertyPath())
        .map(Path::toString)
        .map(Errors::removeListElement)
        .orElse(StringUtils.EMPTY);
  }

  private static String removeListElement(String path) {
    return StringUtils.substringBefore(path, LIST_ELEMENT);
  }
}
