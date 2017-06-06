package com.diyanfilipov.config

import com.mchange.v2.c3p0.ComboPooledDataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn

@Configuration
class JpaMigrationConfiguration {

  @Autowired
  @Qualifier('dataSourcePlatform')
  ComboPooledDataSource dataSourcePlatform

  @Bean
  SpringLiquibase liquibaseUpdate() {
    SpringLiquibase liquibase = new SpringLiquibase()
    liquibase.with {
      changeLog = 'classpath:com/diyanfilipov/migration/changelog.xml'
      dataSource = dataSourcePlatform
    }
    liquibase
  }
}
