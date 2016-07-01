package com.example.reedme.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jolly Raiyani on 4/15/2016.
 */
public class GetStateNameList implements Serializable {
    List<GetStateNameDetail> CategoriesList;

    public GetStateNameList(){}
    public GetStateNameList(List<GetStateNameDetail> items) {
        this.CategoriesList = items;
    }


    public List<GetStateNameDetail> getCategoryItems() {
        return this.CategoriesList;
    }

    public void setCategoryItems(List<GetStateNameDetail> categories) {
        this.CategoriesList = categories;
    }



}

