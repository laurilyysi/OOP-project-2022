package POC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        WebDriver driver = new ChromeDriver();

        String url = "https://www.selver.ee/search?q=" + keyword;
        driver.get(url);

        int pageCount = 1;

        try {
            do {

                if (debug) System.out.println("[Selver] Page (" + pageCount + "/3)");
                while (!waitForBox(driver)) waitForBox(driver);

                WebElement productsView = driver.findElement(By.className("products-view"));
                List<WebElement> items = productsView.findElements(By.className("ProductCard"));

                for (WebElement item : items) {
                    String imgURL = item.findElement(By.tagName("img")).getAttribute("src");
                    WebElement info = item.findElement(By.className("ProductCard__info"));
                    String name = info.findElement(By.className("ProductCard__link")).getText();
                    String priceString = info.findElement(By.className("ProductPrice")).getText();
                    double price = Double.parseDouble(priceString.split(" ")[0].replace(",", "."));

                    if (item.findElements(By.className("ProductBadge__badge--label")).size() > 0) {
                        WebElement badge = item.findElement(By.className("ProductBadge__badge--label"));

                        if (!badge.getText().equals("")) {
                            double salePrice = Double.parseDouble(badge.getText().replace(",", ".").replace(" €", ""));
                            allProducts.add(new SelverDiscountBadge("Selver", name, salePrice, true, imgURL, price));
                        }
                    } else {
                        allProducts.add(new SelverProduct("Selver", name, price, false, imgURL));
                    }
                }
                pageCount++;
            } while (nextPage(driver) && pageCount < 4);
        } catch (StaleElementReferenceException e) {
            System.out.println("[Selver] StaleElementReferenceException");
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

    public static boolean waitForBox(WebDriver driver) {
        try {
            WebElement element = new WebDriverWait(driver, 1)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div[1]/div[4]/main/div/div[2]")));
            if (debug) System.out.println("[Selver] Box loaded");
            return true;
        } catch (TimeoutException e) {
            if (debug) System.out.println("[Selver] Box didn't load, retry..");
            return false;
        }
    }


}
