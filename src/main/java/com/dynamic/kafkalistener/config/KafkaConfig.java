package com.dynamic.kafkalistener.config;

import com.dynamic.kafkalistener.factory.kafkaconsumer.DynamicKafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

  @Value("${kafka.servers}")
  private String kafkaServers;

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 300000);
    props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 75000);
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 2);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    return props;
  }

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
    final ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    final SeekToCurrentErrorHandler errorHandler = new SeekToCurrentErrorHandler(new FixedBackOff(10L, 3L));
    errorHandler.setCommitRecovered(true);
    factory.setErrorHandler(errorHandler);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
    return factory;
  }

  @Bean
  public KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry() {
    return new KafkaListenerEndpointRegistry();
  }

  @Bean
  public DynamicKafkaListenerRegistry dynamicKafkaListenerRegistry(
      KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
      KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory,
      DynamicKafkaConsumerFactory dynamicKafkaConsumerFactory) {
    return new DynamicKafkaListenerRegistry(kafkaListenerEndpointRegistry, kafkaListenerContainerFactory, dynamicKafkaConsumerFactory);
  }


}
