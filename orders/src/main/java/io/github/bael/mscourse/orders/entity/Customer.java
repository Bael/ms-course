package io.github.bael.mscourse.orders.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Column
    private UUID uid;

    @Column(name = "full_name")
    private String fullName;
    
}
