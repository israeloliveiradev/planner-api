package com.israeloliveiradev.planner.services;

import com.israeloliveiradev.planner.dto.ActivityCreateResponse;
import com.israeloliveiradev.planner.dto.ActivityData;
import com.israeloliveiradev.planner.dto.ActivityRequestPayLoad;
import com.israeloliveiradev.planner.entities.*;
import com.israeloliveiradev.planner.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    public ActivityCreateResponse createActivity(ActivityRequestPayLoad activityRequestPayLoad, Trip trip) {
        Activity newActivity = new Activity(activityRequestPayLoad.title(), activityRequestPayLoad.occurs_at(), trip);

        this.activityRepository.save(newActivity);

        return new ActivityCreateResponse(newActivity.getId());
    }

    public List<ActivityData> getAllActivitiesFromId(UUID tripId) {
        return this.activityRepository.findAllByTripId(tripId).stream().map(activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
