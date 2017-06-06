package com.diyanfilipov.config

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DbConfig {

  @Value('${diyanfilipov.services.db.jpa.driver}')
  String jpaDriver

  @Value('${diyanfilipov.services.db.jpa.url}')
  String jpaUrl

  @Value('${diyanfilipov.services.db.jpa.username}')
  String jpaUsername

  @Value('${diyanfilipov.services.db.jpa.password}')
  String jpaPassword

  @Value('${diyanfilipov.services.db.jpa.maxPoolSize : 100}')
  Integer jpaMaxPoolSize

//  @Value("classpath:oauth_tables_schema.sql")
//  private Resource schemaScript
//
//  @Bean
//  DataSourceInitializer dataSourceInitializer(ComboPooledDataSource dataSource) {
//    DataSourceInitializer initializer = new DataSourceInitializer()
//    initializer.setDataSource(dataSource)
//    initializer.setDatabasePopulator(databasePopulator())
//    initializer
//  }
//
//  private DatabasePopulator databasePopulator() {
//    ResourceDatabasePopulator populator = new ResourceDatabasePopulator()
//    populator.addScript(schemaScript)
//    populator
//  }

  @Bean(name = 'dataSourcePlatform', destroyMethod = 'close')
  ComboPooledDataSource dataSourcePlatform() {

    ComboPooledDataSource dataSource = new ComboPooledDataSource()
    dataSource.with {
      driverClass = jpaDriver
      jdbcUrl = jpaUrl
      user = jpaUsername
      password = jpaPassword

      acquireIncrement = 1
      checkoutTimeout = 20000
      debugUnreturnedConnectionStackTraces = false
      idleConnectionTestPeriod = 600
      initialPoolSize = 3
      minPoolSize = 3
      maxIdleTimeExcessConnections = 1800
      maxPoolSize = jpaMaxPoolSize
      maxStatements = 180
      numHelperThreads = 3
      preferredTestQuery = 'select 1;'
      testConnectionOnCheckin = true
      unreturnedConnectionTimeout = 24000
    }
    dataSource
  }
}
