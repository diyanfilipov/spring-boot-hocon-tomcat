package com.diyanfilipov

import org.apache.commons.lang.RandomStringUtils

@Singleton
class RandomDataGenerator {

  Random random = new Random()

  String generateRandomUuid() {
    UUID.randomUUID().toString().replace('-', '')
  }

  String createString(int length) {
    RandomStringUtils.randomAlphanumeric(length)
  }

  String createNumeric(int length) {
    RandomStringUtils.randomNumeric(length)
  }

  String createEmail() {
    RandomStringUtils.randomAlphanumeric(15) +
      '.' + RandomStringUtils.randomAlphanumeric(15) +
      '@' + RandomStringUtils.randomAlphanumeric(20) + '.com'
  }

  Boolean pickBoolean() {
    random.nextBoolean()
  }

  def pickEnum(Class enumType) {
    def enumConstants = enumType.getEnumConstants()
    enumConstants ? enumConstants[random.nextInt(enumConstants.size())] : null
  }

}
