package io.github.bael.mscourse.inventory.entity;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * резерв заказа
 */

@Entity
@Table(name = "order_shipment")
@Data
public class OrderShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "shipment_status")
    private OrderShipmentStatus orderShipmentStatus;

}
