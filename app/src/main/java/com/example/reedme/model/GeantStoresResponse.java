package com.example.reedme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SHRIYA17 on 18-Mar-16.
 */
public class GeantStoresResponse {



    @SerializedName("StoreID")
    @Expose
    private Integer StoreID;
    @SerializedName("StoreName")
    @Expose
    private String StoreName;
    @SerializedName("StoreAddress")
    @Expose
    private String StoreAddress;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("State")
    @Expose
    private String State;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("PhoneNumber")
    @Expose
    private String PhoneNumber;
    @SerializedName("Timing")
    @Expose
    private String Timing;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;
    @SerializedName("Field1")
    @Expose
    private String Field1;
    @SerializedName("Field2")
    @Expose
    private String Field2;
    @SerializedName("Field3")
    @Expose
    private String Field3;
    @SerializedName("Field4")
    @Expose
    private String Field4;
    @SerializedName("CreatedOn")
    @Expose
    private String CreatedOn;
    @SerializedName("UpdatedOn")
    @Expose
    private String UpdatedOn;
    @SerializedName("Active")
    @Expose
    private String Active;

    /**
     *
     * @return
     * The StoreID
     */
    public Integer getStoreID() {
        return StoreID;
    }

    /**
     *
     * @param StoreID
     * The StoreID
     */
    public void setStoreID(Integer StoreID) {
        this.StoreID = StoreID;
    }

    /**
     *
     * @return
     * The StoreName
     */
    public String getStoreName() {
        return StoreName;
    }

    /**
     *
     * @param StoreName
     * The StoreName
     */
    public void setStoreName(String StoreName) {
        this.StoreName = StoreName;
    }

    /**
     *
     * @return
     * The StoreAddress
     */
    public String getStoreAddress() {
        return StoreAddress;
    }

    /**
     *
     * @param StoreAddress
     * The StoreAddress
     */
    public void setStoreAddress(String StoreAddress) {
        this.StoreAddress = StoreAddress;
    }

    /**
     *
     * @return
     * The Country
     */
    public String getCountry() {
        return Country;
    }

    /**
     *
     * @param Country
     * The Country
     */
    public void setCountry(String Country) {
        this.Country = Country;
    }

    /**
     *
     * @return
     * The State
     */
    public String getState() {
        return State;
    }

    /**
     *
     * @param State
     * The State
     */
    public void setState(String State) {
        this.State = State;
    }

    /**
     *
     * @return
     * The City
     */
    public String getCity() {
        return City;
    }

    /**
     *
     * @param City
     * The City
     */
    public void setCity(String City) {
        this.City = City;
    }

    /**
     *
     * @return
     * The PhoneNumber
     */
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    /**
     *
     * @param PhoneNumber
     * The PhoneNumber
     */
    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    /**
     *
     * @return
     * The Timing
     */
    public String getTiming() {
        return Timing;
    }

    /**
     *
     * @param Timing
     * The Timing
     */
    public void setTiming(String Timing) {
        this.Timing = Timing;
    }

    /**
     *
     * @return
     * The Latitude
     */
    public String getLatitude() {
        return Latitude;
    }

    /**
     *
     * @param Latitude
     * The Latitude
     */
    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    /**
     *
     * @return
     * The Longitude
     */
    public String getLongitude() {
        return Longitude;
    }

    /**
     *
     * @param Longitude
     * The Longitude
     */
    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    /**
     *
     * @return
     * The Field1
     */
    public String getField1() {
        return Field1;
    }

    /**
     *
     * @param Field1
     * The Field1
     */
    public void setField1(String Field1) {
        this.Field1 = Field1;
    }

    /**
     *
     * @return
     * The Field2
     */
    public String getField2() {
        return Field2;
    }

    /**
     *
     * @param Field2
     * The Field2
     */
    public void setField2(String Field2) {
        this.Field2 = Field2;
    }

    /**
     *
     * @return
     * The Field3
     */
    public String getField3() {
        return Field3;
    }

    /**
     *
     * @param Field3
     * The Field3
     */
    public void setField3(String Field3) {
        this.Field3 = Field3;
    }

    /**
     *
     * @return
     * The Field4
     */
    public String getField4() {
        return Field4;
    }

    /**
     *
     * @param Field4
     * The Field4
     */
    public void setField4(String Field4) {
        this.Field4 = Field4;
    }

    /**
     *
     * @return
     * The CreatedOn
     */
    public String getCreatedOn() {
        return CreatedOn;
    }

    /**
     *
     * @param CreatedOn
     * The CreatedOn
     */
    public void setCreatedOn(String CreatedOn) {
        this.CreatedOn = CreatedOn;
    }

    /**
     *
     * @return
     * The UpdatedOn
     */
    public String getUpdatedOn() {
        return UpdatedOn;
    }

    /**
     *
     * @param UpdatedOn
     * The UpdatedOn
     */
    public void setUpdatedOn(String UpdatedOn) {
        this.UpdatedOn = UpdatedOn;
    }

    /**
     *
     * @return
     * The Active
     */
    public String getActive() {
        return Active;
    }

    /**
     *
     * @param Active
     * The Active
     */
    public void setActive(String Active) {
        this.Active = Active;
    }
}
