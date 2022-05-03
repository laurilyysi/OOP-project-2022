package com.example.GUI;

import POC.*;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.GUI.GUI.*;

public class GUIbuttons {

    public static User login(String username, String password) {

        if (validLogin(username, password)) {

            System.out.println("correct");

            String userInfoFileName = username + "_info.txt";
            String userFilePath = "data\\userdata\\" + username + "\\" + userInfoFileName;

            try {
                String[] userInfo = Files.readAllLines(Paths.get(userFilePath)).get(0).split(":");

                User user = new User(userInfo[0], userInfo[1], Integer.parseInt(userInfo[2]), userInfo[3], userInfo[4],
                        Boolean.parseBoolean(userInfo[5]),
                        Boolean.parseBoolean(userInfo[6]),
                        Boolean.parseBoolean(userInfo[7]));

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Sisse logitud");
                success.setHeaderText("Kasutaja " + user.getUsername() + " edukalt sisse logitud!");
                success.showAndWait();

                GUI.getScene().setRoot(mainMenuPage());

                return user;

            } catch (IOException ignored) {}

        } else System.out.println("false");

        return null;

    }

    public static void createNewUser(String username, String password, String age, String email, String location,
                              boolean ownsSaastukaart, boolean ownsPartnerkaart, boolean ownsRimikaart) {

        try {
            String userInfo = username + ":" + password + ":" + age + ":" + email + ":" + location + ":" +
                    ownsSaastukaart + ":" + ownsPartnerkaart + ":" + ownsRimikaart;

            Files.write(Paths.get("data\\userdata\\idsandpasswords.txt"),
                    (username + ":" + password + "\n").getBytes(), StandardOpenOption.APPEND);

            String userPath = "data\\userdata\\" + username;
            String userInfoFile = username + "_info.txt";
            new File(userPath).mkdirs();
            Files.write(Path.of(userPath + "\\" + userInfoFile), userInfo.getBytes());

            Files.write(Path.of(userPath + "\\" + username + "_list.txt"), "".getBytes());

            System.out.println("Successful registration");

        } catch (IOException ignored) {}

    }

    public static ArrayList<HashMap<String, ArrayList<Product>>>
    beginSearch(List<String> shoppinglist,
                boolean checkCoop, boolean checkMaxima, boolean checkPrisma, boolean checkRimi, boolean checkSelver) {

        ArrayList<HashMap<String, ArrayList<Product>>> result = new ArrayList<>();

        for (String name : shoppinglist) {

            HashMap<String, ArrayList<Product>> map = new HashMap<>();
            map.put(name, new ArrayList<>());

            if (checkCoop) map.get(name).addAll(Coop.searchProducts(name));
            if (checkMaxima) map.get(name).addAll(Maxima.searchProducts(name));
            if (checkPrisma) map.get(name).addAll(Prisma.searchProducts(name));
            if (checkRimi) map.get(name).addAll(Rimi.searchProducts(name));
            if (checkSelver) map.get(name).addAll(Selver.searchProducts(name));

            result.add(map);

        }

        return result;

    }

}
