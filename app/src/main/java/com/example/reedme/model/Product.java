package com.example.reedme.model;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    int ProductId;
    String ProductName;
    String storename;
    List<Vantage> Vantages;

    public void setProductId(int pId) {
        this.ProductId = pId;
    }

    public void setProductName(String pName) {
        this.ProductName = pName;
    }

    public void setVantages(List<Vantage> vantages) {
        this.Vantages = vantages;
    }

    public int getProductId() {
        return this.ProductId;
    }

    public String getProductName() {
        return this.ProductName;
    }

    public List<Vantage> getVantages() {
        return this.Vantages;
    }

    public  void setStorename(String storename)
    {
        this.storename = storename;
    }
    public String getStorename()
    {
        return this.storename;
    }
}
