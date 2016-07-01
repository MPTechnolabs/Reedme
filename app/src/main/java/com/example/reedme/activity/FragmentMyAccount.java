package com.example.reedme.activity;

import android.Manifest;
import android.app.Activity;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryData;
import com.example.reedme.views.AVLoadingIndicatorView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bhaumik on 9/2/16 for Genatonlinecode.
 */
public class FragmentMyAccount extends AppCompatActivity {

    EditText edt_FirstName,edt_Lastname, edt_MobileNumber, edt_EmailId;
    LinearLayout ll_ChangePassword, ll_ChangeDeliveryAddress;
    JsonObject obj_Data;
    static CategoryData categoryData;
    public static Context context;
    Button btn_Save, btn_Cancel;
    boolean bl_SelectCode = false;
    String str_DeviceId;
    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    //ProgressDialog progress;
    String emailAdd,firstname,lastname,mobile;
    AVLoadingIndicatorView progress;

    JsonObject jsonObjectUpdateProfile;
    String str_FirstName, str_LastName,str_mobile,str_city,star_state,str_country,str_email,str_userid,str_address,str_pincode,str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_account);
        context = this;
        mJsonParser = new MyJSONParser();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("MyObject", "");
        categoryData = gson.fromJson(json, CategoryData.class);

        str_userid = AppPrefs.getAppPrefs(context).getString("user_id");
        str_email = AppPrefs.getAppPrefs(context).getString("email");
        str_FirstName = AppPrefs.getAppPrefs(context).getString("firstname");
        str_LastName = AppPrefs.getAppPrefs(context).getString("lastname");
        str_address = AppPrefs.getAppPrefs(context).getString("address");
        str_mobile = AppPrefs.getAppPrefs(context).getString("phone");
        star_state = AppPrefs.getAppPrefs(context).getString("state");
        str_city = AppPrefs.getAppPrefs(context).getString("city");
        str_pincode = AppPrefs.getAppPrefs(context).getString("pincode");
        str_password = AppPrefs.getAppPrefs(context).getString("password");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Profile");

        findById();
        setAction();
    }

    private void findById( ) {

        edt_FirstName = (EditText) findViewById(R.id.edt_first_name);
        edt_Lastname = (EditText) findViewById(R.id.edt_last_name);
        edt_MobileNumber = (EditText) findViewById(R.id.edt_mobile_name);
        edt_EmailId = (EditText) findViewById(R.id.edt_email_id);
        btn_Cancel = (Button) findViewById(R.id.btn_cancel);
        btn_Save = (Button) findViewById(R.id.btn_save);
        ll_ChangeDeliveryAddress = (LinearLayout) findViewById(R.id.ll_edit_delivery_address);
        ll_ChangePassword = (LinearLayout) findViewById(R.id.ll_change_password);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);

        edt_FirstName.setText(str_FirstName);
        edt_Lastname.setText(str_LastName);
        edt_MobileNumber.setText(str_mobile);
        edt_EmailId.setText(str_email);

    }
    private void setAction() {

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentMyAccount.this, StartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CategoryData", categoryData);
                intent.putExtras(bundle);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                startActivity(intent);
                finish();
            }
        });
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate()) {

                    return;
                }

                new updetprofile().execute();


            }
        });
        ll_ChangeDeliveryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Fragment fragement = new FragmentAllAddressOfUsers();
                //Utills.addFragment(FragmentMyAccount.this, fragement, true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

              /*  Fragment fragement = new FragmentEditDeliveryAddress();
                Utills.addFragment(getActivity(), fragement, true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);*/
            }
        });

        ll_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fragment fragement = new FragmentChangePassword();
                // Utills.addFragment(FragmentMyAccount.this, fragement, true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(FragmentMyAccount.this, StartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryData);
            intent.putExtras(bundle);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FragmentMyAccount.this, StartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("CategoryData", categoryData);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

        startActivity(intent);
        finish();    }
    public boolean validate() {
        boolean valid = true;

        emailAdd = edt_EmailId.getText().toString();
        firstname = edt_FirstName.getText().toString();
        lastname = edt_Lastname.getText().toString();
        mobile = edt_MobileNumber.getText().toString();


        if (emailAdd.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailAdd).matches()) {
            this.edt_EmailId.setError("enter a valid email address");
            valid = false;
        } else {
            edt_EmailId.setError(null);
        }

        if (firstname.isEmpty()) {
            this.edt_FirstName.setError("enter a first name");
            valid = false;
        } else {
            edt_FirstName.setError(null);
        }
        if (lastname.isEmpty()) {
            this.edt_Lastname.setError("enter a last name");
            valid = false;
        } else {
            edt_Lastname.setError(null);
        }

        if (mobile.isEmpty()) {
            this.edt_MobileNumber.setError("enter a mobile number");
            valid = false;
        } else {
            edt_MobileNumber.setError(null);
        }
        return valid;
    }
    public class updetprofile extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Utills.showDialog(context);
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();

                params.put("user_id", str_userid);
                params.put("email", emailAdd);
                params.put("firstname",firstname);
                params.put("lastname",lastname);
                params.put("password", str_password);
                params.put("phone", mobile);
                params.put("address", str_address);
                params.put("city", str_city);
                params.put("state", star_state);
                params.put("country", str_country);
                params.put("pincode", str_pincode);


                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/userSignup.php", params);
                Log.e("Response ------>", jsonObject_parent + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           // Utills.dismissDialog();
            progress.setVisibility(View.GONE);

            LoginCallAction(ParseDataProvider.getInstance(context).IsSuccessRegister(jsonObject_parent));

        }
    }
    protected void LoginCallAction(Integer isSuccess) {
        if (isSuccess == 1) {

                AppPrefs.getAppPrefs(FragmentMyAccount.this).setString("email", emailAdd);
                AppPrefs.getAppPrefs(FragmentMyAccount.this).setString("firstname",firstname);
                AppPrefs.getAppPrefs(FragmentMyAccount.this).setString("lastname",lastname);
                AppPrefs.getAppPrefs(FragmentMyAccount.this).setString("phone", mobile);

            Utills.showCustomSimpleDialog(FragmentMyAccount.this, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {
                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }
            }, Constants.DIALOG_INFO_TITLE, "Profile Update Successfully", false);

        }

        else
        {
            Utills.showCustomSimpleDialog(FragmentMyAccount.this, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {
                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }
            }, Constants.DIALOG_INFO_TITLE, "Something,Went Wrong", false);
        }
    }





}
