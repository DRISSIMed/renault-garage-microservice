package com.cap.renault.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class VehiculeEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(VehiculeEventConsumer.class);
    @KafkaListener(topics = "${app.events.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onVehicleEvent(ConsumerRecord<String, VehiculeCreatedEvent> record) {
        VehiculeCreatedEvent evt = record.value();
        log.info("Received vehicle created event: key={}, event={}", record.key(), evt);

    }
}
