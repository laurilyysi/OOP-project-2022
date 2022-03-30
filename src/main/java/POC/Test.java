package POC;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<Product> list = Coop.searchProducts("leib");
        if (list.size()==0){
            System.out.println("No products found");
        }
        for (Product prod : list) System.out.println(prod.toString());
        System.out.println(list.size());

    }
}
