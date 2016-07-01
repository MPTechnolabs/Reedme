package com.example.reedme.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.reedme.adapter.DeliveryTimeAdapter;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.ApiHelper;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.Helper;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Util;
import com.example.reedme.helper.Utills;
import com.example.reedme.helper.VolleyHelper;
import com.example.reedme.model.Address;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.model.CheckOutVantage;
import com.example.reedme.model.ShippingInfo;
import com.example.reedme.R;
import com.example.reedme.views.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckoutPlaceOrderActivity extends AppCompatActivity {
  public static final String TAG = CheckoutPlaceOrderActivity.class.getSimpleName();
    String address;
    CheckOutData checkOutData;
    Context context;
    TextView txtPayble;
    TextView txtTotalShipping;
    TextView txtTotalValue;
    CategoryData categoryData;
    float tAmount;
    TextView Discount;
    AVLoadingIndicatorView progress;
    float wallet;
    String str_FirstName, str_LastName,str_mobile,str_city,star_state,str_country,str_email,str_userid,str_address,str_pincode;
    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place_order);
        categoryData = (CategoryData) getIntent().getExtras().getSerializable("CategoryData");
        wallet =getIntent().getExtras().getFloat("Wallet");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("CHECKOUT");

        str_userid = AppPrefs.getAppPrefs(context).getString("user_id");
        str_FirstName = AppPrefs.getAppPrefs(context).getString("firstname");
        str_LastName = AppPrefs.getAppPrefs(context).getString("lastname");
        str_address = AppPrefs.getAppPrefs(context).getString("address");
        str_mobile = AppPrefs.getAppPrefs(context).getString("phone");
        star_state = AppPrefs.getAppPrefs(context).getString("state");
        str_city = AppPrefs.getAppPrefs(context).getString("city");
        str_pincode = AppPrefs.getAppPrefs(context).getString("pincode");
        str_email = AppPrefs.getAppPrefs(context).getString("email");

        this.checkOutData = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        tAmount = Util.getInstance(this.context).getCheckOutTotalAmount(this.checkOutData);
        mJsonParser = new MyJSONParser();

        this.context = this;
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);
        LoadContext();
        LoatData();
    }
    public void LoadContext()
    {
        this.txtTotalValue = (TextView) findViewById(R.id.txt_place_payable_total_value);
        this.txtTotalShipping = (TextView) findViewById(R.id.txt_place_payable_shipping_value);
        this.txtPayble = (TextView) findViewById(R.id.txt_place_payable_total_amout_value);
        this.Discount = (TextView) findViewById(R.id.txt_discount);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);
    }

    public void LoatData() {
        this.address = AppPrefs.getAppPrefs(context).getString("address");
        this.checkOutData = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
        float totalAmount = Util.getInstance(this.context).getCheckOutTotalAmount(this.checkOutData);
        float netTotal = totalAmount - wallet;

        this.txtTotalShipping.setText("0.00");
        this.Discount.setText(wallet+"");
        this.txtTotalValue.setText(String.valueOf(totalAmount));
        this.txtPayble.setText(String.valueOf(netTotal));
    }

    public void ContinuePlaceOrder(View view) {
        new OrderCall().execute();
        //AppPrefs.getAppPrefs(this.context).setCheckOutVantage(null);
        //StartActivity.getInstance().SetCheckOutValue();
        //Toast.makeText(getApplicationContext(),"Thank you for Shopping with US :)",Toast.LENGTH_LONG).show();
        /*Intent intent = new Intent(this, StartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("CategoryData", categoryData);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

        startActivity(intent);
        finish();
*/
    }


    public class OrderCall extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            //Utills.showDialog(context);
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... args) {

            try {

                JSONObject address = new JSONObject();
                address.put("user_id",str_userid);
                address.put("Name",str_FirstName+str_LastName);
                address.put("cell_no",str_mobile);
                address.put("email",str_email);
                address.put("house_no","23");
                address.put("street",str_address);
                address.put("locality",star_state);
                address.put("landmark","star");
                address.put("city",str_city);

                JSONObject params = new JSONObject();


                params.put("user_id", str_userid);
                params.put("prod_id",Util.getInstance(context).getCheckOutItemsIds());
                params.put("s_id",Util.getInstance(context).getStoreId());
                params.put("cat_id",Util.getInstance(context).getCategoryId());
                params.put("wallet_point",Util.getInstance(context).getWallet());

                params.put("qty",Util.getInstance(context).getCheckOutItemscount());
                params.put("tot_qty",Util.getInstance(context).getCheckOutTotalItemscount());
                params.put("amount",Util.getInstance(context).getCheckoutPrice());
                params.put("size",Util.getInstance(context).getsize());
                params.put("color",Util.getInstance(context).getColor());

                params.put("tot_Amount",tAmount);
                params.put("address_json",address);


                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/placeOrder.php", params);
                Log.e("Response ------>", jsonObject_parent + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Utills.dismissDialog();
            progress.setVisibility(View.GONE);

            OrderCallAction(ParseDataProvider.getInstance(CheckoutPlaceOrderActivity.this).IsSuccessRegister(jsonObject_parent));

        }
    }

        public String createVantageJson(HashMap<Integer, CheckOutVantage> vantageList) throws JSONException {
        JSONArray jArray = new JSONArray();
        for (CheckOutVantage vObject : vantageList.values()) {
            JSONObject jVantage = new JSONObject();
            if (vObject != null) {
                jVantage.put("variant_id", String.valueOf(vObject.getVantageId()));
                jVantage.put("variant_count", String.valueOf(vObject.getVantageQty()));
                jArray.put(jVantage);
            }
        }
        return jArray.toString();
    }




    private void OrderCallAction(Integer success) {
        if (success == 1) {
          Toast.makeText(CheckoutPlaceOrderActivity.this,"Order Place successfully",Toast.LENGTH_LONG).show();
            AppPrefs.getAppPrefs(this.context).setCheckOutVantage(null);
            StartActivity.getInstance().SetCheckOutValue();
            Intent intent = new Intent(this, StartActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CategoryData", categoryData);
            intent.putExtras(bundle);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

            startActivity(intent);
            finish();
        }
        else
        {
            Utills.showCustomSimpleDialog(CheckoutPlaceOrderActivity.this, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {
                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }
            }, Constants.DIALOG_INFO_TITLE, "Something,Went Wrong", false);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home

                Intent intent = new Intent(this, CheckoutProceedActivity.class);
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
        Intent intent = new Intent(this, CheckoutProceedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("CategoryData", categoryData);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

        startActivity(intent);
        finish();
        //startActivity(new Intent(this, CheckoutProceedActivity.class));
       // finish();
        return true;
    }
}
