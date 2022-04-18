package com.example.fxUI;

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
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getListFileName() {
        return listFileName;
    }

    public void addToList(String item) {
        shoppinglist.add(item);
    }

    public void clearList() {
        shoppinglist.clear();
    }

    public void printList() {
        System.out.println(shoppinglist);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSaastukaart(boolean saastukaart) {
        this.saastukaart = saastukaart;
    }

    public void setPartnerkaart(boolean partnerkaart) {
        this.partnerkaart = partnerkaart;
    }

    public void setRimikaart(boolean rimikaart) {
        this.rimikaart = rimikaart;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }

}
