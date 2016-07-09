package com.example.reedme.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.ApiHelper;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Utills;
import com.example.reedme.helper.VolleyHelper;
import com.example.reedme.model.CategoryData;
import com.example.reedme.views.AVLoadingIndicatorView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Jolly Raiyani on 3/22/2016.
 */
public class Activity_login extends AppCompatActivity {

    public static final String TAG;
    CategoryData categoryDate;
    public static MyJSONParser mJsonParser = null;
    String pass1, user1;
    AVLoadingIndicatorView progress;

    JSONObject jsonObject_parent = null;
    TextView txt_register_now, txt_ShowPassword;
    EditText edt_EmailId, edt_Password;
    Activity_login obj_Login;

    Boolean bl_InputPasswordType = true;
    TextView txt_merchant;
    Button btn_Login;
    private static final int PERMISSION_REQUEST_CODE = 1;

    static {
        TAG = Activity_login.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);

        setContentView(R.layout.activity_login);
        obj_Login = this;

        mJsonParser = new MyJSONParser();

        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("MyObject", "");
        categoryDate = gson.fromJson(json, CategoryData.class);

        findById();
        setAction();

      /*  if (android.os.Build.VERSION.SDK_INT < 23) {

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            str_DeviceId = telephonyManager.getDeviceId();

        } else {
            requestPermission();
        }
*/
    }

    private void findById() {

        edt_EmailId = (EditText) findViewById(R.id.edt_email_mobile);
        edt_Password = (EditText) findViewById(R.id.edt_password);
        txt_register_now = (TextView) findViewById(R.id.txt_register_now);
        txt_ShowPassword = (TextView) findViewById(R.id.txt_show);
        txt_merchant = (TextView) findViewById(R.id.txt_merchant);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);

        btn_Login = (Button) findViewById(R.id.btn_login);

    }

    private void setAction() {

        txt_register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Activity_login.this, activity_user_register.class));
            }
        });

        txt_merchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((AppPrefs.getAppPrefs(Activity_login.this).getIsMerchantLogin())) {
                    Intent i = new Intent(Activity_login.this, DrawerActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                    finish();

                }
                else
                {
                    Intent intent = new Intent(Activity_login.this, activity_merchant_login.class);
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

                  /*  if (!checkPermission()) {
                        requestPermission();
                    } else {
                        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                        str_DeviceId = telephonyManager.getDeviceId();*/
                        callLoginApi(str_EmailId, str_Password);
                       // Log.e("DeviceId", str_DeviceId);
                    //}
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
            pass1=str_Password;
            new JSONParse().execute();


        } else {

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


            //Utills.showDialog(obj_Login);
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String  doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();

                params.put("device", "0");
                params.put("device_tocken", "1223233");
                params.put("password", pass1);
                params.put("username", user1);

                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/userLogin.php", params);
                Log.e("JsonObject",jsonObject_parent+"");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           // Utills.dismissDialog();
            progress.setVisibility(View.GONE);
            LoginCallAction(ParseDataProvider.getInstance(obj_Login).IsSuccessRegister(jsonObject_parent));

            AppPrefs.getAppPrefs(Activity_login.this).setString("password", pass1);


        }
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(obj_Login, Manifest.permission.READ_PHONE_STATE)) {
            Utills.showCustomSimpleDialog(obj_Login, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {
                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "Please Turn On Phone Permission in Setting", false);
        } else {
            ActivityCompat.requestPermissions(obj_Login, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(obj_Login, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    protected void LoginCallAction(Integer isSuccess) {
        if (isSuccess == 1) {

            AppPrefs.getAppPrefs(obj_Login).setIsLogin(true);

            try {
                JSONObject user =jsonObject_parent.getJSONObject("user_data");
                String userid= user.getString("user_id");
                String email = user.getString("email");
                String firstname = user.getString("firstname");
                String lastname= user.getString("lastname");
                String phone = user.getString("phone");
                String address = user.getString("address");
                String qr_number=user.getString("qr_number");
                String state = user.getString("state");
                String city = user.getString("city");
                String pincode = user.getString("pincode");

                AppPrefs.getAppPrefs(Activity_login.this).setString(Activity_login.this.getResources().getString(R.string.user_id), userid);
                AppPrefs.getAppPrefs(Activity_login.this).setString("qr_number",qr_number);
                AppPrefs.getAppPrefs(Activity_login.this).setString("email", email);
                AppPrefs.getAppPrefs(Activity_login.this).setString("firstname",firstname);
                AppPrefs.getAppPrefs(Activity_login.this).setString("lastname",lastname);
                AppPrefs.getAppPrefs(Activity_login.this).setString("address",address);
                AppPrefs.getAppPrefs(Activity_login.this).setString("phone",phone);
                AppPrefs.getAppPrefs(Activity_login.this).setString("state",state);
                AppPrefs.getAppPrefs(Activity_login.this).setString("city",city);
                AppPrefs.getAppPrefs(Activity_login.this).setString("pincode",pincode);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //AppPrefs.getAppPrefs(obj_Login).setString(obj_Login.getResources().getString(R.string.prefs_user_mobile), this.user.getText().toString());
            Intent i = new Intent(Activity_login.this,StartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryDate);
            i.putExtras(bundle);
            startActivity(i);
            finish();
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
            }, Constants.DIALOG_INFO_TITLE, "Email and password does not match ", false);


        }
    }
}


