package com.example.alfappcomp710;

import java.util.Objects;

public class Electronics extends Product implements ItemBuilder{

    private String color,itemType,status;
    //private Electronics ElectronicsObj;
    public Electronics() {

    }

    public Electronics(String classification, String color, String itemType, String status) {
        super(classification);
        this.color = color;
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
        return "";
    }

    public void setName(String name) {
    }

    public String getBodySize() {
        return "";
    }

    public void setBodySize(String bodySize) {

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
        //this.ElectronicsObj = new Electronics();
    }
}