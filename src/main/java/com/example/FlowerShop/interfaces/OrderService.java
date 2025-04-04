package com.example.FlowerShop.interfaces;

import com.example.FlowerShop.model.Customer;
import com.example.FlowerShop.model.Order;
import com.example.FlowerShop.model.Product;
import com.example.FlowerShop.service.AppService;
import java.time.LocalDate;

public interface OrderService extends AppService<Order> {

    boolean createOrder(Customer customer, Product product, int quantity);
    double calculateRevenueBetween(LocalDate startDate, LocalDate endDate);
}
