package com.example.Project;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
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

    List<Point> makeOrderListForCourier(Integer CourierId, Integer startOrderId){
        List<Point> Points = new ArrayList<>();
        OrderEntity startOrder = orderRepository.findById(startOrderId).orElseThrow();
        Points.add(startOrder.getCoords());
        List<OrderEntity> list = orderRepository.findOrdersByCourierExceptStart(CourierId, startOrderId);
        for(var listel : list){
            Points.add(listel.getCoords());
        }
        return Points;
    }

    String FindOptimalPath(Integer CourierId, Integer startOrderId){
        List<Point> Points = makeOrderListForCourier(CourierId, startOrderId);
        String requestString = "https://router.project-osrm.org/trip/v1/driving/";

        for(var Point : Points){
            requestString = requestString + Point.getX() + "," + Point.getY() + ";";
        }

        requestString = requestString.substring(0, requestString.length() - 1) + "?";
        requestString = requestString + "roundtrip=false&source=first&destination=last&geometries=geojson";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestString)).GET().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
         return response.body();
    }


}
