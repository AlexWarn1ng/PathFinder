package com.example.Project;

import org.locationtech.jts.geom.Point;
import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point coords;

    private ZonedDateTime deadline;
    private Integer urgency;
    private Integer status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "district_id")
    private Integer districtId;
    public OrderEntity() {
    }

    public OrderEntity(Integer districtId, Point coords, ZonedDateTime deadline, Integer urgency, Integer status) {
        this.coords = coords;
        this.deadline = deadline;
        this.urgency = urgency;
        this.status = status;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer id) {
        this.districtId = districtId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
