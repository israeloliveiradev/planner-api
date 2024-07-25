package com.israeloliveiradev.planner.services;

import com.israeloliveiradev.planner.entities.Participant;
import com.israeloliveiradev.planner.entities.Trip;
import com.israeloliveiradev.planner.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.stream;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void addParticipantsToEvent(List<String> participantsToInvite, Trip trip) {
        List<Participant> participants = participantsToInvite.stream().map(email ->  new Participant(email, trip)).toList();

        this.participantRepository.saveAll(participants);

        System.out.println(participants.get(0).getId());

    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {

    }
}
