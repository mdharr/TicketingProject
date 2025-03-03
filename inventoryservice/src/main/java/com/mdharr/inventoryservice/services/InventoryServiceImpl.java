package com.mdharr.inventoryservice.services;

import com.mdharr.inventoryservice.entities.Event;
import com.mdharr.inventoryservice.entities.Venue;
import com.mdharr.inventoryservice.repositories.EventRepository;
import com.mdharr.inventoryservice.repositories.VenueRepository;
import com.mdharr.inventoryservice.responses.EventInventoryResponse;
import com.mdharr.inventoryservice.responses.VenueInventoryResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final EventRepository eventRepository;

    private final VenueRepository venueRepository;

    @Autowired
    public InventoryServiceImpl(final EventRepository eventRepository, final VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

//    @Override
//    public List<EventInventoryResponse> getAllEvents() {
//        final List<Event> events = eventRepository.findAll();
//
//        return events.stream().map(event -> EventInventoryResponse.builder()
//                .event(event.getName())
//                .capacity(event.getLeftCapacity())
//                .venue(event.getVenue())
//                .build()).collect(Collectors.toList());
//    }

    @Override
    public List<EventInventoryResponse> getAllEvents() {
        final List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> EventInventoryResponse.builder()
                .eventId(event.getId())        // Add this line
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())  // Add this line
                .build()).collect(Collectors.toList());
    }

    @Override
    public VenueInventoryResponse getVenueInformation(final Long venueId) {
        final Venue venue = venueRepository.findById(venueId).orElse(null);

        return VenueInventoryResponse.builder()
                .venueId(venue.getId())
                .venueName(venue.getName())
                .totalCapacity(venue.getTotalCapacity())
                .build();
    }

    @Override
    public EventInventoryResponse getEventInventory(Long eventId) {
        final Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        // Debug: Print out the event details
        System.out.println("Event ID: " + event.getId());
        System.out.println("Event Name: " + event.getName());

        return EventInventoryResponse.builder()
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
                .eventId(event.getId())
                .build();
    }
}
