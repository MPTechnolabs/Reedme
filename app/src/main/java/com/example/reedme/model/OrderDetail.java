package com.example.reedme.model;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private String Amount;
    private String DeliveryDate;
    private String DeliveryTime;
    private String OrderDate;
    private String OrderId;
    private String PaymentType;
    private String ShippingAmount;
    private String Status;

    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    public void setOrderDate(String orderDate) {
        this.OrderDate = orderDate;
    }

    public void setPaymentType(String type) {
        this.PaymentType = type;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public void setOrderAmount(String amount) {
        this.Amount = amount;
    }

    public void setShippingAmount(String sAmount) {
        this.ShippingAmount = sAmount;
    }

    public void setDeliveryDate(String dDate) {
        this.DeliveryDate = dDate;
    }

    public void setDeliveryTime(String dTime) {
        this.DeliveryTime = dTime;
    }

    public String getOrderId() {
        return this.OrderId;
    }

    public String getOrderDate() {
        return this.OrderDate;
    }

    public String getPaymentType() {
        return this.PaymentType;
    }

    public String getStatus() {
        return this.Status;
    }

    public String getOrderAmount() {
        return this.Amount;
    }

    public String getShippingAmount() {
        return this.ShippingAmount;
    }

    public String getDeliveryDate() {
        return this.DeliveryDate;
    }

    public String getDeliveryTime() {
        return this.DeliveryTime;
    }
}
