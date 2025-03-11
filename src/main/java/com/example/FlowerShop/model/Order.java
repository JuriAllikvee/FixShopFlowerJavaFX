package com.example.FlowerShop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_order") // ✅ Изменено имя таблицы, чтобы избежать ошибки SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    public Order() {}

    public Order(Customer customer, Product product, int quantity) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public void setId(Long id) { this.id = id; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer.getName() + // Упрощенный вывод
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                '}';
    }
}
