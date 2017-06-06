package com.diyanfilipov.config.error

import org.springframework.validation.Errors
import org.springframework.validation.ObjectError

/**
 * Created by diyan.filipov on 1/06/17.
 */
class ValidationErrorBuilder {

  static ValidationError fromBindingErrors(Errors errors) {
    ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)")
    for (ObjectError objectError : errors.getAllErrors()) {
      error.addValidationError(objectError.getDefaultMessage())
    }
    return error
  }
}
