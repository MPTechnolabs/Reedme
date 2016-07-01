package com.example.reedme.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.example.reedme.R;
import com.example.reedme.fragments.CategoryItemActivity;
import com.example.reedme.model.SubCategory;

import java.util.List;

public class CategoryAdapter extends FragmentPagerAdapter {
    List<SubCategory> subCategorys;

    @SuppressLint("ValidFragment")
    public class SelectSubCategoryItemFragment extends Fragment {
        int position;

        public SelectSubCategoryItemFragment(int position) {
            this.position = position;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.category_items, container, false);
            CategoryItemActivity.subCategoryList = (ListView) rootView.findViewById(R.id.lst_category_items);
            CategoryItemActivity.subCategoryList.setAdapter(new CategoryItemAdapter(CategoryAdapter.this.subCategorys.get(this.position).getProducts()));
            return rootView;
        }
    }

    public CategoryAdapter(FragmentManager fm, List<SubCategory> listItems) {
        super(fm);
        this.subCategorys = listItems;
    }

    public Fragment getItem(int position) {
        Fragment fragment = new SelectSubCategoryItemFragment(position);
        Bundle args = new Bundle();
        args.putInt("Value", position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    public int getCount() {
        return this.subCategorys.size();
    }

    public CharSequence getPageTitle(int position) {
        return this.subCategorys.get(position).getSubCategoryName();
    }
}
