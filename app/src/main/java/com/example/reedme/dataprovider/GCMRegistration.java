package com.example.reedme.dataprovider;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.example.reedme.R;
import com.example.reedme.helper.GCMPrefs;
import com.example.reedme.helper.Util;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class GCMRegistration {
    static final String TAG = "GCMRegistration";
    public static GCMRegistration gcmRgistration;
    Context context;
    GoogleCloudMessaging gcm;
    public static String regId;

    /* renamed from: com.vegies.app.dataprovider.GCMRegistration.1 */
   /* class C05121 extends AsyncTask<Void, Void, String> {
        C05121() {
        }

        protected String doInBackground(Void... params) {
            String msg = "";
            try {
                if (GCMRegistration.this.gcm == null) {
                    GCMRegistration.this.gcm = GoogleCloudMessaging.getInstance(GCMRegistration.this.context);
                }
                GCMRegistration.this.regId = GCMRegistration.this.gcm.register(GCMRegistration.this.context.getString(R.string.SENDER_ID));
                Log.d("RegisterActivity", "registerInBackground - regId: " + GCMRegistration.this.regId);
                msg = "Device registered, registration ID=" + GCMRegistration.this.regId;
                GCMRegistration.this.storeRegistrationId(GCMRegistration.this.context, GCMRegistration.this.regId);
            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
                Log.d("RegisterActivity", "Error: " + msg);
            }
            Log.d("RegisterActivity", "AsyncTask completed: " + msg);
            return msg;
        }

        protected void onPostExecute(String msg) {
            Log.d("Registration", "Registered with GCM Server.");
        }
    }
*/
    public GCMRegistration(Context context) {
        this.context = context;
    }

    public static GCMRegistration getInstance(Context context) {
        if (gcmRgistration == null) {
            gcmRgistration = new GCMRegistration(context);
        }
        return gcmRgistration;
    }

    public String Init() {
        if (TextUtils.isEmpty(this.regId)) {
            this.regId = registerGCM();
            Log.d("RegisterActivity", "GCM RegId: " + this.regId);
        } else {
            Log.d("RegisterActivity", "Already Registered with GCM Server! " + this.regId);
        }
        return this.regId;
    }

    public String registerGCM() {
        this.gcm = GoogleCloudMessaging.getInstance(this.context);
        this.regId = getRegistrationId(this.context);
        if (TextUtils.isEmpty(this.regId)) {
            registerInBackground();
            Log.d(TAG, "registerGCM - successfully registered with GCM server - regId: " + this.regId);
        }
        return this.regId;
    }

    private String getRegistrationId(Context context) {
        String registrationId = GCMPrefs.getGCMPrefs(context).getRegistrationKey();
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        } else if (GCMPrefs.getGCMPrefs(context).getRegistrationAppVersion() == Util.getInstance(context).getAppVersion()) {
            return registrationId;
        } else {
            Log.i(TAG, "App version changed.");
            return "";
        }
    }

    private void registerInBackground() {
        /*new C05121().execute(new Void[]{null, null, null});*/
    }

    private void storeRegistrationId(Context context, String regId) {
        GCMPrefs.getGCMPrefs(context).setRegistrationAppVersion(Util.getInstance(context).getAppVersion());
        GCMPrefs.getGCMPrefs(context).setRegistrationKey(regId);
    }
}
