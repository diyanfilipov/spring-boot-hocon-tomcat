package com.diyanfilipov.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.joda.JodaModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary

import java.text.DateFormat
import java.text.SimpleDateFormat

@Configuration
@Import([
  ApplicationPackageScanConfig,
  DbConfig,
  JpaPersistenceConfig,
  SessionConfig,
  ProductionClockConfig,
  JerseyResourceConfig
])
class AppConfig {

  @Bean
  @Primary
  ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper()

    objectMapper.registerModule(new JodaModule())
    objectMapper.registerModule(new CustomJacksonModule())

    objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    objectMapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false)

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    format.timeZone = TimeZone.getTimeZone('UTC')

    // Always serialize to standard date format, but use default date deserializer to support jackson default format too
    objectMapper.setDateFormat(format)
    objectMapper.setTimeZone(format.timeZone)

    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

    objectMapper
  }

//  @Bean
//  @Primary
//  Jackson2ObjectMapperBuilder objectMapperBuilder() {
//
//    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
//
//    ObjectMapper objectMapper = new ObjectMapper()
//    objectMapper.registerModule(new JodaModule())
//    objectMapper.registerModule(new CustomJacksonModule())
//
//    objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
//    objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
//    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//    objectMapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false)
//
//    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//    format.timeZone = TimeZone.getTimeZone('UTC')
//
//    // Always serialize to standard date format, but use default date deserializer to support jackson default format too
//    objectMapper.setDateFormat(format)
//    objectMapper.setTimeZone(format.timeZone)
//
//    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
//
//
//    builder.configure(objectMapper)
//    builder
//  }

  static class CustomJacksonModule extends SimpleModule {

    CustomJacksonModule() {
      super('GStringModule', new Version(1, 1, 0, ''))
      this.addSerializer(GString, new GStringSerializer())
    }
  }

  static class GStringSerializer extends JsonSerializer<GString> {
    @Override
    void serialize(GString gString, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
      jsonGenerator.writeString(gString?.toString())
    }
  }
}
