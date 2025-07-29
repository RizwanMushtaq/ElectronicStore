package com.rizwanmushtaq.ElectronicStore.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {
  private String imageName;
  private String message;
  private boolean success;
  private HttpStatus status;
}