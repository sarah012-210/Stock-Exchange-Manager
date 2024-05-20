package com.example.stockfinal;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class controllerStockOrders {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Stock> operationsTableView = new TableView<>();
    @FXML
    private TableColumn<Stock, String> labelColumn;
    @FXML
    private TableColumn<Stock, String> statusColumn;
    @FXML
    private TableColumn<Stock, Integer> availableColumn;

    @FXML
    private TableColumn<Stock, Double> priceColumn;

    private static final String ORDERS_CSV_FILE = "src/main/resources/com/example/stockfinal/StockWithOpertions.csv";

    public void initialize() {
        labelColumn.setCellValueFactory(data -> data.getValue().stockLabelProperty());
        availableColumn.setCellValueFactory(data -> data.getValue().AvailableProperty().asObject());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        priceColumn.setCellValueFactory(data -> data.getValue().StockPriceProperty().asObject());

        // Assuming your CSV file is named transactions.csv
//        String filePath = "src/main/resources/com/example/stockfinal/transactions.csv";
//        Transaction.loadFromCSV(filePath, transactionTableView);
    }

    @FXML
    private void searchStock() {
        String stockLabel = searchField.getText();

        // Clear previous search results
        operationsTableView.getItems().clear();

        // Search for buy and sell operations related to the stock label
        List<Stock> stocks = searchOperationsByStockLabel(stockLabel , "src/main/resources/com/example/stockfinal/StockWithOpertions.csv");

        // Populate the table view with search results
        operationsTableView.getItems().addAll(stocks);
    }
    public List<Stock> searchOperationsByStockLabel(String stockLabel, String filePath) {
        List<Stock> stocks = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] headers = csvReader.readNext(); // Assuming the first row contains headers
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                // Assuming the CSV format is: Date,StockLabel,OperationType,Quantity,Price
                String currentStockLabel = row[0];
                String operationType = row[1];
                int quantity = Integer.parseInt(row[2]);
                double price = Double.parseDouble(row[3]);

                // Check if the current row matches the specified stock label
                if (currentStockLabel.equalsIgnoreCase(stockLabel)) {
                    // Create a StockOperation object and add it to the list
                    Stock stock = new Stock(currentStockLabel, operationType, quantity, price);
                    stocks.add(stock);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return stocks;
    }


    public static void writeOrderToCSV(String stockLabel, String operationType, int quantity, double price) {
        String[] orderDetails = {stockLabel, operationType, String.valueOf(quantity), String.valueOf(price)};

        try (CSVWriter writer = new CSVWriter(new FileWriter(ORDERS_CSV_FILE, true))) {
            writer.writeNext(orderDetails);
        } catch (IOException e) {
            e.printStackTrace();
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
