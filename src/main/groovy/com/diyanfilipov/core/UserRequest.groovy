package com.diyanfilipov.core

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.NotBlank

class UserRequest {

  @NotBlank(message = 'firstName cannot be blank')
  @Length(max = 191)
  String firstName

  @NotBlank(message = 'lastName cannot be blank')
  @Length(max = 191)
  String lastName

  @NotBlank(message = 'login cannot be blank')
  @Length(max = 191)
  String login

  @NotBlank(message = 'email cannot be blank')
  @Length(max = 191)
  String email

  @NotBlank(message = 'password cannot be blank')
  @Length(max = 191, min = 8)
  String password
}
