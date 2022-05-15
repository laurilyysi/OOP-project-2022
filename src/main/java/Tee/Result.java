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
        StringBuilder str = new StringBuilder("");
        path.forEach(loc -> str.append(loc.getName()).append("\n"));
        return str.substring(0, str.length() - 1);
    }

    public double getDistanceKM() {
        return distanceKM;
    }

    public String mapsLink(String startLoc) {

        StringBuilder link = new StringBuilder("https://www.google.com/maps/dir/");
        link.append(startLoc.replaceAll(" ", "")).append("/");

        for (Location loc : path) {
            link.append(loc.getLinkCoords()).append("/");
        }

        return link.toString();

    }

    @Override
    public int compareTo(Result o) {
        return Double.compare(distanceKM, o.distanceKM);
    }

}