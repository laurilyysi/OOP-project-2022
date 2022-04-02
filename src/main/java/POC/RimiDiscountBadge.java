package POC;

public class RimiDiscountBadge extends RimiProduct {

    // Class for products that are discounted via Rimi kaart (card badge icon on product container)

    private double regularPrice;

    public RimiDiscountBadge(String store, String name, double price, boolean onSale, String imgURL, double regularPrice) {
        super(store, name, price, onSale, imgURL);
        this.regularPrice = regularPrice;
    }

    @Override
    public String toString() {
        return "[R] " + getPrice() + " €\t" + getName() + " [regular price: " + regularPrice + " €]";
    }


}
