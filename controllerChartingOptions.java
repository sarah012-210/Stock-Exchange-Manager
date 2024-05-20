package com.example.stockfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

public class controllerChartingOptions {

        @FXML
        private Label errorMessage;
        @FXML
        private ComboBox<String> chartTypeSelector;

        @FXML
        private ComboBox<String> periodSelector;

        @FXML
        private Button loadButton;

        @FXML
        private StackPane chartPane;

        @FXML
        public void initialize() {
            loadButton.setOnAction(e -> loadChart());
        }

        private void loadChart() {
            String selectedChartType = chartTypeSelector.getValue();
            String selectedPeriod = periodSelector.getValue();
            chartPane.getChildren().clear();
            if (selectedChartType == null || selectedPeriod == null) {
                errorMessage.setText("Please select all required options");
                return;
            }

            if (selectedChartType.equals("Line Chart")) {
                errorMessage.setText("");
                LineChart<String, Number> lineChart = createLineChart(selectedPeriod);
                chartPane.getChildren().add(lineChart);
            } else if (selectedChartType.equals("Candlestick Chart")) {
                errorMessage.setText("");
                CandleStickChart candleStickChart = createCandleStickChart(selectedPeriod);
                chartPane.getChildren().add(candleStickChart);
                   }
            else {
                errorMessage.setText("Invalid chart type selected");
            }
        }


        private LineChart<String, Number> createLineChart(String period) {
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Stock Price Performance");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Stock Data");

            LocalDate startDate = LocalDate.now();
            Random random = new Random();
            for (int i = 0; i < getPeriodDays(period); i++) {
                series.getData().add(new XYChart.Data<>(startDate.minusDays(i).toString(), 100 + random.nextInt(20)));
            }

            lineChart.getData().add(series);
            return lineChart;
        }

        private CandleStickChart createCandleStickChart(String period) {

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            CandleStickChart candleStickChart = new CandleStickChart(xAxis, yAxis);
            candleStickChart.setTitle("Stock Price Performance");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Stock Data");

            // Dummy data generation for demonstration
            LocalDate startDate = LocalDate.now();
            Random random = new Random();
            for (int i = 0; i < getPeriodDays(period); i++) {
                String date = startDate.minusDays(i).toString();
                Number open = 100 + random.nextInt(20);
                Number close = 100 + random.nextInt(20);
                Number high = Math.max(open.doubleValue(), close.doubleValue()) + random.nextInt(10);
                Number low = Math.min(open.doubleValue(), close.doubleValue()) - random.nextInt(10);

                XYChart.Data<String, Number> data = new XYChart.Data<>(date, close);
                data.setExtraValue(new CandleStickChart.CandleData(open, close, high, low));
                series.getData().add(data);
            }

            candleStickChart.getData().add(series);
            return candleStickChart;
        }

        private int getPeriodDays(String period) {
            return switch (period) {
                case "1 Day" -> 1;
                case "1 Week" -> 7;
                case "1 Month" -> 30;
                case "1 Year" -> 365;
                default -> 30;
            };
        }

    public void clickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "Admin.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


