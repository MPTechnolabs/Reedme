package com.example.reedme.model;

import java.io.Serializable;

/**
 * Created by Jolly Raiyani on 4/12/2016.
 */
public class CategoryItem1  implements Serializable {

    String category_id;
    String name;
    String  Image;
    String discount;
    String fav;

    public String getWalletPoint() {
        return walletPoint;
    }

    public void setWalletPoint(String walletPoint) {
        this.walletPoint = walletPoint;
    }

    String walletPoint;


    public CategoryItem1(){}


    public void setId(String category_id) {
        this.category_id = category_id;
    }

    public  void setImage(String image)
    {
        this.Image = image;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setFav(String fav)
    {
        this.fav = fav;
    }

    public String getId() {
        return this.category_id;
    }

    public String fav()
    {
        return  this.fav;
    }
    public String getImage()
    {
        return this.Image;
    }

    public String getName() {
        return this.name;
    }

    public void setDiscount(String discount)
    {
        this.discount = discount;
    }
    public String getDiscount()
    {
        return  this.discount;
    }


}
