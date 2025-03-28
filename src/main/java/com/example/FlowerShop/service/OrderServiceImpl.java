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

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order create(Order order) {
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

    /**
     * Создает заказ, если:
     * - Количество товара достаточно
     * - У покупателя достаточно средств для покупки
     * Если заказ успешно создан, баланс покупателя уменьшается,
     * количество товара обновляется и возвращается true.
     * Иначе возвращается false.
     */
    @Override
    public boolean createOrder(Customer customer, Product product, int quantity) {
        // Проверяем, достаточно ли товара для заказа
        if (product.getQuantity() < quantity) {
            return false;
        }
        double totalCost = product.getPrice() * quantity;
        // Проверяем, достаточно ли средств у покупателя
        if (customer.getBalance() < totalCost) {
            return false;
        }
        // Создаем заказ
        Order order = new Order(customer, product, quantity);
        // Уменьшаем количество товара на складе
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        // Уменьшаем баланс покупателя
        customer.setBalance(customer.getBalance() - totalCost);
        customerRepository.save(customer);
        // Сохраняем заказ
        orderRepository.save(order);
        return true;
    }
}
