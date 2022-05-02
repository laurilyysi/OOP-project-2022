module com.example.oopprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.bonigarcia.webdrivermanager;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.support;


    opens com.example.GUI to javafx.fxml;
    exports com.example.GUI;
}