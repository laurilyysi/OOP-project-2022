package com.example.fxUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

// TODO: Figure out how to make the greeting more personalized, a la "Tere tulemast, <username>!"

public class MainMenuController extends Controller {

    @FXML
    private Text textWelcomeUser;
    @FXML
    private Button buttonOstunimekiri;
    @FXML
    private Button buttonMineOstlema;
    @FXML
    private Button buttonArvutaTee;
    @FXML
    private Button buttonKuvaProfiil;
    @FXML
    private Button buttonOstuajalugu;
    @FXML
    private Button buttonHaldaSopru;
    @FXML
    private Button buttonLogiValja;

    // ---

    public void clickButtonHaldaSopru(ActionEvent event) {

    }

    public void clickButtonLogiValja(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Logi välja");
        confirm.setHeaderText("Kas soovite välja logida?");
        confirm.showAndWait();

        switchToLoginPage(event);

        Alert loggedout = new Alert(Alert.AlertType.INFORMATION);
        loggedout.setTitle("Välja logitud");
        loggedout.setHeaderText("Edukalt välja logitud, kena päeva jätku!");
        loggedout.showAndWait();
    }

}
