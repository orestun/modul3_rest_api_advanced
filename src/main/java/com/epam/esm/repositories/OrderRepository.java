package com.epam.esm.repositories;

import com.epam.esm.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author orest uzhytchak
 * A repository layer for Order
 * */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserID(Long userID, Pageable pageable);

}
