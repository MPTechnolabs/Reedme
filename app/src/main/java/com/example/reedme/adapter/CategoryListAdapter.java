package com.example.reedme.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.helper.ApiHelper;
import com.example.reedme.helper.Util;
import com.example.reedme.model.CategoryItem;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryListAdapter extends BaseAdapter {
    static final /* synthetic */ boolean $assertionsDisabled;
    CategoryItem categoryItem;
    Context context;
    List<CategoryItem> itemList;

    static class ViewHolder {
        ImageView itemIcon;
        ImageView itemImage;
        TextView itemName;

        ViewHolder() {
        }
    }

    static {
        $assertionsDisabled = !CategoryListAdapter.class.desiredAssertionStatus();
    }

    public CategoryListAdapter(Context context, List<CategoryItem> listItems) {
        this.context = context;
        this.itemList = listItems;
    }

    public int getCount() {
        return this.itemList.size();
    }

    public Object getItem(int position) {
        return this.itemList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);

                view = layoutInflater.inflate(R.layout.category_list_item, null);

            holder = new ViewHolder();
            if ($assertionsDisabled || view != null) {
                holder.itemName = (TextView) view.findViewById(R.id.txt_category_item_name);
                holder.itemImage = (ImageView) view.findViewById(R.id.img_category_item);
              //  holder.floatingActionButton = (FloatingActionButton) view.findViewById(R.id.pink_icon);
                //holder.itemIcon = (ImageView) view.findViewById(R.id.img_category_name_item);
                view.setTag(holder);
            } else {
                throw new AssertionError();
            }
        }
        holder = (ViewHolder) view.getTag();
        this.categoryItem = this.itemList.get(position);
        holder.itemName.setText(this.categoryItem.getName());

          Picasso.with(this.context).load("http://www.mptechnolabs.com/1reward/upload/" + this.categoryItem.getImage()).placeholder((int) R.mipmap.logo).into(holder.itemImage);
       /* holder.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
      //  Picasso.with(this.context).load(ApiHelper.imageUrl + this.categoryItem.getImage()).placeholder((int) R.mipmap.mystore).into(holder.itemImage);
        //Picasso.with(this.context).load(ApiHelper.imageUrl + this.categoryItem.getIcon()).placeholder((int) R.mipmap.app_icon).into(holder.itemIcon);

        return view;
    }
}
