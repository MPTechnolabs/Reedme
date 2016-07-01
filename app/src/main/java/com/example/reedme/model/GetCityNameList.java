package com.example.reedme.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jolly Raiyani on 4/15/2016.
 */
public class GetCityNameList  implements Serializable {
    List<GetCityNameDetail> CategoriesList;

    public GetCityNameList(){}
    public GetCityNameList(List<GetCityNameDetail> items) {
        this.CategoriesList = items;
    }


    public List<GetCityNameDetail> getCategoryItems() {
        return this.CategoriesList;
    }

    public void setCategoryItems(List<GetCityNameDetail> categories) {
        this.CategoriesList = categories;
    }

}
