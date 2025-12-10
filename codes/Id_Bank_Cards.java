package com.example.alfappcomp710;

import java.util.Objects;

public class Id_Bank_Cards extends Product implements ItemBuilder{

    private String color, name, bodySize, itemBrand,status;
    //private Id_Bank_Cards Id_Bank_CardsObj;
    public Id_Bank_Cards() {
    }
    //String classification, String color, String name, String bodySize, String itemType,String status
//    public Id_Bank_Cards(String color, String name, String bodySize, String itemType, String status) {
//        this.color = color;
//        this.name = name;
//        this.bodySize = bodySize;
//        this.itemType = itemType;
//        this.status = status;
//    }

    public Id_Bank_Cards(String classification, String color, String name, String bodySize, String itemBrand, String status) {
        super(classification);
        this.color = color;
        this.name = name;
        this.bodySize = bodySize;
        this.itemBrand = itemBrand;
        this.status = status;
        //this.Id_Bank_CardsObj = Id_Bank_CardsObj;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBodySize() {
        return bodySize;
    }

    public void setBodySize(String bodySize) {
        this.bodySize = bodySize;
    }

    public String getBreed() {
        return itemBrand;
    }

    public void setBreed(String itemBrand) {
        this.itemBrand = itemBrand;
    }


    public Electronics getElectronicsObj(){
        //ElectronicsObj = this;
        return null;
    }
    public Wallet_Bags getWallet_BagsObj(){
        return null;
    }

    public Id_Bank_Cards getId_Bank_CardsObj(){
        return null;
    }

    public void reset(){
        //this.Id_Bank_CardsObj = new Id_Bank_Cards();
    }
}
