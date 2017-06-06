package com.diyanfilipov.main

import com.diyanfilipov.config.AppConfig
import groovy.util.logging.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Import

@Slf4j
@SpringBootApplication
@Import(AppConfig)
class MainApp {

  static void main(String[] args) {

    log.info(System.getProperty('spring.profiles.active'))

    SpringApplication.run(MainApp.class, args)
  }
}
