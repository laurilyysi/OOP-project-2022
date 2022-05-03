package POC;

// import com.example.fxUI.OptionsController;

import java.util.List;

public class Worker implements Runnable{
    StoreName storeName;
    String product;
    List<Product> products;
    Product cheapestProduct;
    double cheapestPrice = Double.MAX_VALUE;

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
            if (product.getPrice() < cheapestPrice) {
                cheapestProduct = product;
            }
        }

        // OptionsController.addToCheapestProductsArrayList(cheapestProduct);
        System.out.println(cheapestProduct.toString());
    }


}
