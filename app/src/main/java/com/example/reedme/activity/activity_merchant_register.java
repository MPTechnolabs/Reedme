package com.example.reedme.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import com.example.reedme.model.GetCityNameList;
import com.example.reedme.model.GetCountryNameDetail;
import com.example.reedme.model.GetStateNameList;
import com.example.reedme.views.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Android on 6/24/2016.
 */
public class activity_merchant_register extends AppCompatActivity {

    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    Context context;
    ArrayList myList = new ArrayList();
    String int_SelectCountryId,int_SelectStateId;
    private RelativeLayout rel_two, rel_one;
    private Button iv_next, iv_signup;
    GetCountryNameDetail ld = new GetCountryNameDetail();

    private ImageView iv_shipping_back, iv_signup_back;

    EditText edt_FirstName, edt_LastName, edt_EmailId, edt_Password, edt_ConfirmPassword, edt_MobileNumber;

    EditText edt_City, edt_State, edt_Country, edt_Pincode, edt_Address, edt_SpecialInstruction,edt_category;
    activity_merchant_register obj_Registaration;

    Dialog dialog_CityList,dialog_CountryList, dialog_StateList;

    Boolean  bl_SelectCity = false, bl_SelectCode = false;
    String str_FirstName, str_category,str_LastName, str_EmailId, str_Password, str_ConfirmPassword, str_MobileNumber, str_AppInfo, str_city, str_state, str_country, str_pincode, str_Address, str_SpecialIntruction;
    CheckBox cb_TermCondition;
    TextView txt_ShowPassword, txt_ConfirmPassword;
    Boolean bl_InputPasswordType = true;
    Boolean bl_InputConfirmPasswordType = true;
    String[] country_id = new String[]{
            "101"    };
    String[] country_name = new String[]{
            "India"
    };
    AVLoadingIndicatorView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        obj_Registaration = this;
        mJsonParser = new MyJSONParser();
        idMapping();
        setListeners();


    }
    private void idMapping() {

        rel_one = (RelativeLayout) findViewById(R.id.rel_one);
        rel_two = (RelativeLayout) findViewById(R.id.rel_two);
        iv_signup = (Button) findViewById(R.id.iv_signup);
        iv_next = (Button) findViewById(R.id.iv_next);
        iv_shipping_back = (ImageView) findViewById(R.id.iv_shipping_back);
        iv_signup_back = (ImageView) findViewById(R.id.iv_signup_back);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);

        // First Page
        edt_FirstName = (EditText) findViewById(R.id.edt_fname);
        edt_LastName = (EditText) findViewById(R.id.edt_lname);
        edt_EmailId = (EditText) findViewById(R.id.edt_email);
        edt_Password = (EditText) findViewById(R.id.edt_password);
        edt_ConfirmPassword = (EditText) findViewById(R.id.edt_conpassword);
        edt_MobileNumber = (EditText) findViewById(R.id.edt_mobile_number);

        // Second  Page
        edt_City = (EditText) findViewById(R.id.edt_city);
        edt_State = (EditText) findViewById(R.id.edt_state);
        edt_Country = (EditText) findViewById(R.id.edt_country);
        edt_Pincode = (EditText) findViewById(R.id.edt_pincode);
        edt_Address = (EditText) findViewById(R.id.edt_address);
        edt_SpecialInstruction = (EditText) findViewById(R.id.edt_spe_instruction);
        edt_category = (EditText) findViewById(R.id.edt_category);
        cb_TermCondition = (CheckBox) findViewById(R.id.checkbox_terms);

        txt_ShowPassword = (TextView) findViewById(R.id.txt_show_password);
        txt_ConfirmPassword = (TextView) findViewById(R.id.txt_show_confirm_password);

    }

    private void setListeners() {


        iv_shipping_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_two.setVisibility(View.GONE);
                rel_one.setVisibility(View.VISIBLE);
            }
        });

        iv_signup_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_FirstName = edt_FirstName.getText().toString().trim();
                str_LastName = edt_LastName.getText().toString().trim();
                str_EmailId = edt_EmailId.getText().toString().trim();
                str_Password = edt_Password.getText().toString().trim();
                str_ConfirmPassword = edt_ConfirmPassword.getText().toString().trim();
                str_MobileNumber = edt_MobileNumber.getText().toString().trim();

                if (str_FirstName == null || str_FirstName.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter First Name", false);


                } else if (str_LastName == null || str_LastName.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter LastName", false);



                } else if (str_EmailId == null || str_EmailId.equals("")) {

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


                } else if (str_Password == null || str_Password.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Password", false);


                } else if (str_ConfirmPassword == null || str_ConfirmPassword.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Confirm Password", false);


                } else if (!str_Password.matches(str_ConfirmPassword)) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Your Password Does not Match", false);


                } else if (str_MobileNumber == null || str_MobileNumber.equals("")) {

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

                }  else {

                    rel_two.setVisibility(View.VISIBLE);
                    rel_one.setVisibility(View.GONE);
                }
            }
        });

        iv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_city =  edt_City.getText().toString().trim();
                str_state =  edt_State.getText().toString().trim();
                str_country = edt_Country.getText().toString().trim();
                str_pincode = edt_Pincode.getText().toString().trim();
                str_Address = edt_Address.getText().toString().trim();
                str_category = edt_category.getText().toString().trim();
                str_SpecialIntruction = edt_SpecialInstruction.getText().toString().trim();



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


                } else if (str_category == null || str_category.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Category", false);
                }else if (str_Address == null || str_Address.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Address", false);


                } else if (!cb_TermCondition.isChecked()) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please First Agree Terms & Condition", false);


                } else {

                    callRegisterApi();

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

        txt_ShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bl_InputPasswordType) {
                    bl_InputPasswordType = true;
                    txt_ShowPassword.setText("show");

                } else {
                    bl_InputPasswordType = false;

                    txt_ShowPassword.setText("hide");
                }


                if (!bl_InputPasswordType) {

                    edt_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);


                } else {

                    edt_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


                }
            }
        });

        txt_ConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bl_InputConfirmPasswordType) {
                    bl_InputConfirmPasswordType = true;
                    txt_ConfirmPassword.setText("show");

                } else {
                    bl_InputConfirmPasswordType = false;

                    txt_ConfirmPassword.setText("hide");
                }


                if (!bl_InputConfirmPasswordType) {

                    edt_ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);


                } else {

                    edt_ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
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

                UUID uniqueKey = UUID.randomUUID();


                params.put("m_name","");
                params.put("registration_id","1234");
                params.put("discount_rate","");
                params.put("firstname",str_FirstName);
                params.put("lastname",str_LastName);
                params.put("email", str_EmailId);
                params.put("password", str_Password);
                params.put("mobile_no", str_MobileNumber);
                params.put("m_address", str_Address);
                params.put("city", str_city);
                params.put("state", str_state);
                params.put("country", str_country);
                params.put("pincode", str_pincode);
                params.put("lati", 72.4554);
                params.put("longi", 84.3433);
                params.put("m_logo", "http:");
                params.put("m_catagory",str_category);

                params.put("m_qr",uniqueKey.toString());

                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/mechantRegister.php", params);

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
            try {

                String user = jsonObject_parent.getString("user_data");
                AppPrefs.getAppPrefs(activity_merchant_register.this).setString("m_user", user);

                Intent i_Login = new Intent(obj_Registaration, Activity_add_store.class);
                i_Login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i_Login.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i_Login);
                AppPrefs.getAppPrefs(activity_merchant_register.this).setString("store_id", isSuccess.toString());

                finish();
            }catch (Exception e)
            {}
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
            }, Constants.DIALOG_INFO_TITLE, "Something,Went Wrong Try Again", false);

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
