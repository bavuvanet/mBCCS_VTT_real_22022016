package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.PromotionAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchCodePromotionActivity extends AppCompatActivity implements OnItemClickListener, OnClickListener, AutoConst {

    private ListView lv_kmai;
    private EditText edt_search;
    private ArrayList<PromotionTypeBeans> arrPromotionTypeBeans;
    private Activity activity ;
    private String productCode;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = SearchCodePromotionActivity.this;
        setContentView(R.layout.layout_list_search_hthm);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv_kmai = (ListView) findViewById(R.id.lv_hthm);
        edt_search = (EditText) findViewById(R.id.edt_search);
        edt_search.setHint(getApplicationContext().getString(R.string.searchreasion));
        arrPromotionTypeBeans = (ArrayList<PromotionTypeBeans>) getIntent()
                .getSerializableExtra("arrPromotionTypeBeans");
        productCode = getIntent().getStringExtra("productCode");

        onSearchHTHMBeans();
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
        lv_kmai.setOnItemClickListener(this);
    }

    private void onSearchHTHMBeans() {


        String keySearch = edt_search.getText().toString().trim();
        if (keySearch.length() == 0) {
            AutoCompleteUtil.getInstance(this).sortCodePromotionBySelectedCount(AUTO_CODE_PROMOTION_SEARCH, arrPromotionTypeBeans);

            PromotionAdapter adtPromotion = new PromotionAdapter(this, arrPromotionTypeBeans, productCode);
            lv_kmai.setAdapter(adtPromotion);
            adtPromotion.notifyDataSetChanged();

        } else {
            ArrayList<PromotionTypeBeans> items = new ArrayList<>();
            for (PromotionTypeBeans promotionTypeBeans : arrPromotionTypeBeans) {
                if (promotionTypeBeans.getCodeName().toLowerCase().contains(keySearch.toLowerCase())) {
                    items.add(promotionTypeBeans);
                }
            }
	            AutoCompleteUtil.getInstance(this).sortCodePromotionBySelectedCount(AUTO_CODE_PROMOTION_SEARCH, items);
            PromotionAdapter adtPromotion = new PromotionAdapter(this, items, productCode);
            lv_kmai.setAdapter(adtPromotion);
            adtPromotion.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        PromotionTypeBeans promotionTypeBeans = (PromotionTypeBeans) parent.getAdapter().getItem(position);
        Log.d(Constant.TAG, "SearchCodeHthmActivity onItemClick position: " + position + " item: " + promotionTypeBeans);

        Intent data = new Intent();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("PromotionTypeBeans", promotionTypeBeans);
        data.putExtras(mBundle);

        Log.d(Constant.TAG, "SearchCodeHthmActivity onItemClick putExtras position: " + position + " hthmName: " + promotionTypeBeans.getName());

        if (promotionTypeBeans.getName().contains(getResources().getString(R.string.chonctkm1))
                || promotionTypeBeans.getName().contains(getResources().getString(R.string.selectMKM))) {
            //nothing
        } else {
            AutoCompleteUtil.getInstance(this).addToSuggestionList(AUTO_CODE_PROMOTION_SEARCH, promotionTypeBeans.getName());
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getSupportActionBar().setTitle(getResources().getString(R.string.chonctkm1));
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
