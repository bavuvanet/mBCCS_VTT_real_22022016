package com.viettel.bss.viettelpos.v4.login.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterStaff;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

import java.util.ArrayList;

public class ActivitySearchStaff extends AppCompatActivity implements OnClickListener{

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity_layout);
		unit();
	}
	
	private void unit(){
        ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		mActionBar.setCustomView(R.layout.layout_actionbar_search);
        Button btnBackSearch = (Button) mActionBar.getCustomView()
                .findViewById(R.id.btnBackSearch);
		btnBackSearch.setOnClickListener(this);
        SearchView mSearchView = (SearchView) mActionBar.getCustomView()
                .findViewById(R.id.searchViewStaff);
		mSearchView.setFocusable(true);
		mSearchView.setIconified(false);
		mSearchView.requestFocusFromTouch();
        ListView lvStaffSearch = (ListView) findViewById(R.id.lvStaffSearch);
        ArrayList<Staff> arrayListStaff = new ArrayList<>();
        AdapterStaff mAdapterStaff = new AdapterStaff(arrayListStaff, ActivitySearchStaff.this, AdapterStaff.TYPE_LOCATION);
		lvStaffSearch.setAdapter(mAdapterStaff);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnBackSearch:
			finish();
			break;

		default:
			break;
		}
		
	}

}
