package ru.strelchm.rabbitspring.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.strelchm.rabbitspring.config.MessagingConfig;
import ru.strelchm.rabbitspring.dto.OrderStatus;

@Component
public class Consumer {
    @RabbitListener(queues = MessagingConfig.QUEUE_NAME) // , concurrency = "1-1", exclusive = true
    public void consumeMessageFromQueue(OrderStatus orderStatus) throws InterruptedException {
        System.out.println("Message is " + orderStatus);
        Thread.sleep(5000);
        System.out.println("Message processed: " + orderStatus + "(" + Thread.currentThread().getName() + ")");
    }
}
