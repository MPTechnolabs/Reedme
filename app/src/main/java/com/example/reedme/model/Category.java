package com.example.reedme.model;

public class Category {
    String ImageUrl;
    int ItemId;
    String ItemName;

    public Category(int itemId, String itemName, String itemUrl) {
        this.ItemId = itemId;
        this.ItemName = itemName;
        this.ImageUrl = itemUrl;
    }

    public void setItemId(int itemId) {
        this.ItemId = itemId;
    }

    public void setItemName(String itemName) {
        this.ItemName = itemName;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public int getItemId() {
        return this.ItemId;
    }

    public String getItemName() {
        return this.ItemName;
    }

    public String getImageUrl() {
        return this.ImageUrl;
    }
}
