package com.diyanfilipov.repository

import com.diyanfilipov.IntegrationTestInMemory
import com.diyanfilipov.RandomDataGenerator
import com.diyanfilipov.domain.jpa.User
import com.diyanfilipov.domain.jpa.UserRepo
import com.diyanfilipov.main.MainApp
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@Category(IntegrationTestInMemory)
@SpringBootTest(classes = MainApp, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRepoIntegrationTests {

  @Autowired
  UserRepo userRepo

  List<User> users

  @Before
  void setup() {

    User user = new User(
      id: RandomDataGenerator.instance.generateRandomUuid(),
      firstName: RandomDataGenerator.instance.createString(10),
      lastName: RandomDataGenerator.instance.createString(10),
      email: RandomDataGenerator.instance.createEmail(),
      login: RandomDataGenerator.instance.createEmail(),
      password: RandomDataGenerator.instance.createString(10)
    )

    users = []
    users << userRepo.save(user)
  }

  @After
  void cleanUp() {
    users.each {
      userRepo.delete(it.id)
    }
  }

  @Test
  void testUserFetching() {

    users.each { User user ->
      User foundUser = userRepo.findOneById(user.id)

      assert foundUser != null
    }
  }

  @Test
  void failingTest() {

    assert true == false
  }
}
