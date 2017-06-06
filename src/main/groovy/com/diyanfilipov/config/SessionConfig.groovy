package com.diyanfilipov.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.session.SessionProperties
import org.springframework.boot.autoconfigure.session.StoreType
import org.springframework.context.annotation.Configuration

import javax.annotation.PostConstruct

@Configuration
class SessionConfig {

  @Autowired
  SessionProperties sessionProperties

  @PostConstruct
  void configureSession() {
    sessionProperties.storeType = StoreType.NONE
  }
}
