package com.example.reedme.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.adapter.CategoryListAdapter;
import com.example.reedme.adapter.CategoryListAdapter1;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.fragments.CategoryItemActivity;
import com.example.reedme.fragments.HomeActivity;
import com.example.reedme.helper.ApiHelper;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Util;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CategoryData1;
import com.example.reedme.model.CategoryItem;
import com.example.reedme.model.CategoryItem1;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.views.AVLoadingIndicatorView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.reedme.dataprovider.ParseDataProvider.getInstance;

/**
 * Created by Jolly Raiyani on 4/12/2016.
 */
public class StoreDiaplyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {


    static CategoryData categoryData;
    GridView categoryGridView;
    FragmentManager manager;
    public static TextView txtCheckOutRupee;
    public static TextView txtItemCount;
    public static StoreDiaplyActivity startActivity;
    public static Context context;
    String cat_id,name;
    static CategoryData1 categoryDate;
    AVLoadingIndicatorView progress;

    TextView txt_message;
    private Boolean proStatus = false;
    public static int pageNo = 0;
    CategoryListAdapter1 adapter;
    private String lastCategoryName = "";
    private LinearLayout ll_filter;

    RelativeLayout rel_filter, rel_sort;

    boolean isBack = false;

    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);

        setContentView(R.layout.activity_home1);
        this.context=this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Stores");
        manager = getSupportFragmentManager();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("MyObject", "");
        categoryData = gson.fromJson(json, CategoryData.class);
        cat_id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        mJsonParser = new MyJSONParser();

        txtCheckOutRupee = (TextView) findViewById(R.id.txt_total_rupee);
        txtItemCount = (TextView) findViewById(R.id.widget_title_icon2);
        this.categoryGridView = (GridView) findViewById(R.id.grid_category_item);
        txt_message = (TextView) findViewById(R.id.txt_message);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);




//        lv_AllItems.setOnScrollListener(this);

        SetCheckOutValue();
        lastCategoryName = "";
        pageNo = 10;
        proStatus = true;

        new JSONParse().execute();

    }

    public static StoreDiaplyActivity getInstance() {
        if (startActivity == null) {
            startActivity = new StoreDiaplyActivity();
        }
        return startActivity;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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


                params.put("cat_id", cat_id);
                params.put("user_id",AppPrefs.getAppPrefs(StartActivity.context).getString(StoreDiaplyActivity.this.getResources().getString(R.string.user_id)));

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
            categoryGridView.setVisibility(View.GONE);
            txt_message.setVisibility(View.VISIBLE);
        }


    }

    public void showStartActivity(CategoryData1 categoryData) {

        adapter = new CategoryListAdapter1(StoreDiaplyActivity.this, categoryData.getCategoryItems());
        this.categoryGridView.setAdapter(adapter);
        this.categoryGridView.setOnItemClickListener(this);
        categoryDate = categoryData;

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                adapter.filter(searchQuery.toString().trim());
                categoryGridView.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               {
                   if (!CheckCurrentFragment()) {
                       return true;
                   }
                   else
                   {
                       /*Intent intent = new Intent(StoreDiaplyActivity.this, StartActivity.class);
                       Bundle bundle = new Bundle();
                       bundle.putSerializable("CategoryData", categoryData);
                       intent.putExtras(bundle);
                       overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                       startActivity(intent);*/
                       finish();

                   }
            }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)

    {
        String name =   AppPrefs.getAppPrefs(StartActivity.context).getString("storename");
        String clickname = (StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getName();

        if(Integer.parseInt(txtItemCount.getText().toString().trim()) == 0)
        {
            AppPrefs.getAppPrefs(StartActivity.context).setString("Discount",((StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getDiscount()));
            AppPrefs.getAppPrefs(StartActivity.context).setString("Wallet", ((StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getWalletPoint()));
            AppPrefs.getAppPrefs(StartActivity.context).setString("storename",clickname);
            AppPrefs.getAppPrefs(StartActivity.context).setString("StoreId",((StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getId()));
            AppPrefs.getAppPrefs(StartActivity.context).setString("cateId",cat_id);
            Constants.flag = 1;
        }
        else if(Integer.parseInt(txtItemCount.getText().toString().trim()) != 0 && !name.equals(clickname))
        {
            Constants.flag = 0;

            AppPrefs.getAppPrefs(StartActivity.context).setString("Wallet","0");
            AppPrefs.getAppPrefs(StartActivity.context).setString("Discount","0");


        }else
        {
            AppPrefs.getAppPrefs(StartActivity.context).setString("Discount",((StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getDiscount()));
            AppPrefs.getAppPrefs(StartActivity.context).setString("Wallet",((StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getWalletPoint()));
            AppPrefs.getAppPrefs(StartActivity.context).setString("StoreId",((StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getId()));
            AppPrefs.getAppPrefs(StartActivity.context).setString("storename",clickname);

            AppPrefs.getAppPrefs(StartActivity.context).setString("cateId",cat_id);

            Constants.flag = 1;
        }

        Intent i= new Intent(getApplicationContext(),CategoryItemActivity.class);
        i.putExtra("id", (StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getId());
        i.putExtra("name", (StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getName());
        startActivity(i);


/*
        manager.beginTransaction().setCustomAnimations(R.anim.right_to_left, R.anim.left_to_inner).add(R.id.main_content, CategoryItemActivity.newInstance(((CategoryItem1) StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getId(), ((CategoryItem1) StoreDiaplyActivity.categoryDate.getCategoryItems().get(position)).getName()), CategoryItemActivity.TAG).commit();
*/

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
            /*if(txtItemCount.getText().toString().equals("1"))
            {
                Constants.storepaurchase = 1;
            }
            else
            {
                Constants.storepaurchase = 0;
            }*/
            return;
        }
        txtCheckOutRupee.setText("0");
        txtItemCount.setText("0");
    }
    public void BackFromCategoryItemActivity() {
        Fragment fragment = manager.findFragmentByTag(CategoryItemActivity.TAG);
        if (fragment != null) {
            manager.beginTransaction().setCustomAnimations(R.anim.inner_to_left, R.anim.left_to_out).remove(fragment).commitAllowingStateLoss();
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        else if (!CheckCurrentFragment()) {
            return true;
        } else {
            if (this.isBack) {
                finish();
                return true;
            }
            this.isBack = true;
            return true;
        }
    }
    private boolean CheckCurrentFragment() {
        if (manager.findFragmentByTag(CategoryItemActivity.TAG) != null) {
            getSupportActionBar().setTitle("Stores");

            BackFromCategoryItemActivity();
            return false;
        }
        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle("Stores");
        SetCheckOutValue();



    }
}
