package com.israeloliveiradev.planner.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public record TripData(UUID id, String destination, LocalDateTime starts_at, String ends_at,  String ownerName,  String ownerEmail, boolean isConfirmed) {
}
