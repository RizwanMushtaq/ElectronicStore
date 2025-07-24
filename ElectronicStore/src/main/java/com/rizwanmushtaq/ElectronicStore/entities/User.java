package com.rizwanmushtaq.ElectronicStore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User {
  @Id
  private String id;
  @Column(unique = true, nullable = false)
  private String username;
  private String firstName;
  private String lastName;
  @Column(nullable = false)
  private String password;
  @Column(unique = true, nullable = false)
  private String email;
  @Column(length = 10)
  private String gender;
  @Column(length = 500)
  private String about;
  private String imageName;
}
