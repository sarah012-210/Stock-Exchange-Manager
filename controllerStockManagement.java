//package com.example.stockfinal;
//
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//
//import java.awt.*;
//import java.io.FileReader;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;

//public class controllerStockManagement implements Initializable {
//    @FXML
//    private TableView<Stock> stocksTableView = new TableView<>();
//    @FXML
//    private Label searchMessage;
//    @FXML
//    private Label errorMessage;
//    @FXML
//    private TextField lableTextField;
//    @FXML
//    private TextField priceTextField;
//    @FXML
//    private TextField availableTextField;
//    @FXML
//    private TextField profitTextField;
//    @FXML
//    private ListView searchResult;
//    private ObservableList<Stock> stocks = FXCollections.observableArrayList();
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        TableColumn<Stock, String> labelColumn = new TableColumn<>("Stock Label");
//        labelColumn.setCellValueFactory(data -> data.getValue().stockLabelProperty());
//        TableColumn<Stock, Integer> availableColumn = new TableColumn<>("Available");
//        availableColumn.setCellValueFactory(data -> data.getValue().AvailableProperty().asObject());
//        TableColumn<Stock, Double> priceColumn = new TableColumn<>("Stock Price");
//        priceColumn.setCellValueFactory(data -> data.getValue().StockPriceProperty().asObject());
//        TableColumn<Stock, Double> profitColumn = new TableColumn<>("Profit");
//        profitColumn.setCellValueFactory(data -> data.getValue().ProfitProperty().asObject());
//        stocksTableView.getColumns().addAll(labelColumn,priceColumn, availableColumn, profitColumn);
//        readStocksFromCSV();
//        stocksTableView.setItems(stocks);
//    }
//    private void  readStocksFromCSV() {
//        try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/com/example/stockfinal/stock.csv"))) {
//            csvReader.readNext();
//            String[] nextRecord;
//            while ((nextRecord = csvReader.readNext()) != null) {
//                String lable = nextRecord[0];
//                double StockPrice = Double.parseDouble(nextRecord[1]);
//                int Available = Integer.parseInt(nextRecord[2]);
//                double profit = Double.parseDouble(nextRecord[3]);
//                stocks.add(new Stock(lable, StockPrice, Available , profit));
//            }
//        } catch (IOException | NumberFormatException | CsvValidationException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @FXML
//    private void deleteStock(ActionEvent event) {
//    // Get selected user from table view
//        Stock selectedStock = stocksTableView.getSelectionModel().getSelectedItem();
//
//        if (selectedStock != null) {
//        // Remove the user from the list
//            stocks.remove(selectedStock);
//
//        // Refresh table view
//            stocksTableView.setItems(stocks);
//        }
//    }
//    @FXML
//    private void addStock(ActionEvent event) {
//        // Get input from text fields or other UI components
//        String label = lableTextField.getText(); // Assuming you have a nameTextField
//        double StockPrice = Double.parseDouble(priceTextField.getText()); // Assuming you have a passwordTextField
//        int Available = Integer.parseInt(availableTextField.getText());
//        double profit = Double.parseDouble(profitTextField.getText());
//
//
//        // Add the new user to the list
//        stocks.add(new Stock(label, StockPrice, Available, profit));
//        // Refresh table view
//        stocksTableView.setItems(stocks);
//    }
//
////    public void clickOnUpdate(ActionEvent actionEvent) throws IOException {
////        button="Update";
////        if(currentStock=="")
////            errorMessage.setText("Please select a stock first");
////        else {
////            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateAddStock.fxml"));
////            root = loader.load();
////            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
////            scene = new Scene(root);
////            stage.setScene(scene);
////            stage.show();
////        }
////    }
//
////    public void clickOnAdd(ActionEvent actionEvent) throws IOException {
////        button="Add";
////        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateAddStock.fxml"));
////        root = loader.load();
////        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
////        scene = new Scene(root);
////        stage.setScene(scene);
////        stage.show();
////
////    }
//
////    public void clickOnRetrive(ActionEvent event) throws IOException {
////        if(currentStock=="")
////            errorMessage.setText("Please select a stock first");
////        else {
////            try {
////                Register.loadScene(event, "retriveStock.fxml");
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
////    }
//
////    public void clickOnSearch(ActionEvent actionEvent) {
////        String label = searchField.getText();
////        if(label.isEmpty())
////            searchMessage.setText("Please enter a label");
////        else{
////            boolean found=false;
////            for(Stock stock: DataBase.stocks)
////                if(stock.getLabel().equals(label)){
////                    searchResult.getItems();
////                    found=true;
////                }
////            if(!found){
////                searchMessage.setText("Stock is not found");
////            }
////        }
////    }
//    public void clickOnBack(ActionEvent event) throws IOException {
//        try {
//            Register.loadScene(event, "Admin.fxml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
package com.example.stockfinal;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

public class controllerStockManagement implements Initializable {

