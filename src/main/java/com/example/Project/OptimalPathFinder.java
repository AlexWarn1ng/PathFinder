package com.example.Project;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptimalPathFinder {
    private final OrderRepository orderRepository;
    private final DistrictRepository districtRepository;
    private final OrderCreatingService ordercreatingservice;
    public OptimalPathFinder(OrderRepository orderRepository, DistrictRepository districtRepository, OrderCreatingService ordercreatingservice) {
        this.orderRepository = orderRepository;
        this.districtRepository = districtRepository;
        this.ordercreatingservice = ordercreatingservice;
    }


     List<Point> FindOptimalPath(Integer district){
        List<Point> Points = orderRepository.findPointsByDistrict(district);
        Points.sort((Point point1,Point point2) -> {
                if(point1.getX() != point2.getX()){
                    return Double.compare(point1.getX(), point2.getX());
                }
            return Double.compare(point1.getY(), point2.getY());
        });
      return Points;
    }
}
