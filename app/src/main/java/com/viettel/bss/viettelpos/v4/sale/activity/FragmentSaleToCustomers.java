package com.viettel.bss.viettelpos.v4.sale.activity;

import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.adapter.SaleToCustomerAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelAdapter.OnChangeQuantity;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelAdapter.OncancelStockModel;
import com.viettel.bss.viettelpos.v4.sale.object.PlaceToSaleObject;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentSaleToCustomers extends Fragment implements OncancelStockModel, OnClickListener, OnChangeQuantity {

	private View mView;
	private EditText edtSearch;
	private EditText edt_place_sasle;
	private ListView lvStockModel;
	private Button btnSale;
	private Button btnViewStockModel;
	private SaleToCustomerAdapter stockReturnModelAdapter;
	private Activity mActivity;
    private int functionType;
	private Button btnHome;
	private LinearLayout btnSearch;
	private StockModel curentStockModel;
	private PlaceToSaleObject placeToSaleObject;
	private ImageButton btnClearPlaceSale;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_sale_to_customer, container, false);
			lvStockModel = (ListView) mView.findViewById(R.id.lvStockModel);
			btnSale = (Button) mView.findViewById(R.id.btnOk);
			btnViewStockModel = (Button) mView.findViewById(R.id.btnViewStockModel);
			edtSearch = (EditText) mView.findViewById(R.id.edtSearch);
			edt_place_sasle = (EditText) mView.findViewById(R.id.edt_place_sasle);
			btnSearch = (LinearLayout) mView.findViewById(R.id.btn_search);
			btnClearPlaceSale = (ImageButton) mView.findViewById(R.id.btnClearPlaceSale);
			loadListStockMoel();
		}

		btnViewStockModel.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		btnSale.setOnClickListener(this);
		edt_place_sasle.setOnClickListener(this);
		btnClearPlaceSale.setOnClickListener(this);

		lvStockModel.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("Log", "FragmentSaleToCustomers onCreateView lvStockModel.setOnItemClickListener: " + position);

			}
		});

		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				onSearchStockModel();

			}
		});

		return mView;
	}

	private void loadListStockMoel() {
		if (CommonActivity.isNetworkConnected(mActivity)) {
			AsynctaskGetListStockModel asyntaskGetlistStockModel = new AsynctaskGetListStockModel(mActivity);
			asyntaskGetlistStockModel.execute();
		} else {
			CommonActivity.createAlertDialog(mActivity, getString(R.string.errorNetwork), getString(R.string.app_name))
					.show();
		}
	}

	@Override
	public void onResume() {

		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.title_fragment_sale_custommer);

		if (placeToSaleObject != null) {
			edt_place_sasle.setText(placeToSaleObject.getStaffName());
		}
	}

	@Override
	public void onChangeQuantity(StockModel stockModel) {
		curentStockModel = stockModel;
		// if (listSerial != null) {
		// listSerial.clear();
		// }

		Bundle mBundle = new Bundle();
		if (stockModel != null) {
			mBundle.putSerializable("stockModel", stockModel);
		}

		if (stockModel.getmListSerialSelection() != null && stockModel.getmListSerialSelection().size() > 0) {
			ArrayList<Serial> listSerialPut = new ArrayList<>();
			listSerialPut.addAll(stockModel.getmListSerialSelection());
			mBundle.putSerializable("listSerial", listSerialPut);
		}

		FragmentChooseSerialToSale fragmentChooseSerial = new FragmentChooseSerialToSale();
		fragmentChooseSerial.setArguments(mBundle);
		fragmentChooseSerial.setTargetFragment(FragmentSaleToCustomers.this, 100);
		ReplaceFragment.replaceFragment(mActivity, fragmentChooseSerial, true); 
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == 100) { // chon so serial ban
				if (curentStockModel.getCheckSerial() == 0) {
					curentStockModel.setQuantitySaling(
							Long.parseLong(data.getExtras().getString("strNumberSerial").toString()));
				} else {
					
					ArrayList<Serial> listSerialReceiver = (ArrayList<Serial>)data.getExtras().getSerializable("listSerial");
					long countSerial = 0L;
					for (Serial serial : listSerialReceiver) {
						countSerial += serial.getNumber();
					}
					curentStockModel.setQuantitySaling(countSerial); 
					curentStockModel.setmListSerialSelection(listSerialReceiver); 
					
					
//					for (StockModel stockModel : stockReturnModelAdapter.getLstData()) {
//						if (stockModel.getStockModelId() == curentStockModel.getStockModelId()) {
//							stockModel.setmListSerialSelection(
//									(ArrayList<Serial>) data.getExtras().getSerializable("listSerial"));
//							curentStockModel
//									.setmListSerial((ArrayList<Serial>) data.getExtras().getSerializable("listSerial"));
//						}
//					}
				}
			} else if (requestCode == 101) { // chon diem ban result
				placeToSaleObject = (PlaceToSaleObject) data.getExtras().getSerializable("placeSaleObject");
				edt_place_sasle.setText(placeToSaleObject.getStaffName());
			}
		}
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

		case R.id.btnViewStockModel:
			onFilterViewStock(v);
			break;
		case R.id.edt_place_sasle:
			onSearchPlaceSale();
			break;
		case R.id.btnClearPlaceSale:
			placeToSaleObject = null;
			edt_place_sasle.setText("");
			break;
		case R.id.btnOk:

			boolean isSaleTheGood = false;
			for (StockModel stockModel : stockReturnModelAdapter.getLstData()) {
				if (stockModel.getQuantitySaling() > 0) {
					isSaleTheGood = true;
					break;
				}
			}

			if (isSaleTheGood) {
				if (CommonActivity.isNetworkConnected(mActivity)) { 
					CommonActivity.createDialog(mActivity, getString(R.string.message_confirm_sale_the_good),
							getString(R.string.app_name), getString(R.string.say_ko), getString(R.string.say_co), null,
							saleTheGoodConfirmCallBack).show();
				} else {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}
			} else {
				CommonActivity.createAlertDialog(mActivity, getString(R.string.message_please_sellect_sale_the_good),
						getString(R.string.app_name)).show();
			}

			break;
		default:
			break;
		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(), LoginActivity.class);
