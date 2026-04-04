package com.company.teaPost.repositories;

import com.company.teaPost.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
