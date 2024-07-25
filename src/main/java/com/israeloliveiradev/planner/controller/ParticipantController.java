package com.israeloliveiradev.planner.controller;

import com.israeloliveiradev.planner.entities.Participant;
import com.israeloliveiradev.planner.entities.ParticipantRequestPayLoad;
import com.israeloliveiradev.planner.repositories.ParticipantRepository;
import com.israeloliveiradev.planner.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ParticipantRepository participantRepository;


    @PostMapping("/{id}/confirmation")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayLoad participantRequestPayLoad) {

        Optional<Participant> participant = this.participantRepository.findById(id);

        if(participant.isPresent()) {
            Participant rawParticipant = participant.get();
            rawParticipant.setIsConfirmed(true);
            rawParticipant.setName(participantRequestPayLoad.name());

            this.participantRepository.save(rawParticipant);

            return ResponseEntity.ok(rawParticipant);
        }

        return ResponseEntity.notFound().build();
    }



}
