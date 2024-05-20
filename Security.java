package com.example.stockfinal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Security {
    private  StringProperty symbol;
    private  DoubleProperty priceSecurity;
    public Security(){}
    public Security(String symbol, double price) {
        this.symbol = new SimpleStringProperty(symbol);
        this.priceSecurity = new SimpleDoubleProperty(price);
    }

    public String getSymbol() {
        return symbol.get();
    }

    public StringProperty symbolProperty() {
        return symbol;
    }

    public double getPriceSecurity() {
        return priceSecurity.get();
    }

    public DoubleProperty priceProperty() {
        return priceSecurity;
    }

    public abstract double calculateValue();

    public abstract String getDetails();

    @Override
    public String toString() {
        return symbol.get() + ": " + priceSecurity.get();
    }

    public abstract DoubleProperty faceValueProperty();
    public abstract DoubleProperty  interestRateProperty();
    public abstract DoubleProperty maturityYearsProperty();
}
