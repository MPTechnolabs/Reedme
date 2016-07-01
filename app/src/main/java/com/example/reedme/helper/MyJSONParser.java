package com.example.reedme.helper;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Jolly Raiyani on 4/8/2016.
 */
public class MyJSONParser {

    public MyJSONParser() {

    }

    public JSONObject postData(String url, JSONObject obj) {

        Log.i("URL===>", url);
        Log.i("parameter===>", obj.toString());
        // Create a new HttpClient and Post Header

        JSONObject jObj_responce = null;

        HttpParams myParams = new BasicHttpParams();
        // HttpConnectionParams.setConnectionTimeout(myParams, 10000);
        // HttpConnectionParams.setSoTimeout(myParams, 10000);
        HttpClient httpclient = new DefaultHttpClient(myParams);
        // String json = obj.toString();

        try {

            HttpPost httppost = new HttpPost(url.toString());
            httppost.setHeader("Content-type", "application/json");

            StringEntity se = new StringEntity(obj.toString());
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            httppost.setEntity(se);

            HttpResponse response = httpclient.execute(httppost);
            String temp = EntityUtils.toString(response.getEntity());
            Log.i("tag responce", temp);

            // try parse the string to a JSON object
            try {
                jObj_responce = new JSONObject(temp.toString());
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
        } catch (ClientProtocolException e) {

        } catch (IOException e) {
        }

        // return JSON String
        return jObj_responce;
    }

}