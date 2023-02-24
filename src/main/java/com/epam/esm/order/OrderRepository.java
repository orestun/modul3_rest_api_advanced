package com.epam.esm.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author orest uzhytchak
 * A repository layer for Order
 * */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
