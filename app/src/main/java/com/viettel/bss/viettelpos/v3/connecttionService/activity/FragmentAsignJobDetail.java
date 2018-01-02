package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentAsignJobDetail extends FragmentCommon implements
		OnClickListener, OnItemClickListener {

	private ReceiveRequestBean receiveRequestBean;

	private List<Spin> lstStatus = new ArrayList<Spin>();
	private List<Spin> lstReason = new ArrayList<Spin>();

	private Spinner spnStatus;
	private Spinner spnReason;
	private EditText edtNote;

	private String status;
	private String reasonContent;
	private String note;

	private ProgressBar pbStatus;
	private ProgressBar pbReason;

	private RelativeLayout rlReason;

	private EditText edtStaff;

	private ArrayList<Staff> arrStaff = new ArrayList<Staff>();

	private Staff staff = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.connection_dialog_asign_hotline_update;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBundle = getArguments();
		if (mBundle != null) {
			receiveRequestBean = (ReceiveRequestBean) mBundle
					.getSerializable("receiveRequestBean");
			Log.d(this.getClass().getSimpleName(),
					"onCreate receiveRequestBean "
							+ receiveRequestBean.getReciveRequestId());
		}
	}

	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.text_menu_assignment);
	}

	@Override
	public void unit(View v) {

		edtStaff = (EditText) v.findViewById(R.id.edtStaff);
		edtStaff.setOnClickListener(this);

		EditText edtidrequesthotline = (EditText) v
				.findViewById(R.id.edtidrequesthotline);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean
				.getReciveRequestId())) {
			edtidrequesthotline
					.setText(receiveRequestBean.getReciveRequestId());
		}
		EditText edttenkhachhang = (EditText) v
				.findViewById(R.id.edttenkhachhang);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getCustomerName())) {
			edttenkhachhang.setText(receiveRequestBean.getCustomerName());
		}

		EditText edtsodthotline = (EditText) v
				.findViewById(R.id.edtsodthotline);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getTel())) {
			edtsodthotline.setText(receiveRequestBean.getTel());
		}

		EditText edtdiachihotline = (EditText) v
				.findViewById(R.id.edtdiachihotline);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean.getAddress())) {
			edtdiachihotline.setText(receiveRequestBean.getAddress());
		}

		EditText edtnoidunghotline = (EditText) v
				.findViewById(R.id.edtnoidunghotline);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean
				.getContentRequest())) {
			edtnoidunghotline.setText(receiveRequestBean.getContentRequest());
		}

		EditText edtnguoikhaosat = (EditText) v
				.findViewById(R.id.edtnguoikhaosat);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean
				.getUserUpdateSurvey())) {
			edtnguoikhaosat.setText(receiveRequestBean.getUserUpdateSurvey());
		}

		EditText edtndkhaosat = (EditText) v.findViewById(R.id.edtndkhaosat);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean
				.getDesciptionSurvey())) {
			edtndkhaosat.setText(receiveRequestBean.getDesciptionSurvey());
		}

		EditText edtdichvuhotline = (EditText) v
				.findViewById(R.id.edtdichvuhotline);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean
				.getTelecomServiceName())) {
			edtdichvuhotline
					.setText(receiveRequestBean.getTelecomServiceName());
		}

		EditText edtReasonContent = (EditText) v
				.findViewById(R.id.edtReasonContent);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean
				.getTelecomServiceName())) {
			edtReasonContent
					.setText(receiveRequestBean.getTelecomServiceName());
		}

		EditText edtStatus = (EditText) v.findViewById(R.id.edtStatus);
		if (!CommonActivity.isNullOrEmpty(receiveRequestBean
				.getTelecomServiceName())) {
			edtStatus.setText(receiveRequestBean.getStatusDislay());
		}

		rlReason = (RelativeLayout) v.findViewById(R.id.rlReason);

		pbStatus = (ProgressBar) v.findViewById(R.id.pbStatus);
		pbReason = (ProgressBar) v.findViewById(R.id.pbReason);
		pbStatus.setVisibility(View.GONE);
		pbReason.setVisibility(View.GONE);

		spnStatus = (Spinner) v.findViewById(R.id.spnStatus);
		spnReason = (Spinner) v.findViewById(R.id.spnReason);
		edtNote = (EditText) v.findViewById(R.id.edtNote);

		if (CommonActivity.isNetworkConnected(getActivity())) {
			AsyncGetListStatusForCM async = new AsyncGetListStatusForCM(act,
					pbStatus);
			async.execute();
			GetListStaffAsyn getStaffAsyn = new GetListStaffAsyn(getActivity());
			getStaffAsyn.execute();
		}

		Button btnUpdate = (Button) v.findViewById(R.id.btn_Update_Status_CM);
		btnUpdate.setOnClickListener(this);

		Button btncanel = (Button) v.findViewById(R.id.btncanel);
		btncanel.setOnClickListener(this);

		spnStatus
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						Log.d(this.getClass().getSimpleName(),
								"spnStatus onItemSelected position " + position);
						if (position > 0) {
							Spin spin = (Spin) parent.getAdapter().getItem(
									position);
							if ("22".equalsIgnoreCase(spin.getId())) {
								rlReason.setVisibility(View.GONE);
							} else {
								rlReason.setVisibility(View.VISIBLE);
								AsyncGetListReasonOfStatusForCM async = new AsyncGetListReasonOfStatusForCM(
										act, pbReason);
								async.execute(spin.getId());
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						Log.d(this.getClass().getSimpleName(),
								"spnStatus onNothingSelected");
					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("TAG 9", "FragmentAsignJobDetail onActivityResult requestCode : "
				+ requestCode);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 101:
				staff = (Staff) data.getExtras().getSerializable(
						"StaffBeans");
				if(staff != null && !CommonActivity.isNullOrEmpty(staff.toString())){
					edtStaff.setText(staff.toString());
				}else{
					edtStaff.setText("");
					edtStaff.setHint(getActivity().getString(R.string.select_employee));
				}
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.edtStaff:
			Log.d(Constant.TAG,
					"FragmentAsignJobDetail onClick R.id.edt_hthm arrStaff.size() "
							+ arrStaff.size());

			if (arrStaff.size() > 0) {
				Intent intent = new Intent(getActivity(),
						SearchStaffActivity.class);
				intent.putExtra("arrStaff", arrStaff);
				startActivityForResult(intent, 101);
			}

			break;

		case R.id.relaBackHome:
			act.onBackPressed();
			break;
		case R.id.btncanel:
			act.onBackPressed();
			break;
		case R.id.btn_Update_Status_CM:
			if (CommonActivity.isNetworkConnected(act)) {

				int pos_spnStatus = spnStatus.getSelectedItemPosition();
				Spin spinStatus = (Spin) spnStatus.getSelectedItem();
				status = (spinStatus == null) ? "" : spinStatus.getId();

				note = edtNote.getText().toString().trim();

				if (CommonActivity.isNullOrEmpty(note)) {
					Toast.makeText(act, getString(R.string.note_require_blank),
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (StringUtils.CheckCharSpecical(note)) {
					Toast.makeText(act, getString(R.string.checkcharspecical),
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (pos_spnStatus == 0) {
					CommonActivity.createAlertDialog(act,
							getString(R.string.please_input_status),
							getString(R.string.app_name)).show();
					return;
				}

				int pos_spnReason = spnReason.getSelectedItemPosition();
				Spin spinReason = (Spin) spnReason.getSelectedItem();
				reasonContent = (spinReason == null) ? "" : spinReason
						.getValue();

				if (pos_spnReason == 0
						&& !"22".equalsIgnoreCase(spinStatus.getId())) {
					CommonActivity.createAlertDialog(act,
							getString(R.string.message_not_sellect_reasoon),
							getString(R.string.app_name)).show();
					return;
				}
				
				if(staff == null){
					CommonActivity.createAlertDialog(act,
							getString(R.string.checkStaff),
							getString(R.string.app_name)).show();
					return;
				}
				
				if(CommonActivity.isNullOrEmpty(staff.getStaffCode())){
					CommonActivity.createAlertDialog(act,
							getString(R.string.checkStaff),
							getString(R.string.app_name)).show();
					return;
				}
				

				CommonActivity.createDialog(act,
						getString(R.string.confirmgiaoviec),
						getString(R.string.app_name),
						getString(R.string.say_ko), getString(R.string.say_co),
						null, confirmChargeAct).show();
			} else {
				CommonActivity.createAlertDialog(act,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}
	}

	// confirm charge
	private OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(act)) {
				Log.d(Constant.TAG,
						"FragmentUpdateHotLineDetail onClick receiveRequestBean "
								+ receiveRequestBean.getReciveRequestId()
								+ " status: " + status + " reasonContent: "
								+ reasonContent + " note :" + note);

				// (String receiveRequestId, String status, String
				// reasonContent, String description)
				AsynUpdateStatusRequestForCM async = new AsynUpdateStatusRequestForCM(
						act);
				async.execute(receiveRequestBean.getReciveRequestId(), status,
						reasonContent, note);
				// async.execute("245", "28", "qua tu", "he he");
			} else {
				CommonActivity.createAlertDialog(act,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	// move login
	private OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(act, LoginActivity.class);
			startActivity(intent);
			act.finish();
			MainActivity.getInstance().finish();
			if (act != null) {
				act.finish();
			}
		}
	};

	// ws GetListStatusForCM
	private class AsyncGetListStatusForCM extends
			AsyncTask<String, Void, List<Spin>> {

		private ProgressBar _pbStatus;
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";

		public AsyncGetListStatusForCM(Context context, ProgressBar pbStatus) {
			this.context = context;
			this._pbStatus = pbStatus;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (_pbStatus != null) {
				_pbStatus.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected List<Spin> doInBackground(String... args) {
			return getListStatusForCM();
		}

		@Override
		protected void onPostExecute(List<Spin> result) {
			if (_pbStatus != null) {
				_pbStatus.setVisibility(View.GONE);
			}
			if ("0".equals(errorCode)) {
				if (result != null && !result.isEmpty()) {
					lstStatus = result;
					Spin spin = new Spin("", getString(R.string.txt_select));
					lstStatus.add(0, spin);
					Utils.setDataSpinner(act, lstStatus, spnStatus);
					
					for (Spin item : lstStatus) {
						if("52".equals(item.getId())){
							spnStatus.setSelection(lstStatus.indexOf(item));
							spnStatus.setEnabled(false);
							break;
						}
					}
					
					
				} else {
					CommonActivity.createAlertDialog(act,
							getString(R.string.no_data),
							getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(act, description, context
									.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(act,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private List<Spin> getListStatusForCM() {
			String original = "";
			List<Spin> listSpin = new ArrayList<Spin>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStatusForCM");
				StringBuilder rawData = new StringBuilder();

				rawData.append("<ws:getListStatusForCM>");
				rawData.append("<hotLineInput>");
				rawData.append("<token>" + Session.getToken());
				rawData.append("</token>");
				rawData.append("</hotLineInput>");
				rawData.append("</ws:getListStatusForCM>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, act, "mbccs_getListStatusForCM");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("listAppParams");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						String code = parse.getValue(e1, "code");
						String name = parse.getValue(e1, "name");
						Spin spin = new Spin(code, name);

						listSpin.add(spin);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listSpin;
		}
	}

	// ws getListReasonOfStatusForCM
	private class AsyncGetListReasonOfStatusForCM extends
			AsyncTask<String, Void, List<Spin>> {

		private ProgressBar _pbReason;
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";

		public AsyncGetListReasonOfStatusForCM(Context context,
				ProgressBar pbReason) {
			this.context = context;
			this._pbReason = pbStatus;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (_pbReason != null) {
				_pbReason.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected List<Spin> doInBackground(String... args) {
			return getListReasonOfStatusForCM(args[0]);
		}

		@Override
		protected void onPostExecute(List<Spin> result) {
			if (_pbReason != null) {
				_pbReason.setVisibility(View.GONE);
			}
			if ("0".equals(errorCode)) {
				if (result != null && !result.isEmpty()) {
					lstReason = result;
					Spin spin = new Spin("", getString(R.string.txt_select));
					lstReason.add(0, spin);
					Utils.setDataSpinner(act, lstReason, spnReason);
				} else {
					CommonActivity.createAlertDialog(act,
							getString(R.string.no_data),
							getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(act, description, context
									.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(act,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private List<Spin> getListReasonOfStatusForCM(String statusCode) {
			String original = "";
			List<Spin> listSpin = new ArrayList<Spin>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListReasonOfStatusForCM");
				StringBuilder rawData = new StringBuilder();

				rawData.append("<ws:getListReasonOfStatusForCM>");
				rawData.append("<hotLineInput>");
				rawData.append("<token>" + Session.getToken());
				rawData.append("</token>");
				rawData.append("<statusCode>" + statusCode);
				rawData.append("</statusCode>");
				rawData.append("</hotLineInput>");
				rawData.append("</ws:getListReasonOfStatusForCM>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, act,
						"mbccs_getListReasonOfStatusForCM");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("listAppParams");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						String code = parse.getValue(e1, "code");
						Log.d("code", code);
						String name = parse.getValue(e1, "name");
						Spin spin = new Spin(code, name);

						listSpin.add(spin);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listSpin;
		}
	}

	// ws get list request
	private class AsynUpdateStatusRequestForCM extends
			AsyncTask<String, Void, Void> {

		private ProgressDialog progress;
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";

		public AsynUpdateStatusRequestForCM(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.processing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(String... args) {
			return updateStatusRequestForCM(args[0], args[1], args[2], args[3]);
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				CommonActivity.createAlertDialog(act,
						getString(R.string.updatesucess),
						getString(R.string.app_name)).show();
				act.onBackPressed();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(act, description, context
									.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();

				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(act,
							description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private Void updateStatusRequestForCM(String receiveRequestId,
				String statusCode, String reasonContent, String descript) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_updateStatusRequestForCM");
				StringBuilder rawData = new StringBuilder();

				rawData.append("<ws:updateStatusRequestForCM>");
				rawData.append("<hotLineInput>");

				rawData.append("<token>" + Session.getToken());
				rawData.append("</token>");

				rawData.append("<receiveRequestId>" + receiveRequestId);
				rawData.append("</receiveRequestId>");
				rawData.append("<statusCode>" + statusCode);
				rawData.append("</statusCode>");
				rawData.append("<reasonContent>" + reasonContent);
				rawData.append("</reasonContent>");
				rawData.append("<description>" + descript);
				rawData.append("</description>");

				if(staff != null && !CommonActivity.isNullOrEmpty(staff.getStaffCode())){
					rawData.append("<staffCode>" + staff.getStaffCode());
					rawData.append("</staffCode>");
				}
				
				
				rawData.append("</hotLineInput>");
				rawData.append("</ws:updateStatusRequestForCM>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, act,
						"mbccs_updateStatusRequestForCM");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private class GetListStaffAsyn extends
			AsyncTask<Void, Void, ArrayList<Staff>> {

		private ProgressDialog progress;
		private Context context = null;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";

		public GetListStaffAsyn(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<Staff> doInBackground(Void... arg0) {
			return getListStaff();
		}

		@Override
		protected void onPostExecute(ArrayList<Staff> result) {
			progress.dismiss();

			arrStaff = new ArrayList<Staff>();

			if (errorCode.equals("0")) {

				if (result.size() > 0) {
					arrStaff = result;
					edtStaff.setHint(getActivity().getString(
							R.string.select_employee));
				} else {
					edtStaff.setHint(getActivity().getString(
							R.string.select_employee));
					arrStaff = new ArrayList<Staff>();
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(R.string.notemployee1),
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Staff> getListStaff() {
			String original = "";
			ArrayList<Staff> lstStaff = new ArrayList<Staff>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getStaffListFromShopId");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getStaffListFromShopId>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken());
				rawData.append("</token>");
				rawData.append("</input>");
				rawData.append("</ws:getStaffListFromShopId>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, act,
						"mbccs_getStaffListFromShopId");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nodechild = null;
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodechild = doc.getElementsByTagName("lstStaff");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Staff staff = new Staff();
						String name = parse.getValue(e1, "name");
						staff.setName(name);
						String staffCode = parse.getValue(e1, "staffCode");
						staff.setStaffCode(staffCode);
						String staffId = parse.getValue(e1, "staffId");
						if (!CommonActivity.isNullOrEmpty(staffId)) {
							staff.setStaffId(Long.parseLong(staffId));
						}
						String shopId = parse.getValue(e1, "shopId");
						if (!CommonActivity.isNullOrEmpty(shopId)) {
							staff.setShopId(Long.parseLong(shopId));
						}
						lstStaff.add(staff);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lstStaff;
		}

	}

    @Override
    protected void setPermission() {

    }
}
