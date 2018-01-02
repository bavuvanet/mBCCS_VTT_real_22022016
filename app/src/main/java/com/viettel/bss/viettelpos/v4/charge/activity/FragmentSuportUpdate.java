package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentSuportUpdate extends FragmentCommon {

	private Activity activity;

    private Spinner spnSupport;
	private LinearLayout lnManager;
	private Spinner spnManager;

    private String types[];

	private List<Staff> lstStaff;
	private Staff staff;

	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.support_update);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_support_update;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public void unit(View v) {
        TextView tvStaff = (TextView) v.findViewById(R.id.tvStaff);
		tvStaff.setText(Session.userName.toUpperCase());

		spnSupport = (Spinner) v.findViewById(R.id.spnSupport);
		lnManager = (LinearLayout) v.findViewById(R.id.lnManager);
		spnManager = (Spinner) v.findViewById(R.id.spnManager);

        Button btn_update = (Button) v.findViewById(R.id.btn_update);

		spnSupport.setSelected(false);

		spnSupport
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Log.d(Constant.TAG, "spnSupport onItemClick position:"
								+ position);
						if (position == 1) {
							lnManager.setVisibility(View.VISIBLE);
						} else if (position == 2) {
							lnManager.setVisibility(View.GONE);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						Log.d(Constant.TAG, "spnSupport onNothingSelected");
					}
				});

		spnManager
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Log.d(Constant.TAG, "spnManager onItemClick position:"
								+ position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						Log.d(Constant.TAG, "spnManager onNothingSelected");
					}
				});

		btn_update.setOnClickListener(this);

		types = getResources().getStringArray(R.array.support_value);

		AsyncGetStaffAndGroup asynctask = new AsyncGetStaffAndGroup();
		asynctask.execute();
	}

	// confirm charge
	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				AsyncUpdateSupport asyncUpdateSupport;
				if (posSupport == 1) {
					int posManager = spnManager.getSelectedItemPosition();
					Staff manager = lstStaff.get(posManager);
					asyncUpdateSupport = new AsyncUpdateSupport(activity, staff
							.getStaffId().toString(), manager.getStaffId()
							.toString(), type, descript);
					asyncUpdateSupport.execute();
					Log.d(Constant.TAG,
							"FragmentSuportUpdate onClick btn_update posSupport:"
									+ posSupport + " "
									+ asyncUpdateSupport.toString());
				} else if (posSupport == 2) {
					asyncUpdateSupport = new AsyncUpdateSupport(activity, staff
							.getStaffId().toString(), "", type, "");
					asyncUpdateSupport.execute();
					Log.d(Constant.TAG,
							"FragmentSuportUpdate onClick btn_update posSupport:"
									+ posSupport + " "
									+ asyncUpdateSupport.toString());
				}
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	private int posSupport;
	private String type;
	private String descript;

	@Override
	public void onClick(View view) {
		super.onClick(view);

		switch (view.getId()) {
		case R.id.btn_update:
			if (CommonActivity.isNetworkConnected(activity)) {
				posSupport = spnSupport.getSelectedItemPosition();
				if (posSupport > 0) {
					type = types[posSupport];
					descript = "";

					CommonActivity.createDialog(activity,
							getString(R.string.message_confirm_update),
							getString(R.string.app_name),
							getString(R.string.say_ko),
							getString(R.string.say_co), null, confirmChargeAct)
							.show();
				} else {
					Log.d(Constant.TAG,
							"FragmentSuportUpdate onClick btn_update posSupport: "
									+ posSupport);
					CommonActivity.createAlertDialog(activity,
							getString(R.string.support_update_not_select),
							getString(R.string.app_name)).show();
				}
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}
	}

	private class AsyncUpdateSupport extends AsyncTask<String, Void, Boolean> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String errorDescription = "";
		private ProgressDialog progress;

		private final String staffId;
		private final String nngtStaffId;
		private final String type;
		private final String descript;

		public AsyncUpdateSupport(Activity mActivity, String staffId,
				String nngtStaffId, String type, String descript) {
			super();
			this.mActivity = mActivity;
			this.staffId = staffId;
			this.nngtStaffId = nngtStaffId;
			this.type = type;
			this.descript = descript;
		}

		@Override
		protected void onPreExecute() {
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected Boolean doInBackground(String... params) {
			return updateSupport();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				CommonActivity.createAlertDialog(mActivity,
						getString(R.string.updatesucess),
						getString(R.string.app_name)).show();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									mActivity,
									mActivity.getResources().getString(
											R.string.end_token),
									mActivity.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (errorDescription == null || errorDescription.isEmpty()) {
						errorDescription = mActivity
								.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), errorDescription, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private Boolean updateSupport() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateSupport");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateSupport>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<staffId>").append(staffId).append("</staffId>");
				rawData.append("<nngtStaffId>").append(nngtStaffId).append("</nngtStaffId>");
				rawData.append("<type>").append(type).append("</type>");
				rawData.append("<description>").append(descript).append("</description>");

				rawData.append("</input>");
				rawData.append("</ws:updateSupport>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateSupport");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					errorDescription = parse.getValue(e2, "description");
					Log.d(Constant.TAG,
							"FragmentSuportUpdate updateSupport errorCode: "
									+ errorCode + " errorDescription: "
									+ errorDescription);
					if ("0".equalsIgnoreCase(errorCode)) {

					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentSuportUpdate updateSupport", e);
			}
			return false;
		}

		@Override
		public String toString() {
            return "{\"AsyncUpdateSupport\":{\"staffId\":\"" +
                    staffId + "\", \"nngtStaffId\":\"" +
                    nngtStaffId + "\", \"type\":\"" + type +
                    "\", \"descript\":\"" + descript +
                    "\"}}";
		}
	}

	private class AsyncGetStaffAndGroup extends
			AsyncTask<String, Void, List<Staff>> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.progress = new ProgressDialog(activity);
			this.progress.setCancelable(false);
			this.progress.setMessage(activity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

        @Override
		protected List<Staff> doInBackground(String... params) {

			return getStaffandGroup();
		}

		@Override
		protected void onPostExecute(List<Staff> result) {
			super.onPostExecute(result);

			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					lstStaff = result;
					List<String> list = new ArrayList<>();
					for (Staff staff : result) {
						list.add(staff.getName());
					}

					ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                            activity, android.R.layout.simple_spinner_item,
                            list);
					dataAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnManager.setAdapter(dataAdapter);

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.message_not_result_staff),
							getString(R.string.app_name)).show();
				}
				Log.d(Constant.TAG,
						"AsyncGetStaffAndGroup onPostExecute: result.size()"
								+ result.size());
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(activity, description, activity
									.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = activity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private List<Staff> getStaffandGroup() {
			List<Staff> list = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getStaffandGroup");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getStaffandGroup>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getStaffandGroup>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getStaffandGroup");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodeLstStaffBean = null;
				NodeList nodeStaffBean = null;

				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodeLstStaffBean = doc.getElementsByTagName("lstStaffBean");
					for (int j = 0; j < nodeLstStaffBean.getLength(); j++) {
						Element e1 = (Element) nodeLstStaffBean.item(j);
						Staff obj = new Staff();
						String staffId = parse.getValue(e1, "staffId");
						obj.setStaffId(Long.parseLong(staffId));
						obj.setStaffCode(parse.getValue(e1, "staffCode"));
						obj.setName(parse.getValue(e1, "name"));

						list.add(obj);
					}

					nodeStaffBean = doc.getElementsByTagName("staffBean");
					for (int j = 0; j < nodeStaffBean.getLength(); j++) {
						Element e1 = (Element) nodeStaffBean.item(j);
						staff = new Staff();
						String staffId = parse.getValue(e1, "staffId");
						staff.setStaffId(Long.parseLong(staffId));
						staff.setStaffCode(parse.getValue(e1, "staffCode"));
						staff.setName(parse.getValue(e1, "name"));
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "AsyncGetStaffAndGroup getStaffandGroup", e);
			}
			return list;
		}
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		permission = ";support_update;";
	}
}
