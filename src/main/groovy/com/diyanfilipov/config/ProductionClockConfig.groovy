package com.diyanfilipov.config

import com.diyanfilipov.config.time.Clock
import com.diyanfilipov.config.time.ProductionClock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductionClockConfig {

  @Bean
  Clock clock() {
    new ProductionClock()
  }
}
