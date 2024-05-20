package com.example.stockfinal;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class controllerUserManagement implements Initializable {
    @FXML
    public Label searchMessage;
    @FXML
    public Label errorMessage;
    @FXML
    public Button Back;
    @FXML
    private  TableView<User> usersTableView = new TableView<>();
    @FXML
    private TextField FirstName;
    @FXML
    private TextField SecondName;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField searchField;
    @FXML
    private ListView searchResult;

    private static ObservableList<User> users = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {

        TableColumn<User,String> nameColumn = new TableColumn<>("Name");
        TableColumn<User,String> passwordColumn = new TableColumn<>("Password");
        TableColumn<User,String> typeColumn = new TableColumn<>("Type");

        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        passwordColumn.setCellValueFactory(data -> data.getValue().passwordProperty());
        typeColumn.setCellValueFactory(data -> data.getValue().typeProperty());
        usersTableView.getColumns().addAll(nameColumn, passwordColumn, typeColumn);
        readUsersFromCSV();
        usersTableView.setItems(users);


    }
    private void  readUsersFromCSV() {
        try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/com/example/stockfinal/users.csv"))) {
            csvReader.readNext();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                String name = nextRecord[0];
                String password = nextRecord[1];
                String type = nextRecord[2];
                users.add(new User(name, password, "User"));
            }
        } catch (IOException | NumberFormatException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openCreateUser(ActionEvent event) {
        try {
            Register.loadScene(event, "addUser.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openUpdateUser(ActionEvent event) {
        User selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateUser.fxml"));
                Parent root = loader.load();

                // Get the controller associated with the loaded FXML
                controllerUpdateUser controller = loader.getController();

                // Set the callback function to handle updated user
                controller.setOnUpdateUser(updatedUser -> {
                    // Handle the updated user and update the list and CSV file
                    updateUser(updatedUser);
                    // Refresh table view
                    usersTableView.refresh();
                });

                // Pass the selected user
                controller.initData(selectedUser);

                // Load the scene
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorMessage.setText("Please select a user to update");
        }
    }
    @FXML
    private void openRetrieveUser(ActionEvent event) {
        // Get selected user from table view
        User selectedUser = usersTableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("retriveUser.fxml"));
                Parent root = loader.load();

                // Get the controller associated with the loaded FXML
                controllerRetriveUser controller = loader.getController();

                // Pass the selected user to controllerRetriveUser
                controller.initData(selectedUser);

                // Load the scene
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Handle case where no user is selected
            errorMessage.setText("Please select a user to retrieve.");
        }
    }



    @FXML
    private void createUser(ActionEvent event) {
        // Get input from text fields or other UI components
        String name = nameTextField.getText(); // Assuming you have a nameTextField
        String password = passwordTextField.getText(); // Assuming you have a passwordTextField
        String firstName =  FirstName.getText();
        String secondName =  SecondName.getText();
        String type ="User";

        // Add the new user to the list
        users.add(new User(name, password, type));
        // Refresh table view
        usersTableView.setItems(users);
    }
    public void updateUser(User updatedUser) {
        // Find the index of the updated user in the list
        int index = users.indexOf(updatedUser);

        if (index != -1) {
            // Update the user in the list
            users.set(index, updatedUser);

            // Update the user in the CSV file
            writeAllUsersToCSV(users);
        }
    }
    @FXML
    private void deleteUser(ActionEvent event) {
        // Get selected user from table view
        User selectedUser = usersTableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Remove the user from the list
            users.remove(selectedUser);

            // Refresh table view
            writeAllUsersToCSV(users);
            usersTableView.setItems(users);
        }
    }
    public void clickOnBack(ActionEvent event) throws IOException {
        try {
            Register.loadScene(event, "Admin.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to write all users to the CSV file
    private void writeAllUsersToCSV(List<User> allUsers) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("src/main/resources/com/example/stockfinal/users.csv"))) {
            // Write header
            String[] header = {"Name", "Password", "Type"};
            csvWriter.writeNext(header);
            // Write each user record
            for (User user : allUsers) {
                String[] record = {user.getUserName(), user.getPassword(), "User"};
                csvWriter.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

