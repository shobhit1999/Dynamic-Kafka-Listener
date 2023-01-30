package com.dynamic.kafkalistener.factory.kafkaconsumer;

import com.dynamic.kafkalistener.enumeration.DynamicKafkaListenerType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import static com.dynamic.kafkalistener.enumeration.DynamicKafkaListenerType.CONSUMER_1;

@Slf4j
@Component
public class Consumer1 extends DynamicKafkaListener {

  @Override
  public void consumePayload(String payload, Headers headers, Acknowledgment acknowledgment) {
    log.info("Consuming callback for consumer 1 {}, headers {}", payload, headers);
    acknowledgment.acknowledge();
  }

  @Override
  public @NonNull DynamicKafkaListenerType getListenerType() {
    return CONSUMER_1;
  }

}
