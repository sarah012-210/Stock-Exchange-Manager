package com.example.stockfinal;

import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class controllerUpdateAddStock  {

    @FXML
    private TextField labelField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField availableField;
    @FXML
    private TextField profitField;

    @FXML
    private PasswordField Password;

    @FXML
    private Label errorMessage;

    @FXML
    private Button Back;
    @FXML
    private Button addButton;

    // Method to handle Add button click
    @FXML
    private void clickOnAdd(ActionEvent event) {
        // Get user input from text fields
        String label = labelField.getText();
        double price = Double.parseDouble(priceField.getText());
        int available = Integer.parseInt(availableField.getText());
        double profit = Double.parseDouble(profitField.getText());
        addStockToCSV(label , price , available , profit);

        // Validate user input (You can add your validation logic here)

        // If validation succeeds, you can proceed to add the user
        // For demonstration purposes, let's print the user details
        System.out.println("Label: " + label);
        System.out.println("Price: " + price);
        System.out.println("Available: " + available);
        System.out.println("Profit: " + profit);

        // Clear input fields after adding the user
        clearFields();
    }

    // Method to clear input fields
    private void clearFields() {
        labelField.clear();
        priceField.clear();
        availableField.clear();
        profitField.clear();
    }

    // Method to handle Back button click
    public void clickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "stockManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to add a new user to the CSV file
    public void addStockToCSV(String label, double price, int available, double profit) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("src/main/resources/com/example/stockfinal/stock.csv", true))) {
            String[] record = {label, String.valueOf(price), String.valueOf(available), String.valueOf(profit)};
            csvWriter.writeNext(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to refresh the table view
//    public void refreshTableView() {
//        usersTableView.setItems(users);
//    }
}
