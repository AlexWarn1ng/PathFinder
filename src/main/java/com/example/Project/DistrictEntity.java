package com.example.Project;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Polygon;

@Entity
@Table(name = "districts")
public class DistrictEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Polygon poly;

    public DistrictEntity() {}

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return this.id;
    }
}