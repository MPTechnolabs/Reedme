package com.example.reedme.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.adapter.CategoryListAdapter1;
import com.example.reedme.adapter.ReedMeWalletAdapter;
import com.example.reedme.adapter.category_fav_store;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Util;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CategoryData1;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.views.AVLoadingIndicatorView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Android on 5/19/2016.
 */
public class ReddMeWalletActivity extends AppCompatActivity {


    ListView gridLayout;
    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    static CategoryData categoryData;
    public static Context context;
    public static TextView txtCheckOutRupee;
    public static TextView txtItemCount;
    AVLoadingIndicatorView progress;
    TextView txt_message;

    static CategoryData1 categoryDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reedmelist);

        context = this;

        mJsonParser = new MyJSONParser();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("MyObject", "");
        categoryData = gson.fromJson(json, CategoryData.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("My Wallet");

        txtCheckOutRupee = (TextView) findViewById(R.id.txt_total_rupee);
        txtItemCount = (TextView) findViewById(R.id.widget_title_icon2);

        gridLayout = (ListView) findViewById(R.id.list_category_item);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
        txt_message = (TextView) findViewById(R.id.txt_message);

        SetCheckOutValue();

        new JSONParse().execute();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ReddMeWalletActivity.this, StartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryData);
            intent.putExtras(bundle);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    private class JSONParse extends AsyncTask<String,String,String> {

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

                params.put("user_id",AppPrefs.getAppPrefs(StartActivity.context).getString(ReddMeWalletActivity.this.getResources().getString(R.string.user_id)));

                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/getStorInfo.php", params);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           // Utills.dismissDialog();
            progress.setVisibility(View.GONE);

            CategoryCallAction(ParseDataProvider.getInstance(context).CategoryData1(jsonObject_parent));

        }
    }

    public void CheckOut(View view) {
        if (AppPrefs.getAppPrefs(context).getCheckOutVantage() == null) {
            Toast.makeText(context, "Your cart is empty", Toast.LENGTH_LONG).show();
        } else if (AppPrefs.getAppPrefs(context).getCheckOutVantage().CheckOutVantageList.size() == 0) {
            Toast.makeText(context, "Your cart is empty", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, CheckoutContinueActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryData);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
    public void SetCheckOutValue() {
        CheckOutData checkOutData = AppPrefs.getAppPrefs(context).getCheckOutVantage();
        if (checkOutData != null) {
            txtCheckOutRupee.setText(String.valueOf(Util.getInstance(context).getCheckOutTotalAmount(checkOutData)));
            txtItemCount.setText(String.valueOf(Util.getInstance(context).getCheckOutVantageCount(checkOutData)));

            return;
        }
        txtCheckOutRupee.setText("0");
        txtItemCount.setText("0");
    }

    protected void CategoryCallAction(CategoryData1 categoryData) {


        if(categoryData != null) {
            int i = 0;
            while (i < categoryData.getCategoryItems().size()) {
                try {

                    AppPrefs.getAppPrefs(this.context).setSubCategory1(categoryData.getCategoryItems().get(i).getId(), null);
                    i++;
                } catch (Exception ex) {
                    Log.e("Vegie's", ex.getMessage());
                    return;
                }
            }
            showStartActivity(categoryData);

        }
        else
        {
            gridLayout.setVisibility(View.GONE);
            txt_message.setVisibility(View.VISIBLE);
        }

    }

    public void showStartActivity(CategoryData1 categoryData) {
        this.gridLayout.setAdapter(new ReedMeWalletAdapter(ReddMeWalletActivity.this, categoryData.getCategoryItems()));
        categoryDate = categoryData;

    }
}
