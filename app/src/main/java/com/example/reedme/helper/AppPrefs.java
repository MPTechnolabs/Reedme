package com.example.reedme.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.example.reedme.R;
import com.example.reedme.model.Address;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.model.ShippingInfo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppPrefs {
    private static final String PREFERENCE = "VEGIES_APP";

    private static AppPrefs appPrefs;
    private SharedPreferences appSharedPrefs;
    Context context;
    private Editor prefsEditor;
    private static final int MAX_SIZE = 3;

    public AppPrefs(Context context) {
        this.context = context;
        this.appSharedPrefs = context.getSharedPreferences(PREFERENCE, 0);
        this.prefsEditor = this.appSharedPrefs.edit();
    }

    public static AppPrefs getAppPrefs(Context context) {
        if (appPrefs == null) {
            appPrefs = new AppPrefs(context);
        }
        return appPrefs;
    }

    public void clearAllData() {
        this.prefsEditor.clear();
        this.prefsEditor.commit();
    }

    public void commit() {
        this.prefsEditor.commit();
    }

    public void setIsLogin(boolean isLogin) {
        this.prefsEditor.putBoolean("IsLogin", isLogin).commit();
    }
    public void setIsMerchantLogin(boolean isLogin) {
        this.prefsEditor.putBoolean("IsMLogin", isLogin).commit();
    }
    public boolean getIsMerchantLogin() {
        return this.appSharedPrefs.getBoolean("IsMLogin", false);
    }

    public boolean getIsLogin() {
        return this.appSharedPrefs.getBoolean("IsLogin", false);
    }

    public void setAddress(Address address) {
        this.prefsEditor.putString(this.context.getResources().getString(R.string.prefs_address), new Gson().toJson(address)).commit();
    }

    public Address getAddress() {
        return new Gson().fromJson(this.appSharedPrefs.getString(this.context.getResources().getString(R.string.prefs_address), ""), Address.class);
    }



    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.prefsEditor.putString(this.context.getResources().getString(R.string.prefs_shipping_info), new Gson().toJson(shippingInfo)).commit();
    }

    public ShippingInfo getShippingInfo() {
        return new Gson().fromJson(this.appSharedPrefs.getString(this.context.getResources().getString(R.string.prefs_shipping_info), ""), ShippingInfo.class);
    }

    public void setObject(String key, Object object) {
        this.prefsEditor.putString(key, new Gson().toJson(object)).commit();
    }

    public Object getObject(String key) {
        return new Gson().fromJson(this.appSharedPrefs.getString(key, ""), Object.class);
    }

    public void setString(String key, String value) {
        this.prefsEditor.putString(key, value).commit();
    }

    public String getString(String key) {
        return this.appSharedPrefs.getString(key, null);
    }

    public void setSubCategory(String key, String value) {
        this.prefsEditor.putString(new StringBuilder(String.valueOf(this.context.getResources().getString(R.string.prefs_sub_categories))).append(key).toString(), value).commit();
    }
    public void setSubCategory1(String key, String value) {
        this.prefsEditor.putString(new StringBuilder(String.valueOf(this.context.getResources().getString(R.string.prefs_sub_categories))).append(key).toString(), value).commit();
    }

    public String getSubCategory(String key) {
        return this.appSharedPrefs.getString(new StringBuilder(String.valueOf(this.context.getResources().getString(R.string.prefs_sub_categories))).append(key).toString(), null);
    }

    public void setCheckOutVantage(CheckOutData checkOutProduct) {
        this.prefsEditor.putString(this.context.getResources().getString(R.string.prefs_check_out_vantage), new Gson().toJson(checkOutProduct)).commit();
    }

    public CheckOutData getCheckOutVantage() {
        return new Gson().fromJson(this.appSharedPrefs.getString(this.context.getResources().getString(R.string.prefs_check_out_vantage), null), CheckOutData.class);
    }

    public static void storeList(Context context,String pref_name, String key, List countries) {

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(countries);
        editor.putString(key, jsonFavorites);
        editor.apply();
    }

    public static ArrayList<String> loadList(Context context,String pref_name, String key) {

        SharedPreferences settings;
        List<String> favorites;
        settings = context.getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        if (settings.contains(key)) {
            String jsonFavorites = settings.getString(key, null);
            Gson gson = new Gson();
            String[] favoriteItems = gson.fromJson(jsonFavorites, String[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;
        return (ArrayList<String>) favorites;
    }

    public static void addList(Context context, String pref_name, String key,String country) {
        List<String> favorites = loadList(context, pref_name, key);
        if (favorites == null)
            favorites = new ArrayList<>();

        if(favorites.size() > MAX_SIZE) {
            favorites.clear();
            deleteList(context, pref_name);
        }

        if(favorites.contains(country)){

            favorites.remove(country);

        }
        favorites.add(country);

        storeList(context, pref_name, key, favorites);

    }

    public static void deleteList(Context context, String pref_name){

        SharedPreferences myPrefs = context.getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.clear();
        editor.apply();
    }
}
