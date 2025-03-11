package com.example.FlowerShop;

import com.example.FlowerShop.tool.FormLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FlowerShopApplication extends Application {

	public static ConfigurableApplicationContext applicationContext;
	public static Stage primaryStage;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(FlowerShopApplication.class, args);
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		FlowerShopApplication.primaryStage = stage;
		FormLoader formService = applicationContext.getBean(FormLoader.class);
		formService.loadLoginForm();
	}

	@Override
	public void stop() {
		applicationContext.close();
	}
}
