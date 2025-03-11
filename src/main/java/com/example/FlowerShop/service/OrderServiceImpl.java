package com.example.FlowerShop.service;

import com.example.FlowerShop.interfaces.OrderService;
import com.example.FlowerShop.model.Customer;
import com.example.FlowerShop.model.Order;
import com.example.FlowerShop.model.Product;
import com.example.FlowerShop.repository.CustomerRepository;
import com.example.FlowerShop.repository.OrderRepository;
import com.example.FlowerShop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order create(Order order) {
        Customer customer = order.getCustomer();
        Product product = order.getProduct();
        int quantity = order.getQuantity();
        double totalCost = product.getPrice() * quantity;


        // Создаем заказ
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
