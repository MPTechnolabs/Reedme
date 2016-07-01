package com.example.reedme.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.example.reedme.R;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.model.CategoryData;
import com.google.gson.Gson;

/**
 * Created by ubuntu on 16/6/16.
 */
public class Activity_selection extends AppCompatActivity {

    CategoryData categoryDate;
    RadioButton rb_user,rb_merchant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selection);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();

        String json = appSharedPrefs.getString("MyObject", "");
        categoryDate = gson.fromJson(json, CategoryData.class);

        findById();
        setAction();
    }


    private void findById() {


            rb_user= (RadioButton) findViewById(R.id.radio_button_one);
            rb_merchant= (RadioButton) findViewById(R.id.radio_button_two);

    }

    private void setAction()
    {
        rb_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((AppPrefs.getAppPrefs(Activity_selection.this).getIsLogin())) {
                    Intent i = new Intent(Activity_selection.this, StartActivity.class);
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CategoryData", categoryDate);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();

                    }
                    else
                    {

                        Intent intent = new Intent(Activity_selection.this, Activity_login.class);
                        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                        startActivity(intent);
                        finish();
                     }

            }
        });

        rb_merchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((AppPrefs.getAppPrefs(Activity_selection.this).getIsMerchantLogin())) {
                    Intent i = new Intent(Activity_selection.this, DrawerActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                    finish();

                }
                else {
                    Intent intent = new Intent(Activity_selection.this, activity_merchant_login.class);
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}
