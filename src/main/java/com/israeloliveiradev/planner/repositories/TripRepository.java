package com.israeloliveiradev.planner.repositories;

import com.israeloliveiradev.planner.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {

    public List<Trip> findAllByDestinationContainingIgnoreCase(@Param("destination") String destination);
}
