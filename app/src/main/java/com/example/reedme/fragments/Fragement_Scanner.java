package com.example.reedme.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.activity.MerchantUserQR;
import com.example.reedme.activity.StartActivity;
import com.example.reedme.activity.activity_mystore;
import com.example.reedme.barcodescanner.BarcodeCallback;
import com.example.reedme.barcodescanner.BarcodeResult;
import com.example.reedme.barcodescanner.CaptureManager;
import com.example.reedme.barcodescanner.CompoundBarcodeView;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.model.UserDetail;
import com.example.reedme.model.UserDetailList;
import com.google.zxing.ResultPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ubuntu on 10/6/16.
 */
public class Fragement_Scanner extends Fragment {
    private static final String TAG = Fragement_Scanner.class.getSimpleName();

    private String toast;
    private CompoundBarcodeView barcodeScannerView;
    private String Userurl= "http://mptechnolabs.com/1reward/getUserdetail.php";
    JSONObject jsonObject_parent = null;
    public static MyJSONParser mJsonParser = null;
    String resultQR;
    RelativeLayout parentPanel;

    public Fragement_Scanner() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        displayToast();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);


        mJsonParser = new MyJSONParser();

        parentPanel = (RelativeLayout) view.findViewById(R.id.parentPanel);
        barcodeScannerView = (CompoundBarcodeView)view.findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.decodeContinuous(callback);
        barcodeScannerView.resume();


        return view;
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                barcodeScannerView.setStatusText(result.getText());

                try
                {
                    resultQR = result.getText();
                    barcodeScannerView.pause();
                    new JSONParse().execute();

                }catch (Exception e)
                {
                    Log.e("Exception",e+"");
                }


            }
            //Added preview of scanned barcode
            /*ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));*/
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };


    private void displayToast() {
        if(getActivity() != null && toast != null) {
            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
            toast = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        barcodeScannerView.resume();
    }
    @Override
    public void onPause() {
        super.onPause();

        barcodeScannerView.pause();
    }

    private class JSONParse extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();


                params.put("qr_number", resultQR);

                jsonObject_parent = mJsonParser.postData(Userurl, params);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            UserListCallAction(ParseDataProvider.getInstance(getActivity()).UserQRData(jsonObject_parent));

        }
    }
    protected void UserListCallAction(UserDetailList UserList) {
        if (UserList != null) {

            Intent i = new Intent(getActivity(), MerchantUserQR.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("UserDetail", UserList);
            i.putExtras(bundle);
            getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
            startActivity(i);


        }
        else
        {
            Snackbar.make(parentPanel, "Can't Identify User Please Re-Scan QR code Again.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            barcodeScannerView.resume();
        }


    }



}