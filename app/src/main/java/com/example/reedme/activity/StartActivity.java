package com.example.reedme.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.dataprovider.GCMRegistration;
import com.example.reedme.fragments.CategoryItemActivity;
import com.example.reedme.fragments.HomeActivity;
import com.example.reedme.fragments.PrimaryFragment;
import com.example.reedme.fragments.fragement_map;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Util;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.tabbarview.TabBarView;

import static com.example.reedme.fragments.HomeActivity.newInstance;


public class StartActivity extends AppCompatActivity {

    private String TAG = "StartActivity";

    public static CategoryData categoryDate;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TabBarView mTabBarView;
    public static TextView txtCheckOutRupee;
    public static TextView txtItemCount;
    public static Context context;
    boolean isBack = false;
    private MainScreenPagerAdapter mMainScreenPagerAdapter;
    private ViewPager mViewPager;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FloatingActionButton floting;
    private static StartActivity startActivity;
    TextView username,email;
    public static FragmentManager fragmentManager;

    //Data
    private int PAGE_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        context = this;
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);
        categoryDate = (CategoryData) getIntent().getExtras().getSerializable("CategoryData");
        GCMRegistration.getInstance(context).Init();

        txtCheckOutRupee = (TextView) findViewById(R.id.txt_total_rupee);
        txtItemCount = (TextView) findViewById(R.id.widget_title_icon2);

        SetCheckOutValue();
        setUpCustomTabs();
        setPagerListener();
        setUpNavigationDrawer();
        mFragmentManager = getSupportFragmentManager();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    private void setUpCustomTabs() {
        LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View customTabView = mLayoutInflater.inflate(R.layout.custom_tab_view, null);
        mTabBarView = (TabBarView) customTabView.findViewById(R.id.customTabBar);
        mTabBarView.setStripHeight(7);
        mTabBarView.setStripColor(getResources().getColor(R.color.window_background));

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setCustomView(customTabView);

        mMainScreenPagerAdapter = new MainScreenPagerAdapter(getSupportFragmentManager());
        floting = (FloatingActionButton) findViewById(R.id.pink_icon);
        floting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent =new Intent(StartActivity.this, SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CategoryData", categoryDate);
                intent.putExtras(bundle);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                startActivity(intent);
                finish();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mMainScreenPagerAdapter);
        mTabBarView.setViewPager(mViewPager);

    }

    private void setUpNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        mNavigationView.addHeaderView(header);
        username= (TextView) header.findViewById(R.id.username);
        email = (TextView)header.findViewById(R.id.email);

        if(AppPrefs.getAppPrefs(context).getString("firstname") != null) {
            username.setText(AppPrefs.getAppPrefs(context).getString("firstname") + " " + AppPrefs.getAppPrefs(context).getString("lastname"));
        }
        if(AppPrefs.getAppPrefs(context).getString("email") != null) {
            email.setText(AppPrefs.getAppPrefs(context).getString("email"));
        }


        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d(TAG, "Drawer Opened");

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d(TAG, "Drawer Closed");
            }
        };


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                menuItem.setChecked(false);
                StartActivity.this.navigateTo(menuItem.getItemId());

                return true;
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
    private void navigateTo(int viewId) {
        this.isBack = false;
        Intent intent;
        switch (viewId) {

            case R.id.mystore:
                intent = new Intent(this, activity_mystore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.reedme:
                intent = new Intent(this, ReddMeWalletActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.logout:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StartActivity.context);
                alertDialogBuilder.setMessage(R.string.logout_message).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AppPrefs.getAppPrefs(StartActivity.context).setIsLogin(false);
                        AppPrefs.getAppPrefs(StartActivity.context).clearAllData();


                        Intent i =new Intent(StartActivity.context, Activity_login.class);
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
            case R.id.history:
                intent = new Intent(this, MyOrdersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CategoryData", categoryDate);
                intent.putExtras(bundle);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                startActivity(intent);
                finish();
                break;
            case R.id.profile:
                intent = new Intent(this, FragmentMyAccount.class);
                startActivity(intent);
                finish();
                break;
            case R.id.contactus:
                break;





            /*case R.string.id_my_orders *//*2131230796*//*:
                this.intent = new Intent(this, MyOrdersActivity.class);
                startActivity(this.intent);
                break;
            case R.string.id_rate *//*2131230798*//*:
                AppRater.appRater(context);
                break;
            case R.string.id_faq *//*2131230799*//*:
                this.intent = new Intent(this, FAQActivity.class);
                startActivity(this.intent);
                break;
            case R.string.id_contact_us *//*2131230800*//*:
                this.intent = new Intent(this, ContactUsActivity.class);
                startActivity(this.intent);
                break;
            case R.string.id_share *//*2131230801*//*:
                DialogHelper.getInstance(context).AppShare();
                break;
       */ }
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static StartActivity getInstance() {
        if (startActivity == null) {
            startActivity = new StartActivity();
        }
        return startActivity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                mDrawerLayout.openDrawer(GravityCompat.END);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setPagerListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTabBarView.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "Page: " + position);
                mTabBarView.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mTabBarView.onPageScrollStateChanged(state);
            }
        });
    }

    public class MainScreenPagerAdapter extends FragmentPagerAdapter implements TabBarView.IconTabProvider {

        private int[] tab_icons = {R.mipmap.ic_qr_grey,

                R.mipmap.category_grey,R.mipmap.directtion_grey,
                R.mipmap.ic_qr_white,
                R.mipmap.category_white,R.mipmap.direction_white};

        public MainScreenPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return new PrimaryFragment();
                case 1:
                    new HomeActivity();
                    return newInstance(categoryDate);
                case 2:
                    return new fragement_map();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public int getPageIconResId(int position) {
            return tab_icons[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "tab1";
                case 1:
                    return "tab2";
                case 2:
                    return "tab2";

            }
            return null;
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
          bundle.putSerializable("CategoryData", categoryDate);
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;


        } else {
            if (this.isBack) {
                finish();
                return true;
            }
            this.isBack = true;

            Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show();

            return true;
        }
    }
    public void BackFromCategoryItemActivity() {
        Fragment fragment = fragmentManager.findFragmentByTag(fragement_map.TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.inner_to_left, R.anim.left_to_out).remove(fragment).commitAllowingStateLoss();
        }

    }


}