package com.example.FlowerShop.controller;

import com.example.FlowerShop.model.AppUser;
import com.example.FlowerShop.service.AppUserServiceImpl;
import com.example.FlowerShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginFormController {

    private final FormLoader formLoader;
    private final AppUserServiceImpl appUserServiceImpl;

    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;

    public LoginFormController(FormLoader formLoader, AppUserServiceImpl appUserServiceImpl) {
        this.formLoader = formLoader;
        this.appUserServiceImpl = appUserServiceImpl;
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void showRegistrationForm() {
        formLoader.loadRegistrationForm();
    }

    @FXML
    private void login() {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        Optional<AppUser> optionalUser = appUserServiceImpl.findByUsername(username);

        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            AppUserServiceImpl.currentUser = optionalUser.get();
            formLoader.loadMainForm();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка входа");
            alert.setHeaderText(null);
            alert.setContentText("Неверное имя пользователя или пароль!");
            alert.showAndWait();
        }
    }
}
