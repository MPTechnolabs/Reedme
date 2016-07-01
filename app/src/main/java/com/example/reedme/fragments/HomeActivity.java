package com.example.reedme.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.reedme.R;
import com.example.reedme.activity.StartActivity;
import com.example.reedme.activity.StoreDiaplyActivity;
import com.example.reedme.activity.ThankActivity;
import com.example.reedme.adapter.CategoryHListAdapter;
import com.example.reedme.adapter.CategoryListAdapter;
import com.example.reedme.helper.Util;
import com.example.reedme.model.CategoryData;

import java.util.ArrayList;

public class HomeActivity extends Fragment {
    public static final String TAG;
    static CategoryData categoryData;
    GridView categoryGridView;
    Runnable changeImage;
    Context context;
    Handler handler;

    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    FragmentManager manager;
    /* renamed from: com.vegies.app.HomeActivity.1 */


    private class CategoryItemClickListener implements OnItemClickListener {
        private CategoryItemClickListener() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            Intent i= new Intent(getActivity(),StoreDiaplyActivity.class);
            i.putExtra("id", (HomeActivity.categoryData.getCategoryItems().get(position)).getId());
            i.putExtra("name",(HomeActivity.categoryData.getCategoryItems().get(position)).getName());

            manager.beginTransaction().setCustomAnimations(R.anim.right_to_left, R.anim.left_to_inner);
            startActivity(i);

          //  manager.beginTransaction().setCustomAnimations(R.anim.right_to_left, R.anim.left_to_inner).add(R.id.main_content, CategoryItemActivity.newInstance(((CategoryItem) HomeActivity.categoryData.getCategoryItems().get(position)).getId(), ((CategoryItem) HomeActivity.categoryData.getCategoryItems().get(position)).getName()), CategoryItemActivity.TAG).commit();
        }
    }


    static {
        TAG = HomeActivity.class.getSimpleName();
    }

    public HomeActivity() {
        this.handler = new Handler();
    }

    public static HomeActivity newInstance(CategoryData categoryItems) {
        categoryData = categoryItems;
        return new HomeActivity();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;

            manager = getActivity().getSupportFragmentManager();
            this.context = container.getContext();
            Point point = Util.getInstance(this.context).getDeviceSize();
            Log.d(TAG, point.x + "/" + point.y);

                rootView = inflater.inflate(R.layout.activity_home, container, false);

            this.categoryGridView = (GridView) rootView.findViewById(R.id.grid_category_item);
            StartActivity.getInstance().SetCheckOutValue();
            this.categoryGridView.setAdapter(new CategoryListAdapter(this.context, categoryData.getCategoryItems()));
            this.categoryGridView.setOnItemClickListener(new CategoryItemClickListener());

        return rootView;
    }
}
