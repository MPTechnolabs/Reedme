package com.example.reedme.model;

import java.io.Serializable;

public class Vantage implements Serializable {
    int VantageId;
    String VantageImage;
    String VantageOldPrice;
    String VantagePrice;
    String VantageQty;
    String VantageSize;

    public void setVantageId(int vId) {
        this.VantageId = vId;
    }

    public void setVantagePrice(String vPrice) {
        this.VantagePrice = vPrice;
    }

    public void setVantageOldPrice(String oldPrice) {
        this.VantageOldPrice = oldPrice;
    }

    public void setVantageImage(String image) {
        this.VantageImage = image;
    }

    public void setVantageSize(String size) {
        this.VantageSize = size;
    }

    public void setVantageQty(String vQty) {
        this.VantageQty = vQty;
    }

    public int getVantageId() {
        return this.VantageId;
    }

    public String getVantageSize() {
        return this.VantageSize;
    }

    public String getVantagePrice() {
        return this.VantagePrice;
    }

    public String getVantageOldPrice() {
        return this.VantageOldPrice;
    }

    public String getVantageImage() {
        return this.VantageImage;
    }

    public String gettVantageQty() {
        return this.VantageQty;
    }
}
