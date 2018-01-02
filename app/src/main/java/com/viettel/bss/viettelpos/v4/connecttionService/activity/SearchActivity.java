package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.customer.adapter.ComBoBoxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hantt47 on 8/1/2017.
 */

public class SearchActivity extends GPSTracker implements View.OnClickListener {

    private EditText edt_search;
    private String hint;
    private ListView lvSearch;
    private List<String> listString = new ArrayList<>();
    private List<String> listSearch = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_search_hthm);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.choseAction));

        Bundle mBundle = this.getIntent().getExtras();
        if (mBundle != null) {
            listString = (ArrayList<String>) mBundle.getSerializable("list");
            hint = (String) mBundle.getSerializable("hint");
            String tittle = (String) mBundle.getSerializable("tittle");
            if(!CommonActivity.isNullOrEmpty(tittle))
                getSupportActionBar().setTitle(tittle);
        }
        unit();

    }

    public void unit() {
        edt_search = (EditText) findViewById(R.id.edt_search);
        edt_search.setHint(hint);
        lvSearch = (ListView) findViewById(R.id.lv_hthm);
        onSearchBeans();
        edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                onSearchBeans();
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent data = new Intent();
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("position", listString.indexOf(listSearch.get(arg2)));
                mBundle.putSerializable("select", listSearch.get(arg2));
                data.putExtras(mBundle);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    private void onSearchBeans() {

        String keySearch = edt_search.getText().toString().trim();
        if (keySearch.length() == 0) {
            ArrayAdapter itemsAdapter = new ArrayAdapter(this, R.layout.item_text_simple_13, listString);
            lvSearch.setAdapter(itemsAdapter);
            listSearch = listString;
            itemsAdapter.notifyDataSetChanged();
        } else {
            List<String> functions = new ArrayList<>();
            for (String func : listString) {
                if (func.toLowerCase().contains(keySearch.toLowerCase()))
                    functions.add(func);
            }
            listSearch = functions;
            ArrayAdapter itemsAdapter = new ArrayAdapter(this, R.layout.item_text_simple_13, functions);
            lvSearch.setAdapter(itemsAdapter);
            itemsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relaBackHome:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}

