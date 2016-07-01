package com.example.reedme.views;

import android.support.annotation.MenuRes;
import android.support.v7.widget.PopupMenu;
import android.util.Log;

import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class SweetSheet {

    public static final String Tag="SweetSheet";
    public enum Type{
        RecyclerView,Viewpager,Custom
    }


    private ViewGroup mParentVG;
    private Delegate mDelegate;
    private Effect mEffect=new NoneEffect();
    private boolean mIsBgClickEnable=true;


    public SweetSheet(RelativeLayout parentVG) {
        mParentVG = parentVG;
    }

    public SweetSheet(FrameLayout parentVG) {
        mParentVG = parentVG;
    }



    public SweetSheet(ViewGroup parentVG) {

        if (parentVG instanceof FrameLayout || parentVG instanceof RelativeLayout) {

        } else {
            throw new IllegalStateException("ViewGroup  must FrameLayout or  RelativeLayout.");
        }
        mParentVG = parentVG;

    }


    public void setDelegate(Delegate delegate){
        mDelegate=delegate;
        mDelegate.init(mParentVG);
        setup();

    }

    public Delegate getDelegate() {
        return mDelegate;
    }


    private void setup() {


        mDelegate.setBackgroundEffect(mEffect);
        mDelegate.setBackgroundClickEnable(mIsBgClickEnable);
    }


    public void setBackgroundClickEnable(boolean isBgClickEnable){
        if(mDelegate != null){
            mDelegate.setBackgroundClickEnable(isBgClickEnable);
        }else{
            mIsBgClickEnable=isBgClickEnable;
        }
    }


    public void setBackgroundEffect(Effect effect) {

        if(mDelegate != null) {
            mDelegate.setBackgroundEffect(effect);
        }else{
            mEffect=effect;
        }
    }





    public void show() {

        if(mDelegate != null) {

            mDelegate.show();
        }else{
            Log.e(Tag,"you must setDelegate before");
        }
    }

    public void dismiss() {
        if(mDelegate != null) {

            mDelegate.dismiss();
        }else{
            Log.e(Tag,"you must setDelegate before");
        }

    }

    public void toggle() {
        if(mDelegate != null) {

            mDelegate.toggle();
        }else{
            Log.e(Tag,"you must setDelegate before");
        }

    }

    public boolean isShow() {

        if(mDelegate == null) {

           return  false;
        }
        if (mDelegate.getStatus() == Status.SHOW || mDelegate.getStatus() == Status.SHOWING) {
            return true;
        }
        return false;
    }


    protected enum Status {

        SHOW, SHOWING,
        DISMISS, DISMISSING
    }


}
