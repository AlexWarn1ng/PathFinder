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

   /* List<Point> FindOptimalPath(Integer district){
        List<Point> Points = orderRepository.findPointsByDistrict(district);
        List<Point> ans = new ArrayList<>();
        Point currentPoint = Points.remove(0); ans.add(currentPoint);
        int newPointIndex = -1;
        double currentMinimum = 0;

        while (!Points.isEmpty()){
            currentMinimum = Double.MAX_VALUE;

            for(int i = 0; i < Points.size(); i++){
                double distance = Math.sqrt(Math.pow(currentPoint.getX() - Points.get(i).getX(), 2) + Math.pow(currentPoint.getY() - Points.get(i).getY(), 2));

                if (distance < currentMinimum) {
                    currentMinimum = distance;
                    newPointIndex = i;
                }
            }
            currentPoint = Points.remove(newPointIndex); ans.add(currentPoint);

        }
        double distance = 0;
        for(int i = 0; i < ans.size() - 1; i++){
            distance =  distance + (Math.sqrt(Math.pow(ans.get(i).getX() - ans.get(i+1).getX(), 2) + Math.pow(ans.get(i).getY() - ans.get(i+1).getY(), 2)));
        }
        System.out.println(distance);
        return ans;
    }*/

    String FindOptimalPath(Integer district, Integer startPointId){
        List<Point> Points = orderRepository.findPointsByDistrictAndId(district, startPointId);
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


/*
     List<Point> FindOptimalPath(Integer district){
         double distance = 0;
        List<Point> Points = orderRepository.findPointsByDistrict(district);
        Points.sort((Point point1,Point point2) -> {
                if(point1.getX() != point2.getX()){
                    return Double.compare(point1.getX(), point2.getX());
                }
            return Double.compare(point1.getY(), point2.getY());
        });


        for(int i = 0; i < Points.size() - 1; i++){
            distance =  distance + (Math.sqrt(Math.pow(Points.get(i).getX() - Points.get(i+1).getX(), 2) + Math.pow(Points.get(i).getY() - Points.get(i+1).getY(), 2)));
         }
        System.out.println(distance);
      return Points;
    }*/
}
