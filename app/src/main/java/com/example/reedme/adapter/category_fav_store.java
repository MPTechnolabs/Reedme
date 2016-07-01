package com.example.reedme.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.MaterialFavoriteButton;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.model.CategoryItem1;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Android on 5/9/2016.
 */
public class category_fav_store extends BaseAdapter

    {
        static final  boolean $assertionsDisabled;
        CategoryItem1 categoryItem;
        Context context;
        List<CategoryItem1> itemList;
        String id;
        String store_id;
        String favourite = "0";

        public static MyJSONParser mJsonParser = null;
        JSONObject jsonObject_parent = null;

       public static class ViewHolder {
            ImageView itemImage;
            TextView itemName;
            public MaterialFavoriteButton materialFavoriteButtonNice;
            TextView Store_id;

            ViewHolder() {
            }
        }

        static {
        $assertionsDisabled = !CategoryListAdapter.class.desiredAssertionStatus();
    }

        public category_fav_store(Context context, List<CategoryItem1> listItems) {
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
        public View getView(final int position, final View convertView, final ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);

            view = layoutInflater.inflate(R.layout.activity_fav_listitem, null);
            mJsonParser = new MyJSONParser();

            holder = new ViewHolder();
            if ($assertionsDisabled || view != null) {
                holder.Store_id= (TextView) view.findViewById(R.id.store_id);
                holder.itemName = (TextView) view.findViewById(R.id.txt_category_item_name);
                holder.itemImage = (ImageView) view.findViewById(R.id.img_category_item);
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

        Picasso.with(this.context).load(this.categoryItem.getImage()).placeholder((int) R.mipmap.logo).into(holder.itemImage);
        holder.materialFavoriteButtonNice.setFavorite(true, false);

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
    private class JSONParse extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                Log.d("Response ----------->", id + " " + store_id + " " + favourite);
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


