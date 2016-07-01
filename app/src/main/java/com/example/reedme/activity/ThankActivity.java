package com.example.reedme.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.example.reedme.R;

public class ThankActivity extends Activity {
    public static final String TAG;
    Context context;
    int orderId;
    TextView txtOrderId;

    /* renamed from: com.vegies.app.ThankActivity.1 */
    class C04991 implements OnClickListener {
        C04991() {
        }

        public void onClick(View view) {
            ThankActivity.this.finish();
            ThankActivity.this.overridePendingTransition(R.anim.inner_to_left, R.anim.left_to_out);
        }
    }

    /* renamed from: com.vegies.app.ThankActivity.2 */
    class C05002 implements OnClickListener {
        C05002() {
        }

        public void onClick(View v) {
            ThankActivity.this.startActivity(new Intent(ThankActivity.this, SearchActivity.class));
            ThankActivity.this.finish();
        }
    }

    static {
        TAG = ThankActivity.class.getSimpleName();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thank);
        try {
            this.context = this;
            overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);
         //   this.orderId = getIntent().getExtras().getInt("OrderId");
            LoadContext();
            LoadData();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


    private void LoadContext() {
        this.txtOrderId = (TextView) findViewById(R.id.txt_place_payable_total_amout_value);
    }

    public void LoadData() {
        this.txtOrderId.setText(String.valueOf(this.orderId));
    }

    public void TrackOrder(View view) {
        startActivity(new Intent(this, MyOrdersActivity.class));
        finish();
    }

    public void Shopping(View view) {
        finish();
        overridePendingTransition(R.anim.inner_to_left, R.anim.left_to_out);
    }

    public void ProceedCheckOut(View view) {
        startActivity(new Intent(this, CheckoutPlaceOrderActivity.class));
        finish();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.inner_to_left, R.anim.left_to_out);
    }
}
