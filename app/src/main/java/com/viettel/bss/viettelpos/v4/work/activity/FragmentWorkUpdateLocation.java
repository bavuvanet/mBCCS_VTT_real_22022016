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
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.ApParamObj;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public class FragmentWorkUpdateLocation extends FragmentCommon {
	private EditText edtWorkContent;
	private Spinner spnReason;
	private Button btnUpdate;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(R.string.work_update_location);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// getTask();
		idLayout = R.layout.layout_work_update_location;
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	private ArrayList<ApParamObj> arrApParamObjs;

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		edtWorkContent = (EditText) v.findViewById(R.id.edit_work_content);
		spnReason = (Spinner) v.findViewById(R.id.spinner_work_reason);

		btnUpdate = (Button) v.findViewById(R.id.btn_update);
		btnUpdate.setOnClickListener(this);
		arrApParamObjs = (new ApParamDAL(act)).getArrReasons();
		ArrayAdapter<String> adapterWorkReason = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_dropdown_item_1line,
                android.R.id.text1);
		for (ApParamObj apParamObj : arrApParamObjs) {
			adapterWorkReason.add(apParamObj.getParValue());
		}
		spnReason.setAdapter(adapterWorkReason);
		// initLocation();
	}

	// SQLiteDatabase db;
	private GPSTracker gps;

	// private void initLocation() {
	// // TODO Auto-generated method stub
	// gps = new GPSTracker(getActivity());
	//
	// }
	// private com.viettel.bss.viettelpos.v4.object.Location findMyLocation() {
	// // TODO Auto-generated method stub
	// // Geocoder geocoder = new Geocoder(act, Locale.getDefault());
	// // List<Address> addresses = null;
	// com.viettel.bss.viettelpos.v4.object.Location location =
	// CommonActivity.findMyLocation(getActivity());
	// if(!location.getX().equals("0") &&!location.getY().equals("0")) {
	// // if (!gps.canGetLocation() && flag) {
	// CommonActivity.DoNotLocation(act);
	// return null;
	// }
	// // Location myLocation = gps.getLocation();
	// // Location myLocation = gps.getFakeLocation();
	// // toast("submit 1");
	// if (myLocation == null) {
	// CommonActivity.DoNotLocation(act);
	// return null;
	//
	// }
	// return myLocation;
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

	private String getTextWorkContent() {
		// TODO Auto-generated method stub
		String textWorkContent = edtWorkContent.getText().toString().trim();
		if (!StringUtils.CheckCharSpecical(textWorkContent)) {
			return textWorkContent;
		}
		return null;
	}

	String errorMessage;
	private final String tag = "work update location";
	Location locationSubmit;
	private String textWorkContent;

	private void submit() {
		// toast("submit");
		// TODO Auto-generated method stub
		if (!CommonActivity.isNetworkConnected(getActivity())) {// ok
			Log.e(tag, "net disconn");
			CommonActivity.createAlertDialog(getActivity(),
					R.string.errorNetwork, R.string.app_name).show();
			return;
		} else {
			Log.e(tag, "net ok");
		}
		if (arrApParamObjs == null || arrApParamObjs.isEmpty()) {
			CommonActivity.createAlertDialog(act, R.string.choose_reason_not,
					R.string.app_name).show();
			return;
		}
		int i = spnReason.getSelectedItemPosition();
		textWorkContent = getTextWorkContent();
		if (textWorkContent == null) {
			CommonActivity.createAlertDialog(getActivity(),
					R.string.special_character_work_content, R.string.app_name)
					.show();
			return;
		}
		Log.e(tag, textWorkContent + "..................." + i);
		location = CommonActivity.findMyLocation(act);

        CommonActivity.createDialog(act, R.string.updateConfirm,
                R.string.app_name, R.string.buttonOk,
                R.string.buttonCancel, onClickListenerUpdate, null).show();

    }

	private com.viettel.bss.viettelpos.v4.object.Location location;
	// private void submit_() {
	// // toast("submit");
	// // TODO Auto-generated method stub
	// if (!CommonActivity.isNetworkConnected(getActivity())) {// ok
	// Log.e(tag, "net disconn");
	// CommonActivity.createAlertDialog(getActivity(),
	// R.string.errorNetwork, R.string.app_name).show();
	// return;
	// } else {
	// Log.e(tag, "net ok");
	// }
	// if (arrApParamObjs == null || arrApParamObjs.isEmpty()) {
	// CommonActivity.createAlertDialog(act, R.string.choose_reason_not,
	// R.string.app_name).show();
	// return;
	// }
	// int i = spnReason.getSelectedItemPosition();
	// textWorkContent = getTextWorkContent();
	// if (textWorkContent == null) {
	// CommonActivity.createAlertDialog(getActivity(),
	// R.string.special_character_work_content, R.string.app_name)
	// .show();
	// return;
	// }
	// Log.e(tag, textWorkContent + "..................." + i);
	// locationSubmit = getLocation();
	// if (locationSubmit != null && locationSubmit.getLatitude() != 0
	// && locationSubmit.getLongitude() != 0) {
	// CommonActivity.createDialog(act, R.string.updateConfirm,
	// R.string.app_name, R.string.buttonOk,
	// R.string.buttonCancel, onClickListenerUpdate, null).show();
	// }
	// }
	// Location locationSubmit;
    private final OnClickListener onClickListenerUpdate = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// start update
			AsyncTaskUpdateSender asyncTaskUpdateSender = new AsyncTaskUpdateSender(
					act,
					// locationSubmit
					location.getX(), location.getY()
			// "10", "10"
			);
			asyncTaskUpdateSender.execute();
		}
	};

	private boolean checkOldData() {
		// TODO Auto-generated method stub
		return edtWorkContent.getText().toString().trim().isEmpty();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);

		int id = arg0.getId();
		switch (id) {
		// case R.id.relaBackHome :
		// if (checkOldData()) {
		// CommonActivity.hideKeyboard(edtWorkContent, act);
		// getActivity().onBackPressed();
		// } else {
		// CommonActivity.createDialog(act, R.string.back_confirm,
		// R.string.app_name, R.string.buttonOk,
		// R.string.buttonCancel, onClickListenerBack, null)
		// .show();
		// }
		// CommonActivity.hideKeyboard(edtWorkContent, act);
		// getActivity().onBackPressed();
		// break;
		case R.id.txt_date_to:

			break;
		case R.id.btn_update:
			submit();
			break;

		default:
			break;
		}

	}

	// int[] arrWorkReasonId = new int[]{R.string.work_reason1,
	// R.string.work_reason2, R.string.work_reason3,
	// R.string.work_reason4, R.string.work_reason5,};
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
			int i = spnReason.getSelectedItemPosition();

			return sendRequestUpdate(// location,
					arrApParamObjs.get(i).getParType(), textWorkContent);
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

		private String sendRequestUpdate(String parType, String taskContent) {
			try {
				// TODO Auto-generated method stub
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("wscode", "mbccs_updateTaskLocation");
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addParam("token", Session.getToken());
				input.addParam("parType", parType + "");
				input.addParam("taskContent", taskContent + "");
				input.addParam("x", x);
				input.addParam("y", y);
				String envelope = input.buildInputGateway();
				Log.e("envlop policy", envelope);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateTaskLocation");
				Log.e("res update task loc", response);
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
					// if (!output.getErrorCode().equals("0")) {
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
				e.printStackTrace();
			}

			return null;
		}

		// private String sendRequestUpdate(Location location, String parType,
		// String taskContent) {
		// try {
		// // TODO Auto-generated method stub
		// BCCSGateway input = new BCCSGateway();
		// input.addValidateGateway("wscode", "mbccs_updateTaskLocation");
		// input.addValidateGateway("username", Constant.BCCSGW_USER);
		// input.addValidateGateway("password", Constant.BCCSGW_PASS);
		// input.addParam("token", Session.getToken());
		// input.addParam("parType", parType + "");
		// input.addParam("taskContent", taskContent + "");
		// input.addParam("x", location.getLatitude() + "");
		// input.addParam("y", location.getLongitude() + "");
		// String envelope = input.buildInputGateway();
		// Log.e("envlop policy", envelope);
		// String response = input.sendRequest(envelope,
		// Constant.BCCS_GW_URL);
		// Log.e("res update task loc", response);
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
		// e.printStackTrace();
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
