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
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FragmentAnypayExchange extends Fragment implements OnClickListener {

	private View mView;
	private Activity activity;
	private EditText edt_isdn;
	private EditText edt_amount;
	private EditText edt_password;
	private Button btn_anypay;

	private Button btnHome;
	private TextView txtNameActionBar;

	private String isdn;
	private String amount;
	private String password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_sale_anypay_exchange,
					container, false);
			edt_isdn = (EditText) mView.findViewById(R.id.edt_isdn);
			edt_amount = (EditText) mView.findViewById(R.id.edt_amount);
			edt_password = (EditText) mView.findViewById(R.id.edt_password);
			btn_anypay = (Button) mView.findViewById(R.id.btn_anypay);
		}

		btn_anypay.setOnClickListener(this);

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
		edt_amount.setText("");
		edt_password.setText("");
	}

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.sale_anypay_exchange);
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
					";sale_anypay_exchange;");
			dialog.show();
		}
	};

	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				AsyntaskAnypay asyn = new AsyntaskAnypay(activity);
				asyn.execute(isdn, amount, password);
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
			amount = edt_amount.getText().toString().trim();
			amount = StringUtils.unFormatMoney(amount);

			password = edt_password.getText().toString().trim();

			if (CommonActivity.isNullOrEmpty(isdn)) {
				CommonActivity.toast(activity,
						R.string.message_pleass_input_phone_number);
			}
			if (StringUtils.CheckCharSpecical(isdn)) {
				CommonActivity.toast(activity, R.string.checkaccountspecial);
			}
			if (isdn.length() < 9 || isdn.length() > 11) {
				CommonActivity.toast(activity, R.string.check_length_isdn);
			} else if (CommonActivity.isNullOrEmpty(amount)) {
				CommonActivity.toast(activity, R.string.notsotien);
			} else if (CommonActivity.isNullOrEmpty(password)) {
				CommonActivity.toast(activity, R.string.passwordRequired);
			} else {
				String message = String
						.format(getString(R.string.message_confirm_anypay_transfer_param),
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
					R.string.exchange_anypay_progess));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			return doTransferMoney(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();

			if (errorCode.equals("0")) {
				resetValue();

				if (CommonActivity.isNullOrEmpty(description)) {
					description = getString(R.string.anypay_exchange_progess_success);
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

		private String doTransferMoney(String isdn, String amount,
				String password) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_doTransferMoney");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:doTransferMoney>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("<amount>").append(amount).append("</amount>");
				rawData.append("<password>").append(new SecurityUtil().encrypt(password)).append("</password>");
				rawData.append("</input>");
				rawData.append("</ws:doTransferMoney>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_doTransferMoney");
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
				Log.e("doTransferMoney", e.toString() + "description error", e);
			}
			return errorCode;
		}
	}

}
