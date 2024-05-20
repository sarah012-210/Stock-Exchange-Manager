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

public class controllerRetriveUser {

    @FXML
    private Label nameLabel;

    @FXML
    private Label passwordLabel;

    private User user; // Assuming you have a User class

    // Method to initialize user details
    public void initData(User user) {
        this.user = user;
        String username = user.getUserName();
        String password = user.getPassword();
        nameLabel.setText(username);
        passwordLabel.setText(password);
    }

    // Method to handle back button click
    @FXML
    public void goBack(ActionEvent event) {
        try {
            Register.loadScene(event, "userManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
