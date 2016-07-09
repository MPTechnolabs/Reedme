package com.example.reedme.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.reedme.R;
import com.example.reedme.adapter.OrderVariantAdapter;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.ApiHelper;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Helper;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Utills;
import com.example.reedme.helper.VolleyHelper;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.DeliveryTime;
import com.example.reedme.model.OrderDetail;
import com.example.reedme.model.OrderVariant;
import com.example.reedme.model.ShippingInfo;
import com.example.reedme.views.AVLoadingIndicatorView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity {
    public static final String TAG;
    RelativeLayout cancelOrder;
    Context context;
    ListView lstOrderVariantList;
    OrderDetail orderDetail;
    ShippingInfo shippingInfo;
    TextView txtAmount;
    TextView txtCanceled;
    TextView txtDelivered;
    TextView txtDelivery;
    TextView txtDeliveryData;
    TextView txtDeliveryTime;
    TextView txtOrderAmount;
    TextView txtOrderDate;
    TextView txtOrderId;
    TextView txtPacked;
    TextView txtPaybleAmount;
    TextView txtShippingAmount;
    public static CategoryData categoryDate;
    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    String OrderId;
    AVLoadingIndicatorView progress;

    class C08213 implements Listener<JSONObject> {
        C08213() {
        }

        public void onResponse(JSONObject jsonObject) {
            OrderDetailActivity.this.GetOrderVariantsAction(ParseDataProvider.getInstance(StartActivity.context).OrderVariant(jsonObject));
            Log.e("Response",jsonObject+"");
        }
    }

    class C08224 implements ErrorListener {
        C08224() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                volleyError = new VolleyError(new String(volleyError.networkResponse.data));
            }
        }
    }

    class C08235 implements Listener<JSONObject> {
        C08235() {
        }

        public void onResponse(JSONObject jsonObject) {
            OrderDetailActivity.this.CancelOrderAction(ParseDataProvider.getInstance(StartActivity.context).IsSuccess(jsonObject));
            Log.e("Response",jsonObject+"");
        }
    }

    class C08246 implements ErrorListener {
        C08246() {
        }

        public void onErrorResponse(VolleyError volleyError) {
            if (!(volleyError.networkResponse == null || volleyError.networkResponse.data == null)) {
                volleyError = new VolleyError(new String(volleyError.networkResponse.data));
            }
            progress.setVisibility(View.GONE);
        }
    }

    static {
        TAG = OrderDetailActivity.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_detail);
        try {
            this.context = this;
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);
            this.orderDetail = (OrderDetail) getIntent().getExtras().getSerializable("OrderDetail");
            categoryDate = (CategoryData) getIntent().getExtras().getSerializable("CategoryData");
            mJsonParser = new MyJSONParser();

            OrderId = this.orderDetail.getOrderId();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("My Order");

            LoadContext();
            loatData();

            new JSONParse().execute();


        } catch (Exception ex) {
           // Log.e(TAG, ex.getMessage());

        }
    }

    public void LoadContext() {
        this.txtOrderId = (TextView) findViewById(R.id.txt_order_id_value);
        this.txtOrderDate = (TextView) findViewById(R.id.txt_order_date_value);
        this.txtOrderAmount = (TextView) findViewById(R.id.txt_order_amount_value);
        this.txtAmount = (TextView) findViewById(R.id.txt_place_payable_total_value);
        this.txtShippingAmount = (TextView) findViewById(R.id.txt_place_payable_shipping_value);
        this.txtPaybleAmount = (TextView) findViewById(R.id.txt_place_payable_total_amout_value);
        this.txtDeliveryData = (TextView) findViewById(R.id.txt_order_detail_item_delivery_date_value);
        this.txtDeliveryTime = (TextView) findViewById(R.id.txt_order_detail_item_delivery_time);
        this.txtPacked = (TextView) findViewById(R.id.txt_order_detail_packed);
        this.txtDelivery = (TextView) findViewById(R.id.txt_order_detail_delivering);
        this.txtDelivered = (TextView) findViewById(R.id.txt_order_detail_delivered);
        this.txtCanceled = (TextView) findViewById(R.id.txt_order_detail_canceled);
        this.cancelOrder = (RelativeLayout) findViewById(R.id.lyt_cancel_order);
        this.lstOrderVariantList = (ListView) findViewById(R.id.lst_order_variantes);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);

    }

    public void loatData() {
        //float pAmount = Float.parseFloat(this.orderDetail.getOrderAmount()) + Float.parseFloat(this.orderDetail.getShippingAmount());
        this.txtOrderId.setText(this.orderDetail.getOrderId());
        this.txtOrderDate.setText(this.orderDetail.getOrderDate());
        this.txtOrderAmount.setText(this.orderDetail.getOrderAmount());
        this.txtAmount.setText(this.orderDetail.getOrderAmount());
        this.txtPaybleAmount.setText(this.orderDetail.getOrderAmount());

    }
    private class JSONParse extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


           // Utills.showDialog(context);
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();


                params.put("order_id", OrderId);

                jsonObject_parent = mJsonParser.postData("http://mptechnolabs.com/1reward/order_detail.php", params);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           // Utills.dismissDialog();
            progress.setVisibility(View.GONE);

            OrderDetailActivity.this.GetOrderVariantsAction(ParseDataProvider.getInstance(StartActivity.context).OrderVariant(jsonObject_parent));

        }
    }

    private void GetOrderVariants() {
        Map<String, String> params = new HashMap();
        params.put("order_id", this.orderDetail.getOrderId());
        Volley.newRequestQueue(StartActivity.context).add(new VolleyHelper(1, ApiHelper.orderVariants, new JSONObject(params), new C08213(), new C08224()));
    }

    protected void GetOrderVariantsAction(List<OrderVariant> orderVariant) {
        if (orderVariant != null) {
            this.lstOrderVariantList.setAdapter(new OrderVariantAdapter(this.context, orderVariant));
        }
    }

    public void CancelOrder(View view) {
        //this.progress = ProgressDialog.show(this.context, null, "please wait", true);
        progress.setVisibility(View.VISIBLE);
        CancelOrder();
    }

    private void CancelOrder() {
        Map<String, String> params = new HashMap();
        params.put("order_id", this.orderDetail.getOrderId());
        RequestQueue requestQueue = Volley.newRequestQueue(StartActivity.context);
        VolleyHelper jsObjRequest = new VolleyHelper(1, ApiHelper.orderCancel,  new JSONObject(params), new C08235(), new C08246());
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(ApiHelper.MY_SOCKET_TIMEOUT_MS, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);
    }

    protected void CancelOrderAction(boolean isSuccess) {
      //  this.progress.dismiss();
        progress.setVisibility(View.VISIBLE);
        if (isSuccess) {
            Toast.makeText(this.context, "Order Cancel Successfully", Toast.LENGTH_SHORT).show();
            this.cancelOrder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MyOrdersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CategoryData", categoryDate);
                intent.putExtras(bundle);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyOrdersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("CategoryData", categoryDate);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);

        startActivity(intent);
        finish();
    }
}
