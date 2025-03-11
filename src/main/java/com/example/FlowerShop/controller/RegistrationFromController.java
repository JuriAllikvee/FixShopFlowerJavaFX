package com.example.FlowerShop.controller;

import com.example.FlowerShop.model.AppUser;
import com.example.FlowerShop.service.AppUserServiceImpl;
import com.example.FlowerShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFromController {

    private final AppUserServiceImpl appUserServiceImpl;
    private final FormLoader formLoader;

    @FXML private TextField tfFirstname;
    @FXML private TextField tfLastname;
    @FXML private TextField tfUsername;
    @FXML private TextField pfPassword;

    public RegistrationFromController(AppUserServiceImpl appUserServiceImpl, FormLoader formLoader) {
        this.appUserServiceImpl = appUserServiceImpl;
        this.formLoader = formLoader;
    }

    @FXML
    private void registerUser(){
        try {
            AppUser newUser = new AppUser();
            newUser.setFirstname(tfFirstname.getText());
            newUser.setLastname(tfLastname.getText());
            newUser.setUsername(tfUsername.getText());
            newUser.setPassword(pfPassword.getText());
            newUser.getRoles().add(AppUserServiceImpl.ROLES.USER.toString());

            appUserServiceImpl.add(newUser);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Пользователь зарегистрирован!");
            alert.showAndWait();

            formLoader.loadLoginForm();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Ошибка: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
