package com.example.stockfinal;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class controllerRetriveStock {

    @FXML
    private Label stockLabel;

    @FXML
    private Label priceLabel;
    @FXML
    private Label availableLabel;

    @FXML
    private Label profitLabel;
    private Stock stock; // Assuming you have a User class

    // Method to initialize user details
    public void initData(Stock stock) {
        this.stock = stock;
        String label = stock.getLabel();
        double price = stock.getCompanyInitialPrice();
        int available = stock.getNumOfAvailable();
        double profit = stock.getProfitPercentage();
        stockLabel.setText(label);
        priceLabel.setText(String.valueOf(price));
        availableLabel.setText(String.valueOf(available));
        profitLabel.setText(String.valueOf(profit));
    }

    // Method to handle back button click
    @FXML
    public void goBack(ActionEvent event) {
        try {
            Register.loadScene(event, "stockManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
