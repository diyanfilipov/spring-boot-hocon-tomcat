package com.diyanfilipov.config

import com.diyanfilipov.config.jpa.date.ClockBasedDateTimeProvider
import com.mchange.v2.c3p0.ComboPooledDataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.aspectj.EnableSpringConfigured
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.Database
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EnableTransactionManagement(order = 15)
@EnableJpaAuditing(modifyOnCreate = false, dateTimeProviderRef = 'dateTimeProvider')
@EnableJpaRepositories(
  basePackages = 'com.diyanfilipov.domain.jpa',
  entityManagerFactoryRef = 'entityManagerFactoryJpa',
  transactionManagerRef = 'transactionManagerJpa'
)
@EnableSpringConfigured
class JpaPersistenceConfig {

  @Value('${diyanfilipov.services.db.jpa.type}')
  String jpaType

  @Autowired
  @Qualifier('dataSourcePlatform')
  ComboPooledDataSource dataSourcePlatform

  @Bean(name = 'entityManagerFactoryJpa')
//  @DependsOn('liquibaseUpdate')
  LocalContainerEntityManagerFactoryBean entityManagerFactoryJpa() {

    Properties jpaProps = new Properties()
//    jpaProps.setProperty('hibernate.ejb.naming_strategy', 'org.hibernate.cfg.ImprovedNamingStrategy')
//    jpaProps.setProperty('spring.jpa.hibernate.naming.implicit-strategy', 'org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl')
//    jpaProps.setProperty('spring.jpa.hibernate.naming.physical-strategy', 'org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl')
//    jpaProps.setProperty('hibernate.implicit_naming_strategy', 'org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl')
    jpaProps.setProperty('hibernate.physical_naming_strategy', 'com.diyanfilipov.config.jpa.naming.UnderscoreNamingStrategy')
    jpaProps.setProperty('jadira.usertype.autoRegisterUserTypes', 'true')
    jpaProps.setProperty('hibernate.show_sql', 'true')
    jpaProps.setProperty('hibernate.format_sql', 'true')

    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean()
    factoryBean.with {
      it.dataSource = dataSourcePlatform
      jpaVendorAdapter = jpaVendor()
      jpaProperties = jpaProps
      packagesToScan = ['com.diyanfilipov.domain.jpa'].toArray(new String[1])
      persistenceUnitName = 'entityManagerJpa'
    }
    factoryBean
  }

  @Bean
  @Primary
  JpaTransactionManager transactionManagerJpa() {
    JpaTransactionManager transactionManager = new JpaTransactionManager()
    transactionManager.entityManagerFactory = entityManagerFactoryJpa().object
    transactionManager
  }

  @Bean
  HibernateJpaVendorAdapter jpaVendor() {
    HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter()
    vendor.with {
      generateDdl = false
      database = Database.valueOf(jpaType)
    }
    vendor
  }

//  liquibase {
//    change-log = "classpath:com/diyanfilipov/migration/changelog.xml"
//  }
  @Bean
  SpringLiquibase liquibase() {

    SpringLiquibase liquibase = new SpringLiquibase()
    liquibase.setDataSource(dataSourcePlatform)
    liquibase.setChangeLog('classpath:com/diyanfilipov/migration/changelog.xml')
    liquibase
  }

  @Bean
  DateTimeProvider dateTimeProvider() {
    new ClockBasedDateTimeProvider()
  }
}
