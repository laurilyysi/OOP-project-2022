package com.example.fxUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private static final boolean debug = true;

    @FXML
    private Button buttonOstunimekiri;
    @FXML
    private Button buttonOstle;
    @FXML
    private Button buttonTee;
    @FXML
    private Button buttonProfiil;
    @FXML
    private Button buttonAjalugu;
    @FXML
    private Button buttonSobrad;
    @FXML
    private Button buttonBlokeeri;
    @FXML
    private Button buttonValju;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationPage.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("OO");
        stage.setScene(scene);
        stage.show();
    }

    public void ostunimekiri(ActionEvent e) {
        if (debug) System.out.println("[MainMenu] Button pressed {Muuda ostunimekirja}");
    }

    public void ostle(ActionEvent e) {
        if (debug) System.out.println("[MainMenu] Button pressed {Mine ostlema}");
    }

    public void arvutaTee(ActionEvent e) {
        if (debug) System.out.println("[MainMenu] Button pressed {Arvuta tee}");
    }

    public void profiil(ActionEvent e) {
        if (debug) System.out.println("[MainMenu] Button pressed {Kuva profiil}");
    }

    public void ostuAjalugu(ActionEvent e) {
        if (debug) System.out.println("[MainMenu] Button pressed {Kuva ostuajalugu}");
    }

    public void sobrad(ActionEvent e) {
        if (debug) System.out.println("[MainMenu] Button pressed {Halda sõpru}");
    }

    public void logiValja(ActionEvent e) {
        if (debug) System.out.println("[MainMenu] Button pressed {Logi välja}");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logi välja?");
        alert.setHeaderText("Kas soovid välja logida?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            tagasi(e);
            Alert cya = new Alert(Alert.AlertType.INFORMATION);
            cya.setTitle("Logisite välja");
            cya.setHeaderText("Edukalt välja logitud! Head päeva jätku!");
            cya.showAndWait();
        }
    }

    // ---

    public void tagasi(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("LoginPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            stage.setResizable(false);
            stage.setTitle("OO");
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            if (debug) System.out.println("[RegistrationPage] Exception: " + e.getClass().getSimpleName());
        }
    }

}
