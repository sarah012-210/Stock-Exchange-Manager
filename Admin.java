package com.example.stockfinal;


import java.util.ArrayList;
import java.util.List;

public class  Admin extends Person implements StockObserver  {
    private boolean tradingSession;
    private static Admin instance;
    private List<String> notifications = new ArrayList<>();



    User user=new User();

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

public static boolean checkAdmin(){

      if(instance==null){
          return true;
      }
      return false;

}
    public  void stockOrders(){
        for (User user : DataBase.users)
            DataBase. allTransactions.add(user. getTransactions());

    }
    public void price(String label){
        //to be modified
   /* while(Interactions.searchStock(label) == null) {
      System.out.println("Try again!");
    }*/
        // Interactions.searchStock(label).getPriceHistory();
    }  //stock
    public void approvalSystem(){} //user
//    public void tradingControl(boolean tradingControl ){
//        this.tradingControl = tradingControl;
//        //
//    }
public void addUsers(String userName, String password,String firstName,String secondName ){
    User user=new User(userName,password,firstName,secondName);
    DataBase.users.add(user);
}
    public void removeUsers(String userName ){
        DataBase.users.remove(Search.searchUser(userName));
    }
    public void updateUser(User user,String userName, String password,String firstName,String secondName, boolean premium,double wallet) {
        user.setUserName(userName);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setPremium(premium);
        user.setWallet(wallet);
    }
    public User retriveUser(String userName){
        return Search.searchUser(userName);
    }

    public Stock addStocks(String label, double companyInitialPrice, int numOfAvailable, float ProfitPercentage, float dividends){
        Stock stock=new Stock(label,companyInitialPrice,numOfAvailable,ProfitPercentage,dividends);
        DataBase.stocks.add(stock);
        return stock;

    }
    public void deleteStocks(String label){
        for(Stock stock:DataBase.stocks)
            if(stock.getLabel().equals(label))
                DataBase.stocks.remove(stock);
    }
    /*public Stock retrieve(String label){

  return Interactions.searchStock(label);

    }*/
//    public void updateStocks(String label, float companyInitialPrice, int numOfAvailable, float ProfitPercentage,float dividends){
//        for(Stock stock:DataBase.stocks)
//            if(stock.getLabel().equals(label)){
//                stock.setLabel(label);
//                stock.setCompanyInitialPrice(companyInitialPrice);
//                stock.setNumOfAvailable(numOfAvailable);
//                stock.setProfitPercentage(ProfitPercentage);
//                stock.setDividends(dividends);
//
//            }
//    }
    public void updateStocks(Stock stock, String label,
                             int numOfAvailable, double ProfitPercentage, double dividends, double price){
        stock.setLabel(label);
        stock.setNumOfAvailable(numOfAvailable);
        stock.setProfitPercentage(ProfitPercentage);
        stock.setDividends(dividends);
        stock.updatePrice(price);
    }
    public Stock retriveStock(String label){
        return Search.searchStock(label);
    }


    @Override
    public void update(String stockName, double price) {
        // Implement notification logic here
        System.out.println("Admin received update for stock: " + stockName + ", New price: " + price);
        // You can send notification to admin via email or any other preferred method
    }
    public void addNotification(String notification) {
        notifications.add(notification);
    }

    // Method to display notifications
    public List<String> getNotifications() {
        return notifications;
    }
    public void openTradingSession(){
        tradingSession = true;
    }
    public void closeTradingSession(){
        tradingSession = false;
    }

}