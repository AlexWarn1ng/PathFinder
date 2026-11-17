package com.example.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouriersRepository extends JpaRepository<CouriersEntity, Integer> {
    @Query(value = "SELECT couriers.id FROM couriers JOIN districts ON couriers.workdistrict = districts.name WHERE districts.id = :districtId", nativeQuery = true)
    List<Integer> findCourierIdsByDistrict(@Param("districtId") Integer districtId);
}
