package com.rizwanmushtaq.ElectronicStore.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid
    , String> {
  private Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);

  @Override
  public void initialize(ImageNameValid constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    logger.info("Validating image name: {}", value);
    if (value.isBlank()) {
      return false;
    } else {
      return true;
    }
  }
}
