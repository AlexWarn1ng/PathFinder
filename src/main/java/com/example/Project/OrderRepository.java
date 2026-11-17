package com.example.Project;

import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query(value = "SELECT o.coords FROM orders o JOIN districts d ON ST_Contains(d.poly, o.coords) WHERE d.id = :districtId ORDER BY CASE WHEN o.id = :startPointId THEN 0 ELSE 1 END", nativeQuery = true)
    List<Point> findPointsByDistrictAndId(@Param("districtId") Integer districtId, @Param("startPointId") Integer startPointId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO orders (district_id, coords, deadline, urgency, status) VALUES (:districtId, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), NOW() + :n * INTERVAL '1 hour', :urgency, 1)", nativeQuery = true)
    void insertOrder(@Param("districtId") Integer districtId, @Param("lon") double lon, @Param("lat") double lat,
                     @Param("n") Integer n, @Param("urgency") Integer urgency);

    @Query(value = "SELECT orders.id FROM orders LEFT JOIN courier_orders ON orders.id = courier_orders.order_id WHERE courier_orders.order_id IS NULL AND orders.district_id = :districtId", nativeQuery = true)
    List<Integer> findUnassignedOrders(@Param("districtId") Integer districtId);

    @Query(value = "SELECT DISTINCT orders.district_id FROM orders LEFT JOIN courier_orders ON orders.id = courier_orders.order_id WHERE courier_orders.order_id IS NULL", nativeQuery = true)
    List<Integer> findUnassignedDistricts();

    @Query(value = "SELECT orders.* FROM orders INNER JOIN courier_orders ON orders.id = courier_orders.order_id WHERE courier_orders.courier_id = :courierId AND orders.id != :startOrderId", nativeQuery = true)
    List<OrderEntity> findOrdersByCourierExceptStart(@Param("courierId") Integer courierId, @Param("startOrderId") Integer startOrderId);

    @Query(value = "SELECT order_id FROM courier_orders WHERE courier_id = :courierId", nativeQuery = true)
    List<Integer> findOrderIdsByCourierId(@Param("courierId") Integer courierId);
}












/*
INSERT INTO orders (district_id, coords, deadline, urgency, status)
VALUES (
    5,
    ST_SetSRID(ST_MakePoint(37.6173, 55.7558), 4326),
    NOW() + INTERVAL 'N hours',
    1,
    1
);
*/

/*
INSERT INTO orders (district_id, coords, deadline, urgency, status)
VALUES (
    5,
    ST_SetSRID(ST_MakePoint(37.6173, 55.7558), 4326),
    NOW() + INTERVAL 'N hours',
    1,
    1
);
*/

/*
create table orders (
 id serial primary key,
 district_id int,
 coords public.geometry('POINT', 4326),
 deadline timestamptz,
 urgency int,
 status int,
 created_at timestamptz default NOW()

)
 */

