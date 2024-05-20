package com.example.stockfinal;

import javafx.beans.property.DoubleProperty;

public class Option extends Security {
    private int contracts;
    private double strikePrice;
      public Option(){}
    public Option(String symbol, double priceSecurity, double strikePrice ,int contracts) {
        super(symbol, priceSecurity);
        this.contracts = contracts;
        this.strikePrice = strikePrice;
    }

    @Override
    public double calculateValue() {
        return contracts * (getPriceSecurity() - strikePrice);
    }
    @Override
    public String getDetails() {
        return "Contracts: " + contracts + ", Strike Price: " + strikePrice;
    }
    @Override
    public String toString() {
        return "Option - " + super.toString() + ", Contracts: " + contracts + ", Strike Price: " + strikePrice;
    }
    @Override
    public DoubleProperty faceValueProperty() {
        return null;
    }

    @Override
    public DoubleProperty interestRateProperty() {
        return null;
    }

    @Override
    public DoubleProperty maturityYearsProperty() {
        return null;
    }
}