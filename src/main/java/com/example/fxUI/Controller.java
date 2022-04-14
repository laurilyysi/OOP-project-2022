package com.example.fxUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
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

    public void switchTo(ActionEvent event, String fxmlFile) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            if (debug) System.out.println("Exception {switchTo}: " + e.getClass().getSimpleName());
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

    // ---
    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Parent getRoot() {
        return root;
    }
    // ---

}
