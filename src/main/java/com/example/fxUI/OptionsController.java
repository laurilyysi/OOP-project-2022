package com.example.fxUI;

import POC.Product;
import POC.SearchOptions;
import POC.StoreName;
import POC.Worker;
import Tee.Location;
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
import static Tee.Test.*;

public class OptionsController extends Controller {

    @FXML CheckBox checkCoop;
    @FXML CheckBox checkMaxima;
    @FXML CheckBox checkPrisma;
    @FXML CheckBox checkRimi;
    @FXML CheckBox checkSelver;

    @FXML TextField enterAddress;

    final private static ArrayList<Product> cheapestProducts = new ArrayList<>();
    final private User user = getCurrentUser();
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
        int listOfWorkersSize;

        for (String s : shoppingList) {
            for (StoreName storeName : storeNames) {
                listOfWorkersSize = listOfWorkers.size();
                if (listOfWorkersSize > freeProcessors) {
                    for (int k = 0; k < listOfWorkersSize; k++) {
                        listOfWorkers.get(k).join();
                    }
                    listOfWorkers.clear();
                }
                if (listOfWorkers.size() <= freeProcessors) {
                    Thread worker = new Thread(new Worker(storeName, s));
                    listOfWorkers.add(worker);
                    worker.start();
                }
            }
        }
        for (int i = 0; i < listOfWorkers.size();) {
            listOfWorkers.get(i).join();
        }


        for (Product item : cheapestProducts) {
            System.out.println(item.toString());
        }

        // TODO: 4/24/2022 Marek - fix synchronization issue in multithreading(not showing what items are in cheapestproducts and im not even sure if thing are in cheapestproducts)

    }


    // hetkel ainult koordinaatidega
    public void clickButtonInsertNewAddress(ActionEvent event) {

        address = enterAddress.getText();

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Aadress");
        info.setHeaderText("Aadress uuendatud, otsimisel kasutatakse aadressi: " + enterAddress.getText());
        info.showAndWait();

        shortestPath(address);

    }

    public void clickButtonUseExistingAddress(ActionEvent event) {
        enterAddress.setText(user.getLocation());
        address = user.getLocation();

        shortestPath(address);

    }

    public void shortestPath(String address) {

        if (validCoordinates(address)) {
            String[] coords = address.split(", ");

            double lat = Double.parseDouble(coords[0].substring(0, 5));
            double lon = Double.parseDouble(coords[1].substring(0, 5));

            StringBuilder searchStores = new StringBuilder();

            if (checkCoop.isSelected()) searchStores.append("C");
            if (checkMaxima.isSelected()) searchStores.append("M");
            if (checkPrisma.isSelected()) searchStores.append("P");
            if (checkRimi.isSelected()) searchStores.append("R");
            if (checkSelver.isSelected()) searchStores.append("S");

            paths.clear();

            findShortestPaths(lat, lon, searchStores.toString());

            String shortest = String.valueOf(paths.get(0));
            String otheroptions = "";

            if (paths.size() >= 3) otheroptions = paths.get(1) + " \n" + paths.get(2);

            Alert result = new Alert(Alert.AlertType.INFORMATION);
            result.setTitle("Lühim tee");
            result.setHeaderText("Lühim tee linnulennult on " + shortest);
            if (!otheroptions.equals("")) result.setContentText("Sobivad ka:\n\n" + otheroptions);
            result.showAndWait();

        }

        else {
            System.out.println("Invalid coordinates");
        }

    }

    public void clickButtonOptionsGoBack(ActionEvent event) {
        switchTo(event, "MainMenu.fxml");
    }

}
