package com.example.alfappcomp710;

public class Owner extends Person{
    private String address,username,password;
    public Owner() {

    }

    public Owner(String name, String email, String phoneNum, String address, String username, String password) {
        super(name, email, phoneNum);
        this.address = address;
        this.username = username;
        this.password = password;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
