package com.example.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class OrderApiController {

    private final OrderRepository repository;

    public OrderApiController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/order")
    public List<Order> getOrders() {
        return repository.findAll();
    }

}
