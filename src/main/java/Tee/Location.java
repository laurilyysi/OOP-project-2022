package Tee;

public class Location {

    private String name;
    private char type;

    private double lat;
    private double lon;

    private double calculatedDistance;

    public Location(String name, char store, double lat, double lon) {
        this.name = name;
        this.type = store;
        this.lat = lat;
        this.lon = lon;
        calculatedDistance = 0;
    }

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        name = "start";
        type = 'O';
        calculatedDistance = 0;
    }


    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(double calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }


}
