package com.example.Project;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class OrderCreatingService {
    private final Integer DefaultTimeForDelievery = 12;
    private final OrderRepository orderRepository;
    private final DistrictRepository districtRepository;
    public OrderCreatingService(OrderRepository orderRepository, DistrictRepository districtRepository) {
        this.orderRepository = orderRepository;
        this.districtRepository = districtRepository;
    }

    Integer makeOrder( double lat, double lon, Integer urgency) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        point.setSRID(4326);
        DistrictEntity orderDistrict = districtRepository.findDistrictByPoint(point);
        if(orderDistrict == null){
            throw new RuntimeException("Point out of districts");
        }
        if(urgency < 1 || urgency > 4){
            throw new RuntimeException("Urgency out of bound");
        }
        Integer userDistrictNumber = orderDistrict.getId();
            orderRepository.insertOrder(userDistrictNumber, lon, lat, DefaultTimeForDelievery, urgency);

        return userDistrictNumber;
    }

    Integer makeOrder(double lat, double lon, Integer urgency, Integer TimeForDelievery) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        point.setSRID(4326);
        DistrictEntity orderDistrict = districtRepository.findDistrictByPoint(point);
        if(orderDistrict == null){
            throw new RuntimeException("Point out of districts");
        }
        if(urgency < 1 || urgency > 4){
            throw new RuntimeException("Urgency out of bound");
        }
        Integer userDistrictNumber = orderDistrict.getId();
            orderRepository.insertOrder(userDistrictNumber, lon, lat, TimeForDelievery, urgency);

        return userDistrictNumber;
    }

}
