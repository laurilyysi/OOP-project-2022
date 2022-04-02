package POC;

public class RimiDiscountRed extends RimiProduct {

    // Class for products that have a regular discount (price is red)

    private double regularPrice;

    public RimiDiscountRed(String store, String name, double price, boolean onSale, String imgURL, double regularPrice) {
        super(store, name, price, onSale, imgURL);
        this.regularPrice = regularPrice;
    }

    @Override
    public String toString() {
        return "[C] " + getPrice() + " €\t" + getName() + " [regular price: " + regularPrice + " €]";
    }

}
