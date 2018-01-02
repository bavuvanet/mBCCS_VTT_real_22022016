package com.viettel.bss.viettelpos.v4.work.activity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.business.BhldBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.BhldObject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public class FragmentWorkUpdateLocationMobileSaling extends FragmentCommon {
	private Spinner spinnerSelectSalePoint;
	private Button btnUpdate;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(R.string.work_update_location_mobile_saling);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// getTask();
		idLayout = R.layout.layout_work_update_location_mobile_saling;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	private ArrayList<BhldObject> arrBhld;

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		spinnerSelectSalePoint = (Spinner) v
				.findViewById(R.id.spinner_select_sale_point);

		btnUpdate = (Button) v.findViewById(R.id.btn_update);
		btnUpdate.setOnClickListener(this);
		arrBhld = BhldBusiness.getListProgram(getActivity());
		if (arrBhld != null && !arrBhld.isEmpty()) {
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getActivity(), R.layout.simple_list_item_single_row,
                    R.id.text1);
			for (BhldObject bhldObject : arrBhld) {
				adapter.add(bhldObject.getSaleProgramName() + "");
			}
			spinnerSelectSalePoint.setAdapter(adapter);
		}
		// ArrayAdapter<String> adapterWorkReason = new ArrayAdapter<String>(
		// getActivity(), android.R.layout.simple_dropdown_item_1line,
		// android.R.id.text1, getResources().getStringArray(
		// R.array.work_reasons));

		// initLocation();
	}

	// private GPSTracker gps;
	// private void initLocation() {
	// // TODO Auto-generated method stub
	// gps = new GPSTracker(getActivity());
	//
	// }
	// private Location getLocation() {
	// // TODO Auto-generated method stub
	// // Geocoder geocoder = new Geocoder(act, Locale.getDefault());
	// // List<Address> addresses = null;
	// boolean flag = false;
	// flag = true;
	// if (!gps.canGetLocation() && flag) {
	// CommonActivity.DoNotLocation(act);
	// return null;
	// }
	// Location myLocation = gps.getLocation();
	// // Location myLocation = gps.getFakeLocation();
	// // toast("submit 1");
	// if (myLocation == null) {
	// CommonActivity.DoNotLocation(act);
	// return null;
	//
	// }
	// return myLocation;
	// }

	Location locationSubmit;
	private final String tag = "work update location";

	private void submit() {
		// toast("submit");
		// TODO Auto-generated method stub
		if (arrBhld == null || arrBhld.isEmpty()) {
			CommonActivity.createAlertDialog(act, R.string.textChoosePoint,
					R.string.app_name).show();
			return;
		}
		location = CommonActivity.findMyLocation(act);

		if (!location.getX().equals("0") && !location.getY().equals("0")) {
			CommonActivity.createDialog(act, R.string.updateConfirm,
					R.string.app_name, R.string.buttonOk,
					R.string.buttonCancel, onClickListenerUpdate, null).show();
		} else {
			CommonActivity.DoNotLocation(act);
		}
	}

	private com.viettel.bss.viettelpos.v4.object.Location location;
	// private void submit_() {
	// // toast("submit");
	// // TODO Auto-generated method stub
	// if (arrBhld == null || arrBhld.isEmpty()) {
	// CommonActivity.createAlertDialog(act, R.string.textChoosePoint,
	// R.string.app_name).show();
	// return;
	// }
	// locationSubmit = getLocation();
	// if (locationSubmit != null && locationSubmit.getLatitude() != 0
	// && locationSubmit.getLongitude() != 0) {
	// CommonActivity.createDialog(act, R.string.updateConfirm,
	// R.string.app_name, R.string.buttonOk,
	// R.string.buttonCancel, onClickListenerUpdate, null).show();
	// }
	// }

	// mbccs_updateBHLDLocation
	//
	// input:
	//
	// private String x;
	// private String y;
	// private String recordWorkId;

	private final OnClickListener onClickListenerUpdate = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// start update
			AsyncTaskUpdateSender asyncTaskUpdateSender = new AsyncTaskUpdateSender(
					act,
					// locationSubmit
					location.getX(), location.getY());
			asyncTaskUpdateSender.execute();
		}
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);

		int id = arg0.getId();
		switch (id) {

		case R.id.txt_date_to:
			break;
		case R.id.btn_update:
			if (!CommonActivity.isNetworkConnected(getActivity())) {// ok
				Log.e(tag, "net disconn");
				CommonActivity.createAlertDialog(getActivity(),
						R.string.errorNetwork, R.string.app_name).show();
				return;
			} else {
				Log.e(tag, "net ok");
			}
			submit();
			break;

		default:
			break;
		}

	}

	private class AsyncTaskUpdateSender extends
			AsyncTask<Integer, Void, String> {
		private final Context context;
		final ProgressDialog progress;
		private String original;
		final XmlDomParse parse = new XmlDomParse();
		String description = "";
		String errorCode = "";
		Location location;
		String x, y;

		public AsyncTaskUpdateSender(Context context, Location location) {
			this.context = context;
			this.location = location;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		public AsyncTaskUpdateSender(Context context, String x, String y) {
			this.context = context;
			this.x = x;
			this.y = y;
			this.progress = new ProgressDialog(this.context);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			int i = spinnerSelectSalePoint.getSelectedItemPosition();
			return sendRequestUpdate(
			// location,
			arrBhld.get(i).getRecordWorkId() + "");
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			this.progress.dismiss();
			Log.i("TAG", " Ket qua cap nhat cham soc " + result);
			if (errorCode.equals(Constant.INVALID_TOKEN2)
					&& description != null && !description.isEmpty()) {

				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						description, getResources()
								.getString(R.string.app_name), moveLogInAct);
				dialog.show();
				return;
			} else {

			}
			if (Constant.SUCCESS_CODE.equals(result)) {
				CommonActivity.createAlertDialog(act, R.string.updatesucess,
						R.string.app_name, onClickListenerBack).show();
			}
			if (Constant.ERROR_CODE.equals(result)) {
				String msg = act.getString(R.string.updatefail) + "\n"
						+ description;
				CommonActivity.createAlertDialog(act, msg,
						act.getString(R.string.app_name)).show();
			}
		}

		private String sendRequestUpdate(String idBhld) {
			try {
				// TODO Auto-generated method stub
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("wscode", "mbccs_updateBHLDLocation");
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				// <token>?</token>
				input.addParam("token", Session.getToken());
				// <objectId>?</objectId>
				input.addParam("x", x);
				// <taskRoadId>?</taskRoadId>
				input.addParam("y", y);
				input.addParam("recordWorkId", idBhld + "");
				String envelope = input.buildInputGateway();
				Log.e("envlop policy", envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateBHLDLocation");
				Log.e("response update BHLD", response);
				CommonOutput output = input.parseGWResponse(response);
				if (output == null) {
					return Constant.ERROR_CODE;
				}
				String errorMessage = null;
				original = output.getOriginal();
				if (original == null || original.isEmpty()) {
					return Constant.ERROR_CODE;
				}
				Document doc = parse.getDomElement(original);
				if (doc == null) {
					// CommonActivity.createAlertDialog(act,
					// R.string.task_unavail, R.string.app_name).show();
					return Constant.ERROR_CODE;
				}
				NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				if (nl == null || nl.getLength() == 0) {
					return Constant.ERROR_CODE;
				}

				Log.e(tag, nl.getLength() + "...node list size");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.e("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.e("description", description);
					// nodechild = doc.getElementsByTagName("lstStockOrder");

				}
				if (!errorCode.equals("0")) {
					// if (!output.getError().equals("0")) {
					errorMessage = output.getDescription();
					Log.e(tag, errorMessage);
					return Constant.ERROR_CODE;
				}
				// description = output.getOriginal();
				// Log.e(tag, original);
				return Constant.SUCCESS_CODE;
			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}
		// private String sendRequestUpdate(Location location, String idBhld) {
		// try {
		// // TODO Auto-generated method stub
		// BCCSGateway input = new BCCSGateway();
		// input.addValidateGateway("wscode", "mbccs_updateBHLDLocation");
		// input.addValidateGateway("username", Constant.BCCSGW_USER);
		// input.addValidateGateway("password", Constant.BCCSGW_PASS);
		// // <token>?</token>
		// input.addParam("token", Session.getToken());
		// // <objectId>?</objectId>
		// input.addParam("x", location.getLatitude() + "");
		// // <taskRoadId>?</taskRoadId>
		// input.addParam("y", location.getLongitude() + "");
		// input.addParam("recordWorkId", idBhld + "");
		// String envelope = input.buildInputGateway();
		// Log.e("envlop policy", envelope);
		// String response = input.sendRequest(envelope,
		// Constant.BCCS_GW_URL);
		// Log.e("response update BHLD", response);
		// CommonOutput output = input.parseGWResponse(response);
		// if (output == null) {
		// return Constant.ERROR_CODE;
		// }
		// String errorMessage = null;
		// original = output.getOriginal();
		// if (original == null || original.isEmpty()) {
		// return Constant.ERROR_CODE;
		// }
		// Document doc = parse.getDomElement(original);
		// if (doc == null) {
		// // CommonActivity.createAlertDialog(act,
		// // R.string.task_unavail, R.string.app_name).show();
		// return Constant.ERROR_CODE;
		// }
		// NodeList nl = doc.getElementsByTagName("return");
		// // NodeList nodechild = null;
		// if (nl == null || nl.getLength() == 0) {
		// return Constant.ERROR_CODE;
		// }
		//
		// Log.e(tag, nl.getLength() + "...node list size");
		// for (int i = 0; i < nl.getLength(); i++) {
		// Element e2 = (Element) nl.item(i);
		// errorCode = parse.getValue(e2, "errorCode");
		// Log.e("errorCode", errorCode);
		// description = parse.getValue(e2, "description");
		// Log.e("description", description);
		// // nodechild = doc.getElementsByTagName("lstStockOrder");
		//
		// }
		// if (!output.getError().equals("0")) {
		// errorMessage = output.getDescription();
		// Log.e(tag, errorMessage);
		// return Constant.ERROR_CODE;
		// }
		// // description = output.getOriginal();
		// // Log.e(tag, original);
		// return Constant.SUCCESS_CODE;
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		//
		// return null;
		// }
	}// asyncUpdateSender

	@Override
	public void setPermission() {
		permission = ";work_management;";

	}
}
