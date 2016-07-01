package com.example.reedme.model;

import java.io.Serializable;

public class OrderVariant implements Serializable {
    String VantageName;
    String VantagePrice;
    String VantageQty;
    String  Image;


    public void setVantagePrice(String vPrice) {
        this.VantagePrice = vPrice;
    }

    public void setVantageName(String vName) {
        this.VantageName = vName;
    }

    public void setVantageQty(String vQty) {
        this.VantageQty = vQty;
    }

    public String getVantageName() {
        return this.VantageName;
    }

    public String getVantagePrice() {
        return this.VantagePrice;
    }

    public String getVantageQty() {
        return this.VantageQty;
    }
    public  void setImage(String image)
    {
        this.Image = image;
    }
    public String getImage()
    {
        return this.Image;
    }

}
