package com.example.reedme.model;

/**
 * Created by SHRIYA17 on 07-Apr-16.
 */
public class LocalFilterBrands {

    Integer BrandID;
    String BrandName;
    Boolean bl_IsChecked  = false;

    public Integer getBrandID() {
        return BrandID;
    }

    public void setBrandID(Integer brandID) {
        BrandID = brandID;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public Boolean getBl_IsChecked() {
        return bl_IsChecked;
    }

    public void setBl_IsChecked(Boolean bl_IsChecked) {
        this.bl_IsChecked = bl_IsChecked;
    }
}
