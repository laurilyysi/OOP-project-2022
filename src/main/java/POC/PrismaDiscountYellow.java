package POC;

public class PrismaDiscountYellow extends PrismaProduct {

    // Class for products with a discount (yellow background), saves regular price

    private final double regularPrice;

    PrismaDiscountYellow(String store, String name, double price, boolean onSale, String imgURL, double regularPrice) {
        super(store, name, price, onSale, imgURL);
        this.regularPrice = regularPrice;
    }

    @Override
    public String toString() {
        return "[Y] " + getPrice() + " €\t" + getName() + " [regular price: " + regularPrice + "]";
    }

}
