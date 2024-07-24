package com.israeloliveiradev.planner.controller;


import com.israeloliveiradev.planner.entities.Trip;
import com.israeloliveiradev.planner.entities.TripCreateResponse;
import com.israeloliveiradev.planner.entities.TripRequestPayLoad;
import com.israeloliveiradev.planner.repositories.TripRepository;
import com.israeloliveiradev.planner.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;


    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        return ResponseEntity.ok(this.tripRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/destination/{destination}")
    public ResponseEntity<List<Trip>> getTripsByDestination(@PathVariable String destination) {
        return ResponseEntity.ok(this.tripRepository.findAllByDestinationContainingIgnoreCase(destination));
    }

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayLoad tripRequestPayLoad) {

        Trip newTrip = new Trip(tripRequestPayLoad);

        this.tripRepository.save(newTrip);

        this.participantService.addParticipantsToEvent(tripRequestPayLoad.emails_to_invite(), newTrip.getId());

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayLoad tripRequestPayLoad) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip tripToUpdate = trip.get();
            tripToUpdate.setDestination(tripRequestPayLoad.destination());
            tripToUpdate.setStartsAt(LocalDateTime.parse(tripRequestPayLoad.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            tripToUpdate.setEndsAt(LocalDateTime.parse(tripRequestPayLoad.ends_at(), DateTimeFormatter.ISO_DATE_TIME));

            this.tripRepository.save(tripToUpdate);

            return ResponseEntity.ok(tripToUpdate);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            this.tripRepository.delete(trip.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip tripToConfirm = trip.get();
            tripToConfirm.setConfirmed(true);

            this.tripRepository.save(tripToConfirm);
            this.participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(tripToConfirm);
        }

        return ResponseEntity.notFound().build();
    }

}
