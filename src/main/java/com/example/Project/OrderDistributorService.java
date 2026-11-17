package com.example.Project;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDistributorService {
    private final OrderRepository orderRepository;
    private final DistrictRepository districtRepository;
    private final CouriersRepository couriersrepository;
    private final CourierOrderLinkRepository courierorderlinkrepository;
    public record CourierOrderLink(Integer courierId, Integer orderId) {}

    public OrderDistributorService(CourierOrderLinkRepository courierorderlinkrepository, OrderRepository orderRepository, DistrictRepository districtRepository, CouriersRepository couriersrepository) {
        this.orderRepository = orderRepository;
        this.districtRepository = districtRepository;
        this.couriersrepository = couriersrepository;
        this.courierorderlinkrepository = courierorderlinkrepository;

    }

    @Transactional
    Integer distributeOrders() {
        Integer i = 0, insertedRows = 0;
        List<CourierOrderLink> finalLinks = new ArrayList<>();
        List<Integer> unassignedDistricts = orderRepository.findUnassignedDistricts();

        for (var currDisrtict : unassignedDistricts) {
            i = 0;
            List<Integer> unassignedOrders = orderRepository.findUnassignedOrders(currDisrtict);
            List<Integer> couriers = couriersrepository.findCourierIdsByDistrict(currDisrtict);
            if (unassignedOrders.isEmpty() || couriers.isEmpty()) {
                continue;
            }
            for (var orderId : unassignedOrders) {
                Integer cour = couriers.get(i);
                finalLinks.add(new CourierOrderLink(cour, orderId));
                i = (i + 1) % couriers.size();
            }
        }
        for(var link : finalLinks) {
              courierorderlinkrepository.insertCourierOrder(link.courierId(), link.orderId());
              insertedRows++;
        }
        return insertedRows;
    }
}
