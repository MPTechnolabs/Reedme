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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.reedme.R;
import com.example.reedme.adapter.OrderItemAdapter;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.ApiHelper;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Helper;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Utills;
import com.example.reedme.helper.VolleyHelper;
import com.example.reedme.model.CategoryData;
import com.example.reedme.model.OrderDetail;
import com.example.reedme.views.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOrdersActivity extends AppCompatActivity {
    public static final String TAG;
    List<OrderDetail> myOrderList;
    ListView myOrderListView;
    public static CategoryData categoryDate;
    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    Context context;
    AVLoadingIndicatorView progress;
    TextView txt_message;



    class C04845 implements OnItemClickListener {
        private final /* synthetic */ List val$orderList;

        C04845(List list) {
            this.val$orderList = list;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(MyOrdersActivity.this, OrderDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("OrderDetail", (Serializable) this.val$orderList.get(position));
            bundle.putSerializable("CategoryData", categoryDate);

            intent.putExtras(bundle);
            MyOrdersActivity.this.startActivity(intent);
        }
    }


    static
    {
        TAG = MyOrdersActivity.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_orders);
        try {
            this.context = this;
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);
            categoryDate = (CategoryData) getIntent().getExtras().getSerializable("CategoryData");
            mJsonParser = new MyJSONParser();


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("My Order");
            LoatData();
            GetOrderList();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


    private void LoatData() {
        this.myOrderListView = (ListView) findViewById(R.id.lst_my_order);
        txt_message = (TextView) findViewById(R.id.txt_message);

        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);

    }

    private void GetOrderList() {
            new JSONParse().execute();

                }
        //startActivity(new Intent(this, LoginActivity.class));


    private class JSONParse extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Utills.showDialog(context);
            progress.setVisibility(View.VISIBLE);


        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();


                params.put("user_id", AppPrefs.getAppPrefs(StartActivity.context).getString(MyOrdersActivity.this.getResources().getString(R.string.user_id)));

                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/order_history.php", params);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           // Utills.dismissDialog();
            progress.setVisibility(View.GONE);

            MyOrdersActivity.this.OrderListCallAction(ParseDataProvider.getInstance(StartActivity.context).OrderData(jsonObject_parent));

        }
    }



    protected void OrderListCallAction(List<OrderDetail> orderList) {
        if (orderList != null) {
            this.myOrderListView.setAdapter(new OrderItemAdapter(this.context, orderList));
            this.myOrderListView.setOnItemClickListener(new C04845(orderList));
            ((OrderItemAdapter) this.myOrderListView.getAdapter()).notifyDataSetChanged();
        }
        else
        {
            myOrderListView.setVisibility(View.GONE);
            txt_message.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, StartActivity.class);
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
        Intent intent = new Intent(this, StartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("CategoryData", categoryDate);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
        startActivity(intent);
        finish();
    }
}
