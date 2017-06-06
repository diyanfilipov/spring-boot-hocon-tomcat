package com.diyanfilipov.config.jpa.date

import com.diyanfilipov.config.time.Clock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.auditing.DateTimeProvider

class ClockBasedDateTimeProvider implements DateTimeProvider {

  @Autowired
  Clock clock

  @Override
  Calendar getNow() {
    clock.now.toGregorianCalendar()
  }
}
