package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import java.util.ArrayList;
import java.util.Locale;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetPakageBundleBccs2Adapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentSearchProductMobileBCCS extends GPSTracker implements OnClickListener, OnItemClickListener {

	// == unitview
	private EditText edtsearch;
	private ListView lvpakage;
	private GetPakageBundleBccs2Adapter adapGetPakageBundleAdapter;
	private ArrayList<ProductOfferingDTO> arrChargeBeans = new ArrayList<ProductOfferingDTO>();
	private String newPackageChange;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connectionmobile_layout_lst_pakage);

		UnitView();
		Bundle mBundle = this.getIntent().getExtras();
		if (mBundle != null) {
			arrChargeBeans = (ArrayList<ProductOfferingDTO>) mBundle.getSerializable("arrChargeBeans");
			if (!CommonActivity.isNullOrEmptyArray(arrChargeBeans)) {
				adapGetPakageBundleAdapter = new GetPakageBundleBccs2Adapter(arrChargeBeans,
						FragmentSearchProductMobileBCCS.this);
				lvpakage.setAdapter(adapGetPakageBundleAdapter);
			}

			newPackageChange = mBundle.getString("newPackageChange");
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
				String input = edtsearch.getText().toString().toLowerCase(Locale.getDefault());

				if (adapGetPakageBundleAdapter != null) {
					arrChargeBeans = adapGetPakageBundleAdapter.SearchInput(input);
					lvpakage.setAdapter(adapGetPakageBundleAdapter);
					adapGetPakageBundleAdapter.notifyDataSetChanged();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		if(!CommonActivity.isNullOrEmpty(newPackageChange)){
			edtsearch.setText(newPackageChange);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
//		addActionBar();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ProductOfferingDTO productOfferingDTO = (ProductOfferingDTO) arg0.getAdapter().getItem(arg2);
		Intent data = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("pakageChargeKey", productOfferingDTO);
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
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView().findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(FragmentSearchProductMobileBCCS.this, Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(R.string.servicemobile));
	}
}
