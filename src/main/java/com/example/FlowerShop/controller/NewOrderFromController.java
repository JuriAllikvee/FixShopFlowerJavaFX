package com.example.FlowerShop.controller;

import com.example.FlowerShop.interfaces.CustomerService;
import com.example.FlowerShop.interfaces.OrderService;
import com.example.FlowerShop.interfaces.ProductService;
import com.example.FlowerShop.model.Customer;
import com.example.FlowerShop.model.Order;
import com.example.FlowerShop.model.Product;
import com.example.FlowerShop.tool.FormLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewOrderFromController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final FormLoader formLoader;

    @FXML private ComboBox<Customer> cbCustomers;
    @FXML private ComboBox<Product> cbProducts;
    @FXML private TextField tfQuantity;

    public NewOrderFromController(OrderService orderService, CustomerService customerService,
                                  ProductService productService, FormLoader formLoader) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
        this.formLoader = formLoader;
    }

    @FXML
    private void initialize() {
        List<Customer> customers = customerService.getAll();
        cbCustomers.setItems(FXCollections.observableArrayList(customers));

        List<Product> products = productService.getAll();
        cbProducts.setItems(FXCollections.observableArrayList(products));

        cbProducts.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                setText(empty || product == null ? "" : product.getName());
            }
        });

        cbProducts.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                setText(empty || product == null ? "" : product.getName());
            }
        });
    }
    @FXML
    private void goBackToMainForm() {
        formLoader.loadMainForm();
    }



    @FXML
    private void createOrder() {
        Customer selectedCustomer = cbCustomers.getValue();
        Product selectedProduct = cbProducts.getValue();
        String quantityText = tfQuantity.getText();

        if (selectedCustomer == null || selectedProduct == null || quantityText.isEmpty()) {
            showErrorAlert("Ошибка", "Выберите покупателя, продукт и введите количество.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showErrorAlert("Ошибка", "Количество должно быть положительным числом.");
            return;
        }

        Order newOrder = new Order(selectedCustomer, selectedProduct, quantity);
        orderService.create(newOrder);

        showSuccessAlert("Заказ создан", "Заказ успешно добавлен.");

        formLoader.loadMainForm();
    }


    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
