package POC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class Rimi implements Store {
    // change this variable manually to see info during runtime
    // true - program outputs status info into the console
    // false - no system output (preferred when not testing)
    private static final boolean debug = true;

    public static List<Product> searchProducts(String keyword) {
        // Returns a list of the first 100 products on a given page
        List<Product> allProducts = new ArrayList<>();

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        String url = "https://www.rimi.ee/epood/ee/otsing?page=1&pageSize=100&query=" + keyword;
        driver.get(url);

        // Accepts cookies (although it doesn't seem to be necessary)
        try {
            WebElement element = new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[6]/a[3]")));
            WebElement acceptCookies = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[6]/a[3]"));
            acceptCookies.click();
            if (debug) System.out.println("[Rimi] Cookies accepted");
        } catch (Exception e) {
            if (debug) System.out.println("[Rimi] No cookie popup, ignored");
        }

        WebElement productGrid = driver.findElement(By.className("product-grid"));
        List<WebElement> items = productGrid.findElements(By.className("product-grid__item"));

        for (WebElement item : items) {

            String imgURL = item.findElement(By.tagName("img")).getAttribute("src");
            String name = item.findElement(By.className("card__name")).getText();
            WebElement priceInfo = item.findElement(By.className("card__details-inner"));
            String link = item.findElement(By.tagName("a")).getAttribute("href");

            double price = -1;

            if (!priceInfo.findElement(By.className("card__price-per")).getText().equals("Ei ole saadaval")) {
                // Statement to handle items marked unavailable
                String integer = priceInfo.findElement(By.tagName("span")).getText();
                String decimal = priceInfo.findElement(By.tagName("sup")).getText();
                price = Double.parseDouble(integer + "." + decimal);
            } else if (debug) System.out.println("[Rimi] Product marked unavailable: " + name);
            // Makes product objects based on discount
            if (item.findElements(By.className("-has-discount")).size() > 0) {
                if (priceInfo.findElements(By.className("old-price-tag")).size() > 0) {
                    // Sometimes the old price is not displayed
                    WebElement oldPriceTag = priceInfo.findElement(By.className("old-price-tag"));
                    double oldPrice = Double.parseDouble(oldPriceTag.getText().replace(",", ".").replace("€", ""));
                    allProducts.add(new Product("Rimi", name, price, DiscountType.campaign, imgURL, oldPrice, link));
                } else allProducts.add(new Product("Rimi", name, price, DiscountType.campaign, imgURL, price, link));
            }

            else if (item.findElements(By.className("price-badge")).size() > 0) {
                WebElement priceBadge = item.findElement(By.className("price-badge__price"));
                List<WebElement> saleInfo = priceBadge.findElements(By.tagName("span"));
                double salePrice = Double.parseDouble(saleInfo.get(0).getText() + "." + saleInfo.get(1).getText());
                allProducts.add(new Product("Rimi", name, salePrice, DiscountType.discountCard, imgURL, price, link));
            }

            else if (price != -1) {
                allProducts.add(new Product("Rimi", name, price, DiscountType.noDiscount, imgURL, price, link));
            }
        }

        driver.quit();
        return allProducts;

    }

}
