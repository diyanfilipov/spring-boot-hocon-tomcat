package com.diyanfilipov.config.error

import com.fasterxml.jackson.annotation.JsonInclude

class ValidationError {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<String> errors = new ArrayList<>()

  private final String errorMessage

  ValidationError(String errorMessage) {
    this.errorMessage = errorMessage
  }

  void addValidationError(String error) {
    errors.add(error)
  }

  List<String> getErrors() {
    return errors
  }

  String getErrorMessage() {
    return errorMessage
  }
}
