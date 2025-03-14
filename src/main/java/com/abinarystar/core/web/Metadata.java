package com.abinarystar.core.web;

import java.util.List;
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
public class Metadata {

  private Long totalContent;
  private Object previousToken;
  private Object nextToken;

  public static Metadata of(Page<?> page) {
    if (page == null) {
      return new Metadata();
    }
    return Metadata.builder()
        .totalContent(page.getTotalElements())
        .build();
  }

  public static <T> Metadata of(List<T> content, Function<T, Object> tokenFunction) {
    if (content == null || tokenFunction == null || content.isEmpty()) {
      return new Metadata();
    }
    return Metadata.builder()
        .previousToken(tokenFunction.apply(content.getFirst()))
        .nextToken(tokenFunction.apply(content.getLast()))
        .build();
  }
}
