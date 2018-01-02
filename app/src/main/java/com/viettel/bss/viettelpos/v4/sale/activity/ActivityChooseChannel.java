package com.viettel.bss.viettelpos.v4.sale.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.sale.adapter.StaffAdapter;
import com.viettel.bss.viettelpos.v4.sale.business.ChannelTypeBusiness;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.ChannelTypeObject;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityChooseChannel extends AppCompatActivity implements OnClickListener{
	private View btnOK = null;
	private View btnCancel;
	private View prb;
	private ListView lvStaff;
    private Long channelTypeId;
	private Double x;
	private Double y;
	private ChannelTypeObject channelType;
	private EditText edtStaffFilter;
	private ArrayList<Staff> lstStaff = new ArrayList<>();
	private StaffAdapter adapter;
	private LoadStaffByChannel loadStaff;
    private TextView txtNameActionBar;

	private boolean firstLoad = true;
	private Button btnHome;
	private Long pointOfSale = null;
	private String[] replace;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_channel_layout);

        ButterKnife.bind(this);

        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);

		btnOK = findViewById(R.id.btnOk);
		btnOK.setOnClickListener(this);
		btnCancel = findViewById(R.id.btnViewSaleTrans);
		btnCancel.setOnClickListener(this);
		replace = getResources().getStringArray(
				R.array.dUnicode);
		prb = findViewById(R.id.prbLoadStaff);
		lvStaff = (ListView) findViewById(R.id.lvStaff);
		edtStaffFilter = (EditText) findViewById(R.id.edtSearchStaff);
        Spinner spiChannel = (Spinner) findViewById(R.id.spiChannel);
		final List<ChannelTypeObject> lstChannel = ChannelTypeBusiness
				.getAllChannel(this);
		if (lstChannel != null) {
			ChannelTypeObject channel = new ChannelTypeObject();
			channel.setChannelTypeId(-1L);
			channel.setName(getResources().getString(
					R.string.selectAllChannel));
			lstChannel.add(0, channel);
		}
		ArrayAdapter<String> channelApdater = new ArrayAdapter<>(
                this, R.layout.simple_list_item_single_row,
                R.id.text1);
		ArrayList<String> arrChannel = new ArrayList<>();
		for (ChannelTypeObject channelTypeObject : lstChannel) {
			channelApdater.add(channelTypeObject.getName());
			arrChannel.add(channelTypeObject.getName());
		}

		spiChannel.setAdapter(channelApdater);

		spiChannel.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				ChannelTypeObject item = lstChannel.get(arg2);
				pointOfSale = null;
				if (item.getChannelTypeId().compareTo(-1L) == 0) {
					channelTypeId = null;
					channelType = null;
					
				} else {
					channelTypeId = item.getChannelTypeId();
					channelType = item;
					// channel_type_id =80043: kenh diem ban fix
					// channel_type_id =10,
					// point_of_sale = 1
					if (Constant.CHANNEL_TYPE_POS.compareTo(channelTypeId) == 0) {
						channelTypeId = Constant.CHANNEL_TYPE_COLLABORATOR;
						pointOfSale = Constant.POINT_OF_SALE_TYPE;
					} else if (Constant.CHANNEL_TYPE_COLLABORATOR
							.compareTo(channelTypeId) == 0) {
						// channel_type_id =10: kenh NVDB fix
						// channel_type_id =10,
						// point_of_sale = 2
						pointOfSale = Constant.POINT_OF_SALE_COLLABORATOR;
					}
				}

				// lstStaff.clear();
				// loadStaff = new LoadStaffByChannel();
				// prb.setVisibility(View.VISIBLE);
				// lvStaff.setVisibility(View.GONE);
				// loadStaff.execute();
				edtStaffFilter.setText("");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		lvStaff.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Intent i = new Intent();
				Staff staff = lstStaff.get(arg2);
				i.putExtra("channelType", channelType);
				i.putExtra("staff", staff);
				setResult(Activity.RESULT_OK, i);
				finish();
			}
		});
		edtStaffFilter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (loadStaff != null) {
					loadStaff.cancel(true);
				}
				lstStaff.clear();
				loadStaff = new LoadStaffByChannel();
				loadStaff.execute();
			}
		});
	}



	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setTitle(getResources().getString(
                R.string.choose_channel));
	}

	@Override
	public void onStart() {
		Log.e("tag", "onStart");
		super.onStart();
	}

	@Override
	public void onPause() {
		Log.e("tag", "onPause");
		// CommonActivity.hideKeyboard(context)
		super.onPause();
	}

	@Override
	public void onStop() {
		Log.e("tag", "onStop");
		super.onStop();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		if (loadStaff != null) {
			loadStaff.cancel(true);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == btnOK) {
			Intent i = new Intent();

			i.putExtra("channelTypeId", channelTypeId);
			i.putExtra("channelType", channelType);
			setResult(Activity.RESULT_OK, i);
			finish();
//			getTargetFragment().onActivityResult(getTargetRequestCode(),
//					Activity.RESULT_OK, i);
//			getActivity().onBackPressed();

		} else if (v == btnCancel) {
			setResult(Activity.RESULT_CANCELED, new Intent());
			finish();
		} else {
			switch (v.getId()) {
			case R.id.relaBackHome:
				finish();
				break;
			default:
				break;
			}
		}
	}

	private class LoadStaffByChannel extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... arg0) {
            int count = 100;
            int start = 0;
            lstStaff = StaffBusiness.getStaffByChannel(ActivityChooseChannel.this,
					channelTypeId, pointOfSale, edtStaffFilter.getText()
							.toString(), start, count, x, y, replace);
			return Constant.SUCCESS_CODE;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			prb.setVisibility(View.GONE);
			lvStaff.setVisibility(View.VISIBLE);
			if (firstLoad) {
				if (lstStaff != null && !lstStaff.isEmpty()) {
					adapter = new StaffAdapter(ActivityChooseChannel.this, lstStaff);
					lvStaff.setAdapter(adapter);
					firstLoad = false;
				}
			} else {
				adapter.setLstData(lstStaff);
				adapter.notifyDataSetChanged();
			}
		}
	}

}
