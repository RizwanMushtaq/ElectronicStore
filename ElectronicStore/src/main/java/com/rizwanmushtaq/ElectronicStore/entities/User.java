package com.rizwanmushtaq.ElectronicStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails {
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
  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @ToString.Exclude
  private List<Role> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<SimpleGrantedAuthority> authorities =
        roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
