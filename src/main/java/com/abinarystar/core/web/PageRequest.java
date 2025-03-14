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
public class PageRequest {

  private Integer page;
  private Integer size;
  private String sort;
}
