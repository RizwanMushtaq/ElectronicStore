package com.rizwanmushtaq.ElectronicStore;

import com.rizwanmushtaq.ElectronicStore.entities.User;
import com.rizwanmushtaq.ElectronicStore.repositories.UserRepository;
import com.rizwanmushtaq.ElectronicStore.security.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElectronicStoreApplicationTests {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtHelper jwtHelper;

  @Test
  void contextLoads() {
  }

  @Test
  void testJwtToken() {
    User username = userRepository.findByUsername("admin").get();
    String token = jwtHelper.generateToken(username);
    System.out.println("tesing Token: " + token);
  }
}
