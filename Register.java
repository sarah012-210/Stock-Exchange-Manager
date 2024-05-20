package com.example.stockfinal;
import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Register extends Person implements Initializable {

    @FXML
    private TextField Username;

    @FXML
    private PasswordField Password;

    @FXML
    private Label errorLoginMessage;
    @FXML
    private TextField Type;
    @FXML
    private ChoiceBox<String> choiceBox;
    private final String[] optionsType = {"Admin","User" };
    private String type="";
    @FXML
    private Button windowButton;

    public void initialize(URL url, ResourceBundle rb) {
        choiceBox.getItems().addAll(optionsType);
        choiceBox.setOnAction(this::getOptionsType);

        windowButton.setOnAction(event -> openNewWindow());

    }
    public void getOptionsType(ActionEvent event) {
        String myOption = choiceBox.getValue();
        Type.setText(myOption);
       type=Type.getText();
    }

    public void ClickOnLogin(ActionEvent event) throws IOException {

        userName = Username.getText();
        password = Password.getText();

        if(!searchByUsername(userName)) {
            adminCSVWriter();
            return;
        }

        if ("User".equals(type)) {
            if (searchByUsername(userName)) {
                loadScene(event, "User.fxml");
            } else {
                errorLoginMessage.setText("Invalid username or password. Please try again.");
                return;
            }
        } else if ("Admin".equals(type)) {
            if (searchByUsername(userName)) {
                loadScene(event, "Admin.fxml");
            }
            else {
                errorLoginMessage.setText("Invalid username or password. Please try again.");
                return;
            }

        } else {
            errorLoginMessage.setText("Invalid username or password. Please try again.");
            return;
        }
    }

    public void clickOnSignUp(ActionEvent event) throws IOException {
        loadScene(event, "SignUp.fxml");
    }
    static Admin lyan = Admin.getInstance("Lyan" , "2003l" , "Lyan" , "Ahmed");
    public static void adminCSVWriter() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("src/main/resources/com/example/stockfinal/users.csv", true))) {
            String[] userData = {lyan.getUserName(), lyan.getPassword() , "Admin"};
            writer.writeNext(userData);

            System.out.println("Data has been written to users.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewWindow() {
        openNewWindow("Register.fxml");
    }
    public void openNewWindow(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();
            Stage newWindow = new Stage();
            newWindow.setTitle("TraderApplication");
            newWindow.setScene(new Scene(root));
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Register.class.getResource(fxmlFile)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

