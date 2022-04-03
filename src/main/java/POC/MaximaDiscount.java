package POC;

public class MaximaDiscount extends MaximaProduct {

    private double regularPrice;

    public MaximaDiscount(String store, String name, double price, boolean onSale, String imgURL, double regularPrice) {
        super(store, name, price, onSale, imgURL);
        this.regularPrice = regularPrice;
    }

    @Override
    public String toString() {
        return "[O] " + getPrice() + " €\t" + getName() + " [regular price: " + regularPrice + " €]";
    }

}
