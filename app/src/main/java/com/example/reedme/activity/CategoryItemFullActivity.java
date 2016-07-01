package com.example.reedme.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.adapter.CategoryItemAdapter;
import com.example.reedme.adapter.VantageListAdapter;
import com.example.reedme.fragments.CategoryItemActivity;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.Helper;
import com.example.reedme.helper.Util;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.model.CheckOutVantage;
import com.example.reedme.model.Product;
import com.example.reedme.model.Vantage;
import com.google.gson.Gson;

public class CategoryItemFullActivity extends AppCompatActivity {
    public static final String TAG;
    int chkVItemCount;
    CheckOutVantage chkVantage;
    Context context;
    TextView itemCount;
    TextView itemName;
    TextView itemPrice;
    TextView itemQuntity;
    RelativeLayout lytMinus;
    RelativeLayout lytPlus;
    Product product;
    TextView txtCheckOutItemCount;
    TextView txtCheckOutRupee;
    Vantage vantage;
    ViewPager viewPager;
    CategoryData categoryDate;


    class C04653 implements OnClickListener {
        C04653() {
        }

        public void onClick(View v) {
            if (CategoryItemFullActivity.this.chkVItemCount > 0) {
                CategoryItemFullActivity categoryItemFullActivity = CategoryItemFullActivity.this;
                categoryItemFullActivity.chkVItemCount--;
                CategoryItemFullActivity.this.itemCount.setText(String.valueOf(CategoryItemFullActivity.this.chkVItemCount));
                Util.getInstance(StartActivity.context).RemoveCheckOutVantage(CategoryItemFullActivity.this.vantage.getVantageId());
                CategoryItemFullActivity.this.SetCheckOutValue();

            }
        }
    }

    class C04664 implements OnClickListener {
        C04664() {
        }

        public void onClick(View v) {
                CategoryItemFullActivity categoryItemFullActivity = CategoryItemFullActivity.this;
                categoryItemFullActivity.chkVItemCount++;
                CategoryItemFullActivity.this.itemCount.setText(String.valueOf(CategoryItemFullActivity.this.chkVItemCount));
                Util.getInstance(StartActivity.context).AddCheckOutVantage(CategoryItemFullActivity.this.vantage, CategoryItemFullActivity.this.product.getProductName());
                CategoryItemFullActivity.this.SetCheckOutValue();



                AppPrefs.getAppPrefs(CategoryItemFullActivity.this).setString("storename", CategoryItemFullActivity.this.product.getStorename());




        }
    }

