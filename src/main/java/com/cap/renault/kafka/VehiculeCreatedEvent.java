package com.cap.renault.kafka;

import java.util.Date;
import java.util.Set;

public record VehiculeCreatedEvent(Long vehicleId, Set<Long> garageIds,Set<Long> accessoiresIds, String model, String brand, int anneFabrication, String typeCarburant, Date createdAt) { }
