package com.example.fxUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationPage extends Application {

    private static final boolean debug = true;
    private boolean allFieldsOK = false;
    private LinkedHashMap<String, String> userIDwithPassword = LoginPage.getUserIDwithPassword();
    private boolean registrationSuccess = false;

    @FXML
    private TextField kasutajanimi;
    @FXML
    private PasswordField parool;
    @FXML
    private TextField vanus;
    @FXML
    private TextField email;
    @FXML
    private TextField aadress;

    @FXML
    private CheckBox saast;
    @FXML
    private CheckBox partner;
    @FXML
    private CheckBox rimikaart;

    @FXML
    private Text kasutajaText;
    @FXML
    private Text paroolText;
    @FXML
    private Text vanusText;
    @FXML
    private Text emailText;
    @FXML
    private Text aadressText;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationPage.class.getResource("RegistrationPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("OO");
        stage.setScene(scene);
        stage.show();
    }

    public void tagasi(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("LoginPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            if (registrationSuccess) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Kasutaja registreeritud");
                alert.setHeaderText("Kasutaja " + kasutajanimi.getText() + " edukalt registreeritud");
                alert.setContentText("Võite sisse logida");
                registrationSuccess = false;
                alert.showAndWait();
                tagasi(event);
            } else {
                stage.setResizable(false);
                stage.setTitle("OO");
                stage.setScene(scene);
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        } catch (Exception e) {
            if (debug) System.out.println("[RegistrationPage] Exception: " + e.getClass().getSimpleName());
        }
    }

    public void mineTagasi(ActionEvent event) {
        if (debug) System.out.println("[RegistrationPage] Button pressed {Tagasi sisselogimise lehele}");
        tagasi(event);
    }

    public void looKonto(ActionEvent event) throws IOException {

        if (debug) System.out.println("[RegistrationPage] Button pressed {Loo konto}");

        boolean nimiOK = false;
        boolean paroolOK = false;
        boolean vanusOK = false;
        boolean emailOK = false;

        String nimi = kasutajanimi.getText();
        if (nimi.length() >= 3 && nimi.length() <= 18 && nimi.matches("[A-Za-z0-9]*") && !userIDwithPassword.containsKey(nimi)) {
            kasutajaText.setUnderline(false);
            nimiOK = true;
        } else {
            if (debug) System.out.println("[RegistrationPage] Probleem kasutajanimega");
            kasutajaText.setUnderline(true);
        }

        String salasona = parool.getText();
        if (salasona.length() >= 3 && !salasona.contains(":")) {
            paroolText.setUnderline(false);
            paroolOK = true;
        } else {
            if (debug) System.out.println("[RegistrationPage] Probleem parooliga");
            paroolText.setUnderline(true);
        }

        if (vanus.getText().matches("[0-9]+")) {
            int nrVanus = Integer.parseInt(vanus.getText());
            if (nrVanus >= 0) {
                vanusText.setUnderline(false);
                vanusOK = true;
            }
        } else {
            if (debug) System.out.println("[RegistrationPage] Probleem vanusega");
            vanusText.setUnderline(true);
        }

        String mail = email.getText();
        if (validate(mail)) {
            emailText.setUnderline(false);
            emailOK = true;
        } else {
            if (debug) System.out.println("[RegistrationPage] Probleem emailiga");
            emailText.setUnderline(true);
        }

        // TODO: location verification (might not implement if deemed too cumbersome)
        if (debug) System.out.println("[RegistrationPage] Aadress " + aadress.getText());
        aadressText.setUnderline(false);
        // ---

        allFieldsOK = nimiOK && paroolOK && vanusOK && emailOK;
        if (debug) System.out.println("[RegistrationPage] allFieldsOK: " + allFieldsOK);

        if (allFieldsOK) {

            // adding user to idsandpasswords.txt
            Files.write(Paths.get("data\\userdata\\idsandpasswords.txt"), ("\n" + nimi + ":" + salasona).getBytes(), StandardOpenOption.APPEND);

            // new directory for user
            if (debug) System.out.println("[RegistrationPage] Making new directory for user " + nimi);

            // new file for user
            String userPath = "data\\userdata\\" + nimi;
            String userFileName = nimi + "_info.txt";
            String userInfo = nimi + ":" + salasona + ":" + vanus.getText() + ":" + mail + ":" + "AADRESS" + ":" +
                    saast.isSelected() + ":" + partner.isSelected() + ":" + rimikaart.isSelected();
            new File(userPath).mkdirs();
            File userFile = new File(userPath + "\\" + userFileName);
            Files.write(Path.of(userPath + "\\" + userFileName), userInfo.getBytes());

            // updating user info hashmap
            userIDwithPassword = LoginPage.updateUserIDwithPassword();

            // displays success alert, back to start
            registrationSuccess = true;
            tagasi(event);
            System.out.println("Kasutaja " + nimi + " edukalt registreeritud!");

        } else {
            // user input is faulty, needs to recheck
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vigased kasutaja andmed");
            alert.setHeaderText("Sisestatud andmed on vigased, vaata üle allajoonitud väljad!");

            StringBuilder vead = new StringBuilder("");

            if (userIDwithPassword.containsKey(nimi))
                vead.append("• Valitud kasutajanimi on juba hõivatud, valige uus\n");

            if (!nimiOK)
                vead.append("• Kasutajanime ja parooli pikkus jäägu vahemikku 3-18 märki\n");

            if (!paroolOK)
                if (salasona.contains(":")) vead.append("• Parool ei tohi sisaldada sümbolit ':'\n");

            if (!vanusOK)
                vead.append("• Vanus peab olema positiivne täisarv\n");

            if (!emailOK)
                vead.append("• Vigane email\n");

            if (debug) System.out.println("[RegistrationPage]\n" + vead);
            alert.setContentText(String.valueOf(vead));
            alert.showAndWait();

        }
    }

    // email validation via https://stackoverflow.com/questions/8204680/java-regex-email
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}
