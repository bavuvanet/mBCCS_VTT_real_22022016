package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;
import java.util.Locale;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetCusTypeAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerTypeByCustGroupBeans;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentSearchCusTypeMobile extends GPSTracker implements
		OnClickListener, OnItemClickListener {

	// == unitview
	private EditText edtsearch;
	private ListView lvpakage;
	private GetCusTypeAdapter getCusTypeAdapter;
	private ArrayList<CustomerTypeByCustGroupBeans> arrCustomerGroupBeans = new ArrayList<CustomerTypeByCustGroupBeans>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connectionmobile_layout_lst_pakage);

		UnitView();
		Bundle mBundle = this.getIntent().getExtras();
		if (mBundle != null) {
			arrCustomerGroupBeans = (ArrayList<CustomerTypeByCustGroupBeans>) mBundle
					.getSerializable("arrCustomerTypeBeansKey");

			if (arrCustomerGroupBeans != null
					&& arrCustomerGroupBeans.size() > 0) {
				getCusTypeAdapter = new GetCusTypeAdapter(
						arrCustomerGroupBeans, FragmentSearchCusTypeMobile.this);
				lvpakage.setAdapter(getCusTypeAdapter);
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

				if (getCusTypeAdapter != null) {
					arrCustomerGroupBeans = getCusTypeAdapter
							.SearchInput(input);
					lvpakage.setAdapter(getCusTypeAdapter);
					getCusTypeAdapter.notifyDataSetChanged();
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
		addActionBar();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent data = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("customerTypeBeansKey",
				arrCustomerGroupBeans.get(arg2));
		data.putExtras(mBundle);
		setResult(RESULT_OK, data);
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

	private void addActionBar() {

		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
				.findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(
				R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(FragmentSearchCusTypeMobile.this,
						Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(
				R.string.search));
	}

}
