package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetPromotionUnitVasAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PromotionUnitVas;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchCodePromotionUnitActivity extends AppCompatActivity implements OnItemClickListener, OnClickListener {

	private ListView lv_kmai;
	private EditText edt_search;
	private ArrayList<PromotionUnitVas> arrPromotionTypeBeans;
	@BindView(R.id.toolbar)
	Toolbar toolbar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_list_search_hthm);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		lv_kmai = (ListView) findViewById(R.id.lv_hthm);
		edt_search = (EditText) findViewById(R.id.edt_search);

		arrPromotionTypeBeans = (ArrayList<PromotionUnitVas>) getIntent()
				.getSerializableExtra("arrPromotionTypeBeans");
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
//			ArrayAdapter<PromotionTypeBeans> itemsAdapter = new ArrayAdapter<PromotionTypeBeans>(this, android.R.layout.simple_list_item_1, arrPromotionTypeBeans);
//			lv_kmai.setAdapter(itemsAdapter);
			GetPromotionUnitVasAdapter itemsAdapter = new GetPromotionUnitVasAdapter(arrPromotionTypeBeans, this);
			lv_kmai.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		} else {
			ArrayList<PromotionUnitVas> items = new ArrayList<PromotionUnitVas>();
			for (PromotionUnitVas promotionTypeBeans : arrPromotionTypeBeans) {
				if ( (promotionTypeBeans.getPromotionCodeUnit() + "-" +promotionTypeBeans.getPromotionCodeUnitName()).toLowerCase().contains(keySearch.toLowerCase())) {
					items.add(promotionTypeBeans);
				}
			}
			GetPromotionUnitVasAdapter itemsAdapter = new GetPromotionUnitVasAdapter(items, this);
			lv_kmai.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


		PromotionUnitVas promotionTypeBeans = (PromotionUnitVas) parent.getAdapter().getItem(position);;
		Log.d(Constant.TAG, "SearchCodePromotionUnitActivity onItemClick position: " + position + " item: " + promotionTypeBeans);

		Intent data = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("PromotionTypeBeans", promotionTypeBeans);
		data.putExtras(mBundle);

		Log.d(Constant.TAG, "SearchCodePromotionUnitActivity onItemClick putExtras position: " + position + " hthmName: " + promotionTypeBeans.getPromotionCodeUnitName());

		setResult(RESULT_OK, data);
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

//		addActionBar();

	}

//	private void addActionBar() {
//
//		ActionBar mActionBar = getActionBar();
//		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
//		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView().findViewById(R.id.relaBackHome);
//		relaBackHome.setOnClickListener(this);
//		Button btnHome = (Button) mActionBar.getCustomView().findViewById(R.id.btnHome);
//		// btnHome.setOnClickListener(new OnClickListener() {
//		//
//		// @Override
//		// public void onClick(View arg0) {
//		// CommonActivity.callphone(FragmentSearchPakageMobile.this,
//		// Constant.phoneNumber);
//		// }
//		// });
//		TextView txtNameActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtNameActionbar);
//		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtAddressActionbar);
//		txtAddressActionBar.setVisibility(View.GONE);
//		txtNameActionBar.setText(getResources().getString(R.string.searchctkm));
//	}

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
