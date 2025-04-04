package com.example.FlowerShop.controller;

import com.example.FlowerShop.interfaces.AppUserService;
import com.example.FlowerShop.model.AppUser;
import com.example.FlowerShop.service.AppUserServiceImpl;
import com.example.FlowerShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFormController {

    private final AppUserService appUserService; // <-- интерфейс
    private final FormLoader formLoader;

    @FXML private TextField tfFirstname;
    @FXML private TextField tfLastname;
    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;

    public RegistrationFormController(AppUserService appUserService, FormLoader formLoader) {
        this.appUserService = appUserService;
        this.formLoader = formLoader;
    }

    @FXML
    private void registerUser() {
        AppUser newUser = new AppUser();
        newUser.setFirstname(tfFirstname.getText());
        newUser.setLastname(tfLastname.getText());
        newUser.setUsername(tfUsername.getText());
        newUser.setPassword(pfPassword.getText());
        newUser.getRoles().add(AppUserService.ROLES.USER.name());

        appUserService.create(newUser);

        // ...
    }
}
