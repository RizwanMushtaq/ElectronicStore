package com.rizwanmushtaq.ElectronicStore.exceptions;

import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
    logger.info("Resource not found: {}", exception.getMessage());
    ApiResponseMessage response = ApiResponseMessage.builder()
        .message(exception.getMessage())
        .status(HttpStatus.NOT_FOUND)
        .success(true)
        .build();
    return new ResponseEntity(response, HttpStatus.NOT_FOUND);
  }
}
