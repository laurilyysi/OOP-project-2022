package POC;

public class CoopProduct implements Product {

    private String store;
    private String name;
    private double price;
    private int sale;
    private String imgURL;

    CoopProduct(String store, String name, double price, int sale, String imgURL) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.sale = sale;
        this.imgURL = imgURL;
    }

    public String saleInfo() {
        return switch (sale) {
            case 0 -> "    ";
            case 1 -> "[Y] "; // yellow background
            case 2 -> "[B] "; // blue background
            default -> "[E] "; // ERROR unexpected
        };
    }

    @Override
    public String toString() {
        return saleInfo() + price + " €\t" + name;
    }

}
