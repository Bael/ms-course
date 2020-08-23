package io.github.bael.mscourse.orders.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Заказ
 */
@Entity
@Table(name = "shop_order")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column
//    private UUID uid;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "total_amount")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "order_status_description", length = 500)
    private String orderStatusDescription;

    @Column(name = "delivery_address", length = 500)
    private String deliveryAddress;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

//    @Column(name = "plan_delivery_date")
//    private LocalDate planDeliveryDate;
//
//    @Column(name = "fact_delivery_date")
//    private LocalDate factDeliveryDate;

}
