package com.example.Project;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Character.getName;

@RestController
class OrderApiController {

    private final OrderRepository orderRepository;
    private final DistrictRepository districtRepository;
    private final OrderCreatingService ordercreatingservice;
    private final OptimalPathFinder OptimalPathFinder;
    public OrderApiController(OptimalPathFinder OptimalPathFinder,OrderRepository orderRepository, DistrictRepository districtRepository, OrderCreatingService ordercreatingservice) {
        this.orderRepository = orderRepository;
        this.districtRepository = districtRepository;
        this.ordercreatingservice = ordercreatingservice;
        this.OptimalPathFinder = OptimalPathFinder;
    }

    @PostMapping(value = "/makeorder", params = "!timeForDelivery")
    public ResponseEntity<String> getOrders(@RequestParam double lat, @RequestParam double lon, @RequestParam Integer urgency) {
        try {
            Integer orderDistrict = ordercreatingservice.makeOrder(lat, lon, urgency);
            return ResponseEntity.ok(orderDistrict.toString());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/makeorder", params = "timeForDelivery")
    public ResponseEntity<String> getOrders(@RequestParam double lat, @RequestParam double lon, @RequestParam Integer urgency, @RequestParam Integer timeForDelivery) {
        try {
            Integer orderDistrict = ordercreatingservice.makeOrder(lat, lon, urgency, timeForDelivery);
            return ResponseEntity.ok(orderDistrict.toString());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping(value = "/getoptimalpath")
    public ResponseEntity<String> getOrders(@RequestParam Integer districtId,@RequestParam Integer startPointId){
        try {
            String ans = OptimalPathFinder.FindOptimalPath(districtId, startPointId);
            return ResponseEntity.ok(ans.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

}
