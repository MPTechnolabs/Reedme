package com.example.reedme.helper;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyHelper extends Request<JSONObject> {
    private Listener<JSONObject> listener;
    public JSONObject params;

    public VolleyHelper(String url, JSONObject params, Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(0, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    public VolleyHelper(int method, String url, JSONObject params, Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }



    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers))), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Throwable e) {
            return Response.error(new ParseError(e));
        }
    }

    protected void deliverResponse(JSONObject response) {
        this.listener.onResponse(response);
    }
}
