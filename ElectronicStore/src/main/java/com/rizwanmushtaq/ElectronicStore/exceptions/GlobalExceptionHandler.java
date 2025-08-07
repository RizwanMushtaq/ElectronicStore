package com.rizwanmushtaq.ElectronicStore.exceptions;

import com.rizwanmushtaq.ElectronicStore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  //handle resource not found exception
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

  // handle MethodArgumentNotValidException
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
    logger.info("Validation error: {}", exception.getMessage());
    List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
    Map<String, Object> response = new HashMap<>();
    allErrors.stream().forEach(objectError -> {
      String fieldName = ((FieldError) objectError).getField();
      String errorMessage = objectError.getDefaultMessage();
      response.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadApiRequest.class)
  public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(BadApiRequest exception) {
    logger.info("Bad Api Request: {}", exception.getMessage());
    ApiResponseMessage response = ApiResponseMessage.builder()
        .message(exception.getMessage())
        .status(HttpStatus.BAD_REQUEST)
        .success(false)
        .build();
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }

  // handle DataIntegrityViolationException
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleDuplicateKey(DataIntegrityViolationException exception) {
    logger.info("Bad Request with Data Integrity Violation: {}", exception.getMessage());
    ApiResponseMessage response = ApiResponseMessage.builder()
        .message(exception.getMessage())
        .status(HttpStatus.CONFLICT)
        .success(false)
        .build();
    return new ResponseEntity(response, HttpStatus.CONFLICT);
  }
}
