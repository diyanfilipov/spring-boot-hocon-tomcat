package com.diyanfilipov.resources

import com.diyanfilipov.main.MainApp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate

import static org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner)
@SpringBootTest(classes = MainApp, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthControllerIntegrationTest {

  @Autowired
  TestRestTemplate restTemplate

  @Test
  void health() {
    ResponseEntity<String> entity =
      restTemplate.getForEntity('/jersey/health', String)

    assertTrue(entity.getStatusCode().is2xxSuccessful())
    assertEquals(entity.getBody(), 'Jersey running!')
  }
}
