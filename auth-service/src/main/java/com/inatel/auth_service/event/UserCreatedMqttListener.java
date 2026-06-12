package com.inatel.auth_service.event;

import com.inatel.auth_service.config.MqttProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedMqttListener {

    private final MqttClient mqttClient;
    private final ObjectMapper objectMapper;
    private final MqttProperties props;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(UserCreatedEvent event) {
        try {
            MqttMessage message = new MqttMessage(objectMapper.writeValueAsBytes(event));
            message.setQos(1);
            mqttClient.publish(props.userCreatedTopic(), message);
            log.info("Published user-created event for {}", event.id());
        } catch (MqttException | JacksonException e) {
            log.error("Failed to publish user-created event for {}", event.id(), e);
        }
    }
}
