package com.example.FlowerShop.repository;

import com.example.FlowerShop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Предполагаем, что в Customer поле email используется для сопоставления с AppUser.getUsername()
    Optional<Customer> findByEmail(String email);
}
