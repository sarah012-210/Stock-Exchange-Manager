package com.example.stockfinal;

public abstract class Search {
    static User wrongUser =null;
    static Stock wrongStock =null;
    static Transaction wrongOrder =null;

    public static Stock searchStock(String label){
        for(Stock stock:DataBase.stocks)
            if (stock.getLabel().equals(label))
                return stock;
        return wrongStock;
    }

    public static User searchUser(String userName){
        for(User user:DataBase.users)
            if (user.getUserName().equals(userName))
                return user;
        return wrongUser;
    }
    public static Transaction searchOrder(String label){
        for(Transaction order:DataBase.Orders)
            if (order.getStock().getLabel().equals(label))
                return order;
        return wrongOrder;
    }

}
