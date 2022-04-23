package com.example.fxUI;

import User.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class LoginPage extends Application {

    private static final boolean debug = true;
    private static LinkedHashMap<String, String> userIDwithPassword = new LinkedHashMap<>();

    @FXML
    private TextField enteredUser;
    @FXML
    private PasswordField enteredPassword;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        userIDwithPassword = updateUserIDwithPassword();

        stage.setResizable(false);
        stage.setTitle("OO");
        stage.setScene(scene);
        stage.show();
    }

    public void logiSisse(ActionEvent event) throws IOException {
        if (debug) System.out.println("[LoginPage] Button pressed {Logi sisse}");

        String username = enteredUser.getText();
        String password = enteredPassword.getText();

        if (userIDwithPassword.containsKey(username) && userIDwithPassword.get(username).equals(password)) {
            if (debug) {
                System.out.println("[LoginPage] Correct credentials");

                // TODO: "\\" as a separator will probably not work on platforms other
                //  than Windows, consider changing this to something more universal

                String userFilePath = "data\\userdata\\" + username + "\\" + username+"_info.txt";
                String[] userInfo = String.valueOf(Files.readAllLines(Paths.get(userFilePath))).split(":");

                User user = new User(userInfo[0], userInfo[1], Integer.parseInt(userInfo[2]), userInfo[3], userInfo[4],
                        Boolean.parseBoolean(userInfo[5]), Boolean.parseBoolean(userInfo[6]), Boolean.parseBoolean(userInfo[7]));

                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("MainMenu.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();

                    stage.setResizable(false);
                    stage.setTitle("OO");
                    stage.setScene(scene);
                    stage.show();

                    ((Node)(event.getSource())).getScene().getWindow().hide();

                } catch (Exception e) {
                    System.out.println("Exception: " + e.getClass().getSimpleName());
                }

            }
        } else {
            if (debug) System.out.println("[LoginPage] Wrong credentials, try again!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Viga");
            alert.setHeaderText("Vale kasutajanimi või parool!");
            alert.showAndWait();
        }
    }

    public void registreeri(ActionEvent event) {
        if (debug) System.out.println("[LoginPage] Button pressed {Pole kontot? Registreeru}");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(RegistrationPage.class.getResource("RegistrationPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            stage.setResizable(false);
            stage.setTitle("OO");
            stage.setScene(scene);
            stage.show();

            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            if (debug) System.out.println("[LoginPage] Exception: " + e.getClass().getSimpleName());
        }
    }

    public static LinkedHashMap<String, String> updateUserIDwithPassword() throws FileNotFoundException {
        // TODO: this method breaks if idsandpasswords.txt is empty
        try (Scanner scan = new Scanner(new FileInputStream("data\\userdata\\idsandpasswords.txt"))) {
            while (scan.hasNextLine()) {
                String[] userinfo = scan.nextLine().split(":");
                userIDwithPassword.put(userinfo[0], userinfo[1]);
            }
        }
        return userIDwithPassword;
    }

    public static LinkedHashMap<String, String> getUserIDwithPassword() {
        return userIDwithPassword;
    }

}
