package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import java.util.ArrayList;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoCompleteUtil;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetReansonDTOAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChooseReason extends GPSTracker implements OnClickListener, AutoConst {

	private EditText edt_search;
	private ListView lvReason;
	private ArrayList<ReasonDTO> arrReasonDTO = new ArrayList<ReasonDTO>();
	@BindView(R.id.toolbar)
	Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mBundle = this.getIntent().getExtras();
        if (mBundle != null) {
            arrReasonDTO = (ArrayList<ReasonDTO>) mBundle.getSerializable("arrReasonDTO");
            AutoCompleteUtil.getInstance(this).sortChooseReasonBySelectedCount(AUTO_CHOOSE_REASON, arrReasonDTO);
        }
        setContentView(R.layout.layout_list_search_hthm);
        unit();

	}

	public void unit() {
		edt_search= (EditText) findViewById(R.id.edt_search);
		edt_search.setHint(getApplicationContext().getString(R.string.searchreasion));
		lvReason = (ListView) findViewById(R.id.lv_hthm);
		onSearchBeans();
		edt_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				onSearchBeans();
			}
		});

		lvReason.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                ReasonDTO reasonDTO = (ReasonDTO) arg0.getAdapter().getItem(arg2);
                Intent data = new Intent();
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("reasonDTO", reasonDTO);
                data.putExtras(mBundle);
                setResult(RESULT_OK, data);

                AutoCompleteUtil.getInstance(FragmentChooseReason.this).addToSuggestionList(AUTO_CHOOSE_REASON, reasonDTO.getName());

                finish();
            }
        });
    }

	private void onSearchBeans() {


		String keySearch = edt_search.getText().toString().trim();
		if (keySearch.length() == 0) {
			GetReansonDTOAdapter itemsAdapter = new GetReansonDTOAdapter(arrReasonDTO, FragmentChooseReason.this);
			lvReason.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();

		} else {
			ArrayList<ReasonDTO> items = new ArrayList<ReasonDTO>();
			for (ReasonDTO promotionTypeBeans : arrReasonDTO) {
				if (
						(promotionTypeBeans.toString() != null && promotionTypeBeans.toString().toLowerCase().contains(keySearch.toLowerCase()))) {
					items.add(promotionTypeBeans);
				}
			}
			GetReansonDTOAdapter itemsAdapter = new GetReansonDTOAdapter(items, FragmentChooseReason.this);
			lvReason.setAdapter(itemsAdapter);
			itemsAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
//		addActionBar();
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
				CommonActivity.callphone(FragmentChooseReason.this,
						Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(R.string.searchReason));
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
