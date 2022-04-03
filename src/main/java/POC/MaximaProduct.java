package POC;

public class MaximaProduct implements Product {

    private final String store;
    private final String name;
    private final double price;
    private boolean onSale;
    private final String imgURL;

    public MaximaProduct(String store, String name, double price, boolean onSale, String imgURL) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.onSale = onSale;
        this.imgURL = imgURL;
    }

    @Override
    public String toString() {
        return "    " + price + " €\t" + name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}
