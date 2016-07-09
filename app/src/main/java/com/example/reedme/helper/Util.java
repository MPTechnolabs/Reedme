package com.example.reedme.helper;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.reedme.R;
import com.example.reedme.activity.StartActivity;
import com.example.reedme.activity.activity_merchant_continue;
import com.example.reedme.adapter.CheckoutMerchantItemAdapter;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.model.CheckOutVantage;
import com.example.reedme.model.ShippingInfo;
import com.example.reedme.model.Vantage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private static Util util;
    Context context;
    public static final String PREFS_NAME = "TAKEOFFANDROID";
    public static final String KEY_COUNTRIES = "Countries";

    public static Util getInstance(Context context) {
        if (util == null) {
            util = new Util(context);
        }
        return util;
    }

    public Util(Context context) {
        this.context = context;
    }

    public int getAppVersion() {
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public Point getDeviceSize() {
        Display display = ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public int getMidPoint(int x, int y) {
        return x + ((y - x) / 2);
    }

    public void drawTriangles(int start, int end, int height, Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(2.0f);
        paint.setColor(this.context.getResources().getColor(R.color.colorPrimary));
        paint.setStyle(Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        Path path = new Path();
        path.setFillType(FillType.EVEN_ODD);
        Point a = new Point(getMidPoint(start, end) - 12, height);
        Point b = new Point(getMidPoint(start, end) + 12, height);
        Point c = new Point(getMidPoint(start, end), height - 13);
        path.moveTo((float) a.x, (float) a.y);
        path.lineTo((float) b.x, (float) b.y);
        path.lineTo((float) c.x, (float) c.y);
        path.lineTo((float) a.x, (float) a.y);
        path.close();
        canvas.drawPath(path, paint);
    }

    public boolean IsEmailValid(String email) {
        return email.trim().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+");
    }

    public boolean IsNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void AddCheckOutVantage(Vantage vantage, String vName) {
        CheckOutVantage cVantage = new CheckOutVantage();
        cVantage.setVantageId(vantage.getVantageId());
        cVantage.setVantageName(vName);
        cVantage.setVantageSize(vantage.getVantageSize());
        cVantage.setVantageImage(vantage.getVantageImage());
        cVantage.setVantagePrice(vantage.getVantagePrice());
        AddCheckOutItem(cVantage);
    }

    public void AddCheckOutVantageMerchant(Vantage vantage, String vName) {
        CheckOutVantage cVantage = new CheckOutVantage();
        cVantage.setVantageId(vantage.getVantageId());
        cVantage.setVantageName(vName);
        cVantage.setVantageSize(vantage.getVantageSize());
        cVantage.setVantageImage(vantage.getVantageImage());
        cVantage.setVantagePrice(vantage.getVantagePrice());
        AddCheckOutItemMerchant(cVantage);

    }

    public void AddCheckOutItemMerchant(CheckOutVantage vantage) {
        CheckOutData checkOutData = AppPrefs.getAppPrefs(context).getCheckOutVantage();
        if (checkOutData != null) {
            CheckOutVantage chkVantage = checkOutData.CheckOutVantageList.get(Integer.valueOf(vantage.getVantageId()));
            if (chkVantage != null) {
                vantage.setVantageQty(chkVantage.getVantageQty() + 1);
                checkOutData.CheckOutVantageList.remove(Integer.valueOf(vantage.getVantageId()));
                checkOutData.CheckOutVantageList.put(Integer.valueOf(vantage.getVantageId()), vantage);
            } else {
                vantage.setVantageQty(1);
                checkOutData.CheckOutVantageList.put(Integer.valueOf(vantage.getVantageId()), vantage);
            }
        } else {
            checkOutData = new CheckOutData();
            vantage.setVantageQty(1);
            checkOutData.CheckOutVantageList.put(Integer.valueOf(vantage.getVantageId()), vantage);
        }
        AppPrefs.getAppPrefs(this.context).setCheckOutVantage(checkOutData);

    }



    public void AddCheckOutItem(CheckOutVantage vantage) {
        CheckOutData checkOutData = AppPrefs.getAppPrefs(context).getCheckOutVantage();
        if (checkOutData != null) {
            CheckOutVantage chkVantage = checkOutData.CheckOutVantageList.get(Integer.valueOf(vantage.getVantageId()));
            if (chkVantage != null) {
                vantage.setVantageQty(chkVantage.getVantageQty() + 1);
                checkOutData.CheckOutVantageList.remove(Integer.valueOf(vantage.getVantageId()));
                checkOutData.CheckOutVantageList.put(Integer.valueOf(vantage.getVantageId()), vantage);
            } else {
                vantage.setVantageQty(1);
                checkOutData.CheckOutVantageList.put(Integer.valueOf(vantage.getVantageId()), vantage);
            }
        } else {
            checkOutData = new CheckOutData();
            vantage.setVantageQty(1);
            checkOutData.CheckOutVantageList.put(Integer.valueOf(vantage.getVantageId()), vantage);
        }
        AppPrefs.getAppPrefs(this.context).setCheckOutVantage(checkOutData);
        StartActivity.getInstance().SetCheckOutValue();
    }

    public void RemoveCheckOutVantage(int vantageId) {
        RemoveCheckOutItem(vantageId);
        StartActivity.getInstance().SetCheckOutValue();
    }

    public void RemoveCheckOutVantageMerchant(int vantageId) {
        RemoveCheckOutItem(vantageId);
    }


    public void RemoveCheckOutItem(int vantageId) {
        CheckOutData checkOutdata = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        if (checkOutdata != null) {
            CheckOutVantage chkVantage = checkOutdata.CheckOutVantageList.get(Integer.valueOf(vantageId));
            if (chkVantage != null) {
                if (chkVantage.getVantageQty() == 1) {
                    checkOutdata.CheckOutVantageList.remove(Integer.valueOf(vantageId));
                } else {
                    chkVantage.setVantageQty(chkVantage.getVantageQty() - 1);
                    checkOutdata.CheckOutVantageList.remove(Integer.valueOf(vantageId));
                    checkOutdata.CheckOutVantageList.put(Integer.valueOf(chkVantage.getVantageId()), chkVantage);
                }
            }
        }
        AppPrefs.getAppPrefs(this.context).setCheckOutVantage(checkOutdata);
    }

    public CheckOutVantage getCheckoutAddedVantage(int vantageId) {
        CheckOutData checkOutData = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        if (checkOutData != null) {
            return checkOutData.CheckOutVantageList.get(Integer.valueOf(vantageId));
        }
        return null;
    }

    public float getCheckOutTotalAmount(CheckOutData checkOutData) {
        float totalAmount = 0.0f;
        List<CheckOutVantage> vantageList = new ArrayList(checkOutData.CheckOutVantageList.values());
        for (int i = 0; i < vantageList.size(); i++) {
            CheckOutVantage vantage = vantageList.get(i);
            String value = vantage.getVantagePrice();
            //value = value.substring(1);
            //String result = value.replaceAll("[.,]","");
            totalAmount += Float.parseFloat(value) * ((float) vantage.getVantageQty());

        }
        return totalAmount;
    }

    public int getCheckOutVantageCount(CheckOutData checkOutData) {
        return checkOutData.CheckOutVantageList.size();
    }

    public float getShippingAmount(float totalAmount) {
        ShippingInfo shippingInfo = AppPrefs.getAppPrefs(this.context).getShippingInfo();
        if (totalAmount < Float.parseFloat(shippingInfo.getShippingAmount())) {
            return Float.parseFloat(shippingInfo.getShippingCriteria());
        }
        return 0.0f;
    }

    public float getShippingAmount(float totalAmount, ShippingInfo shippingInfo) {
        if (totalAmount < Float.parseFloat(shippingInfo.getShippingAmount())) {
            return Float.parseFloat(shippingInfo.getShippingCriteria());
        }
        return 0.0f;
    }

    public String getCheckOutItemsIds() {
        CheckOutData categoryDate = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        StringBuilder chkIdsBuilder = new StringBuilder();
        if (categoryDate != null) {
            List<CheckOutVantage> chkVantages = new ArrayList(categoryDate.CheckOutVantageList.values());
            for (int i = 0; i < chkVantages.size(); i++) {
                if (i == 0) {
                    chkIdsBuilder.append(chkVantages.get(i).getVantageId());
                } else {
                    chkIdsBuilder.append(",");
                    chkIdsBuilder.append(chkVantages.get(i).getVantageId());
                }
            }
        }
        return chkIdsBuilder.toString();
    }

    public String getCheckoutPrice() {
        CheckOutData categoryDate = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        StringBuilder chkIdsBuilder = new StringBuilder();
        if (categoryDate != null) {
            List<CheckOutVantage> chkVantages = new ArrayList(categoryDate.CheckOutVantageList.values());
            for (int i = 0; i < chkVantages.size(); i++) {
                if (i == 0) {
                    chkIdsBuilder.append(chkVantages.get(i).getVantagePrice());
                } else {
                    chkIdsBuilder.append(",");
                    chkIdsBuilder.append(chkVantages.get(i).getVantagePrice());
                }
            }
        }
        return chkIdsBuilder.toString();
    }

    public String getCheckOutItemscount() {
        CheckOutData categoryDate = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        StringBuilder chkIdsBuilder = new StringBuilder();
        if (categoryDate != null) {
            List<CheckOutVantage> chkVantages = new ArrayList(categoryDate.CheckOutVantageList.values());
            for (int i = 0; i < chkVantages.size(); i++) {
                if (i == 0) {
                    chkIdsBuilder.append(chkVantages.get(i).getVantageQty());
                } else {
                    chkIdsBuilder.append(",");
                    chkIdsBuilder.append(chkVantages.get(i).getVantageQty());
                }
            }
        }
        return chkIdsBuilder.toString();
    }

    public String getStoreId() {
        CheckOutData categoryDate = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        StringBuilder chkIdsBuilder = new StringBuilder();
        if (categoryDate != null) {
            List<CheckOutVantage> chkVantages = new ArrayList(categoryDate.CheckOutVantageList.values());
            for (int i = 0; i < chkVantages.size(); i++) {
                if (i == 0) {
                    chkIdsBuilder.append(AppPrefs.getAppPrefs(context).getString("StoreId"));
                } else {
                    chkIdsBuilder.append(",");
                    chkIdsBuilder.append(AppPrefs.getAppPrefs(context).getString("StoreId"));
                }
            }
        }
        return chkIdsBuilder.toString();
    }

    public String getCategoryId() {
        CheckOutData categoryDate = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        StringBuilder chkIdsBuilder = new StringBuilder();
        if (categoryDate != null) {
            List<CheckOutVantage> chkVantages = new ArrayList(categoryDate.CheckOutVantageList.values());
            for (int i = 0; i < chkVantages.size(); i++) {
                if (i == 0) {
                    chkIdsBuilder.append(AppPrefs.getAppPrefs(context).getString("cateId"));
                } else {
                    chkIdsBuilder.append(",");
                    chkIdsBuilder.append(AppPrefs.getAppPrefs(context).getString("cateId"));
                }
            }
        }
        return chkIdsBuilder.toString();
    }

    public String getsize() {
        CheckOutData categoryDate = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        StringBuilder chkIdsBuilder = new StringBuilder();
        if (categoryDate != null) {
            List<CheckOutVantage> chkVantages = new ArrayList(categoryDate.CheckOutVantageList.values());
            for (int i = 0; i < chkVantages.size(); i++) {
                if (i == 0) {
                    chkIdsBuilder.append("32");
                } else {
                    chkIdsBuilder.append(",");
                    chkIdsBuilder.append("34");
                }
            }
        }
        return chkIdsBuilder.toString();
    }

    public String getColor() {
        CheckOutData categoryDate = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        StringBuilder chkIdsBuilder = new StringBuilder();
        if (categoryDate != null) {
            List<CheckOutVantage> chkVantages = new ArrayList(categoryDate.CheckOutVantageList.values());
            for (int i = 0; i < chkVantages.size(); i++) {
                if (i == 0) {
                    chkIdsBuilder.append("#ffgfg");
                } else {
                    chkIdsBuilder.append(",");
                    chkIdsBuilder.append("#ffgfg");
                }
            }
        }
        return chkIdsBuilder.toString();
    }

    public String getWallet() {
        CheckOutData categoryDate = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        StringBuilder chkIdsBuilder = new StringBuilder();
        if (categoryDate != null) {
            List<CheckOutVantage> chkVantages = new ArrayList(categoryDate.CheckOutVantageList.values());
            for (int i = 0; i < chkVantages.size(); i++) {
                if (i == 0) {
                    chkIdsBuilder.append(AppPrefs.getAppPrefs(context).getString("Wallet"));
                } else {
                    chkIdsBuilder.append(",");
                    chkIdsBuilder.append(AppPrefs.getAppPrefs(context).getString("Wallet"));
                }
            }
        }
        return chkIdsBuilder.toString();
    }




    public int getCheckOutTotalItemscount() {
        CheckOutData categoryDate = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        ArrayList  arrayList = new ArrayList();
        int total =0;

        if (categoryDate != null) {
            List<CheckOutVantage> chkVantages = new ArrayList(categoryDate.CheckOutVantageList.values());
            for (int i = 0; i < chkVantages.size(); i++) {

                    arrayList.add(chkVantages.get(i).getVantageQty());
                }
                for(int j=0; j<arrayList.size(); j++)
                {
                    total = total + (int) arrayList.get(j);
                }

        }
        return total;
    }


    public void UpdateCheckoutVariants(List<CheckOutVantage> variantList) {
        try {
            CheckOutData checkOutData = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
            if (checkOutData != null) {
                for (int i = 0; i < variantList.size(); i++) {
                    CheckOutVantage sVariant = variantList.get(i);
                    CheckOutVantage chkVantage = checkOutData.CheckOutVantageList.get(Integer.valueOf(sVariant.getVantageId()));
                    if (chkVantage != null) {
                        chkVantage.setVantagePrice(sVariant.getVantagePrice());
                        chkVantage.setVantageSize(sVariant.getVantageSize());
                        checkOutData.CheckOutVantageList.remove(Integer.valueOf(sVariant.getVantageId()));
                        if (sVariant.getVantageQty() > chkVantage.getVantageQty()) {
                            checkOutData.CheckOutVantageList.put(Integer.valueOf(sVariant.getVantageId()), chkVantage);
                        }
                    }
                }
                AppPrefs.getAppPrefs(this.context).setCheckOutVantage(checkOutData);
            }
        } catch (Exception ex) {
            Log.e("Vegies", ex.getMessage());
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public static boolean isConnectedNetwork (Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo () != null && cm.getActiveNetworkInfo ().isConnectedOrConnecting ();

    }
    public static final int PULSE_ANIMATOR_DURATION = 544;

    // Alpha level for time picker selection.
    public static final int SELECTED_ALPHA = 255;
    public static final int SELECTED_ALPHA_THEME_DARK = 255;
    // Alpha level for fully opaque.
    public static final int FULL_ALPHA = 255;

    public static boolean isJellybeanOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Try to speak the specified text, for accessibility. Only available on JB or later.
     * @param text Text to announce.
     */
    @SuppressLint("NewApi")
    public static void tryAccessibilityAnnounce(View view, CharSequence text) {
        if (isJellybeanOrLater() && view != null && text != null) {
            view.announceForAccessibility(text);
        }
    }

    /**
     * Takes a number of weeks since the epoch and calculates the Julian day of
     * the Monday for that week.
     *
     * This assumes that the week containing the {@link Time#EPOCH_JULIAN_DAY}
     * is considered week 0. It returns the Julian day for the Monday
     * {@code week} weeks after the Monday of the week containing the epoch.
     *
     * @param week Number of weeks since the epoch
     * @return The julian day for the Monday of the given week since the epoch
     */
    /**
     public static int getJulianMondayFromWeeksSinceEpoch(int week) {
     return MONDAY_BEFORE_JULIAN_EPOCH + week * 7;
     }
     */

    /**
     * Returns the week since {@link Time#EPOCH_JULIAN_DAY} (Jan 1, 1970)
     * adjusted for first day of week.
     *
     * This takes a julian day and the week start day and calculates which
     * week since {@link Time#EPOCH_JULIAN_DAY} that day occurs in, starting
     * at 0. *Do not* use this to compute the ISO week number for the year.
     *
     * @param julianDay The julian day to calculate the week number for
     * @param firstDayOfWeek Which week day is the first day of the week,
     *          see {@link Time#SUNDAY}
     * @return Weeks since the epoch
     */
    /**
     public static int getWeeksSinceEpochFromJulianDay(int julianDay, int firstDayOfWeek) {
     int diff = Time.THURSDAY - firstDayOfWeek;
     if (diff < 0) {
     diff += 7;
     }
     int refDay = Time.EPOCH_JULIAN_DAY - diff;
     return (julianDay - refDay) / 7;
     }
     */

    /**
     * Render an animator to pulsate a view in place.
     * @param labelToAnimate the view to pulsate.
     * @return The animator object. Use .start() to begin.
     */
    public static ObjectAnimator getPulseAnimator(View labelToAnimate, float decreaseRatio,
                                                  float increaseRatio) {
        Keyframe k0 = Keyframe.ofFloat(0f, 1f);
        Keyframe k1 = Keyframe.ofFloat(0.275f, decreaseRatio);
        Keyframe k2 = Keyframe.ofFloat(0.69f, increaseRatio);
        Keyframe k3 = Keyframe.ofFloat(1f, 1f);

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofKeyframe("scaleX", k0, k1, k2, k3);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofKeyframe("scaleY", k0, k1, k2, k3);
        ObjectAnimator pulseAnimator =
                ObjectAnimator.ofPropertyValuesHolder(labelToAnimate, scaleX, scaleY);
        pulseAnimator.setDuration(PULSE_ANIMATOR_DURATION);

        return pulseAnimator;
    }

    /**
     * Convert Dp to Pixel
     */
    @SuppressWarnings("unused")
    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = hsv[2] * 0.8f; // value component
        return Color.HSVToColor(hsv);
    }

    /**
     * Gets the colorAccent from the current context, if possible/available
     * @param context The context to use as reference for the color
     * @return the accent color of the current context
     */
    public static int getAccentColorFromThemeIfAvailable(Context context) {
        TypedValue typedValue = new TypedValue();
        // First, try the android:colorAccent
        if (Build.VERSION.SDK_INT >= 21) {
            context.getTheme().resolveAttribute(android.R.attr.colorAccent, typedValue, true);
            return typedValue.data;
        }
        // Next, try colorAccent from support lib
        int colorAccentResId = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
        if (colorAccentResId != 0 && context.getTheme().resolveAttribute(colorAccentResId, typedValue, true)) {
            return typedValue.data;
        }
        // Return the value in mdtp_accent_color
        return ContextCompat.getColor(context, R.color.mdtp_accent_color);
    }

    /**
     * Gets dialog type (Light/Dark) from current theme
     * @param context The context to use as reference for the boolean
     * @param current Default value to return if cannot resolve the attribute
     * @return true if dark mode, false if light.
     */
    public static boolean isDarkTheme(Context context, boolean current) {
        return resolveBoolean(context, R.attr.mdtp_theme_dark, current);
    }


    private static boolean resolveBoolean(Context context, @AttrRes int attr, boolean fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getBoolean(0, fallback);
        } finally {
            a.recycle();
        }
    }


}
