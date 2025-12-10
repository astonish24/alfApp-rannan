package com.example.alfappcomp710;

public class Finder extends Person{
    private String address;

    public Finder() {

    }

    public Finder(String name, String phoneNum, String address) {
        this.address = address;
        this.setName(name);
        this.setPhoneNum(phoneNum);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}