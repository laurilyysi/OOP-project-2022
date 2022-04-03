package POC;

public class SelverDiscountBadge extends SelverProduct {

    // Class for products that are discounted via Partnerkaart (Orange badge icon on product container)

    private double regularPrice;

    public SelverDiscountBadge(String store, String name, double price, boolean onSale, String imgURL, double regularPrice) {
        super(store, name, price, onSale, imgURL);
        this.regularPrice = regularPrice;
    }

    @Override
    public String toString() {
        return "[O] " + getPrice() + " €\t" + getName() + " [regular price: " + regularPrice + " €]";
    }

}
