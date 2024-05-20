package com.example.stockfinal;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class controllerSignUp extends Person  {
    Stage stage;

    @FXML
    private Label ErrorMessage,errorMessage;
    @FXML
    private TextField  FirstName;
    @FXML
    private TextField  SecondName;
    @FXML
    private TextField username;
    @FXML
    private PasswordField PassWord;
    public void clickOnCreateAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Parent root = loader.load();

        userName = username.getText();
        password = PassWord.getText();
        firstName=FirstName.getText();
        secondName=SecondName.getText();

        DataBase.users.add(new User(userName, password));



        if(userName.isEmpty()||password.isEmpty()||firstName.isEmpty()||secondName.isEmpty()){
            ErrorMessage.setText("Please enter all requires");
            return;}
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Register.fxml")));

        if (searchByUsername(userName)) {
            ErrorMessage.setText("Username already exists");
            return;
        }
        else {
            writeUserDataToCSV();
        }
        username.clear();
        PassWord.clear();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void writeUserDataToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("src/main/resources/com/example/stockfinal/users.csv", true))) {
            // Writing each user from the users ArrayList to the CSV file
            for (User user : DataBase.users) {
                String[] userData = {user.getUserName(), user.getPassword() , "User"};
                writer.writeNext(userData);
            }
            System.out.println("Data has been written to users.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}