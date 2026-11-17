package com.example.Project;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourierOrderLinkRepository extends JpaRepository<CourierOrderLinkEntity, Integer> {

    @Modifying
    @Query(value = "INSERT INTO courier_orders (courier_id, order_id) VALUES (:courier_id, :order_id)", nativeQuery = true)
    void insertCourierOrder(@Param("courier_id") Integer courier_id, @Param("order_id") Integer order_id);
}