package POC;

import java.util.List;

public class Worker implements Runnable{
    StoreName storeName;
    String product;
    List<Product> products;

    public Worker(StoreName storeName, String product) {
        this.storeName = storeName;
        this.product = product;
    }

    @Override
    public void run() {
        if (storeName == StoreName.coop) {
            products = Coop.searchProducts(product);
        }
        if (storeName == StoreName.maxima){
            products = Maxima.searchProducts(product);
        }
        if (storeName == StoreName.prisma) {
            products = Prisma.searchProducts(product);
        }
        if (storeName == StoreName.rimi) {
            products = Rimi.searchProducts(product);
        }
        if (storeName == StoreName.selver) {
            products = Selver.searchProducts(product);
        }
        System.out.println(storeName);
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
