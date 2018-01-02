package com.viettel.bss.viettelpos.v4.sale.activity;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchStockActivity extends AppCompatActivity implements OnItemClickListener, OnClickListener, AutoConst {

    private ArrayList<AreaObj> arrHthmBeans;
    private ListView lv_hthm;
    private EditText edt_search;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_search_hthm);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv_hthm = (ListView) findViewById(R.id.lv_hthm);
        edt_search = (EditText) findViewById(R.id.edt_search);
        arrHthmBeans = (ArrayList<AreaObj>) getIntent().getSerializableExtra("arrAreaObj");

        AutoCompleteUtil.getInstance(this).sortStockBySelectedCount(AUTO_STOCK_SEARCH,arrHthmBeans);
        System.out.println("12345 aaaaaaaaaaaaaa ");
        onSearchHTHMBeans();

        lv_hthm.setOnItemClickListener(this);

        edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onSearchHTHMBeans();
            }
        });
    }

    private void onSearchHTHMBeans() {
        String keySearch = edt_search.getText().toString().trim();
        if (keySearch.length() == 0) {
//			ArrayAdapter<HTHMBeans> itemsAdapter = new ArrayAdapter<HTHMBeans>(this, android.R.layout.simple_list_item_1, arrHthmBeans);
//			lv_hthm.setAdapter(itemsAdapter);
            AdapterProvinceSpinner itemsAdapter = new AdapterProvinceSpinner(arrHthmBeans, SearchStockActivity.this);
            lv_hthm.setAdapter(itemsAdapter);
            itemsAdapter.notifyDataSetChanged();
        } else {
            ArrayList<AreaObj> items = new ArrayList<>();
            for (AreaObj hBeans : arrHthmBeans) {
                if (hBeans != null && !CommonActivity.isNullOrEmpty(hBeans.getName())) {
//					if ( hBeans.getName() != null && hBeans.getName().toLowerCase().contains(keySearch.toLowerCase()) ||
                    if (hBeans.getName() != null && hBeans.getName().toLowerCase().contains(keySearch.toLowerCase())) {
                        items.add(hBeans);
                    }
                }
            }
            AdapterProvinceSpinner itemsAdapter = new AdapterProvinceSpinner(items, this);
            lv_hthm.setAdapter(itemsAdapter);
            itemsAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        AreaObj hthmBeans = (AreaObj) adapterView.getAdapter().getItem(position);
        Log.d(Constant.TAG, "SearchStockActivity onItemClick position: " + position + " item: " + hthmBeans);

        Intent data = new Intent();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("areaObj", hthmBeans);
        data.putExtras(mBundle);

        AutoCompleteUtil.getInstance(SearchStockActivity.this).addToSuggestionList(AUTO_STOCK_SEARCH, hthmBeans.getName());
//		Log.d(Constant.TAG, "SearchCodeHthmActivity onItemClick putExtras position: " + position + " hthmName: " + hthmBeans.getName());

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getSupportActionBar().setTitle(getResources().getString(R.string.sale_for_search_isdn));
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
