package com.diyanfilipov.resource

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Slf4j
@Component
@Path("/health")
@Consumes('application/json')
@Produces('application/json')
class UserResource {

  @GET
  String helloFromJersey() {
    'Jersey running!'
  }
}
