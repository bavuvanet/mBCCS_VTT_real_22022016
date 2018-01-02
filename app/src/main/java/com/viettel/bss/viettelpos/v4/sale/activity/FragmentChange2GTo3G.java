package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentChange2GTo3G extends Fragment implements OnClickListener {
	private View view;
	private EditText edtCusName;
	private EditText edtCusPhone;
	private EditText edtImei2G;
	private View lnImei3G;
	private Spinner spiProgram;
	private Button btnHome;
    private TextView tvImei3G;
	private ArrayList<StockModel> arrStockModel;
	private ArrayList<Spin> arrProgram;
    private TextView tvChoose3G;
	private static final int REQUEST_CHOOSE_3G_SERIAL = 55;
	private String selectedProgramId;
	private Spinner spiVas;
	private String LOG_TAG = FragmentChange2GTo3G.class.getName();
	private TextView tvChooseChannel;
	private static final int REQUEST_CHOOSE_CHANNEL = 2;
	private TextView tvChooseStaff;
	private static final int REQUEST_CHOOSE_STAFF = 3;
	private String staffDev;
	private Button btnRefreshProgram;
	private Button btnRefreshVas;
	private View prbProgram;
	private View prbVas;
	private StockModel selectedStockModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.layout_change_2g_to_3g, container, false);
			edtCusName = (EditText) view.findViewById(R.id.edt_cus_name);
			edtCusPhone = (EditText) view.findViewById(R.id.edtCusPhone);
			edtImei2G = (EditText) view.findViewById(R.id.edtImei2g);
			spiProgram = (Spinner) view.findViewById(R.id.spn_program);
			spiProgram.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					selectedProgramId = arrProgram.get(arg2).getId();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
			spiVas = (Spinner) view.findViewById(R.id.spnVas);
			tvChoose3G = (TextView) view.findViewById(R.id.tvHandsetChange);
			lnImei3G = view.findViewById(R.id.lnImei3G);
			lnImei3G.setVisibility(View.GONE);
			tvImei3G = (TextView) view.findViewById(R.id.tvImei3G);
			tvChoose3G.setOnClickListener(this);
            Button btnChange = (Button) view.findViewById(R.id.btn_change);
			btnChange.setOnClickListener(this);

			btnRefreshProgram = (Button) view.findViewById(R.id.btnRefreshProgram);
			btnRefreshProgram.setVisibility(View.GONE);

			btnRefreshVas = (Button) view.findViewById(R.id.btnRefreshVas);
			btnRefreshVas.setVisibility(View.GONE);

			prbProgram = view.findViewById(R.id.prbProgram);
			prbVas = view.findViewById(R.id.prbVas);

			if (CommonActivity.isNetworkConnected(getActivity())) {
				AsynctaskGetListProgram asy = new AsynctaskGetListProgram(getActivity());
				asy.execute();
			} else {
				CommonActivity.createAlertDialog(getActivity(), getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			tvChooseChannel = (TextView) view.findViewById(R.id.tvChooseChannel);
			tvChooseChannel.setOnClickListener(this);

			tvChooseStaff = (TextView) view.findViewById(R.id.tvChooseStaff);
			tvChooseStaff.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					FragmentChooseChannel fragment = new FragmentChooseChannel();
					fragment.setTargetFragment(FragmentChange2GTo3G.this, REQUEST_CHOOSE_STAFF);
					ReplaceFragment.replaceFragment(getActivity(), fragment, true);
				}
			});

			btnRefreshProgram.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					if (CommonActivity.isNetworkConnected(getActivity())) {
						new CacheDatabaseManager(MainActivity.getInstance()).insertCacheWithSpinObject(null,
								"3GSaleProgram");
						AsynctaskGetListProgram asy = new AsynctaskGetListProgram(getActivity());
						asy.execute();
					} else {
						CommonActivity.createAlertDialog(getActivity(), getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				}
			});

			btnRefreshVas.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					if (CommonActivity.isNetworkConnected(getActivity())) {
						new CacheDatabaseManager(MainActivity.getInstance()).insertCacheWithSpinObject(null,
								"dataPackage");
						AsynctaskGetListVas asy = new AsynctaskGetListVas(getActivity());
						asy.execute();
					} else {
						CommonActivity.createAlertDialog(getActivity(), getString(R.string.errorNetwork),
								getString(R.string.app_name)).show();
					}
				}
			});

		}
		return view;

	}

	@SuppressWarnings("unused")
	private class AsynctaskGetListProgram extends AsyncTask<Void, Void, ArrayList<Spin>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynctaskGetListProgram(Activity mActivity) {
			prbProgram.setVisibility(View.VISIBLE);
			btnRefreshProgram.setVisibility(View.GONE);
			this.mActivity = mActivity;
		}

		@Override
		protected ArrayList<Spin> doInBackground(Void... params) {
			return getListProgram();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {

			super.onPostExecute(result);
			prbProgram.setVisibility(View.GONE);
			btnRefreshProgram.setVisibility(View.VISIBLE);
			if (errorCode.equals("0")) {
				if (result.size() > 0) {
					arrProgram = result;
					spiProgram.setAdapter(
                            new ArrayAdapter<>(mActivity, android.R.layout.simple_dropdown_item_1line, arrProgram));
					AsynctaskGetListVas asyVas = new AsynctaskGetListVas(mActivity);
					asyVas.execute();
				} else {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.no_sale_program_found),
							getString(R.string.app_name)).show();
				}
			} else {

				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.error_when_get_list_sale_program) + " - " + description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private ArrayList<Spin> getListProgram() {
			ArrayList<Spin> result = new ArrayList<>();
			result = new CacheDatabaseManager(MainActivity.getInstance()).getListCacheWithSpinObject("3GSaleProgram");
			if (result != null && !result.isEmpty()) {
				errorCode = "0";
				return result;
			}
			result = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListProgramsChange2GTo3GBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListProgramsChange2GTo3G>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListProgramsChange2GTo3G>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListProgramsChange2GTo3GBccs2");
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
					nodechild = doc.getElementsByTagName("lstSaleProgrameDTO");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						Spin item = new Spin();

						String programId = parse.getValue(e1, "saleProgramId");

						if (programId != null && !programId.isEmpty()) {
							item.setId(programId);
						}

						String programName = parse.getValue(e1, "name");

						if (programName != null && !programName.isEmpty()) {
							item.setName(programName);
						}

						result.add(item);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}
			if (result != null && !result.isEmpty()) {
				new CacheDatabaseManager(MainActivity.getInstance()).insertCacheWithSpinObject(result, "3GSaleProgram");
			}
			return result;

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (resultCode != Activity.RESULT_OK) {
				return;
			}
			if (requestCode == REQUEST_CHOOSE_3G_SERIAL) {
				Bundle bundle = data.getExtras();
				if (bundle == null) {
					return;
				}
				Long stockModelId = bundle.getLong("stockModelId");
				String serial = bundle.getString("serial3G");
				for (StockModel stockModel : arrStockModel) {
					if (stockModel.getStockModelId().compareTo(stockModelId) == 0) {
						tvChoose3G.setText(stockModel.getStockModelName());
						lnImei3G.setVisibility(View.VISIBLE);
						tvImei3G.setText(serial);
						selectedStockModel = stockModel;
						break;
					}
				}

			} else if (requestCode == REQUEST_CHOOSE_CHANNEL) {
				if (resultCode == Activity.RESULT_OK) {
					Bundle bundle = data.getExtras();
					if (bundle == null) {
						return;
					}
					Staff staff = (Staff) data.getExtras().getSerializable("staff");
					if (staff != null) {
						tvChooseChannel.setText(staff.getName() + " - " + staff.getStaffCode());
					}
				}
			} else if (requestCode == REQUEST_CHOOSE_STAFF) {
				if (resultCode == Activity.RESULT_OK) {
					Bundle bundle = data.getExtras();
					if (bundle == null) {
						return;
					}
					Staff staff = (Staff) data.getExtras().getSerializable("staff");
					if (staff != null) {
						tvChooseStaff.setText(staff.getName() + " - " + staff.getStaffCode());
						staffDev = staff.getStaffId() + "";
					}
				}
			}
		} catch (Exception e) {
			Log.e(getTag(), e.toString(), e);
		}
	}

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
					arrStockModel = result;
					Bundle mBundle = new Bundle();
					mBundle.putSerializable("arrStockModel", arrStockModel);
					FragmentChoose3G fgChoose3G = new FragmentChoose3G();
					fgChoose3G.setArguments(mBundle);
					fgChoose3G.setTargetFragment(FragmentChange2GTo3G.this, REQUEST_CHOOSE_3G_SERIAL);
					ReplaceFragment.replaceFragment(getActivity(), fgChoose3G, true);
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
				input.addValidateGateway("wscode", "mbccs_getListStockModel3GByStaffCodeBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStockModel3GByStaffCode>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListStockModel3GByStaffCode>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListStockModel3GByStaffCodeBccs2");
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
					nodechild = doc.getElementsByTagName("lstProductOfferingDTO");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						StockModel stockModel = new StockModel();
						nodechildListSerial = e1.getElementsByTagName("lstSerial");
						Log.d("Log", "node list serial: " + nodechildListSerial);
						for (int k = 0; k < nodechildListSerial.getLength(); k++) {
							Element serial = (Element) nodechildListSerial.item(k);
							String strSerial = XmlDomParse.getCharacterDataFromElement(serial);
							stockModel.getLstSerial().add(strSerial);
						}
						Log.d("Log", "parser list" + e1);

						String stockModelId = parse.getValue(e1, "productOfferingId");

						if (stockModelId != null && !stockModelId.equals("")) {
							stockModel.setStockModelId(Long.parseLong(stockModelId));
						}
						stockModel.setStockModelName(parse.getValue(e1, "name"));

						listStockModel.add(stockModel);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

			return listStockModel;
		}
	}

	private class Change2GTo3GAsyn extends AsyncTask<Void, Void, Void> {
		private final Activity mActivity;

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public Change2GTo3GAsyn(Activity mActivity) {
			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			doChange2Gto3G();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				progress.dismiss();
				if (errorCode.equals("0")) {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.change2Gto3Gsuccess),
							getString(R.string.app_name)).show();
					for (int i = 0; i < arrStockModel.size(); i++) {
						StockModel stockModel = arrStockModel.get(i);
						if (stockModel.getStockModelId().compareTo(selectedStockModel.getStockModelId()) == 0) {
							for (int k = 0; k < stockModel.getLstSerial().size(); k++) {
								String selectedSerial = tvImei3G.getText().toString().trim();
								if (stockModel.getLstSerial().get(k).equals(selectedSerial)) {
									stockModel.getLstSerial().remove(k);
									if (stockModel.getLstSerial().size() == 0) {
										arrStockModel.remove(i);
									}
									break;
								}
							}
							break;
						}
					}
					selectedStockModel = null;
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
			} catch (Exception ignored) {
			}

		}

		private void doChange2Gto3G() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_change2GTo3GBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:change2GTo3G>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<change2GTo3GBO>");
				rawData.append("<imei2G>").append(edtImei2G.getText().toString().trim()).append("</imei2G>");
				rawData.append("<imei3G>").append(tvImei3G.getText().toString().trim()).append("</imei3G>");
				rawData.append("<stockModelId3G>").append(selectedStockModel.getStockModelId()).append("</stockModelId3G>");

				rawData.append("<programId>").append(selectedProgramId).append("</programId>");
				rawData.append("<customerName>").append(edtCusName.getText().toString().trim()).append("</customerName>");
				rawData.append("<customerTel>").append(CommonActivity.checkStardardIsdn(edtCusPhone.getText().toString().trim())).append("</customerTel>");
				rawData.append("<productOfferingDTO>");
				rawData.append("<productOfferingId>").append(selectedStockModel.getStockModelId()).append("</productOfferingId>");

				rawData.append("<name>").append(selectedStockModel.getStockModelName()).append("</name>");
				rawData.append("<code>").append(selectedStockModel.getStockModelCode()).append("</code>");
				rawData.append("<checkSerial>" + "1" + "</checkSerial>");
				rawData.append("<lstSerial>").append(tvImei3G.getText().toString().trim()).append("</lstSerial>");

				rawData.append("</productOfferingDTO>");
				Spin vas = (Spin) spiVas.getSelectedItem();
				if (vas != null && !vas.getId().equals("-1")) {
					rawData.append("<dataPackageCode>").append(vas.getId());
					rawData.append("</dataPackageCode>");
				}
				if (!CommonActivity.isNullOrEmpty(staffDev)) {
					rawData.append("<collStaffId>").append(staffDev).append("</collStaffId>");
				}

				rawData.append("</change2GTo3GBO>");
				rawData.append("</input>");
				rawData.append("</ws:change2GTo3G>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(), "mbccs_change2GTo3GBccs2");
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

	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getActivity(), LoginActivity.class);
