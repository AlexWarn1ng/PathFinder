package com.example.Project;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO orders (district_id, coords, deadline, urgency, status)VALUES (:districtId,ST_SetSRID(ST_MakePoint(:lon, :lat), 4326),NOW() + :n * INTERVAL '1 hour',:urgency,1)", nativeQuery = true)
    void insertOrder(
            @Param("districtId") Integer districtId,
            @Param("lon") double lon,
            @Param("lat") double lat,
            @Param("n") Integer n,
            @Param("urgency") Integer urgency
    );

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
}
