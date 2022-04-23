package Tee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Test {

    public static ArrayList<Location> coopLoc = readLocations("coop_loc.txt", 'C');
    public static ArrayList<Location> maximaLoc = readLocations("maxima_loc.txt", 'M');
    public static ArrayList<Location> prismaLoc = readLocations("prisma_loc.txt", 'P');
    public static ArrayList<Location> rimiLoc = readLocations("rimi_loc.txt", 'R');
    public static ArrayList<Location> selverLoc = readLocations("selver_loc.txt", 'S');

    public static Location startLocation;
    public static ArrayList<Result> paths = new ArrayList<>();

    public static void main(String[] args) {

        Location delta = new Location(58.38511, 26.72512);
        // findShortestPaths(delta.getLat(), delta.getLon(), "all");

        findShortestPaths(58.37802, 26.72908, "all");

    }

    public static void findShortestPaths(double lat, double lon, String storeTypes) {
        startLocation = new Location(lat, lon);
        if (storeTypes.equals("all")) storeTypes = "CMPRS";
        findAllWays(storeTypes, "");
        Collections.sort(paths);
        paths.forEach(System.out::println);
    }

    public static Result findPath(Location start, String lookFor) {

        ArrayList<Location> teekond = new ArrayList<>();
        double distance = 0;

        for (int i = 0; i < lookFor.length(); i++) {
            char ch = lookFor.charAt(i);
            Location store = closestStore(start, getStoreList(ch));
            teekond.add(store);
            start = store;
            distance += store.getCalculatedDistance();
        }

        double km = Math.round(distance / 10.0) / 100.0;
        return new Result(km, teekond);

    }

    // finds n! possible paths given n stores, where n = lookFor.length()
    public static void findAllWays(String search, String lookFor) {

        if (search.length() == 0) {
            paths.add(findPath(startLocation, lookFor));
            return;
        }

        for (int i = 0; i < search.length(); i++) {
            char store = search.charAt(i);
            String toBeSearched = search.substring(0, i) + search.substring(i+1);
            findAllWays(toBeSearched, lookFor + store);
        }

    }

    public static ArrayList<Location> getStoreList(char type) {
        switch (type) {
            case 'C' -> {
                return coopLoc;
            }
            case 'P' -> {
                return prismaLoc;
            }
            case 'S' -> {
                return selverLoc;
            }
            case 'R' -> {
                return rimiLoc;
            }
            case 'M' -> {
                return maximaLoc;
            }
            default -> {
                return null;
            }
        }

    }

    public static Location closestStore(Location start, ArrayList<Location> locations) {
        double shortest = Double.MAX_VALUE;

        Location closest = null;
        for (Location location : locations) {
            double distance = distance(start.getLat(), start.getLon(), location.getLat(), location.getLon());
            if (distance < shortest) {
                shortest = distance;
                closest = location;
            }
        }

        closest.setCalculatedDistance(shortest);
        return closest;

    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    // reads store locations from file
    public static ArrayList<Location> readLocations(String filename, char storeType) {
        File file = new File("src/main/resources/" + filename);
        ArrayList<Location> locations = new ArrayList<>();

        try (Scanner scan = new Scanner(new FileInputStream(file))) {
            while (scan.hasNextLine()) {
                String[] split = scan.nextLine().split("\t");
                String name = split[0];
                double lat = Double.parseDouble(split[1].split(", ")[0]);
                double lon = Double.parseDouble(split[1].split(", ")[1]);
                Location location = new Location(name, storeType, lat, lon);
                locations.add(location);
            }
        } catch (FileNotFoundException ignored) {}
        return locations;
    }

}
