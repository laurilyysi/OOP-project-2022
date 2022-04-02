package POC;

public class CoopDiscountYellow extends CoopProduct {

    // Class for products that have a regular discount (yellow background)

    private final double regularPrice;

    CoopDiscountYellow(String store, String name, double price, boolean onSale, String imgURL, double regularPrice) {
        super(store, name, price, onSale, imgURL);
        this.regularPrice = regularPrice;
    }

    @Override
    public String toString() {
        return "[Y] " + getPrice() + " €\t" + getName() + " [regular price: " + regularPrice + " €]";
    }

}
