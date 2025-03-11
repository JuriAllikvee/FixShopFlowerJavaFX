package com.example.FlowerShop.repository;

import com.example.FlowerShop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
