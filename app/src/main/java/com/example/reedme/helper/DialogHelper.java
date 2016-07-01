package com.example.reedme.helper;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.activity.Activity_login;
import com.example.reedme.activity.StartActivity;


public class DialogHelper {
    private static DialogHelper dialogHelper;
    Context context;

    /* renamed from: com.vegies.app.helper.DialogHelper.1 */


    /* renamed from: com.vegies.app.helper.DialogHelper.2 */
    class C05202 implements OnClickListener {
        C05202() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
        }
    }
    class C05191 implements OnClickListener {
        C05191() {
        }

        public void onClick(DialogInterface dialog, int id) {
            AppPrefs.getAppPrefs(DialogHelper.this.context).setIsLogin(false);

            Intent i =new Intent(DialogHelper.this.context, Activity_login.class);
            DialogHelper.this.context.startActivity(i);
            dialog.cancel();


        }

    }

    public void logoutDialog() {
        Builder alertDialogBuilder = new Builder(StartActivity.context);
        alertDialogBuilder.setMessage(R.string.logout_message).setCancelable(false).setPositiveButton("Yes", new C05191()).setNegativeButton("No, thanks", new C05202());
        alertDialogBuilder.create().show();
    }


    /* renamed from: com.vegies.app.helper.DialogHelper.3 */
    class C05213 implements OnClickListener {
        C05213() {
        }

        public void onClick(DialogInterface dialog, int id) {
            DialogHelper.this.UpdateApp();
        }
    }

    public static DialogHelper getInstance(Context context) {
        if (dialogHelper == null) {
            dialogHelper = new DialogHelper(context);
        }
        return dialogHelper;
    }

    public DialogHelper(Context context) {
        this.context = context;
    }


    public Builder AppUpdateDialog() {
        Builder alertDialogBuilder = new Builder(this.context);
        alertDialogBuilder.setTitle("Update available");
        alertDialogBuilder.setMessage("A new update of Vegie's is available, Please update");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Update Now", new C05213());
        return alertDialogBuilder;
    }

    public void UpdateApp() {
        try {
            this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ApiHelper.appPath)));
        } catch (Exception e) {
            Toast.makeText(this.context.getApplicationContext(), "Unable to Connect Try Again...", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void AppShare() {
        Intent sendIntent = new Intent();
        sendIntent.setAction("android.intent.action.SEND");
        sendIntent.putExtra("android.intent.extra.TEXT", ApiHelper.appPath);
        sendIntent.setType("text/plain");
        this.context.startActivity(sendIntent);
    }
}
