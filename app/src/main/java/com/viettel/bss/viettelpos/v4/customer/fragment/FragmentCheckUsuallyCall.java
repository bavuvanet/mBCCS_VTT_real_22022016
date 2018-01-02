package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.cc.adapter.DetailIsdnCalledAdapter;
import com.viettel.bss.viettelpos.v4.cc.adapter.DetailIsdnCalledAdapter.OnEditIsdn;
import com.viettel.bss.viettelpos.v4.cc.adapter.DetailIsdnCalledAdapter.OnRemoveIsdn;
import com.viettel.bss.viettelpos.v4.cc.dialog.DialogAddIsdn;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.cc.object.DetailCalledDTO;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentCheckUsuallyCall extends FragmentCommon {
	private Activity activity;
	private TextView tvFromDate;
	private TextView tvToDate;
	private EditText edtIsdn;
	private final List<DetailCalledDTO> lstIsdn = new ArrayList<>();
	private DialogAddIsdn dialog;
	private ListView lvIsdn;
	private DetailIsdnCalledAdapter adapter;

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.check_usually_call);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_check_usually_call;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public void unit(View v) {

		lvIsdn = (ListView) v.findViewById(R.id.lvSub);
		edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
		// edtIsdn.setEnabled(true);
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey("isdnKey")) {
				// edtIsdn.setEnabled(false);
				edtIsdn.setText(bundle.getString("isdnKey"));
			}
		}
		tvFromDate = (TextView) v.findViewById(R.id.tvFromDate);
		tvToDate = (TextView) v.findViewById(R.id.tvToDate);
		tvFromDate.setOnClickListener(this);

		tvToDate.setText(DateTimeUtils.convertDateTimeToString(new Date(),
				"dd/MM/yyyy"));
		Date fromDate = DateTimeUtils.addMonths(new Date(), -1);
		fromDate = DateTimeUtils.addDays(fromDate, 1);

		tvFromDate.setText(DateTimeUtils.convertDateTimeToString(fromDate,
				"dd/MM/yyyy"));

		tvFromDate.setOnClickListener(this);
		tvToDate.setOnClickListener(this);
		v.findViewById(R.id.btnNextDate).setOnClickListener(this);
		v.findViewById(R.id.btnPreDate).setOnClickListener(this);
		v.findViewById(R.id.btnAddIsdn).setOnClickListener(this);
		v.findViewById(R.id.btnCheckSub).setOnClickListener(this);

		// DetailCalledDTO x = new DetailCalledDTO();
		// x.setIsdnCalled("965747879");
		// lstIsdn.add(x);
		for (int i = 0; i < 5; i++) {
			DetailCalledDTO x = new DetailCalledDTO();
			lstIsdn.add(x);
		}
		adapter = new DetailIsdnCalledAdapter(activity, lstIsdn, onRemoveIsdn,
				onEditIsdn);
		lvIsdn.setAdapter(adapter);
		// lvIsdn.setVisibility(View.GONE);

		edtIsdn.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				clearOldSearch();

			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tvFromDate:
			CommonActivity.showDatePickerDialog(activity, DateTimeUtils
					.convertStringToTime(tvFromDate.getText().toString(),
							"dd/MM/yyyy"), fromDateCallBack);
			break;
		case R.id.tvToDate:
			CommonActivity.showDatePickerDialog(activity, DateTimeUtils
					.convertStringToTime(tvToDate.getText().toString(),
							"dd/MM/yyyy"), toDateCallBack);
			break;
		case R.id.btnNextDate:
			Date fromDate = DateTimeUtils.convertStringToTime(tvFromDate
					.getText().toString(), "dd/MM/yyyy");
			Date toDate = DateTimeUtils.convertStringToTime(tvToDate.getText()
					.toString(), "dd/MM/yyyy");
			int days = DateTimeUtils.calculateDays2Date(toDate, fromDate);
			if (toDate.before(fromDate)) {
				return;
			}
			toDate = DateTimeUtils.addDays(toDate, days);
			if (toDate.after(new Date())) {
				toDate = new Date();
			}
			fromDate = DateTimeUtils.addDays(toDate, -days);
			tvFromDate.setText(DateTimeUtils.convertDateTimeToString(fromDate,
					"dd/MM/yyyy"));
			tvToDate.setText(DateTimeUtils.convertDateTimeToString(toDate,
					"dd/MM/yyyy"));
			clearOldSearch();
			break;
		case R.id.btnPreDate:
			fromDate = DateTimeUtils.convertStringToTime(tvFromDate.getText()
					.toString(), "dd/MM/yyyy");
			toDate = DateTimeUtils.convertStringToTime(tvToDate.getText()
					.toString(), "dd/MM/yyyy");
			days = DateTimeUtils.calculateDays2Date(toDate, fromDate);
			if (toDate.before(fromDate)) {
				return;
			}

			fromDate = DateTimeUtils.addDays(fromDate, -days);
			toDate = DateTimeUtils.addDays(toDate, -days);
			tvFromDate.setText(DateTimeUtils.convertDateTimeToString(fromDate,
					"dd/MM/yyyy"));
			tvToDate.setText(DateTimeUtils.convertDateTimeToString(toDate,
					"dd/MM/yyyy"));
			clearOldSearch();
			break;
		case R.id.btnCheckSub:
			String v = validateSearch();
			if (!CommonActivity.isNullOrEmpty(v)) {
				CommonActivity.createAlertDialog(activity, v,
						getString(R.string.app_name)).show();
				return;
			}
			CheckUsuallyCalledByIsdnAsy asy = new CheckUsuallyCalledByIsdnAsy(
					activity);
			asy.execute();
			break;
		case R.id.btnAddIsdn:
			for (int i = 0; i < lstIsdn.size(); i++) {
				if (CommonActivity
						.isNullOrEmpty(lstIsdn.get(i).getIsdnCalled())) {
					CommonActivity.createAlertDialog(activity,
							getString(R.string.chua_nhap_het_thue_bao),
							getString(R.string.app_name)).show();
					return;
				}
			}
			if (lstIsdn.size() >= 10) {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.over_isdn, 10),
						getString(R.string.app_name)).show();
				return;
			}

			DetailCalledDTO dto = new DetailCalledDTO();
			lstIsdn.add(dto);

			adapter = new DetailIsdnCalledAdapter(activity, lstIsdn,
					onRemoveIsdn, onEditIsdn);
			lvIsdn.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			// showDialogAddIsdn(null);
			break;

		default:
			break;
		}
		super.onClick(arg0);
	}

	private final OnDateSetListener fromDateCallBack = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if (!view.isShown()) {
				return;
			}
			Calendar cal = Calendar.getInstance();
			cal.set(year, monthOfYear, dayOfMonth);
			Date fromDate = cal.getTime();
			Date toDate = DateTimeUtils.convertStringToTime(tvToDate.getText()
					.toString(), "dd/MM/yyyy");
			if (fromDate.after(toDate)) {
				CommonActivity
						.createAlertDialog(activity,
								R.string.report_warn_todate_fromdate,
								R.string.app_name).show();
				return;
				// cal.setTime(toDate);
			}

			tvFromDate.setText(DateTimeUtils.convertDateTimeToString(
					cal.getTime(), "dd/MM/yyyy"));
			clearOldSearch();
		}
	};
	private final OnDateSetListener toDateCallBack = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if (!view.isShown()) {
				return;
			}
			Calendar cal = Calendar.getInstance();
			cal.set(year, monthOfYear, dayOfMonth);
			Date toDate = cal.getTime();
			Date fromDate = DateTimeUtils.convertStringToTime(tvFromDate
					.getText().toString(), "dd/MM/yyyy");
			if (fromDate.after(toDate)) {
				CommonActivity
						.createAlertDialog(activity,
								R.string.report_warn_todate_fromdate,
								R.string.app_name).show();
				return;
			}

			if (toDate.after(new Date())) {
				cal = Calendar.getInstance();

			}
			tvToDate.setText(DateTimeUtils.convertDateTimeToString(
					cal.getTime(), "dd/MM/yyyy"));
			clearOldSearch();
		}
	};

	private void showDialogAddIsdn(DetailCalledDTO item) {
		if (dialog == null) {
			dialog = new DialogAddIsdn(activity, lstIsdn, lvIsdn, adapter);

		} else {
			if (item != null) {
				if (CommonActivity.validateIsdn(item.getIsdnCalled())) {
					dialog.setIsdnText(item);
				} else {
					dialog.setIsdnText(null);
				}
			} else {
				dialog.setIsdnText(null);
			}

		}
		dialog.getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		dialog.show();
	}

	private final OnEditIsdn onEditIsdn = new OnEditIsdn() {

		@Override
		public void onEditIsdn(DetailCalledDTO item) {
			if (!CommonActivity.validateIsdn(item.getIsdnCalled())) {
				showDialogAddIsdn(item);
			}

		}
	};

	private final OnRemoveIsdn onRemoveIsdn = new OnRemoveIsdn() {

		@Override
		public void onRemoveIsdn(DetailCalledDTO item) {
			for (int i = 0; i < lstIsdn.size(); i++) {
				DetailCalledDTO tmp = lstIsdn.get(i);
				if (tmp.getIsdnCalled().equals(item.getIsdnCalled())) {
					if (lstIsdn.size() <= 5) {
						lstIsdn.get(i).setIsdnCalled("");
					} else {
						lstIsdn.remove(i);
					}

					break;
				}
			}
			// if (CommonActivity.isNullOrEmpty(lstIsdn)) {
			// lvIsdn.setVisibility(View.GONE);
			// mView.findViewById(R.id.lnHint).setVisibility(View.VISIBLE);
			// }
			adapter.notifyDataSetChanged();
		}
	};

	private class CheckUsuallyCalledByIsdnAsy extends
			AsyncTask<String, Void, CCOutput> {

		private final Context context;
		final ProgressDialog progress;

		public CheckUsuallyCalledByIsdnAsy(Context context) {

			this.context = context;
			this.progress = new ProgressDialog(context);
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected CCOutput doInBackground(String... params) {

			// if (rbNoiMang.isChecked()) {
			return checkUsuallyCalledByIsdn();
			// } else {
			// return getListReasoncareNgoaiMang();
			// }
		}

		@Override
		protected void onPostExecute(CCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (result.getErrorCode().equals("0")) {
				if (!CommonActivity.isNullOrEmpty(result.getListDetailCalled())) {
					for (DetailCalledDTO item : lstIsdn) {
						item.setCheck(true);
						for (DetailCalledDTO tmp : result.getListDetailCalled()) {
							if (tmp.getIsdnCalled()
									.equals(item.getIsdnCalled())) {
								item.copyFrom(tmp);
							}
						}
					}
				}else{
					for (DetailCalledDTO item : lstIsdn) {
						item.setCheck(true);
					}
				}
				adapter = new DetailIsdnCalledAdapter(activity, lstIsdn,
						onRemoveIsdn, onEditIsdn);
				lvIsdn.setAdapter(adapter);
			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin((Activity) context,
							result.getDescription(), ";check_usually_call;");
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(context,
							result.getDescription(),
							context.getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private CCOutput checkUsuallyCalledByIsdn() {

			String original = "";
			CCOutput ccOutput;
			String methodName = "checkUsuallyCalledByIsdn";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<inputCheckSubCallByIsdn>");
				String lstData = "";
				for (DetailCalledDTO item : lstIsdn) {
					if (!CommonActivity.isNullOrEmpty(item.getIsdnCalled())) {
						item.setIsdnCalled(CommonActivity
								.checkStardardIsdn(item.getIsdnCalled()));
					}
					lstData = lstData + item.getIsdnCalled() + ";";
				}
				if (lstData.length() > 1) {
					lstData = lstData.substring(0, lstData.length() - 1);
				}
				rawData.append("<listIsdn>").append(lstData).append("</listIsdn>");
				rawData.append("<isdn>").append(CommonActivity.checkStardardIsdn(edtIsdn.getText()
                        .toString().trim())).append("</isdn>");
				Date fromDate = DateTimeUtils.convertStringToTime(tvFromDate
						.getText().toString(), "dd/MM/yyyy");
				rawData.append("<fromDate>").append(DateTimeUtils.convertDateTimeToString(fromDate,
                        "yyyy/MM/dd")).append(" 00:00:00").append("</fromDate>");
				Date toDate = DateTimeUtils.convertStringToTime(tvToDate
						.getText().toString(), "dd/MM/yyyy");
				rawData.append("<toDate>").append(DateTimeUtils.convertDateTimeToString(toDate,
                        "yyyy/MM/dd")).append(" 23:59:59").append("</toDate>");
				rawData.append("<user>").append(Session.userName).append("</user>");
				rawData.append("</inputCheckSubCallByIsdn>");
				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), "mbccs_"
								+ methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Serializer serializer = new Persister();
				ccOutput = serializer.read(CCOutput.class, original);
				if (ccOutput == null) {
					ccOutput = new CCOutput();
					ccOutput.setDescription(getString(R.string.no_return_from_system));
					ccOutput.setErrorCode(Constant.ERROR_CODE);
					return ccOutput;
				} else {
					return ccOutput;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, methodName, e);
				ccOutput = new CCOutput();
				ccOutput.setDescription(e.getMessage());
				ccOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return ccOutput;
		}
	}

	private String validateSearch() {
		if (CommonActivity.isNullOrEmpty(edtIsdn.getText().toString().trim())) {
			return getString(R.string.must_input_isdn);
		}
		if (!CommonActivity.validateIsdn(edtIsdn.getText().toString().trim())) {
			return getString(R.string.phone_number_invalid_format);
		}

		Date fromDate = DateTimeUtils.convertStringToTime(tvFromDate.getText()
				.toString(), "dd/MM/yyyy");
		Date toDate = DateTimeUtils.convertStringToTime(tvToDate.getText()
				.toString(), "dd/MM/yyyy");

		if (DateTimeUtils.calculateDays2Date(fromDate, toDate) > 30) {
            int DURATION_MAX = 30;
            CommonActivity.createAlertDialog(activity,
					getString(R.string.duration_over_load, DURATION_MAX),
					getString(R.string.app_name)).show();
			return getString(R.string.duration_over_load, DURATION_MAX);
		}

		boolean isValid = false;
		for (DetailCalledDTO item : lstIsdn) {
			if (!CommonActivity.isNullOrEmpty(item.getIsdnCalled())) {
				isValid = true;
				break;
			}
		}
		if (!isValid) {
			return getString(R.string.must_input_atleast_one_isdn);
		}

		for (DetailCalledDTO item : lstIsdn) {
			if (!CommonActivity.isNullOrEmpty(item.getIsdnCalled())
					&& !CommonActivity.validateIsdn(item.getIsdnCalled())) {
				return getString(R.string.so_dien_thoai_sai_dinh_dang,
						item.getIsdnCalled());
			}
		}

		// boolean allCheck = true;
		// for (DetailCalledDTO item : lstIsdn) {
		// if (!item.isCheck()) {
		// allCheck = false;
		// break;
		// }
		// }
		// if (allCheck) {
		// return getString(R.string.must_input_atleast_one_isdn);
		// }
		return "";
	}

	private void clearOldSearch() {
		for (DetailCalledDTO item : lstIsdn) {
			item.setBgDate(null);
			item.setLsDate(null);
			item.setNumCallGt6(0);
			item.setCheck(false);
			item.setNumCallLt6(0);
			item.setNumSms(0);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void setPermission() {
		permission = ";check_usually_call;";

	}
}