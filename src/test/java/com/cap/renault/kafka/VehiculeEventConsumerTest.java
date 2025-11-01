package com.cap.renault.kafka;

import com.cap.renault.entity.Vehicule;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VehiculeEventConsumerTest {

    @InjectMocks
    private VehiculeEventConsumer consumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testConsumeVehicule() {
        VehiculeCreatedEvent evt = new VehiculeCreatedEvent(
                1L, 10L, "Clio 5", "Renault", 2022, "Essence", new Date()
        );

        ConsumerRecord<String, VehiculeCreatedEvent> record =
                new ConsumerRecord<>("vehicule-created-topic", 0, 0L, "key", evt);

        consumer.onVehicleEvent(record);

        VehiculeCreatedEvent consumed = record.value();
        assertEquals(1L, consumed.vehicleId());
        assertEquals(10L, consumed.garageId());
        assertEquals("Clio 5", consumed.model());

    }
}
