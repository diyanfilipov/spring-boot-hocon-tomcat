package com.diyanfilipov.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigList
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigSyntax
import com.typesafe.config.ConfigValue
import org.springframework.boot.env.PropertySourceLoader
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.Resource

class HoconPropertySourceLoader implements PropertySourceLoader {

  @Override
  String[] getFileExtensions() {
    ['gconf']
  }

  @Override
  PropertySource<?> load(String name, Resource resource, String profile) throws IOException {
    if (profile != null) {
      return null
    }
    Config config = ConfigFactory.parseReader(new InputStreamReader(resource.getInputStream()),
      ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF))
      .resolve()

    LinkedHashMap<String, Object> properties = new LinkedHashMap<>()
    toFlatMap(properties, config)
    new MapPropertySource(name, properties)
  }

  private void toFlatMap(LinkedHashMap<String, Object> properties, Config config) {
    toFlatMap(properties, "", config)
  }

  private void toFlatMap(Map<String, Object> properties, String key, Config config) {
    final String prefix = "" == key ? "" : key + "."

    for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
      String propertyKey = prefix + entry.getKey()
      ConfigValue value = entry.getValue()
      addConfigValue(properties, propertyKey, value)
    }
  }

  private void addConfigValue(Map<String, Object> properties, String key, ConfigValue value) {
    if (value instanceof ConfigList) {
      processListValues(properties, key, (ConfigList) value);
    } else if (value instanceof ConfigObject) {
      processObjectValues(properties, key, (ConfigObject) value);
    } else {
      addScalarValue(properties, key, value);
    }
  }

  private void processListValues(Map<String, Object> properties, String key, ConfigList value) {
    int i = 0
    for (ConfigValue element : value) {
      addConfigValue(properties, String.format("%s[%d]", key, i++), element)
    }
  }

  private void processObjectValues(Map<String, Object> properties, String key, ConfigObject value) {
    toFlatMap(properties, key, value.toConfig())
  }

  private static void addScalarValue(Map<String, Object> properties, String key, ConfigValue value) {
    properties.put(key, value.unwrapped())
  }
}
