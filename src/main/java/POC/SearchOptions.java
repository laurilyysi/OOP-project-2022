package POC;

import java.util.ArrayList;

public class SearchOptions {

    private ArrayList<String> shoppinglist;

    private boolean searchCoop;
    private boolean searchMaxima;
    private boolean searchPrisma;
    private boolean searchRimi;
    private boolean searchSelver;

    private String address;

    public SearchOptions(ArrayList<String> shoppinglist, boolean searchCoop, boolean searchMaxima, boolean searchPrisma,
                         boolean searchRimi, boolean searchSelver, String address) {
        this.shoppinglist = shoppinglist;
        this.searchCoop = searchCoop;
        this.searchMaxima = searchMaxima;
        this.searchPrisma = searchPrisma;
        this.searchRimi = searchRimi;
        this.searchSelver = searchSelver;
        this.address = address;
    }

    public boolean isSearchCoop() {
        return searchCoop;
    }

    public void setSearchCoop(boolean searchCoop) {
        this.searchCoop = searchCoop;
    }

    public boolean isSearchMaxima() {
        return searchMaxima;
    }

    public void setSearchMaxima(boolean searchMaxima) {
        this.searchMaxima = searchMaxima;
    }

    public boolean isSearchPrisma() {
        return searchPrisma;
    }

    public void setSearchPrisma(boolean searchPrisma) {
        this.searchPrisma = searchPrisma;
    }

    public boolean isSearchRimi() {
        return searchRimi;
    }

    public void setSearchRimi(boolean searchRimi) {
        this.searchRimi = searchRimi;
    }

    public boolean isSearchSelver() {
        return searchSelver;
    }

    public void setSearchSelver(boolean searchSelver) {
        this.searchSelver = searchSelver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "list: " + shoppinglist +
                "\naddress: " + address +
                "\ncoop:" + searchCoop + ", maxima: " + searchMaxima + ", prisma:" + searchPrisma +
                ", rimi: " + searchRimi + ", selver:" + searchSelver;
    }

}
