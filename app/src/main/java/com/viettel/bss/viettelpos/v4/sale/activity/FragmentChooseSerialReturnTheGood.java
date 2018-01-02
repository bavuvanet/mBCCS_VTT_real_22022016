package com.viettel.bss.viettelpos.v4.sale.activity;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.adapter.SerialSaleReturnAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.SerialSaleReturnAdapter.OnCancelSerialSaleReturnListener;
import com.viettel.bss.viettelpos.v4.sale.object.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransSerialDTO;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentChooseSerialReturnTheGood extends Fragment
		implements OnCancelSerialSaleReturnListener, OnClickListener {

	private View mView;
	private Activity mActivity;
	private EditText edtNumberReturn;
	private ListView lvSerial;
	private Button btnAddSerial;
    private ProductOfferingDTO stockModel;

	private TextView tvNameStock;
	private TextView tvNumberStock;
	private TextView tvSerialNotEnough;
	private TextView tvListSerialReturn;
	private boolean isOntextChange = true;

	private ArrayList<StockTransSerialDTO> mListSerial = new ArrayList<>();
	private SerialSaleReturnAdapter serialSelectionAdapter;

	private ArrayList<StockTransSerialDTO> mListSerialReturn = new ArrayList<>();
	private SerialSaleReturnAdapter serialReturnAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

        Bundle mBundle = getArguments();
		mActivity = getActivity();
		if (mBundle != null) {
			stockModel = (ProductOfferingDTO) mBundle.getSerializable("stockModel");
			mListSerial = (ArrayList<StockTransSerialDTO>) mBundle.getSerializable("listSerial");
			if (mListSerial == null) {
				mListSerial = new ArrayList<>();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_choose_serial_return_the_good, container, false);
			edtNumberReturn = (EditText) mView.findViewById(R.id.edtNumberReturn);
			lvSerial = (ListView) mView.findViewById(R.id.lvSerial);
			btnAddSerial = (Button) mView.findViewById(R.id.btnAddSerial);
			tvNameStock = (TextView) mView.findViewById(R.id.tvNameStock);
			tvNumberStock = (TextView) mView.findViewById(R.id.tvNumberStock);
			tvSerialNotEnough = (TextView) mView.findViewById(R.id.tvSerialNotEnough);
			tvListSerialReturn = (TextView) mView.findViewById(R.id.tvListSerialReturn);

		}

		btnAddSerial.setOnClickListener(this);
		checkNumberSerial();
		if (stockModel != null) {
			String strNameStock = "";
			if (stockModel.getName() != null && stockModel.getName().length() > 0) {
				strNameStock = stockModel.getName();
			}

			if (stockModel.getCode() != null && stockModel.getCode().length() > 0) {
				strNameStock = strNameStock + " - " + stockModel.getCode();
			}
			tvNameStock.setText(strNameStock);
			tvNumberStock.setText(getString(R.string.title_number_qualiti_isue) + ": " + stockModel.getQuantity());
		}

		if (stockModel.getCheckSerial() == 0) {
			tvListSerialReturn.setVisibility(View.GONE);
			lvSerial.setVisibility(View.GONE);
			mView.findViewById(R.id.vLine).setVisibility(View.GONE);
		}

		if (stockModel.getQuantitySaling() > 0) {
			isOntextChange = false;
			edtNumberReturn.setText(StringUtils.formatMoney(stockModel.getQuantitySaling().toString()) + "");
			if (stockModel.getCheckSerial() == 1) {
				long numberSerial = 0L;
				for (StockTransSerialDTO serial : mListSerial) {
					numberSerial = numberSerial + serial.getNumber();
				}
				if (numberSerial < stockModel.getQuantitySaling()) {
					tvSerialNotEnough.setVisibility(View.VISIBLE);
					tvSerialNotEnough.setText(getString(R.string.title_number_not_enought) + " "
							+ (stockModel.getQuantitySaling() - numberSerial));
					btnAddSerial.setTag("1");
					btnAddSerial.setText(getString(R.string.addSerial));
				}
			}
			if (mListSerial != null && mListSerial.size() > 0) {
				serialSelectionAdapter = new SerialSaleReturnAdapter(mActivity, mListSerial,
						FragmentChooseSerialReturnTheGood.this, false);
				lvSerial.setAdapter(serialSelectionAdapter);
			}
		}

		return mView;
	}

	private ArrayList<StockTransSerialDTO> selectList(int numberSerial, ProductOfferingDTO stockModel) {
		ArrayList<StockTransSerialDTO> selectListSerial = new ArrayList<>();

		long count = 0;

		for (StockTransSerialDTO serial : stockModel.getListStockTransSerialDTOs()) {
			if ((numberSerial - count) == serial.getNumber()) {
				selectListSerial.add(serial);
				break;
			} else if (serial.getNumber() > (numberSerial - count)) {
				Long lastToSerial = Long.parseLong(serial.getFromSerial()) + numberSerial - count - 1;
				StockTransSerialDTO lastSerial = new StockTransSerialDTO(serial.getFromSerial(),
						lastToSerial.toString());
				selectListSerial.add(lastSerial);
				break;
			} else {
				count += serial.getNumber();
				selectListSerial.add(serial);
			}
		}

		return selectListSerial;
	}

	private String inputSerial;

	private void checkNumberSerial() {

		edtNumberReturn.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				Log.d("Log", "onTextChanged 1");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				inputSerial = edtNumberReturn.getText().toString().trim();

			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!isOntextChange) {
					isOntextChange = true;
					return;
				}

				String strNumber = edtNumberReturn.getText().toString().trim();
				if (stockModel.getCheckSerial() == 0) {
					validateNotSerial(strNumber);
				} else {
					validateNumberSerial(strNumber);
				}

			}
		});
	}

	private void validateNotSerial(String strNumber) {
		if (!strNumber.equalsIgnoreCase("")) {
			int numberSerial = Integer.parseInt(strNumber);
			if (numberSerial > stockModel.getQuantity()) {
				Toast.makeText(getActivity(), getResources().getString(R.string.valueGreateThanStock),
						Toast.LENGTH_LONG).show();
				Log.d("Log", "input Serial: " + inputSerial);
				edtNumberReturn.setText(inputSerial);

			}
		}
	}

	private void validateNumberSerial(String strNumber) {
		if (strNumber.equalsIgnoreCase("")) {
			mListSerial.clear();
			if (serialSelectionAdapter != null) {
				serialSelectionAdapter.notifyDataSetChanged();
				tvSerialNotEnough.setVisibility(View.GONE);
				btnAddSerial.setTag("0");
				btnAddSerial.setText(getString(R.string.alert_dialog_ok));
			}
			return;
		}

		int numberSerial = Integer.parseInt(edtNumberReturn.getText().toString().trim());
		Log.d("Log", "number serial search" + numberSerial);

		if (numberSerial == 0) {
			mListSerial.clear();
			if (serialSelectionAdapter != null) {
				serialSelectionAdapter.notifyDataSetChanged();
				tvSerialNotEnough.setVisibility(View.GONE);
			}
		} else if (numberSerial > stockModel.getQuantity()) {
			Toast.makeText(getActivity(), getResources().getString(R.string.valueGreateThanStock), Toast.LENGTH_LONG)
					.show();
			Log.d("Log", "input Serial: " + inputSerial);
			edtNumberReturn.setText(inputSerial);
		} else {
			mListSerial.clear();
			if (serialSelectionAdapter != null) {
				serialSelectionAdapter.notifyDataSetChanged();
			}
			mListSerial = selectList(numberSerial, stockModel);
			serialSelectionAdapter = new SerialSaleReturnAdapter(mActivity, mListSerial,
					FragmentChooseSerialReturnTheGood.this, false);
			lvSerial.setAdapter(serialSelectionAdapter);
		}
	}

	@Override
	public void onCancelSerial(StockTransSerialDTO _serial) {
		mListSerial.remove(_serial);
		serialSelectionAdapter.notifyDataSetChanged();

		long numberSerial = 0;

		for (StockTransSerialDTO serial : mListSerial) {
			numberSerial += serial.getNumber();
		}
		int numberSerialInput = Integer.parseInt(edtNumberReturn.getText().toString().trim());
		if (numberSerial < numberSerialInput) {
			btnAddSerial.setText(getString(R.string.addSerial));
			btnAddSerial.setTag("1");
			tvSerialNotEnough.setVisibility(View.VISIBLE);
			tvSerialNotEnough
					.setText(getString(R.string.title_number_not_enought) + " " + (numberSerialInput - numberSerial));
		}
	}

	private ArrayList<StockTransSerialDTO> fillterSerialSelection(
			ArrayList<StockTransSerialDTO> stockListSerial,
			ArrayList<StockTransSerialDTO> listSelectSerial) {
		ArrayList<StockTransSerialDTO> result = new ArrayList<StockTransSerialDTO>();
		// result.addAll(stockListSerial);
		for (StockTransSerialDTO item : stockListSerial) {
			StockTransSerialDTO tmp = new StockTransSerialDTO(item.getFromSerial(), item.getToSerial());
			result.add(tmp);
		}
		for (StockTransSerialDTO selected : listSelectSerial) {
			for (int i = 0; i < result.size(); i++) {
				StockTransSerialDTO serial = result.get(i);
				if (selected.getFromSerial().equals(serial.getFromSerial())
						&& selected.getToSerial().equals(serial.getToSerial())) {
					result.remove(i);
					break;
				}

				if (serial.isContains(selected)) {
					if(StringUtils.isNumberic(selected.getFromSerial())&&StringUtils.isNumberic(selected.getToSerial())
							&&StringUtils.isNumberic(serial.getFromSerial())&&StringUtils.isNumberic(selected.getToSerial())){
						long selectFrom = Long.parseLong(selected.getFromSerial());
						long selectTo = Long.parseLong(selected.getToSerial());
						long from = Long.parseLong(serial.getFromSerial());
						long to = Long.parseLong(serial.getToSerial());
						if (selectFrom == from) {
							serial.setFromSerial(selectTo + 1 + "");
							break;
						}
						if (selectTo == to) {
							serial.setToSerial(selectFrom - 1 + "");
							break;
						}
						serial.setFromSerial(selectTo + 1 + "");
						StockTransSerialDTO newSerial = new StockTransSerialDTO();
						newSerial.setFromSerial(serial.getFromSerial());
						newSerial.setToSerial(selectTo + 1 + "");
						result.add(i, newSerial);
					} else {
						String selectFrom = selected.getFromSerial();
						String selectTo = selected.getToSerial();


						serial.setFromSerial(selectTo);
						StockTransSerialDTO newSerial = new StockTransSerialDTO();
						newSerial.setFromSerial(serial.getFromSerial());
						newSerial.setToSerial(selectTo);
						result.add(i, newSerial);
					}

				}
			}
		}

		return result;
	}
