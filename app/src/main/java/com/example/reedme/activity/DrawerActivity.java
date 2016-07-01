package com.example.reedme.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.reedme.R;
import com.example.reedme.fragments.Fragement_Scanner;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.Utills;


/**
 * Created by ubuntu on 10/6/16.
 */
public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int mSelectedId;
    private static final int PERMISSION_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);

        setContentView(R.layout.activity_drawer);

            if (ContextCompat.checkSelfPermission(DrawerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(DrawerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(DrawerActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DrawerActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }


        setToolbar();
        initView();

        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //default it set first item as selected
        mSelectedId = savedInstanceState == null ? R.id.navigation_item_1 : savedInstanceState.getInt("SELECTED_ID");
        itemSelection(mSelectedId);


    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void initView() {
        mDrawer = (NavigationView) findViewById(R.id.main_drawer);
        mDrawer.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void itemSelection(int mSelectedId) {


        switch (mSelectedId) {

            case R.id.navigation_item_1:


                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, new Fragement_Scanner()).commit();



                mDrawerLayout.closeDrawer(GravityCompat.START);

                break;

            case R.id.navigation_item_2:

                startActivity(new Intent(this, FriendsActivity.class));
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.navigation_item_3:

                startActivity(new Intent(this,Add_product.class));
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.navigation_item_4:

                startActivity(new Intent(this,activity_merchant_continue.class));
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.navigation_sub_item_1:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.navigation_sub_item_2:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.logout:
                mDrawerLayout.closeDrawer(GravityCompat.START);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DrawerActivity.this);
                alertDialogBuilder.setMessage(R.string.logout_message).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AppPrefs.getAppPrefs(DrawerActivity.this).setIsMerchantLogin(false);
                        AppPrefs.getAppPrefs(DrawerActivity.this).clearAllData();


                        Intent i = new Intent(DrawerActivity.this, activity_merchant_login.class);
                        startActivity(i);
                        finish();
                        dialog.cancel();


                    }
                }).setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialogBuilder.create().show();
                break;
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();
        itemSelection(mSelectedId);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //save selected item so it will remains same even after orientation change
        outState.putInt("SELECTED_ID", mSelectedId);
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(DrawerActivity.this, Manifest.permission.CAMERA)) {
            Utills.showCustomSimpleDialog(DrawerActivity.this, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {
                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "Please Turn On Phone Permission in Setting", false);
        } else {
            ActivityCompat.requestPermissions(DrawerActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {



            }
        }


    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(DrawerActivity.this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}