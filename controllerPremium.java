package com.example.stockfinal;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class controllerPremium extends User {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button stockNotificationButton;

    @FXML
    private Button lineChartButton;

    @FXML
    private TextArea notificationArea;

    public void setPremium(boolean isPremium) {
        stockNotificationButton.setDisable(!isPremium);
        lineChartButton.setDisable(!isPremium);
    }

    @FXML
    private void subscribeForNotifications() {
        StockNotificationService notificationService = new StockNotificationService();
        notificationService.setPeriod(Duration.minutes(1)); // Check every minute
        notificationService.setOnSucceeded(event -> {
            String notification = notificationService.getValue();
            Platform.runLater(() -> addNotification(notification));
        });
        notificationService.start();
        System.out.println("Subscribed for stock notifications.");
    }

    private void addNotification(String notification) {
        notificationArea.appendText(notification + "\n");
    }

    @FXML
    private void showLineChart() {
        Stage chartStage = new Stage();
        chartStage.setTitle("Stock Line Chart");

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        yAxis.setLabel("Price");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Stock Price Monitoring");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Stock Prices");

        // Add initial data
        generateInitialData(series);

        // Simulate adding data points periodically
        simulateStockData(series);

        lineChart.getData().add(series);

        Scene scene = new Scene(lineChart, 800, 600);
        chartStage.setScene(scene);
        chartStage.show();
    }

    private void generateInitialData(XYChart.Series<Number, Number> series) {
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            series.getData().add(new XYChart.Data<>(i, random.nextInt(50)));
        }
    }

    private void simulateStockData(XYChart.Series<Number, Number> series) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private int time = 11;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    Random random = new Random();
                    series.getData().add(new XYChart.Data<>(time++, random.nextInt(50)));
                });
            }
        }, 2000, 2000); // Add new data point every 2 seconds
    }

    public void clickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "User.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
