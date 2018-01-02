package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
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

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetHTHMAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.viettel.bss.viettelpos.v4.commons.auto.AutoConst.AUTO_CODE_HTHM_SEARCH;

public class SearchCodeHthmActivity extends AppCompatActivity implements OnItemClickListener, OnClickListener {

	private ArrayList<HTHMBeans> arrHthmBeans;
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
		arrHthmBeans = (ArrayList<HTHMBeans>) getIntent().getSerializableExtra("arrHthmBeans");
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
			AutoCompleteUtil.getInstance(this).sortCodeHTHMBySelectedCount(AUTO_CODE_HTHM_SEARCH,arrHthmBeans);
			GetHTHMAdapter  itemsAdapter = new GetHTHMAdapter(arrHthmBeans, this);
			lv_hthm.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		} else {
			ArrayList<HTHMBeans> items = new ArrayList<>();
			for (HTHMBeans hBeans : arrHthmBeans) { 
				if(hBeans != null && !CommonActivity.isNullOrEmpty(hBeans.getCodeName())){
//					if ( hBeans.getName() != null && hBeans.getName().toLowerCase().contains(keySearch.toLowerCase()) ||
						if	(hBeans.getCodeName() != null && hBeans.getCodeName().toLowerCase().contains(keySearch.toLowerCase())) { 
							items.add(hBeans);
						} 
				}
			}
			AutoCompleteUtil.getInstance(this).sortCodeHTHMBySelectedCount(AUTO_CODE_HTHM_SEARCH,items);
			GetHTHMAdapter  itemsAdapter = new GetHTHMAdapter(items, this);
			lv_hthm.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
		
	}

	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		HTHMBeans hthmBeans = (HTHMBeans) adapterView.getAdapter().getItem(position);
		Log.d(Constant.TAG, "SearchCodeHthmActivity onItemClick position: " + position + " item: " + hthmBeans);

		Intent data = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("HTHMBeans", hthmBeans);
		data.putExtras(mBundle);

		AutoCompleteUtil.getInstance(this).addToSuggestionList(AUTO_CODE_HTHM_SEARCH, hthmBeans.getCodeName());
//		Log.d(Constant.TAG, "SearchCodeHthmActivity onItemClick putExtras position: " + position + " hthmName: " + hthmBeans.getName());
		
		setResult(RESULT_OK, data);
		finish(); 
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getSupportActionBar().setTitle(getResources().getString(R.string.servicemobile));
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
