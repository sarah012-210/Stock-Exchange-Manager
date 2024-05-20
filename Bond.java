package com.example.stockfinal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class Bond extends Security {
    private final double faceValue;
    private final double interestRate;
    private final double maturityYears;


    public Bond(String symbol, double priceSecurity, double faceValue, double interestRate, double maturityYears) {
        super(symbol, priceSecurity);
        this.faceValue = faceValue;
        this.interestRate = interestRate;
        this.maturityYears = maturityYears;
    }



    public double getFaceValue() {
        return faceValue;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMaturityYears() {
        return maturityYears;
    }

    @Override
    public double calculateValue() {
        return faceValue * Math.pow(1 + interestRate, maturityYears);
    }
    @Override
    public String getDetails() {
        return "Face Value: " + faceValue + ", Interest Rate: " + interestRate + "%, Maturity: " + maturityYears + " years";
    }
    @Override
    public String toString() {
        return "Bond - " + super.toString() + ", Face Value: " + faceValue + ", Interest Rate: " + interestRate + "%, Maturity: " + maturityYears + " years";
    }


    public DoubleProperty faceValueProperty() {return  new SimpleDoubleProperty(faceValue);}
    public DoubleProperty  interestRateProperty() {return new SimpleDoubleProperty(interestRate); }
    public DoubleProperty maturityYearsProperty() {return new SimpleDoubleProperty(maturityYears); }



}
