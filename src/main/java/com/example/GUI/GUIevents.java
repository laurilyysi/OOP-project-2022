package com.example.GUI;

import POC.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.GUI.GUI.*;

public class GUIevents {

    public static boolean validUsername(String username) {
        boolean validLength = username.length() >= 3 && username.length() <= 18;
        boolean validCharacters = username.matches("[A-Za-z0-9]*");
        boolean doesntAlreadyExist = !userIDwithPassword.containsKey(username);
        return validLength && validCharacters && doesntAlreadyExist;
    }

    public static boolean validPassword(String password) {
        boolean validLength = password.length() >= 3;
        boolean doesntContainColon = !password.contains(":");
        return validLength && doesntContainColon;
    }

    public static boolean validAge(String age) {
        try {
            return Integer.parseInt(age) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validCoordinates(String coords) {
        Pattern regex = Pattern.compile("^(-?\\d+(\\.\\d+)?),\\s*(-?\\d+(\\.\\d+)?)$");
        Matcher matcher = regex.matcher(coords);
        return matcher.find();
    }

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
                        Boolean.parseBoolean(userInfo[7]),
                        Integer.parseInt(userInfo[8]), Integer.parseInt(userInfo[9]), Double.parseDouble(userInfo[10]),
                        Double.parseDouble(userInfo[11]), userInfo[12]);

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Sisse logitud");
                success.setHeaderText("Kasutaja " + user.getUsername() + " edukalt sisse logitud!");
                success.showAndWait();

                GUI.getScene().setRoot(mainMenuPage());

                return user;

            } catch (IOException ignored) {}

        } else {
            System.out.println("false");

            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Sisselogimine ebaõnnestus");
            fail.setHeaderText("Sisselogimine ebaõnnestus. Vaata üle kasutajanimi ja parool.");
            fail.showAndWait();

        }

        return null;

    }

    public static void createNewUser(String username, String password, String age, String email, String location,
                              boolean ownsSaastukaart, boolean ownsPartnerkaart, boolean ownsRimikaart) {

        try {
            String userInfo = username + ":" + password + ":" + age + ":" + email + ":" + location + ":" +
                    ownsSaastukaart + ":" + ownsPartnerkaart + ":" + ownsRimikaart + ":0:0:0:0:null";

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

    public static void alertRegistrationSuccess(String name) {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Kasutaja registreeritud");
        success.setHeaderText("Kasutaja " + name + " edukalt registreeritud");
        success.setContentText("Võite sisse logida");
        success.showAndWait();
    }

    public static void alertRegistrationFailure() {
        Alert fail = new Alert(Alert.AlertType.ERROR);
        fail.setTitle("Registreerumine ebaõnnestus");
        fail.setHeaderText("Registreerumine ebaõnnestus, vaata üle allajoonitud väljad.");
        fail.showAndWait();
    }

    public static void alertLogOutSuccess() {
        Alert loggedout = new Alert(Alert.AlertType.INFORMATION);
        loggedout.setTitle("Välja logitud");
        loggedout.setHeaderText("Edukalt välja logitud, kena päeva jätku!");
        loggedout.showAndWait();
    }

    public static void alertSavedListSuccess() {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Salvestatud");
        success.setHeaderText("Ostunimekiri salvestatud");
        success.showAndWait();
    }

    public static void updateListArea(File userList, TextArea listArea) {

        listArea.setText("");

        try (Scanner scan = new Scanner(new FileInputStream(userList))) {
            while (scan.hasNextLine()) {
                String item = scan.nextLine();
                listArea.appendText(item + "\n");
            }
        } catch (Exception e) {
            System.out.println("Exception " + e.getClass().getSimpleName());
        }

    }

    public static void saveListArea(TextArea listArea, File userList) {

        String enteredText = listArea.getText();
        String[] items = enteredText.split("\n");

        try (FileOutputStream fos = new FileOutputStream(userList)) {
            for (String item : items) {
                fos.write(item.getBytes());
                fos.write("\n".getBytes());
            }
        } catch (Exception e) {
            System.out.println("exc");
        }

    }

    public static void readListFromUserFile(File userList, ArrayList<String> userShoppingList) {
        try (Scanner scan = new Scanner(new FileInputStream(userList))) {
            while (scan.hasNextLine()) {
                userShoppingList.add(scan.nextLine());
            }
        } catch (Exception ignored) {}
    }

    public static Text displayPrice(Product product) {

        Text productPrice = new Text();
        productPrice.setText(product.getPrice() + " €");
        productPrice.setFont(new Font(20));
        productPrice.setX(103);
        productPrice.setY(58);

        return productPrice;
    }

    public static Text displayDiscountPrice(Product product) {

        Text discountPrice = new Text(product.getPrice() + " €");

        discountPrice.setFill(Color.RED);
        if (product.getDiscountType() == DiscountType.discountCard) discountPrice.setFill(Color.ORANGE);
        discountPrice.setFont(new Font(20));
        discountPrice.setX(103);
        discountPrice.setY(58);

        return discountPrice;

    }

    public static Text displayOldPrice(Product product) {

        Text productPrice = new Text();

        productPrice.setText(product.getPreSalePrice() + " €");
        productPrice.setStrikethrough(true);
        productPrice.setFill(Color.GRAY);
        productPrice.setFont(new Font(14));
        productPrice.setX(170);
        productPrice.setY(58);

        return productPrice;

    }

    public static int productCount(Text amount) {
        return Integer.parseInt(amount.getText().split(" ")[1]);
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            System.out.println("failed to open tab: " + e.getClass().getSimpleName());
        }
    }

    public static Text text(String msg, int size, int x, int y) {
        Text text = new Text(msg);
        text.setFont(new Font(size));
        text.setX(x);
        text.setY(y);

        return text;
    }

    public static Button button(String msg, int pref_x, int pref_y, int x, int y) {

        Button button = new Button();
        button.setText(msg);
        button.setPrefSize(pref_x, pref_y);
        button.setLayoutX(x);
        button.setLayoutY(y);

        return button;

    }

    public static TextField textField(String prompt, int pref_x, int pref_y, int x, int y) {

        TextField textField = new TextField();
        textField.setPrefSize(pref_x, pref_y);
        textField.setLayoutX(x);
        textField.setLayoutY(y);

        return textField;

    }

    public static PasswordField passwordField(String prompt, int pref_x, int pref_y, int x, int y) {

        PasswordField passwordField = new PasswordField();
        passwordField.setPrefSize(pref_x, pref_y);
        passwordField.setLayoutX(x);
        passwordField.setLayoutY(y);

        return passwordField;

    }

    public static Line line(int x, int y, int start_x, int end_x) {

        Line line = new Line();

        line.setLayoutX(x);
        line.setLayoutY(y);
        line.setStartX(start_x);
        line.setEndX(end_x);

        return line;

    }

    public static CheckBox checkbox(String msg, int x, int y, boolean big) {

        CheckBox checkBox = new CheckBox(msg);
        checkBox.setLayoutX(x);
        checkBox.setLayoutY(y);

        if (big) {
            checkBox.setFont(new Font(28));
            checkBox.setPrefSize(26, 27);
        }

        return checkBox;

    }

    public static ScrollPane scrollpane(int pref_x, int pref_y, int x, int y) {

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(pref_x, pref_y);
        scrollPane.setLayoutX(x);
        scrollPane.setLayoutY(y);

        return scrollPane;

    }

    public static Pane pane(int pref_x, int pref_y) {

        Pane pane = new Pane();
        pane.setPrefSize(pref_x, pref_y);

        pane.setPadding(new Insets(20, 10, 20, 10));
        pane.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3,4,3,4))));

        return pane;

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
