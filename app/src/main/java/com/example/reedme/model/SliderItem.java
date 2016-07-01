package com.example.reedme.model;

import java.io.Serializable;

public class SliderItem implements Serializable {
    int ImageId;
    String ImageUrl;

    public void setImageId(String imageId) {
        this.ImageId = this.ImageId;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public int getImageId() {
        return this.ImageId;
    }

    public String getImageUrl() {
        return this.ImageUrl;
    }
}
