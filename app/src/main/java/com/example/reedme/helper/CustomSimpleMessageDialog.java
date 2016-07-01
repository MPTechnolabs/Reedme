package com.example.reedme.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reedme.R;


/**
 * Created by Rahul on 2/6/15.
 */
public class CustomSimpleMessageDialog extends Dialog {

    private String dialog_title, dialog_messege;

    private boolean flag_for_purpose;

    private Context context;

    public CustomSimpleMessageDialog(Context context, SimpleDialogOnClickListener myClick, String title_dialog, String message_dialog, boolean flag_for_purpose) {
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

    public SimpleDialogOnClickListener myListener;

    // This is my interface //
    public interface SimpleDialogOnClickListener {
        void onOkayButtonClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialoug_success);

        this.setCanceledOnTouchOutside(false);

        ImageView iv_Success = (ImageView)findViewById(R.id.iv_success);
        TextView txt_Success = (TextView)findViewById(R.id.txt_success);
        Button btn_ok = (Button) findViewById(R.id.btn_ok);

        if (flag_for_purpose == true){

            iv_Success.setImageResource(R.drawable.success_icon);
            txt_Success.setText("Success");
            btn_ok.setBackgroundResource(R.drawable.successbuttonshape);
            btn_ok.setTextColor(Color.WHITE);
        }

        /// Setup Font Typeface
//        Utills.setupFont(context, btn_ok, Constants.ProximaNovaRegularFonts);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                myListener.onOkayButtonClick(); // I am giving the click to the
                // interface function which we need
                // to implements where we call this
                // class
            }
        });

        TextView txt_title_dialog = (TextView) findViewById(R.id.txt_title_dialog);
        /// Setup Font
        // Utills.setupFont(context, txt_title_dialog, Constants.ProximaNovaRegularFonts);
        txt_title_dialog.setText(dialog_title + "");
        TextView txt_message_dialog = (TextView) findViewById(R.id.txt_message_dialog);
        /// Setup Font
        // Utills.setupFont(context, txt_message_dialog, Constants.ProximaNovaRegularFonts);
        txt_message_dialog.setText(dialog_messege + "");

    }


}
