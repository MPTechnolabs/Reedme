package com.example.reedme.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.reedme.R;
import com.example.reedme.adapter.SearchAdapter;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Util;
import com.example.reedme.model.CategoryData;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jolly Raiyani on 3/30/2016.
 */
public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    private ArrayList<String> mCountries;
    public static CategoryData categoryDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.right_to_left, R.anim.left_to_inner);

        setContentView(R.layout.activity_main);
        categoryDate = (CategoryData) getIntent().getExtras().getSerializable("CategoryData");

        toolBarData();
        loadToolBarSearch();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private void toolBarData() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("MaterialDialogSearchView");
        //toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    public void loadToolBarSearch() {

        ArrayList<String> countryStored = AppPrefs.loadList(SearchActivity.this, Util.PREFS_NAME, Util.KEY_COUNTRIES);

        View view = SearchActivity.this.getLayoutInflater().inflate(R.layout.view_toolbar_search, null);
        LinearLayout parentToolbarSearch = (LinearLayout) view.findViewById(R.id.parent_toolbar_search);
        ImageView imgToolBack = (ImageView) view.findViewById(R.id.img_tool_back);
        final EditText edtToolSearch = (EditText) view.findViewById(R.id.edt_tool_search);
        final ListView listSearch = (ListView) view.findViewById(R.id.list_search);
        final TextView txtEmpty = (TextView) view.findViewById(R.id.txt_empty);

        Util.setListViewHeightBasedOnChildren(listSearch);

        edtToolSearch.setHint("Search your city");

        final Dialog
                toolbarSearchDialog = new Dialog(SearchActivity.this, R.style.MaterialSearch);
        toolbarSearchDialog.setContentView(view);
        toolbarSearchDialog.setCancelable(false);
        toolbarSearchDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        toolbarSearchDialog.getWindow().setGravity(Gravity.BOTTOM);
        toolbarSearchDialog.show();

        toolbarSearchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        countryStored = (countryStored != null && countryStored.size() > 0) ? countryStored : new ArrayList<String>();
        final SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this, countryStored, false);

        listSearch.setVisibility(View.VISIBLE);
        listSearch.setAdapter(searchAdapter);

        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String country = String.valueOf(adapterView.getItemAtPosition(position));
                AppPrefs.addList(SearchActivity.this, Util.PREFS_NAME, Util.KEY_COUNTRIES, country);
                edtToolSearch.setText(country);
                listSearch.setVisibility(View.GONE);


            }
        });
        edtToolSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                String[] country = SearchActivity.this.getResources().getStringArray(R.array.countries_array);
                mCountries = new ArrayList<String>(Arrays.asList(country));
                listSearch.setVisibility(View.VISIBLE);
                searchAdapter.updateList(mCountries, true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String> filterList = new ArrayList<String>();
                boolean isNodata = false;
                if (s.length() > 0) {
                    for (int i = 0; i < mCountries.size(); i++) {


                        if (mCountries.get(i).toLowerCase().startsWith(s.toString().trim().toLowerCase())) {

                            filterList.add(mCountries.get(i));

                            listSearch.setVisibility(View.VISIBLE);
                            searchAdapter.updateList(filterList, true);
                            isNodata = true;
                        }
                    }
                    if (!isNodata) {
                        listSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                        txtEmpty.setText("No data found");
                    }
                } else {
                    listSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SearchActivity.this, StartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CategoryData", categoryDate);
                intent.putExtras(bundle);
                overridePendingTransition(R.anim.right_to_left, R.anim.left_to_out);
                startActivity(intent);
                finish();
            }
        });

    }
}