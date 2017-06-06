package com.diyanfilipov.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.core.type.filter.RegexPatternTypeFilter

import java.util.regex.Pattern

@Configuration
@ComponentScan(
  basePackages = ['com.diyanfilipov'],
  excludeFilters = [@ComponentScan.Filter(type = FilterType.CUSTOM, value = [ConfigPackageExcludeFilter])]
)
class ApplicationPackageScanConfig {

  static class ConfigPackageExcludeFilter extends RegexPatternTypeFilter {
    ConfigPackageExcludeFilter() {
      super(Pattern.compile('com\\.diyanfilipov\\.config.*'))
    }
  }
}
