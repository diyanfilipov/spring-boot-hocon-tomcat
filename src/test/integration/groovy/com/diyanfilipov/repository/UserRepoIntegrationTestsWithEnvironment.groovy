package com.diyanfilipov.repository

import com.diyanfilipov.domain.jpa.User
import com.diyanfilipov.domain.jpa.UserRepo
import com.diyanfilipov.main.MainApp
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest(classes = MainApp, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRepoIntegrationTestsWithEnvironment {

  @Autowired
  UserRepo userRepo

  @Test
  void testUserFetching() {
    User user = userRepo.findOneById('402880e45c60c906015c60c91bf20000')

    Assert.assertNotNull(user)
  }
}
