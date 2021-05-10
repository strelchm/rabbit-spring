package ru.strelchm.rabbitspring.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.strelchm.rabbitspring.MessagingConfig;
import ru.strelchm.rabbitspring.OrderStatus;

@Component
public class User {
    @RabbitListener(queues = MessagingConfig.QUEUE_NAME)
    public void consumeMessageFromQueue(OrderStatus orderStatus) {
        System.out.println("Message is " + orderStatus);
    }
}
