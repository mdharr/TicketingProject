package com.mdharr.bookingservice.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VenueResponse {

    private Long id;
    private String name;
    private String address;
    private Long totalCapacity;
}
