package com.viettel.bss.viettelpos.v4.sale.activity;

import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.AccountAgentObj;
import com.viettel.bss.viettelpos.v4.sale.object.ChannelTypeObject;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FragmentActiveAccountPayment extends Fragment implements
		OnClickListener {

	private static final int REQUEST_CHANGE_CHANNEL = 1;
	private View mView;
	private Activity activity;

	private RadioGroup radioGroup;
	private RadioButton radioHasSim;
	private RadioButton radioNotSim;

	private TextView tvChooseChannel;
	private TextView tvCodeIsdn;
	private TextView tvHelpCheck;
	private TextView tvHelpActive;
	// private TextView tvHelpCheckChanel;

	private EditText edtCodeSerialSim;
	private Button btnCheckChanel;
	private Button btnActive;
	private Staff selectedStaff;
    private AccountAgentObj accountAgent;
    private boolean checkSelectChanel;

	private Button btnHome;
	private TextView txtNameActionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		activity = getActivity();
        Bundle mBundle = getArguments();
		if (mBundle != null) {
			checkSelectChanel = true;
			selectedStaff = (Staff) mBundle.getSerializable("staffKey");

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_active_account_payment,
					container, false);
			tvChooseChannel = (TextView) mView
					.findViewById(R.id.txtChanelDistribution);
			btnCheckChanel = (Button) mView.findViewById(R.id.btnCheck);
			tvCodeIsdn = (TextView) mView.findViewById(R.id.tvIsdn);
			edtCodeSerialSim = (EditText) mView.findViewById(R.id.edtSerialSim);
			btnActive = (Button) mView.findViewById(R.id.btnActive);
			radioGroup = (RadioGroup) mView.findViewById(R.id.radioGroup);
			radioHasSim = (RadioButton) mView.findViewById(R.id.radioHaveSim);
			radioNotSim = (RadioButton) mView.findViewById(R.id.radioNotSim);
			tvHelpActive = (TextView) mView
					.findViewById(R.id.tvHelpActiveOrSwitch);

			tvChooseChannel.setOnClickListener(this);
			btnCheckChanel.setOnClickListener(this);
			btnActive.setOnClickListener(this);
		}

		if (selectedStaff != null) {

			tvChooseChannel.setHint(selectedStaff.getName() + " - "
					+ selectedStaff.getStaffCode());
			// tvChooseChannel.setText(selectedStaff.getName() + " - " +
			// selectedStaff.getStaffCode());
		}

		return mView;

	}

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.active_account_agent);
		if (selectedStaff != null && checkSelectChanel) {

			refreshData();
			tvChooseChannel.setHint(selectedStaff.getName() + " - "
					+ selectedStaff.getStaffCode());
			// tvChooseChannel.setText(selectedStaff.getName() + " - " +
			// selectedStaff.getStaffCode());
		} else {
			tvChooseChannel.setHint(getResources().getString(
					R.string.chanel_distribution));
			// tvChooseChannel.setText(getResources().getString(R.string.chanel_distribution));
		}

		super.onResume();
	}

	private void refreshData() {
		tvHelpActive.setVisibility(View.GONE);
		btnActive.setVisibility(View.GONE);
		tvHelpActive.setVisibility(View.GONE);
		radioGroup.setVisibility(View.GONE);
		tvCodeIsdn.setVisibility(View.GONE);
		btnActive.setVisibility(View.GONE);
		radioHasSim.setChecked(true);
		radioNotSim.setChecked(false);
		tvCodeIsdn.setVisibility(View.GONE);
		tvChooseChannel.setHint(R.string.chanel_distribution);
		// tvChooseChannel.setText(getString(R.string.chanel_distribution));

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {

			switch (requestCode) {
			case REQUEST_CHANGE_CHANNEL:
				checkSelectChanel = true;
				selectedStaff = (Staff) data.getExtras().getSerializable(
						"staff");
                ChannelTypeObject channelType = (ChannelTypeObject) data.getExtras()
                        .getSerializable("channelType");

				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v == tvChooseChannel) {
			getChanelDistribution();
		} else if (v == btnCheckChanel) {
			checkChanelDistribution();
		} else if (v == btnActive) {
			updateChanelDistribution();
		} else if (v.getId() == R.id.relaBackHome) {
			activity.onBackPressed();
		}
	}

	// confirm update
    private final OnClickListener confirmActiveAccountCallBack = new OnClickListener() {

		@Override
		public void onClick(View v) {
			submitNewChanel();
		}
	};

	private final OnClickListener confirmSwitchAccountCallBack = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switchChanel();
		}
	};

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// Intent intent = new Intent(getActivity(), LoginActivity.class);
			// startActivity(intent);
			// getActivity().finish();
			// MainActivity.getInstance().finish();

			// Constant.VSAMenu.MENU_ACTIVE_ACCOUNT_PAYMENT
			LoginDialog dialog = new LoginDialog(activity,
					Constant.VSAMenu.MENU_ACTIVE_ACCOUNT_PAYMENT);
			dialog.show();
		}
	};

	private void updateChanelDistribution() {
		Dialog dialog = null;
		if (btnActive.isSelected()) {
			dialog = CommonActivity.createDialog(activity,
					getString(R.string.message_confirm_active_account),
					getString(R.string.app_name), getString(R.string.say_ko),
					getString(R.string.say_co), null,
					confirmActiveAccountCallBack);

		} else {

			dialog = CommonActivity.createDialog(activity,
					getString(R.string.message_confirm_switch_account),
					getString(R.string.app_name), getString(R.string.say_ko),
					getString(R.string.say_co), null,
					confirmSwitchAccountCallBack);
		}
		dialog.show();
	}

	private void switchChanel() {
		// Dialog dialog = null;
		// String phoneNumber = tvChooseChannel.getText().toString().trim();
		// if (phoneNumber.length() == 0 ||
		// CommonActivity.validateIsdn(phoneNumber) == false) {
		// dialog = CommonActivity.createAlertDialog(activity,
		// getString(R.string.message_pleass_isdn),
		// getString(R.string.app_name));
		// }
		// else if (edtCodeSerialSim.getText().length() == 0) {
		// dialog = CommonActivity.createAlertDialog(activity,
		// getString(R.string.message_pleass_serial_sim),
		// getString(R.string.input_code_serial_sim));
		// }

		// if (dialog != null) {
		// dialog.show();
		// return;
		// }

		if (CommonActivity.isNetworkConnected(activity)) {
			CheckAccountAgentAndUpdateStaffAsynctask updateAccountAgentAsynctask = new CheckAccountAgentAndUpdateStaffAsynctask(
					activity, 3, selectedStaff);
			updateAccountAgentAsynctask.execute();
		} else {
			CommonActivity.createAlertDialog(activity,
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}

	}

	private void submitNewChanel() {
		// Dialog dialog = null;
		// String phoneNumber = edtCodeIsdn.getText().toString().trim();
		// if (phoneNumber.length() > 0 &&
		// CommonActivity.validateIsdn(phoneNumber)) {
		// CommonActivity.createAlertDialog(activity,
		// getString(R.string.message_isdn_error_format),
		// getString(R.string.app_name)).show();
		// return;
		// }

		// if (edtCodeIsdn.getText().length() != 0 &&
		// edtCodeSerialSim.getText().length() == 0) {
		// dialog = CommonActivity.createAlertDialog(activity,
		// getString(R.string.message_pleass_serial_sim),
		// getString(R.string.input_code_serial_sim));
		// } else if (edtCodeSerialSim.getText().length() != 0 &&
		// edtCodeIsdn.getText().length() == 0) {
		// dialog = CommonActivity.createAlertDialog(activity,
		// getString(R.string.message_pleass_isdn),
		// getString(R.string.input_code_isdn));
		// }
		// if (dialog != null) {
		// dialog.show();
		// return;
		// }

		if (CommonActivity.isNetworkConnected(activity)) {
			CheckAccountAgentAndUpdateStaffAsynctask updateAccountAgentAsynctask = new CheckAccountAgentAndUpdateStaffAsynctask(
					activity, 2, selectedStaff);
			updateAccountAgentAsynctask.execute();
		} else {
			CommonActivity.createAlertDialog(activity,
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}

	}

	private void checkChanelDistribution() {

		String strChooseChanel = tvChooseChannel.getHint().toString().trim();
		Log.d("Log", "message chooseChanel to check: " + strChooseChanel);
		if (strChooseChanel.length() == 0
				|| strChooseChanel
						.equals(getString(R.string.chanel_distribution))) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.message_pleass_chanel_distribution),
					getString(R.string.app_name)).show();
		} else {
			CheckAccountAgentAndUpdateStaffAsynctask checkAccountAgentAsynctask = new CheckAccountAgentAndUpdateStaffAsynctask(
					activity, 1, selectedStaff);
			checkAccountAgentAsynctask.execute();
		}
	}

	private void getChanelDistribution() {
		checkSelectChanel = false;
		tvCodeIsdn.setText("");
		edtCodeSerialSim.setText("");
		FragmentChooseChannel fragment = new FragmentChooseChannel();
		fragment.setTargetFragment(FragmentActiveAccountPayment.this,
				REQUEST_CHANGE_CHANNEL);
		ReplaceFragment.replaceFragment(activity, fragment, false);
	}

	@SuppressWarnings("unused")
	private class CheckAccountAgentAndUpdateStaffAsynctask extends
			AsyncTask<Void, Void, Void> {

		private final int typeRequest;
		private final Context context;
		private final Staff staff;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public CheckAccountAgentAndUpdateStaffAsynctask(Context context,
				int typeRequest, Staff staff) {

			this.context = context;
			this.staff = staff;
			this.typeRequest = typeRequest;
			this.progress = new ProgressDialog(this.context);
			this.progress.setCancelable(false);
			if (typeRequest == 1) {
				this.progress.setMessage(context.getResources().getString(
						R.string.loading_checking));
			} else if (typeRequest == 2) {
				this.progress.setMessage(context.getResources().getString(
						R.string.loading_active));
			} else if (typeRequest == 3) {
				this.progress.setMessage(context.getResources().getString(
						R.string.loading_update_account_agent));
			}
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			if (typeRequest == 1) {
				accountAgent = null;
				checkAccountAgent(staff);
			} else if (typeRequest == 2) {
				updateStaff(staff);
			} else if (typeRequest == 3) {
				switchAccountAgent(staff);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			this.progress.dismiss();
			edtCodeSerialSim.setEnabled(true);
			if (errorCode.equals("0")) {
				if (typeRequest == 1) { // kiểm tra kênh phân phối
					tvHelpActive.setVisibility(View.VISIBLE);
					btnActive.setVisibility(View.VISIBLE);
					if (accountAgent != null) { // tồn tại accont agent
						btnActive.setText(getString(R.string.btn_active));
						btnActive.setSelected(false);
						if (accountAgent.getIsdn() != null
								&& accountAgent.getIsdn().length() > 0) {
							tvHelpActive
									.setText(getString(R.string.message_help_switch_chanel_have_Sim));
							tvCodeIsdn.setVisibility(View.VISIBLE);
							tvCodeIsdn.setText(getString(R.string.number_isdn)
									+ " " + accountAgent.getIsdn());
						} else {
							tvHelpActive
									.setText(getString(R.string.message_help_switch_chanel_Not_Sim));
						}
					} else { // ko tồn tại accont agent
						tvHelpActive
								.setText(getString(R.string.message_help_active_chanel));
						btnActive
								.setText(getString(R.string.btn_active_submit));
						btnActive.setSelected(true);
						radioGroup.setVisibility(View.VISIBLE);
					}
				} else if (typeRequest == 2) {
					String strMessage = "";
					if (description != null && description.length() > 0) {
						strMessage = getString(R.string.message_active_account_has_sim_success)
								+ description;

					} else {
						strMessage = getString(R.string.message_active_account_success);
					}
					CommonActivity.createAlertDialog(activity, strMessage,
							getString(R.string.app_name)).show();
					refreshData();

				} else if (typeRequest == 3) {
					String strMessage = "";
					if (description != null && description.length() > 0) {
						strMessage = getString(R.string.message_switch_account_has_sim_success)
								+ description;
					} else {
						strMessage = getString(R.string.message_switch_account_success);
					}
					CommonActivity.createAlertDialog(activity, strMessage,
							getString(R.string.app_name)).show();
					refreshData();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
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

		public void checkAccountAgent(Staff staff) {
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_checkAccountAgent");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:checkAccountAgent>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<channelCode>").append(staff.getStaffCode()).append("</channelCode>");
				rawData.append("</input>");
				rawData.append("</ws:checkAccountAgent>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_checkAccountAgent");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("accountAgent");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						accountAgent = new AccountAgentObj();
						accountAgent.setIsdn(parse.getValue(e1, "isdn"));
						accountAgent.setSerial(parse.getValue(e1, "serial"));
					}
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
		}

		public void switchAccountAgent(Staff staff) {
			String original = "";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_changeOrUpdateAccount");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:changeOrUpdateAccount>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<channelCode>").append(staff.getStaffCode()).append("</channelCode>");

				if (tvCodeIsdn.getVisibility() == View.INVISIBLE
						&& accountAgent.getIsdn().length() > 0) {
					rawData.append("<isdn>").append(accountAgent.getIsdn()).append("</isdn>");
				}

				rawData.append("</input>");
				rawData.append("</ws:changeOrUpdateAccount>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_changeOrUpdateAccount");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					Log.d("updateStaff", "description request" + description);
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
		}

		public void updateStaff(Staff staff) {

			String hasSim = "false";

			if (radioHasSim.isChecked()) {
				hasSim = "true";
			}

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_activeAccountAgent");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:activeAccountAgent>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<channelCode>").append(staff.getStaffCode()).append("</channelCode>");
				rawData.append("<hasSim>").append(hasSim).append("</hasSim>");
				rawData.append("</input>");
				rawData.append("</ws:activeAccountAgent>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_activeAccountAgent");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					Log.d("updateStaff", "description request" + description);
				}
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
		}

	}
}
