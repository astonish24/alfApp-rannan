package com.example.alfappcomp710;

public interface ItemBuilder {
    public String getColor();

    public void setColor(String color);

    public String getStatus();

    public void setStatus(String status);

    public String getName();

    public void setName(String name);

    public String getBodySize();

    public void setBodySize(String bodySize);

    public String getBreed();

    public void setBreed(String itemType);



    public Id_Bank_Cards getId_Bank_CardsObj();
    public Wallet_Bags getWallet_BagsObj();
    public Electronics getElectronicsObj();

    public void reset();

}
