package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.sale.adapter.AdapterIsdnOwnerObject;
import com.viettel.bss.viettelpos.v4.sale.object.IsdnOwnerObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentSearchSerialCard extends Fragment implements OnClickListener {

	private View mView;
	private Activity activity;
	private EditText edt_from_isdn;
	private EditText edt_to_isdn;
	private EditText edt_capcha;
	private Spinner spiner_service;
	private Spinner spiner_container;
	private Spinner spiner_state;
	private Button btn_search;
	private CaptchaView imgCapcha;
	private ListView listViewIsdn;

	private Button btnHome;

	private final ArrayList<AreaObj> mListService = new ArrayList<>();
	private final ArrayList<AreaObj> mListState = new ArrayList<>();
	private ArrayList<AreaObj> mListContainer = new ArrayList<>();

    @Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_sale_search_serial_card, container, false);
			edt_from_isdn = (EditText) mView.findViewById(R.id.edt_from_isdn);
			edt_to_isdn = (EditText) mView.findViewById(R.id.edt_to_isdn);
			edt_capcha = (EditText) mView.findViewById(R.id.edt_capcha);
			spiner_service = (Spinner) mView.findViewById(R.id.spiner_service);
			spiner_container = (Spinner) mView.findViewById(R.id.spiner_container);
			spiner_state = (Spinner) mView.findViewById(R.id.spiner_state);
			btn_search = (Button) mView.findViewById(R.id.btn_search);
			imgCapcha = (CaptchaView) mView.findViewById(R.id.imgCapcha);
			listViewIsdn = (ListView) mView.findViewById(R.id.listViewIsdn);
			loadItemAndShowToSpinner();
		}

		btn_search.setOnClickListener(this);

		return mView;
	}

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.sale_search_serial_card);
		super.onResume();
	}

	@SuppressWarnings("unused")
	private void loadItemAndShowToSpinner() {
		AreaObj areaServiceMobile = new AreaObj();
		areaServiceMobile.setName(getString(R.string.service_mobile));
		areaServiceMobile.setAreaCode("1");
		mListService.add(areaServiceMobile);

		AreaObj areaServiceHomePhone = new AreaObj();
		areaServiceHomePhone.setName(getString(R.string.service_home_phone));
		areaServiceHomePhone.setAreaCode("2");
		mListService.add(areaServiceHomePhone);

		AreaObj areaServicePSTN = new AreaObj();
		areaServicePSTN.setName(getString(R.string.service_pstn));
		areaServicePSTN.setAreaCode("3");
		mListService.add(areaServicePSTN);

		AdapterProvinceSpinner adapterSerVice = new AdapterProvinceSpinner(mListService, activity);
		spiner_service.setAdapter(adapterSerVice);

		// trang thai

		AreaObj areaStateObject = new AreaObj();
		areaStateObject.setName(getString(R.string.selectstatus));
		areaStateObject.setAreaCode("-1");
		mListState.add(areaStateObject);
		
		AreaObj isdn_new = new AreaObj();
		isdn_new.setName(getResources().getString(R.string.isdn_new));
		isdn_new.setAreaCode("1");
		mListState.add(isdn_new);
		
		AreaObj isdn_using = new AreaObj();
		isdn_using.setName(getResources().getString(R.string.isdn_using));
		isdn_using.setAreaCode("2");
		mListState.add(isdn_using);
		
		AreaObj isdn_end_use = new AreaObj();
		isdn_end_use.setName(getResources().getString(R.string.isdn_end_use));
		isdn_end_use.setAreaCode("3");
		mListState.add(isdn_end_use);
		
		AreaObj isdn_start_kit = new AreaObj();
		isdn_start_kit.setName(getResources().getString(R.string.isdn_start_kit));
		isdn_start_kit.setAreaCode("4");
		mListState.add(isdn_start_kit);
		
		AreaObj isdn_lock = new AreaObj();
		isdn_lock.setName(getResources().getString(R.string.isdn_lock));
		isdn_lock.setAreaCode("5");
		mListState.add(isdn_lock);  
		

		AdapterProvinceSpinner adapterState = new AdapterProvinceSpinner(mListState, activity);
		spiner_state.setAdapter(adapterState);

		if (CommonActivity.isNetworkConnected(activity)) {
			AsyntaskGetListStockByStaffCode asyntaskGetListStockByStaffCode = new AsyntaskGetListStockByStaffCode(
					activity);
			asyntaskGetListStockByStaffCode.execute();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			onSearchIsdn();
			break;

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
			LoginDialog dialog = new LoginDialog(getActivity(),
					";sale_search_serial_card;");
			dialog.show();

		}
	};

	private void onSearchIsdn() { 
		CommonActivity.hideKeyboard(btn_search, activity); 
		try { 
			String fromIsdn = edt_from_isdn.getText().toString().trim();
			String toIsdn = edt_to_isdn.getText().toString().trim();

			AreaObj areaService = mListService.get(spiner_service.getSelectedItemPosition());
			String codeCapcha = edt_capcha.getText().toString().trim();
			Dialog dialogError = null;
			if (CommonActivity.isNullOrEmpty(fromIsdn) || CommonActivity.isNullOrEmpty(toIsdn)) {
				dialogError = CommonActivity.createAlertDialog(activity,
						getResources().getString(R.string.please_input_isdn), getString(R.string.app_name));
			} else if (codeCapcha.isEmpty()) {
				dialogError = CommonActivity.createAlertDialog(activity,
						getResources().getString(R.string.please_input_capcha), getString(R.string.app_name));
			} else if (!CaptchaView.match(codeCapcha)) {
				dialogError = CommonActivity.createAlertDialog(activity,
						getResources().getString(R.string.error_input_capcha), getString(R.string.app_name));
			}

			if (dialogError != null) {
				dialogError.show();
				return;
			}

			IsdnOwnerObject isdnOwnerObject = new IsdnOwnerObject();
			isdnOwnerObject.setFromIsdn(fromIsdn);
			isdnOwnerObject.setToIsdn(toIsdn);
			isdnOwnerObject.setStockTypeId(areaService.getAreaCode());

			if (mListContainer.size() > 0) {
				AreaObj areaContainer = mListContainer.get(spiner_container.getSelectedItemPosition());
				if (!areaContainer.getAreaCode().equals("-1")) {
					isdnOwnerObject.setOwnerId(areaContainer.getAreaCode());
					isdnOwnerObject.setOwnerType(areaContainer.getParentCode());
				}
			}

			AreaObj areaState = mListState.get(spiner_state.getSelectedItemPosition());
			if (!areaState.getAreaCode().equals("-1")) {
				isdnOwnerObject.setStatus(areaState.getAreaCode());
			}

			AsyntaskSearchIsdnOwners asyntaskSearchIsdn = new AsyntaskSearchIsdnOwners(activity, isdnOwnerObject);
			asyntaskSearchIsdn.execute();

		} catch (Exception e) {
			Log.d(Constant.TAG, "FragmentSearchISDN onSearchIsdn Exception ", e);
		}

	}

	@SuppressWarnings("unused")
	private class AsyntaskSearchIsdnOwners extends AsyncTask<Void, Void, ArrayList<IsdnOwnerObject>> {

		private final Activity mActivity;
		private AreaObj areaBillDate;

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private final IsdnOwnerObject isdnOwnerObject;

		public AsyntaskSearchIsdnOwners(Activity mActivity, IsdnOwnerObject isdnOwnerObject) {
			this.mActivity = mActivity;
			this.isdnOwnerObject = isdnOwnerObject;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<IsdnOwnerObject> doInBackground(Void... params) {
			return doGetListIsdnOwnerObject();
		}

		@Override
		protected void onPostExecute(ArrayList<IsdnOwnerObject> result) {
			super.onPostExecute(result);
			progress.dismiss();
//			if(imgCapcha != null){
//				imgCapcha.initCaptcha();
//			}
			if(mView != null){
				imgCapcha = (CaptchaView) mView.findViewById(R.id.imgCapcha);
				CaptchaView.initCaptcha();
			}
			
			
			
			
			if (errorCode.equals("0")) {
				edt_capcha.setText("");
				
				
				if (result.size() == 0) {
					CommonActivity.createAlertDialog(mActivity, getString(R.string.not_result_search_isdn),
							getString(R.string.app_name)).show();
				}
                Log.d(Constant.TAG, "onPostExecute mListIsdnOwnerObject " + result.size());
				AdapterIsdnOwnerObject adapterContainer = new AdapterIsdnOwnerObject(mActivity, result);
				listViewIsdn.setAdapter(adapterContainer);
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

		private ArrayList<IsdnOwnerObject> doGetListIsdnOwnerObject() {
			ArrayList<IsdnOwnerObject> listIsdnOwnerObject = new ArrayList<>();

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListIsdnMbccsByRangeIsdnOwnerId");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListIsdnMbccsByRangeIsdnOwnerId>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				if (isdnOwnerObject.getOwnerId() != null) {
					rawData.append("<ownerId>").append(isdnOwnerObject.getOwnerId()).append("</ownerId>");
					rawData.append("<ownerType>").append(isdnOwnerObject.getOwnerType()).append("</ownerType>");
				}
				if (isdnOwnerObject.getStatus() != null) {
					rawData.append("<status>").append(isdnOwnerObject.getStatus()).append("</status>");
				}

				rawData.append("<stockTypeId>").append(isdnOwnerObject.getStockTypeId()).append("</stockTypeId>");
				rawData.append("<fromIsdn>").append(isdnOwnerObject.getFromIsdn()).append("</fromIsdn>");
				rawData.append("<toIsdn>").append(isdnOwnerObject.getToIsdn()).append("</toIsdn>");

				rawData.append("</input>");
				rawData.append("</ws:getListIsdnMbccsByRangeIsdnOwnerId>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListIsdnMbccsByRangeIsdnOwnerId");
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
					nodechild = doc.getElementsByTagName("lstSmartPhoneBO");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Log.d(Constant.TAG, "lstSmartphoneBO count " + j);
						Element e1 = (Element) nodechild.item(j); 
						IsdnOwnerObject isdnOwnerObject = new IsdnOwnerObject();
						isdnOwnerObject.setStaffCode(parse.getValue(e1, "staffCode"));
						isdnOwnerObject.setOwnerCode(parse.getValue(e1, "ownerCode"));
						isdnOwnerObject.setOwnerName(parse.getValue(e1, "ownerName"));
						isdnOwnerObject.setStatus(parse.getValue(e1, "status"));
						isdnOwnerObject.setStatusName(parse.getValue(e1, "statusName"));
						isdnOwnerObject.setStockTypeName(parse.getValue(e1, "stockTypeName"));
						isdnOwnerObject.setStockTypeId(parse.getValue(e1, "stockTypeId"));
						isdnOwnerObject.setOwnerId(parse.getValue(e1, "ownerId"));
						isdnOwnerObject.setOwnerType(parse.getValue(e1, "ownerType"));
						isdnOwnerObject.setIsdn(parse.getValue(e1, "isdn"));
						listIsdnOwnerObject.add(isdnOwnerObject);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

			return listIsdnOwnerObject;
		}

	}

	@SuppressWarnings("unused")
	private class AsyntaskGetListStockByStaffCode extends AsyncTask<Void, Void, ArrayList<AreaObj>> {

		private final Activity mActivity;
		private AreaObj areaBillDate;

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsyntaskGetListStockByStaffCode(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<AreaObj> doInBackground(Void... params) {
			return doGetListStockByStaffCode();
		}

		@Override
		protected void onPostExecute(ArrayList<AreaObj> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				mListContainer = result;
				AdapterProvinceSpinner adapterContainer = new AdapterProvinceSpinner(mListContainer, activity);
				spiner_container.setAdapter(adapterContainer);
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

		private ArrayList<AreaObj> doGetListStockByStaffCode() {
			ArrayList<AreaObj> listStockObject = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStockByStaffCode");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListStockByStaffCode>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getListStockByStaffCode>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListStockByStaffCode");
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
					nodechild = doc.getElementsByTagName("lstSmartPhoneBO");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						AreaObj areaStock = new AreaObj();
						areaStock.setName(parse.getValue(e1, "ownerCode"));
						areaStock.setAreaCode(parse.getValue(e1, "ownerId"));
						areaStock.setParentCode(parse.getValue(e1, "ownerType"));
						listStockObject.add(areaStock);
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

			return listStockObject;

		}

	}

}
