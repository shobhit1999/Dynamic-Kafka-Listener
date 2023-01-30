package com.dynamic.kafkalistener.dto;

import com.dynamic.kafkalistener.enumeration.DynamicKafkaListenerType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DynamicKafkaListenerConfiguration {

    @NotBlank
    private String consumerId;

    @NotBlank
    private String topic;

    @Positive
    private int concurrency;

    @NotBlank
    private String groupId;

    @NotNull
    private DynamicKafkaListenerType listenerType;

    private boolean autoStartup;

}
