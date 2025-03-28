package com.example.FlowerShop.controller;

import com.example.FlowerShop.model.AppUser;
import com.example.FlowerShop.service.AppUserServiceImpl;
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
    private final AppUserServiceImpl appUserServiceImpl;

    @FXML private Menu mProducts;
    @FXML private Menu mOrders;
    @FXML private Menu mAdmin;
    @FXML private Menu mCustomers;

    @FXML private MenuItem miAddProduct;
    @FXML private MenuItem miProductList;
    @FXML private MenuItem miAddOrder;
    @FXML private MenuItem miAddCustomer;

    public MenuFormController(FormLoader formLoader, AppUserServiceImpl appUserServiceImpl) {
        this.formLoader = formLoader;
        this.appUserServiceImpl = appUserServiceImpl;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureMenuByRole();
    }

    private void configureMenuByRole() {
        // Получаем текущего пользователя
        AppUser currentUser = appUserServiceImpl.getCurrentUser();

        // Если пользователь не авторизован — скрываем всё, кроме, возможно, базовых пунктов
        if (currentUser == null) {
            mAdmin.setVisible(false);
            mCustomers.setVisible(false);
            miAddCustomer.setVisible(false);
            miAddOrder.setVisible(false);
            return;
        }

        // Проверяем роли. Предполагаем, что в БД у пользователя лежит, например, "ADMINISTRATOR"
        // (или "MANAGER", "USER") в поле roles.
        boolean isAdmin = currentUser.getRoles().contains(AppUserServiceImpl.ROLES.ADMINISTRATOR.name());
        boolean isManager = currentUser.getRoles().contains(AppUserServiceImpl.ROLES.MANAGER.name());

        // Меню "Администрирование" видно только администратору
        mAdmin.setVisible(isAdmin);

        // Покупатели и создание заказов — для администратора и менеджера
        mCustomers.setVisible(isAdmin || isManager);
        miAddCustomer.setVisible(isAdmin || isManager);
        miAddOrder.setVisible(isAdmin || isManager);

        // Если хотите показать что-то только пользователю (USER), проверьте:
        // boolean isUser = currentUser.getRoles().contains(AppUserServiceImpl.ROLES.USER.name());
        // ...
    }

    // Прочие методы:
    @FXML
    private void showNewProductForm() {
        formLoader.loadNewProductForm();
    }

    @FXML
    private void showProductListForm() {
        formLoader.loadProductListForm();
    }

    @FXML
    private void showNewOrderForm() {
        formLoader.loadNewOrderForm();
    }

    @FXML
    private void showNewCustomerForm() {
        formLoader.loadNewCustomerForm();
    }

    @FXML
    private void logout() {
        formLoader.loadLoginForm();
    }

    @FXML
    private void showCustomerListForm() {
        formLoader.loadCustomerListForm();
    }
}
