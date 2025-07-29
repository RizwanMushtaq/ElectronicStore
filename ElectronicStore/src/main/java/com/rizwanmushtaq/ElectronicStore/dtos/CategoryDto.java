package com.rizwanmushtaq.ElectronicStore.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryDto {
  private String id;
  @NotBlank
  @Min(value = 3, message = "Title must be at least 3 characters long")
  private String title;
  @NotBlank(message = "Description is required")
  private String description;
  @NotBlank(message = "Cover image is required")
  private String coverImage;
}
