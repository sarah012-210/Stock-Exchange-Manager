package com.example.stockfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.function.Consumer;

public class controllerUpdateUser {

    @FXML
    private TextField FirstName;
    @FXML
    private TextField SecondName;
    @FXML
    private TextField UserName;
    @FXML
    private PasswordField Password;
    @FXML
    private Button Back;
    private Consumer<User> onUpdateUser;

    private User userToUpdate;

    public void initData(User user) {
        this.userToUpdate = user;
        FirstName.setText(user.getFirstName());
        SecondName.setText(user.getSecondName());
        UserName.setText(user.getUserName());
        Password.setText(user.getPassword());
    }

    @FXML
    private void clickOnUpdate(ActionEvent event) {
        String firstName = FirstName.getText();
        String secondName = SecondName.getText();
        String userName = UserName.getText();
        String password = Password.getText();

        userToUpdate.setFirstName(firstName);
        userToUpdate.setSecondName(secondName);
        userToUpdate.setUserName(userName);
        userToUpdate.setPassword(password);

        if (onUpdateUser != null) {
            onUpdateUser.accept(userToUpdate);
        }

        clickOnBack(event); // Navigate back after updating
    }

    public void setOnUpdateUser(Consumer<User> onUpdateUser) {
        this.onUpdateUser = onUpdateUser;
    }

    @FXML
    private void clickOnBack(ActionEvent event) {
        try {
            Register.loadScene(event, "userManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
