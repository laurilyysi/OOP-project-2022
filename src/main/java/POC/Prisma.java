package POC;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Double.parseDouble;

public class Prisma implements Store {
    // TODO: 3/30/2022 Suggestion to make products that can be found by
    //  this method, be searched by the links in prismamarket.ee that can
    //  be accessed in the searchbar on the left side of the website. Products like these are "makaron", "riis", "Krõpsud" and others.
    //  This method would remove all unrelated products on these searches
    public static List<Product> searchProducts(String keyword) {
        //Returns a list of products from prismamarket.ee with given keyword
        List<Product> products = new ArrayList<Product>();

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        String url = "https://www.prismamarket.ee/products/search/" + keyword;
        driver.get(url);

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);

        List<WebElement> shelfs = driver.findElements(By.className("js-products-shelf"));//Products are split into different categories in prismamarket.ee

        for (WebElement shelf : shelfs) {
            List<WebElement> items = shelf.findElements(By.className("js-shelf-item"));

            for (WebElement item : items) {
                boolean onSale = false;
                String preSalePriceString = "0";

                WebElement img = item.findElement(By.className("js-image-wrapper"));
                String imgURL = img.findElement(By.tagName("img")).getAttribute("src");
                String name = item.findElement(By.className("name")).getText();

                // TODO: 3/30/2022 Fix: finding out if product is on sale
                try {
                    preSalePriceString = (item.findElement(By.className("discount-price")).getText());
                    onSale = true;
                }catch (Exception ignore) {}

                String integer = item.findElement(By.className("whole-number")).getText();
                String cents = item.findElement(By.className("decimal")).getText();
                double price = parseDouble(integer + "." + cents);
                double preSalePrice = parseDouble(preSalePriceString.replace(",", ".").replace(" €", ""));

                if (onSale) {
                    products.add(new PrismaProduct("Prisma", name, price, onSale, preSalePrice, imgURL));
                }
                else {
                    products.add(new PrismaProduct("Prisma", name, price, onSale, price, imgURL));
                }
            }
        }

        driver.quit();

        return products;
    }

}
