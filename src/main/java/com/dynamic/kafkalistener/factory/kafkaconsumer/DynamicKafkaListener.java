package com.dynamic.kafkalistener.factory.kafkaconsumer;

import com.dynamic.kafkalistener.enumeration.DynamicKafkaListenerType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.MDC;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.UUID;

@Slf4j
public abstract class DynamicKafkaListener implements AcknowledgingMessageListener<String, Object> {

    /**
     * Invoked with data from kafka.
     *
     * @param data           the data to be processed.
     * @param acknowledgment the acknowledgment.
     */
    @Override
    public void onMessage(ConsumerRecord<String, Object> data, Acknowledgment acknowledgment) {
        MDC.put("X-Request-ID", UUID.randomUUID().toString().replace("-", ""));
        try {
            consumePayload(String.valueOf(data.value()), data.headers(), acknowledgment);
        } finally {
            MDC.clear();
        }
    }

    /**
     * DONOT PUT @KafkaListener on this method
     *
     * @param payload        payload
     * @param headers        kafka payload headers
     * @param acknowledgment ack
     */
    abstract void consumePayload(String payload, Headers headers, Acknowledgment acknowledgment);

    /**
     * Unique identifier for the type for consumption process
     *
     * @return enum listener type
     */
    @NonNull
    abstract DynamicKafkaListenerType getListenerType();

}