package com.example.reedme.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Helper {
    private static Helper helper;
    RelativeLayout actionBarMainTitle;
    Context context;
    ImageView imgCategoryIcon;
    ImageView imgMainIcon;
    RelativeLayout mainMenu;
    ImageButton mainMenuBtn;
    RelativeLayout searchButton;
    TextView txtTitle;

    public static Helper getInstance(Context context) {
        if (helper == null) {
            helper = new Helper(context);
        }
        return helper;
    }

    public Helper(Context context) {
        this.context = context;
    }

    public Typeface getArlrdbdFont() {

        return Typeface.createFromAsset(this.context.getAssets(), "fonts/ARLRDBD.TTF");
    }

    public Typeface getMavenProMediumFont() {
        return Typeface.createFromAsset(this.context.getAssets(), "fonts/maven-pro-medium.ttf");
    }

    public int getStatusBarHeight() {
        int resourceId = this.context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return this.context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /*public MenuItemListAdapter getMenuListAdapter() {
        List<VMenuItem> vMenuItems = new ArrayList();
        vMenuItems.add(new VMenuItem(R.string.id_login, "Login", R.mipmap.login_white, R.mipmap.login_red));
        vMenuItems.add(new VMenuItem(R.string.id_home, "Home", R.mipmap.home_white, R.mipmap.home_red));
        vMenuItems.add(new VMenuItem(R.string.id_address, "Address", R.mipmap.address_white, R.mipmap.address_red));
        vMenuItems.add(new VMenuItem(R.string.id_my_orders, "My Orders", R.mipmap.my_order_white, R.mipmap.my_order_red));
        vMenuItems.add(new VMenuItem(R.string.id_contact_us, "Contact Us", R.mipmap.contactus_white, R.mipmap.contactus_red));
        vMenuItems.add(new VMenuItem(R.string.id_rate, "Rate", R.mipmap.rate_white, R.mipmap.rate_red));
        vMenuItems.add(new VMenuItem(R.string.id_faq, "FAQ", R.mipmap.faq_white, R.mipmap.faq_red));
        vMenuItems.add(new VMenuItem(R.string.id_share, "Share", R.mipmap.share, R.mipmap.share));
        vMenuItems.add(new VMenuItem(R.string.id_logout, "Logout", R.mipmap.login_white, R.mipmap.login_red));
        return new MenuItemListAdapter(this.context, vMenuItems);
    }*/

   /* public void setCustomActionBar(ActionBar mActionBar) {

        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        View mCustomView = LayoutInflater.from(this.context).inflate(R.layout.action_bar_main, null);
        mCustomView.setLayoutParams(new LayoutParams(-1, -2));
        this.imgMainIcon = (ImageView) mCustomView.findViewById(R.id.img_action_bar_logo);
        this.actionBarMainTitle = (RelativeLayout) mCustomView.findViewById(R.id.rly_image_and_title);
        this.txtTitle = (TextView) mCustomView.findViewById(R.id.txt_action_bar_category_name);
        this.imgCategoryIcon = (ImageView) mCustomView.findViewById(R.id.img_action_bar_category_icon);
        this.mainMenu = (RelativeLayout) mCustomView.findViewById(R.id.lyt_action_bar_menu);
        this.mainMenuBtn = (ImageButton) mCustomView.findViewById(R.id.img_action_bar_menu);
        this.searchButton = (RelativeLayout) mCustomView.findViewById(R.id.rly_action_bar_search);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }*/

   /* public void setBackButton(String title) {
        this.mainMenuBtn.setBackgroundResource(R.mipmap.back);
        this.imgMainIcon.setVisibility(View.GONE);
        this.actionBarMainTitle.setVisibility(View.VISIBLE);
        this.txtTitle.setText(title);
        this.imgCategoryIcon.setImageResource(R.mipmap.icon_continue_top);
    }*/

    public void hideSearchButton() {
        this.searchButton.setVisibility(View.GONE);
    }

    public RelativeLayout getSearchButton() {
        return this.searchButton;
    }

    public RelativeLayout getMainMenu() {
        return this.mainMenu;
    }

   /* public void setBackOnMenu() {
        this.mainMenuBtn.setBackgroundResource(R.mipmap.back);
    }*/
}
