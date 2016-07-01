package com.example.reedme.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.reedme.R;


public class GCMPrefs {
    private static final String PREFERENCE = "VEGIES_APP_GCM";
    private static GCMPrefs gcmPrefs;
    Context context;
    private SharedPreferences gcmSharedPrefs;
    private Editor prefsEditor;

    public GCMPrefs(Context context) {
        this.context = context;
        this.gcmSharedPrefs = context.getSharedPreferences(PREFERENCE, 0);
        this.prefsEditor = this.gcmSharedPrefs.edit();
    }

    public static GCMPrefs getGCMPrefs(Context context) {
        if (gcmPrefs == null) {
            gcmPrefs = new GCMPrefs(context);
        }
        return gcmPrefs;
    }

    public void commit() {
        this.prefsEditor.commit();
    }

    public void setRegistrationKey(String value) {
        this.prefsEditor.putString(this.context.getResources().getString(R.string.prefs_gcm_reg_id), value).commit();
    }

    public String getRegistrationKey() {
        return this.gcmSharedPrefs.getString(this.context.getResources().getString(R.string.prefs_gcm_reg_id), "");
    }

    public void setRegistrationAppVersion(int appVersion) {
        this.prefsEditor.putInt(this.context.getResources().getString(R.string.prefs_app_version), appVersion).commit();
    }

    public int getRegistrationAppVersion() {
        return this.gcmSharedPrefs.getInt(this.context.getResources().getString(R.string.prefs_app_version), 0);
    }
}
