package com.example.reedme.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reedme.R;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.MaterialFavoriteButton;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.CategoryItem;
import com.example.reedme.model.CategoryItem1;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jolly Raiyani on 4/13/2016.
 */
public class CategoryListAdapter1 extends BaseAdapter {
    static final /* synthetic */ boolean $assertionsDisabled;
    CategoryItem1 categoryItem;
    Context context;
    List<CategoryItem1> itemList;
    ArrayList<CategoryItem1> arraylist;

    String id;
    String store_id;
    String favourite = "0";

    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;

    static class ViewHolder {
        ImageView itemImage;
        TextView itemName;
        MaterialFavoriteButton materialFavoriteButtonNice;
        TextView Store_id;
        ImageView img_cashback;
        TextView txt_cashback,txt_cashback1;
        TextView favflag;




        ViewHolder() {
        }
    }

    static {
        $assertionsDisabled = !CategoryListAdapter.class.desiredAssertionStatus();
    }

    public CategoryListAdapter1(Context context, List<CategoryItem1> listItems) {
        this.context = context;
        this.itemList = listItems;
        arraylist = new ArrayList<CategoryItem1>();
        arraylist.addAll(itemList);

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

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);

            view = layoutInflater.inflate(R.layout.activity_storelistitem, null);
            mJsonParser = new MyJSONParser();

            holder = new ViewHolder();
            if ($assertionsDisabled || view != null) {
                holder.Store_id= (TextView) view.findViewById(R.id.store_id);
                holder.itemName = (TextView) view.findViewById(R.id.txt_category_item_name);
                holder.itemImage = (ImageView) view.findViewById(R.id.img_category_item);
                holder.img_cashback = (ImageView) view.findViewById(R.id.img_cashback);
                holder.txt_cashback = (TextView) view.findViewById(R.id.txt_cashback);
                holder.txt_cashback1 = (TextView) view.findViewById(R.id.txt_cashback1);
                holder.favflag = (TextView) view.findViewById(R.id.favflag);

                holder.materialFavoriteButtonNice =
                        (MaterialFavoriteButton) view.findViewById(R.id.favorite_nice);

                view.setTag(holder);
            } else {
                throw new AssertionError();
            }
        }
        holder = (ViewHolder) view.getTag();
        this.categoryItem = this.itemList.get(position);
        holder.Store_id.setText(this.categoryItem.getId());

        holder.itemName.setText(this.categoryItem.getName());
        holder.favflag.setText(this.categoryItem.fav());

        if(this.categoryItem.getDiscount() != null )
        {
            holder.img_cashback.setVisibility(View.VISIBLE);
            holder.txt_cashback.setText(this.categoryItem.getDiscount()+"%");
            holder.txt_cashback1.setText("Off");
        }
        else{
            holder.img_cashback.setVisibility(View.INVISIBLE);
            holder.txt_cashback1.setText("");
            holder.txt_cashback.setVisibility(View.INVISIBLE);
        }

        Picasso.with(this.context).load(this.categoryItem.getImage()).placeholder((int) R.mipmap.logo).into(holder.itemImage);

        if(holder.favflag.getText().toString().equals("1")) {
            holder.materialFavoriteButtonNice.setFavorite(true, false);
        }
        else
        {
            holder.materialFavoriteButtonNice.setFavorite(false, false);

        }

        final ViewHolder finalHolder = holder;
        holder.materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            favourite = "1";
                            store_id = finalHolder.Store_id.getText().toString();

                            new JSONParse().execute();

                        } else {
                            favourite = "0";
                            store_id = finalHolder.Store_id.getText().toString();

                            new JSONParse().execute();

                        }
                    }
                });

        return view;
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        itemList.clear();
        if (charText.length() == 0) {
            itemList.addAll(arraylist);

        } else {
            for (CategoryItem1 postDetail : arraylist) {
                if (charText.length() != 0 && postDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemList.add(postDetail);
                }

                else if (charText.length() != 0 && postDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemList.add(postDetail);
                }
            }
        }
        notifyDataSetChanged();
    }

    private class JSONParse extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                Log.d("Response ----------->",id+" "+store_id+" "+favourite);
                JSONObject params = new JSONObject();


                params.put("user_id", id);
                params.put("s_id",store_id);
                params.put("fav_flag",favourite);

                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/userStore.php", params);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }

    }
}
