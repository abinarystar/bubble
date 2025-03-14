package com.abinarystar.core.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScrollRequest {

  private Object previousToken;
  private Object nextToken;
  private Integer size;
  private String sort;
}
