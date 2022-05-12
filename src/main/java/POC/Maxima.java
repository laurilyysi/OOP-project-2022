package POC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class Maxima implements Store {

    // change this variable manually to see info during runtime
    // true - program outputs status info into the console
    // false - no system output (preferred when not testing)
    private static final boolean debug = true;

    public static List<Product> searchProducts(String keyword) {
        List<Product> allProducts = new ArrayList<>();

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        String url = "https://barbora.ee/otsing?q=" + keyword;
        driver.get(url);

        WebElement productsWrapper = driver.findElement(By.className("b-products-list--wrapper"));
        List<WebElement> items = productsWrapper.findElements(By.className("b-product--wrap"));

            for (WebElement item : items) {
                if (!(item.findElements(By.className("b-product-unavailable--wrap")).size() > 0)) {

                    String imgURL = item.findElement(By.tagName("img")).getAttribute("src");
                    String[] info = item.getText().split("\n");
                    String name = info[0];
                    String link = item.findElement(By.tagName("a")).getAttribute("href");

                    if (item.findElements(By.className("b-product-promo-label-primary")).size() > 0) {
                        String[] prices = info[1].replaceAll("€", "").split(" ");
                        double oldPrice = Double.parseDouble(prices[0]);
                        double salePrice = Double.parseDouble(prices[1]);
                        allProducts.add(new Product("Maxima", name, salePrice, DiscountType.campaign, imgURL, oldPrice, link));
                    }
                    else {
                        double price = Double.parseDouble(info[1].replace("€", ""));
                        allProducts.add(new Product("Maxima", name, price, DiscountType.noDiscount, imgURL, price, link));
                    }

                }
                else {
                    if (debug) System.out.println("[Maxima] Product unavailable");
                }
            }

        driver.quit();
        return allProducts;
    }

}
