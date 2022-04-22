package com.example.fxUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationPageController extends Controller {

    private static final boolean debug = true;

    private HashMap<String, String> userIDwithPassword = new HashMap<>();

    @FXML private TextField enterUsername;
    @FXML private PasswordField enterPassword;
    @FXML private TextField enterAge;
    @FXML private TextField enterEmail;
    @FXML private TextField enterLocation;
    @FXML private CheckBox saastukaart;
    @FXML private CheckBox partnerkaart;
    @FXML private CheckBox rimikaart;

    @FXML private Text textUsername;
    @FXML private Text textPassword;
    @FXML private Text textAge;
    @FXML private Text textEmail;

    public void clickButtonRegistreeru(ActionEvent event) throws IOException {

        if (debug) System.out.println("[RegistrationPage] Button pressed {Loo konto}");
        userIDwithPassword = updateUserIDwithPassword();

        boolean usernameOK = validUsername(enterUsername.getText());
        boolean passwordOK = validPassword(enterPassword.getText());
        boolean ageOK = validAge(enterAge.getText());
        boolean emailOK = validEmail(enterEmail.getText());

        boolean allValid = usernameOK && passwordOK && ageOK && emailOK;

        if (allValid) {
            userIDwithPassword = updateUserIDwithPassword();
            createNewUser();
            displaySuccessAlert();
            switchTo(event, "LoginPage.fxml");
        } else {
            // displays error alert

            StringBuilder errors = new StringBuilder("");

            if (!usernameOK) errors.append("Vigane kasutajanimi\n");
            else textUsername.setUnderline(false);

            if (!passwordOK) errors.append("Vigane parool\n");
            else textPassword.setUnderline(false);

            if (!ageOK) errors.append("Vigane vanus\n");
            else textAge.setUnderline(false);

            if (!emailOK) errors.append("Vigane email\n");
            else textEmail.setUnderline(false);

            Alert failed = new Alert(Alert.AlertType.ERROR);
            failed.setTitle("Viga");
            failed.setHeaderText("Registreerimine ebaõnnestus, palun kontrolli üle");
            failed.setContentText(String.valueOf(errors));
            failed.showAndWait();
        }

    }

    public void clickButtonMineTagasi(ActionEvent event) {
        switchTo(event, "LoginPage.fxml");
    }

    public boolean validUsername(String username) {
        boolean validLength = username.length() >= 3 && username.length() <= 18;
        boolean validCharacters = username.matches("[A-Za-z0-9]*");
        boolean doesntAlreadyExist = !userIDwithPassword.containsKey(username);

        textUsername.setUnderline(!(validLength && validCharacters && doesntAlreadyExist));
        return validLength && validCharacters && doesntAlreadyExist;
    }

    public boolean validPassword(String password) {
        boolean validLength = password.length() >= 3;
        boolean doesntContainColon = !password.contains(":");

        textPassword.setUnderline(!(validLength && doesntContainColon));
        return validLength && doesntContainColon;
    }

    public boolean validAge(String age) {
        try {
            return Integer.parseInt(age) >= 0;
        } catch (NumberFormatException e) {
            textAge.setUnderline(true);
            return false;
        }
    }

    // https://stackoverflow.com/questions/8204680/java-regex-email
    public boolean validEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        boolean result = matcher.find();
        textEmail.setUnderline(!result);
        return result;
    }

    public void createNewUser() throws IOException {

        String username = enterUsername.getText();
        String password = enterPassword.getText();
        String age = enterAge.getText();
        String email = enterEmail.getText();
        String location = "AADRESS"; // TODO: change this
        boolean ownsSaastukaart = saastukaart.isSelected();
        boolean ownsPartnerkaart = partnerkaart.isSelected();
        boolean ownsRimikaart = rimikaart.isSelected();

        String userInfo = username + ":" +
                password + ":" +
                age + ":" +
                email + ":" +
                location + ":" +
                ownsSaastukaart + ":" +
                ownsPartnerkaart + ":" +
                ownsRimikaart;

        // adding new user to idsandpasswords.txt
        Files.write(Paths.get("data\\userdata\\idsandpasswords.txt"),
                (username + ":" + password + "\n").getBytes(), StandardOpenOption.APPEND);

        // new directory for user
        String userPath = "data\\userdata\\" + username;
        String userInfoFile = username + "_info.txt";
        new File(userPath).mkdirs();
        Files.write(Path.of(userPath + "\\" + userInfoFile), userInfo.getBytes());

        // list for user
        Files.write(Path.of(userPath + "\\" + username + "_list.txt"), "".getBytes());

        System.out.println("Successful registration");

    }

    public void displaySuccessAlert() {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Kasutaja registreeritud");
        success.setHeaderText("Kasutaja " + enterUsername.getText() + " edukalt registreeritud");
        success.setContentText("Võite sisse logida");
        success.showAndWait();
    }

}
