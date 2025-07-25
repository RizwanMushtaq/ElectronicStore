package com.rizwanmushtaq.ElectronicStore.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
  private String id;
  private String username;
  private String name;
  private String password;
  private String email;
  private String gender;
  private String about;
  private String imageName;
}
