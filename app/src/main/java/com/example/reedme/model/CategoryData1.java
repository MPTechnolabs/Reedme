package com.example.reedme.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jolly Raiyani on 4/12/2016.
 */
public class CategoryData1 implements Serializable {
    int AppVersion;
    List<CategoryItem1> CategoriesList;
    boolean IsForceUpdate;
    List<SliderItem> SliderList;

    public CategoryData1() {
    }

    public CategoryData1(List<CategoryItem1> items, int appVersion) {
        this.CategoriesList = items;
        this.AppVersion = appVersion;
    }

    public void setCategoryItems(List<CategoryItem1> categories) {
        this.CategoriesList = categories;
    }

    public void setSliderItems(List<SliderItem> items) {
        this.SliderList = items;
    }

    public void setAppVersion(int appVersion) {
        this.AppVersion = appVersion;
    }

    public void setIsForceUpdate(boolean isForceUpdate) {
        this.IsForceUpdate = isForceUpdate;
    }

    public List<CategoryItem1> getCategoryItems() {
        return this.CategoriesList;
    }
}



