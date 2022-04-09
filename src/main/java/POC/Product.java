package POC;

public class Product {
    private final String store;
    private final DiscountType discountType;
    private final String name;
    private final double currentPrice;
    private final double preSalePrice;
    private final String imgURL;

    Product(String store, String name, double price, DiscountType discountType, String imgURL, double preSalePrice) {
        this.store = store;
        this.name = name;
        this.currentPrice = price;
        this.discountType = discountType;
        this.imgURL = imgURL;
        this.preSalePrice = preSalePrice;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return currentPrice;
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
}
