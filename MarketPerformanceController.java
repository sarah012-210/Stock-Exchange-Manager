package com.example.stockfinal;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class MarketPerformanceController implements Initializable {

    private final ObservableList<Stock> market = FXCollections.observableArrayList();
    @FXML
    private TableView<Stock> stockTableView;

    public void initialize(URL location, ResourceBundle resources) {

        try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/com/example/stockfinal/market.csv"))) {
            csvReader.readNext();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                String index = nextRecord[0];
                double value = Double.parseDouble(nextRecord[1]);
                double change = Double.parseDouble(nextRecord[2]);
                String percent = nextRecord[3];
                market.add(new Stock(index, value, change, percent));
            }
        } catch (IOException | NumberFormatException | CsvValidationException e) {
            e.printStackTrace();
        }


        stockTableView.setItems(market);

        TableColumn<Stock, String> indexColumn = new TableColumn<>("Index");
        TableColumn<Stock, Double> valueColumn = new TableColumn<>("Value");
        TableColumn<Stock, Double> changeColumn = new TableColumn<>("Change");
        TableColumn<Stock, String> percentColumn = new TableColumn<>("Percent");
        indexColumn.setCellValueFactory(data -> data.getValue().indexProperty());
        valueColumn.setCellValueFactory(data -> data.getValue().valueProperty().asObject());
        changeColumn.setCellValueFactory(data -> data.getValue().changeProperty().asObject());
        percentColumn.setCellValueFactory(data -> data.getValue().percentProperty());
        stockTableView.getColumns().addAll(indexColumn, valueColumn, changeColumn, percentColumn);

    }

    public void ClickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}