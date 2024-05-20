package com.example.stockfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController  {

    @FXML
    private Button notificationButton;


    public void initialize(ResourceBundle resources) {
        notificationButton.setOnAction(this::showNotifications);
    }

    @FXML
    private void showNotifications(ActionEvent event) {
        try {
            Register.loadScene(event, "AdminApproval.fxml");
//            List<String> notifications = getNotifications();
            handleNotifications();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleNotifications() {

        String userName = Register.lyan.userName;
        String password = Register.lyan.password;
        Admin admin = Admin.getInstance(userName, password);

        List<String> notifications = admin.getNotifications();

        if (!notifications.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pending Notifications");
            alert.setHeaderText("You have pending notifications:");
            StringBuilder sb = new StringBuilder();
            for (String notification : notifications) {
                sb.append(notification).append("\n");
            }
            alert.setContentText(sb.toString());
            alert.showAndWait();
        }
    }
    @FXML
    public void clickOnUsers(ActionEvent event) {
        try {
            Register.loadScene(event, "userManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickOnStockOrders(ActionEvent event) {
        try {
            Register.loadScene(event, "stockOrders.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clickOnStocks(ActionEvent event) {
        try {
            Register.loadScene(event, "stockManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}





