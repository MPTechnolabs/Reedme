package com.example.reedme.model;

import java.util.List;

public class ShippingInfo {
    List<DeliveryTime> AllDeliveryTimeList;
    List<DeliveryTime> DeliveryTimeList;
    String ShippingAmount;
    String ShippingCriteria;

    public void setDeliveryTimeList(List<DeliveryTime> dTimeList) {
        this.DeliveryTimeList = dTimeList;
    }

    public void setAllDeliveryTimeList(List<DeliveryTime> dTimeList) {
        this.AllDeliveryTimeList = dTimeList;
    }

    public void setShippingCriteria(String shippingc) {
        this.ShippingCriteria = shippingc;
    }

    public void setShippingAmount(String shippingAmount) {
        this.ShippingAmount = shippingAmount;
    }

    public List<DeliveryTime> getDeliveryTimeList() {
        return this.DeliveryTimeList;
    }

    public List<DeliveryTime> getAllDeliveryTimeList() {
        return this.AllDeliveryTimeList;
    }

    public String getShippingCriteria() {
        return this.ShippingCriteria;
    }

    public String getShippingAmount() {
        return this.ShippingAmount;
    }
}
