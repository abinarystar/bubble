package com.abinarystar.bubble.web.model.user;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserWebResponse {

  private String id;
  private String name;
  private List<String> email;
  private Boolean isDeleted;
}
