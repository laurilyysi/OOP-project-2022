package Tee;

import java.util.ArrayList;

public class Result implements Comparable<Result> {

    private double distanceKM;
    private ArrayList<Location> path;

    public Result(double distanceKM, ArrayList<Location> path) {
        this.distanceKM = distanceKM;
        this.path = path;
    }

    public String pathToString() {
        StringBuilder str = new StringBuilder(" • ");
        path.forEach(loc -> str.append(loc.getName()).append(" -> "));
        return str.substring(0, str.length() - 3);
    }

    @Override
    public String toString() {
        return distanceKM + " km\n" + pathToString();
    }

    @Override
    public int compareTo(Result o) {
        return Double.compare(distanceKM, o.distanceKM);
    }

}
