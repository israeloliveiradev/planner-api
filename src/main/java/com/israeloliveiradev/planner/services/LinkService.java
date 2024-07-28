package com.israeloliveiradev.planner.services;

import com.israeloliveiradev.planner.entities.*;
import com.israeloliveiradev.planner.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    LinkRepository linkRepository;

    public LinkCreateResponse registerLink(LinkRequestPayLoad linkRequestPayLoad, Trip trip) {
        Link newLink = new Link(linkRequestPayLoad.title(), linkRequestPayLoad.url(), trip);

        this.linkRepository.save(newLink);

        return new LinkCreateResponse(newLink.getId());
    }

    public List<LinkData> getAllLinksFromId(UUID linkId) {
        return this.linkRepository.findAllByTripId(linkId).stream().map(link -> new LinkData(link.getId(), link.getTitle(), link.getUrl())).toList();
    }
}

