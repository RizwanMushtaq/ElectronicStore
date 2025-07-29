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
@Table(name = "categories")
public class Category {
  @Id
  private String id;
  @Column(length = 60, nullable = false, unique = true)
  private String title;
  @Column(length = 500, nullable = false)
  private String description;
  private String coverImage;
}
