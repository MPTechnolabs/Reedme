package com.example.reedme.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Helper;
import com.example.reedme.model.Address;
import com.example.reedme.R;
import com.example.reedme.model.CategoryData;

public class CheckoutProceedActivity extends AppCompatActivity {
    public static final String TAG;
    Context context;
    boolean isClickable;
    TextView txtCity;
    TextView txtHome;
    TextView txtMobile;
    TextView txtStreet;
    CategoryData categoryData;
    TextView userWallet;
    EditText edt_wallet;
    CheckBox chk_select;
    TextView txt_wallet;


    public CheckoutProceedActivity() {
        this.isClickable = true;
    }

    static {
        TAG = CheckoutProceedActivity.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout_proceed);
        categoryData = (CategoryData) getIntent().getExtras().getSerializable("CategoryData");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("CHECKOUT");
        try {
            this.context = this;
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);
            //setActionBar();
            LoadContext();
            LoatData();
        }catch (Exception e)
        {
            Log.e("Exception",e+"");
        }
    }

    public void LoadContext() {
        this.txtHome = (TextView) findViewById(R.id.txt_checkout_address_home);
        this.txtStreet = (TextView) findViewById(R.id.txt_checkout_address_street);
        this.txtCity = (TextView) findViewById(R.id.txt_checkout_address_city);
        this.txtMobile = (TextView) findViewById(R.id.txt_checkout_contact_number);
        this.txt_wallet = (TextView) findViewById(R.id.userWallet);
        this.chk_select = (CheckBox) findViewById(R.id.chkSelectWallet);
        this.edt_wallet = (EditText) findViewById(R.id.enterWallet);


    }

    public void LoatData() {
    this.txtHome.setText(AppPrefs.getAppPrefs(context).getString("firstname") +" "+AppPrefs.getAppPrefs(context).getString("lastname"));
            this.txtStreet.setText(AppPrefs.getAppPrefs(context).getString("address")+",");
            this.txtCity.setText(AppPrefs.getAppPrefs(context).getString("city")+","+AppPrefs.getAppPrefs(context).getString("state"));
            this.txtMobile.setText(AppPrefs.getAppPrefs(context).getString("phone"));
            txt_wallet.setText("Your Wallet : "+ AppPrefs.getAppPrefs(StartActivity.context).getString("Wallet"));
            edt_wallet.setText(AppPrefs.getAppPrefs(StartActivity.context).getString("Wallet"));

            chk_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked)
                    {
                        edt_wallet.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        edt_wallet.setVisibility(View.GONE);
                    }
                }
            });

    }


    public void ProceedCheckOut(View view) {
        this.isClickable = false;

        float yourwallet  = Float.parseFloat(AppPrefs.getAppPrefs(StartActivity.context).getString("Wallet"));
        float enterwallet = Float.parseFloat((edt_wallet.getText().toString()));

        if(yourwallet < enterwallet) {
            edt_wallet.setError("Plese Enter Valid Amount");
        }

         else if (enterwallet == 0) {
                Intent intent = new Intent(this, CheckoutPlaceOrderActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("CategoryData", categoryData);
                bundle.putFloat("Wallet", 0);

                intent.putExtras(bundle);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, CheckoutPlaceOrderActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("CategoryData", categoryData);
                bundle.putFloat("Wallet", enterwallet);

                intent.putExtras(bundle);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                startActivity(intent);
                finish();

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, CheckoutContinueActivity.class);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        Intent intent = new Intent(this, CheckoutContinueActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("CategoryData", categoryData);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

        startActivity(intent);
        finish();
        // startActivity(new Intent(this, CheckoutContinueActivity.class));
       // finish();
        return true;
    }
}
