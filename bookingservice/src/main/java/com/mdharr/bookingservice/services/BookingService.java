package com.mdharr.bookingservice.services;

import com.mdharr.bookingservice.requests.BookingRequest;
import com.mdharr.bookingservice.responses.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(final BookingRequest request);
}
