package com.example.GUI;

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

    private int shoppingCount;
    private int totalItemsBought;
    private double totalMoneySpent;
    private String favoriteStore;


    public User(String username, String password, int age, String email, String location, boolean saastukaart, boolean partnerkaart, boolean rimikaart) {
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

        this.shoppingCount = 0;
        this.totalItemsBought = 0;
        this.totalMoneySpent = 0.0;
        this.favoriteStore = "";

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

    public ArrayList<String> getShoppinglist() {
        return shoppinglist;
    }

    public int getShoppingCount() {
        return shoppingCount;
    }

    public int getTotalItemsBought() {
        return totalItemsBought;
    }

    public double getTotalMoneySpent() {
        return totalMoneySpent;
    }

    public String getFavoriteStore() {
        return favoriteStore;
    }

    @Override
    public String toString() {
        return "User: " + username + ", password: " + password + ", age: " + age +
                ", email: " + email + ", location: " + location;
    }

}
