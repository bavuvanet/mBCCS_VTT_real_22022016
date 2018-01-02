package com.viettel.bss.viettelpos.v4.sale.activity;

import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.SecurityUtil;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentAnypayISDN extends Fragment implements OnClickListener {

	private View mView;
	private Activity activity;
	private EditText edt_isdn;
	private Spinner spn_subType;
	private EditText edt_amount;
	private EditText edt_password;
	private Button btn_anypay;
	private LinearLayout lnPassword;

	private Button btnHome;
	private TextView txtNameActionBar;

	private String isdn;
	private String subType;
	private String amount;
	private String password;

	private boolean staffMutipleService = false;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_sale_anypay_isdn,
					container, false);
			edt_isdn = (EditText) mView.findViewById(R.id.edt_isdn);
			spn_subType = (Spinner) mView.findViewById(R.id.spn_subType);
			edt_amount = (EditText) mView.findViewById(R.id.edt_amount);
			edt_password = (EditText) mView.findViewById(R.id.edt_password);
			btn_anypay = (Button) mView.findViewById(R.id.btn_anypay);

			lnPassword = (LinearLayout) mView.findViewById(R.id.lnPassword);
		}

		btn_anypay.setOnClickListener(this);

		List<Spin> lstSpin = new ArrayList<>();
		Spin spinDefault = new Spin("0", getString(R.string.txt_select));
		Spin spinPre = new Spin("0", getString(R.string.subfirst));
		Spin spinPos = new Spin("1", getString(R.string.sublast));
		lstSpin.add(spinDefault);
		lstSpin.add(spinPre);
		lstSpin.add(spinPos);

		Utils.setDataSpinner(activity, lstSpin, spn_subType);

		Session.loginUser = StaffBusiness.getStaffByStaffCode(activity,
				Session.userName);

		if (Session.loginUser.getChannelTypeId() == 14
				&& (Session.loginUser.getType() == 8 || Session.loginUser
						.getType() == 9)) {
			staffMutipleService = true;
		}
		// staffMutipleService = true;
		if (staffMutipleService) {
			spn_subType.setSelection(1);
			spn_subType.setEnabled(false);
			lnPassword.setVisibility(View.GONE);
			// edt_password.setText("123456");
		} else {
			spn_subType.setEnabled(true);
			lnPassword.setVisibility(View.VISIBLE);
			edt_password.setText("");
		}

		edt_amount.addTextChangedListener(new TextWatcher() {
			boolean ignoreChange = false;

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() != 0) {
					String money = StringUtils.formatMoney(s.toString());
					Log.d(this.getClass().getSimpleName(),
							"afterTextChanged money " + money);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!ignoreChange) {
					String money = StringUtils.unFormatMoney(String.valueOf(s));
					money = StringUtils.formatMoney(money);
					ignoreChange = true;
					edt_amount.setText(money);
					edt_amount.setSelection(edt_amount.getText().length());
					ignoreChange = false;
				}
			}
		});

		return mView;
	}

	private void resetValue() {
		edt_isdn.setText("");
		if (!staffMutipleService) {
			spn_subType.setSelection(0);
		}
		edt_amount.setText("");
		edt_password.setText("");
	}

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.sale_anypay_isdn);
		super.onResume();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_anypay:
			onAnypayIsdn();
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
					";sale_anypay_isdn;");
			dialog.show();

		}
	};

	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				if (staffMutipleService) {
					AsynReloadPreByStaff asyn = new AsynReloadPreByStaff(
							activity);
					asyn.execute(isdn, subType, amount, password);
				} else {
					AsyntaskAnypay asyn = new AsyntaskAnypay(activity);
					asyn.execute(isdn, subType, amount, password);
				}
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

	private void onAnypayIsdn() {
		CommonActivity.hideKeyboard(btn_anypay, activity);
		try {
			isdn = edt_isdn.getText().toString().trim();
			Spin spin = (Spin) spn_subType.getSelectedItem();
			subType = spin.getId();
			amount = edt_amount.getText().toString().trim();
			amount = StringUtils.unFormatMoney(amount);

			password = edt_password.getText().toString().trim();

			if (CommonActivity.isNullOrEmpty(isdn)) {
				CommonActivity.toast(activity,
						R.string.message_pleass_input_phone_number);
			} else if (isdn.length() < 9) {
				CommonActivity.toast(activity, R.string.check_isdn_invalid);
			} else if (StringUtils.CheckCharSpecical(isdn)) {
				CommonActivity.toast(activity, R.string.checkaccountspecial);
			} else if (spn_subType.getSelectedItemPosition() == 0) {
				CommonActivity.toast(activity, R.string.checkloaithuebao);
			} else if (CommonActivity.isNullOrEmpty(amount)) {
				CommonActivity.toast(activity, R.string.notsotien);
			} else if (CommonActivity.isNullOrEmpty(password)
					&& !staffMutipleService) {
				CommonActivity.toast(activity, R.string.passwordRequired);
			} else {
				String message = String.format(
						getString(R.string.message_confirm_anypay_param),
						StringUtils.formatMoney(amount));
				CommonActivity.createDialog(activity, message,
						getString(R.string.app_name),
						getString(R.string.say_ko), getString(R.string.say_co),
						null, confirmChargeAct).show();
			}
		} catch (Exception e) {
			Log.d(Constant.TAG, "FragmentSearchISDN onSearchIsdn Exception ", e);
		}
	}

	private class AsyntaskAnypay extends AsyncTask<String, String, String> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		public AsyntaskAnypay(Activity mActivity) {
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.anypay_progess));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			return doReloadAnypay(params[0], params[1], params[2], params[3]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();

			if (errorCode.equals("0")) {
				resetValue();

				if (CommonActivity.isNullOrEmpty(description)) {
					description = getString(R.string.anypay_progess_success);
				}
				CommonActivity.createAlertDialog(activity, description,
						getString(R.string.app_name)).show();
			} else {
				Log.d("Log", "description error update" + description);
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
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							activity.getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private String doReloadAnypay(String isdn, String subType,
				String amount, String password) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_doReloadAnypay");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:doReloadAnypay>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("<subType>").append(subType).append("</subType>");
				rawData.append("<amount>").append(amount).append("</amount>");
				rawData.append("<password>").append(new SecurityUtil().encrypt(password)).append("</password>");
				rawData.append("</input>");
				rawData.append("</ws:doReloadAnypay>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_doReloadAnypay");
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
					Log.d("errorCode", errorCode + " description: "
							+ description);
				}
			} catch (Exception e) {
				Log.e("doReloadAnypay", e.toString() + "description error", e);
			}
			return errorCode;
		}
	}

	private class AsynReloadPreByStaff extends
			AsyncTask<String, String, String> {

		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private final ProgressDialog progress;

		public AsynReloadPreByStaff(Activity mActivity) {
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.anypay_progess));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			return reloadPreByStaff(params[0], params[1], params[2], params[3]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();

			if (errorCode.equals("0")) {
				resetValue();

				if (CommonActivity.isNullOrEmpty(description)) {
					description = getString(R.string.anypay_progess_success);
				}
				CommonActivity.createAlertDialog(activity, description,
						getString(R.string.app_name)).show();
			} else {
				Log.d("Log", "description error update" + description);
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
					Dialog dialog = CommonActivity.createAlertDialog(activity,
							description,
							activity.getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private String reloadPreByStaff(String isdn, String subType,
				String amount, String password) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_reloadPreByStaff");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:reloadPreByStaff>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("<subType>").append(subType).append("</subType>");
				rawData.append("<amount>").append(amount).append("</amount>");
				rawData.append("</input>");
				rawData.append("</ws:reloadPreByStaff>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_reloadPreByStaff");
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
					Log.d("errorCode", errorCode + " description: "
							+ description);
				}
			} catch (Exception e) {
				Log.e("reloadPreByStaff", e.toString() + "description error", e);
			}
			return errorCode;
		}
	}

}
