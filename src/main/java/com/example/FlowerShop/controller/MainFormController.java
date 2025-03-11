package com.example.FlowerShop.controller;

import com.example.FlowerShop.interfaces.OrderService;
import com.example.FlowerShop.model.Order;
import com.example.FlowerShop.tool.FormLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class MainFormController implements Initializable {

    private final OrderService orderService;
    private final FormLoader formLoader;

    @FXML private VBox menuContainer;
    @FXML private TableView<Order> tvOrderList;
    @FXML private TableColumn<Order, Long> tcId;
    @FXML private TableColumn<Order, String> tcCustomer;
    @FXML private TableColumn<Order, String> tcProducts;
    @FXML private TableColumn<Order, Double> tcTotalAmount;

    @Autowired
    public MainFormController(OrderService orderService, FormLoader formLoader) {
        this.orderService = orderService;
        this.formLoader = formLoader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMenu();
        setupTable();
        refreshOrders();
    }

    @FXML
    private void showNewOrderForm() {
        formLoader.loadNewOrderForm();
    }

    private void loadMenu() {
        try {
            FXMLLoader loader = formLoader.getSpringFXMLLoader().load("/menu/menuForm.fxml");
            VBox menu = loader.load();
            menuContainer.getChildren().add(menu);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке меню", e);
        }
    }


    private void setupTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCustomer.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getCustomer().getName()));
        tcProducts.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getProduct().getName()));
        tcTotalAmount.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createObjectBinding(() ->
                        cellData.getValue().getProduct().getPrice() * cellData.getValue().getQuantity()));
    }

    @FXML
    private void refreshOrders() {
        List<Order> orders = orderService.getAll();
        ObservableList<Order> observableList = FXCollections.observableArrayList(orders);
        tvOrderList.setItems(observableList);
    }
}
