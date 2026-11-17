package com.example.Project;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courier_orders")
public class CourierOrderLinkEntity {
    @Column(name = "courier_id")
    private Integer courierId;

    @Id
    @Column(name = "order_id")
    private Integer orderId;

    public CourierOrderLinkEntity() {}
}
