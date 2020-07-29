package io.github.bael.mscourse.orders.entity;

public enum OrderStatus {
    CREATED,
    CONFIRMED, // waiting for payment
    PAID,
    ASSEMBLING, // сборка заказа
    SHIPPED, // отгружен
    CANCELLED
}