    class C08125 implements OnPageChangeListener {
        C08125() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            CategoryItemFullActivity.this.chkVItemCount = 0;
            CategoryItemFullActivity.this.vantage = (Vantage) CategoryItemFullActivity.this.product.getVantages().get(position);
            CategoryItemFullActivity.this.chkVantage = Util.getInstance(StartActivity.context).getCheckoutAddedVantage(CategoryItemFullActivity.this.vantage.getVantageId());
            if (CategoryItemFullActivity.this.chkVantage != null) {
                Log.d(CategoryItemFullActivity.TAG, "Vantage Id = " + CategoryItemFullActivity.this.vantage.getVantageId() + "/ qty = " + CategoryItemFullActivity.this.chkVantage.getVantageQty());
                CategoryItemFullActivity.this.chkVItemCount = CategoryItemFullActivity.this.chkVantage.getVantageQty();
            }
           // if (Integer.parseInt(CategoryItemFullActivity.this.vantage.gettVantageQty()) > 0) {
                CategoryItemFullActivity.this.itemCount.setTextColor(StartActivity.context.getResources().getColor(R.color.black));
                CategoryItemFullActivity.this.lytMinus.setVisibility(View.VISIBLE);
                CategoryItemFullActivity.this.lytPlus.setVisibility(View.VISIBLE);
            /*} else {
                CategoryItemFullActivity.this.lytPlus.setVisibility(View.GONE);
                CategoryItemFullActivity.this.lytMinus.setVisibility(View.GONE);
                CategoryItemFullActivity.this.itemCount.setText("Out Of Stock");
                CategoryItemFullActivity.this.itemCount.setTextColor(StartActivity.context.getResources().getColor(R.color.primaryColor));
            }*/
            CategoryItemFullActivity.this.itemName.setText(CategoryItemFullActivity.this.product.getProductName());
            CategoryItemFullActivity.this.itemQuntity.setText(CategoryItemFullActivity.this.vantage.getVantageSize());
            CategoryItemFullActivity.this.itemPrice.setText(CategoryItemFullActivity.this.vantage.getVantagePrice());
           // CategoryItemFullActivity.this.itemOldPrice.setText(CategoryItemFullActivity.this.vantage.getVantageOldPrice());
           // CategoryItemFullActivity.this.itemOldPrice.setPaintFlags(CategoryItemFullActivity.this.itemOldPrice.getPaintFlags() | 16);
        }
        public void onPageScrollStateChanged(int state) {
        }
    }

    static {
        TAG = CategoryItemFullActivity.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);

        setContentView(R.layout.activity_category_item_full);
        try {
            this.context = this;

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Product");

            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString("MyObject", "");
             categoryDate = gson.fromJson(json, CategoryData.class);


            this.product = (Product) getIntent().getExtras().getSerializable("Product");
            this.txtCheckOutRupee = (TextView) findViewById(R.id.txt_total_rupee);
            this.txtCheckOutItemCount = (TextView) findViewById(R.id.widget_title_icon2);

            SetCheckOutValue();
            loatData();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    private void loatData() {
        this.viewPager = (ViewPager) findViewById(R.id.vp_vantage_item);
        this.itemName = (TextView) findViewById(R.id.txt_category_sub_item_full_name);
        this.itemQuntity = (TextView) findViewById(R.id.txt_category_sub_item_full_quantity);
        this.itemPrice = (TextView) findViewById(R.id.txt_category_sub_item_full_price);
       // this.itemOldPrice = (TextView) findViewById(R.id.txt_category_sub_item_full_old_price);
        this.itemCount = (TextView) findViewById(R.id.txt_category_sub_item_full_add_count);
        this.lytMinus = (RelativeLayout) findViewById(R.id.lyt_category_sub_item_full_minus);
        this.lytPlus = (RelativeLayout) findViewById(R.id.lyt_category_sub_item_full_plus);
        this.vantage = (Vantage) this.product.getVantages().get(0);
        this.chkVantage = Util.getInstance(CategoryItemFullActivity.this).getCheckoutAddedVantage(this.vantage.getVantageId());
        if (this.chkVantage != null) {
            this.chkVItemCount = this.chkVantage.getVantageQty();
        }
        this.itemName.setText(this.product.getProductName());


        this.itemQuntity.setText(this.vantage.getVantageSize());
        this.itemPrice.setText(this.vantage.getVantagePrice());

        //this.itemOldPrice.setText(this.vantage.getVantageOldPrice());
        this.itemCount.setText(String.valueOf(this.chkVItemCount));
        //this.itemOldPrice.setPaintFlags(this.itemOldPrice.getPaintFlags() | 16);


        if(Constants.flag == 0)
        {
            this.itemCount.setVisibility(View.GONE);
            this.lytMinus.setVisibility(View.GONE);
            this.lytPlus.setVisibility(View.GONE);
        }
        else
        {
            this.itemCount.setTextColor(StartActivity.context.getResources().getColor(R.color.black));
            this.lytMinus.setVisibility(View.VISIBLE);
            this.lytPlus.setVisibility(View.VISIBLE);
        }
            this.lytMinus.setOnClickListener(new C04653());
            this.lytPlus.setOnClickListener(new C04664());

        this.viewPager.setAdapter(new VantageListAdapter(this.product.getVantages()));
        this.viewPager.addOnPageChangeListener(new C08125());
    }

    public void CheckOut(View view) {

        /*if (!AppPrefs.getAppPrefs(context).getIsLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
        }  else*/ if (AppPrefs.getAppPrefs(context).getCheckOutVantage() == null) {
            Toast.makeText(context, "Your cart is empty", Toast.LENGTH_LONG).show();
        } else if (AppPrefs.getAppPrefs(context).getCheckOutVantage().CheckOutVantageList.size() == 0) {
            Toast.makeText(context, "Your cart is empty", Toast.LENGTH_LONG).show();
        } else {


            Intent intent = new Intent(this, CheckoutContinueActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryDate);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
    public void SetCheckOutValue() {
        CheckOutData checkOutData = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        if (checkOutData != null) {
            this.txtCheckOutRupee.setText(String.valueOf(Util.getInstance(this.context).getCheckOutTotalAmount(checkOutData)));
            this.txtCheckOutItemCount.setText(String.valueOf(Util.getInstance(this.context).getCheckOutVantageCount(checkOutData)));
            return;
        }
        txtCheckOutRupee.setText("0");
        txtCheckOutItemCount.setText("0");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        GoBack();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
/*
                ((CategoryItemAdapter) CategoryItemActivity.subCategoryList.getAdapter()).notifyDataSetChanged();
*/
                finish();
                overridePendingTransition(R.anim.inner_to_left, R.anim.left_to_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void GoBack() {
/*
        ((CategoryItemAdapter) CategoryItemActivity.subCategoryList.getAdapter()).notifyDataSetChanged();
*/
        finish();
        overridePendingTransition(R.anim.inner_to_left, R.anim.left_to_out);
    }
}
