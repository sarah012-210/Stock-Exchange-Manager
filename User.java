package com.example.stockfinal;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.ArrayList;
import java.util.Collections;


public class User extends Person  implements StockObserver{
   private static boolean premium=false;
    private static double wallet;
    private String userName;
    private final ArrayList<Stock> userStocks=new ArrayList <>();
    private final ArrayList<Transaction> transactions =new ArrayList <>();

    public User(String userName, String password, String type) {
        super(userName,password);

    }

    @Override
    public void update(String stockName, double price) {
        System.out.println("User " + userName + " notified: Stock: " + stockName + ", Price: " + price);
    }
    public User(){
 }
    public User(String userName, String password){

        super(userName,password);

    }

    public User(String userName, String password,String firstName,String secondName){

        super(userName,password,firstName,secondName);

    }
    public void setPremium(boolean premium) {
        User.premium = premium;
    }

    public void setWallet(double wallet) {
        User.wallet = wallet;
        System.out.println(User.wallet);
    }
    public double getWallet() {
        System.out.println(wallet);
        return wallet;
    }
    public boolean isPremium() {
        return premium;
    }
    public void upgradeToPremium() {
        premium = true;
    }

    public void buyStocks(Stock stock) {
        Transaction transaction = new Transaction(stock, "bought");
        transactions.add(transaction);

    }
    public void buyBonds(Bond bond) {
        Transaction transaction = new Transaction(bond, "bought");
        transactions.add(transaction);

    }
    public double calculateEarnedInterest(Bond bond) {
        return bond.getPriceSecurity() * bond.getInterestRate() * bond.getMaturityYears();
    }

    public double calculateLoss(Bond bond) {
        return bond.getPriceSecurity() * bond.getMaturityYears();
    }


    public void sellStocks(String label){
        for(Stock stock:DataBase.stocks)
            if(stock.getLabel().equals(label)) {
                wallet+=stock.getPrice();
                userStocks.remove(stock);
                if(stock.getNumOfAvailable()==1)
                    DataBase.stocks.add(stock);
                else
                    stock.setNumOfAvailable(stock.getNumOfAvailable()+1);
                Transaction transaction=new Transaction(stock,"sold");
                transactions.add(transaction);
            }
    }
    public ArrayList<Stock> getUserStocks() {
        return userStocks;
    }
    public void chartingOptions(Stock stock){
        double openingPrice, closingPrice, maximumPrice, minimumPrice,profitOrLoss;
        openingPrice=stock.getCompanyInitialPrice();
        closingPrice=stock.getPrice();
        maximumPrice=Collections.max(stock.getPriceHistory());
        minimumPrice=Collections.min(stock.getPriceHistory());
        profitOrLoss= stock.getProfitPercentage();
    }  //stock //need to be charts

//stock

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }


    public StringProperty nameProperty() {
        return new SimpleStringProperty(getUserName());
    }
    public StringProperty passwordProperty() {
        return new SimpleStringProperty(getPassword());
    }
    public StringProperty typeProperty() {
        return new SimpleStringProperty("User");
    }
}
