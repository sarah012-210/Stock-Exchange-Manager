// PortfolioController.java
package com.example.stockfinal;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;

public class PortfolioController {

    @FXML
    private TableView<Security> securitiesTable;
    @FXML
    private TableColumn<Security, String> symbolColumn;
    @FXML
    private TableColumn<Security, String> typeColumn;
    @FXML
    private TableColumn<Security, Double> priceColumn;
    @FXML
    private TableColumn<Security, Double> valueColumn;
    @FXML
    private TableColumn<Security, String> detailsColumn;
    @FXML
    private Label totalValueLabel;

    private Portfolio portfolio;
    private ObservableList<Security> securitiesList;

    public void initialize() {
        portfolio = new Portfolio();
        securitiesList = FXCollections.observableArrayList();

        symbolColumn.setCellValueFactory(cellData -> cellData.getValue().symbolProperty());
        typeColumn.setCellValueFactory(cellData -> {
            Security security = cellData.getValue();
            String type = security.getClass().getSimpleName();
            return new SimpleStringProperty(type);
        });
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        valueColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().calculateValue()).asObject());
        detailsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDetails()));
        securitiesTable.setItems(securitiesList);
        updateTotalValue();
    }

    @FXML
    private void handleAddSecurity() {
        // For demonstration, adding a Stock. In practice, you might open a dialog to get user input.
        Stock stock = new Stock("AAPL", 150, 10.0);
        portfolio.addSecurity(stock);
        securitiesList.add(stock);
        Bond bond = new Bond("US10Y", 1000, 1000, 0.05, 10);
        portfolio.addSecurity(bond);
        securitiesList.add(bond);
        Option option = new Option("vgvg", 10, 1, 87);
        portfolio.addSecurity(option);
        securitiesList.add(option);

        updateTotalValue();
    }

    @FXML
    private void handleRemoveSecurity() {
        Security selectedSecurity = securitiesTable.getSelectionModel().getSelectedItem();
        if (selectedSecurity != null) {
            portfolio.removeSecurity(selectedSecurity);
            securitiesList.remove(selectedSecurity);
            updateTotalValue();
        }
    }

    private void updateTotalValue() {
        double totalValue = portfolio.calculateTotalValue();
        totalValueLabel.setText("Total Value: " + totalValue);
    }
    public void ClickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}