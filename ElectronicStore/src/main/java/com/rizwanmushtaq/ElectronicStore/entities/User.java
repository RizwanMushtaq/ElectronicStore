package com.rizwanmushtaq.ElectronicStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
  @JsonIgnore
  @ToString.Exclude
  private Cart cart;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonIgnore
  @ToString.Exclude
  private List<Order> orders = new ArrayList<>();
}
