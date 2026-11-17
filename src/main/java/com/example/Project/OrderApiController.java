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
    private final OrderDistributorService OrderDistributorService;
    private final CouriersRepository CouriersRepository;
    private final CourierOrderLinkRepository CourierOrderLinkRepository;
    public OrderApiController(CourierOrderLinkRepository CourierOrderLinkRepository, CouriersRepository CouriersRepository, OrderDistributorService OrderDistributorService, OptimalPathFinder OptimalPathFinder,OrderRepository orderRepository, DistrictRepository districtRepository, OrderCreatingService ordercreatingservice) {
        this.orderRepository = orderRepository;
        this.districtRepository = districtRepository;
        this.ordercreatingservice = ordercreatingservice;
        this.OptimalPathFinder = OptimalPathFinder;
        this.OrderDistributorService = OrderDistributorService;
        this.CouriersRepository = CouriersRepository;
        this.CourierOrderLinkRepository = CourierOrderLinkRepository;
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

    @PostMapping("/distribute")
    public ResponseEntity<String> testDistribute() {
        try {
            Integer res = OrderDistributorService.distributeOrders();
            return ResponseEntity.ok("Orders distributed successfully: " + res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getdistrict")
    public ResponseEntity<Integer> getDistrict(@RequestParam double lat, @RequestParam double lon) {

        GeometryFactory factory = new GeometryFactory();
        Point point = factory.createPoint(new Coordinate(lon, lat));
        point.setSRID(4326);
        DistrictEntity district = districtRepository.findDistrictByPoint(point);
        if (district == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(district.getId());
    }

    @GetMapping(value = "/getoptimalpath")
    public ResponseEntity<String> getOrders(@RequestParam Integer CourierId ,@RequestParam Integer startOrderId){
        try {
            String ans = OptimalPathFinder.FindOptimalPath(CourierId, startOrderId);
            return ResponseEntity.ok(ans.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @GetMapping(value = "/getcouriers")
    public ResponseEntity<List<CouriersEntity>> getCouriers() {
        return ResponseEntity.ok(CouriersRepository.findAll());
    }

    @GetMapping(value = "/getcourierorders")
    public ResponseEntity<List<Integer>> getCourierOrders(@RequestParam Integer courierId) {
        return ResponseEntity.ok(CourierOrderLinkRepository.findOrderIdsByCourierId(courierId));
    }

}
