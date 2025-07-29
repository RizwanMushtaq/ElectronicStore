package com.rizwanmushtaq.ElectronicStore.entities;

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
@Table(name = "categories")
public class Category {
  @Id
  private String id;
  @Column(length = 60, nullable = false, unique = true)
  private String title;
  @Column(length = 500, nullable = false)
  private String description;
  @Column(nullable = false)
  private String coverImage;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
  private List<Product> products = new ArrayList<>();
}
