package com.abinarystar.bubble.web.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserWebRequest {

  @NotBlank
  @Length(min = 3, max = 50)
  private String name;

  private List<@NotBlank @Length(max = 320) @Email String> emails;
}
