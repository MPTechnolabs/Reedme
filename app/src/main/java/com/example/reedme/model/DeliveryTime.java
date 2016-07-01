package com.example.reedme.model;

public class DeliveryTime {
    String DeliveryTimeId;
    String DeliveryTimeValue;

    public void setDeliveryTimeId(String dId) {
        this.DeliveryTimeId = dId;
    }

    public void setDeliveryTimeValue(String dValue) {
        this.DeliveryTimeValue = dValue;
    }

    public String getDeliveryTimeId() {
        return this.DeliveryTimeId;
    }

    public String getDeliveryValue() {
        return this.DeliveryTimeValue;
    }
}
