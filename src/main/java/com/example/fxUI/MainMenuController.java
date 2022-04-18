package com.example.fxUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class MainMenuController extends Controller {

    public static final boolean debug = true;
    private final User user = getCurrentUser();

    // Main menu buttons
    @FXML private Text textWelcomeUser;
    @FXML private Button buttonOstunimekiri;
    @FXML private Button buttonMineOstlema;
    @FXML private Button buttonArvutaTee;
    @FXML private Button buttonKuvaProfiil;
    @FXML private Button buttonOstuajalugu;
    @FXML private Button buttonHaldaSopru;
    @FXML private Button buttonLogiValja;

    // Muuda ostunimekirja
    @FXML private TextArea textareaList;

    // Mine ostlema
    // Arvuta tee

    // Kuva profiil
    @FXML private PasswordField enterPassword;
    @FXML private TextField enterAge;
    @FXML private TextField enterEmail;
    @FXML private TextField enterLocation;
    @FXML private CheckBox saastukaart;
    @FXML private CheckBox partnerkaart;
    @FXML private CheckBox rimikaart;
    private String infoString = "";
    private boolean passwordOK = false;

    // Kuva ostuajalugu
    // Halda sõpru
    // Logi välja

    // <editor-fold desc="Ostunimekiri">
    public void clickButtonOstunimekiri(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Muuda ostunimekirja}");
        switchTo(event, "Muuda.fxml");
    }

    public void clickButtonOstunimekiriLaeFailist(ActionEvent event) {
        File userList = new File("data/userdata/" + user.getUsername() + "/" + user.getListFileName());
        textareaList.setText("");
        try (Scanner scan = new Scanner(new FileInputStream(userList))) {
            while (scan.hasNextLine()) {
                String item = scan.nextLine();
                textareaList.appendText(item + "\n");
            }
        } catch (Exception e) {
            if (debug) System.out.println("[Ostunimekiri] Exception " + e.getClass().getSimpleName());
        }
    }

    // TODO: perhaps remind the user to save the file before going back to main menu
    //  (if they haven't done so already)
    public void clickButtonOstunimekiriMineTagasi(ActionEvent event) {
        File userList = new File("data/userdata/" + user.getUsername() + "/" + user.getListFileName());

        try (Scanner scan = new Scanner(new FileInputStream(userList))) {
            user.clearList();
            while (scan.hasNextLine()) {
                String item = scan.nextLine();
                user.addToList(item);
            }

            switchTo(event, "MainMenu.fxml");
        } catch (Exception e) {
            System.out.println("exc");
        }
    }

    public void clickButtonOstunimekiriSalvesta(ActionEvent event) {
        String enteredText = textareaList.getText();
        String[] items = enteredText.split("\n");

        File userList = new File("data/userdata/" + user.getUsername() + "/" + user.getListFileName());

        try (FileOutputStream fos = new FileOutputStream(userList)) {
            for (String item : items) {
                fos.write(item.getBytes());
                fos.write("\n".getBytes());
            }
        } catch (Exception e) {
            System.out.println("exc");
        }

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Salvestatud");
        success.setHeaderText("Ostunimekiri salvestatud");
        success.showAndWait();

    }
    // </editor-fold>

    // <editor-fold desc="Mine ostlema">
    public void clickButtonMineOstlema(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Mine ostlema}");
    }
    // </editor-fold>

    // <editor-fold desc="Arvuta tee">
    public void clickButtonArvutaTee(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Arvuta tee}");
    }
    // </editor-fold>

    // <editor-fold desc="Kuva profiil">
    public void clickButtonKuvaProfiil(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Kuva profiil}");
        switchTo(event, "Profiil.fxml");
    }

    public void clickButtonKuvaProfiilMineTagasi(ActionEvent event) {
        switchTo(event, "MainMenu.fxml");
    }
    // </editor-fold>

    // <editor-fold desc="Ostuajalugu">
    public void clickButtonOstuajalugu(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Ostuajalugu}");
    }
    // </editor-fold>

    // <editor-fold desc="Halda sõpru">
    public void clickButtonHaldaSopru(ActionEvent event) {
        if (debug) System.out.println("[MainMenu] Pressed button {Halda sõpru}");
    }
    // </editor-fold>

    // <editor-fold desc="Ostunimekiri">
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
    // </editor-fold>

}
