package com.mdharr.inventoryservice.repositories;

import com.mdharr.inventoryservice.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
}