    @FXML
    private TableView<Stock> stocksTableView = new TableView<>();
    @FXML
    private TextField FirstName;
    @FXML
    private TextField SecondName;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField searchField;
    @FXML
    private ListView searchResult;
    @FXML
    private Label errorMessage;

    private ObservableList<Stock> stocks = FXCollections.observableArrayList();
@Override
    public void initialize(URL location, ResourceBundle resources) {

        TableColumn<Stock, String> labelColumn = new TableColumn<>("Stock Label");
        labelColumn.setCellValueFactory(data -> data.getValue().stockLabelProperty());
        TableColumn<Stock, Integer> availableColumn = new TableColumn<>("Available");
        availableColumn.setCellValueFactory(data -> data.getValue().AvailableProperty().asObject());
        TableColumn<Stock, Double> priceColumn = new TableColumn<>("Stock Price");
        priceColumn.setCellValueFactory(data -> data.getValue().StockPriceProperty().asObject());
        TableColumn<Stock, Double> profitColumn = new TableColumn<>("Profit");
        profitColumn.setCellValueFactory(data -> data.getValue().ProfitProperty().asObject());
        stocksTableView.getColumns().addAll(labelColumn,priceColumn, availableColumn, profitColumn);
        readStocksFromCSV();
        stocksTableView.setItems(stocks);


    }
    private void  readStocksFromCSV() {
        try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/com/example/stockfinal/stock.csv"))) {
            csvReader.readNext();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                String lable = nextRecord[0];
                double StockPrice = Double.parseDouble(nextRecord[1]);
                int Available = Integer.parseInt(nextRecord[2]);
                double profit = Double.parseDouble(nextRecord[3]);
                stocks.add(new Stock(lable, StockPrice, Available , profit));
            }
        } catch (IOException | NumberFormatException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openAddStock(ActionEvent event) {
        try {
            Register.loadScene(event, "updateAddStock.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openUpdateStock(ActionEvent event) {
        Stock selectedStock = stocksTableView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateStock.fxml"));
                Parent root = loader.load();

                // Get the controller associated with the loaded FXML
                controllerUpdateStock controller = loader.getController();

                // Set the callback function to handle updated user
                controller.setOnUpdateStock(updatedStock -> {
                    // Handle the updated user and update the list and CSV file
                    updateStock(updatedStock);
                    // Refresh table view
                    stocksTableView.refresh();
                });

                // Pass the selected user
                controller.initData(selectedStock);

                // Load the scene
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorMessage.setText("Please select a stock to update");
        }
    }
    public void updateStock(Stock updatedStock) {
        // Find the index of the updated user in the list
        int index = stocks.indexOf(updatedStock);

        if (index != -1) {
            // Update the user in the list
            stocks.set(index, updatedStock);

            // Update the user in the CSV file
            writeAllStocksToCSV(stocks);
        }
    }


    @FXML
    private void openRetrieveStock(ActionEvent event) {
        // Get selected user from table view
         Stock selectedStock = stocksTableView.getSelectionModel().getSelectedItem();

        if (selectedStock != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("retriveStock.fxml"));
                Parent root = loader.load();

                // Get the controller associated with the loaded FXML
                controllerRetriveStock controller = loader.getController();

                // Pass the selected user to controllerRetriveUser
                controller.initData(selectedStock);

                // Load the scene
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where no user is selected
            System.out.println("Please select a stock to retrieve.");
        }
    }

    @FXML
    private void addStock(ActionEvent event) {
        // Get input from text fields or other UI components
        String label = nameTextField.getText(); // Assuming you have a nameTextField
        double price = Double.parseDouble(passwordTextField.getText()); // Assuming you have a passwordTextField
        int available = Integer.parseInt(FirstName.getText());
        double profit = Double.parseDouble(SecondName.getText());

        // Add the new user to the list
        stocks.add(new Stock(label, price, available,profit));
        // Refresh table view
        stocksTableView.setItems(stocks);
    }



    // Method to delete an existing user
    @FXML
    private void deleteStock(ActionEvent event) {
        // Get selected user from table view
        Stock selectedStock = stocksTableView.getSelectionModel().getSelectedItem();

        if (selectedStock != null) {
            // Remove the user from the list
            stocks.remove(selectedStock);
            writeAllStocksToCSV(stocks);
            // Refresh table view
            stocksTableView.setItems(stocks);
        }
    }
    public void clickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "Admin.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write all users to the CSV file
    private void writeAllStocksToCSV(List<Stock> allStocks) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("src/main/resources/com/example/stockfinal/stock.csv"))) {
            // Write header
            String[] header = {"Label","Price","Available","Profit"};
            csvWriter.writeNext(header);
            // Write each user record
            for (Stock stock : allStocks) {
                String[] record = {stock.getLabel(), String.valueOf(stock.getPrice()), String.valueOf(stock.getNumOfAvailable()), String.valueOf(stock.getProfitPercentage())};
                csvWriter.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

