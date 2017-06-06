package com.diyanfilipov.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

  ServerSecurityConfig() {
    // Disable all filters by default
    super(true)
  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//      .withUser("john").password("123").roles("USER");
//  }

  @Override
  @Bean
  AuthenticationManager authenticationManagerBean() throws Exception {
    super.authenticationManagerBean()
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.anonymous()
//    http.authorizeRequests()
//      .antMatchers("/login").permitAll()
//      .anyRequest().fullyAuthenticated()
//      .and()
//      .formLogin().permitAll()
  }
}
