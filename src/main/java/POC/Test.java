package POC;
import java.util.ArrayList;
import java.util.List;
public class Test {
    public static void main(String[] args) throws Exception{

        List<StoreName> storeNames = new ArrayList<>();
        storeNames.add(StoreName.coop);
        storeNames.add(StoreName.maxima);
        storeNames.add(StoreName.prisma);
        storeNames.add(StoreName.rimi);
        storeNames.add(StoreName.selver);

        ArrayList<String> products = new ArrayList<>();
        products.add("makaron");
        products.add("sai");
        products.add("piim");

        ArrayList<Thread> listOfWorkers = new ArrayList<>();


        for (int i=0;i<5;i++){
            for (String product : products) {
                Thread worker = new Thread(new Worker(storeNames.get(i), product));
                listOfWorkers.add(worker);
                worker.start();
            }
        }

        for (Thread worker : listOfWorkers) {
            worker.join();
        }
/*
        List<Product> listCoop = Coop.searchProducts("makaron");
        double priceCoop;
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
        System.out.println("Neist odavaim on " + cheapestProductCoop.toString());

        List<Product> listPrisma = Prisma.searchProducts("makaron");
        double price;
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
        System.out.println("Neist odavaim on " + cheapestProduct.toString());
        System.out.println(listPrisma.size());

        List<Product> listSelver = Selver.searchProducts("makaron");
        double priceSelver;
        double cheapestPriceSelver = Double.MAX_VALUE;
        Product cheapestProductSelver = listSelver.get(0);
        for (Product prod : listSelver) {
            System.out.println(prod.toString());
            priceSelver = prod.getPrice();
            if (priceSelver <= cheapestPriceSelver){
                cheapestProductSelver = prod;
                cheapestPriceSelver = priceSelver;
            }
        }
        System.out.println("Neist odavaim on " + cheapestProductSelver.toString());
        System.out.println(listSelver.size());

        List<Product> listMaxima = Maxima.searchProducts("makaron");
        double priceMaxima;
        double cheapestPriceMaxima = Double.MAX_VALUE;
        Product cheapestProductMaxima = listSelver.get(0);
        for (Product prod : listMaxima) {
            System.out.println(prod.toString());
            priceMaxima = prod.getPrice();
            if (priceMaxima <= cheapestPriceMaxima){
                cheapestProductMaxima = prod;
                cheapestPriceMaxima = priceMaxima;
            }
        }
        System.out.println("Neist odavaim on " + cheapestProductMaxima.toString());
        System.out.println(listMaxima.size());

        List<Product> listRimi = Rimi.searchProducts("makaron");
        double priceRimi;
        double cheapestPriceRimi = Double.MAX_VALUE;
        Product cheapestProductRimi = listSelver.get(0);
        for (Product prod : listRimi) {
            System.out.println(prod.toString());
            priceRimi = prod.getPrice();
            if (priceRimi <= cheapestPriceRimi){
                cheapestProductRimi = prod;
                cheapestPriceRimi = priceRimi;
            }
        }
        System.out.println("Neist odavaim on " + cheapestProductRimi.toString());
        System.out.println(listRimi.size());

 */
    }
}
