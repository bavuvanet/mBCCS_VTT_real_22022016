package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
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

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetPakageBundleBccs2Adapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PakageBundeBeanBCCS;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.viettel.bss.viettelpos.v4.commons.auto.AutoConst.AUTO_PAKAGE_SEARCH;

public class FragmentSearchPakageMobileBCCS extends GPSTracker implements OnClickListener,OnItemClickListener{

	// == unitview
	private EditText edtsearch;
	private ListView lvpakage;
	private GetPakageBundleBccs2Adapter adapGetPakageBundleAdapter;
    private ArrayList<ProductOfferingDTO> arrChargeBeans = new ArrayList<>();
	private  OnClickListener moveLogInAct;



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
            PakageBundeBeanBCCS pakageBundeBean = (PakageBundeBeanBCCS) mBundle.getSerializable("PakageKey");
             moveLogInAct = (OnClickListener) mBundle.getSerializable("moveLogInAct");

			if(pakageBundeBean != null){
				arrChargeBeans = pakageBundeBean.getArrChargeBeans();
				if(arrChargeBeans != null && arrChargeBeans.size() > 0){
					AutoCompleteUtil.getInstance(this).sortPakageBySelectedCount(AUTO_PAKAGE_SEARCH,arrChargeBeans);
					System.out.println("12345 sortPakageBySelectedCount ");
					adapGetPakageBundleAdapter = new GetPakageBundleBccs2Adapter(arrChargeBeans, FragmentSearchPakageMobileBCCS.this);
					lvpakage.setAdapter(adapGetPakageBundleAdapter);
				}
			}
		}
		
	
	}


	private void UnitView(){
		edtsearch = (EditText) findViewById(R.id.edtsearch);
		lvpakage = (ListView) findViewById(R.id.lstpakage);
		lvpakage.setOnItemClickListener(this);
		lvpakage.setTextFilterEnabled(true);
		edtsearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());
				
				
				if(adapGetPakageBundleAdapter != null){
					arrChargeBeans = adapGetPakageBundleAdapter.SearchInput(input);
					lvpakage.setAdapter(adapGetPakageBundleAdapter);
					adapGetPakageBundleAdapter.notifyDataSetChanged();
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
		Intent data = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("pakageChargeKey", arrChargeBeans.get(arg2));
		data.putExtras(mBundle);
		setResult(RESULT_OK, data);

		AutoCompleteUtil.getInstance(this).addToSuggestionList(AUTO_PAKAGE_SEARCH, arrChargeBeans.get(arg2).getName());

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
