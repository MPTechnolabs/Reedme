package com.example.reedme.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.adapter.CheckoutItemAdapter;
import com.example.reedme.fragments.CategoryItemActivity;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Helper;
import com.example.reedme.helper.Util;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CheckOutData;

public class CheckoutContinueActivity extends AppCompatActivity {
    public static final String TAG;
    public static ListView checkoutList;
    static Context context;
    static float totalAmount;
    static TextView txtPaybleValue;
    static TextView txtShipping;
    CategoryData categoryData;
    String address;


    class C04693 implements DialogInterface.OnClickListener {
        C04693() {
        }

        public void onClick(DialogInterface dialog, int id) {
            AppPrefs.getAppPrefs(CheckoutContinueActivity.context).setCheckOutVantage(null);
            StartActivity.getInstance().SetCheckOutValue();

            CheckoutContinueActivity.this.finish();
            Intent intent =new Intent(CheckoutContinueActivity.this, StartActivity.class);
            CheckoutContinueActivity.this.overridePendingTransition(R.anim.inner_to_left, R.anim.left_to_out);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryData);
            intent.putExtras(bundle);
            startActivity(intent);
            dialog.cancel();
        }
    }

    /* renamed from: com.vegies.app.CheckoutContinueActivity.4 */
    class C04704 implements DialogInterface.OnClickListener {
        C04704() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
        }
    }

    static {
        TAG = CheckoutContinueActivity.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout_continue);
        categoryData = (CategoryData) getIntent().getExtras().getSerializable("CategoryData");
        address = AppPrefs.getAppPrefs(CheckoutContinueActivity.this).getString("address");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("CHECKOUT");
        try {
            context = this;
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);
            LoadContext();
            LoatData();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        address = AppPrefs.getAppPrefs(CheckoutContinueActivity.this).getString("address");

    }

    public void LoadContext() {
        txtPaybleValue = (TextView) findViewById(R.id.txt_payable_value);
        txtShipping = (TextView) findViewById(R.id.txt_checkout_shipping_value);
    }

    public void LoatData()
    {
        CheckOutData checkOutData = AppPrefs.getAppPrefs(context).getCheckOutVantage();
        setCheckOutPayableValue(checkOutData);
        checkoutList = (ListView) findViewById(R.id.lst_checkout_continue_item);
        checkoutList.setAdapter(new CheckoutItemAdapter(checkOutData.CheckOutVantageList));
    }

    public static void setCheckOutPayableData() {
        setCheckOutPayableValue(AppPrefs.getAppPrefs(context).getCheckOutVantage());
    }

    private static void setCheckOutPayableValue(CheckOutData checkOutData) {
        float totalAmount = Util.getInstance(context).getCheckOutTotalAmount(checkOutData);
        //float shippingValue = Util.getInstance(context).getShippingAmount(totalAmount);
        txtPaybleValue.setText(String.valueOf( totalAmount));
        //txtShipping.setText(String.valueOf(shippingValue));
    }

    public void ContinueCheckOut(View view) {

        if(address == null || address.equals(""))
        {
            Intent i= new Intent(CheckoutContinueActivity.this,Activity_add_address.class);
            startActivity(i);
            //finish();
        }
        else {
            Intent intent = new Intent(CheckoutContinueActivity.this, CheckoutProceedActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryData);
            intent.putExtras(bundle);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
            startActivity(intent);
            finish();
        }
    }

    public void ClearCart(View view) {
        emptyCartDialog();
    }

    public void emptyCartDialog() {
        Builder alertDialogBuilder = new Builder(context);
        alertDialogBuilder.setMessage(R.string.empty_cart_message).setCancelable(false).setPositiveButton("Yes", new C04693()).setNegativeButton("No, thanks", new C04704());
        alertDialogBuilder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.add_product, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, StartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CategoryData", categoryData);
                intent.putExtras(bundle);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.inner_to_left, R.anim.left_to_out);
    }
}
