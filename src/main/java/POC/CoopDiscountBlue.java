package POC;

public class CoopDiscountBlue extends CoopProduct {

    // Class for products that are discounted via Säästukaart (blue background)

    private final double regularPrice;

    CoopDiscountBlue(String store, String name, double price, boolean onSale, String imgURL, double regularPrice) {
        super(store, name, price, onSale, imgURL);
        this.regularPrice = regularPrice;
    }

    @Override
    public String toString() {
        return "[B] " + getPrice() + " €\t" + getName() + " [regular price: " + regularPrice + " €]";
    }



}
