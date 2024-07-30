package com.israeloliveiradev.planner.services;

import com.israeloliveiradev.planner.dto.*;
import com.israeloliveiradev.planner.entities.*;
import com.israeloliveiradev.planner.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;


    public ResponseEntity<List<Trip>> getAllTrips() {

        return ResponseEntity.ok(this.tripRepository.findAll());
    }

    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Trip>> getTripsByDestination(@PathVariable String destination) {
        return ResponseEntity.ok(this.tripRepository.findAllByDestinationContainingIgnoreCase(destination));

    }

    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayLoad tripRequestPayLoad) {
        Trip newTrip = new Trip(tripRequestPayLoad);
        this.tripRepository.save(newTrip);

        UUID firstParticipantId = this.participantService.addParticipantsToEvent(tripRequestPayLoad.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId(), firstParticipantId));
    }

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

    public ResponseEntity<Void> deleteTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            this.tripRepository.delete(trip.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

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

    public ResponseEntity<ParticipantCreateResponse> inviteParticipants(@PathVariable UUID id, @RequestBody
    ParticipantRequestPayLoad participantRequestPayLoad) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ParticipantCreateResponse participantResponse = this.participantService.addParticipantToEvent(participantRequestPayLoad.email(), rawTrip);

            if (rawTrip.isConfirmed()) {
                this.participantService.triggerConfirmationEmailToParticipant(participantRequestPayLoad.email());
            }

            return ResponseEntity.ok(participantResponse);
        }

        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<ActivityCreateResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayLoad activityRequestPayLoad) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ActivityCreateResponse activityResponse = this.activityService.createActivity(activityRequestPayLoad, rawTrip);

            return ResponseEntity.ok(activityResponse);
        }

        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<LinkCreateResponse> registerLinks(@PathVariable UUID id, @RequestBody LinkRequestPayLoad linkRequestPayLoad) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            LinkCreateResponse linkCreateResponse = this.linkService.registerLink(linkRequestPayLoad, rawTrip);

            return ResponseEntity.ok(linkCreateResponse);
        }

        return ResponseEntity.notFound().build();
    }
}