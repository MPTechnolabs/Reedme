package com.example.reedme.model;

import java.io.Serializable;

/**
 * Created by jolly on 16/7/16.
 */
public class GetAgeDetail implements Serializable {

    String age_id;
    String name;


    public GetAgeDetail(String age_id,String name) {

        this.age_id =age_id;
        this.name = name;
    }


    public void setId(String age_id) {
        this.age_id = age_id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return this.age_id;
    }

    public String getName() {
        return this.name;
    }

}