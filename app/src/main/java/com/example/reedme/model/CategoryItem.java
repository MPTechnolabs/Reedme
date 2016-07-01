package com.example.reedme.model;

import java.io.Serializable;

public class CategoryItem implements Serializable {

    String category_id;
    String name;
    String  Image;


    public CategoryItem(){}


    public void setId(String category_id) {
        this.category_id = category_id;
    }

    public  void setImage(String image)
    {
        this.Image = image;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return this.category_id;
    }
    public String getImage()
    {
        return this.Image;
    }

    public String getName() {
        return this.name;
    }

   }
