package com.example.stockfinal;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Stock  extends Security implements StockSubject {
    private String label;
private List<StockObserver> observers;
    private String stockName;

    private double price;
    private double companyInitialPrice;
    private int numOfAvailable;
    private double ProfitPercentage;
    private final ArrayList<Double> priceHistory=new ArrayList <>();
    private int shares;
    private String index;
    private double value;
    private double change;
    private String percent;
    private String operationType; // Buy or Sell

    public Stock(String symbol,int shares ,double priceSecurity) {
        super(symbol, priceSecurity);
        this.shares = shares;
        this.price=  priceSecurity;
    }

    public Stock(String stockName, double price) {
        this.stockName = stockName;
        this.price = price;
        this.observers = new ArrayList<>();
    }
    public Stock(String stockLabel, double StockPrice,int Available) {
        this.label = stockLabel;
        this.companyInitialPrice =StockPrice;
        this.numOfAvailable = Available;

    }

    public Stock(String label, double companyInitialPrice, int numOfAvailable,
                 float ProfitPercentage, float dividends) {
        this.label = label;
        setDividends(dividends);
        setCompanyInitialPrice(companyInitialPrice);
        setNumOfAvailable(numOfAvailable);
        this.ProfitPercentage = ProfitPercentage;
        updatePrice(companyInitialPrice);

    }
    public Stock(String index, double value, double change, String percent) {
        this.index = index;
        this.value = value;
        this.change = change;
        this.percent = percent;
    }
    public Stock(String stockLabel, double StockPrice, int Available , double profit) {
        this.label = stockLabel;
        this.companyInitialPrice =StockPrice;
        this.numOfAvailable = Available;
        this.ProfitPercentage = profit;
    }

    public Stock(String currentStockLabel, String operationType, int quantity, double price) {
        this.label = currentStockLabel;
        this.operationType =operationType;
        this.numOfAvailable = quantity;
        this.companyInitialPrice = price;
    }

    public StringProperty indexProperty() {
        return new SimpleStringProperty(index);
    }

    public DoubleProperty valueProperty() {
        return new SimpleDoubleProperty(value);
    }

    public DoubleProperty changeProperty() {return  new SimpleDoubleProperty(change);}
    public StringProperty percentProperty() {return new SimpleStringProperty(percent); }

    public void buy(int quantity) {
        if (this.numOfAvailable >= quantity) {
            this.numOfAvailable -= quantity;
        } else {
            System.out.println("Not enough stock available.");
        }
    }


    public StringProperty stockLabelProperty() {
        return new SimpleStringProperty(label);
    }

    public IntegerProperty AvailableProperty() {
        return new SimpleIntegerProperty(numOfAvailable);
    }

    public DoubleProperty StockPriceProperty() {
        return  new SimpleDoubleProperty(companyInitialPrice);
    }
    public DoubleProperty ProfitProperty() {
        return  new SimpleDoubleProperty(ProfitPercentage);
    }
    public StringProperty statusProperty() {
        return new SimpleStringProperty(operationType);
    }

    public void setDividends(double dividends) {
        while(true) {
            if (dividends > 0) {
                break;
            }
        }
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public double getCompanyInitialPrice() {
        return companyInitialPrice;
    }

    public void setCompanyInitialPrice(double companyInitialPrice) {
        while(true) {
            if(companyInitialPrice>0) {
                this.companyInitialPrice = companyInitialPrice;
                this.price=companyInitialPrice;
                break;
            }
        }
    }

    public int getNumOfAvailable() {
        return numOfAvailable;
    }

    public void setNumOfAvailable(int numOfAvailable) {
        while (true) {
            if (numOfAvailable > 0) {
                this.numOfAvailable = numOfAvailable;
                break;
            }
        }
    }

    public double getProfitPercentage() {
        return ProfitPercentage;
    }

    public void setProfitPercentage(double profitPercentage) {
        this.ProfitPercentage = profitPercentage;
    }
    public ArrayList<Double> getPriceHistory() {
        return priceHistory;
    }



    public void updatePrice(double price) {
        this.price = price;
        priceHistory.add(price);
        notifyObservers();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }



    @Override
    public void registerObserver(StockObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(StockObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (StockObserver observer : observers) {
            observer.update(stockName, price);
        }
    }


    @Override
    public double calculateValue() {
        return getPrice() * shares;
    }
    @Override
    public String getDetails() {
        return "Shares: " + shares;
    }

    @Override
    public String toString() {
        return "Stock - " + super.toString() + ", Shares: " + shares;
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
    public void sell(int quantity) {
        if(this.numOfAvailable >= quantity){
            this.numOfAvailable -= quantity;
            System.out.println(numOfAvailable);
        }
    }
}





