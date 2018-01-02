package com.viettel.bss.viettelpos.v4.sale.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelByStaffAdapter;
import com.viettel.bss.viettelpos.v4.sale.object.StockModelByStaff;

import java.util.ArrayList;

public class FragmentChooseStockMode extends FragmentCommon implements
		StockModelByStaffAdapter.OnChangeQuantity {

	private Activity activity;
    private ArrayList<StockModelByStaff> lstStockModel;
	private StockModelByStaffAdapter adapter;

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.chon_mat_hang);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_channel_order;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	@Override
	public void unit(View v) {
        ListView lvStockModel = (ListView) v.findViewById(R.id.lvStockModel);
        EditText edtSearch = (EditText) v.findViewById(R.id.edtSearch);
		v.findViewById(R.id.tvOrderId).setVisibility(View.GONE);
		v.findViewById(R.id.btnAdd).setVisibility(View.GONE);
		v.findViewById(R.id.lnButton).setVisibility(View.GONE);
		v.findViewById(R.id.tvQuantitySaling).setVisibility(View.GONE);
		v.findViewById(R.id.imgDeleteChannel).setVisibility(View.GONE);

		Bundle bundle = getArguments();
		if (bundle != null) {
			lstStockModel = (ArrayList) bundle.getSerializable("lstStockModel");

		}
		if (lstStockModel == null || lstStockModel.isEmpty()) {
			return;
		}

		adapter = new StockModelByStaffAdapter(activity, lstStockModel,
				StockModelByStaffAdapter.SHOW_TYPE_CHOOSE_GOODS, null,
				FragmentChooseStockMode.this);
		lvStockModel.setAdapter(adapter);
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				adapter.searchInput(s.toString());
			}
		});
	}

	private Boolean isFormatted = false;

	@Override
	public void onChangeQuantity(final StockModelByStaff stockModel) {
		final Dialog dialog = new Dialog(getActivity());
		final EditText edtQuantity;
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.sale_input_quantity_dialog);
		TextView tvDialogTitle = (TextView) dialog
				.findViewById(R.id.tvDialogTitle);
		tvDialogTitle.setText(stockModel.getStockModelCode() + " - "
				+ stockModel.getName());
		edtQuantity = (EditText) dialog.findViewById(R.id.edtQuantity);
		if (stockModel.getQuantity() != null && stockModel.getQuantity() > 0) {
			edtQuantity.setText(stockModel.getQuantity() + "");
		}

		edtQuantity.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!isFormatted) {
					isFormatted = true;
					edtQuantity.setText(StringUtils.formatMoney(s.toString()
							.replaceAll("\\.", "")));
					edtQuantity.setSelection(edtQuantity.getText().toString()
							.length());
					isFormatted = false;
				}
			}
		});
		dialog.findViewById(R.id.btnOk).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {

						String quantity = edtQuantity.getText().toString();
						try {
							stockModel.setQuantity(Long.parseLong(quantity
									.replace(".", "")));
						} catch (Exception e) {
							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.quantity_not_valid),
									Toast.LENGTH_LONG).show();
							return;
						}

						if (stockModel.getQuantity().intValue() == 0) {
							Toast.makeText(
									getActivity(),
									getResources()
											.getString(
													R.string.quantity_must_greate_than_0),
									Toast.LENGTH_LONG).show();
							return;
						}
						dialog.dismiss();
						Intent i = new Intent();
						i.putExtra("stockModel", stockModel);
						getTargetFragment().onActivityResult(
								getTargetRequestCode(), Activity.RESULT_OK, i);
						getActivity().onBackPressed();
					}
				});
		dialog.findViewById(R.id.btnViewSaleTrans).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						CommonActivity.hideSoftKeyboard(MainActivity
								.getInstance());
						dialog.dismiss();
					}
				});
		dialog.getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		dialog.show();
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub

	}

}
