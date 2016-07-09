package com.example.reedme.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.activity.*;
import com.example.reedme.adapter.AdapterSortList;
import com.example.reedme.adapter.CategoryItemAdapter;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Util;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.Category;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.model.SubCategory;
import com.example.reedme.views.AVLoadingIndicatorView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemActivity extends AppCompatActivity {
    static String ItemId = null;
    public static final String TAG;
    static String itemName;
    public static ListView subCategoryList;
    private ViewPager mViewPager;
    public static CategoryItemActivity startActivity;

    CategoryData categoryDate;
    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    public static int pageNo = 0;
    String str_PriceLow = "", str_PriceHigh = "", str_SortBy = "";
    String[] sTitleSort = {"Price High - Low", "Price Low - High"};
    public  static  EditText edt_PriceLow, edt_PriceHigh;
    ArrayList<String> listTitleSort = new ArrayList<String>();
    TextView txt_message;
    CategoryItemAdapter adapter;

    private LinearLayout ll_filter;
    RelativeLayout rel_filter, rel_sort;
    Dialog dialog_Filter;
    AVLoadingIndicatorView progress;
    public static TextView txtCheckOutRupee;
    public static TextView txtItemCount;

    static {
        TAG = CategoryItemActivity.class.getSimpleName();
    }

  /*  public static CategoryItemActivity newInstance(String id, String name) {
        ItemId = id;
        itemName = name;
        return new CategoryItemActivity();
    }
*/
  public static CategoryItemActivity getInstance() {
      if (startActivity == null) {
          startActivity = new CategoryItemActivity();
      }
      return startActivity;
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);

      setContentView(R.layout.category_items);

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setTitle("Products");

      SharedPreferences appSharedPrefs = PreferenceManager
              .getDefaultSharedPreferences(this.getApplicationContext());
      Gson gson = new Gson();

      String json = appSharedPrefs.getString("MyObject", "");
      categoryDate = gson.fromJson(json, CategoryData.class);

      ItemId = getIntent().getStringExtra("id");
      itemName = getIntent().getStringExtra("name");
      mJsonParser = new MyJSONParser();

      txt_message = (TextView) findViewById(R.id.txt_message);
      txtCheckOutRupee = (TextView) findViewById(R.id.txt_total_rupee);
      txtItemCount = (TextView) findViewById(R.id.widget_title_icon2);
      ll_filter = (LinearLayout) findViewById(R.id.ll_filter);
      subCategoryList = (ListView) findViewById(R.id.lst_category_items);

      rel_filter = (RelativeLayout) findViewById(R.id.rel_filter);
      rel_sort = (RelativeLayout) findViewById(R.id.rel_sort);
      progress = (AVLoadingIndicatorView) findViewById(R.id.progress);

      rel_filter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              openFilteDialoug();
          }
      });
      rel_sort.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openSortDialoug();

          }
      });

      SetCheckOutValue();

      LoadData();
  }



    private void LoadData() {

      /*  String savedsubCategory = AppPrefs.getAppPrefs(StoreDiaplyActivity.context).getSubCategory(ItemId);
        if (savedsubCategory == null) {*/
            new JSONParse().execute();
           /* return;
        }
        this.subCategories = ParseDataProvider.getInstance(StartActivity.context).ParseSubCategoryData(savedsubCategory);
        SubCategoryCallAction(this.subCategories);*/
    }

    private class JSONParse extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // Utills.showDialog(getActivity());
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();

                params.put("s_id", ItemId);
                params.put("min_price",str_PriceLow);
                params.put("max_price",str_PriceHigh);
                params.put("order",str_SortBy);

                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/getProducts.php", params);
                //LoginCallAction(ParseDataProvider.getInstance(context).IsSuccessRegister(jsonObject_parent));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Utills.dismissDialog();
            progress.setVisibility(View.GONE);

            CategoryItemActivity.this.SubCategoryCallAction(ParseDataProvider.getInstance(CategoryItemActivity.this).SubCategoryData(ItemId, jsonObject_parent));

        }
    }

    protected void SubCategoryCallAction(List<SubCategory> subCategories) {

        if (subCategories != null) {
            txt_message.setVisibility(View.GONE);
            ll_filter.setVisibility(View.VISIBLE);
            rel_filter.setVisibility(View.VISIBLE);
            rel_sort.setVisibility(View.VISIBLE);
            subCategoryList.setVisibility(View.VISIBLE);


            int i = 0;
        while (i < subCategories.size()) {
            try {
                adapter = new CategoryItemAdapter(subCategories.get(i).getProducts());

                subCategoryList.setAdapter(adapter);

                i++;



            } catch (Exception ex) {
                Log.e("Vegie's", ex.getMessage());
                return;
            }
        }

    }
    else
    {
        subCategoryList.setVisibility(View.GONE);
        txt_message.setVisibility(View.VISIBLE);
        ll_filter.setVisibility(View.GONE);
        rel_filter.setVisibility(View.GONE);
        rel_sort.setVisibility(View.GONE);


    }

    }



    private void openFilteDialoug() {

        dialog_Filter = new Dialog(CategoryItemActivity.this);
        dialog_Filter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Filter.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_Filter.getWindow().setGravity(Gravity.CENTER);
        dialog_Filter.setCanceledOnTouchOutside(false);
        dialog_Filter.setContentView(R.layout.dialoug_filter);

        edt_PriceLow = (EditText) dialog_Filter.findViewById(R.id.edt_price_low);
        edt_PriceHigh = (EditText) dialog_Filter.findViewById(R.id.edt_price_high);

        Button btn_Done = (Button) dialog_Filter.findViewById(R.id.btn_done);
        Button btn_cancel = (Button) dialog_Filter.findViewById(R.id.btn_cancel);

        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_PriceHigh = edt_PriceHigh.getText().toString().trim();
                str_PriceLow = edt_PriceLow.getText().toString().trim();
                int high = 0, low = 0;
                if (!str_PriceHigh.equalsIgnoreCase("") && !str_PriceLow.equalsIgnoreCase("")) {
                    high = Integer.parseInt(str_PriceHigh);
                    low = Integer.parseInt(str_PriceLow);
                }

                if (high > 0 && low > 0) {
                    if (!(high > low)) {
                        Utills.hideDialog();
                        Utills.showCustomSimpleDialog(CategoryItemActivity.this, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                            @Override
                            public void onOkayButtonClick() {
                                if (Utills.customSimpleMessageDialog != null) {
                                    Utills.customSimpleMessageDialog.dismiss();
                                }
                            }
                        }, Constants.DIALOG_INFO_TITLE, "Please enter Correct Price", false);
                        return;
                    } else {
                        LoadData();

                    }
                } else {
                    LoadData();

                }
                if (dialog_Filter.isShowing()) {
                    dialog_Filter.dismiss();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_PriceHigh = "";
                str_PriceLow = "";

                LoadData();

                if (dialog_Filter.isShowing()) {
                    dialog_Filter.dismiss();
                }
            }
        });

        dialog_Filter.show();
    }

    private void openSortDialoug() {

        final Dialog dialog = new Dialog(CategoryItemActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_sort);

        ListView listView = (ListView) dialog.findViewById(R.id.lv_sort_price);
        RelativeLayout Rel_sort = (RelativeLayout) dialog.findViewById(R.id.rel_sort);

        listTitleSort.clear();
        for (int i = 0; i < sTitleSort.length; i++) {
            listTitleSort.add(sTitleSort[i]);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    str_SortBy = "max_price";
                }
                if (position == 1) {
                    str_SortBy = "min_price";
                }
                LoadData();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        Rel_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        AdapterSortList adapterSortList = new AdapterSortList(CategoryItemActivity.this, listTitleSort);
        listView.setAdapter(adapterSortList);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                adapter.filter(searchQuery.toString().trim());
                subCategoryList.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();


            }


            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void SetCheckOutValue() {
        CheckOutData checkOutData = AppPrefs.getAppPrefs(CategoryItemActivity.this).getCheckOutVantage();
        if (checkOutData != null) {
            txtCheckOutRupee.setText(String.valueOf(Util.getInstance(CategoryItemActivity.this).getCheckOutTotalAmount(checkOutData)));
            txtItemCount.setText(String.valueOf(Util.getInstance(CategoryItemActivity.this).getCheckOutVantageCount(checkOutData)));

            return;
        }
        txtCheckOutRupee.setText("0");
        txtItemCount.setText("0");
    }
    public void CheckOut(View view) {
        if (AppPrefs.getAppPrefs(CategoryItemActivity.this).getCheckOutVantage() == null) {
            Toast.makeText(CategoryItemActivity.this, "Your cart is empty", Toast.LENGTH_LONG).show();
        } else if (AppPrefs.getAppPrefs(CategoryItemActivity.this).getCheckOutVantage().CheckOutVantageList.size() == 0) {
            Toast.makeText(CategoryItemActivity.this, "Your cart is empty", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, CheckoutContinueActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryDate);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
