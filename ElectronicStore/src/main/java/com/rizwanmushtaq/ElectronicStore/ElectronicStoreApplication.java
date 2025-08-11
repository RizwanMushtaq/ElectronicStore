package com.rizwanmushtaq.ElectronicStore;

import com.rizwanmushtaq.ElectronicStore.config.AppConstants;
import com.rizwanmushtaq.ElectronicStore.entities.Role;
import com.rizwanmushtaq.ElectronicStore.entities.User;
import com.rizwanmushtaq.ElectronicStore.repositories.RoleRepository;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(ElectronicStoreApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
    Role normalRole = roleRepository.findByName("ROLE_NORMAL").orElse(null);
    if (adminRole == null) {
      Role newRoleAdmin = Role
          .builder()
          .id(UUID.randomUUID().toString())
          .name("ROLE_" + AppConstants.ROLE_ADMIN)
          .build();
      adminRole = roleRepository.save(newRoleAdmin);
    }
    if (normalRole == null) {
      Role newRoleNormal = Role
          .builder()
          .id(UUID.randomUUID().toString())
          .name("ROLE_" + AppConstants.ROLE_NORMAL)
          .build();
      normalRole = roleRepository.save(newRoleNormal);
    }
    User adminUser = userRepository.findByUsername("admin").orElse(null);
    User johnUser = userRepository.findByUsername("john").orElse(null);
    if (adminUser == null) {
      User newAdmin = User.builder()
          .id(UUID.randomUUID().toString())
          .username("admin")
          .name("rizwan")
          .password(passwordEncoder.encode("admin"))
          .email("rizwan@gmail.de")
          .gender("male")
          .about("I am admin of application")
          .roles(List.of(adminRole))
          .build();
      userRepository.save(newAdmin);
    }
    if (johnUser == null) {
      User newUser = User.builder()
          .id(UUID.randomUUID().toString())
          .username("john")
          .name("john")
          .password(passwordEncoder.encode("john"))
          .email("john@gmail.de")
          .gender("male")
          .about("I am john")
          .roles(List.of(normalRole))
          .build();
      userRepository.save(newUser);
    }
  }
}
