package com.mdharr.orderservice.service;

import com.mdharr.bookingservice.event.BookingEvent;
import com.mdharr.orderservice.entity.Order;

public interface OrderService {

    void orderEvent(BookingEvent bookingEvent);
}
