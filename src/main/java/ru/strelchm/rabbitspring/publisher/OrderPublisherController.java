package ru.strelchm.rabbitspring.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.rabbitspring.Order;
import ru.strelchm.rabbitspring.OrderStatus;

import java.util.UUID;

import static ru.strelchm.rabbitspring.MessagingConfig.*;

@RestController
@RequestMapping("/order")
public class OrderPublisherController {
    private final OrderpublisherService service;

    @Autowired
    public OrderPublisherController(OrderpublisherService service) {
        this.service = service;
    }

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName) throws InterruptedException {
        return service.bookOrder(order, restaurantName);
    }
}
