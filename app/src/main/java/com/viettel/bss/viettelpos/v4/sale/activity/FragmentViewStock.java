package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.adapter.AvailableSerialAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockViewAdapter;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentViewStock extends Fragment implements OnClickListener,
		OnItemClickListener {

	private View mView;
	private ListView lvStockModel;
    private Activity mActivity;
	private ArrayList<StockModel> mListStockModel = new ArrayList<>();
    private TextView tvStaffCode;
	private StockModel curentStockModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_view_stock, container,
					false);
			lvStockModel = (ListView) mView.findViewById(R.id.lvStockModel);
			tvStaffCode = (TextView) mView.findViewById(R.id.tvStaffCode);
            TextView tvStaffName = (TextView) mView.findViewById(R.id.tvStaffName);

			Bundle bundle = getArguments();
			if (bundle != null) {
				tvStaffCode
						.setText(bundle.getString("staffCode").toUpperCase());
				tvStaffName
						.setText(bundle.getString("staffName").toUpperCase());
			}

			lvStockModel.setOnItemClickListener(this);

			loadListStockMoel();
		}

		return mView;
	}

    private Boolean isStockHandset = true;

    @Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

        StockModel stockModel = mListStockModel.get(position);

		if (stockModel.isCheckSerial()) {
            Dialog selectSerialDialog = new Dialog(getActivity());
            selectSerialDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            selectSerialDialog.setContentView(R.layout.view_serial_layout);

            Boolean isStockCard;

            isStockCard = stockModel.getTableName() != null && "Stock_Card".equalsIgnoreCase(stockModel.getTableName());

//            if (stockModel.getTableName() != null
//                    && !stockModel.getTableName().isEmpty()) {
//                isStockHandset = "Stock_Handset".equalsIgnoreCase(stockModel
//                        .getTableName());
//            }

//            ArrayList<String> lstSerial = new ArrayList<>();
//            if (stockModel.getmListSerial() != null) {
//                for (Serial serial : stockModel.getmListSerial()) {
//                    if (serial.getFromSerial().equals(serial.getToSerial())) {
//                        lstSerial.add(serial.getFromSerial());
//                    } else {
//                        lstSerial.add(serial.getFromSerial() + " - "
//                                + serial.getToSerial());
//                    }
//                }
//            }

//            AvailableSerialAdapter avaiLableSerialAdapter = new AvailableSerialAdapter(getActivity(),
//                    SaleCommons.getRangeSerial(lstSerial, isStockHandset),
//                    isStockCard, isStockHandset);
			AvailableSerialAdapter avaiLableSerialAdapter = new AvailableSerialAdapter(getActivity(),
					stockModel.getmListSerial(),
                    isStockCard, isStockHandset);
            ListView lvAvailable = (ListView) selectSerialDialog
                    .findViewById(R.id.lvAvailableSerial);
            lvAvailable.setAdapter(avaiLableSerialAdapter);
            selectSerialDialog.show();
        }

	}

	private void loadListStockMoel() {
		if (CommonActivity.isNetworkConnected(mActivity)) {
			AsynctaskGetListStockModel asyntaskGetlistStockModel = new AsynctaskGetListStockModel(
					mActivity);
			asyntaskGetlistStockModel.execute();
		} else {
			CommonActivity.createAlertDialog(mActivity,
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.view_stock);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == 100) {
				if (curentStockModel.getCheckSerial() == 0) {
					curentStockModel.setQuantitySaling(Long.parseLong(data
							.getExtras().getString("strNumberSerial")
							.toString()));
				} else {

					ArrayList<Serial> listSerialReceiver = (ArrayList<Serial>) data
							.getExtras().getSerializable("listSerial");
					long countSerial = 0L;
					for (Serial serial : listSerialReceiver) {
						countSerial += serial.getNumber();
					}
					curentStockModel.setQuantitySaling(countSerial);
					curentStockModel
							.setmListSerialSelection(listSerialReceiver);

					// for (StockModel stockModel :
					// stockReturnModelAdapter.getLstData()) {
					// if (stockModel.getStockModelId() ==
					// curentStockModel.getStockModelId()) {
					//
					// ArrayList<Serial> listSerialReceiver =
					// (ArrayList<Serial>)data.getExtras().getSerializable("listSerial");
					// long countSerial = 0L;
					// for (Serial serial : listSerialReceiver) {
					// countSerial += serial.getNumber();
					// }
					// stockModel.setQuantitySaling(countSerial);
					// stockModel.setmListSerialSelection(listSerialReceiver);
					// }
					// }
				}

			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();
			// MainActivity.getInstance().finish();

			LoginDialog dialog = new LoginDialog(getActivity(), ";view_stock;");
			dialog.show();

		}
	};

	@SuppressWarnings("unused")
	private class AsynctaskGetListStockModel extends
			AsyncTask<Void, Void, ArrayList<StockModel>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsynctaskGetListStockModel(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
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
					mListStockModel = result;
                    StockViewAdapter stockViewAdapter = new StockViewAdapter(getActivity(),
                            mListStockModel);
					lvStockModel.setAdapter(stockViewAdapter);
				} else {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.ko_co_dl),
							getString(R.string.app_name)).show();
				}
			} else {
				Log.d("Log", "description error update" + description);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
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
				input.addValidateGateway("wscode", "mbccs_getStockStaffDetailBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getStockStaffDetail>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				if (!CommonActivity.isNullOrEmpty(tvStaffCode.getText()
						.toString())) {
					rawData.append("<staffCode>").append(tvStaffCode.getText().toString()).append("</staffCode>");
				}
				rawData.append("</input>");
				rawData.append("</ws:getStockStaffDetail>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getStockStaffDetailBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodechildListSerial = null;
				NodeList nodeListQuantity = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstProductOfferingDTO");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						StockModel stockModel = new StockModel();
						nodechildListSerial = e1
								.getElementsByTagName("listStockTransSerialDTOs");
						Log.d("Log", "node list serial: " + nodechildListSerial);
						for (int k = 0; k < nodechildListSerial.getLength(); k++) {
							Element e4 = (Element) nodechildListSerial.item(k);
							Serial serial = new Serial();
							serial.setFromSerial(parse.getValue(e4,
									"fromSerial"));
							serial.setToSerial(parse.getValue(e4, "toSerial"));
							stockModel.getmListSerial().add(serial);
						}

						Log.d("Log", "parser list" + e1);

						String stockModelId = parse
								.getValue(e1, "productOfferingId");

						if (stockModelId != null && !stockModel.equals("")) {
							stockModel.setStockModelId(Long
									.parseLong(stockModelId));
						}

						String stockModelCode = parse.getValue(e1,
								"code");

						if (stockModelCode != null
								&& !stockModelCode.equals("")) {
							stockModel.setStockModelCode(stockModelCode);
						}

						stockModel
								.setStockModelName(parse.getValue(e1, "name"));
						stockModel
								.setStateName(parse.getValue(e1, "stateIdName"));

						String stockTypeId = parse.getValue(e1, "productOfferTypeId");
						if (stockTypeId != null && !stockTypeId.equals("")) {
							stockModel.setStockTypeId(Long
									.parseLong(stockTypeId));
						}

						String telecomServiceId = parse.getValue(e1,
								"telecomServiceId");

						if (telecomServiceId != null
								&& !telecomServiceId.equals("")) {
							stockModel.setTelecomServiceId(Long
									.parseLong(telecomServiceId));
						}

						String stateId = parse.getValue(e1, "stateId");

						if (stateId != null && !stateId.equals("")) {
							stockModel.setStateId(Long.parseLong(stateId));
						}

						String price = parse.getValue(e1, "price");
						if (price != null && !price.equals("")) {
							stockModel.setPrice(Long.parseLong(price));
						}

						String quantityIssue = parse.getValue(e1,
								"availableQuantity");
						if (quantityIssue != null && !quantityIssue.equals("")) {
							stockModel.setQuantityIssue(Long
									.parseLong(quantityIssue));
						}

						String checkSerial = parse.getValue(e1, "checkSerial");

						if (checkSerial != null && !checkSerial.equals("")) {
							stockModel.setCheckSerial(Long
									.parseLong(checkSerial));
						}

						nodeListQuantity = e1.getElementsByTagName("quantity");
						Element quantity = (Element) nodeListQuantity
								.item(nodeListQuantity.getLength() - 1);

						// String quality = parse.getValue(quantity,
						// "quantity");
						String quality = quantity.getTextContent();

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

}
