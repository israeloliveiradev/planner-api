package com.israeloliveiradev.planner.services;

import com.israeloliveiradev.planner.entities.*;
import com.israeloliveiradev.planner.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;


    public ResponseEntity<List<Trip>> getAllTrips() {

        return ResponseEntity.ok(this.tripRepository.findAll());
    }

    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    public ResponseEntity<List<Trip>> getTripsByDestination(@PathVariable String destination){
        return ResponseEntity.ok(this.tripRepository.findAllByDestinationContainingIgnoreCase(destination));

    }
}