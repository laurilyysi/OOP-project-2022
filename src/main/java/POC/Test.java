// TODO: 4/1/2022 Perhaps change language on either websites to english or
//  when making user interface, make it in estonian, as to avoid merging languages.
package POC;
import java.util.List;
public class Test {
    public static void main(String[] args){

        List<Product> listCoop = Coop.searchProducts("leib");

        double priceCoop = 0;
        double cheapestPriceCoop = Double.MAX_VALUE;
        Product cheapestProductCoop = listCoop.get(0);
        for (Product prod : listCoop) {
            System.out.println(prod.toString());
            priceCoop = prod.getPrice();
            if (priceCoop <= cheapestPriceCoop){
                cheapestProductCoop = prod;
                cheapestPriceCoop = priceCoop;
            }
        }
        System.out.println(listCoop.size());
        System.out.println("The cheapest of these is " + cheapestProductCoop.toString());

        List<Product> listPrisma = Prisma.searchProducts("leib");

        double price = 0;
        double cheapestPrice = Double.MAX_VALUE;
        Product cheapestProduct = listPrisma.get(0);
        for (Product prod : listPrisma) {
            System.out.println(prod.toString());
            price = prod.getPrice();
            if (price <= cheapestPrice){
                cheapestProduct = prod;
                cheapestPrice = price;
            }
        }
        System.out.println("the cheapest of these is " + cheapestProduct.toString());
        System.out.println(listPrisma.size());


    }
}
