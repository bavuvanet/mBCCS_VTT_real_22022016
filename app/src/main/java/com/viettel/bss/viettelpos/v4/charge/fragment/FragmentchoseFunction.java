package com.viettel.bss.viettelpos.v4.charge.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetReansonDTOAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thuandq on 7/3/2017.
 */

public class FragmentchoseFunction extends GPSTracker implements View.OnClickListener {

    private EditText edt_search;
    private ListView lvFunction;
    private List<String> listFunction = new ArrayList<>();
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
            listFunction = (ArrayList<String>) mBundle.getSerializable("listFunction");
        }
        unit();

    }

    public void unit() {
        edt_search = (EditText) findViewById(R.id.edt_search);
        edt_search.setHint("Nhập tên để tìm kiếm");
//        edt_search.setHint(getApplicationContext().getString(R.string.searchreasion));
        lvFunction = (ListView) findViewById(R.id.lv_hthm);
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

        lvFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String action = (String) arg0.getAdapter().getItem(arg2);
                Intent data = new Intent();
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("function", action);
                data.putExtras(mBundle);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    private void onSearchBeans() {

        String keySearch = edt_search.getText().toString().trim();
        if (keySearch.length() == 0) {
            ArrayAdapter itemsAdapter = new ArrayAdapter(this, R.layout.item_text_simple_13, listFunction);
            lvFunction.setAdapter(itemsAdapter);
            itemsAdapter.notifyDataSetChanged();
        } else {
            List<String> functions = new ArrayList<>();
            for (String func : listFunction) {
                if (func.contains(keySearch))
                    functions.add(func);
            }
            ArrayAdapter itemsAdapter = new ArrayAdapter(this, R.layout.item_text_simple_13, functions);
            lvFunction.setAdapter(itemsAdapter);
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

