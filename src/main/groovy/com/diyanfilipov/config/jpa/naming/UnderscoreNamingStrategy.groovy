package com.diyanfilipov.config.jpa.naming

import org.apache.commons.lang.StringUtils
import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment

class UnderscoreNamingStrategy implements PhysicalNamingStrategy {

  static final String UNDERSCORE = '_'

  @Override
  Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    name
  }

  @Override
  Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    name
  }

  @Override
  Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    return jdbcEnvironment.getIdentifierHelper().toIdentifier(
      splitByUnderscore(splitCamelCaseWords(name.text)),
      name.isQuoted()
    )
  }

  @Override
  Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    name
  }

  @Override
  Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
    return jdbcEnvironment.getIdentifierHelper().toIdentifier(
      splitByUnderscore(splitCamelCaseWords(name.text)),
      name.isQuoted()
    )
  }

  private static LinkedList<String> splitCamelCaseWords(String name) {
    StringUtils.splitByCharacterTypeCamelCase(name).findAll {
      it != null && StringUtils.isNotBlank(it)
    }.collect { String part ->
      part.toLowerCase(Locale.ROOT)
    }
  }

  private static String splitByUnderscore(List<String> parts) {
    boolean firstPass = true
    String separator = ""
    StringBuilder result = new StringBuilder()

    parts.each { String part ->
      result.append(separator).append(part)
      if (firstPass) {
        firstPass = false
        separator = UNDERSCORE
      }
    }

    return result.toString()
  }
}
