package com.israeloliveiradev.planner.controller;


import com.israeloliveiradev.planner.dto.*;
import com.israeloliveiradev.planner.entities.*;
import com.israeloliveiradev.planner.repositories.TripRepository;
import com.israeloliveiradev.planner.services.ActivityService;
import com.israeloliveiradev.planner.services.LinkService;
import com.israeloliveiradev.planner.services.ParticipantService;
import com.israeloliveiradev.planner.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {


    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private TripService tripService;


    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        return this.tripService.getAllTrips();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {

        return this.tripService.getTripDetails(id);
    }

    @GetMapping("/destination/{destination}")
    public ResponseEntity<List<Trip>> getTripsByDestination(@PathVariable String destination) {
        return this.tripService.getTripsByDestination(destination);
    }

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayLoad tripRequestPayLoad) {

        return this.tripService.createTrip(tripRequestPayLoad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayLoad tripRequestPayLoad) {

        return this.tripService.updateTrip(id, tripRequestPayLoad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable UUID id) {
        return this.tripService.deleteTrip(id);
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        return this.tripService.confirmTrip(id);
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipants(@PathVariable UUID id, @RequestBody
    ParticipantRequestPayLoad participantRequestPayLoad){

        return this.tripService.inviteParticipants(id, participantRequestPayLoad);
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id) {
        List<ParticipantData> participantList = this.participantService.getAllParticipantsFromEvent(id);

        return ResponseEntity.ok(participantList);
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityCreateResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayLoad activityRequestPayLoad) {

        return this.tripService.registerActivity(id, activityRequestPayLoad);
    }
    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID id) {
        List<ActivityData> activityDataList = this.activityService.getAllActivitiesFromId(id);


        return ResponseEntity.ok(activityDataList);
    }

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkCreateResponse> registerLinks(@PathVariable UUID id, @RequestBody LinkRequestPayLoad linkRequestPayLoad) {

        return this.tripService.registerLinks(id, linkRequestPayLoad);
    }
    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID id) {
        List<LinkData> linkDataList = this.linkService.getAllLinksFromId(id);


        return ResponseEntity.ok(linkDataList);
    }
}


