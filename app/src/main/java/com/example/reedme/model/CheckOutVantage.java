package com.example.reedme.model;

public class CheckOutVantage {
    int VantageId;
    String VantageImage;
    String VantageName;
    String VantageOldPrice;
    String VantagePrice;
    String VantageSize;
    int vantageQty;

    public void setVantageId(int vId) {
        this.VantageId = vId;
    }

    public void setVantageName(String vName) {
        this.VantageName = vName;
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

    public void setVantageQty(int qty) {
        this.vantageQty = qty;
    }

    public int getVantageId() {
        return this.VantageId;
    }

    public String getVantageName() {
        return this.VantageName;
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

    public int getVantageQty() {
        return this.vantageQty;
    }
}
