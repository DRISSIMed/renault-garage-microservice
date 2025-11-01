package com.cap.renault.kafka;

import com.cap.renault.entity.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class VehiculeEventProducer {
    @Autowired
    private  KafkaTemplate<String, VehiculeCreatedEvent> kafka;
    @Value("${app.events.topic}")
    private  String topic;

    public void publishCreatedVehicule(Vehicule v) {
        VehiculeCreatedEvent evt = new VehiculeCreatedEvent(
                v.getId(),
                v.getGarage() != null ? v.getGarage().getId() : null,
                v.getModel(),
                v.getBrand(),
                v.getAnneeFabrication() ,
                v.getTypeCarburant(),
                new Date()
        );
        kafka.send(topic, evt);
    }
}
