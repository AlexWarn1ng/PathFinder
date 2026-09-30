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
    public OrderApiController(OrderRepository orderRepository, DistrictRepository districtRepository) {
        this.orderRepository = orderRepository;
        this.districtRepository = districtRepository;
    }

    @GetMapping("/order")
    public String getOrders(@RequestParam double lat, @RequestParam double lon) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        point.setSRID(4326);
        DistrictEntity orderDistrict = districtRepository.findDistrictByPoint(point);
        if (orderDistrict == null) {
            return "NotFound";
        }

    return orderDistrict.getName() + " " + orderDistrict.getId();
    }

}
