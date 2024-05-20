package com.example.stockfinal;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class StockNotificationService extends ScheduledService<String> {
    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            @Override
            protected String call() {
                return "Stock XYZ price has changed!";
            }
        };
    }
}
