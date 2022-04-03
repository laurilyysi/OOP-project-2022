// TODO: 4/1/2022 Perhaps change language on either websites to english or
//  when making user interface, make it in estonian, as to avoid merging languages.
package POC;
import java.util.List;
public class Main {
    public static void main(String[] args){

        // keyword to search from different stores
        String keyword = "makaron";

        long startTime1 = System.currentTimeMillis();
        List<Product> selverid = Selver.searchProducts(keyword);
        for (Product prod : selverid) System.out.println(prod.toString());
        System.out.println("Size: " + selverid.size());
        long estimatedTime1 = System.currentTimeMillis() - startTime1;

        long startTime2 = System.currentTimeMillis();
        List<Product> coopid = Coop.searchProducts(keyword);
        for (Product prod : coopid) System.out.println(prod.toString());
        System.out.println("Size: " + coopid.size());
        long estimatedTime2 = System.currentTimeMillis() - startTime2;

        long startTime3 = System.currentTimeMillis();
        List<Product> prismad = Prisma.searchProducts(keyword);
        for (Product prod : prismad) System.out.println(prod.toString());
        System.out.println("Size: " + prismad.size());
        long estimatedTime3 = System.currentTimeMillis() - startTime3;

        long startTime4 = System.currentTimeMillis();
        List<Product> rimid = Rimi.searchProducts(keyword);
        for (Product prod : rimid) System.out.println(prod.toString());
        System.out.println("Size: " + rimid.size());
        long estimatedTime4 = System.currentTimeMillis() - startTime4;

        long startTime5 = System.currentTimeMillis();
        List<Product> maximad = Maxima.searchProducts(keyword);
        for (Product prod : maximad) System.out.println(prod.toString());
        System.out.println("Size: " + maximad.size());
        long estimatedTime5 = System.currentTimeMillis() - startTime5;

        System.out.println();

        System.out.println("Selver: " + estimatedTime1);
        System.out.println("Coop: " + estimatedTime2);
        System.out.println("Prisma: " + estimatedTime3);
        System.out.println("Rimi: " + estimatedTime4);
        System.out.println("Maxima: " + estimatedTime5);

    }
}
