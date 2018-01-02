package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetStaffdapter;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchStaffActivity extends AppCompatActivity implements OnItemClickListener, OnClickListener {


	private ArrayList<Staff> arrStaffs;
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

		lv_hthm = (ListView) findViewById(R.id.lv_hthm);
		edt_search = (EditText) findViewById(R.id.edt_search);
		edt_search.setHint(this.getString(R.string.chonnvgiaoviec));
		arrStaffs = (ArrayList<Staff>) getIntent().getSerializableExtra("arrStaff");
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
			GetStaffdapter  itemsAdapter = new GetStaffdapter(arrStaffs, this);
			lv_hthm.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		} else {
			ArrayList<Staff> items = new ArrayList<>();
			for (Staff hBeans : arrStaffs) { 
				if(hBeans != null && !CommonActivity.isNullOrEmpty(hBeans.toString())){
						if(hBeans != null && hBeans.toString().toLowerCase().contains(keySearch.toLowerCase())) { 
							items.add(hBeans);
						} 
				}
			} 
			GetStaffdapter  itemsAdapter = new GetStaffdapter(items, this);
			lv_hthm.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
		
	}

	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		Staff staffBean = (Staff) adapterView.getAdapter().getItem(position);
		Intent data = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("StaffBeans", staffBean);
		data.putExtras(mBundle);
		
		setResult(RESULT_OK, data);
		finish(); 
	}

	@Override
	protected void onResume() {
		super.onResume();
		getSupportActionBar().setTitle(getResources().getString(R.string.giaoviechotline));

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
