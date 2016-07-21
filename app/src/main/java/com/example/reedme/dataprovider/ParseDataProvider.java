package com.example.reedme.dataprovider;

import android.content.Context;
import android.util.Log;

import com.example.reedme.R;
import com.example.reedme.activity.StartActivity;
import com.example.reedme.adapter.DBAdapter;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.model.Address;
import com.example.reedme.model.Area;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CategoryData1;
import com.example.reedme.model.CategoryItem;
import com.example.reedme.model.CategoryItem1;
import com.example.reedme.model.DeliveryTime;
import com.example.reedme.model.GetCityNameDetail;
import com.example.reedme.model.GetCityNameList;
import com.example.reedme.model.GetStateNameDetail;
import com.example.reedme.model.GetStateNameList;
import com.example.reedme.model.LatLongData;
import com.example.reedme.model.OrderDetail;
import com.example.reedme.model.OrderVariant;
import com.example.reedme.model.Product;
import com.example.reedme.model.ShippingInfo;
import com.example.reedme.model.SubCategory;
import com.example.reedme.model.UserDetail;
import com.example.reedme.model.UserDetailList;
import com.example.reedme.model.Vantage;
import com.google.android.gms.games.Games;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseDataProvider {
    public static ParseDataProvider parseDataProvider;
    Context context;
    private String message;
    private int statusCode;

    public static ParseDataProvider getInstance(Context context) {
        if (parseDataProvider == null) {
            parseDataProvider = new ParseDataProvider(context);
        }
        return parseDataProvider;
    }

    public ParseDataProvider(Context context) {
        this.context = context;
    }

    public String getErrorMessage()
    {
        return this.message;
    }

    public boolean IsSuccess(JSONObject jsonObject) {
        try {
            this.message = jsonObject.getString("status");
            return this.message.equals("OK");

        } catch (Exception ex) {
            Log.e("Register", ex.getMessage());
            return false;
        }
    }

    public Integer IsSuccessRegister(JSONObject jsonObject) {
        try {
            Integer status = jsonObject.getInt("success");
            Log.e("status----->",status+"");
            return status;


        } catch (Exception ex) {
            Log.e("Register", ex.getMessage());
            return 0;

        }
    }
    public CategoryData1  CategoryData1(JSONObject jsonObject) {

        CategoryData1 categoryData1 = null;
        try {
            if(jsonObject.getInt("success") == 1) {

                List<CategoryItem1> categoryItems = new ArrayList();
                JSONArray categoryArray = jsonObject.getJSONArray("storelist");

                for (int j = 0; j < categoryArray.length(); j++) {
                    JSONObject elem = categoryArray.getJSONObject(j);
                    CategoryItem1 categoryItem = new CategoryItem1();
                    categoryItem.setId(elem.getString("s_id"));
                    categoryItem.setName(elem.getString("store_name"));
                    categoryItem.setImage(elem.getString("store_img"));
                    categoryItem.setDiscount(elem.getString("discount"));
                    categoryItem.setFav(elem.getString("fav"));
                    categoryItem.setWalletPoint(elem.getString("wallet_point"));


                    categoryItems.add(categoryItem);

                }
                Log.e("Response :", categoryItems + "");
                categoryData1 = new CategoryData1();
                categoryData1.setCategoryItems(categoryItems);
                return categoryData1;

            }else
            {
                return null;
            }

        } catch (Exception e) {

        }
        return null;
    }

    public CategoryData1  fav_store(JSONObject jsonObject) {

        CategoryData1 categoryData1 = null;
        try {
            if(jsonObject.getInt("success") == 1) {

                List<CategoryItem1> categoryItems = new ArrayList();
                JSONArray categoryArray = jsonObject.getJSONArray("storelist");

                for (int j = 0; j < categoryArray.length(); j++) {
                    JSONObject elem = categoryArray.getJSONObject(j);
                    CategoryItem1 categoryItem = new CategoryItem1();
                    categoryItem.setId(elem.getString("s_id"));
                    categoryItem.setName(elem.getString("store_name"));
                    categoryItem.setImage(elem.getString("store_img"));
                    categoryItem.setDiscount(elem.getString("discount"));

                    categoryItems.add(categoryItem);

                }
                Log.e("Response :", categoryItems + "");
                categoryData1 = new CategoryData1();
                categoryData1.setCategoryItems(categoryItems);
                return categoryData1;

            }else
            {
                return null;
            }

        } catch (Exception e) {

        }
        return null;
    }


    public CategoryData CategoryData(JSONObject jsonObject) {
        CategoryData categoryData = null;
        try {
            int i;

            List<CategoryItem> categoryItems = new ArrayList();
            JSONArray categoryArray = jsonObject.getJSONArray("categorylist");


            for (int j = 0; j < categoryArray.length(); j++) {
                JSONObject elem = categoryArray.getJSONObject(j);
                CategoryItem categoryItem = new CategoryItem();
                categoryItem.setId(elem.getString("cat_id"));
                categoryItem.setName(elem.getString("name"));
                categoryItem.setImage(elem.getString("cat_img"));
                categoryItems.add(categoryItem);

            }


                 Log.e("Response :", categoryItems + "");
                categoryData = new CategoryData();
                categoryData.setCategoryItems(categoryItems);


        } catch (Exception e) {

        }
        return categoryData;
    }

    public GetStateNameList StateNameList(JSONObject jsonObject) {
        GetStateNameList categoryData = null;

        try {
            int i;
            List<GetStateNameDetail> categoryItems = new ArrayList();

            JSONArray categoryArray = jsonObject.getJSONArray("location");


            for (int j = 0; j < categoryArray.length(); j++) {
                JSONObject elem = categoryArray.getJSONObject(j);
                GetStateNameDetail categoryItem = new GetStateNameDetail();
                categoryItem.setId(elem.getString("state_id"));
                categoryItem.setName(elem.getString("state_name"));
                categoryItems.add(categoryItem);

            }

            Log.e("Response :", categoryItems + "");
            categoryData = new GetStateNameList();
            categoryData.setCategoryItems(categoryItems);

        } catch (Exception e) {

        }
        return categoryData;
    }

    public GetCityNameList CityNameList(JSONObject jsonObject) {
        GetCityNameList categoryData = null;

        try {
            int i;
            List<GetCityNameDetail> categoryItems = new ArrayList();

            JSONArray categoryArray = jsonObject.getJSONArray("location");


            for (int j = 0; j < categoryArray.length(); j++) {
                JSONObject elem = categoryArray.getJSONObject(j);
                GetCityNameDetail categoryItem = new GetCityNameDetail();
                categoryItem.setId(elem.getString("city_id"));
                categoryItem.setName(elem.getString("city_name"));
                categoryItems.add(categoryItem);

            }
            Log.e("Response :", categoryItems + "");
            categoryData = new GetCityNameList();
            categoryData.setCategoryItems(categoryItems);


        } catch (Exception e) {

        }
        return categoryData;
    }


    public List<SubCategory> SubCategoryData(String itemId, JSONObject jsonObject) {

        try {
           // this.statusCode = jsonObject.getInt(Games.EXTRA_STATUS);

            if(jsonObject.getInt("success") == 1) {

                List<SubCategory> subCategoryList = ParseSubCategoryData(jsonObject.toString());
                AppPrefs.getAppPrefs(this.context).setSubCategory(itemId, jsonObject.toString());
                Log.d("subcategory", subCategoryList + "");
                return subCategoryList;
            } else
            {
                return null;
            }

        } catch (Exception ex) {
            Log.e("Login", ex.getMessage());
            return null;
        }
    }

    public List<SubCategory> ParseSubCategoryData(String json) {
        Exception ex;
        List<SubCategory> subCategoryList = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            List<SubCategory> subCategoryList2 = new ArrayList();
            try {
                JSONArray sjsonArray = jsonObject.getJSONArray("Product_list");
                SubCategory subCategory = new SubCategory();
                subCategory.setProducts(ParseProductList(sjsonArray));
                subCategoryList2.add(subCategory);

                return subCategoryList2;
            } catch (Exception e) {
                ex = e;
                subCategoryList = subCategoryList2;
                Log.e("Login", ex.getMessage());
                return subCategoryList;
            }
        } catch (Exception e2) {
            ex = e2;
            Log.e("Login", ex.getMessage());
            return subCategoryList;
        }
    }

    public List<Product> SearchData(JSONObject jsonObject) {
        Exception ex;
        List<Product> productList = null;
        try {
            this.statusCode = jsonObject.getInt(Games.EXTRA_STATUS);
            if (this.statusCode == 200) {
                if (!jsonObject.isNull("data")) {
                    List<Product> productList2 = new ArrayList();
                    try {
                        productList = ParseProductList(jsonObject.getJSONArray("data"));
                    } catch (Exception e) {
                        ex = e;
                        productList = productList2;
                        Log.e("Login", ex.getMessage());
                        return productList;
                    }
                }
            } else if (!jsonObject.isNull("error_msg")) {
                this.message = jsonObject.getString("error_msg");
            }
        } catch (Exception e2) {
            ex = e2;
            Log.e("Login", ex.getMessage());
            return productList;
        }
        return productList;
    }

    private List<Product> ParseProductList(JSONArray pjsonArray) {


        List<Product> productList = new ArrayList();
        int p = 0;
        while (p < pjsonArray.length()) {
            try {
                JSONObject pObject = pjsonArray.getJSONObject(p);
                Product product = new Product();
                product.setProductId(Integer.parseInt(pObject.getString("pro_id")));
                product.setProductName(pObject.getString("product_name"));
                product.setStorename(pObject.getString("store_name"));
                List<Vantage> vantageList = new ArrayList();
                Vantage vantage = new Vantage();
                vantage.setVantageId(Integer.parseInt(pObject.getString("pro_id")));
                vantage.setVantagePrice(pObject.getString("price"));
                vantage.setVantageSize(pObject.getString("size"));
                //vantage.setVantageOldPrice(vjsonObject.getString("old_price"));
                vantage.setVantageImage(pObject.getString("product_img"));
                vantageList.add(vantage);
                product.setVantages(vantageList);
                productList.add(product);
                p++;

                //JSONArray vjsonArray = pObject.getJSONArray("products");

            } catch (Exception ex) {
                Log.e("Login", ex.getMessage());
            }
        }
        return productList;
    }

    public List<Area> AreaData(JSONObject jsonObject) {
        Exception ex;
        List<Area> areaList = null;
        try {
            List<Area> areaList2 = new ArrayList();
            try {
                JSONArray ajsonArray = jsonObject.getJSONArray("data");
                for (int s = 0; s < ajsonArray.length(); s++) {
                    JSONObject ajsonObject = ajsonArray.getJSONObject(s);
                    Area area = new Area();
                    area.setAreaId(Integer.parseInt(ajsonObject.getString("area_id")));
                    area.setAreaName(ajsonObject.getString("area_name"));
                    areaList2.add(area);
                }
                return areaList2;
            } catch (Exception e) {
                ex = e;
                areaList = areaList2;
                Log.e("Login", ex.getMessage());
                return areaList;
            }
        } catch (Exception e2) {
            ex = e2;
            Log.e("Login", ex.getMessage());
            return areaList;
        }
    }

    public List<Address> AddressData(JSONObject jsonObject) {
        Exception ex;
        List<Address> addressList = null;
        try {
            this.statusCode = jsonObject.getInt(Games.EXTRA_STATUS);
            if (this.statusCode == 200) {
                List<Address> addressList2 = new ArrayList();
                try {
                    JSONArray ajsonArray = jsonObject.getJSONArray("data");
                    for (int s = 0; s < ajsonArray.length(); s++) {
                        JSONObject ajsonObject = ajsonArray.getJSONObject(s);
                        Address address = new Address();
                        address.setAddressId(Integer.parseInt(ajsonObject.getString("address_id")));
                        address.setAddressName(ajsonObject.getString("address_name"));
                        address.setAreaId(ajsonObject.getString("area_id"));
                        address.setAreaName(ajsonObject.getString("area_name"));
                        address.setHouseNo(ajsonObject.getString("home_no"));
                        address.setStreetNo(ajsonObject.getString("street"));
                        address.setMobile(ajsonObject.getString("mobile"));
                        address.setAddressStatus(ajsonObject.getString("active_addr"));
                        if (address.getAddressStatus().contains("1")) {
                            AppPrefs.getAppPrefs(this.context).setAddress(address);
                        }
                        addressList2.add(address);
                    }
                    return addressList2;
                } catch (Exception e) {
                    ex = e;
                    addressList = addressList2;
                    Log.e("Login", ex.getMessage());
                    return addressList;
                }
            } else if (jsonObject.isNull("error_msg")) {
                return null;
            } else {
                this.message = jsonObject.getString("error_msg");
                return null;
            }
        } catch (Exception e2) {
            ex = e2;
            Log.e("Login", ex.getMessage());
            return addressList;
        }
    }

    public List<OrderDetail> OrderData(JSONObject jsonObject) {
        Exception ex;
        List<OrderDetail> orderDetailList = null;
        try {
            this.statusCode = jsonObject.getInt("success");
            if (this.statusCode == 1) {
                List<OrderDetail> orderDetailList2 = new ArrayList();
                try {
                    JSONArray ajsonArray = jsonObject.getJSONArray("order_list");
                    for (int s = 0; s < ajsonArray.length(); s++) {
                        JSONObject ajsonObject = ajsonArray.getJSONObject(s);
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrderId(ajsonObject.getString("order_id"));
                        orderDetail.setOrderAmount(ajsonObject.getString("amount"));
                        orderDetail.setOrderDate(ajsonObject.getString("order_date"));
                        orderDetailList2.add(orderDetail);
                    }
                    return orderDetailList2;
                } catch (Exception e) {
                    ex = e;
                    orderDetailList = orderDetailList2;
                    Log.e("Login", ex.getMessage());
                    return orderDetailList;
                }
            } else if (jsonObject.isNull("error_msg")) {
                return null;
            } else {
                this.message = jsonObject.getString("error_msg");
                return null;
            }
        } catch (Exception e2) {
            ex = e2;
            Log.e("Login", ex.getMessage());
            return orderDetailList;
        }
    }

    public List<LatLongData> LatLongData(JSONObject jsonObject) {
        Exception ex;
        List<LatLongData> orderDetailList = null;
        try {
            // this.statusCode = jsonObject.getInt("success");
            // if (this.statusCode == 1) {

            List<LatLongData> orderDetailList2 = new ArrayList();
            try {
                JSONArray ajsonArray = jsonObject.getJSONArray("data");
                for (int s = 0; s < ajsonArray.length(); s++) {
                    JSONObject ajsonObject = ajsonArray.getJSONObject(s);
                    LatLongData orderDetail = new LatLongData();
                    orderDetail.setLatitude(ajsonObject.getString("lat"));
                    orderDetail.setLongitude(ajsonObject.getString("longitude"));
                    orderDetail.setTitle(ajsonObject.getString("name"));
                    orderDetailList2.add(orderDetail);
                }
                return orderDetailList2;
            } catch (Exception e) {
                ex = e;
                orderDetailList = orderDetailList2;
                Log.e("Login", ex.getMessage()+"hellooo");
                return orderDetailList;
            }
            // } else if (jsonObject.isNull("error_msg")) {
            //return null;

            // }
           /* else {
                this.message = jsonObject.getString("error_msg");
                return null;
            }
        } */
        }catch (Exception e2) {
            ex = e2;
            Log.e("Login", ex.getMessage());
            return orderDetailList;
        }
    }

    public int OrderSuccess(JSONObject jsonObject) {
        try {
            this.statusCode = jsonObject.getInt(Games.EXTRA_STATUS);
            if (this.statusCode == 200) {
                return jsonObject.getInt("order_id");
            }
            if (jsonObject.isNull("error_msg")) {
                return 0;
            }
            this.message = jsonObject.getString("error_msg");
            return 0;
        } catch (Exception ex) {
            Log.e("Login", ex.getMessage());
            return 0;
        }
    }

    public List<OrderVariant> OrderVariant(JSONObject jsonObject) {
        Exception ex;
        List<OrderVariant> orderVariantList = null;
        try {
            this.statusCode = jsonObject.getInt("success");
            if (this.statusCode == 1) {
                List<OrderVariant> orderVariantList2 = new ArrayList();
                try {
                    JSONArray ajsonArray = jsonObject.getJSONArray("product_detail");

                        for (int s = 0; s < ajsonArray.length(); s++) {
                            JSONObject ajsonObject = ajsonArray.getJSONObject(s);
                            OrderVariant orderVariant = new OrderVariant();

                            orderVariant.setVantageName(ajsonObject.getString("product_name"));
                            orderVariant.setVantagePrice(ajsonObject.getString("order_price"));
                            orderVariant.setVantageQty(ajsonObject.getString("order_qty"));
                            orderVariant.setImage(ajsonObject.getString("product_img"));
                            orderVariantList2.add(orderVariant);
                        }

                    return orderVariantList2;
                } catch (Exception e) {
                    ex = e;
                    orderVariantList = orderVariantList2;
                    Log.e("Login", ex.getMessage());
                    return orderVariantList;
                }
            } else if (jsonObject.isNull("error_msg")) {
                return null;
            } else {
                this.message = jsonObject.getString("error_msg");
                return null;
            }
        } catch (Exception e2) {
            ex = e2;
            Log.e("Login", ex.getMessage());
            return orderVariantList;
        }
    }
  /*  public List<UserDetail> UserQRData(JSONObject jsonObject) {
        Exception ex;
        List<UserDetail> userDetailList = null;
        try {
            this.statusCode = jsonObject.getInt("success");
            if (this.statusCode == 1) {
                List<UserDetail> userDetailList2 = new ArrayList();
                try {
                    JSONArray ajsonArray = jsonObject.getJSONArray("user_data");
                    for (int s = 0; s < ajsonArray.length(); s++) {

                        JSONObject ajsonObject = ajsonArray.getJSONObject(s);
                        UserDetail orderDetail = new UserDetail();
                        orderDetail.setWallet_point(ajsonObject.getString("wallet_point"));
                        orderDetail.setUser_id(ajsonObject.getString("user_id"));
                        orderDetail.setUsername(ajsonObject.getString("username"));
                        orderDetail.setFirstname(ajsonObject.getString("firstname"));
                        orderDetail.setLastname(ajsonObject.getString("lastname"));
                        orderDetail.setPhone(ajsonObject.getString("phone"));
                        orderDetail.setState(ajsonObject.getString("state"));
                        orderDetail.setCity(ajsonObject.getString("city"));
                        orderDetail.setPincode(ajsonObject.getString("pincode"));
                        orderDetail.setAddress(ajsonObject.getString("address"));
                        orderDetail.setQr_number(ajsonObject.getString("qr_number"));
                        orderDetail.setEmail(ajsonObject.getString("email"));
                        orderDetail.setJoin_date(ajsonObject.getString("join_date"));
                        orderDetail.setProfile_pic(ajsonObject.getString("profile_pic"));

                userDetailList2.add(orderDetail);
                    }
                    return userDetailList2;
                } catch (Exception e) {
                    ex = e;
                    userDetailList = userDetailList2;
                    Log.e("UserDetail", ex.getMessage());
                    return userDetailList;
                }
            } else if (jsonObject.isNull("error_msg")) {
                return null;
            } else {
                this.message = jsonObject.getString("error_msg");
                return null;
            }
        } catch (Exception e2) {
            ex = e2;
            Log.e("UserDetail", ex.getMessage());
            return userDetailList;
        }
    }
*/
    public UserDetailList UserQRData(JSONObject jsonObject) {
        UserDetailList categoryData = null;
        try {
            int i;

            List<UserDetail> categoryItems = new ArrayList();
            JSONArray categoryArray = jsonObject.getJSONArray("user_data");


            for (int j = 0; j < categoryArray.length(); j++) {
                JSONObject ajsonObject = categoryArray.getJSONObject(j);
                UserDetail orderDetail = new UserDetail();
                orderDetail.setWallet_point(ajsonObject.getString("wallet_point"));
                orderDetail.setUser_id(ajsonObject.getString("user_id"));
                orderDetail.setUsername(ajsonObject.getString("username"));
                orderDetail.setFirstname(ajsonObject.getString("firstname"));
                orderDetail.setLastname(ajsonObject.getString("lastname"));
                orderDetail.setPhone(ajsonObject.getString("phone"));
                orderDetail.setState(ajsonObject.getString("state"));
                orderDetail.setCity(ajsonObject.getString("city"));
                orderDetail.setPincode(ajsonObject.getString("pincode"));
                orderDetail.setAddress(ajsonObject.getString("address"));
                orderDetail.setQr_number(ajsonObject.getString("qr_number"));
                orderDetail.setEmail(ajsonObject.getString("email"));
                orderDetail.setJoin_date(ajsonObject.getString("join_date"));
                orderDetail.setProfile_pic(ajsonObject.getString("profile_pic"));

                categoryItems.add(orderDetail);

            }


            Log.e("Response :", categoryItems + "");
            categoryData = new UserDetailList();
            categoryData.setUserDetailItem(categoryItems);


        } catch (Exception e) {

        }
        return categoryData;
    }
}
