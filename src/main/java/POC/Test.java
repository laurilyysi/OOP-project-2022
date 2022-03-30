package POC;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<Product> listCoop = Coop.searchProducts("leib");

        for (Product prod : listCoop) System.out.println(prod.toString());
        System.out.println(listCoop.size());

        List<Product> listPrisma = Prisma.searchProducts("makaron");

        for (Product prod : listPrisma) System.out.println(prod.toString());
        System.out.println(listPrisma.size());
    }
}