//			startActivity(intent);
//			getActivity().finish();
//			MainActivity.getInstance().finish();
			
			LoginDialog dialog = new LoginDialog(getActivity(),
					";2Gto3G;");			
			dialog.show();

		}
	};

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.change2Gto3G);
		super.onResume();
	}

	private final OnClickListener saleClick = new OnClickListener() {
		public void onClick(View arg0) {
			Change2GTo3GAsyn asy = new Change2GTo3GAsyn(getActivity());
			asy.execute();
		}
    };

	private boolean validateInput() {
		if (selectedProgramId == null || selectedProgramId.isEmpty()) {
			CommonActivity.createAlertDialog(getActivity(), R.string.errorNotProgram, R.string.app_name).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(edtCusName.getText().toString().trim())) {
			CommonActivity.createAlertDialog(getActivity(), R.string.errorNotCusName, R.string.app_name).show();
			edtCusName.requestFocus();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(edtCusPhone.getText().toString().trim())) {
			CommonActivity.createAlertDialog(getActivity(), R.string.errorNotCusPhone, R.string.app_name).show();
			edtCusPhone.requestFocus();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(edtImei2G.getText().toString().trim())) {
			CommonActivity.createAlertDialog(getActivity(), R.string.errorNotImei2G, R.string.app_name).show();
			edtImei2G.requestFocus();
			return false;
		}

		if (selectedStockModel == null) {
			CommonActivity.createAlertDialog(getActivity(), R.string.errorNot3G, R.string.app_name).show();
			return false;
		}

		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.relaBackHome) {
			getActivity().onBackPressed();
		} else if (v.getId() == R.id.tvHandsetChange) {
			if (arrStockModel != null) {
				Bundle mBundle = new Bundle();
				mBundle.putSerializable("arrStockModel", arrStockModel);
				FragmentChoose3G fgChoose3G = new FragmentChoose3G();
				fgChoose3G.setArguments(mBundle);
				fgChoose3G.setTargetFragment(FragmentChange2GTo3G.this, REQUEST_CHOOSE_3G_SERIAL);
				ReplaceFragment.replaceFragment(getActivity(), fgChoose3G, true);
			} else {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					AsynctaskGetListStockModel asyntaskGetlistStockModel = new AsynctaskGetListStockModel(
							getActivity());
					asyntaskGetlistStockModel.execute();
				} else {
					CommonActivity.createAlertDialog(getActivity(), getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}
			}

		} else if (v.getId() == R.id.btn_change) {
			if (!validateInput()) {
				return;
			}
			if (CommonActivity.isNetworkConnected(getActivity())) {
				Dialog dialog = CommonActivity.createDialog(getActivity(),
						getActivity().getString(R.string.confirm_change_2g_to_3g),
						getActivity().getString(R.string.app_name), getResources().getString(R.string.cancel),
						getResources().getString(R.string.ok ), null, saleClick );
				dialog.show();

			} else {
				CommonActivity.createAlertDialog(getActivity(), getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		} else if (view == tvChooseChannel) {
			FragmentChooseChannel fragment = new FragmentChooseChannel();
			fragment.setTargetFragment(this, REQUEST_CHOOSE_CHANNEL);
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
		}

	}


	@SuppressWarnings("unused")
	private class AsynctaskGetListVas extends AsyncTask<Void, Void, ArrayList<Spin>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynctaskGetListVas(Activity mActivity) {
			prbVas.setVisibility(View.VISIBLE);
			btnRefreshVas.setVisibility(View.GONE);
			this.mActivity = mActivity;
		}

		@Override
		protected ArrayList<Spin> doInBackground(Void... params) {
			return getListVas();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {

			super.onPostExecute(result);
			prbVas.setVisibility(View.GONE);
			btnRefreshVas.setVisibility(View.VISIBLE);
			if ("0".equals(errorCode)) {
                spiVas.setAdapter(
                        new ArrayAdapter<>(mActivity, android.R.layout.simple_dropdown_item_1line, result));
			} else {

				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
							mActivity.getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private ArrayList<Spin> getListVas() {
			ArrayList<Spin> result = new ArrayList<>();
			result = new CacheDatabaseManager(MainActivity.getInstance()).getListCacheWithSpinObject("dataPackage");
			if (result != null && !result.isEmpty()) {
				errorCode = "0";
				Spin first = new Spin("-1", getActivity().getString(R.string.chooseVasProduct));
				result.add(0, first);
				return result;
			}
			result = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListDataPackageBccs2");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListDataPackage>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListDataPackage>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListDataPackageBccs2");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();


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
					nodechild = doc.getElementsByTagName("listDataPackage");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin item = new Spin();
						String programId = parse.getValue(e1, "value");
						if (programId != null && !programId.isEmpty()) {
							item.setId(programId);
						}
						String programName = parse.getValue(e1, "name");
						if (programName != null && !programName.isEmpty()) {
							item.setName(programName);
						}
						result.add(item);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}
			if (result != null && !result.isEmpty()) {
				new CacheDatabaseManager(MainActivity.getInstance()).insertCacheWithSpinObject(result, "dataPackage");

			}
			Spin first = new Spin("-1", mActivity.getString(R.string.chooseVasProduct));
			result.add(0, first);

			return result;

		}
	}
}
