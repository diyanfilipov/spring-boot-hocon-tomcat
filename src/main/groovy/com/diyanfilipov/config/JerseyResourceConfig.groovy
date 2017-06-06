package com.diyanfilipov.config

import com.diyanfilipov.resource.UserResource
import org.glassfish.jersey.server.ResourceConfig
import org.springframework.context.annotation.Configuration

import javax.ws.rs.ApplicationPath

@Configuration
@ApplicationPath('/jersey')
class JerseyResourceConfig extends ResourceConfig {

  JerseyResourceConfig() {
    register(UserResource)
  }
}
