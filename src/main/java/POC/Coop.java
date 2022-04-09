package POC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class Coop implements Store {

    // change this variable manually to see info during runtime
    // true - program outputs status info into the console
    // false - no system output (preferred when not testing)
    private static final boolean debug = true;

    public static WebDriver initializeDriver() {
        // Creates a WebDriver object for further actions
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    public static List<Product> searchProducts(String keyword) {
        // Returns a list of products that have the given keyword
        // Only checks the first 3 pages of for keyword

        WebDriver driver = initializeDriver();
        String url = "https://ecoop.ee/et/otsing?query=" + keyword;
        driver.get(url);

        List<Product> allProducts = new ArrayList<>();
        List<Product> pageProducts;

        //Accepts cookies, so it would be easier to navigate on website
        try {
            WebElement element = new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/div/app-main-footer/footer/div[3]/button")));
            WebElement acceptCookies = driver.findElement(By.xpath("/html/body/app-root/div/div/app-main-footer/footer/div[3]/button"));
            acceptCookies.click();
            if (debug) System.out.println("[Coop] Cookies accepted");
        } catch (Exception ignore) {
            if (debug) System.out.println("[Coop] No cookie popup, ignored");
        }//there is no button to accept cookies, sometimes ecoop is like that.

        int page = 1;

        do {
            try {
                if (debug) System.out.println("[Coop] Page (" + page + "/3)");
                waitForBox(driver);
                pageProducts = scrapeBox(driver);
                allProducts.addAll(pageProducts);
            } catch (Exception e) {
                if (debug) System.out.println("[Coop] Exception: " + e.getClass().getSimpleName());
                waitForBox(driver);
                pageProducts = scrapeBox(driver);
                allProducts.addAll(pageProducts);
            } finally {
                page++;
            }

        } while (nextPage(driver) && page < 4);

        driver.quit();

        return allProducts;
    }

    public static List<Product> scrapeBox(WebDriver driver) {
        // Scrapes the container "products-outer-wrapper" for a given page
        // Returns a list of products per page

        List<Product> products = new ArrayList<>();

        WebElement productsOuterWrapper = driver.findElement(By.className("products-outer-wrapper"));
        List<WebElement> items = productsOuterWrapper.findElements(By.tagName("app-product-card"));

        for (WebElement item : items) {

            WebElement img = item.findElement(By.className("product-img-wp"));
            String imgURL = img.findElement(By.tagName("img")).getAttribute("src");
            String name = item.findElement(By.className("product-name")).getText();
            String integer = item.findElement(By.className("integer")).getText();
            String cents = item.findElement(By.className("decimal")).getText().split(" ")[0];
            double price = Double.parseDouble(integer + "." + cents);

            // Determining if product is on sale
            String priceTag = item.findElement(By.tagName("app-price-tag")).getAttribute("class");

            // If discount is via Säästukaart, creates a new DiscountProduct and saves regular price
            if (priceTag.equals("discount-card") || priceTag.equals("discount")) {
                WebElement pricesInfo = item.findElement(By.className("prices-info"));
                double regularPrice = Double.parseDouble(pricesInfo.getText().split(" ")[0]);

                if (priceTag.equals("discount-card"))
                    products.add(new Product("Coop", name, price, DiscountType.discountCard, imgURL, regularPrice));
                if (priceTag.equals("discount")) {
                    products.add(new Product("Coop", name, price, DiscountType.campaign, imgURL, regularPrice));
                }
            }
            // If discount is regular
            else {
                products.add(new Product("Coop", name, price, DiscountType.noDiscount, imgURL, price));
            }

        }
        return products;
    }

    public static boolean nextPage(WebDriver driver) {
        // goes to next page and returns true if next page exists, else returns false
        WebElement nextButton = driver.findElement(By.xpath(
                "//*[@id=\"body\"]/app-root/div/div/ais-instantsearch/" +
                "div/app-search/div/ais-hits/div/app-pagination/button[2]"));

        if (nextButton.isEnabled()) {
            nextButton.click();
            waitForBox(driver);
            return true;
        }

        return false;

    }

    public static void waitForBox(WebDriver driver) {
        // Waits for the "products-outer-wrapper" container to load before scraping
        WebDriverWait wait = new WebDriverWait(driver, 1);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("products-outer-wrapper")));
    }


}