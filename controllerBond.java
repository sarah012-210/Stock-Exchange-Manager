package com.example.stockfinal;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class controllerBond extends User implements Initializable { //"Name","Coupon Rate","Maturity Date","Yield"
    @FXML
    private TableView<Bond> tableViewBond;
    @FXML
    private TableView<Bond> tableViewBoughtBonds;
    @FXML
    private double walletBalance = getWallet();

    @FXML
    private Button buyButton;
    @FXML
    private Button sellButton;

    @FXML
    private Label balanceLabel;

    ObservableList<Bond> bonds = FXCollections.observableArrayList();
    private final ObservableList<Bond> yourBonds = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        // Setting up columns for available bonds table
        TableColumn<Bond, String> symbolColumn = new TableColumn<>("Symbol");
        symbolColumn.setCellValueFactory(data -> data.getValue().symbolProperty());
        TableColumn<Bond, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        TableColumn<Bond, Double> faceValueColumn = new TableColumn<>("Face Value");
        faceValueColumn.setCellValueFactory(data -> data.getValue().faceValueProperty().asObject());
        TableColumn<Bond, Double> interestRateColumn = new TableColumn<>("Interest Rate");
        interestRateColumn.setCellValueFactory(data -> data.getValue().interestRateProperty().asObject());
        TableColumn<Bond, Double> maturityYearsColumn = new TableColumn<>("Maturity Years");
        maturityYearsColumn.setCellValueFactory(data -> data.getValue().maturityYearsProperty().asObject());

        tableViewBond.getColumns().addAll(symbolColumn, priceColumn, faceValueColumn, interestRateColumn, maturityYearsColumn);

        // Setting up columns for bought bonds table
        TableColumn<Bond, String> boughtSymbolColumn = new TableColumn<>("Symbol");
        boughtSymbolColumn.setCellValueFactory(data -> data.getValue().symbolProperty());
        TableColumn<Bond, Double> boughtPriceColumn = new TableColumn<>("Price");
        boughtPriceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        TableColumn<Bond, Double> boughtFaceValueColumn = new TableColumn<>("Face Value");
        boughtFaceValueColumn.setCellValueFactory(data -> data.getValue().faceValueProperty().asObject());
        TableColumn<Bond, Double> boughtInterestRateColumn = new TableColumn<>("Interest Rate");
        boughtInterestRateColumn.setCellValueFactory(data -> data.getValue().interestRateProperty().asObject());
        TableColumn<Bond, Double> boughtMaturityYearsColumn = new TableColumn<>("Maturity Years");
        boughtMaturityYearsColumn.setCellValueFactory(data -> data.getValue().maturityYearsProperty().asObject());

        tableViewBoughtBonds.getColumns().addAll(boughtSymbolColumn, boughtPriceColumn, boughtFaceValueColumn, boughtInterestRateColumn, boughtMaturityYearsColumn);

        try {
            LoadBondsFromCSV("src/main/resources/com/example/finalooppro/bondnew.csv");
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        tableViewBond.setItems(bonds);
        tableViewBoughtBonds.setItems(yourBonds);
        updateBalanceLabel();

        buyButton.setOnAction(event -> buyBond());
        sellButton.setOnAction(event -> sellBond());
    }

    private void updateBalanceLabel() {
        balanceLabel.setText(String.format("Wallet Balance: $%.2f", walletBalance));
    }

    private void showError(String message) {
        System.out.println("Error: " + message);
    }

    private void showMessage(String message) {
        System.out.println("Message: " + message);
    }

    @FXML
    private void buyBond() {
        Bond selectedBond = tableViewBond.getSelectionModel().getSelectedItem();
        if (selectedBond != null && walletBalance >= selectedBond.getPriceSecurity()) {
            walletBalance -= selectedBond.getPriceSecurity();
            double earnedInterest = calculateEarnedInterest(selectedBond);
            walletBalance += earnedInterest;
            updateBalanceLabel();
            showMessage("Bond purchased for $" + selectedBond.getPriceSecurity() + " with earned interest of $" + earnedInterest);
            yourBonds.add(selectedBond);
            bonds.remove(selectedBond);
            updateCSV();
        } else {
            showError("Not enough balance to purchase bond or no bond selected.");
        }
    }

    @FXML
    private void sellBond() {
        Bond selectedBond = tableViewBoughtBonds.getSelectionModel().getSelectedItem();
        if (selectedBond != null) {
            double loss = calculateLoss(selectedBond);
            walletBalance += selectedBond.getPriceSecurity() - loss;
            updateBalanceLabel();
            showMessage("Bond sold for $" + (selectedBond.getPriceSecurity() - loss) + " with a loss of $" + loss);
            bonds.add(selectedBond);
            yourBonds.remove(selectedBond);
            updateCSV();
        } else {
            showError("No bond selected to sell.");
        }
    }

    public void LoadBondsFromCSV(String filepath) throws IOException, CsvValidationException, RuntimeException {
        List<Bond> loadedBonds = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            String[] nextRecord;
            // Skip the header row
            reader.readNext();
            while ((nextRecord = reader.readNext()) != null) {
                String symbol = nextRecord[0];
                double priceSecurity = Double.parseDouble(nextRecord[1]);
                double faceValue = Double.parseDouble(nextRecord[2]);
                double interestRate = Double.parseDouble(nextRecord[3]);
                double maturityYears = Double.parseDouble(nextRecord[4]);
                Bond bond = new Bond(symbol, priceSecurity, faceValue, interestRate, maturityYears);
                loadedBonds.add(bond);
            }
        }
        bonds.addAll(loadedBonds);
        tableViewBond.setItems(bonds);
    }

    private void updateCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("C:\\Users\\Sarah\\IdeaProjects\\oop pro\\src\\main\\resources\\com\\example\\ooppro\\bondnew.csv"))) {
            // Write column headers
            String[] headers = {"Symbol", "Price", "Face Value", "Interest Rate", "Maturity Years"};
            writer.writeNext(headers);

            // Write available bonds
            for (Bond bond : bonds) {
                String[] data = {
                        bond.getSymbol(),
                        String.valueOf(bond.getPriceSecurity()),
                        String.valueOf(bond.getFaceValue()),
                        String.valueOf(bond.getInterestRate()),
                        String.valueOf(bond.getMaturityYears())
                };
                writer.writeNext(data);
            }

            // Write bought bonds
            for (Bond bond : yourBonds) {
                String[] data = {
                        bond.getSymbol(),
                        String.valueOf(bond.getPriceSecurity()),
                        String.valueOf(bond.getFaceValue()),
                        String.valueOf(bond.getInterestRate()),
                        String.valueOf(bond.getMaturityYears())
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ClickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
