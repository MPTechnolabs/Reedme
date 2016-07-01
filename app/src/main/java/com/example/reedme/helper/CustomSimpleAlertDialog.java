package com.example.reedme.helper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.reedme.R;


/**
 * Created by Rahul on 2/6/15.
 */
public class CustomSimpleAlertDialog extends Dialog {

    private String dialog_title, dialog_messege;

    private boolean flag_for_purpose;

    private Context context;

    public CustomSimpleAlertDialog(Context context, SimpleDialogOnClickListener1 myClick, String title_dialog, String message_dialog, boolean flag_for_purpose) {
        super(context);

        this.context = context;
        this.myListener = myClick;
        this.dialog_title = title_dialog;
        this.dialog_messege = message_dialog;
        this.flag_for_purpose = flag_for_purpose;
    }

    public String getDialog_title() {

        return dialog_title;
    }

    public void setDialog_title(String dialog_title) {

        this.dialog_title = dialog_title;
    }

    public String getDialog_messege() {
        return dialog_messege;
    }

    public void setDialog_messege(String dialog_messege) {
        this.dialog_messege = dialog_messege;
    }

    public SimpleDialogOnClickListener1 myListener;

    // This is my interface //
    public interface SimpleDialogOnClickListener1 {
        void onOkayButtonClick();

        void onCancelButtonClick();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_simple_alert_dialog_layout);

        this.setCanceledOnTouchOutside(false);

        Button btn_ok = (Button) findViewById(R.id.btn_ok);

        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);


        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                myListener.onOkayButtonClick(); // I am giving the click to the
                // interface function which we need
                // to implements where we call this
                // class

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();

            }
        });

        TextView txt_title_dialog = (TextView) findViewById(R.id.txt_title_dialog);

        TextView txt_msg_dialog = (TextView) findViewById(R.id.txt_message_dialog);


        txt_title_dialog.setText(dialog_title + "");

        txt_msg_dialog.setText(dialog_messege + "");


    }


}
