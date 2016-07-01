package com.example.reedme.helper;


import com.google.android.gms.fitness.FitnessStatusCodes;

public class ApiHelper {
    public static String APP_PNAME = null;
    public static int MY_SOCKET_TIMEOUT_MS = 0;

    public static final String OTP_DELIMITER = "is ";
    public static final String SMS_ORIGIN = "MD-VEGIES";
    public static String addAddress;
    public static String address;
    public static String appPath;
    public static String area;
    public static String categoryUrl;
    public static String changeAddressStatus;
    public static String customerOrder;
    public static String deleteAddress;
    public static String homeFragment;
    public static String imageUrl;
    public static String login;
    public static String mainUrl;
    public static String orderCancel;
    public static String orderVariants;
    public static String orders;
    public static String searchProduct;
    public static String sendOtp;
    public static String sliderImageUrl;
    public static String subCategoryUrl;
    public static String updateAddress;
    public static String url;

    static {
        APP_PNAME = "com.vegies.app";
        MY_SOCKET_TIMEOUT_MS = FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS;

        appPath = "https://play.google.com/store/apps/details?id=com.vegies.app";
        mainUrl = "http://vegies.co.in/admin/";
        url = mainUrl + "webservice_v2/";
        imageUrl = mainUrl + "assets/uploads/";
        sliderImageUrl = mainUrl + "assets/uploads/slider/";
        categoryUrl = url + "category_list";
        subCategoryUrl = url + "products_list/";
        sendOtp = url + "send_otp";
        login = url + "login";
        area = url + "area_list";
        address = url + "customer_details";
        addAddress = url + "add_address";
        updateAddress = url + "update_address";
        changeAddressStatus = url + "change_active_address";
        deleteAddress = url + "delete_address";
        customerOrder = url + "customer_order";
        searchProduct = url + "search_product";
        orders = url + "order_list";
        orderCancel = url + "order_cancel";
        orderVariants = url + "order_detail_list";
        homeFragment = "home_fragment";
    }
}
