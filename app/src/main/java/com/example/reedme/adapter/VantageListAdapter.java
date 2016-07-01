package com.example.reedme.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.reedme.R;
import com.example.reedme.activity.StartActivity;
import com.example.reedme.helper.ApiHelper;
import com.example.reedme.model.Vantage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VantageListAdapter extends PagerAdapter {
    LayoutInflater mLayoutInflater;
    List<Vantage> vantageList;

    public VantageListAdapter(List<Vantage> listItems) {
        this.vantageList = listItems;
        this.mLayoutInflater = (LayoutInflater) StartActivity.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.vantageList.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = this.mLayoutInflater.inflate(R.layout.vantage_list_item, container, false);
        Picasso.with(StartActivity.context).load(((Vantage) this.vantageList.get(position)).getVantageImage()).placeholder((int) R.mipmap.mystore).into((ImageView) itemView.findViewById(R.id.img_category_h_item));
        container.addView(itemView);
        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
