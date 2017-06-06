package com.diyanfilipov.config

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore

@Configuration
@EnableAuthorizationServer // [1]
class OAuth2Config extends AuthorizationServerConfigurerAdapter {

  @Autowired
  ComboPooledDataSource dataSourcePlatform

  @Autowired
  private AuthenticationManager authenticationManager

  @Override // [2]
  void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
      .tokenStore(tokenStore())
      .authenticationManager(authenticationManager)
  }

  @Override
  void configure(AuthorizationServerSecurityConfigurer oauthServer)
    throws Exception {
    oauthServer
      .tokenKeyAccess("permitAll()")
      .checkTokenAccess("isAuthenticated()")
  }

  @Override // [3]
  void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
      .withClient("client-with-registered-redirect")
      .authorizedGrantTypes("authorization_code")
      .authorities("ROLE_CLIENT")
      .scopes("read", "trust")
      .autoApprove(true)
      .and()
      .withClient("clientIdPassword")
      .secret("secret")
      .authorizedGrantTypes(
      "password","authorization_code", "refresh_token")
      .scopes("read")
  }

  @Bean
  TokenStore tokenStore() {
    return new JdbcTokenStore(dataSourcePlatform)
  }

}
