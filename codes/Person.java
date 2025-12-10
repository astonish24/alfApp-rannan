package com.example.alfappcomp710;

public class Person {
    private String name, email,phoneNum;

    public Person() {

    }
    public Person(String name, String email, String phoneNum) {
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
