package com.example.reedme.model;

/**
 * Created by jayes_000 on 21-Dec-15.
 */
public class GiftListModel {

    public int icon;
    public String title;
    public String seller;
    public String status;
    public String price;
    public GiftListModel(){
        super();
    }

    public GiftListModel(int icon, String title, String seller, String status, String price)
    {
        super();
        this.icon = icon;
        this.title = title;
        this.seller = seller;
        this.status = status;
        this.price = price;
    }
}