//			startActivity(intent);
//			getActivity().finish();
//			MainActivity.getInstance().finish();
			
			
			LoginDialog dialog = new LoginDialog(getActivity(),
					";outlet_sale;");
			dialog.show();

		}
	};

	// confirm update
    private final OnClickListener saleTheGoodConfirmCallBack = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!CommonActivity.isNetworkConnected(mActivity)) {
				CommonActivity
						.createAlertDialog(mActivity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			} else {
				ArrayList<StockModel> listStockReturn = new ArrayList<>();
				for (StockModel stockModel : stockReturnModelAdapter.getLstData()) {
					if (stockModel.getQuantitySaling() > 0) {
						listStockReturn.add(stockModel);
					}
				}
				AsyntaskSaleTheGood asyntaskReturnThegood = new AsyntaskSaleTheGood(mActivity, listStockReturn);
				asyntaskReturnThegood.execute();
			}
		}
	};

	private void onFilterViewStock(View v) {
		if (v.getTag().equals("2")) {

			boolean isReturnTheGood = false;
			for (StockModel stockModel : stockReturnModelAdapter.getLstData()) {
				if (stockModel.getQuantitySaling() > 0) {
					isReturnTheGood = true;
					break;
				}
			}
			if (!isReturnTheGood) {
				CommonActivity.createAlertDialog(mActivity, getString(R.string.message_not_select_return_the_good),
						getString(R.string.app_name)).show();
			} else {
				stockReturnModelAdapter.filter(true);
				v.setTag("3");
				((Button) v).setText(mActivity.getResources().getString(R.string.view_comback));
			}
		} else {
			stockReturnModelAdapter.filter(false);
			v.setTag("2");
			((Button) v).setText(mActivity.getResources().getString(R.string.choosed));
		}

	}

	@SuppressWarnings("unused")
	private void onSearchStockModel() {

		if (stockReturnModelAdapter != null) {
			String strKeySearch = edtSearch.getText().toString();
			stockReturnModelAdapter.SearchInput(strKeySearch);
		}
	}

	private void onSearchPlaceSale() {
		FragmentSearchPlaceSale fragmentSearchPlaceSale = new FragmentSearchPlaceSale();
		fragmentSearchPlaceSale.setTargetFragment(FragmentSaleToCustomers.this, 101);
		ReplaceFragment.replaceFragment(mActivity, fragmentSearchPlaceSale, false);
	}

	@Override
	public void onCancelStockModelListener(StockModel stockModel) {
	}

	@SuppressWarnings("unused")
	private class AsynctaskGetListStockModel extends AsyncTask<Void, Void, ArrayList<StockModel>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsynctaskGetListStockModel(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<StockModel> doInBackground(Void... params) {
			return getListStockModel();
		}

		@Override
		protected void onPostExecute(ArrayList<StockModel> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result.size() > 0) {
                    stockReturnModelAdapter = new SaleToCustomerAdapter(getActivity(), result,
							FragmentSaleToCustomers.this, FragmentSaleToCustomers.this, FragmentSaleToCustomers.this,
							false);
					lvStockModel.setAdapter(stockReturnModelAdapter);
				} else {
					CommonActivity
							.createAlertDialog(mActivity, getString(R.string.ko_co_dl), getString(R.string.app_name))
							.show();
				}
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} 
		}

		private ArrayList<StockModel> getListStockModel() {
			ArrayList<StockModel> listStockModel = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_lookUpStockSale");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:lookUpStockSale>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:lookUpStockSale>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_lookUpStockSale");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodechildListSerial = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("listStockModel");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						StockModel stockModel = new StockModel();
						nodechildListSerial = e1.getElementsByTagName("lstSerial");
						Log.d("Log", "node list serial: " + nodechildListSerial);
						for (int k = 0; k < nodechildListSerial.getLength(); k++) {
							Element e4 = (Element) nodechildListSerial.item(k);
							Serial serial = new Serial();
							serial.setFromSerial(parse.getValue(e4, "fromSerial"));
							serial.setToSerial(parse.getValue(e4, "toSerial"));
							stockModel.getmListSerial().add(serial);
						}

						Log.d("Log", "parser list" + e1);

						String stockModelId = parse.getValue(e1, "stockModelId");

						if (stockModelId != null && !stockModel.equals("")) {
							stockModel.setStockModelId(Long.parseLong(stockModelId));
						}

						String stockModelCode = parse.getValue(e1, "stockModelCode");

						if (stockModelCode != null && !stockModelCode.equals("")) {
							stockModel.setStockModelCode(stockModelCode);
						}

						stockModel.setStockModelName(parse.getValue(e1, "name"));
						stockModel.setStateName(parse.getValue(e1, "stateName"));

						String stockTypeId = parse.getValue(e1, "stockTypeId");
						if (stockTypeId != null && !stockTypeId.equals("")) {
							stockModel.setStockTypeId(Long.parseLong(stockTypeId));
						}

						String telecomServiceId = parse.getValue(e1, "telecomServiceId");

						if (telecomServiceId != null && !telecomServiceId.equals("")) {
							stockModel.setTelecomServiceId(Long.parseLong(telecomServiceId));
						}

						String stateId = parse.getValue(e1, "stateId");

						if (stateId != null && !stateId.equals("")) {
							stockModel.setStateId(Long.parseLong(stateId));
						}

						String price = parse.getValue(e1, "price");
						if (price != null && !price.equals("")) {
							stockModel.setPrice(Long.parseLong(price));
						}

						String quantityIssue = parse.getValue(e1, "quantity");
						if (quantityIssue != null && !quantityIssue.equals("")) {
							stockModel.setQuantityIssue(Long.parseLong(quantityIssue));
						}

						String checkSerial = parse.getValue(e1, "checkSerial");

						if (checkSerial != null && !checkSerial.equals("")) {
							stockModel.setCheckSerial(Long.parseLong(checkSerial));
						}

						String quality = parse.getValue(e1, "quantity");
						if (quality != null && !quality.equals("")) {
							stockModel.setQuantity(Long.parseLong(quality));
						}

						listStockModel.add(stockModel);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

			return listStockModel;

		}
	}

	@SuppressWarnings("unused")
	private class AsyntaskSaleTheGood extends AsyncTask<Void, Void, Void> {
		private final Activity mActivity;
		private final ArrayList<StockModel> listStockModel;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsyntaskSaleTheGood(Activity mActivity, ArrayList<StockModel> listStockModel) {
			this.mActivity = mActivity;
			this.listStockModel = listStockModel;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.loadding_saling));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			doSaleTheGood(listStockModel);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {
				CommonActivity.createAlertDialog(mActivity, getString(R.string.message_sale_thegood_success),
						getString(R.string.app_name)).show();
				loadListStockMoel();
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} 
		}

		private void doSaleTheGood (ArrayList<StockModel> listStockModel) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_createOutletSaleTrans");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:createOutletSaleTrans>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				if (placeToSaleObject != null) {
					rawData.append("<staffId>").append(placeToSaleObject.getStaffId()).append("</staffId>");
				}
				
				for (StockModel stockModel2 : listStockModel) {
					rawData.append("<lstStockModel>");
					rawData.append("<stockModelCode>").append(stockModel2.getStockModelCode()).append("</stockModelCode>");
					rawData.append("<stockModelId>").append(stockModel2.getStockModelId()).append("</stockModelId>");
					rawData.append("<stockTypeId>").append(stockModel2.getStockTypeId()).append("</stockTypeId>");
					rawData.append("<checkSerial>").append(stockModel2.getCheckSerial()).append("</checkSerial>");
					rawData.append("<quantity>").append(stockModel2.getQuantitySaling()).append("</quantity>");
					
					
					if (stockModel2.getCheckSerial() == 1) {
						for (int i = 0; i < stockModel2.getmListSerialSelection().size(); i++) {
							Serial serial = stockModel2.getmListSerialSelection().get(i);

							String strFromSerial = "";
							String strToSerial = "";
							if (stockModel2.getStockTypeId() == 6) { // serial co so 0 dang truoc nen goi ham nomal de them 
								strFromSerial = SaleCommons.normalSerial(serial.getFromSerial());
								strToSerial = SaleCommons.normalSerial(serial.getToSerial());
							} else {
								strFromSerial = serial.getFromSerial();
								strToSerial = serial.getToSerial();
							}

							rawData.append("<lstSerial>");
							rawData.append("<fromSerial>").append(strFromSerial).append("</fromSerial>");
							rawData.append("<quantity>").append(serial.getNumber()).append("</quantity>");
							rawData.append("<toSerial>").append(strToSerial).append("</toSerial>");
							rawData.append("</lstSerial>");
						}
					}

					rawData.append("</lstStockModel>");
				} 

				rawData.append("</input>");
				rawData.append("</ws:createOutletSaleTrans>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_createOutletSaleTrans");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");

				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

		}

	}

}
