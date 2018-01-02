package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

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
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.BusTypeBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.BusTypePreBean;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetBusTypeAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetBusTypePreAdapter;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSearchBusTypeMobile extends GPSTracker implements
		OnClickListener, OnItemClickListener {

	// == unitview
	private EditText edtsearch;
	private ListView lvpakage;
	private GetBusTypeAdapter getBusTypeAdapter;
	private GetBusTypePreAdapter getBusTypePreAdapter;
	private ArrayList<BusTypeBean> arrBusTypeBeans = new ArrayList<>();
	private ArrayList<BusTypePreBean> arrBusTypePreBeans = new ArrayList<>();
	
	private String checkSub = "";
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
			arrBusTypeBeans = (ArrayList<BusTypeBean>) mBundle
					.getSerializable("arrBusTypeBeansKey");
			checkSub = mBundle.getString("checkSubKey","");
			arrBusTypePreBeans = (ArrayList<BusTypePreBean>) mBundle
					.getSerializable("arrBusTypePreBeansKey");
			
			if(!CommonActivity.isNullOrEmpty(checkSub)){
				// tra truoc
				if (arrBusTypePreBeans != null
						&& arrBusTypePreBeans.size() > 0) {
					getBusTypePreAdapter = new GetBusTypePreAdapter(
							arrBusTypePreBeans, FragmentSearchBusTypeMobile.this);
					lvpakage.setAdapter(getBusTypePreAdapter);
				}
				
			}else{
				if (arrBusTypeBeans != null
						&& arrBusTypeBeans.size() > 0) {
					getBusTypeAdapter = new GetBusTypeAdapter(
							arrBusTypeBeans, FragmentSearchBusTypeMobile.this);
					lvpakage.setAdapter(getBusTypeAdapter);
				}
			}
		}
	}
	private void UnitView() {
		edtsearch = (EditText) findViewById(R.id.edtsearch);
		lvpakage = (ListView) findViewById(R.id.lstpakage);
		lvpakage.setOnItemClickListener(this);
		lvpakage.setTextFilterEnabled(true);
		TextView txtinfo = (TextView) findViewById(R.id.txtinfo);
		txtinfo.setText(getString(R.string.dscustype));
		edtsearch.setHint(R.string.checkcustype);
		edtsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());
				if(!CommonActivity.isNullOrEmpty(checkSub)){
					if (getBusTypePreAdapter != null) {
						arrBusTypePreBeans = getBusTypePreAdapter
								.SearchInput(input);
						lvpakage.setAdapter(getBusTypePreAdapter);
						getBusTypePreAdapter.notifyDataSetChanged();
					}
				}else{
					if (getBusTypeAdapter != null) {
						arrBusTypeBeans = getBusTypeAdapter
								.SearchInput(input);
						lvpakage.setAdapter(getBusTypeAdapter);
						getBusTypeAdapter.notifyDataSetChanged();
					}
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
		getSupportActionBar().setTitle(getString(R.string.search));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(!CommonActivity.isNullOrEmpty(checkSub)){
			Intent data = new Intent();
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("busTypePreBeansKey",
					arrBusTypePreBeans.get(arg2));
			data.putExtras(mBundle);
			setResult(RESULT_OK, data);
			finish();
		}else{
			Intent data = new Intent();
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("busTypeBeansKey",
					arrBusTypeBeans.get(arg2));
			data.putExtras(mBundle);
			setResult(RESULT_OK, data);
			finish();
		}

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
