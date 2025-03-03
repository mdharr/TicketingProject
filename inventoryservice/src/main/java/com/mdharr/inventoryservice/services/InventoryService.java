package com.mdharr.inventoryservice.services;

import com.mdharr.inventoryservice.responses.EventInventoryResponse;
import com.mdharr.inventoryservice.responses.VenueInventoryResponse;

import java.util.List;

public interface InventoryService {

    List<EventInventoryResponse> getAllEvents();

    VenueInventoryResponse getVenueInformation(final Long venueId);

    EventInventoryResponse getEventInventory(Long eventId);
}
