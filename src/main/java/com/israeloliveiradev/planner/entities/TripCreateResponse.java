package com.israeloliveiradev.planner.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


public record TripCreateResponse(UUID tripID, UUID firstParticipantID) {
}
