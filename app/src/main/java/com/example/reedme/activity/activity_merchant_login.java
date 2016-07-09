package com.example.reedme.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.login_code;
import com.example.reedme.views.AVLoadingIndicatorView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Android on 6/24/2016.
 */
public class activity_merchant_login extends AppCompatActivity {

    public static final String TAG;
    CategoryData categoryDate;
    public static MyJSONParser mJsonParser = null;
    String pass1, user1;

    JSONObject jsonObject_parent = null;
    TextView txt_register_now, txt_ShowPassword;
    EditText edt_EmailId, edt_Password;
    activity_merchant_login obj_Login;
    ProgressBar progressBar;
    Boolean bl_InputPasswordType = true;
    Button btn_Login;
    EditText inputCode;
    Button btn_verify_code;
    TextView txt_user;
    LinearLayout layout_login,layout_code;
    static {
        TAG = activity_merchant_login.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);


        setContentView(R.layout.activity_merchant_login);
        obj_Login = this;
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("MyObject", "");
        categoryDate = gson.fromJson(json, CategoryData.class);
        mJsonParser = new MyJSONParser();

        findById();
        setAction();

    }

    private void findById() {

        edt_EmailId = (EditText) findViewById(R.id.edt_email_mobile);
        edt_Password = (EditText) findViewById(R.id.edt_password);
        txt_user = (TextView) findViewById(R.id.txt_user);

        txt_register_now = (TextView) findViewById(R.id.txt_register_now);
        txt_ShowPassword = (TextView) findViewById(R.id.txt_show);
        layout_code = (LinearLayout) findViewById(R.id.layout_code);
        layout_login = (LinearLayout) findViewById(R.id.layout_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        inputCode = (EditText) findViewById(R.id.inputCode);
        btn_verify_code = (Button) findViewById(R.id.btn_verify_code);

        btn_Login = (Button) findViewById(R.id.btn_login);

    }

    private void setAction() {

        txt_register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(activity_merchant_login.this, activity_merchant_register.class));
            }
        });

        txt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((AppPrefs.getAppPrefs(activity_merchant_login.this).getIsLogin())) {
                    Intent i = new Intent(activity_merchant_login.this, StartActivity.class);
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CategoryData", categoryDate);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();

                }
                else
                {

                    Intent intent = new Intent(activity_merchant_login.this, Activity_login.class);
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputCode.getText().toString().equals(""))
                {
                    inputCode.setError("Plese Enter code");
                }
                else
                {
                    inputCode.setError(null);
                    Intent intent = new Intent(activity_merchant_login.this, DrawerActivity.class);
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                    startActivity(intent);
                    finish();
                }
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_EmailId = edt_EmailId.getText().toString().trim();
                String str_Password = edt_Password.getText().toString().trim();

                if (str_EmailId == null || str_EmailId.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Login, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Email Id or Username", false);

                } else if (str_Password == null || str_Password.equals("")) {
                    Utills.showCustomSimpleDialog(obj_Login, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Password", false);
                } else {


                    callLoginApi(str_EmailId, str_Password);

                }
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
    }
    private void callLoginApi(String str_EmailId, String str_Password) {
        if (Utills.isConnectingToInternet(obj_Login)) {
            user1 = str_EmailId;
            pass1 = str_Password;
            new JSONParse().execute();
        }
        else
        {

            Utills.showCustomSimpleDialog(obj_Login, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
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
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String  doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();

                params.put("device", "0");
                params.put("device_tocken", "1223233");
                params.put("password", pass1);
                params.put("email", user1);

                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/merchantLogin.php", params);
                Log.e("JsonObject", jsonObject_parent + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);
            LoginCallAction(ParseDataProvider.getInstance(obj_Login).IsSuccessRegister(jsonObject_parent));
        }
    }


    protected void LoginCallAction(Integer isSuccess) {
        if (isSuccess == 1) {

/*
            AppPrefs.getAppPrefs(obj_Login).setIsMerchantLogin(true);
*/

            try {

                JSONObject user =jsonObject_parent.getJSONObject("user_data");
                String userid= user.getString("id");
                String m_name = user.getString("m_name");
                String m_address = user.getString("m_address");
                String m_state =user.getString("state");
                String m_city =user.getString("city");
                String m_discount =user.getString("discount_rate");
                String m_country =user.getString("country");
                String m_flag =user.getString("m_flag");
                String m_pincode =user.getString("pincode");
                String m_category = user.getString("m_catagory");
                String m_qr = user.getString("m_qr");
                String lati = user.getString("lati");
                String longi = user.getString("longi");
                String purchase_flag = user.getString("m_purchase_flag");
                String email = user.getString("email");
                String firstname = user.getString("firstname");
                String lastname= user.getString("lastname");
                String phone = user.getString("mobile_no");
                String m_logo = user.getString("m_logo");



                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_password", pass1);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_user", userid);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_email", email);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_firstname",firstname);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_lastname",lastname);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_phone",phone);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_state",m_state);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_city",m_city);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_pincode",m_pincode);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_name",m_name);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_address",m_address);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_discount",m_discount);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_country",m_country);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_flag",m_flag);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_category",m_category);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_qr",m_qr);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_lati",lati);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_longi",longi);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_purchase_flag",purchase_flag);
                AppPrefs.getAppPrefs(activity_merchant_login.this).setString("m_logo",m_logo);

                JSONArray jsonStore = user.optJSONArray("store_data");

                if(jsonStore != null) {
                    AppPrefs.getAppPrefs(obj_Login).setIsMerchantLogin(true);

                    for (int i = 0; i < jsonStore.length(); i++) {

                        JSONObject jsonChildNode = jsonStore.getJSONObject(i);

                        login_code code = new login_code();
                        code.setStore_id(jsonChildNode.getString("store_id"));
                        code.setStore_code(jsonChildNode.getString("store_code"));

                    }
                    layout_login.setVisibility(View.GONE);
                    layout_code.setVisibility(View.VISIBLE);
                }
                else
                {
                    Intent i_Login = new Intent(activity_merchant_login.this, Activity_add_store.class);
                    i_Login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i_Login.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i_Login);
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
           /* Intent intent = new Intent(activity_merchant_login.this, activity_merchant_login.class);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

            startActivity(intent);
            finish();*/
/*
            if(AppPrefs.getAppPrefs(activity_merchant_login.this).getString("store_id").equals("1"))
            {

            }*/



        }
        else
        {

            Utills.showCustomSimpleDialog(obj_Login, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {
                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }
            }, Constants.DIALOG_INFO_TITLE, "Email and password does not match", false);


            edt_EmailId.setFocusable(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
