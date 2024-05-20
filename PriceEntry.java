package com.example.stockfinal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PriceEntry {

    private final StringProperty label;
    private final DoubleProperty open;
    private final DoubleProperty close;
    private final DoubleProperty high;
    private final DoubleProperty low;

    public PriceEntry(String label, double open, double close, double high, double low) {
        this.label = new SimpleStringProperty(label);
        this.open = new SimpleDoubleProperty(open);
        this.close = new SimpleDoubleProperty(close);
        this.high = new SimpleDoubleProperty(high);
        this.low = new SimpleDoubleProperty(low);
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public double getOpen() {
        return open.get();
    }

    public DoubleProperty openProperty() {
        return open;
    }

    public double getClose() {
        return close.get();
    }

    public DoubleProperty closeProperty() {
        return close;
    }

    public double getHigh() {
        return high.get();
    }

    public DoubleProperty highProperty() {
        return high;
    }

    public double getLow() {
        return low.get();
    }

    public DoubleProperty lowProperty() {
        return low;
    }
}

