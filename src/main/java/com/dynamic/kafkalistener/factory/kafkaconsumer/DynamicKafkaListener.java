package com.dynamic.kafkalistener.factory.kafkaconsumer;

import com.dynamic.kafkalistener.dto.DynamicKafkaListenerConfiguration;
import com.dynamic.kafkalistener.enumeration.DynamicKafkaListenerType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.MDC;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

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
        try {
            preProcessRequest(data, acknowledgment);
            consumePayload(String.valueOf(data.value()), data.headers(), acknowledgment);
        } finally {
            MDC.clear();
        }
    }

    protected void preProcessRequest(ConsumerRecord<String, Object> data, Acknowledgment acknowledgment) {
        //eat
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