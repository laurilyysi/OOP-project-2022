package POC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Coop implements Store {

    public static List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<Product>();

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        String url = "https://ecoop.ee/et/otsing?query=" + keyword;
        driver.get(url);

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement productsOuterWrapper = driver.findElement(By.className("products-outer-wrapper"));
        List<WebElement> items = productsOuterWrapper.findElements(By.tagName("app-product-card"));

        for (WebElement item : items) {

            WebElement img = item.findElement(By.className("product-img-wp"));
            String imgURL = img.findElement(By.tagName("img")).getAttribute("src");
            String name = item.findElement(By.className("product-name")).getText();
            String integer = item.findElement(By.className("integer")).getText();
            String cents = item.findElement(By.className("decimal")).getText().split(" ")[0];
            double price = Double.parseDouble(integer + "." + cents);

            products.add(new CoopProduct("Coop", name, price, false, imgURL));
        }

        driver.quit();
        return products;
    }

}
