package com.example.reedme.model;

import java.io.Serializable;

public class SubCategoryItem implements Serializable {
    int Id;
    String Image;
    String Name;
    String OldPrice;
    String Price;
    String Quantity;
    int SelectedItemCount;

    public SubCategoryItem(String name, String quantity, String price, String image) {
        this.Name = name;
        this.Quantity = quantity;
        this.Price = price;
        this.OldPrice = price;
        this.Image = image;
        this.SelectedItemCount = 2;
    }

    public void setId(int itemId) {
        this.Id = itemId;
    }

    public void setSelectedItemCount(int itemCount) {
        this.SelectedItemCount = itemCount;
    }

    public void setName(String itemName) {
        this.Name = itemName;
    }

    public void setQuantity(String itemQuantity) {
        this.Quantity = itemQuantity;
    }

    public void setImage(String itemImage) {
        this.Image = itemImage;
    }

    public void setPrice(String itemPrice) {
        this.Price = itemPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.OldPrice = oldPrice;
    }

    public int getId() {
        return this.Id;
    }

    public int getSelectedItemCount() {
        return this.SelectedItemCount;
    }

    public String getName() {
        return this.Name;
    }

    public String getQuantity() {
        return this.Quantity;
    }

    public String getImage() {
        return this.Image;
    }

    public String getPrice() {
        return this.Price;
    }

    public String getOldPrice() {
        return this.OldPrice;
    }
}
