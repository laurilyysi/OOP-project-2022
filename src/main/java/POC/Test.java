package POC;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<Product> list = Prisma.searchProducts("banaan");
        for (Product prod : list) {
            System.out.println(prod.toString());
        }

    }
}
