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
import com.example.reedme.fragments.fragement_map_places;
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
                    return new fragement_map_places();

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
            Toast.makeText(context, "Press again to exit", Toast.LENGTH_LONG).show();

            return true;
        }
    }
    public void BackFromCategoryItemActivity() {
        Fragment fragment = fragmentManager.findFragmentByTag(CategoryItemActivity.TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.inner_to_left, R.anim.left_to_out).remove(fragment).commitAllowingStateLoss();
        }

    }

}
   /* public static CategoryData categoryDate;
    public static Context context;
    public static FragmentManager fragmentManager;
    public static ActionBar mActionBar;
    private static ListView mDrawerList;
    public static int selectedItem;
    private static StartActivity startActivity;
    public static TextView txtCheckOutRupee;
    public static TextView txtItemCount;
    public static TextView userAddress;
    public static TextView userMobile;
    Intent intent;
    boolean isBack = false;
    private DrawerLayout mDrawerLayout;
    RelativeLayout mainMenu;
    private MenuItemListAdapter menuItemListAdapter;

    private class VegiesMenuItemClickListener implements OnItemClickListener {
        private VegiesMenuItemClickListener() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            StartActivity.selectedItem = position;
            StartActivity.this.menuItemListAdapter.notifyDataSetChanged();
            StartActivity.this.navigateTo(view.getId());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_start);
        context = this;
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);
        categoryDate = (CategoryData) getIntent().getExtras().getSerializable("CategoryData");
        GCMRegistration.getInstance(context).Init();
        moveDrawerToTop();
        initDrawer();
        Helper.getInstance(context).setCustomActionBar(getActionBar());
        setActionBarButtonListener();
        setLoginOrLogout();
        setAddressOnMenu();
        txtCheckOutRupee = (TextView) findViewById(R.id.txt_total_rupee);
        txtItemCount = (TextView) findViewById(R.id.widget_title_icon2);
        navigateTo(R.string.id_home);
        fragmentManager = getSupportFragmentManager();
    }

    public static StartActivity getInstance() {
        if (startActivity == null) {
            startActivity = new StartActivity();
        }
        return startActivity;
    }

    private void moveDrawerToTop() {
        DrawerLayout drawer;
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        if (Util.getInstance(context).getDeviceSize().x < 1000) {
            drawer = (DrawerLayout) inflater.inflate(R.layout.drawer, null);
        } else {
            drawer = (DrawerLayout) inflater.inflate(R.layout.drawer_xh, null);
        }
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        ((LinearLayout) drawer.findViewById(R.id.drawer_content)).addView(child, 0);
        drawer.findViewById(R.id.lyt_drawer).setPadding(0, Helper.getInstance(context).getStatusBarHeight(), 0, 0);
        decor.addView(drawer);
    }

    private void initDrawer() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.menuItemListAdapter = Helper.getInstance(context).getMenuListAdapter();
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        mDrawerList.setAdapter(this.menuItemListAdapter);
        mDrawerList.setOnItemClickListener(new VegiesMenuItemClickListener());
        userAddress = (TextView) findViewById(R.id.txt_drawer_city_address);
        userMobile = (TextView) findViewById(R.id.txt_drawer_user_name);
        userAddress.setTypeface(Helper.getInstance(context).getArlrdbdFont());
        userMobile.setTypeface(Helper.getInstance(context).getArlrdbdFont());
    }

    private void setActionBarButtonListener() {
        Helper.getInstance(context).getMainMenu().setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StartActivity.this.setMenuNavigation();
            }
        });
        Helper.getInstance(context).getSearchButton().setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StartActivity.this.startActivity(new Intent(StartActivity.this, SearchActivity.class));
            }
        });
    }

    private void setMenuNavigation() {

        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            this.mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }


    private void navigateTo(int viewId) {
        this.isBack = false;
        switch (viewId) {
            case R.string.id_login *//*2131230792*//*:
                this.intent = new Intent(this, LoginActivity.class);
                startActivity(this.intent);
                break;
            case R.string.id_logout *//*2131230793*//*:
                DialogHelper.getInstance(context).logoutDialog();
                break;
            case R.string.id_home *//*2131230794*//*:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, HomeActivity.newInstance(categoryDate), ApiHelper.homeFragment).commit();
                break;
            case R.string.id_address *//*2131230795*//*:
                this.intent = new Intent(this, AddressActivity.class);
                startActivity(this.intent);
                break;
            case R.string.id_my_orders *//*2131230796*//*:
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
        }
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void setAddressOnMenu() {
        if (AppPrefs.getAppPrefs(context).getIsLogin()) {
            Address address = AppPrefs.getAppPrefs(context).getAddress();
            if (address != null) {
                userAddress.setVisibility(View.VISIBLE);
                userAddress.setText(address.getAreaName());
            }
            userMobile.setVisibility(View.VISIBLE);
            userMobile.setText(AppPrefs.getAppPrefs(context).getString(context.getResources().getString(R.string.prefs_user_mobile)));
            return;
        }
        userAddress.setVisibility(View.VISIBLE);
        userMobile.setVisibility(View.VISIBLE);
    }

    public void setLoginOrLogout() {
        if (AppPrefs.getAppPrefs(context).getIsLogin()) {
            this.menuItemListAdapter = Helper.getInstance(context).getMenuListAdapter();
            mDrawerList.setAdapter(this.menuItemListAdapter);
            this.menuItemListAdapter.getMenuItemList().remove(0);
            selectedItem = 0;
            this.menuItemListAdapter.notifyDataSetChanged();
            return;
        }
        this.menuItemListAdapter = Helper.getInstance(context).getMenuListAdapter();
        mDrawerList.setAdapter(this.menuItemListAdapter);
        this.menuItemListAdapter.getMenuItemList().remove(this.menuItemListAdapter.getMenuItemList().size() - 1);
        selectedItem = 1;
        this.menuItemListAdapter.notifyDataSetChanged();
        userAddress.setVisibility(View.VISIBLE);
        userMobile.setVisibility(View.VISIBLE);
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

    public void CheckOut(View view) {
        if (!AppPrefs.getAppPrefs(context).getIsLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (AppPrefs.getAppPrefs(context).getAddress() == null) {
            startActivity(new Intent(this, AddressActivity.class));
        } else if (AppPrefs.getAppPrefs(context).getCheckOutVantage() == null) {
            Toast.makeText(context, "Your cart is empty", Toast.LENGTH_LONG).show();
        } else if (AppPrefs.getAppPrefs(context).getCheckOutVantage().CheckOutVantageList.size() == 0) {
            Toast.makeText(context, "Your cart is empty", Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(this, CheckoutContinueActivity.class));
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
    }

    public void BackFromCategoryItemActivity() {
        Fragment fragment = fragmentManager.findFragmentByTag(CategoryItemActivity.TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.inner_to_left, R.anim.left_to_out).remove(fragment).commitAllowingStateLoss();
        }
    }

    private boolean CheckCurrentFragment() {
        if (fragmentManager.findFragmentByTag(CategoryItemActivity.TAG) != null) {
            BackFromCategoryItemActivity();
            return false;
        } else if (getSupportFragmentManager().findFragmentByTag(ApiHelper.homeFragment) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, HomeActivity.newInstance(categoryDate), ApiHelper.homeFragment).commit();
            return false;
        } else if (getSupportFragmentManager().findFragmentByTag(ApiHelper.homeFragment).isVisible()) {
            return true;
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, HomeActivity.newInstance(categoryDate), ApiHelper.homeFragment).commit();
            return false;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (!CheckCurrentFragment()) {
            return true;
        } else {
            if (this.isBack) {
                finish();
                return true;
            }
            this.isBack = true;
            Toast.makeText(context, "Press again to exit", Toast.LENGTH_LONG).show();
            return true;
        }
    }
*/
