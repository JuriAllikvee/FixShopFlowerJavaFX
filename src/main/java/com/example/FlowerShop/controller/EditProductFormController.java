package com.example.FlowerShop.controller;

import com.example.FlowerShop.interfaces.ProductService;
import com.example.FlowerShop.model.Product;

import com.example.FlowerShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class EditProductFormController {

    private final ProductService productService;
    private final FormLoader formLoader;
    private Product selectedProduct;

    @FXML private TextField tfName;
    @FXML private TextField tfCategory;
    @FXML private TextField tfPrice;
    @FXML private TextField tfQuantity;

    public EditProductFormController(ProductService productService, FormLoader formLoader) {
        this.productService = productService;
        this.formLoader = formLoader;
    }

    public void setProduct(Product product) {
        this.selectedProduct = product;
        tfName.setText(product.getName());
        tfCategory.setText(product.getCategory());
        tfPrice.setText(String.valueOf(product.getPrice()));
    }

    @FXML
    private void saveProduct() {
        try {
            selectedProduct.setName(tfName.getText().trim());
            selectedProduct.setCategory(tfCategory.getText().trim());
            selectedProduct.setPrice(Double.parseDouble(tfPrice.getText().trim()));
            productService.updateProduct(selectedProduct.getId(), selectedProduct);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Товар обновлён");
            alert.setContentText("Товар \"" + selectedProduct.getName() + "\" успешно обновлён.");
            alert.showAndWait();

            formLoader.loadProductListForm();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка при редактировании товара");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel() {
        formLoader.loadProductListForm();
    }
}
