package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockReturnAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockReturnAdapter.OnChangeQuantity;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockReturnAdapter.OncancelStockModel;
import com.viettel.bss.viettelpos.v4.sale.object.InventoryOutput;
import com.viettel.bss.viettelpos.v4.sale.object.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransSerialDTO;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentSaleSalingReturn extends Fragment
		implements OncancelStockModel, OnClickListener, OnChangeQuantity {

	private View mView;
	private EditText edtSearch;
	private ListView lvStockModel;
	private Button btnSale;
	private Button btnViewStockModel;
	private StockReturnAdapter stockReturnModelAdapter;
	private Activity mActivity;
    private Button btnHome;
	private LinearLayout btnSearch;
	private ProductOfferingDTO curentStockModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_sale_return_the_good, container, false);
			lvStockModel = (ListView) mView.findViewById(R.id.lvStockModel);
			btnSale = (Button) mView.findViewById(R.id.btnOk);
			btnViewStockModel = (Button) mView.findViewById(R.id.btnViewStockModel);
			edtSearch = (EditText) mView.findViewById(R.id.edtSearch);
			btnSearch = (LinearLayout) mView.findViewById(R.id.btn_search);
			loadListStockMoel();
		}

		btnViewStockModel.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		btnSale.setOnClickListener(this);

		lvStockModel.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("Log", "click stock item position: " + position);

			}
		});

		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

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
		MainActivity.getInstance().setTitleActionBar(R.string.title_fragment_return_good);
	}

	@Override
	public void onChangeQuantity(ProductOfferingDTO stockModel) {
		curentStockModel = stockModel;
		// if (listSerial != null) {
		// listSerial.clear();
		// }

		Bundle mBundle = new Bundle();
		if (stockModel != null) {
			mBundle.putSerializable("stockModel", stockModel);
		}

		if (stockModel.getmListSerialSelection() != null && stockModel.getmListSerialSelection().size() > 0) {
			ArrayList<StockTransSerialDTO> listSerialPut = new ArrayList<>();
			listSerialPut.addAll(stockModel.getmListSerialSelection());
			mBundle.putSerializable("listSerial", listSerialPut);
		}

		FragmentChooseSerialReturnTheGood fragmentChooseSerial = new FragmentChooseSerialReturnTheGood();
		fragmentChooseSerial.setArguments(mBundle);
		fragmentChooseSerial.setTargetFragment(FragmentSaleSalingReturn.this, 100);
		ReplaceFragment.replaceFragment(mActivity, fragmentChooseSerial, true);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == 100) {
				if (curentStockModel.getCheckSerial() == 0) {
					curentStockModel.setQuantitySaling(
							Long.parseLong(data.getExtras().getString("strNumberSerial").toString()));
				} else {

					ArrayList<StockTransSerialDTO> listSerialReceiver = (ArrayList<StockTransSerialDTO>) data
							.getExtras().getSerializable("listSerial");
					long countSerial = 0L;
					for (StockTransSerialDTO serial : listSerialReceiver) {
						countSerial += serial.getNumber();
					}
					curentStockModel.setQuantitySaling(countSerial);
					curentStockModel.setmListSerialSelection(listSerialReceiver);

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
		case R.id.btn_search:
			onSearchStockModel();
			break;

		case R.id.btnViewStockModel:
			onFilterViewStock(v);
			break;
		case R.id.btnOk:

			boolean isReturnTheGood = false;
			for (ProductOfferingDTO stockModel : stockReturnModelAdapter.getLstData()) {
				if (stockModel.getQuantitySaling() > 0) {
					isReturnTheGood = true;
					break;
				}
			}

			if (isReturnTheGood) {
				if (CommonActivity.isNetworkConnected(mActivity)) {
					CommonActivity.createDialog(mActivity, getString(R.string.message_confirm_return_the_good),
							getString(R.string.app_name), getString(R.string.say_ko), getString(R.string.say_co), null,
							returnTheGoodConfirmCallBack).show();
				} else {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}
			} else {
				CommonActivity.createAlertDialog(mActivity, getString(R.string.message_please_sellect_return_the_good),
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
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();
			// MainActivity.getInstance().finish();

			LoginDialog dialog = new LoginDialog(getActivity(),
					Constant.VSAMenu.MENU_RETURN_GOOD);
			dialog.show();
		}
	};

	// confirm update
    private final OnClickListener returnTheGoodConfirmCallBack = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!CommonActivity.isNetworkConnected(mActivity)) {
				CommonActivity
						.createAlertDialog(mActivity, getString(R.string.errorNetwork), getString(R.string.app_name))
						.show();
			} else {

				ArrayList<ProductOfferingDTO> listStockReturn = new ArrayList<>();
				for (ProductOfferingDTO stockModel : stockReturnModelAdapter.getLstData()) {
					if (stockModel.getQuantitySaling() > 0) {
						listStockReturn.add(stockModel);
					}
				}
				AsyntaskReturnTheGood asyntaskReturnThegood = new AsyntaskReturnTheGood(mActivity, listStockReturn);
				asyntaskReturnThegood.execute();
			}
		}
	};

	private void onFilterViewStock(View v) {
		if(!CommonActivity.isNullOrEmpty(stockReturnModelAdapter)) {
			if (v.getTag().equals("2")) {

				boolean isReturnTheGood = false;
				if(!CommonActivity.isNullOrEmpty(stockReturnModelAdapter.getLstData())) {
					for (ProductOfferingDTO stockModel : stockReturnModelAdapter.getLstData()) {
						if (stockModel.getQuantitySaling() > 0) {
							isReturnTheGood = true;
							break;
						}
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

	}

	@SuppressWarnings("unused")
	private void onSearchStockModel() {
		if (stockReturnModelAdapter != null) {
			String strKeySearch = edtSearch.getText().toString();
			stockReturnModelAdapter.SearchInput(strKeySearch);
		}
	}

	@Override
	public void onCancelStockModelListener(ProductOfferingDTO stockModel) {
	}

	@SuppressWarnings("unused")
	private class AsynctaskGetListStockModel extends AsyncTask<Void, Void, InventoryOutput> {

		private Activity mActivity = null;
		XmlDomParse parse = new XmlDomParse();
		final String errorCode = "";
		final String description = "";
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
		protected InventoryOutput doInBackground(Void... params) {
			return getListStockModel();
		}

		@Override
		protected void onPostExecute(InventoryOutput result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(result.getErrorCode())) {

                ArrayList<ProductOfferingDTO> mListStockModel = result.getLstProductOfferingDTO();
				if (mListStockModel != null && mListStockModel.size() > 0) {
					stockReturnModelAdapter = new StockReturnAdapter(getActivity(), mListStockModel,
							FragmentSaleSalingReturn.this, FragmentSaleSalingReturn.this, FragmentSaleSalingReturn.this,
							false);
					lvStockModel.setAdapter(stockReturnModelAdapter);
				} else {
					CommonActivity.createAlertDialog(MainActivity.getInstance(), getActivity().getString(R.string.ko_co_dl),
							getString(R.string.app_name)).show();// ,getString(R.string.thu_lai)
				}
			} else {

				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();

				} else {
					String description = result.getDescription();
					if (CommonActivity.isNullOrEmpty(description)) {
						description = getActivity().getString(R.string.fails_not_description,
								getString(R.string.getListDeptByObject));
					}
					CommonActivity.createErrorDialog(MainActivity.getInstance(), description, result.getErrorCode())
							.show();
				}

			}
		}

		private InventoryOutput getListStockModel() {
			ArrayList<ProductOfferingDTO> listStockModel = new ArrayList<>();
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
				rawData.append("</input>");
				rawData.append("</ws:getStockStaffDetail>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getStockStaffDetailBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				Serializer serializer = new Persister();
				InventoryOutput inventoryOtuput = serializer.read(InventoryOutput.class, original);
				if (inventoryOtuput == null) {
					inventoryOtuput = new InventoryOutput();
					inventoryOtuput.setDescription(getString(R.string.no_return_from_system));
					inventoryOtuput.setErrorCode(Constant.ERROR_CODE);
					return inventoryOtuput;
				} else {
					return inventoryOtuput;
				}
			} catch (Exception e) {
				Log.e("getListIP", e.toString() + "description error", e);
				InventoryOutput inventoryOtuput = new InventoryOutput();
				inventoryOtuput.setDescription(getString(R.string.no_return_from_system));
				inventoryOtuput.setErrorCode(Constant.ERROR_CODE);
				return inventoryOtuput;
			}
		}
	}

	private class AsyntaskReturnTheGood extends AsyncTask<Void, Void, Void> {
		private final Activity mActivity;
		private final ArrayList<ProductOfferingDTO> listStockModel;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsyntaskReturnTheGood(Activity mActivity, ArrayList<ProductOfferingDTO> listStockModel) {
			this.mActivity = mActivity;
			this.listStockModel = listStockModel;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.loadding_returing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			doReturnTheGood(listStockModel);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {
				CommonActivity.createAlertDialog(mActivity, getString(R.string.message_returnthegood_success),
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

		private void doReturnTheGood(ArrayList<ProductOfferingDTO> listStockModel) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_staffExportStockToShopBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:staffExportStockToShop>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				for (ProductOfferingDTO stockModel2 : listStockModel) {

					rawData.append("<lstProductOfferingSM>");
					rawData.append("<productOfferingId>").append(stockModel2.getProductOfferingId()).append("</productOfferingId>");
					rawData.append("<code>").append(stockModel2.getCode()).append("</code>");
					rawData.append("<stateId>").append(stockModel2.getStateId()).append("</stateId>");
					rawData.append("<quantity>").append(stockModel2.getQuantitySaling()).append("</quantity>");
					if (stockModel2.getCheckSerial() == 1) {
						for (int i = 0; i < stockModel2.getmListSerialSelection().size(); i++) {
							StockTransSerialDTO serial = stockModel2.getmListSerialSelection().get(i);
							String strFromSerial = "";
							String strToSerial = "";
							strFromSerial = serial.getFromSerial();
							strToSerial = serial.getToSerial();
							rawData.append("<listStockTransSerialDTOs>");
							rawData.append("<fromSerial>").append(strFromSerial).append("</fromSerial>");
							rawData.append("<toSerial>").append(strToSerial).append("</toSerial>");
							rawData.append("</listStockTransSerialDTOs>");
						}
					}

					rawData.append("</lstProductOfferingSM>");
				}

				rawData.append("</input>");
				rawData.append("</ws:staffExportStockToShop>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_staffExportStockToShopBccs2");
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
