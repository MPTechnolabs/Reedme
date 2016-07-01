package com.example.reedme.model;

/**
 * Created by jayes_000 on 24-Dec-15.
 */
public class WishListModel {
    public int icon;
    public String title;
    public String review;
    public String price;

    public WishListModel() {
        super();
    }

    public WishListModel(int icon, String title, String review, String price)
    {
        super();
        this.icon = icon;
        this.title = title;
        this.review = review;
        this.price = price;
    }
}
