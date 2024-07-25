package com.israeloliveiradev.planner.repositories;

import com.israeloliveiradev.planner.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    public List<Participant> findAllByTripId(UUID tripId);
}
