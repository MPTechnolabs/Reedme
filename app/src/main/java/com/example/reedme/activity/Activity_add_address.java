package com.example.reedme.activity;

/**
 * Created by jolly on 15/7/16.
 */
        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.graphics.drawable.ColorDrawable;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.telephony.TelephonyManager;
        import android.text.InputType;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.MenuItem;
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

        import com.example.reedme.R;
        import com.example.reedme.adapter.CityAdapter;
        import com.example.reedme.adapter.CountryAdapter;
        import com.example.reedme.adapter.StoreAdapter;
        import com.example.reedme.dataprovider.ParseDataProvider;

        import com.example.reedme.helper.AppPrefs;
        import com.example.reedme.helper.Constants;
        import com.example.reedme.helper.CustomSimpleMessageDialog;
        import com.example.reedme.helper.MyJSONParser;
        import com.example.reedme.helper.Utills;
        import com.example.reedme.helper.VolleyHelper;
        import com.example.reedme.model.CategoryData;
        import com.example.reedme.model.GetCityNameDetail;
        import com.example.reedme.model.GetCityNameList;
        import com.example.reedme.model.GetCountryNameDetail;
        import com.example.reedme.model.GetStateNameDetail;
        import com.example.reedme.model.GetStateNameList;
        import com.example.reedme.views.AVLoadingIndicatorView;
        import com.google.gson.Gson;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

        import java.util.UUID;


/**
 * Created by Jolly Raiyani on 4/7/2016.
 */
public class Activity_add_address extends AppCompatActivity {

    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    Context context;
    ArrayList myList = new ArrayList();
    ArrayList areaList = new ArrayList();
    String int_SelectCountryId,int_SelectStateId;
    private Button iv_add;
    GetCountryNameDetail ld = new GetCountryNameDetail();
    EditText edt_City, edt_State, edt_Country, edt_Pincode, edt_Address, edt_mobile;
    Activity_add_address obj_Registaration;
    Dialog  dialog_CityList,dialog_CountryList, dialog_StateList;
    Boolean  bl_SelectCity = false, bl_SelectCode = false;
    String  str_MobileNumber="",  str_city="", str_state="", str_country="", str_pincode="", str_Address="", str_SpecialIntruction="",str_age="";
    private static final int PERMISSION_REQUEST_CODE = 1;
    String[] country_id = new String[]{
            "101"    };
    String[] country_name = new String[]{
            "India"
    };
    static CategoryData categoryData;

    AVLoadingIndicatorView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        obj_Registaration = this;
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("MyObject", "");
        categoryData = gson.fromJson(json, CategoryData.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Add Address");
        mJsonParser = new MyJSONParser();
        idMapping();
        setListeners();

    }
    private void idMapping()
    {

        edt_City = (EditText) findViewById(R.id.edt_city);
        edt_State = (EditText) findViewById(R.id.edt_state);
        edt_Country = (EditText) findViewById(R.id.edt_country);
        edt_Pincode = (EditText) findViewById(R.id.edt_pincode);
        edt_Address = (EditText) findViewById(R.id.edt_address);
        iv_add = (Button) findViewById(R.id.iv_add);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile_number);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);

    }
    private void setListeners() {

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_city =  edt_City.getText().toString().trim();
                str_state =  edt_State.getText().toString().trim();
                str_country = edt_Country.getText().toString().trim();
                str_pincode = edt_Pincode.getText().toString().trim();
                str_Address = edt_Address.getText().toString().trim();
                str_MobileNumber = edt_mobile.getText().toString().trim();


                if (str_city == null || str_city.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Select  City", false);

                } else if (str_state == null || str_state.equals("")) {

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


                } else if (str_Address == null || str_Address.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Address", false);

                } else {

                    callRegisterApi();

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

    }

    private void callRegisterApi() {

        if (Utills.isConnectingToInternet(obj_Registaration)) {

            //Utills.showDialog(obj_Registaration);

            // createRegistationJson();
            new JSONParse().execute();
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

    private class JSONParse extends AsyncTask<String,String,String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Utills.showDialog(obj_Registaration);
            progress.setVisibility(View.VISIBLE);


        }
        @Override
        protected String  doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();

                params.put("user_id",AppPrefs.getAppPrefs(Activity_add_address.this).getString(Activity_add_address.this.getResources().getString(R.string.user_id)));
                params.put("phone", str_MobileNumber);
                params.put("address", str_Address);
                params.put("city", str_city);
                params.put("state", str_state);
                params.put("country", str_country);
                params.put("pincode", str_pincode);

                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/14-04-2016userSignup.php", params);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Utills.dismissDialog();
            progress.setVisibility(View.GONE);

            LoginCallAction(ParseDataProvider.getInstance(context).IsSuccessRegister(jsonObject_parent));

        }
    }

    protected void LoginCallAction(Integer isSuccess) {

        if(isSuccess == 1) {

            Toast.makeText(Activity_add_address.this,"Successfully Added",Toast.LENGTH_LONG).show();
            AppPrefs.getAppPrefs(Activity_add_address.this).setString("address",str_Address);
            AppPrefs.getAppPrefs(Activity_add_address.this).setString("phone",str_MobileNumber);
            AppPrefs.getAppPrefs(Activity_add_address.this).setString("state",str_state);
            AppPrefs.getAppPrefs(Activity_add_address.this).setString("city",str_city);
            AppPrefs.getAppPrefs(Activity_add_address.this).setString("pincode",str_pincode);
            AppPrefs.getAppPrefs(Activity_add_address.this).setString("country",str_country);
           /* Intent intent = new Intent(this, CheckoutContinueActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryData);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtras(bundle);
            startActivity(intent);*/
            finish();

        }
        else
        {
            Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {

                    if (Utills.customSimpleMessageDialog != null) {

                        Utills.customSimpleMessageDialog.dismiss();

                    }
                }
            }, Constants.DIALOG_INFO_TITLE, "Can't Add Address", false);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

}
