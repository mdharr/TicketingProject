package com.mdharr.inventoryservice.service;

import com.mdharr.inventoryservice.entity.Event;
import com.mdharr.inventoryservice.entity.Venue;
import com.mdharr.inventoryservice.repository.EventRepository;
import com.mdharr.inventoryservice.repository.VenueRepository;
import com.mdharr.inventoryservice.response.EventInventoryResponse;
import com.mdharr.inventoryservice.response.VenueInventoryResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final EventRepository eventRepository;

    private final VenueRepository venueRepository;

    @Autowired
    public InventoryServiceImpl(final EventRepository eventRepository, final VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public List<EventInventoryResponse> getAllEvents() {
        final List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> EventInventoryResponse.builder()
                .eventId(event.getId())
                .event(event.getName())
                .capacity(event.getLeftCapacity())
                .venue(event.getVenue())
                .ticketPrice(event.getTicketPrice())
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

    @Override
    public void updateEventCapacity(final Long eventId, final Long ticketsBooked) {
        final Event event = eventRepository.findById(eventId).orElse(null);
        event.setLeftCapacity(event.getLeftCapacity() - ticketsBooked);
        eventRepository.saveAndFlush(event);
        log.info("Updated event capacity for event id: {} with tickets booked: {}", eventId, ticketsBooked);
    }
}
