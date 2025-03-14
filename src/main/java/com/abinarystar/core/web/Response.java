package com.abinarystar.core.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Response<T> {

  private T content;
  private Metadata metadata;
  private String errorCode;
  private Map<String, List<String>> errors;

  public static <T> Response<T> ok() {
    return new Response<>();
  }

  public static <T> Response<T> ok(T content) {
    return Response.<T>builder()
        .content(content)
        .build();
  }

  public static <T> Response<List<T>> ok(Page<T> page) {
    if (page == null) {
      return Response.ok();
    }
    return Response.<List<T>>builder()
        .content(page.getContent())
        .metadata(Metadata.of(page))
        .build();
  }

  public static <T> Response<List<T>> ok(List<T> content, Function<T, Object> tokenFunction) {
    if (content == null) {
      content = Collections.emptyList();
    }
    return Response.<List<T>>builder()
        .content(content)
        .metadata(Metadata.of(content, tokenFunction))
        .build();
  }

  public static Response<Void> error(String errorCode) {
    return Response.<Void>builder()
        .errorCode(errorCode)
        .build();
  }

  public static Response<Void> error(Map<String, List<String>> errors) {
    return Response.<Void>builder()
        .errors(errors)
        .build();
  }

  public static Response<Void> error(String errorCode, Map<String, List<String>> errors) {
    return Response.<Void>builder()
        .errorCode(errorCode)
        .errors(errors)
        .build();
  }
}
