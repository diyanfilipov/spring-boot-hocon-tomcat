package com.diyanfilipov.domain.jpa

import javax.persistence.Entity

@Entity
class User extends UuidPersistable {

  String firstName
  String lastName
  String email
  String login
  String password

}
