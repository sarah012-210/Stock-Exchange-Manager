package com.example.stockfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDateTime;

public class TransactionController {

    @FXML
    private TableView<Transaction> transactionTableView;

    @FXML
    private TableColumn<Transaction, String> typeColumn;

    @FXML
    private TableColumn<Transaction, Double> amountColumn;

    @FXML
    private TableColumn<Transaction, String> statusColumn;

    @FXML
    private TableColumn<Transaction, LocalDateTime> dateColumn;

    public void initialize() {
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());
        amountColumn.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        dateColumn.setCellValueFactory(data -> data.getValue().dateProperty());

        // Assuming your CSV file is named transactions.csv
        String filePath = "src/main/resources/com/example/stockfinal/transactions.csv";
        Transaction.loadFromCSV(filePath, transactionTableView);
    }
    public void ClickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}




