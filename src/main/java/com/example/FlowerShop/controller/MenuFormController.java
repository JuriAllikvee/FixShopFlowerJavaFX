package com.example.FlowerShop.controller;

import com.example.FlowerShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final FormLoader formLoader;

    @FXML private Menu mProducts;
    @FXML private Menu mOrders;
    @FXML private Menu mAdmin;
    @FXML private Menu mCustomers;

    @FXML private MenuItem miAddProduct;
    @FXML private MenuItem miProductList;
    @FXML private MenuItem miAddOrder;
    @FXML private MenuItem miAddCustomer;

    public MenuFormController(FormLoader formLoader) {
        this.formLoader = formLoader;
    }

    @FXML private void showNewProductForm() {
        formLoader.loadNewProductForm();
    }

    @FXML private void showProductListForm() {
        formLoader.loadProductListForm();
    }

    @FXML
    private void showNewOrderForm() {
        formLoader.loadNewOrderForm();
    }


    @FXML private void showNewCustomerForm() {
        formLoader.loadNewCustomerForm();
    }

    @FXML private void logout() {
        formLoader.loadLoginForm();
    }
    @FXML private void showCustomerListForm() {
        formLoader.loadCustomerListForm();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureMenuByRole();
    }

    private void configureMenuByRole() {
        boolean isAdmin = true;
        boolean isManager = false;

        mAdmin.setVisible(isAdmin);
        mCustomers.setVisible(isAdmin || isManager);
        miAddCustomer.setVisible(isAdmin || isManager);
        miAddOrder.setVisible(isAdmin || isManager);
    }
}
