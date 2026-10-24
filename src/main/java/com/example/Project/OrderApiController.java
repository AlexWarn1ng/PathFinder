package com.example.Project;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Character.getName;

@RestController
class OrderApiController {

    private final OrderRepository orderRepository;
    private final DistrictRepository districtRepository;
    private final OrderCreatingService ordercreatingservice;
    public OrderApiController(OrderRepository orderRepository, DistrictRepository districtRepository, OrderCreatingService ordercreatingservice) {
        this.orderRepository = orderRepository;
        this.districtRepository = districtRepository;
        this.ordercreatingservice = ordercreatingservice;
    }

    @GetMapping("/makeorder")
    public String getOrders(@RequestParam double lat, @RequestParam double lon) {
        Integer orderDistrict = ordercreatingservice.makeOrder(lat, lon);
        if (orderDistrict == null) {
            return "NotFound";
        }

    return orderDistrict.toString();
    }

}
