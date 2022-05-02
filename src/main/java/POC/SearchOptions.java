package POC;

import java.util.ArrayList;

import static POC.StoreName.*;

public class SearchOptions {

    private boolean searchCoop;
    private boolean searchMaxima;
    private boolean searchPrisma;
    private boolean searchRimi;
    private boolean searchSelver;

    private ArrayList<StoreName> storeNames;

    public SearchOptions(boolean searchCoop, boolean searchMaxima, boolean searchPrisma, boolean searchRimi, boolean searchSelver) {
        this.searchCoop = searchCoop;
        this.searchMaxima = searchMaxima;
        this.searchPrisma = searchPrisma;
        this.searchRimi = searchRimi;
        this.searchSelver = searchSelver;
        this.storeNames = storeNames();
    }

    public ArrayList<StoreName> storeNames() {
        ArrayList<StoreName> names = new ArrayList<>();

        if (searchCoop) names.add(coop);
        if (searchMaxima) names.add(maxima);
        if (searchPrisma) names.add(prisma);
        if (searchRimi) names.add(rimi);
        if (searchSelver) names.add(selver);

        return names;
    }

    public ArrayList<StoreName> getStoreNames() {
        return storeNames;
    }

    @Override
    public String toString() {
        return "SearchOptions{" +
                "searchCoop=" + searchCoop +
                ", searchMaxima=" + searchMaxima +
                ", searchPrisma=" + searchPrisma +
                ", searchRimi=" + searchRimi +
                ", searchSelver=" + searchSelver +
                '}';
    }

}
