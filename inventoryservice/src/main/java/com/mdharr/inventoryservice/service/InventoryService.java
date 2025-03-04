package com.mdharr.inventoryservice.service;

import com.mdharr.inventoryservice.response.EventInventoryResponse;
import com.mdharr.inventoryservice.response.VenueInventoryResponse;

import java.util.List;

public interface InventoryService {

    List<EventInventoryResponse> getAllEvents();

    VenueInventoryResponse getVenueInformation(final Long venueId);

    EventInventoryResponse getEventInventory(Long eventId);

    void updateEventCapacity(final Long eventId, final Long ticketsBooked);
}
