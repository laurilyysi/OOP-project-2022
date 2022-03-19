package POC;

public class CoopProduct implements Product {

    private String store;
    private String name;
    private double price;
    private boolean onSale;
    private String imgURL;

    CoopProduct(String store, String name, double price, boolean onSale, String imgURL) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.onSale = onSale;
        this.imgURL = imgURL;
    }

    @Override
    public String toString() {
        return price + " €\t" + name;
    }
}
