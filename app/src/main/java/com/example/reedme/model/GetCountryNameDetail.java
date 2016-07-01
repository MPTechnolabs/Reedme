package com.example.reedme.model;

import java.io.Serializable;

/**
 * Created by Jolly Raiyani on 4/15/2016.
 */
public class GetCountryNameDetail  implements Serializable {

    String country_id;
    String name;


    public GetCountryNameDetail() {
    }


    public void setId(String state_id) {
        this.country_id = state_id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return this.country_id;
    }

    public String getName() {
        return this.name;
    }


}

