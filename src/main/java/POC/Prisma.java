package POC;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Prisma implements Store {

    public static List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<Product>();

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        String url = "https://www.prismamarket.ee/products/search/" + keyword;
        driver.get(url);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement shelf = driver.findElement(By.className("js-shelf"));
        List<WebElement> items = shelf.findElements(By.className("js-shelf-item"));

        for (WebElement item : items) {

            WebElement img = item.findElement(By.className("js-image-wrapper"));
            String imgURL = img.findElement(By.tagName("img")).getAttribute("src");
            String name = item.findElement(By.className("name")).getText();
            String integer = item.findElement(By.className("whole-number")).getText();
            String cents = item.findElement(By.className("decimal")).getText();
            double price = Double.parseDouble(integer + "." + cents);

            products.add(new PrismaProduct("Prisma", name, price, false, imgURL));
        }

        driver.quit();

        return products;
    }

}
