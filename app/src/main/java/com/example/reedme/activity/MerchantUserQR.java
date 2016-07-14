package com.example.reedme.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.model.UserDetail;
import com.example.reedme.model.UserDetailList;

import java.util.List;

/**
 * Created by jolly on 30/6/16.
 */
public class MerchantUserQR extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    List<UserDetail> userList;
    UserDetail user;

    UserDetailList userdetail;
    TextView txt_name,txt_email,txt_wallet,txt_mobile,txt_address;
    Button iv_generateBill;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        userdetail = (UserDetailList) getIntent().getExtras().getSerializable("UserDetail");

        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_mobile = (TextView) findViewById(R.id.txt_mobile);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_address = (TextView) findViewById(R.id.txt_address);
        txt_wallet = (TextView) findViewById(R.id.txt_wallet);
        iv_generateBill = (Button) findViewById(R.id.iv_generateBill);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Profile");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        setData();

        iv_generateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MerchantUserQR.this,activity_merchant_continue.class));
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                finish();

            }
        });




    }

    private void setData() {

        userList = userdetail.getCategoryItems();
        user = userList.get(0);
        txt_email.setText(user.getEmail());
        txt_name.setText(String.format("%s %s", user.getFirstname(), user.getLastname()));
        txt_address.setText(user.getAddress());
        txt_wallet.setText(String.format("Wallet : %s", user.getWallet_point()));
        txt_mobile.setText(user.getPhone());

        AppPrefs.getAppPrefs(MerchantUserQR.this).setIsQR(true);

        AppPrefs.getAppPrefs(MerchantUserQR.this).setString(MerchantUserQR.this.getResources().getString(R.string.user_id), user.getUser_id());

        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("qr_number",user.getQr_number());
        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("email", user.getEmail());
        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("firstname",user.getFirstname());
        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("lastname",user.getLastname());
        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("address",user.getAddress());
        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("phone",user.getPhone());
        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("state",user.getState());
        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("city",user.getCity());
        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("pincode",user.getPincode());
        AppPrefs.getAppPrefs(MerchantUserQR.this).setString("Wallet",user.getWallet_point());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:


                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