//	private ArrayList<StockTransSerialDTO> fillterSerialSelection(ArrayList<StockTransSerialDTO> stockListSerial,
//			ArrayList<StockTransSerialDTO> listSelectSerial) {
//		ArrayList<StockTransSerialDTO> filterListSerial = new ArrayList<>();
//		for (StockTransSerialDTO mSerial : stockListSerial) {
//			boolean isContain = false;
//			for (StockTransSerialDTO selectSerial : listSelectSerial) {
//				try {
//
//					String strFromSerial = mSerial.getFromSerial();
//					String strToSerial = mSerial.getToSerial();
//
//					String strFromSerialSelect = selectSerial.getFromSerial();
//					String strToSerialSelect = selectSerial.getToSerial();
//
//					Long fromSerial = 0L;
//					Long toSerial = 0L;
//					Long fromSerialSelect = 0L;
//					Long toSerialSelect = 0L;
//
//					if (strFromSerial.equals(strToSerial) && strFromSerialSelect.equals(strToSerialSelect)
//							&& strFromSerial.equals(strToSerialSelect)) {
//						isContain = true;
//						break;
//					}
//
//					if (strFromSerial != null && !strFromSerial.equals("")) {
//						fromSerial = Long.parseLong(strFromSerial);
//					}
//
//					if (strFromSerial != null && !strFromSerial.equals("")) {
//						toSerial = Long.parseLong(strToSerial);
//					}
//
//					if (strFromSerial != null && !strFromSerial.equals("")) {
//						fromSerialSelect = Long.parseLong(strFromSerialSelect);
//					}
//
//					if (strFromSerial != null && !strFromSerial.equals("")) {
//						toSerialSelect = Long.parseLong(strToSerialSelect);
//					}
//
//					if ((fromSerial == fromSerialSelect) && (toSerial == toSerialSelect)) {
//						// truong hop serial trung voi serial lay
//						isContain = true;
//						break;
//					} else if ((fromSerial == fromSerialSelect) && (toSerial > toSerialSelect)) {
//						// truong hop from serial selection trung voi from
//						// serial
//						// goc con to serial selection be hon
//						// to serial goc
//
//						Long lastToSerial = toSerialSelect + 1;
//						StockTransSerialDTO lastSerial = new StockTransSerialDTO(lastToSerial.toString(),
//								mSerial.getToSerial());
//						filterListSerial.add(lastSerial);
//						isContain = true;
//						break;
//
//					} else if ((fromSerial < fromSerialSelect) && (toSerial == toSerialSelect)) {
//						//
//						Long lastToSerial = fromSerialSelect - 1;
//						StockTransSerialDTO lastSerial = new StockTransSerialDTO(mSerial.getFromSerial().toString(),
//								lastToSerial.toString());
//						filterListSerial.add(lastSerial);
//						isContain = true;
//						break;
//					} else if ((fromSerial < fromSerialSelect) && (toSerial > toSerialSelect)) {
//
//                        Long toSerial1 = fromSerialSelect - 1;
//
//						StockTransSerialDTO lastSerial1 = new StockTransSerialDTO(fromSerial.toString(),
//								toSerial1.toString());
//						filterListSerial.add(lastSerial1);
//
//						Long fromSerial2 = toSerialSelect + 1;
//
//                        StockTransSerialDTO lastSerial2 = new StockTransSerialDTO(fromSerial2.toString(),
//								toSerial.toString());
//						filterListSerial.add(lastSerial2);
//
//						isContain = true;
//						break;
//					}
//
//				} catch (Exception ignored) {
//
//				}
//
//			}
//
//			if (!isContain) {
//				filterListSerial.add(mSerial);
//			}
//		}
//
//		return filterListSerial;
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btnAddSerial:
			if (btnAddSerial.getTag().equals("1")) {
				Long numberSelectionSerial = 0L;
				for (StockTransSerialDTO serial : mListSerial) {
					numberSelectionSerial += serial.getNumber();
				}
				int numberSerialInput = Integer.parseInt(edtNumberReturn.getText().toString().trim());
				Long numberSerialReturn = numberSerialInput - numberSelectionSerial;
				showSerialTolistSelected(numberSerialReturn);
			} else {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				if (stockModel.getCheckSerial() == 1) {
					bundle.putSerializable("listSerial", mListSerial);
				} else {
					String strNumberSerial = edtNumberReturn.getText().toString().trim().replace(".", "");
					if (strNumberSerial.length() > 0) {
						bundle.putString("strNumberSerial", strNumberSerial);
					} else {
						CommonActivity.createAlertDialog(mActivity, getString(R.string.message_please_input_serial),
								getString(R.string.app_name)).show();
						return;
					}
				}
				intent.putExtras(bundle);
				getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
				mActivity.onBackPressed();
			}
			break;

		default:
			break;
		}
	}

	private Long numberSerial;
	private Dialog dialogListSerialReturn;
	private ListView lvSerialReturn;
	private TextView tvSerialNotEnoughReturn;

	private void showSerialTolistSelected(Long numberSerialSelect) {
		numberSerial = numberSerialSelect;
		mListSerialReturn = fillterSerialSelection(stockModel.getListStockTransSerialDTOs(), mListSerial);
		dialogListSerialReturn = new Dialog(mActivity);
		dialogListSerialReturn.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogListSerialReturn.setContentView(R.layout.layout_list_serial_return);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialogListSerialReturn.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialogListSerialReturn.getWindow().setAttributes(lp);

		final EditText edtSearchSerial = (EditText) dialogListSerialReturn.findViewById(R.id.edtSearchSerial);
		tvSerialNotEnoughReturn = (TextView) dialogListSerialReturn.findViewById(R.id.tvSerialNotEnoughReturn);

		tvSerialNotEnoughReturn.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);
		tvSerialNotEnough.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);
		lvSerialReturn = (ListView) dialogListSerialReturn.findViewById(R.id.lvSerial);
		serialReturnAdapter = new SerialSaleReturnAdapter(mActivity, mListSerialReturn,
				FragmentChooseSerialReturnTheGood.this, true);
		lvSerialReturn.setAdapter(serialReturnAdapter);

		edtSearchSerial.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String strSerialNumber = edtSearchSerial.getText().toString();
				serialReturnAdapter.SearchInput(strSerialNumber, stockModel);
			}
		});

		lvSerialReturn.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				StockTransSerialDTO serial = mListSerialReturn.get(position);
				if (numberSerial > serial.getNumber()) {
					mListSerial.add(serial);
					serialSelectionAdapter.notifyDataSetChanged();

					mListSerialReturn = fillterSerialSelection(stockModel.getListStockTransSerialDTOs(), mListSerial);
					serialReturnAdapter = new SerialSaleReturnAdapter(mActivity, mListSerialReturn,
							FragmentChooseSerialReturnTheGood.this, true);
					lvSerialReturn.setAdapter(serialReturnAdapter);
					serialReturnAdapter.notifyDataSetChanged();
					numberSerial -= serial.getNumber();
				} else if (numberSerial == serial.getNumber()) {
					mListSerial.add(serial);
					serialSelectionAdapter.notifyDataSetChanged();
					numberSerial = 0L;
					btnAddSerial.setText(getString(R.string.ok));
					btnAddSerial.setTag("0");
					tvSerialNotEnough.setVisibility(View.GONE);
					dialogListSerialReturn.dismiss();
					CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
				} else {
					showDialogSelectFromAndToSerial(serial);
				}

				tvSerialNotEnoughReturn.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);
				tvSerialNotEnough.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);
			}
		});

		dialogListSerialReturn.show();
	}

	private TextView tvSerialNotEnought2;

	private void showDialogSelectFromAndToSerial(final StockTransSerialDTO serial) {
		final Dialog dialog = new Dialog(mActivity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_dialog_sellect_from_to_serial);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(lp);

		Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
		Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
		TextView tvSerial = (TextView) dialog.findViewById(R.id.tvSerial);
		final EditText edtFromSerial = (EditText) dialog.findViewById(R.id.edtFromSerial);
		final EditText edToSerial = (EditText) dialog.findViewById(R.id.edToSerial);
		tvSerialNotEnought2 = (TextView) dialog.findViewById(R.id.tvSerialNotEnought2);
		tvSerialNotEnought2.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);

		edtFromSerial.setText(serial.getFromSerial());
		Long toSerial = Long.parseLong(serial.getFromSerial()) + numberSerial - 1;
		edToSerial.setText(toSerial.toString());

		if (serial.getFromSerial().equals(serial.getToSerial())) {
			tvSerial.setText(getString(R.string.list_serial_parent) + " " + serial.getFromSerial());
		} else {
			tvSerial.setText(getString(R.string.list_serial_parent) + " " + serial.getFromSerial() + " - "
					+ serial.getToSerial());
		}

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String strFromSerial = edtFromSerial.getText().toString().trim();
				String strToSerial = edToSerial.getText().toString().trim();

				Long countSerialSelect = 0L;
				if (!strFromSerial.equalsIgnoreCase("") && !strToSerial.equalsIgnoreCase("")) {
					countSerialSelect = Long.parseLong(strToSerial) - Long.parseLong(strFromSerial) + 1;
				}

				Dialog dialogError = null;
				if (strFromSerial.equalsIgnoreCase("")) {
					dialogError = CommonActivity.createAlertDialog(mActivity,
							getString(R.string.message_please_input_from_serial), getString(R.string.app_name));
				} else if (strToSerial.equalsIgnoreCase("")) {
					dialogError = CommonActivity.createAlertDialog(mActivity,
							getString(R.string.message_please_input_to_serial), getString(R.string.app_name));
				} else if (Long.parseLong(strFromSerial) < Long.parseLong(serial.getFromSerial())) {
					dialogError = CommonActivity.createAlertDialog(mActivity, getString(R.string.serial_out_of_range),
							getString(R.string.app_name));
				} else if (Long.parseLong(strToSerial) > Long.parseLong(serial.getToSerial())) {
					dialogError = CommonActivity.createAlertDialog(mActivity, getString(R.string.serial_out_of_range),
							getString(R.string.app_name));
				} else if (Long.parseLong(strFromSerial) > Long.parseLong(strToSerial)) {
					dialogError = CommonActivity.createAlertDialog(mActivity,
							getString(R.string.from_serial_less_than_to_serial), getString(R.string.app_name));
				} else if (countSerialSelect > numberSerial) {
					dialogError = CommonActivity.createAlertDialog(mActivity,
							getString(R.string.number_serial_sellect_less_than_to_serial),
							getString(R.string.app_name));
				}

				if (dialogError != null) {
					dialogError.show();
					return;
				}
				StockTransSerialDTO serial = new StockTransSerialDTO(strFromSerial, strToSerial);
				mListSerial.add(serial);
				serialSelectionAdapter.notifyDataSetChanged();

//				Log.i("DATA","countSerialSelect: "+countSerialSelect+" numberSerial: "+numberSerial);

				if (countSerialSelect < numberSerial) {
					mListSerialReturn = fillterSerialSelection(stockModel.getListStockTransSerialDTOs(), mListSerial);
					Log.d("Log", "count list serial return: " + mListSerialReturn.size());
					serialReturnAdapter = new SerialSaleReturnAdapter(mActivity, mListSerialReturn,
							FragmentChooseSerialReturnTheGood.this, true);
					lvSerialReturn.setAdapter(serialReturnAdapter);
					numberSerial -= countSerialSelect;
				} else if (countSerialSelect.equals(numberSerial)) {
					numberSerial = 0L;
					btnAddSerial.setText(getString(R.string.ok));
					btnAddSerial.setTag("0");
					tvSerialNotEnough.setVisibility(View.GONE);
					dialogListSerialReturn.dismiss();
					CommonActivity.hideSoftKeyboard(MainActivity.getInstance());
				}

				tvSerialNotEnoughReturn.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);
				tvSerialNotEnough.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);
				tvSerialNotEnought2.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);

				dialog.dismiss();
			}
		});

		dialog.show();
	}

}
