package com.example.reedme.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.adapter.CategoryListAdapter1;
import com.example.reedme.adapter.FavStoreAdapter;
import com.example.reedme.adapter.category_fav_store;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MaterialFavoriteButton;
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
 * Created by Kirtan on 5/1/2016.
 */
public class activity_mystore extends AppCompatActivity {

     static String  id;

    GridView gridLayout;
    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    static CategoryData categoryData;
    public static Context context;
    public static TextView txtCheckOutRupee;
    public static TextView txtItemCount;
    AVLoadingIndicatorView progress;

    static CategoryData1 categoryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);
        setContentView(R.layout.activity_fav_store);

        context = this;
        id = AppPrefs.getAppPrefs(context).getString("user_id");

        mJsonParser = new MyJSONParser();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("MyObject", "");
        categoryData = gson.fromJson(json, CategoryData.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("My Store");

        txtCheckOutRupee = (TextView) findViewById(R.id.txt_total_rupee);
        txtItemCount = (TextView) findViewById(R.id.widget_title_icon2);
        gridLayout = (GridView) findViewById(R.id.list_grid);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);

        SetCheckOutValue();

        new JSONParse().execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(activity_mystore.this, StartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryData);
            intent.putExtras(bundle);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public class JSONParse extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);

            // Utills.showDialog(context);
        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();


                params.put("user_id", id);
                params.put("fav_flag", "1");

                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/userStore.php", params);
                Log.e("Response ------>", jsonObject_parent + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Utills.dismissDialog();
            progress.setVisibility(View.GONE);

            CategoryCallAction(ParseDataProvider.getInstance(context).fav_store(jsonObject_parent));

        }
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
            Utills.showCustomSimpleDialog(activity_mystore.this, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {
                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "You don't have any favrouit store", false);

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

    public void showStartActivity(CategoryData1 categoryData) {

        this.gridLayout.setAdapter(new category_fav_store(activity_mystore.this, categoryData.getCategoryItems()));
        //this.gridLayout.setOnItemClickListener(this);
        categoryDate = categoryData;


    }
}
