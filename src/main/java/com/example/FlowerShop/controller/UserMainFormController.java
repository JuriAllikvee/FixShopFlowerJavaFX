package com.example.FlowerShop.controller;

import com.example.FlowerShop.interfaces.CustomerService;
import com.example.FlowerShop.interfaces.OrderService;
import com.example.FlowerShop.interfaces.ProductService;
import com.example.FlowerShop.model.Customer;
import com.example.FlowerShop.model.Product;
import com.example.FlowerShop.service.AppUserServiceImpl;
import com.example.FlowerShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class UserMainFormController implements Initializable {

    @FXML private Label lblBalance;
    @FXML private FlowPane fpCards;
    @FXML private Button btnRefresh;

    private final ProductService productService;
    private final OrderService orderService;
    private final AppUserServiceImpl appUserServiceImpl;
    private final CustomerService customerService;
    private final FormLoader formLoader;

    private Customer currentCustomer;

    public UserMainFormController(ProductService productService,
                                  OrderService orderService,
                                  AppUserServiceImpl appUserServiceImpl,
                                  CustomerService customerService,
                                  FormLoader formLoader) {
        this.productService = productService;
        this.orderService = orderService;
        this.appUserServiceImpl = appUserServiceImpl;
        this.customerService = customerService;
        this.formLoader = formLoader;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentCustomer = customerService.getCustomerByUser(appUserServiceImpl.getCurrentUser());
        updateBalanceLabel();

        btnRefresh.setOnAction(e -> refreshProducts());

        refreshProducts();
    }

    private void refreshProducts() {
        fpCards.getChildren().clear();
        List<Product> products = productService.getAll();
        for (Product product : products) {
            VBox card = createProductCard(product);
            fpCards.getChildren().add(card);
        }
        currentCustomer = customerService.getCustomerByUser(appUserServiceImpl.getCurrentUser());
        updateBalanceLabel();
    }


    private VBox createProductCard(Product product) {
        VBox card = new VBox(5);
        card.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label lblName = new Label(product.getName());
        lblName.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label lblCategory = new Label("Категория: " + product.getCategory());
        Label lblPrice = new Label("Цена: " + product.getPrice());
        Label lblQuantity = new Label("В наличии: " + product.getQuantity());

        Button btnBuy = new Button("Купить");
        btnBuy.setOnAction(e -> handleBuy(product));

        card.getChildren().addAll(lblName, lblCategory, lblPrice, lblQuantity, btnBuy);
        return card;
    }

    private void handleBuy(Product product) {
        try {
            FXMLLoader loader = formLoader.getSpringFXMLLoader().load("/fxml/ModalProductDetailsForm.fxml");
            Parent root = loader.load();

            ModalProductDetailsFormController controller = loader.getController();

            controller.setData(product, currentCustomer, orderService, () -> {
                refreshProducts();
            });

            Stage modalStage = new Stage();
            modalStage.setTitle("Детали товара");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(fpCards.getScene().getWindow());
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBalanceLabel() {
        if (currentCustomer != null) {
            lblBalance.setText("Баланс: " + currentCustomer.getBalance());
        } else {
            lblBalance.setText("Баланс: неизвестен");
        }
    }
}
