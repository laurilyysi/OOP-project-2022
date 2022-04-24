package com.example.fxUI;

import POC.Product;
import POC.SearchOptions;
import POC.StoreName;
import POC.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static POC.StoreName.*;

public class OptionsController extends Controller {

    @FXML CheckBox checkCoop;
    @FXML CheckBox checkMaxima;
    @FXML CheckBox checkPrisma;
    @FXML CheckBox checkRimi;
    @FXML CheckBox checkSelver;

    @FXML TextField enterAddress;

    private static ArrayList<Product> cheapestProducts = new ArrayList<>();
    private User user = getCurrentUser();
    private String address = user.getLocation();

    @FXML Button buttonSaveAddress;
    @FXML Button buttonUseExistingAddress;
    @FXML Button buttonBeginSearch;
    @FXML Button buttonOptionsGoBack;

    public static void addToCheapestProductsArrayList(Product product) {
        cheapestProducts.add(product);
    }

    public synchronized void clickButtonBeginSearch(ActionEvent event) throws InterruptedException {

        File userList = new File("data/userdata/" + user.getUsername() + "/" + user.getListFileName());

        try (Scanner scan = new Scanner(new FileInputStream(userList))) {
            user.clearList();
            while (scan.hasNextLine()) {
                String item = scan.nextLine();
                user.addToList(item);
            }
        } catch (FileNotFoundException ignored) {}

        ArrayList<String> shoppingList = user.getShoppinglist();
        new SearchOptions(shoppingList,
                checkCoop.isSelected(),
                checkMaxima.isSelected(),
                checkPrisma.isSelected(),
                checkRimi.isSelected(),
                checkSelver.isSelected(),
                address);

        List<StoreName> storeNames = SearchOptions.getStores();
        ArrayList<Thread> listOfWorkers = new ArrayList<>();

        int freeProcessors = Runtime.getRuntime().availableProcessors();

        for (String item : shoppingList) {
            for (StoreName storeName : storeNames) {
                if (listOfWorkers.size() > freeProcessors) {
                    for (Thread worker : listOfWorkers) {
                        worker.join();
                        listOfWorkers.remove(worker);
                    }
                }
                if (listOfWorkers.size() <= freeProcessors) {
                    Thread worker = new Thread(new Worker(storeName, item));
                    listOfWorkers.add(worker);
                    worker.start();
                }
            }
        }
        for (Thread worker : listOfWorkers) {
            worker.join();
            listOfWorkers.remove(worker);
        }

        for (Product item : cheapestProducts) {
            System.out.println(item.toString());
        }

        // TODO: 4/24/2022 Marek - fix synchronization issue in multithreading(not showing what items are in cheapestproducts and im not even sure if thing are in cheapestproducts)

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
