package ru.strelchm.rabbitspring.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.strelchm.rabbitspring.dto.Order;

@RestController
@RequestMapping("/")
public class PublisherController {
    private final PublisherService service;

    @Autowired
    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @PostMapping("/{name}")
    public String bookOrder(@RequestBody Order order, @PathVariable String name) throws InterruptedException {
        return service.bookOrder(order, name);
    }
}
