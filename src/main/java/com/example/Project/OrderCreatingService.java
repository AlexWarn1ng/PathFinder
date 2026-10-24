package com.example.Project;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class OrderCreatingService {
    private final OrderRepository orderRepository;
    private final DistrictRepository districtRepository;
    public OrderCreatingService(OrderRepository orderRepository, DistrictRepository districtRepository) {
        this.orderRepository = orderRepository;
        this.districtRepository = districtRepository;
    }

    Integer makeOrder( double lat, double lon) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        point.setSRID(4326);
        DistrictEntity orderDistrict = districtRepository.findDistrictByPoint(point);
        Integer userDistrictNumber = orderDistrict.getId();


        return userDistrictNumber;
    }

}
