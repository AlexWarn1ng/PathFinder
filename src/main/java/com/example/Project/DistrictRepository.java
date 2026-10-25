package com.example.Project;
import jakarta.transaction.Transactional;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Integer> {

    @Query(value = " SELECT * FROM districts WHERE ST_Contains(poly, :point) LIMIT 1", nativeQuery = true)
    DistrictEntity findDistrictByPoint(@Param("point") Point point);
}