package com.example.reedme.model;

import java.io.Serializable;

/**
 * Created by Jolly Raiyani on 4/15/2016.
 */
public class GetCityNameDetail implements Serializable {

    String city_id;
    String name;


    public GetCityNameDetail(){}


    public void setId(String city_id) {
        this.city_id = city_id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return this.city_id;
    }

    public String getName() {
        return this.name;
    }

}
