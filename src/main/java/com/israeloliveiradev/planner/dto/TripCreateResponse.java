package com.israeloliveiradev.planner.dto;

import java.util.UUID;


public record TripCreateResponse(UUID tripID, UUID firstParticipantID) {
}
