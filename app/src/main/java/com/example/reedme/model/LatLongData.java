package com.example.reedme.model;

import java.io.Serializable;

/**
 * Created by jolly on 20/7/16.
 */
public class LatLongData implements Serializable {
    private String Latitude;
    private String Longitude;
    private String title;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
