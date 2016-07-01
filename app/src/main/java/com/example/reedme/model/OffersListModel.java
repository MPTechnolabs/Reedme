package com.example.reedme.model;

/**
 * Created by jayes_000 on 22-Dec-15.
 */
public class OffersListModel
{
    public String discount;
    public String code;
    public String description;
    public String expdate;
    public OffersListModel(){
        super();
    }

    public OffersListModel(String discount, String code, String description, String expdate)
    {
        super();
        this.discount = discount;
        this.code = code;
        this.description = description;
        this.expdate = expdate;
    }
}

