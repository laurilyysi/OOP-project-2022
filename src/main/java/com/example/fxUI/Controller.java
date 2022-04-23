package com.example.fxUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {

    private static final boolean debug = true;

    private HashMap<String, String> userIDwithPassword = new HashMap<>();
    private static User currentUser;

    private Stage stage;
    private Scene scene;
    private FXMLLoader root;

    public void switchTo(ActionEvent event, String fxmlFile) {
        try {
            root = new FXMLLoader(getClass().getResource(fxmlFile));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            if (debug) System.out.println("Exception {switchTo}: " + e.getClass().getSimpleName());
        }
    }

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

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

}
