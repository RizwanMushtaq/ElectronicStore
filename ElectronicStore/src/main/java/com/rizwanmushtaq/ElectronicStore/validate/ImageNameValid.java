package com.rizwanmushtaq.ElectronicStore.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {
  // default error message
  String message() default "Invalid image name";

  // represents group of constraints
  Class<?>[] groups() default {};

  // represents additional information about annotation
  Class<? extends Payload>[] payload() default {};
}
