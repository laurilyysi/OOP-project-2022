package POC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Coop implements Store {

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

        //todo find out if this way is faster/doable and implement if so
        // driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);

        List<Product> allProducts = new ArrayList<>();
        List<Product> pageProducts;

        //Accepts cookies, so it would be easier to navigate on website
        try {
            WebElement element = new WebDriverWait(driver, 1).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/div/app-main-footer/footer/div[3]/button")));
            WebElement acceptCookies = driver.findElement(By.xpath("/html/body/app-root/div/div/app-main-footer/footer/div[3]/button"));
            acceptCookies.click();
        } catch (Exception ignore){}//there is no button to accept cookies, sometimes ecoop is like that.

        int page = 1;

        do {

            try {
                waitForBox(driver);
                pageProducts = scrapeBox(driver);
                allProducts.addAll(pageProducts);
            } catch (StaleElementReferenceException e) {
                System.out.println("EXCEPTION");
                waitForBox(driver);
                pageProducts = scrapeBox(driver);
                allProducts.addAll(pageProducts);
            }finally {
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

            // Determining if product is on sale,
            //   sale = 0 -> product is not on sale (white background, no discount)
            //   sale = 1 -> product is on sale (yellow background, regular discount)
            //   sale = 2 -> product is on sale with Säästukaart (blue background, discount with card)

            int sale = -1;
            String priceTag = item.findElement(By.tagName("app-price-tag")).getAttribute("class");
            if (priceTag.equals("regular")) sale = 0;
            else if (priceTag.equals("discount")) sale = 1;
            else if (priceTag.equals("discount-card")) sale = 2;

            products.add(new CoopProduct("Coop", name, price, sale, imgURL));

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
