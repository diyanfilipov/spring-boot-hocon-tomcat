package com.diyanfilipov.controller

import com.diyanfilipov.config.error.ValidationErrorBuilder
import com.diyanfilipov.core.UserRequest
import com.diyanfilipov.domain.jpa.User
import com.diyanfilipov.domain.jpa.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@Controller
class UserController {

  @Autowired
  UserRepo userRepo

  @PutMapping("/create")
  @ResponseBody
  ResponseEntity create(@Valid @RequestBody UserRequest userRequest, Errors errors) {

    if (errors.hasErrors()) {
      return new ResponseEntity(ValidationErrorBuilder.fromBindingErrors(errors), HttpStatus.BAD_REQUEST)
    }

    User user = new User(
      firstName: userRequest.firstName,
      lastName: userRequest.lastName,
      email: userRequest.email,
      login: userRequest.login,
      password: userRequest.password
    )

    try {
      userRepo.save(user)
    } catch (Exception ex) {
      return ResponseEntity.badRequest().body("Error occured while creating User: [${ex.message}].")
    }

    ResponseEntity.ok(user)
  }

  @GetMapping('/user/{id}')
  @ResponseBody
  User getUserById(@PathVariable('id') String id) {
    userRepo.findOneById(id)
  }
}
