package com.example.reedme.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleAlertDialog;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Util;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.CheckOutData;
import com.example.reedme.model.CheckOutVantage;
import com.example.reedme.views.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jolly on 7/7/16.
 */
public class CheckoutMerchantPlaceOrder extends AppCompatActivity
{

        public static final String TAG = CheckoutMerchantPlaceOrder.class.getSimpleName();
        String address;
        CheckOutData checkOutData;
        Context context;

        TextView txtPayble;
        TextView txtTotalShipping;
        TextView txtTotalValue;
        TextView txt_name;
        EditText edt_wallet;
        CheckBox chk_select;
        TextView txt_wallet;
        float totalAmount;
        float tAmount;
        float yourwallet,enterwallet;
        float netTotal;

        TextView Discount;
        float wallet= 0;
        String str_FirstName, str_LastName,str_mobile,str_city,star_state,str_country,str_email,str_userid,str_address,str_pincode;
        public static MyJSONParser mJsonParser = null;
        JSONObject jsonObject_parent = null;
        float netAmount;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_merchant_palce_order);

            this.context = this;

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Place Order");

            str_userid = AppPrefs.getAppPrefs(context).getString("user_id");
            str_FirstName = AppPrefs.getAppPrefs(context).getString("firstname");
            str_LastName = AppPrefs.getAppPrefs(context).getString("lastname");
            str_address = AppPrefs.getAppPrefs(context).getString("address");
            str_mobile = AppPrefs.getAppPrefs(context).getString("phone");
            star_state = AppPrefs.getAppPrefs(context).getString("state");
            str_city = AppPrefs.getAppPrefs(context).getString("city");
            str_pincode = AppPrefs.getAppPrefs(context).getString("pincode");
            str_email = AppPrefs.getAppPrefs(context).getString("email");

            checkOutData = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
            tAmount = Util.getInstance(this.context).getCheckOutTotalAmount(this.checkOutData);
            mJsonParser = new MyJSONParser();

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
            this.txt_wallet = (TextView) findViewById(R.id.userWallet);
            this.chk_select = (CheckBox) findViewById(R.id.chkSelectWallet);
            this.edt_wallet = (EditText) findViewById(R.id.enterWallet);
            this.txt_name = (TextView) findViewById(R.id.txt_name);
            txt_wallet.setText("Your Wallet : "+ AppPrefs.getAppPrefs(CheckoutMerchantPlaceOrder.this).getString("Wallet"));
            edt_wallet.setText("0");


        }

        public void LoatData()
        {
            checkOutData = AppPrefs.getAppPrefs(this.context).getCheckOutVantage();
            totalAmount = Util.getInstance(this.context).getCheckOutTotalAmount(this.checkOutData);
            if(AppPrefs.getAppPrefs(CheckoutMerchantPlaceOrder.this).getString("Wallet").equals(""))
            {}
            else {

                yourwallet = Float.parseFloat(AppPrefs.getAppPrefs(CheckoutMerchantPlaceOrder.this).getString("Wallet"));
            }
            enterwallet = Float.parseFloat((edt_wallet.getText().toString()));
            netTotal = totalAmount - enterwallet;

            chk_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(isChecked)
                    {

                        if(yourwallet < enterwallet) {
                            edt_wallet.setError("Plese Enter Valid Amount");
                        }
                        if (enterwallet == 0) {

                            wallet =0;

                        }
                        else
                        {
                            wallet = enterwallet;
                        }
                        netTotal = totalAmount - wallet;


                        edt_wallet.setVisibility(View.VISIBLE);
                        txt_name.setText("Hello, "+str_FirstName+" "+str_LastName);

                        txtTotalShipping.setText("0.00");

                        Discount.setText(wallet+"");
                        txtTotalValue.setText(String.valueOf(totalAmount));
                        txtPayble.setText(String.valueOf(netTotal));


                    }
                    else
                    {
                        edt_wallet.setText("0");
                        edt_wallet.setVisibility(View.GONE);
                    }
                }
            });


            txt_name.setText("Hello, "+str_FirstName+" "+str_LastName);
            txtTotalShipping.setText("0.00");
            Discount.setText(wallet+"");
            txtTotalValue.setText(String.valueOf(totalAmount));
            txtPayble.setText(String.valueOf(netTotal));
        }

        public void ContinuePlaceOrder(View view) {
            netAmount = Float.parseFloat(txtPayble.getText().toString());
            new OrderCall().execute();

        }


public class OrderCall extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        Utills.showDialog(context);
        //progress.setVisibility(View.VISIBLE);

    }

    @Override
    protected String doInBackground(String... args) {

        try {

            JSONObject address = new JSONObject();
            address.put("user_id",str_userid);
            address.put("Name",str_FirstName+str_LastName);
            address.put("cell_no",str_mobile);
            address.put("email",str_email);
            address.put("house_no","");
            address.put("street",str_address);
            address.put("locality",star_state);
            address.put("landmark","");
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

            params.put("tot_Amount",netAmount);
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

        Utills.dismissDialog();
        //progress.setVisibility(View.GONE);

        OrderCallAction(ParseDataProvider.getInstance(CheckoutMerchantPlaceOrder.this).IsSuccessRegister(jsonObject_parent));

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
            Toast.makeText(CheckoutMerchantPlaceOrder.this,"Order Place successfully",Toast.LENGTH_LONG).show();
            AppPrefs.getAppPrefs(this.context).setCheckOutVantage(null);


            AppPrefs.getAppPrefs(CheckoutMerchantPlaceOrder.this).setIsQR(false);

            Intent intent = new Intent(CheckoutMerchantPlaceOrder.this, DrawerActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
            startActivity(intent);
            finish();
        }
        else
        {



            Utills.showCustomSimpleDialog(CheckoutMerchantPlaceOrder.this, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {
                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }
            }, Constants.DIALOG_INFO_TITLE, "Can't Place Order...", false);

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home

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

        finish();

        return true;
    }
}
