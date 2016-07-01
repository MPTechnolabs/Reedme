package com.example.reedme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.MaterialFavoriteButton;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.model.CategoryItem1;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Android on 5/19/2016.
 */
public class ReedMeWalletAdapter extends BaseAdapter {

    static final /* synthetic */ boolean $assertionsDisabled;
    CategoryItem1 categoryItem;
    Context context;
    List<CategoryItem1> itemList;
    String id;
    String store_id;
    static class ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemDiscount;
        TextView wallet;




        ViewHolder() {
        }
    }

    static {
        $assertionsDisabled = !CategoryListAdapter.class.desiredAssertionStatus();
    }

    public ReedMeWalletAdapter(Context context, List<CategoryItem1> listItems) {
        this.context = context;
        this.itemList = listItems;
        id = AppPrefs.getAppPrefs(this.context).getString("user_id");
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);

            view = layoutInflater.inflate(R.layout.reedme_listitem, null);

            holder = new ViewHolder();
            if ($assertionsDisabled || view != null) {
                holder.itemName = (TextView) view.findViewById(R.id.storeName);
                holder.itemImage = (ImageView) view.findViewById(R.id.img_category_item);
                holder.itemDiscount = (TextView) view.findViewById(R.id.storeDiscount);
                holder.wallet = (TextView) view.findViewById(R.id.walletPoint);

                view.setTag(holder);
            } else {
                throw new AssertionError();
            }
        }
        holder = (ViewHolder) view.getTag();
        this.categoryItem = this.itemList.get(position);

        holder.itemName.setText(this.categoryItem.getName());
        holder.itemDiscount.setText("Discount : " +this.categoryItem.getDiscount()+"%");
        holder.wallet.setText("Rs. "+this.categoryItem.getWalletPoint());

        Picasso.with(this.context).load(this.categoryItem.getImage()).placeholder((int) R.mipmap.logo).into(holder.itemImage);


        return view;
    }
}
