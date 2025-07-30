package com.rizwanmushtaq.ElectronicStore.entities;

import jakarta.persistence.*;
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
  private String name;
  @Column(nullable = false)
  private String password;
  @Column(unique = true, nullable = false)
  private String email;
  @Column(length = 10)
  private String gender;
  @Column(length = 500)
  private String about;
  private String imageName;
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Cart cart;
}
