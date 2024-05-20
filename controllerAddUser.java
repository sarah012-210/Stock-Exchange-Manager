package com.example.stockfinal;

import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;

public class controllerAddUser {

    @FXML
    private TextField FirstName;

    @FXML
    private TextField SecondName;

    @FXML
    private TextField UserName;

    @FXML
    private PasswordField Password;

    @FXML
    private Label errorMessage;

    @FXML
    private Button Back;

    // Method to handle Add button click
    @FXML
    private void clickOnAdd(ActionEvent event) {
        // Get user input from text fields
        String firstName = FirstName.getText();
        String secondName = SecondName.getText();
        String userName = UserName.getText();
        String password = Password.getText();
        addUserToCSV(userName , password , "User");

        // Validate user input (You can add your validation logic here)

        // If validation succeeds, you can proceed to add the user
        // For demonstration purposes, let's print the user details
        System.out.println("First Name: " + firstName);
        System.out.println("Second Name: " + secondName);
        System.out.println("Username: " + userName);
        System.out.println("Password: " + password);

        // Clear input fields after adding the user
        clearFields();
    }

    // Method to clear input fields
    private void clearFields() {
        FirstName.clear();
        SecondName.clear();
        UserName.clear();
        Password.clear();
    }

    // Method to handle Back button click
    public void clickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "userManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Method to add a new user to the CSV file
    public void addUserToCSV(String name, String password, String type) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("src/main/resources/com/example/stockfinal/users.csv", true))) {
            String[] record = {name, password, type};
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
