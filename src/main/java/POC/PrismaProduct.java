package POC;

public class PrismaProduct implements Product {

    private final String store;
    private final String name;
    private final double price;
    private final boolean onSale;
    private final String imgURL;

    PrismaProduct(String store, String name, double price, boolean onSale, String imgURL) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.onSale = onSale;
        this.imgURL = imgURL;
    }

    public double getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }

    public boolean isOnSale() {
        return onSale;
    }

    @Override
    public String toString() {
        return "    " + price + " €\t" + name;
    }

}
