package com.example.fxUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Controller {

    // FOR ALL FXML FILES
    // this class needs to be added as a controller class

    private static final boolean debug = true;

    private HashMap<String, String> userIDwithPassword = new HashMap<>();
    private static User currentUser;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToLoginPage(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginPage.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            if (debug) System.out.println("Exception {switchToLoginPage}: " + e.getClass().getSimpleName());
        }
    }

    public void switchToRegistrationPage(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegistrationPage.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            if (debug) System.out.println("Exception {switchToRegistrationPage}: " + e.getClass().getSimpleName());
        }
    }

    public void switchToMainMenu(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            if (debug) System.out.println("Exception {switchToMainMenu}: " + e.getClass().getSimpleName());
        }
    }

    // ----

    public HashMap<String, String> updateUserIDwithPassword() throws FileNotFoundException {
        try (Scanner scan = new Scanner(new FileInputStream("data\\userdata\\idsandpasswords.txt"))) {
            while (scan.hasNextLine()) {
                String[] userinfo = scan.nextLine().split(":");
                userIDwithPassword.put(userinfo[0], userinfo[1]);
            }
        }
        return userIDwithPassword;
    }

    public HashMap<String, String> getUserIDwithPassword() {
        return userIDwithPassword;
    }

    public static void setCurrentUser(User currentUser) {
        Controller.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static String getCurrentUserName() {
        return currentUser.getUsername();
    }

}
