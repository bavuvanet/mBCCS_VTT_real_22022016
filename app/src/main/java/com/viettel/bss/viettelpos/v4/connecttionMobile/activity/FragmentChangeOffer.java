package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.GetPakageBundleAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ChangeZoneBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PakageChargeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentChangeOffer extends Fragment implements OnClickListener {

    private EditText edtCusPhone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_change_offer, container,
				false);
		unitView(view);
		return view;
	}

	private void unitView(View view) {

		edtCusPhone = (EditText) view.findViewById(R.id.edtCusPhone);
        Button lncheckchanel = (Button) view.findViewById(R.id.btn_change);
        Button btn_change_zone = (Button) view.findViewById(R.id.btn_change_zone);
		btn_change_zone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!CommonActivity.isNullOrEmpty(edtCusPhone.getText()
						.toString().trim())) {

					ValidateChangeZoneAsyn validateChangeProductAsyn = new ValidateChangeZoneAsyn(
							getActivity());
					validateChangeProductAsyn.execute(CommonActivity
							.checkStardardIsdn(edtCusPhone.getText().toString()
									.trim()));

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.validate_phone),
							getString(R.string.app_name)).show();
				}

			}
		});
		lncheckchanel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!CommonActivity.isNullOrEmpty(edtCusPhone.getText()
						.toString().trim())) {

					ValidateChangeProductAsyn validateChangeProductAsyn = new ValidateChangeProductAsyn(
							getActivity());
					validateChangeProductAsyn.execute(CommonActivity
							.checkStardardIsdn(edtCusPhone.getText().toString()
									.trim()));

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.validate_phone),
							getString(R.string.app_name)).show();
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.Chuyendoigoicuoc);
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	private class ChangeProductAsyn extends AsyncTask<String, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public ChangeProductAsyn(Context context) {
			this.context = context;
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
		protected String doInBackground(String... arg0) {
			return changeProduct(arg0[0], arg0[1], arg0[2], arg0[3]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {

				if (dialog != null) {
					dialog.dismiss();
				}
				if (dialogSelectReason != null) {
					dialogSelectReason.dismiss();
				}
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.chuyendoigoisuccess),
						getString(R.string.app_name)).show();

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

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

		private String changeProduct(String isdn, String offerId,
				String reasonId, String zoneCode) {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_changeProduct");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:changeProduct>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");
				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");

				rawData.append("<reasonId>").append(reasonId);
				rawData.append("</reasonId>");

				rawData.append("<isdn>").append(isdn);
				rawData.append("</isdn>");
				if ("1".equals(alowZone)) {
					rawData.append("<zoneCode>").append(zoneCode);
					rawData.append("</zoneCode>");
				} else {
					rawData.append("<zoneCode>" + "");
					rawData.append("</zoneCode>");
				}

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:changeProduct>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_changeProduct");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					String token = parse.getValue(e2, "token");
					if (!CommonActivity.isNullOrEmpty(token)) {
						Session.setToken(token);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return errorCode;
		}

	}

	private class ChangeZoneAsyn extends AsyncTask<String, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public ChangeZoneAsyn(Context context) {
			this.context = context;
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
		protected String doInBackground(String... arg0) {
			return changeZoneNew(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {
				if (dialogChangeZone != null) {
					dialogChangeZone.dismiss();
				}
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.chuyenzonesuccess),
						getString(R.string.app_name)).show();

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

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

		private String changeZoneNew(String isdn, String reasonId,
				String zoneCode) {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_changeZoneNew");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:changeZoneNew>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");

				rawData.append("<reasonId>").append(reasonId);
				rawData.append("</reasonId>");

				rawData.append("<isdn>").append(isdn);
				rawData.append("</isdn>");

				rawData.append("<zoneCode>").append(zoneCode);
				rawData.append("</zoneCode>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:changeZoneNew>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_changeZoneNew");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					String token = parse.getValue(e2, "token");
					if (!CommonActivity.isNullOrEmpty(token)) {
						Session.setToken(token);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return errorCode;
		}

	}

	private class ValidateChangeZoneAsyn extends
			AsyncTask<String, Void, ChangeZoneBean> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public ValidateChangeZoneAsyn(Context context) {
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
		protected ChangeZoneBean doInBackground(String... arg0) {
			return validateChangeZone(arg0[0]);
		}

		@Override
		protected void onPostExecute(ChangeZoneBean result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {
				if (result != null && result.getLstReason() != null
						&& !result.getLstReason().isEmpty()) {

					showDialogChangeZone(result);

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getString(R.string.checknotthongtinzone),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

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

		private ChangeZoneBean validateChangeZone(String isdn) {
			String original = null;
			ChangeZoneBean changeZoneBean = new ChangeZoneBean();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_validateChangeZone");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:validateChangeZone>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");
				rawData.append("<isdn>").append(isdn);
				rawData.append("</isdn>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:validateChangeZone>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_validateChangeZone");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);
				// parse xml

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nlOffer = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					String token = parse.getValue(e2, "token");
					if (!CommonActivity.isNullOrEmpty(token)) {
						Session.setToken(token);
					}
					String numResetZone = parse.getValue(e2, "numResetZone");
					changeZoneBean.setNumResetZone(numResetZone);
					String balance = parse.getValue(e2, "balance");
					changeZoneBean.setBalance(balance);
					String currZoneCode = parse.getValue(e2, "currZoneCode");
					changeZoneBean.setCurrZoneCode(currZoneCode);
					String currProduct = parse.getValue(e2, "currProduct");
					changeZoneBean.setCurrProduct(currProduct);
					String feeChangeZone = parse.getValue(e2, "feeChangeZone");
					changeZoneBean.setFeeChangeZone(feeChangeZone);
					String numberChangeZone = parse.getValue(e2,
							"numberChangeZone");
					changeZoneBean.setNumberChangeZone(numberChangeZone);
					ArrayList<Spin> arrReasonPre = new ArrayList<>();
					nlOffer = e2.getElementsByTagName("lstReasonPre");
					for (int j = 0; j < nlOffer.getLength(); j++) {
						Element e1 = (Element) nlOffer.item(j);
						Spin paChargeBeans = new Spin();
						String reasonId = parse.getValue(e1, "reasonId");
						Log.d("reasonId", reasonId);
						paChargeBeans.setId(reasonId);
						String name = parse.getValue(e1, "name");
						Log.d("name", name);
						paChargeBeans.setValue(name);
						arrReasonPre.add(paChargeBeans);
					}
					changeZoneBean.setLstReason(arrReasonPre);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return changeZoneBean;
		}

	}

	private class ValidateChangeProductAsyn extends
			AsyncTask<String, Void, ArrayList<PakageChargeBeans>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public ValidateChangeProductAsyn(Context context) {
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
		protected ArrayList<PakageChargeBeans> doInBackground(String... arg0) {
			return validateChangeProduct(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<PakageChargeBeans> result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {
				if (result != null && !result.isEmpty()) {
					showDialogSelectOffer(result);
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), getString(R.string.notpakagemobile),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

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

		private ArrayList<PakageChargeBeans> validateChangeProduct(String isdn) {
			String original = null;
			ArrayList<PakageChargeBeans> arrPakageChargeBeans = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_validateChangeProduct");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:validateChangeProduct>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");
				rawData.append("<isdn>").append(isdn);
				rawData.append("</isdn>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:validateChangeProduct>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_validateChangeProduct");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// parse xml

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nlOffer = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					String token = parse.getValue(e2, "token");
					if (!CommonActivity.isNullOrEmpty(token)) {
						Session.setToken(token);
					}
					nlOffer = e2.getElementsByTagName("lstProductOfferCM");
					for (int j = 0; j < nlOffer.getLength(); j++) {
						Element e1 = (Element) nlOffer.item(j);
						PakageChargeBeans paChargeBeans = new PakageChargeBeans();
						String offerId = parse.getValue(e1, "offerId");
						Log.d("offerId", offerId);
						paChargeBeans.setOfferId(offerId);
						String offerName = parse.getValue(e1, "offerName");
						Log.d("offerName", offerName);
						paChargeBeans.setOfferName(offerName);
						arrPakageChargeBeans.add(paChargeBeans);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arrPakageChargeBeans;
		}

	}

	private String alowZone = "";

	private class GetInfoChangeProductAsyn extends
			AsyncTask<String, Void, ArrayList<Spin>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetInfoChangeProductAsyn(Context context) {
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
		protected ArrayList<Spin> doInBackground(String... arg0) {
			return getInfoChangeProduct(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {
				if (result != null && !result.isEmpty()) {
					showDialogSelectReason(result, alowZone);

				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), getString(R.string.checklydooffer),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

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

		private ArrayList<Spin> getInfoChangeProduct(String offerId) {
			String original = null;
			ArrayList<Spin> arrReason = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getInfoChangeProduct");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getInfoChangeProduct>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken());
				rawData.append("</token>");
				rawData.append("<offerId>").append(offerId);
				rawData.append("</offerId>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getInfoChangeProduct>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getInfoChangeProduct");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nlOffer = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					String token = parse.getValue(e2, "token");
					if (!CommonActivity.isNullOrEmpty(token)) {
						Session.setToken(token);
					}
					alowZone = parse.getValue(e2, "allowZone");
					nlOffer = e2.getElementsByTagName("lstReasonPre");
					for (int j = 0; j < nlOffer.getLength(); j++) {
						Element e1 = (Element) nlOffer.item(j);
						Spin spin = new Spin();
						String id = parse.getValue(e1, "reasonId");
						spin.setId(id);
						String name = parse.getValue(e1, "name");
						spin.setValue(name);
						arrReason.add(spin);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arrReason;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.relaBackHome:

			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

	// lay danh sach tinh
	private ArrayList<AreaBean> arrProvince = new ArrayList<>();

	private void initProvince() {
		try {
			GetAreaDal dal = new GetAreaDal(getActivity());
			dal.open();
			arrProvince = dal.getLstProvince();
			dal.close();

			if (arrProvince != null && arrProvince.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (AreaBean areaBean : arrProvince) {
					adapter.add(areaBean.getNameProvince());
				}
				spn_tinhtp.setAdapter(adapter);

			}
		} catch (Exception ex) {
			Log.e("initProvince", ex.toString());
		}
	}

	private Dialog dialogSelectReason;
    private Spinner spn_tinhtp;
    private String reasonId = "";
	private String province = "";

	private void showDialogSelectReason(final ArrayList<Spin> arrReson,
			String alowZone) {
		dialogSelectReason = new Dialog(getActivity());
		dialogSelectReason.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogSelectReason.setContentView(R.layout.layout_change_offer_dialog);

        Spinner spn_reason = (Spinner) dialogSelectReason.findViewById(R.id.spn_reason);
		spn_reason.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				reasonId = arrReson.get(arg2).getId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		spn_tinhtp = (Spinner) dialogSelectReason.findViewById(R.id.spn_tinhtp);
		spn_tinhtp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				province = arrProvince.get(arg2).getProvince();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
        LinearLayout lntinhtp = (LinearLayout) dialogSelectReason
                .findViewById(R.id.lntinhtp);
		Utils.setDataSpinner(getActivity(), arrReson, spn_reason);
		if (alowZone.equalsIgnoreCase("1")) {
			lntinhtp.setVisibility(View.VISIBLE);
			if (arrProvince != null && !arrProvince.isEmpty()) {
				arrProvince = new ArrayList<>();
			}
			initProvince();
		} else {
			lntinhtp.setVisibility(View.GONE);
		}

		dialogSelectReason.findViewById(R.id.btnOk).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						CommonActivity.createDialog(getActivity(),
								getString(R.string.checkconfirmoffer),
								getString(R.string.app_name),
								getString(R.string.buttonCancel),
								getString(R.string.buttonOk ),
								null,onclickChangeProduct ).show();
					}
				});
		dialogSelectReason.findViewById(R.id.btncancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						dialogSelectReason.dismiss();
					}
				});

		dialogSelectReason.show();
	}

	// chuyen doi goi cuoc cho nay
    private final OnClickListener onclickChangeProduct = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO xu ly goi ws chuyen doi goi cuoc
			if (CommonActivity.isNetworkConnected(getActivity())) {
				ChangeProductAsyn changeProductAsyn = new ChangeProductAsyn(
						getActivity());
				changeProductAsyn.execute(
						CommonActivity.checkStardardIsdn(edtCusPhone.getText()
								.toString().trim()), offerId, reasonId,
						province);
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			dialogSelectReason.dismiss();

		}
	};

	private Dialog dialogChangeZone;
	private ArrayList<AreaBean> arrProvinceChangeZone = new ArrayList<>();
	private String provinceChangeZone = "";

	private void showDialogChangeZone(final ChangeZoneBean changeZoneBean) {

		dialogChangeZone = new Dialog(getActivity());
		dialogChangeZone.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogChangeZone.setContentView(R.layout.layout_change_zone_dialog);

		EditText edtgoicuochientai = (EditText) dialogChangeZone
				.findViewById(R.id.edtgoicuochientai);

		if (!CommonActivity.isNullOrEmpty(changeZoneBean.getCurrProduct())) {
			edtgoicuochientai.setText(changeZoneBean.getCurrProduct());
		}

		EditText edtslanresetzone = (EditText) dialogChangeZone
				.findViewById(R.id.edtslanresetzone);

		if (!CommonActivity.isNullOrEmpty(changeZoneBean.getNumResetZone())) {
			edtslanresetzone.setText(StringUtils.formatMoney(changeZoneBean
					.getNumResetZone()));
		}

		EditText edttkchinh = (EditText) dialogChangeZone
				.findViewById(R.id.edttkchinh);
		if (!CommonActivity.isNullOrEmpty(changeZoneBean.getBalance())) {
			edttkchinh.setText(changeZoneBean.getBalance());
		}
		EditText edtmazonehientai = (EditText) dialogChangeZone
				.findViewById(R.id.edtmazonehientai);
		if (!CommonActivity.isNullOrEmpty(changeZoneBean.getCurrZoneCode())) {
			edtmazonehientai.setText(changeZoneBean.getCurrZoneCode());
		}

		Spinner spn_tinhtp = (Spinner) dialogChangeZone
				.findViewById(R.id.spiner_tinhtp);
		spn_tinhtp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arrProvinceChangeZone != null
						&& !arrProvinceChangeZone.isEmpty()) {
					provinceChangeZone = arrProvinceChangeZone.get(arg2)
							.getProvince();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		try {
			GetAreaDal dal = new GetAreaDal(getActivity());
			dal.open();
			arrProvinceChangeZone = dal.getLstProvince();
			dal.close();

			if (arrProvinceChangeZone != null
					&& arrProvinceChangeZone.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				for (AreaBean areaBean : arrProvinceChangeZone) {
					adapter.add(areaBean.getNameProvince());
				}
				spn_tinhtp.setAdapter(adapter);

			}
		} catch (Exception ex) {
			Log.e("initProvince", ex.toString());
		}

		final Spinner spn_reason = (Spinner) dialogChangeZone
				.findViewById(R.id.spn_reason);

		if (changeZoneBean.getLstReason() != null
				&& !changeZoneBean.getLstReason().isEmpty()) {
			Utils.setDataSpinner(getActivity(), changeZoneBean.getLstReason(),
					spn_reason);
		}

		Button btnOk = (Button) dialogChangeZone.findViewById(R.id.btnOk);

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// private String changeZoneNew(String isdn, String reasonId,
				// String zoneCode) {

				OnClickListener onclickChangeZone = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO xu ly goi ws chuyen doi goi cuoc
						if (CommonActivity.isNetworkConnected(getActivity())) {

							Spin subItem = (Spin) spn_reason.getSelectedItem();
							ChangeZoneAsyn changeZoneAsyn = new ChangeZoneAsyn(
									getActivity());
							changeZoneAsyn.execute(CommonActivity
									.checkStardardIsdn(edtCusPhone.getText()
											.toString().trim()), subItem
									.getId(), provinceChangeZone);
						} else {
							CommonActivity.createAlertDialog(getActivity(),
									getString(R.string.errorNetwork),
									getString(R.string.app_name)).show();
						}
						// dialogChangeZone.dismiss();
					}
				};

				Spin subItem = (Spin) spn_reason.getSelectedItem();

				// var numResetZone = ${fn:escapeXml(numResetZone)};
				// var numberChangeZone = ${fn:escapeXml(numberChangeZone)};
				// var feeChangeZone = ${fn:escapeXml(feeChangeZone)};
				// String numberChangeZone = changeZoneBean.getN
				// if (numResetZone != null && numResetZone != "" &&
				// numberChangeZone != null && numberChangeZone != "" &&
				// parseInt(numResetZone) + 1 > parseInt(numberChangeZone) &&
				// feeChangeZone != null && feeChangeZone != "0") {
				// strConfirm =
				// "Phí chuyển đổi là ${fn:escapeXml(feeChangeZone)} đồng và sẽ trừ vào tài khoản gốc của thuê bao. Bạn có muốn tiếp tục hay không?";
				// }
				String strConfirm = "";

				if (!CommonActivity.isNullOrEmpty(changeZoneBean
						.getNumResetZone())
						&& !CommonActivity.isNullOrEmpty(changeZoneBean
								.getNumberChangeZone())
						&& Integer.parseInt(changeZoneBean
								.getNumResetZone()) + 1 > Integer
								.parseInt(changeZoneBean.getNumberChangeZone())
						&& changeZoneBean.getFeeChangeZone() != null
						&& !changeZoneBean.getFeeChangeZone().equals("0")) {
					strConfirm = getActivity().getString(R.string.feechargeis)
							+ " "
							+ changeZoneBean.getFeeChangeZone()
							+ " "
							+ getActivity().getString(
									R.string.checkconfirmzonefeecharge);
				} else {
					strConfirm = getActivity().getString(
							R.string.checkconfirmzone);
				}
				// if(!CommonActivity.isNullOrEmpty(changeZoneBean.getFeeChangeZone())
				// && Integer.parseInt(changeZoneBean.getFeeChangeZone()) > 0){
				//
				// }else{
				//
				// }

				if (subItem != null
						&& !CommonActivity.isNullOrEmpty(subItem.getId())) {
					CommonActivity.createDialog(getActivity(), strConfirm,
							getString(R.string.app_name),
							getString(R.string.buttonCancel),
							getString(R.string.buttonOk),
							null, onclickChangeZone ).show();
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkreasonchangezone),
							getString(R.string.app_name)).show();
				}
			}
		});

		Button btncancel = (Button) dialogChangeZone
				.findViewById(R.id.btncancel);

		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogChangeZone.dismiss();
			}
		});

		dialogChangeZone.show();

	}

	private Dialog dialog;
	private String offerId;

	private void showDialogSelectOffer(
			final ArrayList<PakageChargeBeans> arrChargeBeans) {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.connection_layout_lst_offer);
		ListView lvPakage = (ListView) dialog.findViewById(R.id.lstpstn);
		GetPakageBundleAdapter adapGetPakageBundleAdapter = new GetPakageBundleAdapter(
				arrChargeBeans, getActivity());
		lvPakage.setAdapter(adapGetPakageBundleAdapter);
		lvPakage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				offerId = arrChargeBeans.get(arg2).getOfferId();
				Log.d("offerIddddddđ", offerId);
				if (CommonActivity.isNetworkConnected(getActivity())) {
					GetInfoChangeProductAsyn getInfoChangeProductAsyn = new GetInfoChangeProductAsyn(
							getActivity());
					getInfoChangeProductAsyn.execute(offerId);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}

			}
		});
		Button btnhuy = (Button) dialog.findViewById(R.id.btnhuy);
		btnhuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();

			}
		});
		dialog.show();
	}

}
