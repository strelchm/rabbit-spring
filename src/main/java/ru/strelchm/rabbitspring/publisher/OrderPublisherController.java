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

    private final RabbitTemplate template;

    @Autowired
    public OrderPublisherController(RabbitTemplate template) {
        this.template = template;
    }

    @PostMapping("/{restaurantName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String restaurantName) {
        order.setId(UUID.randomUUID());
        OrderStatus orderStatus = new OrderStatus(order, "PROGRESS", "order placed successfully in restaurant " + restaurantName);
        template.convertAndSend(QUEUE_EXCHANGE_NAME, QUEUE_BINDING_KEY, orderStatus);
        return "SUCCESS";
    }
}
