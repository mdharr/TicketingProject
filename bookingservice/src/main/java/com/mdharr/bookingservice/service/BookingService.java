package com.mdharr.bookingservice.service;

import com.mdharr.bookingservice.request.BookingRequest;
import com.mdharr.bookingservice.response.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(final BookingRequest request);
}
