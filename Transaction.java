package com.example.stockfinal;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private final StringProperty type;
    private final DoubleProperty amount;
    private final StringProperty status;
    private  ObjectProperty<LocalDateTime> date;
    private  ObjectProperty<Stock> stock;
    private  StringProperty state;

    public Transaction(String type, double amount, String status, LocalDateTime date) {
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.status = new SimpleStringProperty(status);
        this.date = new SimpleObjectProperty<>(date);
        this.stock = new SimpleObjectProperty<>(null);
        this.state = new SimpleStringProperty("");
    }

    public Transaction(Stock stock, String state) {
        this.type = new SimpleStringProperty("");
        this.amount = new SimpleDoubleProperty(0.0);
        this.status = new SimpleStringProperty("");
        this.date = new SimpleObjectProperty<>(null);
        this.stock = new SimpleObjectProperty<>(stock);
        this.state = new SimpleStringProperty(state);
    }

    public Transaction(String type, double amount, String status) {
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.status = new SimpleStringProperty(status);
    }
    public Transaction(Bond bond, String state) {
        this.type = new SimpleStringProperty("");
        this.amount = new SimpleDoubleProperty(0.0);
        this.status = new SimpleStringProperty("");
        this.date = new SimpleObjectProperty<>(null);
        ObjectProperty<Bond> bond1 = new SimpleObjectProperty<>(bond);
        this.state = new SimpleStringProperty(state);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public LocalDateTime getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDateTime> dateProperty() {
        return date;
    }

    public Stock getStock() {
        return stock.get();
    }

    public void setStock(Stock stock) {
        this.stock.set(stock);
    }

    public void writeToCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String formattedDate = getDate().format(DateTimeFormatter.ISO_DATE_TIME);
            writer.append(String.join(",", getType(), String.valueOf(getAmount()), getStatus(), formattedDate));
            writer.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadFromCSV(String filePath, TableView<Transaction> tableView) {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String type = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    String status = parts[2].trim();
                    LocalDateTime date = LocalDateTime.parse(parts[3].trim(), DateTimeFormatter.ISO_DATE_TIME);
                    Transaction transaction = new Transaction(type, amount, status, date);
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tableView.setItems(transactions);
    }
}


