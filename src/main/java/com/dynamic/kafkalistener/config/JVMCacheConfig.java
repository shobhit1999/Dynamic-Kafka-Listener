package com.dynamic.kafkalistener.config;

import com.dynamic.kafkalistener.dao.PropertiesDao;
import com.dynamic.kafkalistener.entities.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JVMCacheConfig {

  private final PropertiesDao propertiesDao;
  private final Map<String, Properties> propertiesCache = new HashMap<>();

  @Autowired
  public JVMCacheConfig(
      PropertiesDao propertiesDao) {
    this.propertiesDao = propertiesDao;
  }

  @PostConstruct
  private void init() {
    propertiesDao.findAll().forEach(item -> this.propertiesCache.put(item.getName(), item));
  }

  public Properties getPropertyByName(String name) {
    return propertiesCache.get(name);
  }
}
