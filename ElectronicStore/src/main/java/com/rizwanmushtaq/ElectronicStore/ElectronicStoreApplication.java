package com.rizwanmushtaq.ElectronicStore;

import com.rizwanmushtaq.ElectronicStore.entities.Role;
import com.rizwanmushtaq.ElectronicStore.repositories.RoleRepository;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private UserRepository userRepository;

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
          .name("ROLE_ADMIN")
          .build();
      adminRole = roleRepository.save(newRoleAdmin);
    }
    if (normalRole == null) {
      Role newRoleNormal = Role
          .builder()
          .id(UUID.randomUUID().toString())
          .name("ROLE_NORMAL")
          .build();
      normalRole = roleRepository.save(newRoleNormal);
    }
  }
}
