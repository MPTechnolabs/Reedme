package com.example.reedme.model;

import java.io.Serializable;

public class Address implements Serializable {
    int AddressId;
    String AddressName;
    String AddressStatus;
    String AreaId;
    String AreaName;
    String HouseNo;
    String Mobile;
    String StreetNo;

    public void setAddressId(int addressId) {
        this.AddressId = addressId;
    }

    public void setAreaId(String areaId) {
        this.AreaId = areaId;
    }

    public void setAreaName(String areaName) {
        this.AreaName = areaName;
    }

    public void setAddressName(String addressName) {
        this.AddressName = addressName;
    }

    public void setHouseNo(String houseNo) {
        this.HouseNo = houseNo;
    }

    public void setStreetNo(String streetNo) {
        this.StreetNo = streetNo;
    }

    public void setMobile(String mobile) {
        this.Mobile = mobile;
    }

    public int getAddressId() {
        return this.AddressId;
    }

    public void setAddressStatus(String status) {
        this.AddressStatus = status;
    }

    public String getAreaId() {
        return this.AreaId;
    }

    public String getAreaName() {
        return this.AreaName;
    }

    public String getAddressName() {
        return this.AddressName;
    }

    public String getHouseNo() {
        return this.HouseNo;
    }

    public String getStreetNo() {
        return this.StreetNo;
    }

    public String getMobile() {
        return this.Mobile;
    }

    public String getAddressStatus() {
        return this.AddressStatus;
    }
}
