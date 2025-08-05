package com.rizwanmushtaq.ElectronicStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    // diable CORS as of now
    httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
    // disable CSRF as of now
    httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
    // Configure Urls
    httpSecurity.authorizeHttpRequests(request ->
        request
            .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
            .requestMatchers(HttpMethod.DELETE, "/api/users").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN", "NORMAL")
            .requestMatchers(HttpMethod.POST, "/api/users/**").hasAnyRole(
                "ADMIN", "NORMAL")
            .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
            .requestMatchers("/api/products/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
            .requestMatchers("/api/categories/**").hasRole("ADMIN")
    );
    // Configure Type of Security
    httpSecurity.httpBasic(Customizer.withDefaults());
    return httpSecurity.build();
  }

  // password encoder
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
