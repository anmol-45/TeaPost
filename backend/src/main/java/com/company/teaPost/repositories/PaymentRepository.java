package com.company.teaPost.repositories;

import com.company.teaPost.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String>{
    Optional<Payment> findTopByOrderIdOrderByCreatedAtDesc(String orderId);

}

