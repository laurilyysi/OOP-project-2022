package com.example.GUI;

import POC.DiscountType;
import POC.Product;
import Tee.Result;
import Tee.Test;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

import static Tee.Test.findShortestPaths;
import static com.example.GUI.GUIevents.*;

public class GUI extends Application {

    static HashMap<String, String> userIDwithPassword = new HashMap<>();
    private static User user;
    private static Scene scene;
    private static ArrayList<HashMap<String, ArrayList<Product>>> result = new ArrayList<>();
    private static HashMap<Product, Integer> purchased = new HashMap<>();

    private static int totalProducts = 0;
    private static double totalPrice = 0;
    private static double totalLength = 0.0;

    private static boolean saveTrip = true;
    private static String storesToSearch = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        scene = new Scene(loginPage(), 450, 550); // 450 ?
        stage.setResizable(false);
        stage.setTitle("OO");
        stage.setScene(scene);
        stage.show();
    }

    // <editor-fold desc="Methods">

    public static Scene getScene() {
        return scene;
    }

    public static HashMap<String, String> updateUserIDwithPassword() {
        try (Scanner scan = new Scanner(new FileInputStream("data\\userdata\\idsandpasswords.txt"))) {
            while (scan.hasNextLine()) {
                String[] userinfo = scan.nextLine().split(":");
                userIDwithPassword.put(userinfo[0], userinfo[1]);
            }
        } catch (FileNotFoundException ignored) {}
        return userIDwithPassword;
    }

    public static boolean validLogin(String username, String password) {
        return userIDwithPassword.containsKey(username) && userIDwithPassword.get(username).equals(password);
    }

    public static void updateInfo(Text products, Text prices) {
        if (totalProducts == 0) totalPrice = 0;

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        products.setText(totalProducts + "");
        prices.setText(df.format(totalPrice) + " €");
    }

    // </editor-fold>

    private static Group loginPage() {

        userIDwithPassword = updateUserIDwithPassword();

        Group root = new Group();

        root.getChildren().add(text("OO", 28, 195, 128));
        root.getChildren().add(text("Kasutajanimi", 12, 45, 186));
        root.getChildren().add(text("Salasõna", 12, 45, 219));

        TextField loginEnterUsername = textField("", 254, 25, 152, 167);
        root.getChildren().add(loginEnterUsername);

        PasswordField loginEnterPassword = passwordField("", 254, 25, 152, 200);
        root.getChildren().add(loginEnterPassword);

        Button loginLogIn = button("Logi sisse", 361, 25, 45, 275);
        loginLogIn.setOnAction(event -> {
            user = login(loginEnterUsername.getText(), loginEnterPassword.getText());
        });
        root.getChildren().add(loginLogIn);

        Button loginNoAccount = button("Pole kontot? Registreeru", 361, 25, 45, 315);
        loginNoAccount.setOnAction(event -> {
            scene.setRoot(registrationPage());
        });
        root.getChildren().add(loginNoAccount);

        return root;

    }

    private static Group kms() {

        Group root = new Group();


        Image logo = null;
        try {
            logo = new Image(new FileInputStream("src/main/resources/coop.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView logoview = new ImageView(logo);
        logoview.setX(120);
        logoview.setY(120);
        root.getChildren().add(logoview);

        return root;

    }

    private static Group registrationPage() {

        userIDwithPassword = updateUserIDwithPassword();

        Group root = new Group();

        root.getChildren().add(text("Registreeru", 28, 155, 70));

        Text regUsername = text("Kasutajanimi", 12, 42, 131);
        regUsername.setUnderline(true);
        root.getChildren().add(regUsername);

        Text regPassword = text("Salasõna", 12, 42, 162);
        regPassword.setUnderline(true);
        root.getChildren().add(regPassword);

        Text regAge = text("Vanus", 12, 42, 193);
        regAge.setUnderline(true);
        root.getChildren().add(regAge);

        TextField regEnterUsername = textField("4-18 märki, kasuta numbreid ja ladina tähti", 250, 25, 158, 114);
        regEnterUsername.setOnKeyTyped(event -> {
            regUsername.setUnderline(!validUsername(regEnterUsername.getText()));
        });

        root.getChildren().add(regEnterUsername);

        PasswordField regEnterPassword = passwordField("Ei tohi sisaldada märki ':'", 250, 25, 158, 145);
        regEnterPassword.setOnKeyTyped(event -> regPassword.setUnderline(!validPassword(regEnterPassword.getText())));
        root.getChildren().add(regEnterPassword);

        TextField regEnterAge = textField("Sisesta positiivne täisarv", 250, 25, 158, 176);
        regEnterAge.setOnKeyTyped(event -> regAge.setUnderline(!validAge(regEnterAge.getText())));
        root.getChildren().add(regEnterAge);

        root.getChildren().add(line(144, 212, -102, 263));
        root.getChildren().add(line(144, 304, -102, 263));

        Text regEmail = text("E-mail", 12, 42, 244);
        root.getChildren().add(regEmail);

        Text regCoords = text("Koordinaadid", 12, 42, 271);
        regCoords.setUnderline(true);
        root.getChildren().add(regCoords);

        Hyperlink regCoordsHelp = new Hyperlink("Abi");
        regCoordsHelp.setLayoutX(39);
        regCoordsHelp.setLayoutY(273);
        regCoordsHelp.setOnAction(e -> openWebpage("https://i.gyazo.com/c33c7cb93af4f5e2cd3d56e2b6dd4ac5.png"));
        root.getChildren().add(regCoordsHelp);

        TextField regEnterEmail = textField("", 250, 25, 158, 227);
        regEnterEmail.setOnKeyTyped(event -> regEmail.setUnderline(!validEmail(regEnterEmail.getText())));
        root.getChildren().add(regEnterEmail);

        TextField regEnterCoords = textField("nt kujul 58.3851, 26.7250", 250, 25, 158, 262);
        regEnterCoords.setOnKeyTyped(event -> regCoords.setUnderline(!validCoordinates(regEnterCoords.getText())));
        root.getChildren().add(regEnterCoords);

        root.getChildren().add(text("Olemasolevad kliendikaardid", 12, 42, 334));

        CheckBox regCheckCoop = checkbox("Säästukaart (Coop)", 48, 344, false);
        root.getChildren().add(regCheckCoop);

        CheckBox regCheckSelver = checkbox("Partnerkaart (Selver)", 48, 369, false);
        root.getChildren().add(regCheckSelver);

        CheckBox regCheckRimi = checkbox("Rimi kaart (Rimi)", 48, 394, false);
        root.getChildren().add(regCheckRimi);

        Button regButtonRegister = button("Registreeru", 189, 98, 216, 318);
        regButtonRegister.setOnAction(event -> {

            userIDwithPassword = updateUserIDwithPassword();

            boolean usernameOK = validUsername(regEnterUsername.getText());
            boolean passwordOK = validPassword(regEnterPassword.getText());
            boolean ageOK = validAge(regEnterAge.getText());
            boolean locationOK = validCoordinates(regEnterCoords.getText());
            boolean emailOK = validEmail(regEnterEmail.getText());

            boolean allValid = usernameOK && passwordOK && ageOK && locationOK && emailOK;

            if (allValid) {
                userIDwithPassword = updateUserIDwithPassword();

                createNewUser(regEnterUsername.getText(), regEnterPassword.getText(), regEnterAge.getText(),
                        regEnterEmail.getText(), regEnterCoords.getText(), regCheckCoop.isSelected(),
                        regCheckSelver.isSelected(), regCheckRimi.isSelected());

                alertRegistrationSuccess(regEnterUsername.getText());
                scene.setRoot(loginPage());

            }

            else alertRegistrationFailure();

        });

        root.getChildren().add(regButtonRegister);

        Button regButtonGoBack = button("Mine tagasi", 366, 25, 41, 429);
        regButtonGoBack.setOnAction(event -> {
            scene.setRoot(loginPage());
        });
        root.getChildren().add(regButtonGoBack);

        return root;

    }

    static Group mainMenuPage() {

        Group root = new Group();

        root.getChildren().add(text("Tere tulemast!", 28, 136, 72));
        root.getChildren().add(text("Mida soovid teha?", 16, 159, 124));

        Button mainChangeList = button("Muuda ostunimekirja", 266, 39, 91, 156);
        mainChangeList.setFont(new Font(18));
        mainChangeList.setOnAction(event -> {
            scene.setRoot(menuChangeList());
        });
        root.getChildren().add(mainChangeList);

        Button mainGoShopping = button("Mine ostlema", 266, 39, 91, 205);
        mainGoShopping.setFont(new Font(18));
        mainGoShopping.setOnAction(event -> {
            scene.setRoot(menuGoShopping());
        });
        root.getChildren().add(mainGoShopping);

        Button button3 = button(" ", 266, 39, 91, 254);
        button3.setFont(new Font(18));
        button3.setDisable(true);
        root.getChildren().add(button3);

        root.getChildren().add(line(122, 304, -30, 236));

        Button mainViewProfile = button("Kuva profiil", 266, 39, 91, 316);
        mainViewProfile.setFont(new Font(18));
        mainViewProfile.setOnAction(event -> {
            scene.setRoot(menuViewProfile());
        });
        root.getChildren().add(mainViewProfile);

        Button mainViewHistory = button("Kuva ostuajalugu", 266, 39, 91, 365);
        mainViewHistory.setFont(new Font(18));
        mainViewHistory.setOnAction(e -> scene.setRoot(shoppingHistory()));
        root.getChildren().add(mainViewHistory);

        Button button6 = button(" ", 266, 39, 91, 414);
        button6.setFont(new Font(18));
        button6.setDisable(true);
        root.getChildren().add(button6);

        Button mainLogOut = button("Logi välja", 266, 39, 91, 463);
        mainLogOut.setFont(new Font(18));
        mainLogOut.setOnAction(event -> {

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Logi välja");
            confirm.setHeaderText("Kas soovite välja logida?");

            Optional<ButtonType> buttonResult = confirm.showAndWait();
            if (buttonResult.isPresent() && buttonResult.get() == ButtonType.OK) {

                try {
                    Files.write(Path.of(user.getPath() + "\\" + user.getInfoFileName()), user.userInfoString().getBytes());
                } catch (IOException ignored) {}

                user = null;
                scene.setRoot(loginPage());
                alertLogOutSuccess();

            } else {
                confirm.close();
            }

        });

        root.getChildren().add(mainLogOut);

        return root;

    }

    private static Group menuChangeList() {

        Group root = new Group();

        root.getChildren().add(text("Ostunimekiri", 28, 146, 60));
        root.getChildren().add(text("Sisesta siia tooted, mida soovid otsida", 12, 120, 87));
        root.getChildren().add(text("Iga toode eraldi reale", 12, 167, 104));

        TextArea listArea = new TextArea();
        listArea.setFont(new Font(22));
        listArea.setPrefSize(358, 324);
        listArea.setLayoutX(46);
        listArea.setLayoutY(134);
        root.getChildren().add(listArea);

        Button loadFromFile = button("Lae varasem ostunimekiri failist", 358, 25, 46, 469);
        loadFromFile.setOnAction(event -> {
            updateListArea(user.getUserShoppingListFile(), listArea);
        });
        root.getChildren().add(loadFromFile);

        Button saveToFile = button("Salvesta faili", 170, 25, 46, 502);
        saveToFile.setOnAction(event -> {
            saveListArea(listArea, user.getUserShoppingListFile());
            alertSavedListSuccess();
        });
        root.getChildren().add(saveToFile);

        Button backToMain = button("Tagasi menüüsse", 170, 25, 234, 502);
        backToMain.setOnAction(event -> {
            user.clearList();
            readListFromUserFile(user.getUserShoppingListFile(), user.getShoppinglist());
            scene.setRoot(mainMenuPage());
        });
        root.getChildren().add(backToMain);

        return root;

    }

    private static Group menuGoShopping() {

        Group root = new Group();

        root.getChildren().add(text("Seadistused", 28, 151, 88));
        root.getChildren().add(text("Otsi poodidest", 20, 160, 171));

        CheckBox checkCoop = checkbox("", 59, 195, true);
        root.getChildren().add(checkCoop);
        root.getChildren().add(text("Coop", 12, 60, 247));

        CheckBox checkMaxima = checkbox("", 134, 195, true);
        root.getChildren().add(checkMaxima);
        root.getChildren().add(text("Maxima", 12, 130, 247));

        CheckBox checkPrisma = checkbox("", 209, 195, true);
        root.getChildren().add(checkPrisma);
        root.getChildren().add(text("Prisma", 12, 207, 247));

        CheckBox checkRimi = checkbox("", 284, 195, true);
        root.getChildren().add(checkRimi);
        root.getChildren().add(text("Rimi", 12, 288, 247));

        CheckBox checkSelver = checkbox("", 359, 195, true);
        root.getChildren().add(checkSelver);
        root.getChildren().add(text("Selver", 12, 359, 247));

        Button beginSearch = button("OTSI", 346, 60, 53, 421);
        beginSearch.setFont(new Font(28));
        beginSearch.setOnAction(event -> {
            System.out.println("Begin search..");

            purchased.clear();
            user.clearList();
            readListFromUserFile(user.getUserShoppingListFile(), user.getShoppinglist());

            try {
                result = beginSearch(user.getShoppinglist(), checkCoop.isSelected(), checkMaxima.isSelected(),
                        checkPrisma.isSelected(), checkRimi.isSelected(), checkSelver.isSelected());
            } catch (InterruptedException ignore) {}

            scene.setRoot(shoppingScene());

            System.out.println("End search..");
        });
        root.getChildren().add(beginSearch);

        Button goBack = button("Mine tagasi", 107, 25, 169, 491);
        goBack.setOnAction(event -> {
            scene.setRoot(mainMenuPage());
        });
        root.getChildren().add(goBack);

        return root;

    }

    private static Group shoppingScene() {

        Group root = new Group();

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        tabPane.setPrefSize(450, 550);

        Tab kuva = new Tab("  Kuva  ");
        kuva.setClosable(false);
        AnchorPane kuvaTab = new AnchorPane();

            ScrollPane addedProducts = scrollpane(420, 387, 14, 14);
            kuvaTab.getChildren().add(addedProducts);

            VBox vbox2 = new VBox();
            addedProducts.setContent(vbox2);

            kuvaTab.getChildren().add(text("Tooteid kokku: ", 16, 25, 430));
            kuvaTab.getChildren().add(text("Hind kokku: ", 16, 25, 455));

            Text totalProductsCount = text(String.valueOf(totalProducts), 16, 160, 430);
            kuvaTab.getChildren().add(totalProductsCount);

            Text totalPriceCount = text(totalPrice + " €", 16, 160, 455);
            kuvaTab.getChildren().add(totalPriceCount);

        kuva.setContent(kuvaTab);

        // ----

        Tab vali = new Tab("  Vali  ");
        vali.setClosable(false);
        AnchorPane valiTab = new AnchorPane();

            ScrollPane allProducts = scrollpane(420, 387, 14, 14);
            valiTab.getChildren().add(allProducts);

            VBox vbox = new VBox();

            Text preShopInfo = new Text("\n\n\n\n\n\n\n\n\n                 " +
                    "Vali rippmenüüst toode. Erinevad valikud ilmuvad siia kasti.");
            vbox.getChildren().add(preShopInfo);

            allProducts.setContent(vbox);

            valiTab.getChildren().add(text("Toode", 12, 24, 427));

            ChoiceBox<String> dropdown = new ChoiceBox<>();
            dropdown.setPrefSize(225, 25);
            dropdown.setLayoutX(68);
            dropdown.setLayoutY(408);
            ObservableList<String> dropdownItems = FXCollections.observableArrayList(user.getShoppinglist());
            dropdown.setItems(dropdownItems);

            dropdown.setOnAction(event -> {

                vbox.getChildren().clear();
                String selected = dropdown.getValue();
                HashMap<String, ArrayList<Product>> products = result.get(user.getShoppinglist().indexOf(selected));
                Collections.sort(products.get(selected));

                for (Product product : products.get(selected)) {

                    Pane pane = pane(402, 100);

                    String productNameString = product.getName();
                    if (productNameString.length() > 50) productNameString = productNameString.substring(0, 50) + "...";

                    Text productName = text(productNameString, 12, 103, 27);
                    Text productStore = text(product.getStore(), 12, 103, 82);

                    Text productPrice;
                    if (product.getDiscountType() != DiscountType.noDiscount) {
                        productPrice = displayOldPrice(product);
                        Text discountPrice = displayDiscountPrice(product);
                        pane.getChildren().add(discountPrice);
                    } else productPrice = displayPrice(product);

                    /*
                    Image image = product.getImage(); // new Image(product.getImgURL(), 90, 90, true, false);
                    ImageView imgView = new ImageView(image);
                    imgView.setX(6);
                    imgView.setY(6);
                    imgView.prefHeight(90);
                    imgView.prefWidth(90);
                    */

                    pane.getChildren().add(logo(product.getStore()));

                    Button addTo = button("Lisa korvi", 70, 25, 320, 64);
                    Button removeFrom = button("Eemalda", 70, 25, 320, 64);

                    Button plus = button("+", 27, 8, 264, 64);
                    Button minus = button("-", 27, 8, 291, 64);
                    minus.setDisable(true);

                    Button moreInfo = button("Lisainfo", 70, 25, 320, 34);
                    moreInfo.setOnAction(e -> {
                        openWebpage(product.getLink());
                    });


                    Text amount = text("Kogus: 1", 12, 202, 82);

                    plus.setOnAction(p -> {
                        int howMany = productCount(amount) + 1;
                        amount.setText("Kogus: " + howMany);

                        if (howMany <= 1) minus.setDisable(true);
                        else minus.setDisable(false);

                        purchased.put(product, purchased.get(product) + 1);
                        totalPrice += product.getPrice();
                        totalProducts += 1;
                        updateInfo(totalProductsCount, totalPriceCount);

                    });

                    minus.setOnAction(m -> {
                        int mitu = productCount(amount) - 1;

                        if (mitu <= 1) minus.setDisable(true);
                        else minus.setDisable(false);

                        amount.setText("Kogus: " + mitu);
                        purchased.put(product, purchased.get(product) - 1);

                        totalPrice -= product.getPrice();
                        totalProducts -= 1;
                        updateInfo(totalProductsCount, totalPriceCount);

                    });

                    addTo.setOnAction(e -> {

                        if (!purchased.containsKey(product)) {

                            purchased.put(product, 1);

                            totalPrice += product.getPrice();
                            totalProducts += 1;
                            updateInfo(totalProductsCount, totalPriceCount);

                            vbox2.getChildren().add(pane);
                            pane.getChildren().remove(addTo);

                            pane.getChildren().add(removeFrom);
                            pane.getChildren().add(amount);
                            pane.getChildren().add(plus);
                            pane.getChildren().add(minus);

                        } else System.out.println("juba olemas");

                    });

                    removeFrom.setOnAction(f -> {

                        int mitu = productCount(amount);

                        purchased.remove(product);
                        totalPrice -= product.getPrice() * mitu;
                        totalProducts -= mitu;
                        updateInfo(totalProductsCount, totalPriceCount);

                        vbox.getChildren().add(pane);
                        vbox2.getChildren().remove(pane);

                        pane.getChildren().remove(removeFrom);
                        pane.getChildren().remove(amount);
                        pane.getChildren().remove(plus);
                        pane.getChildren().remove(minus);

                        pane.getChildren().add(addTo);

                    });

                    pane.getChildren().add(productName);
                    pane.getChildren().add(productStore);
                    pane.getChildren().add(productPrice);
                    pane.getChildren().add(addTo);
                    pane.getChildren().add(moreInfo);

                    vbox.getChildren().add(pane);

                }

            });

            valiTab.getChildren().add(dropdown);

            Button lopeta = button("Lõpeta", 80, 80, 355, 410);
            lopeta.setOnAction(event -> {
                scene.setRoot(shoppingComplete());
            });
            kuvaTab.getChildren().add(lopeta);

        vali.setContent(valiTab);

        tabPane.getTabs().add(vali);
        tabPane.getTabs().add(kuva);
        root.getChildren().add(tabPane);

        return root;

    }

    private static Group menuViewProfile() {

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        Group root = new Group();

        root.getChildren().add(text("Profiil", 28, 190, 84));
        root.getChildren().add(text("Ostukordi kokku: ", 18, 57, 147));
        root.getChildren().add(text("Kokku tooteid ostetud: ", 18, 57, 211));
        root.getChildren().add(text("Kokku raha kulutatud: ", 18, 57, 278));
        root.getChildren().add(text("Kokku raha säästetud: ", 18, 57, 346));

        // ---
        Text vpTotal = text(user.getTotalVisits() + "", 18, 330, 147);
        root.getChildren().add(vpTotal);

        Text vpBought = text(df.format(user.getTotalBought()) + " tk", 18, 330, 211);
        root.getChildren().add(vpBought);

        Text vpSpent = text(df.format(user.getTotalSpent()) + " €", 18, 330, 278);
        root.getChildren().add(vpSpent);

        Text vpSaved = text(df.format(user.getTotalSaved()) + " €", 18, 330, 346);
        root.getChildren().add(vpSaved);

        // ---

        Button vpDelete = button("Kustuta andmed", 173, 25, 49, 472);
        vpDelete.setDisable(true);
        root.getChildren().add(vpDelete);

        Button vpSummary = button("Kokkuvõte", 173, 25, 230, 472);
        vpSummary.setDisable(true);
        root.getChildren().add(vpSummary);

        Button vpGoBack = button("Mine tagasi", 356, 25, 48, 504);
        vpGoBack.setOnAction(e -> {
            scene.setRoot(mainMenuPage());
        });
        root.getChildren().add(vpGoBack);

        return root;

    }

    private static Group shoppingComplete() {

        Group root = new Group();

        ScrollPane scScrollPane = scrollpane(387, 200, 32, 150);
        VBox vbox = new VBox();
        scScrollPane.setContent(vbox);

        StringBuilder data = new StringBuilder("");

        for (Product product : purchased.keySet()) {

            Pane pane = pane(370, 75);

            String strAmount = String.valueOf(purchased.get(product));

            Text amount = text(strAmount + "tk", 16, 14, 43);
            pane.getChildren().add(amount);

            String productNameString = product.getName();

            Text name = text(productNameString, 12, 54, 43);
            name.setWrappingWidth(210);
            pane.getChildren().add(name);

            String strPrice = String.valueOf(product.getPrice());

            Text price = text(strPrice + " €", 16, 268, 43);
            pane.getChildren().add(price);

            Button info = button("Info", 42, 25, 320, 25);
            info.setOnAction(e -> {
                openWebpage(product.getLink());
            });

            pane.getChildren().add(info);

            vbox.getChildren().add(pane);

            data.append(product.getStore() + "\t" + strAmount + "\t" + productNameString + "\t" + strPrice + "\n");

        }

        root.getChildren().add(scScrollPane);

        root.getChildren().add(text("Kokkuvõte", 28, 155, 95));
        root.getChildren().add(text("Tooteid: ", 18, 32, 141));
        root.getChildren().add(text("Kokku: ", 18, 298, 141));

        Text teekond = text("Teekond: ", 12, 32, 369);
        root.getChildren().add(teekond);

        Text scProductsBought = text(totalProducts + " tk", 18, 106, 141);
        root.getChildren().add(scProductsBought);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        Text scMoneySpent = text(df.format(totalPrice) + " €", 18, 357, 142);
        root.getChildren().add(scMoneySpent);

        String[] coords = user.getLocation().split(", ");
        findShortestPaths(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), lookFor(purchased));
        Result res = Test.paths.get(0);
        teekond.setText("Teekond: " + res.getDistanceKM() + " km (linnulennult)");

        Text path = text(res.pathToString(), 12, 41, 386);
        root.getChildren().add(path);

        Button scDisableSave = button("Ära salvesta seda ostukorda", 188, 25, 32, 471);
        scDisableSave.setOnAction(e -> {
            saveTrip = false;
            scDisableSave.setDisable(true);
        });
        root.getChildren().add(scDisableSave);

        Button scSendSummary = button(" ", 188, 25, 32, 503);
        scSendSummary.setDisable(true);
        root.getChildren().add(scSendSummary);

        Button mapsLink = button("Ava Google Maps", 188, 25, 230, 471);
        mapsLink.setOnAction(e -> {
            openWebpage(res.mapsLink(user.getLocation()));
        });
        root.getChildren().add(mapsLink);

        Button scBackToMenu = button("Tagasi menüüsse", 188, 25, 231, 503);
        scBackToMenu.setOnAction(e -> {

            scene.setRoot(mainMenuPage());

            if (saveTrip) {
                user.setTotalVisits(user.getTotalVisits() + 1);
                user.setTotalBought(user.getTotalBought() + totalProducts);
                user.setTotalSpent(user.getTotalSpent() + totalPrice);

                // TODO
                user.setTotalSaved(user.getTotalSaved() + 0);

                try {
                    Files.write(Path.of(user.getPath() + "\\" + user.getInfoFileName()), user.userInfoString().getBytes());
                } catch (IOException ignored) {}

                String savePath = "data\\userdata\\" + user.getUsername() + "\\" + user.getUsername() + "_shop" + user.getTotalVisits() + ".txt";
                StringBuilder info = new StringBuilder(LocalDateTime.now() + "\t" + df.format(totalPrice) + "\n");
                data.append(info);

                try {
                    Files.write(Path.of(savePath), data.toString().getBytes());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

            saveTrip = true;
            totalProducts = 0;
            totalPrice = 0;
            totalLength = 0.0;

        });

        root.getChildren().add(scBackToMenu);

        return root;

    }

    private static Group shoppingHistory() {

        Group root = new Group();

        Text title = text("Ajalugu", 28, 178, 82);
        root.getChildren().add(title);

        Text time = text("Aeg: ", 18, 33, 131);
        root.getChildren().add(time);

        Text ttlPrice = text("00.00 €", 18, 357, 131);
        root.getChildren().add(ttlPrice);

        ScrollPane scrollPane = scrollpane(387, 280, 32, 147);
        VBox vbox = new VBox();
        scrollPane.setContent(vbox);
        root.getChildren().add(scrollPane);

        Text choose = text("Vali", 12, 32, 453);
        root.getChildren().add(choose);

        ChoiceBox<String> choices = new ChoiceBox<>();
        choices.setPrefSize(351, 25);
        choices.setLayoutX(68);
        choices.setLayoutY(436);

        ArrayList<String> options = new ArrayList<>();
        for (int i = 1; i <= user.getTotalVisits(); i++) options.add(user.getUsername() + "_shop" + i + ".txt");
        ObservableList<String> dropdownItems = FXCollections.observableArrayList(options);
        choices.setItems(dropdownItems);
        choices.setOnAction(e -> {
            vbox.getChildren().clear();
            File shop = new File("data/userdata/" + user.getUsername() + "/" + choices.getValue());
            try (Scanner scan = new Scanner(new FileInputStream(shop))) {
                while (scan.hasNextLine()) {

                    String line = scan.nextLine();
                    try {
                        String[] split = line.split("\t");

                        Pane pane = pane(370, 75);

                        Text amount = text(split[1] + " tk", 16, 14, 43);
                        pane.getChildren().add(amount);

                        String productNameString = split[2];
                        Text name = text(productNameString, 12, 54, 43);
                        name.setWrappingWidth(210);
                        pane.getChildren().add(name);

                        String strPrice = split[3];
                        Text price = text(strPrice + " €", 16, 268, 43);
                        pane.getChildren().add(price);

                        vbox.getChildren().add(pane);

                    } catch (Exception exception) {
                        String[] split = line.split("\t");
                        String[] datetime = split[0].split("T");
                        time.setText(datetime[0] + " " + datetime[1].substring(0, 8));
                        ttlPrice.setText(split[1] + " €");
                    }

                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }


        });
        root.getChildren().add(choices);

        Button menu = button("Tagasi menüüsse", 387, 25, 32, 488);
        menu.setOnAction(e -> scene.setRoot(mainMenuPage()));
        root.getChildren().add(menu);

        return root;

    }

}