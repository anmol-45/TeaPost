package com.company.teaPost.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String addressId;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String pinCode;
}
