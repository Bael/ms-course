package io.github.bael.mscourse.orders.data;

import io.github.bael.mscourse.orders.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
