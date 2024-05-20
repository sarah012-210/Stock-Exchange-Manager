package com.example.stockfinal;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class orderManagementController extends User implements Initializable {
    Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableView<Stock> selectedStocksTable;
    @FXML
    private TextField sellField;
    @FXML
    private Button sellButton;
    @FXML
    private Label balanceLabel;
    @FXML
    private double walletBalance = getWallet();
    private static int quantityToSell;
    private static Stock selectedStock;
    private ObservableList<Stock> stocks = FXCollections.observableArrayList();
    private ObservableList<Stock> soldStocks = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {

        try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/com/example/stockfinal/ustocks.csv"))) {
            csvReader.readNext();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                String label = nextRecord[0];
                double price = Double.parseDouble(nextRecord[1]);
                int available = Integer.parseInt(nextRecord[2]);
                double profit = Double.parseDouble(nextRecord[3]);
                stocks.add(new Stock(label, price, available, profit));
            }
        } catch (IOException | NumberFormatException | CsvValidationException e) {
            e.printStackTrace();
        }

        TableColumn<Stock, String> labelColumn = new TableColumn<>("Stock Label");
        labelColumn.setCellValueFactory(data -> data.getValue().stockLabelProperty());

        TableColumn<Stock, Integer> availableColumn = new TableColumn<>("Available");
        availableColumn.setCellValueFactory(data -> data.getValue().AvailableProperty().asObject());

        TableColumn<Stock, Double> priceColumn = new TableColumn<>("Stock Price");
        priceColumn.setCellValueFactory(data -> data.getValue().StockPriceProperty().asObject());

        TableColumn<Stock, Double> profitColumn = new TableColumn<>("Profit");
        profitColumn.setCellValueFactory(data -> data.getValue().ProfitProperty().asObject());

        selectedStocksTable.getColumns().addAll(labelColumn, availableColumn, priceColumn, profitColumn);
        selectedStocksTable.setItems(stocks);
        updateBalanceLabel();
        sellButton.setOnAction(event -> sellStocks());

    }

    @FXML
    private void sellStocks() {
         selectedStock = selectedStocksTable.getSelectionModel().getSelectedItem();
        try {
            quantityToSell = Integer.parseInt(sellField.getText());
            if (selectedStock != null && !sellField.getText().isEmpty() && quantityToSell > 0) {
                if (selectedStock.getNumOfAvailable() >= quantityToSell) {
                    double totalSalePrice = selectedStock.getCompanyInitialPrice() * quantityToSell;

                    walletBalance += totalSalePrice;
                    updateBalanceLabel();

                    selectedStock.sell(quantityToSell);

                    soldStocks.add(selectedStock);
                    selectedStocksTable.refresh();
                    updateCSVUserStocks();
                    updateCSVMainStocks();
                    Transaction transaction = new Transaction("SELL", quantityToSell, "COMPLETED", LocalDateTime.now());
                    transaction.writeToCSV("src/main/resources/com/example/stockfinal/transactions.csv");
                    controllerStockOrders.writeOrderToCSV(selectedStock.getLabel(), "SELL", quantityToSell, selectedStock.getCompanyInitialPrice());

                } else {
                    System.out.println("Insufficient stocks available.");
                }
                setWallet(walletBalance);
            } else {
                System.out.println("Invalid quantity or stock not selected.");
            }
        } catch (NumberFormatException e ){
            System.out.println("Invalid quantity entered.");

        }

    }

    public ObservableList<Stock> getSoldStocks() {
        return soldStocks;
    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Wallet Balance: $%.2f", walletBalance));
    }

    private void updateCSVMainStocks() {
        String csvFilePath = "src/main/resources/com/example/stockfinal/stock.csv";
        boolean fileExists = new File(csvFilePath).exists();

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFilePath, true))) {
            if (!fileExists || new File(csvFilePath).length() == 0) {
                csvWriter.writeNext(new String[]{"Label", "Price", "Available", "Profit"});
            }
            for (Stock stock : soldStocks) {
                String[] record = {
                        stock.getLabel(),
                        String.valueOf(stock.getCompanyInitialPrice()),
                        String.valueOf(quantityToSell),
                        String.valueOf(stock.getProfitPercentage())
                };
                csvWriter.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCSVUserStocks() {
        String csvFilePath = "src/main/resources/com/example/stockfinal/ustocks.csv";

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFilePath))) {
            csvWriter.writeNext(new String[]{"Label", "Price", "Available", "Profit"});

            for (Stock stock : stocks) {
                String[] record = {
                        stock.getLabel(),
                        String.valueOf(stock.getCompanyInitialPrice()),
                        String.valueOf(stock.getNumOfAvailable()),
                        String.valueOf(stock.getProfitPercentage())
                };
                csvWriter.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void clickOnBack(ActionEvent event) {
        try {
            Register.loadScene(event, "User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}