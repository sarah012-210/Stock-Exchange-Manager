package com.example.stockfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.io.IOException;

public class controllerPremiumSubscription extends User{

    @FXML
    private Label balanceLabel,errorLabel;

    @FXML
    private double walletBalance=getWallet();

    @FXML
    private RadioButton monthlyRadioButton;

    @FXML
    private RadioButton yearlyRadioButton;

    private ToggleGroup subscriptionGroup;

    public void initialize() {
        subscriptionGroup = new ToggleGroup();
        monthlyRadioButton.setToggleGroup(subscriptionGroup);
        yearlyRadioButton.setToggleGroup(subscriptionGroup);
        updateBalanceLabel();
    }

    @FXML

    void handlePayButtonClick(ActionEvent event) {
        updateBalanceLabel();
        RadioButton selectedRadioButton = (RadioButton) subscriptionGroup.getSelectedToggle();
        String selectedOption = selectedRadioButton.getText();
        System.out.println(walletBalance);
        if(walletBalance>=9.99&& selectedOption.equals(monthlyRadioButton.getText())) {
            walletBalance=walletBalance - 9.99;
            setWallet(walletBalance);
            updateBalanceLabel();

            boolean paymentSuccess = processPayment(selectedOption);
            Alert alert = new Alert(paymentSuccess ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Payment Result");
            alert.setHeaderText(null);
            alert.setContentText(paymentSuccess ? "Payment Successful! You are now a premium member." : "Payment Failed! Please try again.");
            alert.showAndWait();
            upgradeToPremium();

    }
        if(walletBalance>=99.99&& selectedOption.equals(yearlyRadioButton.getText())) {
            walletBalance=walletBalance - 99.99;
            setWallet(walletBalance);
            updateBalanceLabel();

            boolean paymentSuccess = processPayment(selectedOption);
            Alert alert = new Alert(paymentSuccess ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Payment Result");
            alert.setHeaderText(null);
            alert.setContentText(paymentSuccess ? "Payment Successful! You are now a premium member." : "Payment Failed! Please try again.");
            alert.showAndWait();
            upgradeToPremium();

        }
        else
         errorLabel.setText("Charge your wallet balance");




    }

    private boolean processPayment(String option) {
        System.out.println("Processing payment for: " + option);
        return true;
    }
    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Wallet Balance: $%.2f", walletBalance));
    }

    public void clickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ClickOnPremiumOptions(ActionEvent event) throws IOException {
        if(isPremium()) {
            try {
                Register.loadScene(event, "PremiumOptions.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else  errorLabel.setText("Subscribe to premium first");

    }
}