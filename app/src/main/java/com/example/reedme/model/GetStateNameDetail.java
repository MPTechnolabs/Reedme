package com.example.reedme.model;

import java.io.Serializable;

/**
 * Created by Jolly Raiyani on 4/15/2016.
 */
public class GetStateNameDetail implements Serializable {

    String state_id;
    String name;


    public GetStateNameDetail(){}


    public void setId(String state_id) {
        this.state_id = state_id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return this.state_id;
    }

    public String getName() {
        return this.name;
    }

}

