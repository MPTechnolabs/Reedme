package com.example.reedme.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.adapter.CheckoutItemAdapter;
import com.example.reedme.adapter.CheckoutMerchantItemAdapter;
import com.example.reedme.barcodescanner.BarcodeCallback;
import com.example.reedme.barcodescanner.BarcodeResult;
import com.example.reedme.barcodescanner.CompoundBarcodeView;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Util;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.model.Product;
import com.example.reedme.model.Vantage;
import com.example.reedme.model.login_code;
import com.example.reedme.views.CustomDelegate;
import com.example.reedme.views.SweetSheet;
import com.google.zxing.ResultPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 6/24/2016.
 */
public class activity_merchant_continue  extends AppCompatActivity {
    public static ListView checkoutList;
    static Context context;
    static float totalAmount;
    private SweetSheet mSweetSheet;
    private RelativeLayout rl;
    RelativeLayout add;
    private CompoundBarcodeView barcodeScannerView;
    static TextView txtPaybleValue;
    String product_result;
    ProgressBar progressBar;
    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    CheckOutData checkOutData;
    RelativeLayout.LayoutParams layoutparams;
    CheckoutMerchantItemAdapter adapter;
    TextView txt_name;
    String str_FirstName, str_LastName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_merchant_continue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("CHECKOUT");


        try {
            context = this;
            mJsonParser = new MyJSONParser();
            LoadContext();

            LoatData();

        } catch (Exception ex) {

            Log.e("Exception",ex.toString());
        }
        if(AppPrefs.getAppPrefs(activity_merchant_continue.this).getIsQR())
        {
            str_FirstName = AppPrefs.getAppPrefs(context).getString("firstname");
            str_LastName = AppPrefs.getAppPrefs(context).getString("lastname");

            txt_name.setText("Hello, "+str_FirstName+" "+str_LastName);
        }
    }

    public void LoadContext() {
        txtPaybleValue = (TextView) findViewById(R.id.txt_payable_value);
        txt_name = (TextView) findViewById(R.id.txt_name);

        rl = (RelativeLayout) findViewById(R.id.rl);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        add= (RelativeLayout) findViewById(R.id.lyt_checkout_empty_cart_button);

        setupCustomView();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetSheet.toggle();
                barcodeScannerView.pause();
                barcodeScannerView.resume();
            }
        });
    }
    private void setupCustomView() {
        mSweetSheet = new SweetSheet(rl);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation);
        View view = LayoutInflater.from(activity_merchant_continue.this).inflate(R.layout.fragment_scan, null, false);
        customDelegate.setCustomView(view);
        mSweetSheet.setDelegate(customDelegate);


        barcodeScannerView = (CompoundBarcodeView)view.findViewById(R.id.zxing_barcode_scanner);
        // barcodeScannerView.resume();

        barcodeScannerView.decodeContinuous(callback);

    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                //txt_result.setText(result.getText());
                product_result = result.getText();
                barcodeScannerView.pause();

                new JSONParse().execute();



                //barcodeScannerView.setStatusText(result.getText());
                mSweetSheet.dismiss();
            }
        }
        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };


    public void LoatData() {
        checkOutData = AppPrefs.getAppPrefs(context).getCheckOutVantage();
        setCheckOutPayableValue(checkOutData);
        checkoutList = (ListView) findViewById(R.id.lst_checkout_continue_item);

        adapter = new CheckoutMerchantItemAdapter(checkOutData.CheckOutVantageList);
        checkoutList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public static void setCheckOutPayableData() {
        setCheckOutPayableValue(AppPrefs.getAppPrefs(context).getCheckOutVantage());
    }

    private static void setCheckOutPayableValue(CheckOutData checkOutData) {
        float totalAmount = Util.getInstance(context).getCheckOutTotalAmount(checkOutData);
        //float shippingValue = Util.getInstance(context).getShippingAmount(totalAmount);
        txtPaybleValue.setText(String.valueOf(totalAmount));
        //txtShipping.setText(String.valueOf(shippingValue));
    }


    public void ContinueCheckOut(View view) {

        if((AppPrefs.getAppPrefs(activity_merchant_continue.this).getIsQR()) == true) {
            Intent intent = new Intent(this, CheckoutMerchantPlaceOrder.class);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, DrawerActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
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

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.inner_to_left, R.anim.left_to_out);
    }

    private class JSONParse extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String  doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();

                params.put("barcode_num", product_result);
                jsonObject_parent = mJsonParser.postData("http://mptechnolabs.com/1reward/getProducts.php", params);
                Log.e("JsonObject", jsonObject_parent + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);
            LoginCallAction(ParseDataProvider.getInstance(context).IsSuccessRegister(jsonObject_parent));

        }
    }



    protected void LoginCallAction(Integer isSuccess) {
        if (isSuccess == 1) {

            try {

                JSONArray jsonStore = jsonObject_parent.optJSONArray("Product_list");
                JSONObject pObject = jsonStore.getJSONObject(0);

                Product product = new Product();
                product.setProductId(Integer.parseInt(pObject.getString("pro_id")));
                product.setProductName(pObject.getString("product_name"));
                product.setStorename(pObject.getString("store_name"));
                List<Vantage> vantageList = new ArrayList();
                Vantage vantage = new Vantage();
                vantage.setVantageId(Integer.parseInt(pObject.getString("pro_id")));
                vantage.setVantagePrice(pObject.getString("price"));
                vantage.setVantageSize(pObject.getString("size"));
                vantage.setVantageImage(pObject.getString("product_img"));
                vantageList.add(vantage);
                product.setVantages(vantageList);

                Util.getInstance(context).AddCheckOutVantageMerchant(vantage, product.getProductName());

                finish();
                Intent intent = getIntent();

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                activity_merchant_continue.this.overridePendingTransition(0, 0);


                startActivity(intent);

            } catch (Exception e) {
            }


        } else {

            final Dialog dialog = new Dialog(activity_merchant_continue.this);
            // Include dialog.xml file
            dialog.setContentView(R.layout.custom_simple_alert_dialog_layout);
            // Set dialog title
            Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

            Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
            TextView txt_title_dialog = (TextView) dialog.findViewById(R.id.txt_title);

            TextView txt_msg_dialog = (TextView) dialog.findViewById(R.id.txt_message);
            txt_title_dialog.setText("Add Product");
            txt_msg_dialog.setText("No Product Found ...Do you want to Add New Product ?");

            dialog.show();

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(activity_merchant_continue.this, Add_product.class));
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                    dialog.dismiss();

                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }



            /*Utills.showCustomSimpleDialog(context, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {
                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }
            }, Constants.DIALOG_INFO_TITLE, "No product found", false);
*/

        }

}

