package POC;

public class PrismaProduct implements Product {

    private final String store;
    private final String name;
    private final double price;
    private final boolean onSale;
    private final double preSalePrice;
    private final String imgURL;

    PrismaProduct(String store, String name, double price, boolean onSale, double preSalePrice, String imgURL) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.onSale = onSale;
        this.preSalePrice = preSalePrice;
        this.imgURL = imgURL;
    }

    public double getPrice() {
        return price;
    }

    public boolean isOnSale() {
        return onSale;
    }

    @Override
    public String toString() {
        if (onSale) {
            return "SALE " + price + " €\t" + name + ", presale price was " + preSalePrice;
        }
        return "     " + price + " €\t" + name;
    }

}
