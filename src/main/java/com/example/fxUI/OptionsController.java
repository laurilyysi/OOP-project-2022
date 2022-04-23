package com.example.fxUI;

import POC.SearchOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OptionsController extends Controller {

    @FXML CheckBox checkCoop;
    @FXML CheckBox checkMaxima;
    @FXML CheckBox checkPrisma;
    @FXML CheckBox checkRimi;
    @FXML CheckBox checkSelver;

    @FXML TextField enterAddress;

    private User user = getCurrentUser();
    private String address = user.getLocation();

    @FXML Button buttonSaveAddress;
    @FXML Button buttonUseExistingAddress;
    @FXML Button buttonBeginSearch;
    @FXML Button buttonOptionsGoBack;

    public void clickButtonBeginSearch(ActionEvent event) {

        File userList = new File("data/userdata/" + user.getUsername() + "/" + user.getListFileName());

        try (Scanner scan = new Scanner(new FileInputStream(userList))) {
            user.clearList();
            while (scan.hasNextLine()) {
                String item = scan.nextLine();
                user.addToList(item);
            }
        } catch (FileNotFoundException ignored) {}

        SearchOptions options = new SearchOptions(user.getShoppinglist(),
                checkCoop.isSelected(),
                checkMaxima.isSelected(),
                checkPrisma.isSelected(),
                checkRimi.isSelected(),
                checkSelver.isSelected(),
                address);

        System.out.println(options);

        // -- begin multithread --
        // vb seda searchoptions pole vajagi kui sa teed multithreadimist siin
        // aga ilmselt on seda mugavam teha seal POC packages

    }

    public void clickButtonInsertNewAddress(ActionEvent event) {

        address = enterAddress.getText();

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Aadress");
        info.setHeaderText("Aadress uuendatud, otsimisel kasutatakse aadressi: " + enterAddress.getText());
        info.showAndWait();

    }

    public void clickButtonUseExistingAddress(ActionEvent event) {
        enterAddress.setText(user.getLocation());
        address = user.getLocation();
    }

    public void clickButtonOptionsGoBack(ActionEvent event) {
        switchTo(event, "MainMenu.fxml");
    }

}
