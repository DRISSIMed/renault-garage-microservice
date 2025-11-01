package com.cap.renault.kafka;

import java.util.Date;

public record VehiculeCreatedEvent(Long vehicleId, Long garageId, String model, String brand, int anneFabrication, String typeCarburant, Date createdAt) { }
