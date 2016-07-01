package com.example.reedme.model;

import java.io.Serializable;

/**
 * Created by jolly on 30/6/16.
 */
public class UserDetail implements Serializable {


    private String wallet_point;
    private String user_id;
    private String username;
    private String firstname;
    private String lastname;
    private String phone;
    private String state;
    private String city;
    private String pincode;
    private String address;
    private String qr_number;
    private String email;
    private String join_date;
    private String profile_pic;

    public String getWallet_point() {
        return wallet_point;
    }

    public void setWallet_point(String wallet_point) {
        this.wallet_point = wallet_point;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQr_number() {
        return qr_number;
    }

    public void setQr_number(String qr_number) {
        this.qr_number = qr_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}