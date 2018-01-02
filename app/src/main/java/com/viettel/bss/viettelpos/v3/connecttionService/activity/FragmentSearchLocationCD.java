package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;
import java.util.Locale;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetDistrictCDAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetPrecinctCDAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetProvinceCDAdapter;

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

public class FragmentSearchLocationCD extends GPSTracker implements
		OnClickListener, OnItemClickListener {

	// == unitview
	private EditText edtsearch;
	private ListView lvpakage;
	private GetProvinceCDAdapter getProvinceAdapter;
	private GetDistrictCDAdapter getDistrictAdapter;
	private GetPrecinctCDAdapter getPrecinctAdapter;
	private ArrayList<AreaObj> arrProvinces = new ArrayList<AreaObj>();
	private ArrayList<AreaObj> arrDistrict = new ArrayList<AreaObj>();
	private ArrayList<AreaObj> arrPrecinct = new ArrayList<AreaObj>();
	private String check = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connectionmobile_layout_lst_pakage);

		UnitView();
		Bundle mBundle = this.getIntent().getExtras();
		if (mBundle != null) {
			arrProvinces = (ArrayList<AreaObj>) mBundle
					.getSerializable("arrProvincesKey");
			arrDistrict = (ArrayList<AreaObj>) mBundle
					.getSerializable("arrDistrictKey");
			arrPrecinct = (ArrayList<AreaObj>) mBundle
					.getSerializable("arrPrecinctKey");
			check = mBundle.getString("checkKey","");
			switch (Integer.parseInt(check)) {
			case 1:
				if(arrProvinces != null && !arrProvinces.isEmpty()){
					getProvinceAdapter = new GetProvinceCDAdapter(arrProvinces, FragmentSearchLocationCD.this);
					lvpakage.setAdapter(getProvinceAdapter);
					getProvinceAdapter.notifyDataSetChanged();
				}
				break;
			case 2:
				if(arrDistrict != null && !arrDistrict.isEmpty()){
					getDistrictAdapter = new GetDistrictCDAdapter(arrDistrict, FragmentSearchLocationCD.this);
					lvpakage.setAdapter(getDistrictAdapter);
					getDistrictAdapter.notifyDataSetChanged();
				}
				break;
			case 3:
				if(arrPrecinct != null && !arrPrecinct.isEmpty()){
					getPrecinctAdapter = new GetPrecinctCDAdapter(arrPrecinct, FragmentSearchLocationCD.this);
					lvpakage.setAdapter(getPrecinctAdapter);
					getPrecinctAdapter.notifyDataSetChanged();
				}
				break;
			default:
				break;
			}
		}
	}

	private void UnitView() {
		edtsearch = (EditText) findViewById(R.id.edtsearch);
		lvpakage = (ListView) findViewById(R.id.lstpakage);
		lvpakage.setOnItemClickListener(this);
		lvpakage.setTextFilterEnabled(true);
		TextView txtinfo = (TextView) findViewById(R.id.txtinfo);
		txtinfo.setText(getString(R.string.createAddress));
		edtsearch.setHint(R.string.createAddress);
		edtsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());

				switch (Integer.parseInt(check)) {
					case 1:
						if(getProvinceAdapter != null){
							arrProvinces = getProvinceAdapter.SearchInput(input);
							lvpakage.setAdapter(getProvinceAdapter);
							getProvinceAdapter.notifyDataSetChanged();
						}
						break;
					case 2: 
						if(getDistrictAdapter != null){
							arrDistrict = getDistrictAdapter.SearchInput(input);
							lvpakage.setAdapter(getDistrictAdapter);
							getDistrictAdapter.notifyDataSetChanged();
						}
						break;
					case 3:
						if(getPrecinctAdapter != null){
							arrPrecinct = getPrecinctAdapter.SearchInput(input);
							lvpakage.setAdapter(getPrecinctAdapter);
							getPrecinctAdapter.notifyDataSetChanged();
						}
						break;
					default :
						break;
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
		
		switch (Integer.parseInt(check)) {
		case 1:
			Intent data = new Intent();
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("provinceKey",
					arrProvinces.get(arg2));
			data.putExtras(mBundle);
			setResult(RESULT_OK, data);
			finish();
			break;
		case 2:
			Intent data1 = new Intent();
			Bundle mBundle1 = new Bundle();
			mBundle1.putSerializable("districtKey",
					arrDistrict.get(arg2));
			data1.putExtras(mBundle1);
			setResult(RESULT_OK, data1);
			finish();
			break;
		case 3:
			Intent data2 = new Intent();
			Bundle mBundle2 = new Bundle();
			mBundle2.putSerializable("precinctKey",
					arrPrecinct.get(arg2));
			data2.putExtras(mBundle2);
			setResult(RESULT_OK, data2);
			finish();
			break;
		default:
			break;
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
				CommonActivity.callphone(FragmentSearchLocationCD.this,
						Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(R.string.search));
	}

}
