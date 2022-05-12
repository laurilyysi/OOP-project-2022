package POC;

import javafx.scene.image.Image;

public class Product implements Comparable<Product> {
    private final String store;
    private DiscountType discountType;
    private final String name;
    private final double currentPrice;
    private double preSalePrice;
    private String imgURL;
    private String link;

    // private final Image image;

    Product(String store, String name, double price, DiscountType discountType, String imgURL, double preSalePrice, String link) {
        this.store = store;
        this.name = name;
        this.currentPrice = price;
        this.discountType = discountType;
        this.imgURL = imgURL;
        this.preSalePrice = preSalePrice;
        this.link = link;
        // this.image = new Image(imgURL, 90, 90, true, false);
    }

    public String getName() {
        return name;
    }

    public String getStore() {
        return store;
    }

    public double getPrice() {
        return currentPrice;
    }

    public String getImgURL() {
        return imgURL;
    }

    /*
    public Image getImage() {
        return image;
    }
    */

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getPreSalePrice() {
        return preSalePrice;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        if (discountType == DiscountType.noDiscount) {
            return "    " + currentPrice + " €\t" + name;
        }
        if (discountType == DiscountType.campaign) {
            return "Kampaania " + currentPrice + " €\t" + getName() + " [tavahind: " + preSalePrice + " €]";
        }
        return "Kliendikaardiga " + currentPrice + " €\t" + getName() + " [tavahind: " + preSalePrice + " €]";
    }

    @Override
    public int compareTo(Product o) {
        return Double.compare(currentPrice, o.currentPrice);
    }
}
