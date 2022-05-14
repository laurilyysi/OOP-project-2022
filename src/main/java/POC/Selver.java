package POC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Selver {

    // change this variable manually to see info during runtime
    // true - program outputs status info into the console
    // false - no system output (preferred when not testing)
    private static final boolean debug = true;

    public static List<Product> searchProducts(String keyword) {
        List<Product> allProducts = new ArrayList<>();

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        String url = "https://www.selver.ee/search?q=" + keyword;
        driver.get(url);

        int pageCount = 1;
        String xpath = "/html/body/div[1]/div/div[1]/div[4]/main/div/div[2]";

        try {
            do {

                if (debug) System.out.println("[Selver] Page (" + pageCount + "/3)");

                while (!waitFor(driver, xpath)) waitFor(driver, xpath);

                WebElement productsView = driver.findElement(By.className("products-view"));
                List<WebElement> items = productsView.findElements(By.className("ProductCard"));

                for (WebElement item : items) {
                    String imgURL = item.findElement(By.tagName("img")).getAttribute("src");
                    WebElement info = item.findElement(By.className("ProductCard__info"));
                    String name = info.findElement(By.className("ProductCard__link")).getText();
                    String priceString = info.findElement(By.className("ProductPrice")).getText();
                    double price = Double.parseDouble(priceString.split(" ")[0].replace(",", "."));
                    String link = item.findElement(By.tagName("a")).getAttribute("href");

                    if (item.findElements(By.className("ProductBadge__badge--label")).size() > 0) {
                        WebElement badge = item.findElement(By.className("ProductBadge__badge--label"));

                        if (!badge.getText().equals("")) {
                            double salePrice = Double.parseDouble(badge.getText().replace(",", ".").replace(" €", ""));
                            allProducts.add(new Product("Selver", name, salePrice, DiscountType.discountCard, imgURL, price, link));
                        }
                    } else {
                        allProducts.add(new Product("Selver", name, price, DiscountType.noDiscount, imgURL, price, link));
                    }
                }
                pageCount++;
            } while (nextPage(driver) && pageCount < 4);
        } catch (Exception e) {
            System.out.println("[Selver] " + e.getClass().getSimpleName());
        }

        driver.quit();
        return allProducts;

    }

    public static boolean nextPage(WebDriver driver) {
        String xpath = "/html/body/div[1]/div/div[1]/div[4]/main/div/header/nav/div[2]";
        try {
            if (driver.findElements(By.xpath(xpath)).size() > 0) {
                WebElement nextButton = driver.findElement(By.xpath(xpath));
                nextButton.click();
                return true;
            }
        } catch (ElementNotInteractableException e) {
            if (debug) System.out.println("[Selver] No more pages");
            return false;
        }
        return false;
    }

    public static boolean waitFor(WebDriver driver, String xpath) {
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofMillis(1))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            if (debug) System.out.println("[Selver] Loaded");
            return true;
        } catch (TimeoutException e) {
            if (debug) System.out.println("[Selver] Didn't load, retry..");
            return false;
        }
    }


}
