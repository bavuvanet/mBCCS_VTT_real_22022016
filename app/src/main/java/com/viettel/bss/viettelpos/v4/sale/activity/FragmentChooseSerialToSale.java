package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.adapter.SerialAdapterToSale;
import com.viettel.bss.viettelpos.v4.sale.adapter.SerialAdapterToSale.OnCancelSerialSaleReturnListener;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentChooseSerialToSale extends Fragment implements OnCancelSerialSaleReturnListener, OnClickListener {

	private View mView;
	private Activity mActivity;
	private EditText edtNumberReturn;
	private ListView lvSerial;
	private Button btnAddSerial;
    private StockModel stockModel;

	private TextView tvNameStock;
	private TextView tvNumberStock;
	private TextView tvSerialNotEnough;
	private TextView tvListSerialReturn;
	private View vLine;
	private boolean isOntextChange = true;

	private ArrayList<Serial> mListSerial = new ArrayList<>();
	private SerialAdapterToSale serialSelectionAdapter;

	private ArrayList<Serial> mListSerialReturn = new ArrayList<>();
	private SerialAdapterToSale serialReturnAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

        Bundle mBundle = getArguments();
		mActivity = getActivity();
		if (mBundle != null) {
			stockModel = (StockModel) mBundle.getSerializable("stockModel");
			mListSerial = (ArrayList<Serial>) mBundle.getSerializable("listSerial");
			if (mListSerial == null) {
				mListSerial = new ArrayList<>();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_choose_serial_to_sale, container, false);
			edtNumberReturn = (EditText) mView.findViewById(R.id.edtNumberReturn);
			lvSerial = (ListView) mView.findViewById(R.id.lvSerial);
			btnAddSerial = (Button) mView.findViewById(R.id.btnAddSerial);
			tvNameStock = (TextView) mView.findViewById(R.id.tvNameStock);
			tvNumberStock = (TextView) mView.findViewById(R.id.tvNumberStock);
			tvSerialNotEnough = (TextView) mView.findViewById(R.id.tvSerialNotEnough);
			tvListSerialReturn = (TextView) mView.findViewById(R.id.tvListSerialReturn);
		}

		btnAddSerial.setOnClickListener(this);

		if (stockModel != null) {

			if (stockModel.getmListSerial().size() > 0) {
				checkNumberSerial();
			}

			String strNameStock = "";
			if (stockModel.getStockModelName() != null && stockModel.getStockModelName().length() > 0) {
				strNameStock = stockModel.getStockModelName();
			}

			if (stockModel.getStockModelCode() != null && stockModel.getStockModelCode().length() > 0) {
				strNameStock = strNameStock + " - " + stockModel.getStockModelCode();
			}
			tvNameStock.setText(strNameStock);
			tvNumberStock.setText(getString(R.string.title_number_qualiti_isue) + ": " + stockModel.getQuantityIssue());
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

				Long countSerial = 0L;
				for (Serial serial : mListSerial) {
					countSerial += serial.getNumber();
				}
				if (countSerial < stockModel.getQuantitySaling()) {
					tvSerialNotEnough.setVisibility(View.VISIBLE);
					tvSerialNotEnough.setText(getString(R.string.title_number_not_enought) + " "
							+ (stockModel.getQuantitySaling() - countSerial));
					btnAddSerial.setTag("1");
					btnAddSerial.setText(getString(R.string.addSerial));
				}
			}
			if (mListSerial != null && mListSerial.size() > 0) {
				serialSelectionAdapter = new SerialAdapterToSale(mActivity, mListSerial,
						FragmentChooseSerialToSale.this, false);
				lvSerial.setAdapter(serialSelectionAdapter);

			}
		}

		return mView;
	}

	private ArrayList<Serial> selectList(int numberSerial, StockModel stockModel) {
		ArrayList<Serial> selectListSerial = new ArrayList<>();

		long count = 0;

		for (Serial serial : stockModel.getmListSerial()) {
			if ((numberSerial - count) == serial.getNumber()) {
				selectListSerial.add(serial);
				break;
			} else if (serial.getNumber() > (numberSerial - count)) {
				Long lastToSerial = Long.parseLong(serial.getFromSerial()) + numberSerial - count - 1;
				Serial lastSerial = new Serial(serial.getFromSerial(), lastToSerial.toString());
				selectListSerial.add(lastSerial);
				break;
			} else {
				count += serial.getNumber();
				selectListSerial.add(serial);
			}
		}

		if (stockModel.getStockTypeId() == 6) {
			for (Serial serial : selectListSerial) {
				serial.setFromSerial(SaleCommons.normalSerial(serial.getFromSerial()));
				serial.setToSerial(SaleCommons.normalSerial(serial.getToSerial()));
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
		if (!edtNumberReturn.getText().toString().trim().isEmpty()) {
			int numberSerial = Integer.parseInt(edtNumberReturn.getText().toString().trim());
			if (numberSerial > stockModel.getQuantityIssue()) {
				CommonActivity.createAlertDialog(mActivity, getString(R.string.valueGreateThanStock),
						getString(R.string.app_name)).show();
				Log.d("Log", "input Serial: " + inputSerial);
				edtNumberReturn.setText(inputSerial);
			}
		}
	}

	private void validateNumberSerial(String strNumber) {
		if (edtNumberReturn.getText().toString().trim().isEmpty()) {
			mListSerial.clear();
			if (serialSelectionAdapter != null) {
				serialSelectionAdapter.notifyDataSetChanged();
				tvSerialNotEnough.setVisibility(View.GONE);
			}
			btnAddSerial.setTag("0");
			btnAddSerial.setText(getString(R.string.alert_dialog_ok));
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
			btnAddSerial.setTag("0");
			btnAddSerial.setText(getString(R.string.alert_dialog_ok));
		} else if (numberSerial > stockModel.getQuantityIssue()) {
			Toast.makeText(getActivity(), getResources().getString(R.string.valueGreateThanStock), Toast.LENGTH_LONG)
					.show();
			Log.d("Log", "input Serial: " + inputSerial);
			edtNumberReturn.setText(inputSerial);
		} else {
			tvSerialNotEnough.setVisibility(View.VISIBLE);
			tvSerialNotEnough.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);
			btnAddSerial.setText(getString(R.string.addSerial));
			btnAddSerial.setTag("1");
			mListSerial.clear();
			serialSelectionAdapter = new SerialAdapterToSale(mActivity, mListSerial, FragmentChooseSerialToSale.this,
					false);
			lvSerial.setAdapter(serialSelectionAdapter);
		}
	}

	@Override
	public void onCancelSerial(Serial _serial) {
		mListSerial.remove(_serial);
		serialSelectionAdapter.notifyDataSetChanged();

		long numberSerial = 0;

		for (Serial serial : mListSerial) {
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

	private ArrayList<Serial> fillterSerialSelection(List<Serial> stockListSerial, List<Serial> listSelectSerial) {

		ArrayList<Serial> filterListSerial = new ArrayList<>();

		List<Serial> listSerialFilter = new ArrayList<>();
		for (Serial serialStoct : stockListSerial) {
			Serial serialClone = new Serial();
			serialClone.setFromSerial(serialStoct.getFromSerial());
			serialClone.setToSerial(serialStoct.getToSerial());
			listSerialFilter.add(serialClone);
		}

		for (Serial mSerial : listSerialFilter) {
			boolean isContain = false;
			for (Serial selectSerial : listSelectSerial) {

				Log.d("test", "test");

				try {

					String strFromSerial = mSerial.getFromSerial();
					String strToSerial = mSerial.getToSerial();

					String strFromSerialSelect = selectSerial.getFromSerial();
					String strToSerialSelect = selectSerial.getToSerial();

					Long fromSerial = 0L;
					Long toSerial = 0L;
					Long fromSerialSelect = 0L;
					Long toSerialSelect = 0L;

					if (strFromSerial.equals(strToSerial) && strFromSerialSelect.equals(strToSerialSelect)
							&& strFromSerial.equals(strToSerialSelect)) {
						isContain = true;
						continue;
					}

					if (strFromSerial != null && !strFromSerial.equals("")) {
						fromSerial = Long.parseLong(strFromSerial);
					}

					if (strFromSerial != null && !strFromSerial.equals("")) {
						toSerial = Long.parseLong(strToSerial);
					}

					if (strFromSerialSelect != null && !strFromSerialSelect.equals("")) {
						fromSerialSelect = Long.parseLong(strFromSerialSelect);
					}

					if (strToSerialSelect != null && !strToSerialSelect.equals("")) {
						toSerialSelect = Long.parseLong(strToSerialSelect);
					}

					if ((fromSerial.longValue() == fromSerialSelect.longValue())
							&& (toSerial.longValue() == toSerialSelect.longValue())) {
						// truong hop serial trung voi serial lay
						isContain = true;
                    } else if ((fromSerial.longValue() == fromSerialSelect.longValue())
							&& (toSerial > toSerialSelect)) {
						// truong hop from serial selection trung voi from
						// serial
						// goc con to serial selection be hon
						// to serial goc

						Long lastToSerial = toSerialSelect + 1;
						Serial lastSerial = new Serial(lastToSerial.toString(), mSerial.getToSerial());
						mSerial.setFromSerial(lastToSerial.toString());
						filterListSerial.add(lastSerial);
						isContain = true;

                    } else if ((fromSerial < fromSerialSelect)
							&& (toSerial.longValue() == toSerialSelect.longValue())) {
						//
						Long lastToSerial = fromSerialSelect - 1;
						Serial lastSerial = new Serial(mSerial.toString(), lastToSerial.toString());
						filterListSerial.add(lastSerial);
						mSerial.setToSerial(lastToSerial.toString());
						isContain = true;
                    } else if ((fromSerial < fromSerialSelect)
							&& (toSerial > toSerialSelect)) {

                        Long toSerial1 = fromSerialSelect - 1;

						Serial lastSerial1 = new Serial(fromSerial.toString(), toSerial1.toString());

						filterListSerial.add(lastSerial1);

						mSerial.setToSerial(toSerial1.toString());

						Long fromSerial2 = toSerialSelect + 1;

                        Serial lastSerial2 = new Serial(fromSerial2.toString(), toSerial.toString());
						listSerialFilter.add(lastSerial2);

						isContain = true;
                    }

				} catch (Exception e) {
					Log.d("Log", "error description: ");
				}
			}

			if (!isContain) {
				filterListSerial.add(mSerial);
			}
		}

		if (stockModel.getStockTypeId() == 6) {
			for (Serial serial : filterListSerial) {
				serial.setFromSerial(SaleCommons.normalSerial(serial.getFromSerial()));
				serial.setToSerial(SaleCommons.normalSerial(serial.getToSerial()));
			}
		}

		return filterListSerial;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btnAddSerial:
			if (btnAddSerial.getTag().equals("1")) {
				Long numberSelectionSerial = 0L;
				for (Serial serial : mListSerial) {
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

		case R.id.btnGetBarcode:
			edtSearchSerial.setText("");
			showBarcode();
			break;

		default:
			break;
		}
	}

	private void showBarcode() {

		/**************************************
		 * Intents are objects that get passed between applications. In this
		 * example we are going to create an Intent that will ask the Barcode
		 * Scanner app to scan a code and give us the result.
		 ***************************************/

		Intent intent = new Intent("com.google.zxing.client.android.SCAN");

		/*
		 * you can optionally add an extra to the intent that tells it what type
		 * of code its looking for. Like this:
		 * 
		 * intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		 * 
		 * If you don't put that in it will scan all types.
		 */

		try {
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			/**************************************
			 * If BarcodeScanner is not installed we are going to create a
			 * Dialog to prompt the user to install it from the market.
			 ***************************************/
			new AlertDialog.Builder(mActivity).setTitle("WARNING:")
					.setMessage("You don't have Barcode Scanner installed. Please install it.").setCancelable(false) // <---
																														// Whether
																														// or
																														// not
																														// you
																														// want
																														// the
																														// back
																														// button
																														// to
																														// close
																														// the
																														// Dialog.
					.setNeutralButton("Install it now", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							/**************************************
							 * When they click on the button we will open up the
							 * Market and search for BarcodeScanner by package
							 * name.
							 ***************************************/
							Uri uri = Uri.parse("market://search?q=pname:com.google.zxing.client.android");
							startActivity(new Intent(Intent.ACTION_VIEW, uri));
						}
					}).show(); // <--- Remember to show your the dialog once it
								// is created.
		}

	}

	private Long numberSerial;
	private Dialog dialogListSerialReturn;
	private ListView lvSerialReturn;
	private TextView tvSerialNotEnoughReturn;
    private EditText edtSearchSerial;

	private void showSerialTolistSelected(Long numberSerialSelect) {
		numberSerial = numberSerialSelect;
		mListSerialReturn = fillterSerialSelection(stockModel.getmListSerial(), mListSerial);
		dialogListSerialReturn = new Dialog(mActivity);
		dialogListSerialReturn.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogListSerialReturn.setContentView(R.layout.layout_dialog_list_serial_to_sale);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialogListSerialReturn.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialogListSerialReturn.getWindow().setAttributes(lp);

		edtSearchSerial = (EditText) dialogListSerialReturn.findViewById(R.id.edtSearchSerial);
		tvSerialNotEnoughReturn = (TextView) dialogListSerialReturn.findViewById(R.id.tvSerialNotEnoughReturn);
        Button btnGetBarcode = (Button) dialogListSerialReturn.findViewById(R.id.btnGetBarcode);
		// Button btnClose = (Button)
		// dialogListSerialReturn.findViewById(R.id.btnClose);

		tvSerialNotEnoughReturn.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);
		tvSerialNotEnough.setText(getString(R.string.title_number_not_enought) + " " + numberSerial);
		lvSerialReturn = (ListView) dialogListSerialReturn.findViewById(R.id.lvSerial);
		serialReturnAdapter = new SerialAdapterToSale(mActivity, mListSerialReturn, FragmentChooseSerialToSale.this,
				true);
		lvSerialReturn.setAdapter(serialReturnAdapter);

		// btnClose.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// dialogListSerialReturn.dismiss();
		// }
		// });

		edtSearchSerial.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (stockModel != null) {
					String strSerialNumber = edtSearchSerial.getText().toString();
					serialReturnAdapter.SearchInput(strSerialNumber, stockModel);
				}
			}
		});

		lvSerialReturn.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Serial serial = mListSerialReturn.get(position);
				if (numberSerial > serial.getNumber()) {

					mListSerial.add(serial);
					serialSelectionAdapter = new SerialAdapterToSale(mActivity, mListSerial,
							FragmentChooseSerialToSale.this, false);
					lvSerial.setAdapter(serialSelectionAdapter);

					edtSearchSerial.setText("");
					mListSerialReturn.remove(serial);
					serialReturnAdapter = new SerialAdapterToSale(mActivity, mListSerialReturn,
							FragmentChooseSerialToSale.this, true);
					lvSerialReturn.setAdapter(serialReturnAdapter);
					numberSerial -= serial.getNumber();
				} else if (numberSerial == serial.getNumber() && numberSerial == 1L) {
					mListSerial.add(serial);
					serialSelectionAdapter = new SerialAdapterToSale(mActivity, mListSerial,
							FragmentChooseSerialToSale.this, false);
					lvSerial.setAdapter(serialSelectionAdapter);
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

		btnGetBarcode.setOnClickListener(this);

		dialogListSerialReturn.show();
	}

	private TextView tvSerialNotEnought2;

	private void showDialogSelectFromAndToSerial(final Serial serialFromTo) {
		final Dialog dialog = new Dialog(mActivity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_dialog_sellect_from_to_serial_to_sale);

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

		edtFromSerial.setText(serialFromTo.getFromSerial());
		Long toSerial = Long.parseLong(serialFromTo.getFromSerial()) + numberSerial - 1;
		String strToserial = toSerial.toString();
		if (stockModel.getStockTypeId() == 6) {
			strToserial = SaleCommons.normalSerial(strToserial);
		}
		edToSerial.setText(strToserial);

		if (serialFromTo.getFromSerial().equalsIgnoreCase(serialFromTo.getToSerial())) {
			tvSerial.setText(getString(R.string.list_serial_parent) + " " + serialFromTo.getFromSerial());
		} else {
			tvSerial.setText(getString(R.string.list_serial_parent) + " " + serialFromTo.getFromSerial() + " - "
					+ serialFromTo.getToSerial());
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
				} else if (Long.parseLong(strFromSerial) < Long.parseLong(serialFromTo.getFromSerial())) {
					dialogError = CommonActivity.createAlertDialog(mActivity, getString(R.string.serial_out_of_range),
							getString(R.string.app_name));
				} else if (Long.parseLong(strToSerial) > Long.parseLong(serialFromTo.getToSerial())) {
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
				Serial serial = new Serial(strFromSerial, strToSerial);
				mListSerial.add(serial);
				serialSelectionAdapter = new SerialAdapterToSale(mActivity, mListSerial,
						FragmentChooseSerialToSale.this, false);
				lvSerial.setAdapter(serialSelectionAdapter);

				// serialReturnAdapter = new SerialSaleReturnAdapter(mActivity,
				// mListSerialReturn,
				// FragmentChooseSerialReturnTheGood.this, true);
				// lvSerialReturn.setAdapter(serialReturnAdapter);

				if (countSerialSelect < numberSerial) {
					if (strFromSerial.equalsIgnoreCase(serialFromTo.getFromSerial())) {
						Serial serialsub = new Serial();
						Long toSerial = Long.parseLong(strToSerial) + 1;
						serialsub.setFromSerial(toSerial.toString());
						serialsub.setToSerial(serialFromTo.getToSerial().toString());
						mListSerialReturn.add(serialsub);

					} else if (strToSerial.equalsIgnoreCase(serialFromTo.getToSerial())) {
						Serial serialsub = new Serial();
						Long toSerial = Long.parseLong(strToSerial) - 1;
						serialsub.setFromSerial(serialFromTo.getFromSerial());
						serialsub.setToSerial(toSerial.toString());
						mListSerialReturn.add(serialsub);
					} else {
						Serial serialSub1 = new Serial();
						Long tSerial = Long.parseLong(strFromSerial) - 1;
						serialSub1.setFromSerial(serialFromTo.getFromSerial());
						serialSub1.setToSerial(tSerial.toString());
						mListSerialReturn.add(serialSub1);

						Serial serialSub2 = new Serial();
						Long frSerial = Long.parseLong(strToSerial) + 1;
						serialSub2.setFromSerial(frSerial.toString());
						serialSub2.setToSerial(serialFromTo.getToSerial());
						mListSerialReturn.add(serialSub2);
					}
					mListSerialReturn.remove(serialFromTo);

					if (stockModel.getStockTypeId() == 6) {
						for (Serial serialReturn : mListSerialReturn) {
							serialReturn.setFromSerial(SaleCommons.normalSerial(serialReturn.getFromSerial()));
							serialReturn.setToSerial(SaleCommons.normalSerial(serialReturn.getToSerial()));
						}
					}

					serialReturnAdapter = new SerialAdapterToSale(mActivity, mListSerialReturn,
							FragmentChooseSerialToSale.this, true);
					lvSerialReturn.setAdapter(serialReturnAdapter);
					numberSerial -= countSerialSelect;
				} else if (countSerialSelect == numberSerial) {
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0) {

			if (resultCode == Activity.RESULT_OK) {
				try {
					String contents = data.getStringExtra("SCAN_RESULT");
					String format = data.getStringExtra("SCAN_RESULT_FORMAT");
					Log.d("Log", "barcode result: " + (format + "\n" + contents));
					edtSearchSerial.setText(contents);
				} catch (Exception e) {
					Log.d(Constant.TAG, "FragmentChooseSerialToSale onActivityResult barcode result error ", e);
				}

			}
		}

	}

}
