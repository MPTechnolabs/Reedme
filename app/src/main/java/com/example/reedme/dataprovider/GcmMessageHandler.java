package com.example.reedme.dataprovider;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.reedme.R;
import com.example.reedme.activity.SplashActivity;
import com.example.reedme.adapter.DBAdapter;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

public class GcmMessageHandler extends IntentService {
    private Handler handler;
    String msg;
    String title;

    /* renamed from: com.vegies.app.dataprovider.GcmMessageHandler.1 */
    class C05131 implements Runnable {
        C05131() {
        }

        public void run() {
            Intent notificationIntent;
            Context applicationContext = GcmMessageHandler.this.getApplicationContext();
            GcmMessageHandler.this.getApplicationContext();
            ComponentName rootActivity = ((ActivityManager) applicationContext.getSystemService(ACTIVITY_SERVICE)).getRunningTasks(1).get(0).baseActivity;
            if (rootActivity.getPackageName().equalsIgnoreCase("com.example.reedme")) {
                notificationIntent = new Intent();
                notificationIntent.setComponent(rootActivity);
            } else {
                notificationIntent = new Intent(GcmMessageHandler.this.getApplicationContext(), SplashActivity.class);
            }
            ((NotificationManager) GcmMessageHandler.this.getSystemService(NOTIFICATION_SERVICE)).notify(1, new Builder(GcmMessageHandler.this.getApplicationContext()).setContentIntent(PendingIntent.getActivity(GcmMessageHandler.this.getApplicationContext(), 0, notificationIntent, 0)).setContentTitle(GcmMessageHandler.this.title).setContentText(GcmMessageHandler.this.msg).setTicker("Vegies").setWhen(System.currentTimeMillis()).setDefaults(1).setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher).build());
        }
    }

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    public void onCreate() {
        super.onCreate();
        this.handler = new Handler();
    }

    protected void onHandleIntent(Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            String messageType = GoogleCloudMessaging.getInstance(this).getMessageType(intent);
            JSONObject object = new JSONObject(extras.getString(DBAdapter.KEY_MESSAGE));
            this.msg = object.getString(DBAdapter.KEY_MESSAGE);
            this.title = object.getString(DBAdapter.KEY_TITLE);
            showToast();
            Log.i(GoogleCloudMessaging.INSTANCE_ID_SCOPE, "Received : (" + messageType + ")  " + this.msg);
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        } catch (Exception e) {
        }
    }

    private void saveNotification(String title, String message) {
        DBAdapter db = new DBAdapter(this);
        db.open();
        long id = db.insertNotification(title, message);
        db.close();
    }

    public void showToast() {
        this.handler.post(new C05131());
    }
}
