package com.example.stockfinal;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DepositWithdrawalController {

    @FXML
    private TextField amountField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button depositButton;

    @FXML
    private Button withdrawButton;

    @FXML
    TableView<Transaction> transactionsTable = new TableView<>();

    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws CsvValidationException, IOException {
        TableColumn<Transaction, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());

        TableColumn<Transaction, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty().asObject());

        TableColumn<Transaction, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());


        transactionsTable.getColumns().addAll(typeColumn, amountColumn, statusColumn);

        loadTransactionsFromCSV("src/main/resources/com/example/stockfinal/depositwithdrawal transactions.csv");
        transactionsTable.setItems(transactions);


    }


    @FXML
    private void handleDeposit() {
        String amountText = amountField.getText();
        if (!amountText.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountText);
                Transaction transaction = new Transaction("Deposit", amount, "Pending" , LocalDateTime.now());
                transactions.add(transaction);
                messageLabel.setText("Deposit request submitted");
                writeDepositeDataToCSV();
                transaction.writeToCSV("src/main/resources/com/example/stockfinal/transactions.csv");
            } catch (NumberFormatException e) {
                messageLabel.setText("Invalid amount entered");
            }
        } else {
            messageLabel.setText("Please enter an amount");
        }
    }

    @FXML
    private void handleWithdraw() {
        String amountText = amountField.getText();
        if (!amountText.isEmpty()) {
            try {
                double amount = Double.parseDouble(amountText);
                Transaction transaction = new Transaction("Withdraw",amount, "Pending" , LocalDateTime.now());
                transactions.add(transaction);
                messageLabel.setText("Withdrawal request submitted");
                writeDepositeDataToCSV();
                transaction.writeToCSV("src/main/resources/com/example/stockfinal/transactions.csv");
            } catch (NumberFormatException e) {
                messageLabel.setText("Invalid amount entered");
            }
        } else {
            messageLabel.setText("Please enter an amount");
        }
    }
    @FXML
    private void writeDepositeDataToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("src/main/resources/com/example/stockfinal/depositwithdrawal transactions.csv"))) {
            // Write column headers
            String[] headers = {"Type", "Amount", "Status"};
            writer.writeNext(headers);

            // Iterate over transactions and write each one to CSV
            for (Transaction transaction : transactions) {
                String[] data = {transaction.getType(), String.valueOf(transaction.getAmount()), transaction.getStatus()};
                writer.writeNext(data);
            }

            messageLabel.setText("Data written to CSV file");
        } catch (IOException e) {
            messageLabel.setText("Error writing data to CSV file");
            e.printStackTrace();
        }
    }
    public void loadTransactionsFromCSV(String filePath) throws IOException, CsvValidationException {
        List<Transaction> loadedTransactions = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextRecord;
            // Skip the header row
            reader.readNext();
            while ((nextRecord = reader.readNext()) != null) {
                String type = nextRecord[0];
                double amount = Double.parseDouble(nextRecord[1]);
                String status = nextRecord[2];
                Transaction transaction = new Transaction(type, amount, status);
                loadedTransactions.add(transaction);
            }
        }

        transactions.addAll(loadedTransactions);
        transactionsTable.setItems(transactions);
    }
    public void ClickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


