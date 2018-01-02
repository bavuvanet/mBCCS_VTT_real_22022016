package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetPakageCategoryTBBccs2Adapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CategoryTBBundeBeanBCCS;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.viettel.bss.viettelpos.v4.commons.auto.AutoConst.AUTO_CATEGORY_TB;

public class FragmentSearchCategoryTBBCCS extends GPSTracker implements OnClickListener, OnItemClickListener {

    // == unitview
    private EditText edtsearch;
    private ListView lvpakage;
    private GetPakageCategoryTBBccs2Adapter mGetPakageCategoryTBBccs2Adapter;
    private ArrayList<SubTypeBeans> arrChargeBeans = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectionmobile_layout_lst_pakage);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        UnitView();
        Bundle mBundle = this.getIntent().getExtras();
        if (mBundle != null) {
            CategoryTBBundeBeanBCCS pakageBundeBean = (CategoryTBBundeBeanBCCS) mBundle.getSerializable("loaithuebao");

            if (pakageBundeBean != null) {
                arrChargeBeans = pakageBundeBean.getArrBeans();
                if (arrChargeBeans != null && arrChargeBeans.size() > 0) {
                    AutoCompleteUtil.getInstance(this).sortCategoryTBBySelectedCount2(AUTO_CATEGORY_TB, arrChargeBeans);
                    System.out.println("12345 sort AUTO_CATEGORY_TB ");

                 /*   for (int i = 0, n = arrChargeBeans.size(); i < n; i++) {
                        System.out.println("12345 xxx " + arrChargeBeans.get(i).getName() + " >> " + arrChargeBeans.get(i).getSelectedCount());
                    }*/
                    mGetPakageCategoryTBBccs2Adapter = new GetPakageCategoryTBBccs2Adapter(arrChargeBeans, FragmentSearchCategoryTBBCCS.this);
                    lvpakage.setAdapter(mGetPakageCategoryTBBccs2Adapter);
                }
            }
        }


    }


    private void UnitView() {
        edtsearch = (EditText) findViewById(R.id.edtsearch);
        edtsearch.setHint("Nhập loại thuê bao để tìm kiếm");
        lvpakage = (ListView) findViewById(R.id.lstpakage);
        lvpakage.setOnItemClickListener(this);
        lvpakage.setTextFilterEnabled(true);
        edtsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = edtsearch.getText().toString()
                        .toLowerCase(Locale.getDefault());


                if (mGetPakageCategoryTBBccs2Adapter != null) {
                    arrChargeBeans = mGetPakageCategoryTBBccs2Adapter.SearchInput(input);
                    lvpakage.setAdapter(mGetPakageCategoryTBBccs2Adapter);
                    mGetPakageCategoryTBBccs2Adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle(getString(R.string.servicemobile));
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        SubTypeBeans subTypeBeans = arrChargeBeans.get(arg2);
        subTypeBeans.pos = arg2;

        Intent data = new Intent();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("loaithuebao", subTypeBeans);
        data.putExtras(mBundle);
        setResult(RESULT_OK, data);

        AutoCompleteUtil.getInstance(this).addToSuggestionList(AUTO_CATEGORY_TB, subTypeBeans.getName());

        finish();
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
