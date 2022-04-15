package com.example.fxUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class LoginPageController extends Controller {

    private static final boolean debug = true;
    private HashMap<String, String> userIDwithPassword = getUserIDwithPassword();
    private User currentUser;

    @FXML
    private TextField enterUsername;
    @FXML
    private PasswordField enterPassword;

    public void clickButtonLogiSisse(ActionEvent event) throws IOException {
        if (debug) System.out.println("[LoginPage] Button pressed {Logi sisse}");

        userIDwithPassword = updateUserIDwithPassword();

        String username = enterUsername.getText();
        String password = enterPassword.getText();

        if (validLogin(username, password)) {

            if (debug) System.out.println("[LoginPage] Correct credentials");

            String userInfoFileName = username + "_info.txt";
            String userFilePath = "data\\userdata\\" + username + "\\" + userInfoFileName;
            String[] userInfo = Files.readAllLines(Paths.get(userFilePath)).get(0).split(":");

            currentUser = new User(userInfo[0], userInfo[1], Integer.parseInt(userInfo[2]), userInfo[3], userInfo[4],
                    Boolean.parseBoolean(userInfo[5]),
                    Boolean.parseBoolean(userInfo[6]),
                    Boolean.parseBoolean(userInfo[7]));

            Controller.setCurrentUser(currentUser);
            switchTo(event, "MainMenu.fxml");
            displayLoginSuccess(event);

        } else {
            if (debug) System.out.println("[LoginPage] Wrong credentials");
            displayLoginError();
        }

    }

    public void clickButtonNoAccount(ActionEvent event) {
        switchTo(event, "RegistrationPage.fxml");
    }

    public boolean validLogin(String username, String password) {
        return userIDwithPassword.containsKey(username) && userIDwithPassword.get(username).equals(password);
    }

    public void displayLoginSuccess(ActionEvent event) {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Sisse logitud");
        success.setHeaderText("Kasutaja " + enterUsername.getText() + " edukalt sisse logitud!");
        success.showAndWait();
    }

    public void displayLoginError() {
        Alert loginError = new Alert(Alert.AlertType.ERROR);
        loginError.setTitle("Viga");
        loginError.setHeaderText("Viga sisselogimisel, kontrolli üle kasutajanimi ja parool");
        loginError.showAndWait();
    }

}
