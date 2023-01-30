package com.dynamic.kafkalistener.factory.kafkaconsumer;

import com.dynamic.kafkalistener.config.JVMCacheConfig;
import com.dynamic.kafkalistener.dto.DynamicKafkaListenerConfiguration;
import com.dynamic.kafkalistener.entities.Properties;
import com.dynamic.kafkalistener.enumeration.DynamicKafkaListenerType;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DynamicKafkaConsumerFactory {

  private final Map<DynamicKafkaListenerType, DynamicKafkaListener> dynamicKafkaListenerMap;
  private final JVMCacheConfig jvmCacheConfig;

  public DynamicKafkaConsumerFactory(List<DynamicKafkaListener> dynamicKafkaListeners, JVMCacheConfig jvmCacheConfig) {
    this.jvmCacheConfig = jvmCacheConfig;
    final long distinctListenerTypesCount = dynamicKafkaListeners.stream().map(DynamicKafkaListener::getListenerType).distinct().count();
    Assert.isTrue(distinctListenerTypesCount == dynamicKafkaListeners.size(), "Duplicate listeners registered");
    dynamicKafkaListenerMap =
        Collections.unmodifiableMap(dynamicKafkaListeners.stream().collect(Collectors.toMap(DynamicKafkaListener::getListenerType,
            Function.identity())));
  }

  @NonNull
  public DynamicKafkaListener getKafkaListener(DynamicKafkaListenerType type) {
    return dynamicKafkaListenerMap.get(type);
  }

  public List<DynamicKafkaListenerConfiguration> getConfigurations() {
    return dynamicKafkaListenerMap.values().stream().map(this::getConfiguration).collect(Collectors.toList());
  }

  private DynamicKafkaListenerConfiguration getConfiguration(DynamicKafkaListener dynamicKafkaListener) {
    final DynamicKafkaListenerType listenerType = dynamicKafkaListener.getListenerType();
    final Properties config = jvmCacheConfig.getPropertyByName(listenerType.name());
    Assert.notNull(config, "Properties not found for listener: " + listenerType.name());
    final DynamicKafkaListenerConfiguration defaultConfig = new DynamicKafkaListenerConfiguration();
    defaultConfig.setConsumerId(String.valueOf(config.getId()));
    defaultConfig.setGroupId(config.getGroupId());
    defaultConfig.setTopic(config.getTopic());
    defaultConfig.setConcurrency(config.getConcurrency());
    defaultConfig.setListenerType(listenerType);
    defaultConfig.setAutoStartup(config.isAutoStartup());
    return defaultConfig;
  }


}
