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

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    public LoginFormController(FormLoader formLoader, AppUserServiceImpl appUserServiceImpl) {
        this.formLoader = formLoader;
        this.appUserServiceImpl = appUserServiceImpl;
    }

    @FXML
    private void initialize() {
        // Если нужно, можете добавить логику инициализации полей
    }

    @FXML
    private void showRegistrationForm() {
        formLoader.loadRegistrationForm();
    }

    @FXML
    private void login() {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        // Ищем пользователя по имени
        Optional<AppUser> optionalUser = appUserServiceImpl.findByUsername(username);

        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            // Проверяем пароль
            if (user.getPassword().equals(password)) {
                // Сохраняем текущего пользователя в статическом поле
                AppUserServiceImpl.currentUser = user;

                // Проверяем, есть ли у пользователя роль "ADMINISTRATOR" (из enum)
                // или, если у вас в базе хранится "ADMIN", меняем проверку на "ADMIN"
                boolean isAdmin = user.getRoles().contains(AppUserServiceImpl.ROLES.ADMINISTRATOR.name());
                // Или:
                // boolean isAdmin = user.getRoles().contains("ADMIN");

                // Если админ — грузим главное окно, иначе — окно пользователя
                if (isAdmin) {
                    formLoader.loadMainForm();
                } else {
                    // Окно для обычного пользователя (нужно добавить метод loadUserMainForm)
                    formLoader.loadUserMainForm();
                }
            } else {
                showErrorAlert("Неверное имя пользователя или пароль!");
            }
        } else {
            showErrorAlert("Неверное имя пользователя или пароль!");
        }
    }

    /**
     * Выводит Alert с ошибкой
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка входа");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
