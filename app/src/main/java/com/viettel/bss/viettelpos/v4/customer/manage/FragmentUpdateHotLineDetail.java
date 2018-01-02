package com.viettel.bss.viettelpos.v4.customer.manage;

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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.DateTime;
import com.viettel.bss.viettelpos.v4.ui.DateTimePicker;
import com.viettel.bss.viettelpos.v4.ui.SimpleDateTimePicker;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.adapter.ItemProcessHistoryAdapter;
import com.viettel.bss.viettelpos.v4.work.object.ProcessRequestBO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentUpdateHotLineDetail extends FragmentCommon implements
		OnClickListener, OnItemClickListener {

	private ReceiveRequestBean receiveRequestBean;

    private Spinner spnStatus;
	private Spinner spnReason;
	private EditText edtNote;

	private String status;
	private String reasonContent;
	private String note;

	private ProgressBar pbStatus;
	private ProgressBar pbReason;

	private RelativeLayout rlReason;
	private EditText edithotlineDateAppoint;
	private LinearLayout lnthotlineDateAppoint;


	// thientv7 them list history
	private ArrayList<ProcessRequestBO> arrProcessRequestBOs = new ArrayList<>();
	private ItemProcessHistoryAdapter adapterHis;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.connection_dialog_detail_hotline_update;
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
        setTitleActionBar(R.string.update_status2);
	}

	@Override
	public void unit(View v) {
		edithotlineDateAppoint = (EditText) v
				.findViewById(R.id.edithotlineDateAppoint);
		edithotlineDateAppoint.setText(DateTimeUtils.convertDateTimeToString(
				new Date(), "dd/MM/yyyy HH:mm:ss"));
		edithotlineDateAppoint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePicker();

			}
		});
		lnthotlineDateAppoint = (LinearLayout) v
				.findViewById(R.id.lnthotlineDateAppoint);
		lnthotlineDateAppoint.setVisibility(View.GONE);

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

		AsyncGetListStatusForCM async = new AsyncGetListStatusForCM(act,
				pbStatus);
		async.execute();

		Button btnViewHistory = (Button) v.findViewById(R.id.btn_view_history);
		btnViewHistory.setOnClickListener(this);

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
							if ("22".equalsIgnoreCase(spin.getId()) || "25".equalsIgnoreCase(spin.getId())) {
								rlReason.setVisibility(View.GONE);
							} else {
								rlReason.setVisibility(View.VISIBLE);
								AsyncGetListReasonOfStatusForCM async = new AsyncGetListReasonOfStatusForCM(
										act, pbReason);
								async.execute(spin.getId());
							}
							if ("28".equalsIgnoreCase(spin.getId())) {
								lnthotlineDateAppoint
										.setVisibility(View.VISIBLE);
							} else {
								lnthotlineDateAppoint.setVisibility(View.GONE);
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
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.relaBackHome:
			act.onBackPressed();
			break;
		case R.id.btncanel:
			act.onBackPressed();
			break;

		case R.id.btn_view_history:
			showDialogViewHistory(receiveRequestBean.getReciveRequestId());
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
						&& !"22".equalsIgnoreCase(spinStatus.getId()) && !"25".equalsIgnoreCase(spinStatus.getId())) {
					CommonActivity.createAlertDialog(act,
							getString(R.string.message_not_sellect_reasoon),
							getString(R.string.app_name)).show();
					return;
				}

				CommonActivity.createDialog(act,
						getString(R.string.message_confirm_update),
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
	private final OnClickListener confirmChargeAct = new OnClickListener() {

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
				if ("28".equals(status)) {
					Date nowDate = new Date();
					Date dateSelect = DateTimeUtils.convertStringToTime(
							edithotlineDateAppoint.getText().toString(),
							"dd/MM/yyyy HH:mm:ss");
					if (dateSelect.compareTo(nowDate) < 0) {
						CommonActivity.createAlertDialog(
								getActivity(),
								getActivity().getString(
										R.string.validMsghotlineDateAppoint),
								getActivity().getString(R.string.app_name))
								.show();
						return;
					}
				}
				AsynUpdateStatusRequestForCM async = new AsynUpdateStatusRequestForCM(
						act);
				async.execute(receiveRequestBean.getReciveRequestId(), status,
						reasonContent, note, edithotlineDateAppoint.getText()
								.toString());
				// async.execute("245", "28", "qua tu", "he he");
			} else {
				CommonActivity.createAlertDialog(act,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	// move login
	private final OnClickListener moveLogInAct = new OnClickListener() {

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

		private final ProgressBar _pbStatus;
		private Context context = null;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
        private String choiseString = "";

		public AsyncGetListStatusForCM(Context context, ProgressBar pbStatus) {
			this.context = context;
			this._pbStatus = pbStatus;
            this.choiseString = context.getString(R.string.txt_select);
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
                    Spin spin = new Spin("", choiseString);
					result.add(0, spin);
					Utils.setDataSpinner(act, result, spnStatus);
				} else {
					CommonActivity.createAlertDialog(act,
							getActivity().getString(R.string.no_data),
							getActivity().getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(act, description, getActivity()
									.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getActivity().getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(act,
							description,
							getActivity().getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private List<Spin> getListStatusForCM() {
			String original = "";
			List<Spin> listSpin = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListStatusForCM");
                String rawData = "<ws:getListStatusForCM>" +
                        "<hotLineInput>" +
                        "<token>" + Session.getToken() +
                        "</token>" +
                        "<receiveRequestId>" + receiveRequestBean.getReciveRequestId() +
                        "</receiveRequestId>" +
                        "<isNw>true</isNw>" +
                        "</hotLineInput>" +
                        "</ws:getListStatusForCM>";

                String envelope = input.buildInputGatewayWithRawData(rawData);
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

		private final ProgressBar _pbReason;
		private Context context = null;
		private final XmlDomParse parse = new XmlDomParse();
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
                    Spin spin = new Spin("", getString(R.string.txt_select));
					result.add(0, spin);
					Utils.setDataSpinner(act, result, spnReason);
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
			List<Spin> listSpin = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListReasonOfStatusForCM");
                String rawData = "<ws:getListReasonOfStatusForCM>" +
                        "<hotLineInput>" +
                        "<token>" + Session.getToken() +
                        "</token>" +
                        "<statusCode>" + statusCode +
                        "</statusCode>" +
                        "</hotLineInput>" +
                        "</ws:getListReasonOfStatusForCM>";

                String envelope = input.buildInputGatewayWithRawData(rawData);
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
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";

		public AsynUpdateStatusRequestForCM(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
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
		protected Void doInBackground(String... args) {
			return updateStatusRequestForCM(args[0], args[1], args[2], args[3],
					args[4]);
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
				String statusCode, String reasonContent, String descript,
				String dateExtend) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_updateStatusRequestForCM");
                String rawData = "<ws:updateStatusRequestForCM>" +
                        "<hotLineInput>" +
                        "<token>" + Session.getToken() +
                        "</token>" +
                        "<receiveRequestId>" + receiveRequestId +
                        "</receiveRequestId>" +
                        "<statusCode>" + statusCode +
                        "</statusCode>" +
                        "<reasonContent>" + reasonContent +
                        "</reasonContent>" +
                        "<description>" + descript +
                        "</description>" +
                        "<dateExtend>" + DateTimeUtils.convertDateTimeToString(DateTimeUtils
                                .convertStringToTime(dateExtend,
                                        "dd/MM/yyyy HH:mm:ss"),
                        "yyyy-MM-dd HH:mm:ss") +
                        "</dateExtend>" +
                        "</hotLineInput>" +
                        "</ws:updateStatusRequestForCM>";

                String envelope = input.buildInputGatewayWithRawData(rawData);
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

	private void showDatePicker() {
		SimpleDateTimePicker.make(getString(R.string.txt_dat_lich_hen),
				new Date(), new DateTimePicker.OnDateTimeSetListener() {

					@Override
					public void DateTimeSet(Date date) {
						// TODO Auto-generated method stub
						DateTime mDateTime = new DateTime(date);
						edithotlineDateAppoint.setText(mDateTime
								.getDateString("dd/MM/yyyy HH:mm:ss"));
					}
				}, getFragmentManager()).show();
	}

	@Override
	public void setPermission() {
		permission = ";update_hotline;";
	}

	// thientv7 them xem lich su
	// show dialog history
	ListView lstHistory;
	Dialog dialog;
	private void showDialogViewHistory(String reciveRequestId) {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_dialog_view_history_hotline);
		TextView txttitle = (TextView) dialog.findViewById(R.id.txttitle);

		txttitle.setText(getString(R.string.txt_view_history_connect));
		lstHistory = (ListView) dialog.findViewById(R.id.lst_history);
		GetListRequestHistoryHotlineAsyn getListRequestHistoryHotlineAsyn = new GetListRequestHistoryHotlineAsyn(getActivity());
		getListRequestHistoryHotlineAsyn.execute(reciveRequestId);
		Button btnClose = (Button) dialog.findViewById(R.id.btn_close);
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	// ws get list history
	// ws get list request
	private class GetListRequestHistoryHotlineAsyn extends
			AsyncTask<String, Void, ArrayList<ProcessRequestBO>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListRequestHistoryHotlineAsyn(Context context) {
			this.context = context;
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
		protected ArrayList<ProcessRequestBO> doInBackground(String... arg0) {
			return getListHistoryRequest(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<ProcessRequestBO> result) {
			progress.dismiss();
			if("0".equals(errorCode)){
				if(result != null && !result.isEmpty()){

					arrProcessRequestBOs = result;
					adapterHis = new ItemProcessHistoryAdapter(getActivity(), arrProcessRequestBOs);
					lstHistory.setAdapter(adapterHis);
				}else{
					if(arrProcessRequestBOs != null && !arrProcessRequestBOs.isEmpty()){
						arrProcessRequestBOs = new ArrayList<>();
					}

					adapterHis = new ItemProcessHistoryAdapter(getActivity(), arrProcessRequestBOs);
					lstHistory.setAdapter(adapterHis);
					adapterHis.notifyDataSetChanged();

					CommonActivity.createAlertDialog(getActivity(), getString(R.string.no_data), getString(R.string.app_name), new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					}).show();
				}
			}else{
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if(description == null || description.isEmpty()){
						description = context.getString(R.string.checkdes);
					}
					CommonActivity.createAlertDialog(getActivity(), description, getString(R.string.app_name), new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					}).show();

				}
			}
		}

		private ArrayList<ProcessRequestBO> getListHistoryRequest(String receiveRequestId){
			String original = "";
			arrProcessRequestBOs = new ArrayList<>();
			try{
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getRequestHotlineDetail");
				StringBuilder rawData = new StringBuilder();

				rawData.append("<ws:getRequestHotlineDetail>");
				rawData.append("<hotLineInput>");

				rawData.append("<receiveRequestId>").append(receiveRequestId);
				rawData.append("</receiveRequestId>");

				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");

				rawData.append("</hotLineInput>");
				rawData.append("</ws:getRequestHotlineDetail>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_getRequestHotlineDetail");
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
//					description = parse.getValue(e2, "description");
//					Log.d("description", description);
					nodechild = doc.getElementsByTagName("listProcessRequest");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ProcessRequestBO processRequestBO = new ProcessRequestBO();

						String processDate = parse.getValue(e1, "processDate");
						processRequestBO.setProcessDate(processDate);

						String reciveDate = parse.getValue(e1, "reciveDate");
						processRequestBO.setReciveDate(reciveDate);

						String userCreate = parse.getValue(e1, "userCreate");
						processRequestBO.setUserCreate(userCreate);

						String status = parse.getValue(e1, "status");
						processRequestBO.setStatus(status);

						String contentProcess = parse.getValue(e1, "contentProcess");
						processRequestBO.setContentProcess(contentProcess);
						arrProcessRequestBOs.add(processRequestBO);
					}
					Log.d("arrProcessRequestBOs", "arrProcessRequestBOs size: "+arrProcessRequestBOs.size());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return arrProcessRequestBOs;
		}
	}
}
