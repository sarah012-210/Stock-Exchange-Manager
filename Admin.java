package com.example.stockfinal;


import java.util.ArrayList;
import java.util.List;

public class  Admin extends Person implements StockObserver  {
    private static Admin instance;
    private List<String> notifications = new ArrayList<>();

  private Admin(String userName, String password){

        super(userName,password);

    }
    private Admin(String userName, String password,String firstName,String secondName){

        super(userName,password,firstName,secondName);

    }

    public static Admin getInstance(String userName,String password) {
        if (instance == null) {
            instance = new Admin(userName, password);

        }
        return instance;

    }

    public static Admin getInstance(String userName,String password,String firstName,String secondName) {
    if (instance == null) {
        instance = new Admin(userName, password,firstName,secondName);

    }
    return instance;

}

    @Override
    public void update(String stockName, double price) {
        // Implement notification logic here
        System.out.println("Admin received update for stock: " + stockName + ", New price: " + price);
        // You can send notification to admin via email or any other preferred method
    }

    public List<String> getNotifications() {
        return notifications;
    }


}