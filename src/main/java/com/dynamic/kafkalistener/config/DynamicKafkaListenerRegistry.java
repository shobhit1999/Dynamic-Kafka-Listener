package com.dynamic.kafkalistener.config;

import com.dynamic.kafkalistener.dto.DynamicKafkaListenerConfiguration;
import com.dynamic.kafkalistener.enumeration.DynamicKafkaListenerType;
import com.dynamic.kafkalistener.factory.kafkaconsumer.DynamicKafkaConsumerFactory;
import com.dynamic.kafkalistener.factory.kafkaconsumer.DynamicKafkaListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import javax.annotation.PostConstruct;

@Slf4j
public class DynamicKafkaListenerRegistry {

  private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
  private final KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory;
  private final DynamicKafkaConsumerFactory dynamicKafkaConsumerFactory;

  public DynamicKafkaListenerRegistry(
      KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
      KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory,
      DynamicKafkaConsumerFactory dynamicKafkaConsumerFactory) {
    this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    this.kafkaListenerContainerFactory = kafkaListenerContainerFactory;
    this.dynamicKafkaConsumerFactory = dynamicKafkaConsumerFactory;
  }

  @PostConstruct
  public void registerDynamicListeners() {
    log.info("Refreshing dynamic kafka listener registry!");
    dynamicKafkaConsumerFactory.getConfigurations().forEach(this::registerCustomKafkaListener);
  }

  private void registerCustomKafkaListener(DynamicKafkaListenerConfiguration dynamicKafkaListenerConfiguration) {
    final DynamicKafkaListenerType listenerType = dynamicKafkaListenerConfiguration.getListenerType();
    final AbstractCustomMessageListener customMessageListener = new AbstractCustomMessageListener(dynamicKafkaListenerConfiguration) {
      @Override
      protected DynamicKafkaListener getDynamicKafkaListenerInstance() {
        return dynamicKafkaConsumerFactory.getKafkaListener(listenerType);
      }
    };
    final KafkaListenerEndpoint kafkaListenerEndpoint = customMessageListener.createKafkaListenerEndpoint();
    final boolean autoStartup = dynamicKafkaListenerConfiguration.isAutoStartup();
    kafkaListenerEndpointRegistry.registerListenerContainer(kafkaListenerEndpoint, kafkaListenerContainerFactory, autoStartup);
  }


}
