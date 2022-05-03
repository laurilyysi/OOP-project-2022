package com.example.GUI;

import POC.Product;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.GUI.GUIbuttons.*;

public class GUI extends Application {

    private static HashMap<String, String> userIDwithPassword = new HashMap<>();
    private static User user;
    private static Scene scene;

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


    public static void setScene(Scene scene) {
        GUI.scene = scene;
    }

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

    public static void setUser(User user) {
        GUI.user = user;
    }

    public static boolean validUsername(String username) {
        boolean validLength = username.length() >= 3 && username.length() <= 18;
        boolean validCharacters = username.matches("[A-Za-z0-9]*");
        boolean doesntAlreadyExist = !userIDwithPassword.containsKey(username);

        // textUsername.setUnderline(!(validLength && validCharacters && doesntAlreadyExist));
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

    // https://stackoverflow.com/questions/8204680/java-regex-email
    public static boolean validEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    // </editor-fold>

    private static Group loginPage() {

        userIDwithPassword = updateUserIDwithPassword();

        Group root = new Group();

        Text loginTitle = new Text("OO");
        loginTitle.setFont(new Font(28));
        loginTitle.setX(195);
        loginTitle.setY(128);
        root.getChildren().add(loginTitle);

        Text loginUsername = new Text("Kasutajanimi");
        loginUsername.setX(45);
        loginUsername.setY(186);
        root.getChildren().add(loginUsername);

        Text loginPassword = new Text("Salasõna");
        loginPassword.setX(45);
        loginPassword.setY(219);
        root.getChildren().add(loginPassword);

        TextField loginEnterUsername = new TextField();
        loginEnterUsername.setPrefSize(254, 25);
        loginEnterUsername.setLayoutX(152);
        loginEnterUsername.setLayoutY(167);
        root.getChildren().add(loginEnterUsername);

        PasswordField loginEnterPassword = new PasswordField();
        loginEnterPassword.setPrefSize(254, 25);
        loginEnterPassword.setLayoutX(152);
        loginEnterPassword.setLayoutY(200);
        root.getChildren().add(loginEnterPassword);

        Button loginLogIn = new Button("Logi sisse");
        loginLogIn.setPrefSize(361, 25);
        loginLogIn.setLayoutX(45);
        loginLogIn.setLayoutY(275);

        loginLogIn.setOnAction(event -> {
            user = login(loginEnterUsername.getText(), loginEnterPassword.getText());
        });

        root.getChildren().add(loginLogIn);

        Button loginNoAccount = new Button("Pole kontot? Registreeru");
        loginNoAccount.setPrefSize(361, 25);
        loginNoAccount.setLayoutX(45);
        loginNoAccount.setLayoutY(315);

        loginNoAccount.setOnAction(event -> {
            scene.setRoot(registrationPage());
        });

        root.getChildren().add(loginNoAccount);

        return root;

    }

    private static Group registrationPage() {

        userIDwithPassword = updateUserIDwithPassword();

        Group root = new Group();

        Text regTitle = new Text("Registreeru");
        regTitle.setFont(new Font(28));
        regTitle.setX(155);
        regTitle.setY(70);
        root.getChildren().add(regTitle);

        Text regUsername = new Text("Kasutajanimi");
        regUsername.setUnderline(true);
        regUsername.setX(42);
        regUsername.setY(131);
        root.getChildren().add(regUsername);

        Text regPassword = new Text("Salasõna");
        regPassword.setUnderline(true);
        regPassword.setX(42);
        regPassword.setY(162);
        root.getChildren().add(regPassword);

        Text regAge = new Text("Vanus");
        regAge.setUnderline(true);
        regAge.setX(42);
        regAge.setY(193);
        root.getChildren().add(regAge);

        TextField regEnterUsername = new TextField();
        regEnterUsername.setPromptText("4-18 märki, kasuta numbreid ja ladina tähti");
        regEnterUsername.setPrefSize(250, 25);
        regEnterUsername.setLayoutX(158);
        regEnterUsername.setLayoutY(114);

        regEnterUsername.setOnKeyTyped(event -> {
            regUsername.setUnderline(!validUsername(regEnterUsername.getText()));
        });

        root.getChildren().add(regEnterUsername);

        TextField regEnterPassword = new TextField();
        regEnterPassword.setPromptText("Ei tohi sisaldada märki ':'");
        regEnterPassword.setPrefSize(250, 25);
        regEnterPassword.setLayoutX(158);
        regEnterPassword.setLayoutY(145);

        regEnterPassword.setOnKeyTyped(event -> {
            regPassword.setUnderline(!validPassword(regEnterPassword.getText()));
        });

        root.getChildren().add(regEnterPassword);

        TextField regEnterAge = new TextField();
        regEnterAge.setPromptText("Sisesta positiivne täisarv");
        regEnterAge.setPrefSize(250, 25);
        regEnterAge.setLayoutX(158);
        regEnterAge.setLayoutY(176);

        regEnterAge.setOnKeyTyped(event -> {
            regAge.setUnderline(!validAge(regEnterAge.getText()));
        });

        root.getChildren().add(regEnterAge);

        Line regLine = new Line();
        regLine.setLayoutX(144);
        regLine.setLayoutY(212);
        regLine.setStartX(-102);
        regLine.setEndX(263);
        root.getChildren().add(regLine);

        Text regEmail = new Text("E-mail");
        regEmail.setUnderline(true);
        regEmail.setX(42);
        regEmail.setY(244);
        root.getChildren().add(regEmail);

        Text regCoords = new Text("Koordinaadid");
        regCoords.setUnderline(true);
        regCoords.setX(42);
        regCoords.setY(271);
        root.getChildren().add(regCoords);

        TextField regEnterEmail = new TextField();
        regEnterEmail.setPrefSize(250, 25);
        regEnterEmail.setLayoutX(158);
        regEnterEmail.setLayoutY(227);

        regEnterEmail.setOnKeyTyped(event -> {
            regEmail.setUnderline(!validEmail(regEnterEmail.getText()));
        });

        root.getChildren().add(regEnterEmail);

        TextField regEnterCoords = new TextField();
        regEnterCoords.setPrefSize(250, 25);
        regEnterCoords.setLayoutX(158);
        regEnterCoords.setLayoutY(262);

        regEnterCoords.setOnKeyTyped(event -> {
            regCoords.setUnderline(!true); // TODO: this
        });

        root.getChildren().add(regEnterCoords);

        Line regLineBelow = new Line();
        regLineBelow.setLayoutX(144);
        regLineBelow.setLayoutY(304);
        regLineBelow.setStartX(-102);
        regLineBelow.setEndX(263);
        root.getChildren().add(regLineBelow);

        Text regCards = new Text("Olemasolevad kliendikaardid");
        regCards.setX(42);
        regCards.setY(334);
        root.getChildren().add(regCards);

        CheckBox regCheckCoop = new CheckBox("Säästukaart (Coop)");
        regCheckCoop.setLayoutX(48);
        regCheckCoop.setLayoutY(344);
        root.getChildren().add(regCheckCoop);

        CheckBox regCheckSelver = new CheckBox("Partnerkaart (Selver)");
        regCheckSelver.setLayoutX(48);
        regCheckSelver.setLayoutY(369);
        root.getChildren().add(regCheckSelver);

        CheckBox regCheckRimi = new CheckBox("Rimi kaart (Rimi)");
        regCheckRimi.setLayoutX(48);
        regCheckRimi.setLayoutY(394);
        root.getChildren().add(regCheckRimi);

        Button regButtonRegister = new Button("Registreeru");
        regButtonRegister.setPrefSize(189, 98);
        regButtonRegister.setLayoutX(216);
        regButtonRegister.setLayoutY(318);

        regButtonRegister.setOnAction(event -> {

            userIDwithPassword = updateUserIDwithPassword();

            boolean usernameOK = validUsername(regEnterUsername.getText());
            boolean passwordOK = validPassword(regEnterPassword.getText());
            boolean ageOK = validAge(regEnterAge.getText());
            boolean locationOK = true; // TODO: change this
            boolean emailOK = validEmail(regEnterEmail.getText());

            boolean allValid = usernameOK && passwordOK && ageOK && locationOK && emailOK;

            if (allValid) {
                userIDwithPassword = updateUserIDwithPassword();

                createNewUser(regEnterUsername.getText(), regEnterPassword.getText(), regEnterAge.getText(),
                        regEnterEmail.getText(), regEnterCoords.getText(), regCheckCoop.isSelected(),
                        regCheckSelver.isSelected(), regCheckRimi.isSelected());


                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Kasutaja registreeritud");
                success.setHeaderText("Kasutaja " + regEnterUsername.getText() + " edukalt registreeritud");
                success.setContentText("Võite sisse logida");
                success.showAndWait();

                scene.setRoot(loginPage());

            }

            else {
                Alert fail = new Alert(Alert.AlertType.ERROR);
                fail.setTitle("Registreerumine ebaõnnestus");
                fail.setHeaderText("Registreerumine ebaõnnestus, vaata üle allajoonitud väljad.");
                fail.showAndWait();
            }


        });

        root.getChildren().add(regButtonRegister);

        Button regButtonGoBack = new Button("Mine tagasi");
        regButtonGoBack.setPrefSize(366, 25);
        regButtonGoBack.setLayoutX(41);
        regButtonGoBack.setLayoutY(429);

        regButtonGoBack.setOnAction(event -> {
            scene.setRoot(loginPage());
        });

        root.getChildren().add(regButtonGoBack);

        return root;

    }

    static Group mainMenuPage() {

        Group root = new Group();

        Text mainWelcome = new Text("Tere tulemast!");
        mainWelcome.setFont(new Font(28));
        mainWelcome.setX(136);
        mainWelcome.setY(72);
        root.getChildren().add(mainWelcome);

        Text mainPrompt = new Text("Mida soovid teha?");
        mainPrompt.setFont(new Font(16));
        mainPrompt.setX(159);
        mainPrompt.setY(124);
        root.getChildren().add(mainPrompt);

        Button mainChangeList = new Button("Muuda ostunimekirja");
        mainChangeList.setFont(new Font(18));
        mainChangeList.setPrefSize(266, 39);
        mainChangeList.setLayoutX(91);
        mainChangeList.setLayoutY(156);

        mainChangeList.setOnAction(event -> {
            scene.setRoot(menuChangeList());
        });

        root.getChildren().add(mainChangeList);

        Button mainGoShopping = new Button("Mine ostlema");
        mainGoShopping.setFont(new Font(18));
        mainGoShopping.setPrefSize(266, 39);
        mainGoShopping.setLayoutX(91);
        mainGoShopping.setLayoutY(205);

        mainGoShopping.setOnAction(event -> {
            scene.setRoot(menuGoShopping());
        });

        root.getChildren().add(mainGoShopping);

        Button mainCalculatePath = new Button("Arvuta tee");
        mainCalculatePath.setFont(new Font(18));
        mainCalculatePath.setPrefSize(266, 39);
        mainCalculatePath.setLayoutX(91);
        mainCalculatePath.setLayoutY(254);
        root.getChildren().add(mainCalculatePath);

        Line line = new Line();
        line.setLayoutX(122);
        line.setLayoutY(304);
        line.setStartX(-30);
        line.setEndX(236);
        root.getChildren().add(line);

        Button mainViewProfile = new Button("Kuva profiil");
        mainViewProfile.setFont(new Font(18));
        mainViewProfile.setPrefSize(266, 39);
        mainViewProfile.setLayoutX(91);
        mainViewProfile.setLayoutY(316);
        root.getChildren().add(mainViewProfile);

        Button mainViewHistory = new Button("Kuva ostuajalugu");
        mainViewHistory.setFont(new Font(18));
        mainViewHistory.setPrefSize(266, 39);
        mainViewHistory.setLayoutX(91);
        mainViewHistory.setLayoutY(365);
        root.getChildren().add(mainViewHistory);

        Button mainFriends = new Button("Halda sõpru");
        mainFriends.setFont(new Font(18));
        mainFriends.setPrefSize(266, 39);
        mainFriends.setLayoutX(91);
        mainFriends.setLayoutY(414);
        root.getChildren().add(mainFriends);

        Button mainLogOut = new Button("Logi välja");
        mainLogOut.setFont(new Font(18));
        mainLogOut.setPrefSize(266, 39);
        mainLogOut.setLayoutX(91);
        mainLogOut.setLayoutY(463);

        mainLogOut.setOnAction(event -> {

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Logi välja");
            confirm.setHeaderText("Kas soovite välja logida?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                scene.setRoot(loginPage());
                Alert loggedout = new Alert(Alert.AlertType.INFORMATION);
                loggedout.setTitle("Välja logitud");
                loggedout.setHeaderText("Edukalt välja logitud, kena päeva jätku!");
                loggedout.showAndWait();
            } else {
                confirm.close();
            }

        });

        root.getChildren().add(mainLogOut);

        return root;

    }

    private static Group menuChangeList() {

        Group root = new Group();

        Text title = new Text("Ostunimekiri");
        title.setFont(new Font(28));
        title.setX(146);
        title.setY(60);
        root.getChildren().add(title);

        Text infoPrompt1 = new Text("Sisesta siia tooted, mida soovid otsida");
        infoPrompt1.setX(120);
        infoPrompt1.setY(87);
        root.getChildren().add(infoPrompt1);

        Text infoPrompt2 = new Text("Iga toode eraldi reale");
        infoPrompt2.setX(167);
        infoPrompt2.setY(104);
        root.getChildren().add(infoPrompt2);

        TextArea listArea = new TextArea();
        listArea.setFont(new Font(22));
        listArea.setPrefSize(358, 324);
        listArea.setLayoutX(46);
        listArea.setLayoutY(134);
        root.getChildren().add(listArea);

        Button loadFromFile = new Button("Lae varasem ostunimekiri failist");
        loadFromFile.setPrefSize(358, 25);
        loadFromFile.setLayoutX(46);
        loadFromFile.setLayoutY(469);

        loadFromFile.setOnAction(event -> {

            File userList = new File("data/userdata/" + user.getUsername() + "/" + user.getListFileName());
            listArea.setText("");

            try (Scanner scan = new Scanner(new FileInputStream(userList))) {
                while (scan.hasNextLine()) {
                    String item = scan.nextLine();
                    listArea.appendText(item + "\n");
                }
            } catch (Exception e) {
                System.out.println("Exception " + e.getClass().getSimpleName());
            }

        });

        root.getChildren().add(loadFromFile);

        Button saveToFile = new Button("Salvesta faili");
        saveToFile.setPrefSize(170, 25);
        saveToFile.setLayoutX(46);
        saveToFile.setLayoutY(502);

        saveToFile.setOnAction(event -> {

            String enteredText = listArea.getText();
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

        });

        root.getChildren().add(saveToFile);

        Button backToMain = new Button("Tagasi menüüsse");
        backToMain.setPrefSize(170, 25);
        backToMain.setLayoutX(234);
        backToMain.setLayoutY(502);

        backToMain.setOnAction(event -> {

            File userList = new File("data/userdata/" + user.getUsername() + "/" + user.getListFileName());

            try (Scanner scan = new Scanner(new FileInputStream(userList))) {
                user.clearList();
                while (scan.hasNextLine()) {
                    String item = scan.nextLine();
                    user.addToList(item);
                }
                scene.setRoot(mainMenuPage());
            } catch (Exception e) {
                System.out.println("[Exception] clickButtonOstunimekiriMineTagasi " + e.getClass().getSimpleName());
            }

        });

        root.getChildren().add(backToMain);

        return root;

    }

    private static Group menuGoShopping() {

        Group root = new Group();

        Text title = new Text("Seadistused");
        title.setFont(new Font(28));
        title.setX(151);
        title.setY(88);
        root.getChildren().add(title);

        Text selectSearchFrom = new Text("Otsi poodidest");
        selectSearchFrom.setFont(new Font(20));
        selectSearchFrom.setX(160);
        selectSearchFrom.setY(171);
        root.getChildren().add(selectSearchFrom);

        CheckBox checkCoop = new CheckBox();
        checkCoop.setFont(new Font(18));
        checkCoop.setPrefSize(26, 27);
        checkCoop.setLayoutX(59);
        checkCoop.setLayoutY(195);
        root.getChildren().add(checkCoop);

        Text labelCoop = new Text("Coop");
        labelCoop.setX(57);
        labelCoop.setY(245);
        root.getChildren().add(labelCoop);

        CheckBox checkMaxima = new CheckBox();
        checkMaxima.setFont(new Font(18));
        checkMaxima.setPrefSize(26, 27);
        checkMaxima.setLayoutX(134);
        checkMaxima.setLayoutY(195);
        root.getChildren().add(checkMaxima);

        Text labelMaxima = new Text("Maxima");
        labelMaxima.setX(127);
        labelMaxima.setY(245);
        root.getChildren().add(labelMaxima);

        CheckBox checkPrisma = new CheckBox();
        checkPrisma.setFont(new Font(18));
        checkPrisma.setPrefSize(26, 27);
        checkPrisma.setLayoutX(209);
        checkPrisma.setLayoutY(195);
        root.getChildren().add(checkPrisma);

        Text labelPrisma = new Text("Prisma");
        labelPrisma.setX(205);
        labelPrisma.setY(245);
        root.getChildren().add(labelPrisma);

        CheckBox checkRimi = new CheckBox();
        checkRimi.setFont(new Font(18));
        checkRimi.setPrefSize(26, 27);
        checkRimi.setLayoutX(284);
        checkRimi.setLayoutY(195);
        root.getChildren().add(checkRimi);

        Text labelRimi = new Text("Rimi");
        labelRimi.setX(285);
        labelRimi.setY(245);
        root.getChildren().add(labelRimi);

        CheckBox checkSelver = new CheckBox();
        checkSelver.setFont(new Font(18));
        checkSelver.setPrefSize(26, 27);
        checkSelver.setLayoutX(359);
        checkSelver.setLayoutY(195);
        root.getChildren().add(checkSelver);

        Text labelSelver = new Text("Selver");
        labelSelver.setX(356);
        labelSelver.setY(245);
        root.getChildren().add(labelSelver);

        // TODO: search by coordinates if reserved button remains unused

        Button beginSearch = new Button("OTSI");
        beginSearch.setFont(new Font(28));
        beginSearch.setPrefSize(346, 60);
        beginSearch.setLayoutX(53);
        beginSearch.setLayoutY(421);

        beginSearch.setOnAction(event -> {

            System.out.println("Begin search ...");

            File userList = new File("data/userdata/" + user.getUsername() + "/" + user.getListFileName());

            try (Scanner scan = new Scanner(new FileInputStream(userList))) {
                user.clearList();
                while (scan.hasNextLine()) {
                    String item = scan.nextLine();
                    user.addToList(item);
                }
            } catch (FileNotFoundException ignored) {}

            ArrayList<HashMap<String, ArrayList<Product>>> result = beginSearch(user.getShoppinglist(), checkCoop.isSelected(), checkMaxima.isSelected(),
                    checkPrisma.isSelected(), checkRimi.isSelected(), checkSelver.isSelected());

            System.out.println("DONE");



            for (HashMap<String, ArrayList<Product>> map : result) {

                System.out.println("TOODE: " + map.keySet());

                for (ArrayList<Product> products : map.values()) {
                    products.forEach(System.out::println);
                }

                System.out.println();

            }


        });

        root.getChildren().add(beginSearch);

        Button goBack = new Button("Mine tagasi");
        goBack.setPrefSize(107, 25);
        goBack.setLayoutX(169);
        goBack.setLayoutY(491);

        goBack.setOnAction(event -> {
            scene.setRoot(mainMenuPage());
        });

        root.getChildren().add(goBack);

        return root;

    }

    private static Group menuViewProfile() {

        Group root = new Group();

        Text title = new Text("Profiil");
        title.setFont(new Font(28));
        title.setX(190);
        title.setY(84);
        root.getChildren().add(title);

        Button goBack = new Button("Mine tagasi");
        goBack.setPrefSize(329, 25);
        goBack.setLayoutX(60);
        goBack.setLayoutY(458);
        root.getChildren().add(goBack);

        return root;

    }

}