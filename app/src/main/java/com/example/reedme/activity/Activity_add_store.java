package com.example.reedme.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.MultipartEntity;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.example.reedme.R;
import com.example.reedme.adapter.CategoryDialogAdapter;
import com.example.reedme.adapter.CityAdapter;
import com.example.reedme.adapter.CountryAdapter;
import com.example.reedme.adapter.StoreAdapter;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.date.DatePickerDialog;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.GetCityNameList;
import com.example.reedme.model.GetCountryNameDetail;
import com.example.reedme.model.GetStateNameList;
import com.example.reedme.views.AVLoadingIndicatorView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.reedme.dataprovider.ParseDataProvider.getInstance;

/**
 * Created by jolly on 5/7/16.
 */
public class Activity_add_store extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    Context context;
    ArrayList myList = new ArrayList();
    String int_SelectCountryId,int_SelectStateId;
    private RelativeLayout rel_two, rel_one;
    private Button iv_add;
    GetCountryNameDetail ld = new GetCountryNameDetail();
    File fileBanner;
    String base64Image;
    String int_categoryId = "";
    EditText edt_fname, edt_EmailId, edt_MobileNumber,edt_category_select,edt_discount;

    EditText edt_City, edt_State, edt_Country, edt_Pincode, edt_Address, edt_toDate,edt_fromDate;
    ImageView img_store,img_camera,img_to,img_from;

    Activity_add_store obj_Registaration;

    Dialog dialog_CityList,dialog_CountryList, dialog_StateList,dialog_category;

    Boolean  bl_SelectCity = false, bl_SelectCode = false;
    String str_FirstName="",  str_EmailId="", str_MobileNumber="", str_city="", str_state="", str_country="",
            str_pincode="", str_Address="", str_discount="",str_fromdate="",str_todate="",str_category;

    String[] country_id = new String[]{
            "101"    };
    String[] country_name = new String[]{
            "India"
    };
    Integer store_id=0;
    View vv;
    AVLoadingIndicatorView progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        context = this;
        obj_Registaration = this;
        mJsonParser = new MyJSONParser();
        idMapping();
        setListeners();

    }
    private void idMapping() {

        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        edt_fname = (EditText) findViewById(R.id.edt_fname);
        edt_EmailId = (EditText) findViewById(R.id.edt_email);
        edt_MobileNumber = (EditText) findViewById(R.id.edt_mobile_number);
        edt_City = (EditText) findViewById(R.id.edt_city);
        edt_State = (EditText) findViewById(R.id.edt_state);
        edt_Country = (EditText) findViewById(R.id.edt_country);
        edt_Pincode = (EditText) findViewById(R.id.edt_pincode);
        edt_Address = (EditText) findViewById(R.id.edt_address);
        edt_toDate = (EditText) findViewById(R.id.edt_toDate);
        edt_fromDate = (EditText) findViewById(R.id.edt_fromDate);
        img_store = (ImageView) findViewById(R.id.img_store);
        img_camera = (ImageView) findViewById(R.id.img_camera);
        img_to = (ImageView) findViewById(R.id.img_to);
        img_from = (ImageView) findViewById(R.id.img_from);
        edt_category_select = (EditText) findViewById(R.id.edt_category_select);
        edt_discount = (EditText) findViewById(R.id.edt_discount);
        iv_add = (Button) findViewById(R.id.iv_add);
    }

    private void setListeners() {

        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        img_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv=v;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        Activity_add_store.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(true);
                dpd.vibrate(true);
                dpd.dismissOnPause(true);
                dpd.showYearPickerFirst(true);
                dpd.show(getFragmentManager(), "DatepickerdialogFrom");

            }
        });

        img_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edt_toDate.setError(null);

                vv = v;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd1 = DatePickerDialog.newInstance(
                        Activity_add_store.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd1.setThemeDark(true);
                dpd1.vibrate(true);
                dpd1.dismissOnPause(true);
                dpd1.showYearPickerFirst(true);
                dpd1.show(getFragmentManager(), "DatepickerdialogTo");

            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_FirstName = edt_fname.getText().toString().trim();

                str_city =  edt_City.getText().toString().trim();
                str_state =  edt_State.getText().toString().trim();
                str_country = edt_Country.getText().toString().trim();
                str_pincode = edt_Pincode.getText().toString().trim();
                str_Address = edt_Address.getText().toString().trim();
                str_EmailId = edt_EmailId.getText().toString().trim();
                str_MobileNumber = edt_MobileNumber.getText().toString().trim();
                str_category = int_categoryId;


                if (str_FirstName == null || str_FirstName.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter First Name", false);
                }

                else if (str_city == null || str_city.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Select  City", false);


                }
                else if (str_category == null || str_category.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Select  Category", false);


                }
                else if (str_EmailId == null || str_EmailId.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Email Id", false);


                } else if (!Utills.isValidEmail(str_EmailId)) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Valid Email Id", false);

                }
                    else if (str_MobileNumber == null || str_MobileNumber.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Mobile Number", false);


                } else if (str_MobileNumber.length() < 9) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Valid Mobile Number", false);

                }else if (str_state == null || str_state.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Select  State", false);


                } else if (str_country == null || str_country.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Country", false);


                } else if (str_pincode == null || str_pincode.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Pincode", false);


                }else if (str_Address == null || str_Address.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Address", false);




                }
                else
                {

                    str_discount = edt_discount.getText().toString();
                    str_todate = edt_toDate.getText().toString();
                    str_fromdate = edt_fromDate.getText().toString();

                    if(!CheckDates(str_fromdate,str_todate))
                    {
                        edt_toDate.setError("Please enter valid date");
                    }
                    else
                    {
                        edt_toDate.setError(null);
                        callAddApi();

                    }

                    //Log.e("DeviceId", str_DeviceId);

                }


            }
        });

        edt_City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bl_SelectCity == false) {

                    if (edt_State.getText().toString() == null || edt_State.getText().toString().equals("")) {

                        Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                            @Override
                            public void onOkayButtonClick() {
                                if (Utills.customSimpleMessageDialog != null) {
                                    Utills.customSimpleMessageDialog.dismiss();
                                }
                            }

                        }, Constants.DIALOG_INFO_TITLE, "Please Select State First", false);
                    } else {

                        getCityList();
                    }
                }
            }
        });

        edt_State.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bl_SelectCode == false) {

                    if (edt_Country.getText().toString() == null || edt_Country.getText().toString().equals("")) {

                        Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                            @Override
                            public void onOkayButtonClick() {
                                if (Utills.customSimpleMessageDialog != null) {
                                    Utills.customSimpleMessageDialog.dismiss();
                                }
                            }

                        }, Constants.DIALOG_INFO_TITLE, "Please Select Country First", false);
                    } else {

                        getStateList();
                    }
                }
            }
        });


        edt_Country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialougSelectCountryList();

            }
        });

        edt_category_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategory();
            }
        });

    }
    private void callAddApi() {

        if (Utills.isConnectingToInternet(obj_Registaration)) {

            //Utills.showDialog(obj_Registaration);

            // createRegistationJson();
            new Add_store().execute();
        }
        else {

            Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {

                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "No Internet", false);
        }

    }
    private void getStateList() {

        if (Utills.isConnectingToInternet(obj_Registaration)) {
            new GetState().execute();
        }
        else {

            Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {


                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "No Internet", false);
        }
    }
    private void getCategory() {

        if (Utills.isConnectingToInternet(obj_Registaration)) {
            new GetCategory().execute();
        }
        else {

            Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {


                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "No Internet", false);
        }
    }
    private void getCityList() {

        if (Utills.isConnectingToInternet(obj_Registaration)) {
            new GetCity().execute();
        }
        else {

            Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {


                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "No Internet", false);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (vv.getId() == R.id.img_from) {
           // String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
            String date = year+"-"+(++monthOfYear)+"-"+dayOfMonth;

            edt_fromDate.setText(date);
        }
        else {
            //String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
            String date = year+"-"+(++monthOfYear)+"-"+dayOfMonth;

            edt_toDate.setText(date);

        }
    }



    public boolean CheckDates(String str_fromdate, String str_todate)   {
        SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");

        boolean b = false;
        try {
            if(dfDate.parse(str_fromdate).before(dfDate.parse(str_todate)))
            {
                b = true;//If start date is before end date
            }
            else if(dfDate.parse(str_fromdate).equals(dfDate.parse(str_todate)))
            {
                b = false;//If two dates are equal
            }
            else
            {
                b = false; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }



    private class GetState extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();


                params.put("country_id", int_SelectCountryId);


                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/getlocation.php", params);
                Log.e("JsonObject", jsonObject_parent + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            GetStoreList(ParseDataProvider.getInstance(obj_Registaration).StateNameList(jsonObject_parent));

        }
    }

    private class GetCity extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();


                params.put("state_id", int_SelectStateId);


                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/getCity.php", params);
                Log.e("JsonObject", jsonObject_parent + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            openDialougSelectCityList(ParseDataProvider.getInstance(obj_Registaration).CityNameList(jsonObject_parent));

        }
    }
    private class GetCategory extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();



                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/getCategories.php", params);
                Log.e("JsonObject", jsonObject_parent + "");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            CategoryCallAction(getInstance(Activity_add_store.this.context).CategoryData(jsonObject_parent));
        }
    }

    private void CategoryCallAction(final CategoryData categoryData) {

        dialog_category = new Dialog(obj_Registaration);
        dialog_category.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_category.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_category.getWindow().setGravity(Gravity.CENTER);

        dialog_category.setCanceledOnTouchOutside(false);

        dialog_category.setContentView(R.layout.dialoug_select_heard_about_us);


        TextView txt_HeadeTitle = (TextView) dialog_category.findViewById(R.id.txt_title_dialog);

        txt_HeadeTitle.setText("Select Category");

        ListView lst_view = (ListView) dialog_category.findViewById(R.id.lv_select_state);


        CategoryDialogAdapter adp_AreaList = new CategoryDialogAdapter(obj_Registaration, categoryData.getCategoryItems());

        lst_view.setAdapter(adp_AreaList);


        lst_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edt_City.setText("");
                edt_category_select.setText(categoryData.getCategoryItems().get(position).getName());
                int_categoryId = (categoryData.getCategoryItems().get(position).getId());
                dialog_category.dismiss();

            }
        });
        dialog_category.show();
    }


    private void GetStoreList(final GetStateNameList SateList) {

        dialog_StateList = new Dialog(obj_Registaration);
        dialog_StateList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_StateList.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_StateList.getWindow().setGravity(Gravity.CENTER);

        dialog_StateList.setCanceledOnTouchOutside(false);

        dialog_StateList.setContentView(R.layout.dialoug_select_heard_about_us);


        TextView txt_HeadeTitle = (TextView) dialog_StateList.findViewById(R.id.txt_title_dialog);

        txt_HeadeTitle.setText("Select State");

        ListView lst_view = (ListView) dialog_StateList.findViewById(R.id.lv_select_state);


        StoreAdapter adp_AreaList = new StoreAdapter(obj_Registaration, SateList.getCategoryItems());

        lst_view.setAdapter(adp_AreaList);


        lst_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edt_City.setText("");
                edt_State.setText(SateList.getCategoryItems().get(position).getName());
                int_SelectStateId = (SateList.getCategoryItems().get(position).getId());
                dialog_StateList.dismiss();

            }
        });
        dialog_StateList.show();
    }
    private void openDialougSelectCityList(final GetCityNameList cityList) {

        dialog_CityList = new Dialog(obj_Registaration);
        dialog_CityList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_CityList.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_CityList.getWindow().setGravity(Gravity.CENTER);

        dialog_CityList.setCanceledOnTouchOutside(false);

        //setting custom layout to dialog_car_variant
        dialog_CityList.setContentView(R.layout.dialoug_select_heard_about_us);


        TextView txt_HeadeTitle = (TextView) dialog_CityList.findViewById(R.id.txt_title_dialog);

        txt_HeadeTitle.setText("Select City");

        ListView lst_view = (ListView) dialog_CityList.findViewById(R.id.lv_select_state);


        CityAdapter adp_CityList = new CityAdapter(obj_Registaration, cityList.getCategoryItems());

        lst_view.setAdapter(adp_CityList);


        lst_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edt_City.setText(cityList.getCategoryItems().get(position).getName());
                int_SelectStateId = (cityList.getCategoryItems().get(position).getId());
                dialog_CityList.dismiss();

            }
        });
        dialog_CityList.show();
    }


    private void openDialougSelectCountryList() {

        dialog_CountryList = new Dialog(obj_Registaration);
        dialog_CountryList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_CountryList.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_CountryList.getWindow().setGravity(Gravity.CENTER);
        dialog_CountryList.setCanceledOnTouchOutside(false);

        //setting custom layout to dialog_car_variant
        dialog_CountryList.setContentView(R.layout.dialoug_select_heard_about_us);


        TextView txt_HeadeTitle = (TextView) dialog_CountryList.findViewById(R.id.txt_title_dialog);

        txt_HeadeTitle.setText("Select Country");

        final ListView lst_view = (ListView) dialog_CountryList.findViewById(R.id.lv_select_state);


        getDataInList();
        CountryAdapter adapter = new CountryAdapter(obj_Registaration, myList);
        lst_view.setAdapter(adapter);

        lst_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edt_State.setText("");
                edt_City.setText("");

                edt_Country.setText(ld.getName());
                int_SelectCountryId = (ld.getId());
                dialog_CountryList.dismiss();

            }
        });
        dialog_CountryList.show();
    }
    private void getDataInList() {
        for (int i = 0; i < country_id.length; i++) {
            ld.setId(country_id[i]);
            ld.setName(country_name[i]);
            // Add this object into the ArrayList myList
            myList.add(ld);
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_add_store.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmapOptions.inSampleSize = 8;

                    bitmap = getResizedBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions), 200);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(getImageOrientation(f.getAbsolutePath()));
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, baos);
                    byte b[] = baos.toByteArray();
                    base64Image = Base64.encodeToString(b, Base64.DEFAULT);
                    Log.e("byte array", base64Image);


                    img_store.setImageBitmap(rotatedBitmap);
                    f.delete();
                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator;

                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".png");
                    //  new UploadProfileImageAsync(file).execute();
                    FileBanner(file);

                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);

                c.close();
                Bitmap thumbnail = getResizedBitmap((BitmapFactory.decodeFile(picturePath)), 100);


                Log.w("path from gallery", picturePath + "");

                Matrix matrix = new Matrix();
                matrix.postRotate(getImageOrientation(picturePath));
                Bitmap rotatedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(),
                        thumbnail.getHeight(), matrix, true);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte b[] = baos.toByteArray();
                base64Image = Base64.encodeToString(b, Base64.DEFAULT);

                img_store.setImageBitmap(rotatedBitmap);
                byte[] bytarray = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                        bytarray.length);
                File file = new File(picturePath);
                //new UploadProfileImageAsync(file).execute();
                FileBanner(file);

            }
        }
    }

    private void FileBanner(File file) {

        fileBanner = file;

    }


    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("DatepickerdialogFrom");
        DatePickerDialog dpd1 = (DatePickerDialog) getFragmentManager().findFragmentByTag("DatepickerdialogTo");

        if(dpd != null) dpd.setOnDateSetListener(this);
        if(dpd1 != null) dpd1.setOnDateSetListener(this);

    }

    class Add_store extends AsyncTask<String, Void, String> {

        String sResponse = null;

        private String mfilePath;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Utills.showDialog(context);


        }

        @Override
        protected String doInBackground(String... params) {
            try {
//                MultipartEntityBuilder entity;
                String url = "http://www.mptechnolabs.com/1reward/addstore.php";
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                post.addHeader("Accept", "application/json");

                Part[] parts = {
                        new StringPart("m_id", AppPrefs.getAppPrefs(Activity_add_store.this).getString("m_user")),
                        new StringPart("name",str_FirstName),
                        new StringPart("cat_id",str_category),
                        new StringPart("store_email",str_EmailId),
                        new StringPart("contact_no",str_MobileNumber),
                        new StringPart("address",str_Address),
                        new StringPart("city",str_city),
                        new StringPart("state",str_state),
                        new StringPart("country",str_country),
                        new StringPart("pincode",str_pincode),
                        new StringPart("from_date",str_fromdate),
                        new StringPart("to_date",str_todate),
                        new StringPart("discount",str_discount),
                        new StringPart("status","0"),
                        new StringPart("user_status","0"),
                        new StringPart("lat","35.4545"),
                        new StringPart("long","34.4545"),

                        new FilePart("store_img", fileBanner)
                };

                Log.e ("file baaner : ",fileBanner.toString());
                MultipartEntity reqEntity = new MultipartEntity(parts, post.getParams());

                post.setEntity(reqEntity);
                Log.e("multipart",reqEntity+"");


                try {
                    sResponse = EntityUtils.toString(client.execute(post).getEntity(), "UTF-8");
                    Log.d("post",post.toString());
                    Log.e("Response", sResponse);



                    JSONObject object = new JSONObject(sResponse);
                    store_id = object.getInt("success");


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    client.getConnectionManager().shutdown();
                }

            } catch (Exception e) {
                Log.e(e.getClass().getName(), e.getMessage(), e);

            }
            return sResponse;
        }

        @Override
        protected void onPostExecute(String sResponse) {
//            Log.i("UpdateBanner", sResponse);
            Utills.dismissDialog();
            try {

                if (sResponse != null) {

                    Log.i("Res", sResponse);
                    if(store_id == 1)
                    {

                        AppPrefs.getAppPrefs(Activity_add_store.this).setString("store_id", "2");

                        Toast.makeText(Activity_add_store.this,"successfully added",Toast.LENGTH_LONG).show();
                        Intent i_Login = new Intent(obj_Registaration, activity_merchant_login.class);
                        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                        i_Login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i_Login.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i_Login);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(Activity_add_store.this,"store Can't added Please Try Again...",Toast.LENGTH_LONG).show();

                    }

                }

            } catch (Exception e) {
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
