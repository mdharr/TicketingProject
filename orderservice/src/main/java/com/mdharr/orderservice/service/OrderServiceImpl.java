package com.mdharr.orderservice.service;

import com.mdharr.bookingservice.event.BookingEvent;
import com.mdharr.orderservice.client.InventoryServiceClient;
import com.mdharr.orderservice.entity.Order;
import com.mdharr.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private InventoryServiceClient inventoryServiceClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            InventoryServiceClient inventoryServiceClient) {
        this.orderRepository = orderRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @KafkaListener(topics = "booking", groupId = "order-service")
    public void orderEvent(BookingEvent bookingEvent) {
        log.info("Received order event: {}", bookingEvent);

        // Create Order object for DB
        Order order = createOrder(bookingEvent);
        orderRepository.saveAndFlush(order);

        // Update Inventory
        inventoryServiceClient.updateInventory(order.getEventId(), order.getTicketCount());
        log.info("Inventory updated for event: {}, less tickets: {}", order.getEventId(), order.getTicketCount());
    }

    private Order createOrder(BookingEvent bookingEvent) {
        return Order.builder()
                .customerId(bookingEvent.getUserId())
                .eventId(bookingEvent.getEventId())
                .ticketCount(bookingEvent.getTicketCount())
                .totalPrice(bookingEvent.getTotalPrice())
                .build();
    }
}
