package com.viettel.bss.viettelpos.v4.commons;

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
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetHTHMAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSearchCommon extends GPSTracker implements OnClickListener,OnItemClickListener{
	
	
	// == unitview
	private EditText edtsearch;
	private ListView lvpakage;
	private GetHTHMAdapter adapHthmCDAdapter;

    //==== HTHM CO DINH =================
	private ArrayList<HTHMBeans> arrHthmBeansCD = new ArrayList<>();

	private ArrayList<PromotionTypeBeans> arrPromotionTypeBeansCD = new ArrayList<>();
	
	
	//==== HTHM MOBILE==================
	private ArrayList<HTHMBeans> arrHthmBeansMobile = new ArrayList<>();
	private ArrayList<PromotionTypeBeans> arrPromotionTypeBeansMobile = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connectionmobile_layout_search);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

		UnitView();
		Bundle mBundle = this.getIntent().getExtras();
		if (mBundle != null) {
            CommonSearchBean commonSearchBean = (CommonSearchBean) mBundle.getSerializable("commonSearchBeanKey");
			
			if(commonSearchBean != null){
				arrHthmBeansCD = commonSearchBean.getArrHthmBeans();
				if(arrHthmBeansCD != null && arrHthmBeansCD.size() > 0){
					adapHthmCDAdapter = new GetHTHMAdapter(arrHthmBeansCD, FragmentSearchCommon.this);
					lvpakage.setAdapter(adapHthmCDAdapter);
				}
			}
		}
		
	
	}


	private void UnitView(){
        TextView txtInfo = (TextView) findViewById(R.id.txtinfo);
		edtsearch = (EditText) findViewById(R.id.edtsearch);
		lvpakage = (ListView) findViewById(R.id.lstpakage);
		lvpakage.setOnItemClickListener(this);
		lvpakage.setTextFilterEnabled(true);
		edtsearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());
				
				
				if(adapHthmCDAdapter != null){
					arrHthmBeansCD = adapHthmCDAdapter.SearchInput(input);
					lvpakage.setAdapter(adapHthmCDAdapter);
					adapHthmCDAdapter.notifyDataSetChanged();
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
		getSupportActionBar().setSubtitle(getResources().getString(
                R.string.servicemobile));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent data = new Intent();
		Bundle mBundle = new Bundle();
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
}
