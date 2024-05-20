package com.example.stockfinal;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminApprovalController {

    @FXML
    private TableView<Transaction> transactionsTable;

    @FXML
    private TableColumn<Transaction, String> typeColumn;

    @FXML
    private TableColumn<Transaction, Double> amountColumn;

    @FXML
    private TableColumn<Transaction, String> statusColumn;

    @FXML
    private Button approveButton;

    @FXML
    private Button rejectButton;

    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        List<Transaction> loadedTransactions = loadTransactionsFromCSV("src/main/resources/com/example/stockfinal/depositwithdrawal transactions.csv");
        transactions.addAll(loadedTransactions);

        transactionsTable.setItems(transactions);

    }

    public List<Transaction> loadTransactionsFromCSV(String filePath) {
        List<Transaction> transactions = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextRecord;
            // Skip the header row
            reader.readNext();
            while ((nextRecord = reader.readNext()) != null) {
                String type = nextRecord[0];
                double amount = Double.parseDouble(nextRecord[1]);
                String status = nextRecord[2];
                Transaction transaction = new Transaction(type, amount, status , LocalDateTime.now());
                transactions.add(transaction);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    @FXML
    private void handleApprove() {
        Transaction selectedTransaction = transactionsTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null && selectedTransaction.getStatus().equals("Pending")) {
            selectedTransaction.setStatus("Approved");
            transactionsTable.refresh(); // Refresh table view
            updateCSV(); // Update CSV with new status

            // Update status in DepositWithdrawalController
            updateTransactionStatus(selectedTransaction);
        }
    }

    @FXML
    private void handleReject() {
        Transaction selectedTransaction = transactionsTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null && selectedTransaction.getStatus().equals("Pending")) {
            selectedTransaction.setStatus("Rejected");
          //  transactionsTable.refresh();
            transactions.remove(selectedTransaction); // Remove from approval table
            transactionsTable.refresh(); // Refresh table view
            updateCSV(); // Update CSV with new status
            updateTransactionStatus(selectedTransaction);
        }

    }

    private void updateCSV() {
    try (CSVWriter writer = new CSVWriter(new FileWriter("src/main/resources/com/example/stockfinal/depositwithdrawal transactions.csv"))) {
        // Write column headers
        String[] headers = {"Type", "Amount", "Status"};
        writer.writeNext(headers);

        // Iterate over transactions and write each one to CSV
        for (Transaction transaction : transactions) {
            String[] data = {transaction.getType(), String.valueOf(transaction.getAmount()), transaction.getStatus()};
            writer.writeNext(data);
        }
    } catch (IOException e) {
        // Handle the IOException here
        e.printStackTrace();
        // Optionally, display an error message to the user
        // For example:
        // messageLabel.setText("Error updating CSV file");
    }
}
    public void updateTransactionStatus(Transaction updatedTransaction) {
        for (Transaction transaction : transactions) {
            if (transaction.equals(updatedTransaction)) {
                transaction.setStatus(updatedTransaction.getStatus());
                break;
            }
        }
    }
    public void clickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "Admin.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
