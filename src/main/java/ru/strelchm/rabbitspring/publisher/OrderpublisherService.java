package ru.strelchm.rabbitspring.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.strelchm.rabbitspring.Order;
import ru.strelchm.rabbitspring.OrderStatus;

import java.util.UUID;

import static ru.strelchm.rabbitspring.MessagingConfig.QUEUE_BINDING_KEY;
import static ru.strelchm.rabbitspring.MessagingConfig.QUEUE_EXCHANGE_NAME;

@Service
public class OrderpublisherService {
    private final RabbitTemplate template;

    @Autowired
    public OrderpublisherService(RabbitTemplate template) {
        this.template = template;
        this.template.setChannelTransacted(true);
    }

    @Transactional
    public String bookOrder(Order order, String restaurantName) throws InterruptedException {
        order.setId(UUID.randomUUID());
        OrderStatus orderStatus = new OrderStatus(order, "PROGRESS", "order placed successfully in restaurant " + restaurantName);
        template.convertAndSend(QUEUE_EXCHANGE_NAME, QUEUE_BINDING_KEY, orderStatus);
        Thread.sleep(30000L);
        return "success";
    }
}
