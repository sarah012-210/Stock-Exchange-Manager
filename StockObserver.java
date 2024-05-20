package com.example.stockfinal;

public interface StockObserver {
    void update(String stockName, double price);
}
