package com.example.reedme.model;

import java.io.Serializable;

/**
 * Created by Android on 6/24/2016.
 */
public class login_code implements Serializable {

    private String store_code;
    private String store_id;

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }
}