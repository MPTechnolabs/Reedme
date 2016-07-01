package com.example.reedme.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.reedme.R;
import com.example.reedme.model.CategoryItem;
import com.example.reedme.model.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryHListAdapter extends PagerAdapter {
    CategoryItem categoryItem;
    Context context;
    List<SliderItem> itemList;
    LayoutInflater mLayoutInflater;
    private ArrayList<Integer> IMAGES;

    static class ViewHolder {
        ImageView itemImage;
        SliderItem sliderItem;

        ViewHolder() {
        }
    }

    public CategoryHListAdapter(Context context, ArrayList<Integer> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return IMAGES.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder = new ViewHolder();
        View itemView = this.mLayoutInflater.inflate(R.layout.category_h_list_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_category_h_item);
        imageView.setImageResource(IMAGES.get(position));

        //holder.sliderItem = (SliderItem) this.itemList.get(position);
        //Picasso.with(this.context).load(ApiHelper.sliderImageUrl + holder.sliderItem.getImageUrl()).placeholder((int) R.mipmap.app_icon).into(imageView);
        container.addView(itemView, 0);
        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
    @Override
    public Parcelable saveState() {
        return null;
    }
}
