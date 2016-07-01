package com.example.reedme.model;

public class VMenuItem {
    public int ImageId;
    public int ImageSelectedId;
    public int ItemId;
    public String ItemImage;
    public String ItemName;

    public VMenuItem(int itemId, String itemName, int imageId, int imageSelectedId) {
        this.ItemId = itemId;
        this.ItemName = itemName;
        this.ImageId = imageId;
        this.ImageSelectedId = imageSelectedId;
    }

    public void setItemId(int itemId) {
        this.ItemId = itemId;
    }

    public void setImageId(int imageId) {
        this.ImageId = imageId;
    }

    public void setItemName(String itemName) {
        this.ItemName = itemName;
    }

    public void setItemImage(String itemImage) {
        this.ItemImage = itemImage;
    }

    public void setImageSelected(int sImageId) {
        this.ImageSelectedId = sImageId;
    }

    public int getItemId() {
        return this.ItemId;
    }

    public String getItemName() {
        return this.ItemName;
    }

    public String getItemImage() {
        return this.ItemImage;
    }

    public int getImageId() {
        return this.ImageId;
    }

    public int getImageSelected() {
        return this.ImageSelectedId;
    }
}
