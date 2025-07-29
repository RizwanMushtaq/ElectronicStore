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
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column(length = 60, nullable = false, unique = true)
  private String title;
  @Column(length = 500, nullable = false)
  private String description;
  @Column(nullable = false)
  private String coverImage;
}
