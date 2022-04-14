package com.example.fxUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import java.util.Optional;

public class MainMenuController extends Controller {

    public static final boolean debug = true;

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

    public void clickButtonOstunimekiri(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Muuda ostunimekirja}");
    }

    public void clickButtonMineOstlema(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Mine ostlema}");
    }

    public void clickButtonArvutaTee(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Arvuta tee}");
    }

    public void clickButtonKuvaProfiil(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Kuva profiil}");
    }

    public void clickButtonOstuajalugu(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Ostuajalugu}");
    }

    public void clickButtonHaldaSopru(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Halda sõpru}");
    }

    public void clickButtonLogiValja(ActionEvent event) {

        if (debug) System.out.println("[MainMenu] Pressed button {Logi välja}");

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Logi välja");
        confirm.setHeaderText("Kas soovite välja logida?");

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.get() == ButtonType.OK) {
            switchTo(event, "LoginPage.fxml");
            Alert loggedout = new Alert(Alert.AlertType.INFORMATION);
            loggedout.setTitle("Välja logitud");
            loggedout.setHeaderText("Edukalt välja logitud, kena päeva jätku!");
            loggedout.showAndWait();
        } else {
            confirm.close();
        }

    }

}
