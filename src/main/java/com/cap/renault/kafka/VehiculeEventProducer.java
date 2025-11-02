package com.cap.renault.kafka;

import com.cap.renault.entity.Accessoire;
import com.cap.renault.entity.Garage;
import com.cap.renault.entity.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class VehiculeEventProducer {
    @Autowired
    private  KafkaTemplate<String, VehiculeCreatedEvent> kafka;
    @Value("${app.events.topic}")
    private  String topic;

    public void publishCreatedVehicule(Vehicule v) {
        VehiculeCreatedEvent evt = new VehiculeCreatedEvent(
                v.getId(),
                v.getGarageVehicules()!= null && !v.getGarageVehicules().isEmpty() ? v.getGarageVehicules().stream()
                        .map(Garage::getId)
                        .collect(Collectors.toSet())
                        : Collections.emptySet(),
                v.getAccessoiresVehicules() !=null && !v.getAccessoiresVehicules().isEmpty() ?v.getAccessoiresVehicules().stream()
                        .map(Accessoire::getId)
                        .collect(Collectors.toSet())
                        : Collections.emptySet(),
                v.getModel(),
                v.getBrand(),
                v.getAnneeFabrication() ,
                v.getTypeCarburant(),
                new Date()
        );
        kafka.send(topic, evt);
    }
}
