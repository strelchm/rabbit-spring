package ru.strelchm.rabbitspring.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strelchm.rabbitspring.dto.Order;
import ru.strelchm.rabbitspring.dto.OrderStatus;

import java.util.UUID;
import java.util.logging.Logger;

import static ru.strelchm.rabbitspring.config.MessagingConfig.QUEUE_BINDING_KEY;
import static ru.strelchm.rabbitspring.config.MessagingConfig.QUEUE_EXCHANGE_NAME;

@Service
public class PublisherService {
    private final RabbitTemplate template;

    @Autowired
    public PublisherService(RabbitTemplate template) {
        this.template = template;
        this.template.setChannelTransacted(true);
    }

    @Transactional
    public String bookOrder(Order order, String restaurantName) throws InterruptedException {
        order.setId(UUID.randomUUID());
        OrderStatus orderStatus = new OrderStatus(order, "PROGRESS", "order placed successfully in restaurant " + restaurantName);
        try {
            template.convertAndSend(QUEUE_EXCHANGE_NAME, QUEUE_BINDING_KEY, orderStatus);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        Logger.getLogger(PublisherService.class.getName()).warning("start");
        Thread.sleep(1000L);
        Logger.getLogger(PublisherService.class.getName()).warning("finish");
//        boolean v = true;
//        if (v) {
//            throw new RuntimeException();
//        }
        return "success";
    }
}
