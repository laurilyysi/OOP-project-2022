package POC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchResults {

    private static ArrayList<HashMap<String, List<Product>>> products;

    public SearchResults(ArrayList<HashMap<String, List<Product>>> products) {
        SearchResults.products = products;
    }

    public static ArrayList<HashMap<String, List<Product>>> getProducts() {
        return products;
    }

}
