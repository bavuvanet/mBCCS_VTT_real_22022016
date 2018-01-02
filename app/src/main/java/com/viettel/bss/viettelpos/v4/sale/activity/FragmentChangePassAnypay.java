package com.viettel.bss.viettelpos.v4.sale.activity;

import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
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

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FragmentChangePassAnypay extends Fragment implements
		OnClickListener {

	private EditText edtOldPassword, edtNewPassword;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.layout_change_pass_anypay,
				container, false);
		unitView(mView);
		return mView;
	}

	private void unitView(View mView) {
		edtOldPassword = (EditText) mView.findViewById(R.id.edtOldPassword);
		edtNewPassword = (EditText) mView.findViewById(R.id.edtNewPassword);
        Button btnchangepass = (Button) mView.findViewById(R.id.btnchangepass);
		btnchangepass.setOnClickListener(this);
        Button btncanel = (Button) mView.findViewById(R.id.btncanel);
		btncanel.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		addActionBarTitle();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnchangepass:

			if (CommonActivity.isNullOrEmpty(edtOldPassword.getText()
					.toString())) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.passwordOldRequired),
						getActivity().getString(R.string.app_name)).show();
				return;
			}
			if (CommonActivity.isNullOrEmpty(edtNewPassword.getText()
					.toString())) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.passwordNewRequired),
						getActivity().getString(R.string.app_name)).show();
				return;
			}

			if (edtOldPassword.getText().toString().trim()
					.equals(edtNewPassword.getText().toString().trim())) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.password_not_same),
						getActivity().getString(R.string.app_name)).show();
				return;
			}
			if (!CommonActivity.isNetworkConnected(getActivity())) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.errorNetwork),
						getActivity().getString(R.string.app_name)).show();
				return;
			}

			OnClickListener changePassClick = new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					ChangePasswordAsyn changePasswordAsyn = new ChangePasswordAsyn(
							getActivity());
					changePasswordAsyn.execute(edtOldPassword.getText()
							.toString().trim(), edtNewPassword.getText()
							.toString().trim());
				}
			};

			CommonActivity.createDialog(getActivity(),
					getActivity().getString(R.string.confirmchangepass),
					getActivity().getString(R.string.app_name),
					getActivity().getString(R.string.OK),
					getActivity().getString(R.string.cancel), changePassClick,
					null).show();

			break;
		case R.id.btncanel:

			getActivity().onBackPressed();

			break;

		case R.id.relaBackHome:

			getActivity().onBackPressed();
			break;
		default:
			break;
		}

	}

	private void addActionBarTitle() {
        MainActivity.getInstance().setTitleActionBar(R.string.change_pass_anypay);
	}

	private class ChangePasswordAsyn extends AsyncTask<String, Void, String> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public ChangePasswordAsyn(Activity context) {
			this.progress = new ProgressDialog(context);
			// check font
			this.mActivity = context;
			this.progress.setMessage(context.getResources().getString(
					R.string.processing));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected String doInBackground(String... arg0) {
			return changePass(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.cp_success),
						getActivity().getString(R.string.app_name)).show();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									getActivity().getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getActivity()
								.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getActivity()
									.getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private String changePass(String oldPass, String newPass) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_changePassword");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:changePassword>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<password>").append(oldPass);
				rawData.append("</password>");
				rawData.append("<newPassword>").append(newPass);
				rawData.append("</newPassword>");

				rawData.append("</input>");
				rawData.append("</ws:changePassword>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_changePassword");
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
					Log.d("errorCode", errorCode);

				}
			} catch (Exception e) {
				Log.d("mbccs_changePassword",
						e.toString() + "description error", e);
			}

			return errorCode;

		}
	}

	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();
			// MainActivity.getInstance().finish();

			LoginDialog dialog = new LoginDialog(getActivity(),
					";change.pass.anypay;");
			dialog.show();

		}
	};
}
