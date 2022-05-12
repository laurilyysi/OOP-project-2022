package com.example.GUI;

import java.io.File;
import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private int age;
    private String email;
    private String location;

    private boolean saastukaart;
    private boolean partnerkaart;
    private boolean rimikaart;

    private ArrayList<String> shoppinglist;

    private String listFileName;
    private String infoFileName;

    private int totalVisits;
    private int totalBought;
    private double totalSpent;
    private double totalSaved;
    private String mostVisited;


    public User(String username, String password, int age, String email, String location, boolean saastukaart, boolean partnerkaart, boolean rimikaart,
                int totalVisits, int totalBought, double totalSpent, double totalSaved, String mostVisited) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.location = location;
        this.saastukaart = saastukaart;
        this.partnerkaart = partnerkaart;
        this.rimikaart = rimikaart;

        this.shoppinglist = new ArrayList<>();
        this.listFileName = username + "_list.txt";
        this.infoFileName = username + "_info.txt";

        this.totalVisits = totalVisits;
        this.totalBought = totalBought;
        this.totalSpent = totalSpent;
        this.totalSaved = totalSaved;
        this.mostVisited = mostVisited;

    }

    public String getUsername() {
        return username;
    }

    public String getListFileName() {
        return listFileName;
    }

    public String getLocation() {
        return location;
    }

    public void addToList(String item) {
        shoppinglist.add(item);
    }

    public void clearList() {
        shoppinglist.clear();
    }

    public void setTotalVisits(int totalVisits) {
        this.totalVisits = totalVisits;
    }

    public void setTotalBought(int totalBought) {
        this.totalBought = totalBought;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public void setTotalSaved(double totalSaved) {
        this.totalSaved = totalSaved;
    }

    public void setMostVisited(String mostVisited) {
        this.mostVisited = mostVisited;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public int getTotalBought() {
        return totalBought;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public double getTotalSaved() {
        return totalSaved;
    }

    public String getMostVisited() {
        return mostVisited;
    }

    public ArrayList<String> getShoppinglist() {
        return shoppinglist;
    }

    public String getPath() {
        return "data\\userdata\\" + username;
    }

    public String getInfoFileName() {
        return username + "_info.txt";
    }

    public File getUserShoppingListFile() {
        return new File("data/userdata/" + username + "/" + listFileName);
    };

    @Override
    public String toString() {
        return "User: " + username + ", password: " + password + ", age: " + age +
                ", email: " + email + ", location: " + location;
    }

    public String userInfoString() {
        return username + ":" + password + ":" + age + ":" + email + ":" + location + ":" +
                saastukaart + ":" + partnerkaart + ":" + rimikaart + ":" +
                totalVisits + ":" + totalBought + ":" + totalSpent + ":" + totalSaved + ":" + mostVisited;
    }

}
