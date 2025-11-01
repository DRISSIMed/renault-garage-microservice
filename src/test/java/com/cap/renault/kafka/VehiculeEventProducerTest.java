package com.cap.renault.kafka;

import com.cap.renault.entity.Vehicule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class VehiculeEventProducerTest {
    @Mock
    private KafkaTemplate<String, VehiculeCreatedEvent> kafkaTemplate;

    @InjectMocks
    private VehiculeEventProducer producer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(producer, "topic", "vehicule-created-topic");
    }

    @Test
    void testPublishCreatedVehicule() {
        Vehicule vehicule = new Vehicule();
        vehicule.setId(1L);
        vehicule.setModel("Clio");
        vehicule.setAnneeFabrication(2025);
        vehicule.setBrand("Renault");
        producer.publishCreatedVehicule(vehicule);
        verify(kafkaTemplate, times(1)).send( eq("vehicule-created-topic"), any(VehiculeCreatedEvent.class));
    }
}