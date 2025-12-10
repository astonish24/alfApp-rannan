package com.example.alfappcomp710;

import java.util.Objects;

public class Wallet_Bags extends Product implements ItemBuilder{

    private String color, name, bodySize, itemType, otherDesc,status;
    //private Wallet_Bags Wallet_BagsObj;
    public Wallet_Bags() {

    }

    public Wallet_Bags(String classification, String color, String name, String bodySize, String itemType, String status) {
        super(classification);
        this.color = color;
        this.name = name;
        this.bodySize = bodySize;
        this.itemType = itemType;
        this.status = status;
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
        return itemType;
    }

    public void setBreed(String itemType) {
        this.itemType = itemType;
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
        //this.Wallet_BagsObj = new Wallet_Bags();
    }
}
