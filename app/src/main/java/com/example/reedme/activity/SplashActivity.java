package com.example.reedme.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.reedme.R;
import com.example.reedme.helper.ApiHelper;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Util;
import com.example.reedme.helper.VolleyHelper;
import com.example.reedme.model.CategoryData;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import static com.example.reedme.dataprovider.ParseDataProvider.getInstance;

public class SplashActivity extends Activity {
    Context context;
    Intent intent;
    class C04941 implements OnClickListener {
        C04941() {
        }

        public void onClick(DialogInterface dialog, int id) {
            SplashActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
            SplashActivity.this.finish();
        }
    }
    class C04952 implements OnClickListener {
        C04952() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
            SplashActivity.this.Init();
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(1);
        try {
            setContentView(R.layout.activity_splash);
            this.context = this;
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);


           // ((TextView) findViewById(R.id.txt_splash_icon_name)).setTypeface(Helper.getInstance(this.context).getArlrdbdFont());
            Init();

        } catch (Exception ex) {
        }
    }

    public void Init() {
        if (Util.getInstance(this.context).IsNetworkAvailable()) {
            CategoryCall();
        } else {
            showAlertDialog();
        }
    }

    public void showAlertDialog() {
        Builder alertDialogBuilder = new Builder(this.context);
        alertDialogBuilder.setTitle("Network connection error").setMessage("Make sure you are connected to a wifi or mobile network data").setCancelable(false).setPositiveButton("Setting", new C04941()).setNegativeButton("Try Again", new C04952());
        alertDialogBuilder.create().show();
    }

    private void CategoryCall() {
        Map<String, String> params = new HashMap();
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        VolleyHelper jsObjRequest = new VolleyHelper(1, "http://www.mptechnolabs.com/1reward/getCategories.php", new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SplashActivity.this.CategoryCallAction(getInstance(SplashActivity.this.context).CategoryData(response));
                Log.e("categoryResponse",response+"");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SplashActivity.this.showAlertDialog();

            }
        });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(ApiHelper.MY_SOCKET_TIMEOUT_MS, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);
    }

    protected void CategoryCallAction(CategoryData categoryData) {
        int i = 0;
        while (i < categoryData.getCategoryItems().size()) {
            try {

                AppPrefs.getAppPrefs(this.context).setSubCategory(categoryData.getCategoryItems().get(i).getId(), null);
                i++;
            } catch (Exception ex) {
                Log.e("Vegie's", ex.getMessage());
                return;
            }
        }
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(categoryData);
        prefsEditor.putString("MyObject", json);
        prefsEditor.apply();

        showStartActivity(categoryData);
    }

    public void showStartActivity(CategoryData categoryData) {
        if ((AppPrefs.getAppPrefs(this.context).getIsLogin()) == true) {



            Intent intent = new Intent(SplashActivity.this, StartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryData);
            intent.putExtras(bundle);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
            startActivity(intent);
            finish();
        }else if((AppPrefs.getAppPrefs(this.context).getIsMerchantLogin()) == true)
        {
            Intent intent = new Intent(SplashActivity.this, DrawerActivity.class);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

            startActivity(intent);
            finish();
        }

        else {
            Intent intent = new Intent(SplashActivity.this, Activity_selection.class);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

            startActivity(intent);
            finish();
        }

    }

}
