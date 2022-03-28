package POC;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<Product> list = Coop.searchProducts("makaron");
        for (Product prod : list) System.out.println(prod.toString());
        System.out.println(list.size());

    }
}
