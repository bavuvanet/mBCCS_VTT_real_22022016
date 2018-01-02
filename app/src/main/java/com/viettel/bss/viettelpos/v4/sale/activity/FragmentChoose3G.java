package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

import java.util.ArrayList;

public class FragmentChoose3G extends Fragment implements OnClickListener {

	private View mView;
	private EditText edtSearch;
	private ListView lvStockModel;
	private ArrayList<StockModel> lstStockModel = new ArrayList<>();
	private Button btnHome;
	private final ArrayList<StockModel> lstStockModelBK = new ArrayList<>();
	private ArrayAdapter<StockModel> stockModelAdapter;
	private Dialog selectSerialDialog;
	private EditText edtSearchSerial;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle mBundle = getArguments();
		lstStockModel = (ArrayList<StockModel>) mBundle
				.getSerializable("arrStockModel");
		for (StockModel stockModel : lstStockModel) {
			lstStockModelBK.add(stockModel);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_sale_choose_3g, container,
					false);
			lvStockModel = (ListView) mView.findViewById(R.id.lvStockModel);
			edtSearch = (EditText) mView.findViewById(R.id.edtSearch);

			edtSearch = (EditText) mView.findViewById(R.id.edtSearch);
		}
		stockModelAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, lstStockModelBK);
		lvStockModel.setAdapter(stockModelAdapter);

		lvStockModel.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final StockModel stockModel = lstStockModelBK.get(position);
				final ArrayList<String> lstSerial = new ArrayList<>();
				for (String string : stockModel.getLstSerial()) {
					lstSerial.add(string);
				}
				selectSerialDialog = new Dialog(getActivity());
				selectSerialDialog
						.requestWindowFeature(Window.FEATURE_NO_TITLE);
				selectSerialDialog
						.setContentView(R.layout.sale_availabel_serial_layout);
				TextView tvStockModelName = (TextView) selectSerialDialog
						.findViewById(R.id.tvStockModelName);
				tvStockModelName.setText(stockModel.getStockModelName());
				tvStockModelName.setVisibility(View.VISIBLE);
				final ArrayAdapter<String> serialAdapter = new ArrayAdapter<>(
                        getActivity(), android.R.layout.simple_list_item_1,
                        lstSerial);
				ListView lvAvailable = (ListView) selectSerialDialog
						.findViewById(R.id.lvAvailableSerial);
				lvAvailable.setAdapter(serialAdapter);
				lvAvailable.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int serialPosition, long arg3) {
						Intent i = new Intent();
						i.putExtra("stockModelId", stockModel.getStockModelId());
						i.putExtra("serial3G", lstSerial.get(serialPosition));
						getTargetFragment().onActivityResult(
								getTargetRequestCode(), Activity.RESULT_OK, i);
						selectSerialDialog.dismiss();
						getActivity().onBackPressed();
					}
				});
				edtSearchSerial = (EditText) selectSerialDialog
						.findViewById(R.id.edtSearchSerial);
				edtSearchSerial.setInputType(InputType.TYPE_CLASS_TEXT);
				edtSearchSerial.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {
					}

					@Override
					public void afterTextChanged(Editable editable) {
						String strKeySearch = editable.toString();
						lstSerial.clear();
						if (CommonActivity.isNullOrEmpty(strKeySearch)) {
							for (String string : stockModel.getLstSerial()) {
								lstSerial.add(string);
							}
						} else {
							for (String string : stockModel.getLstSerial()) {
								if (string.toLowerCase().contains(
										strKeySearch.toLowerCase().trim())) {
									lstSerial.add(string);
								}
							}
						}
						serialAdapter.notifyDataSetChanged();

					}
				});
				selectSerialDialog.show();

			}
		});

		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				onSearchStockModel();

			}
		});

		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.choose_serial_3g);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btn_search:
			onSearchStockModel();
			break;
		default:
			break;
		}
	}

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	@SuppressWarnings("unused")
	private void onSearchStockModel() {
		String strKeySearch = edtSearch.getText().toString();
		lstStockModelBK.clear();
		if (CommonActivity.isNullOrEmpty(strKeySearch)) {
			for (StockModel stockModel : lstStockModel) {
				lstStockModelBK.add(stockModel);
			}
		} else {
			for (StockModel stockModel : lstStockModel) {
				if (stockModel.getStockModelName().toLowerCase()
						.contains(strKeySearch.toLowerCase().trim())) {
					lstStockModelBK.add(stockModel);
				}
			}
		}
		if (stockModelAdapter != null) {
			stockModelAdapter.notifyDataSetChanged();
		} else {
			stockModelAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, lstStockModelBK);
			lvStockModel.setAdapter(stockModelAdapter);
		}
	}

}
