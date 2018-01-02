package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.GetReasondapter;
import com.viettel.bss.viettelpos.v4.customer.object.ReasonBundleObj;

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

import static com.viettel.bss.viettelpos.v4.commons.auto.AutoConst.AUTO_REG_NEW_REASON;

public class SearchReasonActivity extends GPSTracker implements OnClickListener, OnItemClickListener {


    // == unitview
    private EditText edtsearch;
    private ListView lvpakage;
    private GetReasondapter mGetReasondapter;
    private ArrayList<ReasonDTO> mReasonDTOArrayList = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_reason);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        UnitView();
        Bundle mBundle = this.getIntent().getExtras();
        if (mBundle != null) {
            ReasonBundleObj reasonBundleObj = (ReasonBundleObj) mBundle.getSerializable("mReasonDTOList");

            if (reasonBundleObj != null) {
                mReasonDTOArrayList = reasonBundleObj.arrReason;
                if (mReasonDTOArrayList != null && mReasonDTOArrayList.size() > 0) {
                    //sort
                    AutoCompleteUtil.getInstance(this).sortReasonChangeSimBySelectedCount(AUTO_REG_NEW_REASON, mReasonDTOArrayList);

                    mGetReasondapter = new GetReasondapter(mReasonDTOArrayList, this);
                    lvpakage.setAdapter(mGetReasondapter);
                }
            }
        }

    }


    private void UnitView() {
        edtsearch = (EditText) findViewById(R.id.edtsearch);
        lvpakage = (ListView) findViewById(R.id.lstpakage);
        lvpakage.setOnItemClickListener(this);
        lvpakage.setTextFilterEnabled(true);
        edtsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = edtsearch.getText().toString()
                        .toLowerCase(Locale.getDefault());


                if (mGetReasondapter != null) {
                    mReasonDTOArrayList = mGetReasondapter.SearchInput(input);
                    lvpakage.setAdapter(mGetReasondapter);
                    mGetReasondapter.notifyDataSetChanged();
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
        getSupportActionBar().setTitle(getString(R.string.choose_reason));
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ReasonDTO reasonDTO = mReasonDTOArrayList.get(arg2);

        Intent data = new Intent();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("mReasonObj", reasonDTO);
        data.putExtras(mBundle);
        setResult(RESULT_OK, data);

        AutoCompleteUtil.getInstance(this).addToSuggestionList(AUTO_REG_NEW_REASON, reasonDTO.getReasonCode() + reasonDTO.getName());

        finish();
//		Intent i = new Intent();
//		PakageChargeBeans pakageChargeBeans = arrChargeBeans.get(arg2);
//		i.putExtra("pakageChargeKey", pakageChargeBeans);
//		getTargetFragment().onActivityResult(
//				getTargetRequestCode(), Activity.RESULT_OK, i);
//		getActivity().onBackPressed();

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