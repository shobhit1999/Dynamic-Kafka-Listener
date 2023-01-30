package com.dynamic.kafkalistener.config;

import com.dynamic.kafkalistener.dto.DynamicKafkaListenerConfiguration;
import com.dynamic.kafkalistener.factory.kafkaconsumer.DynamicKafkaListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.lang.reflect.Method;

public abstract class AbstractCustomMessageListener {

    private final DynamicKafkaListenerConfiguration dynamicKafkaListenerConfiguration;

    protected AbstractCustomMessageListener(DynamicKafkaListenerConfiguration dynamicKafkaListenerConfiguration) {
        this.dynamicKafkaListenerConfiguration = dynamicKafkaListenerConfiguration;
    }

    public KafkaListenerEndpoint createKafkaListenerEndpoint() {
        final MethodKafkaListenerEndpoint<String, Object> kafkaListenerEndpoint = new MethodKafkaListenerEndpoint<>();

        kafkaListenerEndpoint.setId(dynamicKafkaListenerConfiguration.getConsumerId());
        kafkaListenerEndpoint.setGroupId(dynamicKafkaListenerConfiguration.getGroupId());
        kafkaListenerEndpoint.setAutoStartup(dynamicKafkaListenerConfiguration.isAutoStartup());
        kafkaListenerEndpoint.setTopics(dynamicKafkaListenerConfiguration.getTopic());
        kafkaListenerEndpoint.setConcurrency(dynamicKafkaListenerConfiguration.getConcurrency());

        kafkaListenerEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        final DynamicKafkaListener dynamicKafkaListenerInstance = getDynamicKafkaListenerInstance();

        kafkaListenerEndpoint.setBean(dynamicKafkaListenerInstance);
        try {
            final Method method = dynamicKafkaListenerInstance.getClass().getMethod("onMessage", ConsumerRecord.class,
                                                                                    Acknowledgment.class);
            kafkaListenerEndpoint.setMethod(method);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Method not found exception triggered for dynamic kafka config " + dynamicKafkaListenerConfiguration, e);
        }
        return kafkaListenerEndpoint;
    }

    protected abstract DynamicKafkaListener getDynamicKafkaListenerInstance();
}
