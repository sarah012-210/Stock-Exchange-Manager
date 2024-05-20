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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class controllerUser extends User implements Initializable {

    @FXML
    private TextField User;
    @FXML
    private TableView<Stock> tableViewStock;

    @FXML
    private TextField quantityField;

    @FXML
    private Button buyButton;
    @FXML
    private Button depositWithdrawalButton;
    @FXML
    private Button transactionHistoryButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField amountField;
    @FXML
    private double walletBalance =getWallet();
    @FXML
    private Label balanceLabel;
    private static int quantity;
    @FXML
    private Label moreError;



    private Stock selectedStock;
    private ObservableList<Stock> stocks = FXCollections.observableArrayList();
    private ObservableList<Stock> yourStocks = FXCollections.observableArrayList();
    TableView<Transaction> transactionList = new TableView<>();

public void initialize(URL location, ResourceBundle resources) {

    try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/com/example/stockfinal/stock.csv"))) {
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

    TableColumn<Stock, Double> priceColumn = new TableColumn<>("Stock Price");
    priceColumn.setCellValueFactory(data -> data.getValue().StockPriceProperty().asObject());

    TableColumn<Stock, Integer> availableColumn = new TableColumn<>("Available");
    availableColumn.setCellValueFactory(data -> data.getValue().AvailableProperty().asObject());

    TableColumn<Stock, Double> profitColumn = new TableColumn<>("Profit");
    profitColumn.setCellValueFactory(data -> data.getValue().ProfitProperty().asObject());

    tableViewStock.getColumns().addAll(labelColumn, priceColumn, availableColumn, profitColumn);

    tableViewStock.setItems(stocks);
    updateBalanceLabel();
    addButton.setOnAction(e -> addToWallet());
    buyButton.setOnAction(event -> buyStock());
    depositWithdrawalButton.setOnAction(this::openDepositWithdrawalWindow);
    transactionHistoryButton.setOnAction(this::showTransactionHistory);


}
    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Wallet Balance: $%.2f", walletBalance));
    }
    private void addToWallet() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount > 0) {
                walletBalance += amount;
                updateBalanceLabel();
                amountField.clear();
            } else {
                // showError("Amount must be positive.");
            }
            setWallet(walletBalance);
        } catch (NumberFormatException e) {
            //  showError("Invalid amount entered.");
        }
    }
    private void showError(String message) {

        System.out.println("Error: " + message);
    }

    private void showMessage(String message) {
        System.out.println("Message: " + message);
    }

    @FXML
    private void buyStock() {
        Stock selectedStock = tableViewStock.getSelectionModel().getSelectedItem();
        setSelectedStock(selectedStock);
        try {
            DataBase.stocks.add(selectedStock);
            quantity = Integer.parseInt(quantityField.getText());
            if (selectedStock.getCompanyInitialPrice() > 0) {
                if (walletBalance >= selectedStock.getCompanyInitialPrice() * quantity) {
                    if (selectedStock != null) {
                        if (yourStocks.contains(selectedStock) && quantity > selectedStock.getNumOfAvailable()) {
                            moreError.setText("Not enough stocks.");
                        } else {
                            walletBalance -= selectedStock.getCompanyInitialPrice() * quantity;
                            updateBalanceLabel();
                            showMessage("Stock purchased for $" + selectedStock.getCompanyInitialPrice());
                            setWallet(selectedStock.getCompanyInitialPrice());
                            buyStocks(getSelectedStock());
                            selectedStock.buy(quantity);
                            yourStocks.add(selectedStock);
                            tableViewStock.refresh();
                            updateCSV("src/main/resources/com/example/stockfinal/stock.csv", stocks);
                            updateCSVForYourStocks();
                            Transaction transaction = new Transaction("BUY", quantity, "COMPLETED", LocalDateTime.now());
                            transaction.writeToCSV("src/main/resources/com/example/stockfinal/transactions.csv");
                            controllerStockOrders.writeOrderToCSV(selectedStock.getLabel(), "BUY", quantity, selectedStock.getCompanyInitialPrice());
                        }
                    }

                } else {
                    showError("Stock price must be positive.");
                }
            } setWallet(walletBalance);
        } catch (NumberFormatException e) {
            showError("Invalid stock price entered.");
        }


    }

    public Stock getSelectedStock() {
        return selectedStock;
    }

    public void setSelectedStock(Stock selectedStock) {
        this.selectedStock = selectedStock;
    }
    @FXML
    private void ClickOnOrder(ActionEvent event) {
        try {
            Register.loadScene(event, "SellStocks.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ClickOnChartingOptions(ActionEvent event) throws IOException{
        try {
            Register.loadScene(event, "Chart.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void ClickOnMarketPerformance(ActionEvent event) throws IOException{
        try {
            Register.loadScene(event, "market_performance.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ClickOnPremium(ActionEvent event) throws IOException {
        try{
            if (!isPremium()) {
                Register.loadScene(event, "Premium.fxml");
        }
        if (isPremium()) {
            Register.loadScene(event, "PremiumOptions.fxml");
        }
    }
        catch (IOException e) {
            e.printStackTrace();
        }

}
    public void ClickOnSecurity(ActionEvent event){
        try {
            Register.loadScene(event, "Portfolio.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openDepositWithdrawalWindow(ActionEvent event) {
        try {
            Register.loadScene(event, "DepositWithdrawal.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void showTransactionHistory(ActionEvent event) {
        try {
            Register.loadScene(event, "TransactionHistory.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showPriceHistory(ActionEvent event) {
        try {
            Register.loadScene(event, "PriceHistory.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ClickOnBonds(ActionEvent event) {
        try {
            Register.loadScene(event, "Bond.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void updateCSV(String filename, ObservableList<Stock> updatedStocks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Label,Price,Available,Profit\n");
            for (Stock stock : updatedStocks) {
                writer.write(stock.getLabel() + "," + stock.getCompanyInitialPrice() + "," + stock.getNumOfAvailable() + "," + stock.getProfitPercentage() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCSVForYourStocks() {
        String csvFilePath = "src/main/resources/com/example/stockfinal/ustocks.csv";
        boolean fileExists = new File(csvFilePath).exists();

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFilePath, true))) {
            if (!fileExists || new File(csvFilePath).length() == 0) {
                csvWriter.writeNext(new String[]{"Label", "Price", "Available", "Profit"});
            }
            for (Stock stock : yourStocks) {
                String[] record = {
                        stock.getLabel(),
                        String.valueOf(stock.getCompanyInitialPrice()),
                        String.valueOf(quantity),
                        String.valueOf(stock.getProfitPercentage())
                };
                csvWriter.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}


