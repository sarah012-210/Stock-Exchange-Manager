package com.example.stockfinal;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PriceHistoryController {

    @FXML
    private TableView<PriceEntry> priceHistoryTable;

    @FXML
    private TableColumn<PriceEntry, String> labelColumn;

    @FXML
    private TableColumn<PriceEntry, Double> openColumn;

    @FXML
    private TableColumn<PriceEntry, Double> closeColumn;

    @FXML
    private TableColumn<PriceEntry, Double> highColumn;

    @FXML
    private TableColumn<PriceEntry, Double> lowColumn;
    @FXML
    private Button exportButton;

    @FXML
    public void initialize() {
        // Initialize table columns
        labelColumn.setCellValueFactory(data -> data.getValue().labelProperty());
        openColumn.setCellValueFactory(data -> data.getValue().openProperty().asObject());
        closeColumn.setCellValueFactory(data -> data.getValue().closeProperty().asObject());
        highColumn.setCellValueFactory(data -> data.getValue().highProperty().asObject());
        lowColumn.setCellValueFactory(data -> data.getValue().lowProperty().asObject());

        // Load price history from CSV
        loadPriceHistoryFromCSV("src/main/resources/com/example/stockfinal/pricehistory.csv");
        exportButton.setOnAction(event -> exportToCSV());
    }

    // Method to load price history from CSV
    private void loadPriceHistoryFromCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextRecord;
            List<PriceEntry> priceHistory = new ArrayList<>();
            reader.readNext();

            while ((nextRecord = reader.readNext()) != null) {
                String label = nextRecord[0];
                double open = Double.parseDouble(nextRecord[1]);
                double close = Double.parseDouble(nextRecord[2]);
                double high = Double.parseDouble(nextRecord[3]);
                double low = Double.parseDouble(nextRecord[4]);
                priceHistory.add(new PriceEntry(label, open, close, high, low));
            }

            priceHistoryTable.getItems().addAll(priceHistory);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void exportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Stock History");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write("Label,Open,Close,High,Low\n");

                for (PriceEntry stock : priceHistoryTable.getItems()) {
                    writer.write(stock.getLabel() + "," + stock.getOpen() +  "," + stock.getClose()+ ","
                            + stock.getHigh()+ "," + stock.getLow() +"\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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


