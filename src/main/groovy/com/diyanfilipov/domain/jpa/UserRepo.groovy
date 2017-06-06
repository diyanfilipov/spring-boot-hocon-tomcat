package com.diyanfilipov.domain.jpa

import org.springframework.data.jpa.repository.JpaRepository

import javax.transaction.Transactional

@Transactional
interface UserRepo extends JpaRepository<User, String> {

  User findOneById(String id)
  User findOneByLogin(String login)
  User findOneByEmail(String email)

}
