package com.example.reedme.model;

import java.io.Serializable;
import java.util.List;

public class CategoryData implements Serializable {
    int AppVersion;
    List<CategoryItem> CategoriesList;
    boolean IsForceUpdate;
    List<SliderItem> SliderList;

    public CategoryData(){}
    public CategoryData(List<CategoryItem> items, int appVersion) {
        this.CategoriesList = items;
        this.AppVersion = appVersion;
    }

    public void setCategoryItems(List<CategoryItem> categories) {
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

    public List<CategoryItem> getCategoryItems() {
        return this.CategoriesList;
    }

    public List<SliderItem> getSliderItems() {
        return this.SliderList;
    }

    public int getAppVersion() {
        return this.AppVersion;
    }

    public boolean getForceUpdate() {
        return this.IsForceUpdate;
    }
}
