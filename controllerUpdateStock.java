package com.example.stockfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.function.Consumer;

public class controllerUpdateStock {

    @FXML
    private TextField labelField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField availableField;
    @FXML
    private TextField profitField;
    @FXML
    private Button Back;
    private Consumer<Stock> onUpdateStock;

    private Stock stockToUpdate;

    public void initData(Stock stock) {
        this.stockToUpdate = stock;
        labelField.setText(stock.getLabel());
        priceField.setText(Double.toString(stock.getCompanyInitialPrice()));
        availableField.setText(Integer.toString(stock.getNumOfAvailable()));
        profitField.setText(Double.toString(stock.getProfitPercentage()));
    }

    @FXML
    private void clickOnUpdate(ActionEvent event) {
        String label = labelField.getText();
        int available = Integer.parseInt(availableField.getText());
        double price = Double.parseDouble(priceField.getText());
        double profit = Double.parseDouble(profitField.getText());

        stockToUpdate.setLabel(label);
        stockToUpdate.setNumOfAvailable(available);
        stockToUpdate.setCompanyInitialPrice(price);
        stockToUpdate.setProfitPercentage(profit);

        if (onUpdateStock != null) {
            onUpdateStock.accept(stockToUpdate);
        }

        clickOnBack(event); // Navigate back after updating
    }

    public void setOnUpdateStock(Consumer<Stock> onUpdateStock) {
        this.onUpdateStock = onUpdateStock;
    }

    @FXML
    private void clickOnBack(ActionEvent event) {
        try {
            Register.loadScene(event, "stockManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
