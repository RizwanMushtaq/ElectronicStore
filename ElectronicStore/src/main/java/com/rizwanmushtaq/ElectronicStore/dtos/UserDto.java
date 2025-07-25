package com.rizwanmushtaq.ElectronicStore.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
  private String id;
  private String username;
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 " +
      "characters")
  private String name;
  @NotBlank(message = "Password is required")
  private String password;
  @Email(message = "Invalid Email format")
  private String email;
  @Size(min = 4, max = 6, message = "Invalid Gender")
  private String gender;
  @NotBlank(message = "About is required")
  private String about;
  private String imageName;
}
