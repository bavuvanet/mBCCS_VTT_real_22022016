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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.adapter.PlaceToSaleAdapter;
import com.viettel.bss.viettelpos.v4.sale.object.PlaceToSaleObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentSearchPlaceSale extends Fragment implements OnClickListener,OnItemClickListener {

	private View mView;
	private Activity activity;
	private EditText edt_code_place_sale;
	private EditText edt_name_place_sale;
	private EditText edt_phone_number;
	private EditText edt_isdn_agent;
	private Button btn_search;
	private ListView listview_info_place_sale;
	private PlaceToSaleAdapter mPlaceTosaleAdapter;
	private ArrayList<PlaceToSaleObject> mlistPlace = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_search_place_sale, container, false);
			edt_code_place_sale = (EditText) mView.findViewById(R.id.edt_code_place_sale);
			edt_name_place_sale = (EditText) mView.findViewById(R.id.edt_name_place_sale);
			edt_phone_number = (EditText) mView.findViewById(R.id.edt_phone_number);
			edt_isdn_agent   = (EditText) mView.findViewById(R.id.edt_isdn_agent);
			btn_search = (Button) mView.findViewById(R.id.btn_search);
			listview_info_place_sale = (ListView) mView.findViewById(R.id.listview_info_place_sale);
		}
 
		btn_search.setOnClickListener(this);
		listview_info_place_sale.setOnItemClickListener(this);

		return mView;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.title_fragment_sale_custommer);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
			
		case R.id.btn_search:
			onSearchPlaceSaleToCustomer();
			break;

		default:
			break;
		}

	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	private void onSearchPlaceSaleToCustomer() {
		CommonActivity.hideKeyboard(btn_search, activity);
		String staffCode = edt_code_place_sale.getText().toString();
		String staffName = edt_name_place_sale.getText().toString();
		String phoneNumber = edt_phone_number.getText().toString();
		String isdnAgent = edt_isdn_agent.getText().toString();
		if (	!CommonActivity.isNullOrEmpty(staffCode) 
				|| !CommonActivity.isNullOrEmpty(staffName)
				|| !CommonActivity.isNullOrEmpty(phoneNumber)
				|| !CommonActivity.isNullOrEmpty(isdnAgent)) {
			
//			if (!CommonActivity.isNullOrEmpty(phoneNumber) && !CommonActivity.validateIsdn(phoneNumber)) {
//				CommonActivity.createAlertDialog(activity, getString(R.string.isdn_error_validate),
//						getString(R.string.app_name)).show();
//			} else {
				if (CommonActivity.isNetworkConnected(activity)) { 
					mlistPlace.clear();
					if (mPlaceTosaleAdapter == null) {
						mPlaceTosaleAdapter = new PlaceToSaleAdapter(activity, mlistPlace);
						listview_info_place_sale.setAdapter(mPlaceTosaleAdapter);
					} else {
						mPlaceTosaleAdapter.notifyDataSetChanged();
					} 
					
					PlaceToSaleObject placeObject = new PlaceToSaleObject();
					placeObject.setStaffCode(staffCode);
					placeObject.setStaffName(staffName);
					
					if (phoneNumber.length() > 0) {
						phoneNumber = "0" + CommonActivity.checkStardardIsdn(phoneNumber);
					}
					
					if (isdnAgent.length() > 0) {
						isdnAgent = "0" + CommonActivity.checkStardardIsdn(isdnAgent);
					} 
					
					placeObject.setTel(phoneNumber);
					placeObject.setIsdnAgent(isdnAgent);
					AsyntaskSearchPlaceToSale asyntaskSearchPlaceToSale = new AsyntaskSearchPlaceToSale(placeObject,
							activity);
					asyntaskSearchPlaceToSale.execute();
				} else {
					CommonActivity.createAlertDialog(activity, getResources().getString(R.string.errorNetwork),
							getResources().getString(R.string.app_name)).show();
				}
//			}
		} else {
			CommonActivity.createAlertDialog(activity, getString(R.string.Plase_Input_Info_To_Search),
					getString(R.string.app_name)).show();
		}
	}

	@SuppressWarnings("unused")
	private class AsyntaskSearchPlaceToSale extends AsyncTask<Void, Void, ArrayList<PlaceToSaleObject>> {
		private final Activity mActivity;
		private final PlaceToSaleObject placeObject;

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsyntaskSearchPlaceToSale(PlaceToSaleObject placeObject, Activity mActivity) {
			this.placeObject = placeObject;
			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<PlaceToSaleObject> doInBackground(Void... params) {

			return doGetListPlaceObject(placeObject);
		}

		@Override
		protected void onPostExecute(ArrayList<PlaceToSaleObject> result) {

			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {

				if (result != null && result.size() > 0) {
					mlistPlace = result;
				} else {
					mlistPlace.clear();
					CommonActivity.createAlertDialog(mActivity, getResources().getString(R.string.not_result_search),
							getResources().getString(R.string.app_name)).show();
				} 
				Log.d(Constant.TAG, "Count list place: " + mlistPlace.size());
				mPlaceTosaleAdapter = new PlaceToSaleAdapter(activity, mlistPlace);
				listview_info_place_sale.setAdapter(mPlaceTosaleAdapter);
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

		private ArrayList<PlaceToSaleObject> doGetListPlaceObject(PlaceToSaleObject placeObject) {
			ArrayList<PlaceToSaleObject> listPlaceSale = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_searchChannel");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchChannel>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<isdnAgent>").append(placeObject.getIsdnAgent()).append("</isdnAgent>");
				rawData.append("<staffCode>").append(placeObject.getStaffCode()).append("</staffCode>");
				rawData.append("<staffName>").append(placeObject.getStaffName()).append("</staffName>");
				rawData.append("<tel>").append(placeObject.getTel()).append("</tel>");
				rawData.append("</input>");
				rawData.append("</ws:searchChannel>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_searchChannel");
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
					nodechild = doc.getElementsByTagName("lstStaff");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						PlaceToSaleObject placeToSaleObject = new PlaceToSaleObject();
						placeToSaleObject.setStaffName(parse.getValue(e1, "name"));
						placeToSaleObject.setStaffCode(parse.getValue(e1, "staffCode"));
						placeToSaleObject.setStaffId(parse.getValue(e1, "staffId"));
						placeToSaleObject.setTel(parse.getValue(e1, "tel"));
						listPlaceSale.add(placeToSaleObject); 
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString() + "description error", e);
			}

			return listPlaceSale;

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PlaceToSaleObject placeSaleObject = mlistPlace.get(position);
		Intent intent = new Intent(); 
		intent.putExtra("placeSaleObject", placeSaleObject);
		getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
		activity.onBackPressed();
	}

}
