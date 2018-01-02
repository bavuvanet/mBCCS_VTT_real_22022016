package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListPaymentDetailChargeAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSub;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidDetailBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentChangePromotion extends Fragment implements
		OnClickListener {

	private Spinner spnService, spn_makm, spn_dongcuocmoi, spn_reason_fail,
			spn_tgapdung;
	private EditText edt_account;
	private TextView txt_makmht, txt_ctdcht;
	private Button btn_apply, btn_cancel;

	private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();
	private ArrayList<Spin> arrPromotionUnit = new ArrayList<Spin>();
	private ArrayList<Spin> lstReason = new ArrayList<Spin>();
	private LinearLayout ll_prepair_info;
	private ArrayList<PaymentPrePaidPromotionBeans> arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
	private String prepaidCode = "";
	private ImageButton btn_search;

	private String serviceType = "";
	private String telecomServiceId = "";

	private ProgressBar prbreason, prbCuocdongtruoc, prbProunit;
	private InfoSub infoSub = null;
	private String dateNowString = "";
	private ArrayAdapter<String> adapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Calendar calendar = Calendar.getInstance();
		int fromYear = calendar.get(Calendar.YEAR);
		int fromMonth = calendar.get(Calendar.MONTH) + 1;
		int fromDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		dateNowString = fromDay + "/" + fromMonth + "/" + fromYear + "";

		View view = inflater.inflate(R.layout.activity_update_promotion,
				container, false);
		unitView(view);
		return view;
	}

	private void unitView(View mView) {
		if (arrTelecomServiceBeans != null && !arrTelecomServiceBeans.isEmpty()) {
			arrTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();
		}

		prbreason = (ProgressBar) mView.findViewById(R.id.prbreason);
		prbCuocdongtruoc = (ProgressBar) mView
				.findViewById(R.id.prbCuocdongtruoc);
		prbProunit = (ProgressBar) mView.findViewById(R.id.prbProunit);
		spn_tgapdung = (Spinner) mView.findViewById(R.id.spn_tgapdung);

		spnService = (Spinner) mView.findViewById(R.id.spnService);
		spnService.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				resetUI();

				TelecomServiceBeans telecomServiceBeans = arrTelecomServiceBeans
						.get(arg2);
				if (telecomServiceBeans != null) {
					serviceType = telecomServiceBeans.getServiceAlias();
					telecomServiceId = telecomServiceBeans
							.getTelecomServiceId();
					// if (!CommonActivity.isNullOrEmpty(serviceType)) {
					// if (CommonActivity.isNetworkConnected(getActivity())) {
					// AsyntaskGetReasonPos asGetReasonPos = new
					// AsyntaskGetReasonPos(
					// getActivity());
					// asGetReasonPos.execute();
					// }
					// }

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spn_makm = (Spinner) mView.findViewById(R.id.spn_makm);
		spn_makm.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				// if (arg2 == 0) {
				// arrPaymentPrePaidPromotionBeans = new
				// ArrayList<PaymentPrePaidPromotionBeans>();
				// adapter = new ArrayAdapter<String>(getActivity(),
				// android.R.layout.simple_dropdown_item_1line,
				// android.R.id.text1);
				// for (PaymentPrePaidPromotionBeans typePaperBeans :
				// arrPaymentPrePaidPromotionBeans) {
				// adapter.add(typePaperBeans.getName());
				// }
				// spn_dongcuocmoi.setAdapter(adapter);
				// adapter.notifyDataSetChanged();
				// prepaidCode = "";
				// } else {

				if (item != null
						// && !CommonActivity.isNullOrEmpty(item.getId())
						&& infoSub != null
						&& !CommonActivity.isNullOrEmpty(infoSub
								.getProductCode())
						// && !CommonActivity.isNullOrEmpty(infoSub
						// .getPromotionCode())
						&& (!CommonActivity.isNullOrEmpty(infoSub.getProvince())
								|| !CommonActivity.isNullOrEmpty(infoSub
										.getDistrict()) || !CommonActivity
									.isNullOrEmpty(infoSub.getPrecinct()))) {
					arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
					adapter = new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_dropdown_item_1line,
							android.R.id.text1);
					for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
						adapter.add(typePaperBeans.getName());
					}
					spn_dongcuocmoi.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					prepaidCode = "";
					// private ArrayList<PaymentPrePaidPromotionBeans>
					// getAllListPaymentPrePaid(
					// String promProgramCode, String packageId, String
					// provinceCode,
					// String today) {
					// l;
					GetAllListPaymentPrePaidAsyn getAllListPaymentPrePaidAsyn = new GetAllListPaymentPrePaidAsyn(
							getActivity());
					getAllListPaymentPrePaidAsyn.execute(item.getId(),
							infoSub.getProductCode(),
							infoSub.getProvince() + infoSub.getDistrict()
									+ infoSub.getPrecinct(), dateNowString);

					if (!CommonActivity.isNullOrEmpty(serviceType)
							&& !CommonActivity.isNullOrEmpty(edt_account
									.getText().toString())
							&& !CommonActivity.isNullOrEmpty(item.getId())) {
						AsyntaskGetReasonPos asGetReasonPos = new AsyntaskGetReasonPos(
								getActivity());
						asGetReasonPos.execute(edt_account.getText().toString()
								.trim(), serviceType, item.getId());
					}

				}
				// }

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spn_dongcuocmoi = (Spinner) mView.findViewById(R.id.spn_dongcuocmoi);
		spn_dongcuocmoi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arrPaymentPrePaidPromotionBeans != null
						&& !arrPaymentPrePaidPromotionBeans.isEmpty()) {
					prepaidCode = arrPaymentPrePaidPromotionBeans.get(arg2)
							.getPrePaidCode();
					if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
						showSelectCuocDongTruoc(arrPaymentPrePaidPromotionBeans
								.get(arg2));
					}
				} else {
					prepaidCode = "";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spn_reason_fail = (Spinner) mView.findViewById(R.id.spn_reason_fail);

		edt_account = (EditText) mView.findViewById(R.id.edt_account);
		txt_makmht = (TextView) mView.findViewById(R.id.txt_makmht);
		txt_ctdcht = (TextView) mView.findViewById(R.id.txt_ctdcht);

		ll_prepair_info = (LinearLayout) mView
				.findViewById(R.id.ll_prepair_info);
		ll_prepair_info.setVisibility(View.VISIBLE);

		btn_apply = (Button) mView.findViewById(R.id.btn_apply);
		btn_apply.setOnClickListener(this);
		btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		btn_search = (ImageButton) mView.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		initTelecomService();
		initTGapdung();

	}

	private void resetUI() {

		ll_prepair_info.setVisibility(View.GONE);
		edt_account.setText("");
		txt_makmht.setText("");
		txt_ctdcht.setText("");

		arrPromotionUnit = new ArrayList<Spin>();
		Spin spin = new Spin();
		spin.setValue(getString(R.string.txt_select));
		spin.setId("");
		arrPromotionUnit.add(0, spin);
		Utils.setDataSpinner(getActivity(), arrPromotionUnit, spn_makm);

		arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
		for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
			adapter.add(typePaperBeans.getName());
		}
		spn_dongcuocmoi.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	private void initTGapdung() {

		ArrayList<Spin> lstTGAD = new ArrayList<Spin>();
		lstTGAD.add(new Spin("1", getString(R.string.tdiemchuyendoi)));
		lstTGAD.add(new Spin("2", getString(R.string.ngay1)));

		Utils.setDataSpinner(getActivity(), lstTGAD, spn_tgapdung);

	}

	@Override
	public void onResume() {
		super.onResume();
		addActionBar();
	}

	private void addActionBar() {

		ActionBar mActionBar = getActivity().getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
				.findViewById(R.id.relaBackHome);
		relaBackHome.setOnClickListener(this);
		Button btnHome = (Button) mActionBar.getCustomView().findViewById(
				R.id.btnHome);
		btnHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			}
		});
		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtNameActionbar);
		txtNameActionBar.setText(getResources().getString(R.string.updatekm));
		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
				.findViewById(R.id.txtAddressActionbar);
		txtAddressActionBar.setVisibility(View.GONE);
		txtNameActionBar.setText(getResources().getString(R.string.updatekm));
	}

	// init typepaper
	private void initTelecomService() {
		try {

			GetServiceDal dal = new GetServiceDal(getActivity());
			dal.open();
			arrTelecomServiceBeans = dal.getlisTelecomServiceBeans();
			dal.close();
			TelecomServiceBeans serviceBeans = new TelecomServiceBeans();
			serviceBeans.setTele_service_name(getActivity().getResources()
					.getString(R.string.chondichvu));
			arrTelecomServiceBeans.add(0, serviceBeans);
			if (arrTelecomServiceBeans != null
					&& !arrTelecomServiceBeans.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						android.R.id.text1);
				for (TelecomServiceBeans telecomServiceBeans : arrTelecomServiceBeans) {
					adapter.add(telecomServiceBeans.getTele_service_name());
				}
				spnService.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e("initTypePaper", e.toString());
		}
	}

	// ham chuyen doi chuong trinh khuyen mai
	private class AsynUpdatePromotion extends AsyncTask<String, Void, String> {

		ProgressDialog progress;
		private Context context = null;
		private int type;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynUpdatePromotion(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			return updatePromotion(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				resetUI();
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.changePromotionSuccess),
						getString(R.string.app_name)).show();

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

		private String updatePromotion(String account, String serviceType,
				String promotionCodeNew, String prepaidCode, String reasonId) {
			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updatePromotion");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updatePromotion>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");

				rawData.append("<serviceType>" + serviceType + "</serviceType>");

				rawData.append("<promotionCodeNew>" + promotionCodeNew
						+ "</promotionCodeNew>");
				if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
					rawData.append("<prepaidCode>" + prepaidCode
							+ "</prepaidCode>");
				} else {
					rawData.append("<prepaidCode>" + "" + "</prepaidCode>");
				}

				Spin spinEffect = (Spin) spn_tgapdung.getSelectedItem();
				if (spinEffect != null) {
					rawData.append("<effectTime>" + spinEffect.getId()
							+ "</effectTime>");
				}

				rawData.append("<reasonId>" + reasonId + "</reasonId>");
				rawData.append("</input>");
				rawData.append("</ws:updatePromotion>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updatePromotion");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

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
				e.printStackTrace();
			}

			return errorCode;

		}

	}

	// ham lay thong tin thue bao
	private class AsynGetInfoSubscriberByAccountAndServiceType extends
			AsyncTask<String, Void, InfoSub> {

		ProgressDialog progress;
		private Context context = null;
		private int type;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynGetInfoSubscriberByAccountAndServiceType(Context mContext) {
			this.context = mContext;
			this.progress = new ProgressDialog(this.context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected InfoSub doInBackground(String... arg0) {
			return getInfoSubscriberByAccountAndServiceType(arg0[0]);
		}

		@Override
		protected void onPostExecute(InfoSub result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				infoSub = new InfoSub();
				if (result != null
						&& !CommonActivity.isNullOrEmpty(result.getAccount())
						// && !CommonActivity.isNullOrEmpty(result
						// .getPromotionCode())
						// && !CommonActivity.isNullOrEmpty(result
						// .getProductCode())
						&& (!CommonActivity.isNullOrEmpty(result.getProvince())
								|| !CommonActivity.isNullOrEmpty(result
										.getDistrict()) || !CommonActivity
									.isNullOrEmpty(result.getPrecinct()))) {
					infoSub = result;

					AsynGetPromotionUnit asynGetPromotionUnit = new AsynGetPromotionUnit(
							getActivity());
					asynGetPromotionUnit.execute(edt_account.getText()
							.toString().trim(), serviceType);

					txt_ctdcht.setText(result.getPrepaidCode());
					txt_makmht.setText(result.getPromotionCode());

					ll_prepair_info.setVisibility(View.VISIBLE);
				} else {
					ll_prepair_info.setVisibility(View.GONE);
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkinfosub),
							getString(R.string.app_name)).show();
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

		private InfoSub getInfoSubscriberByAccountAndServiceType(String account) {

			// Spin serviceItem = (Spin) spnService.getSelectedItem();
			InfoSub infoSub = new InfoSub();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getInfoSubscriberByAccountAndServiceType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getInfoSubscriberByAccountAndServiceType>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");
				// if (serviceItem != null) {
				rawData.append("<serviceType>" + serviceType + "</serviceType>");
				// }
				rawData.append("</input>");
				rawData.append("</ws:getInfoSubscriberByAccountAndServiceType>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getInfoSubscriberByAccountAndServiceType");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);

					// lay ra thong tin thue bao
					nodechild = doc.getElementsByTagName("infoSub");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						String account1 = parse.getValue(e1, "account");
						infoSub.setAccount(account1);
						String precinct = parse.getValue(e1, "precinct");
						infoSub.setPrecinct(precinct);
						String prepaidCode = parse.getValue(e1, "prepaidCode");
						infoSub.setPrepaidCode(prepaidCode);

						String productCode = parse.getValue(e1, "productCode");
						infoSub.setProductCode(productCode);
						String promotionCode = parse.getValue(e1,
								"promotionCode");
						infoSub.setPromotionCode(promotionCode);
						String province = parse.getValue(e1, "province");
						infoSub.setProvince(province);
						String district = parse.getValue(e1, "district");
						infoSub.setDistrict(district);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return infoSub;
		}

	}

	private class AsyntaskGetReasonPos extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetReasonPos(Context context) {
			this.context = context;
			// this.progress = new ProgressDialog(this.context);
			// this.progress.setCancelable(false);
			// this.progress.setMessage(context.getResources().getString(
			// R.string.waitting));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
			prbreason.setVisibility(View.VISIBLE);
			if (lstReason != null && lstReason.size() > 0) {
				lstReason.clear();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getReasonInfoPos(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			prbreason.setVisibility(View.GONE);
			if (errorCode.equalsIgnoreCase("0")) {
				lstReason.add(new Spin("-1",
						getString(R.string.txt_select_reason)));
				lstReason.addAll(result);
				Utils.setDataSpinner(getActivity(), lstReason, spn_reason_fail);

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					lstReason.add(new Spin("-1",
							getString(R.string.txt_select_reason)));
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
					lstReason.add(new Spin("-1",
							getString(R.string.txt_select_reason)));
					Utils.setDataSpinner(getActivity(), lstReason,
							spn_reason_fail);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Spin> getReasonInfoPos(String account,
				String serviceType, String promotionCodeNew) {

			// Spin serviceItem = (Spin) spnService.getSelectedItem();
			ArrayList<Spin> lstReason = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListReasonPosSaleByMap");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReasonPosSaleByMap>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");
				rawData.append("<promotionCodeNew>" + promotionCodeNew
						+ "</promotionCodeNew>");

				rawData.append("</input>");
				rawData.append("</ws:getListReasonPosSaleByMap>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListReasonPosSaleByMap");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstReason = parserListGroupPos(original);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReason;
		}

		public ArrayList<Spin> parserListGroupPos(String original) {
			ArrayList<Spin> lstReason = new ArrayList<Spin>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstReason");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "codeName"));
					Log.d("LOG", "value: " + spin.getValue());
					spin.setId(parse.getValue(e1, "reasonId"));
					Log.d("LOG", "Idddd: " + spin.getId());
					lstReason.add(spin);
				}
			}

			return lstReason;
		}

	}

	// lay danh sach khuyen mai trai nghiem
	private class AsynGetPromotionUnit extends
			AsyncTask<String, Void, ArrayList<Spin>> {

		private Context mContext = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynGetPromotionUnit(Context context) {
			this.mContext = context;
			prbProunit.setVisibility(View.VISIBLE);

		}

		@Override
		protected ArrayList<Spin> doInBackground(String... arg0) {
			return getLstPromotionUnit(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			super.onPostExecute(result);
			prbProunit.setVisibility(View.GONE);
			arrPromotionUnit = new ArrayList<Spin>();
			if (errorCode.equalsIgnoreCase("0")) {
				arrPromotionUnit.addAll(result);
				Spin spin = new Spin();
				spin.setValue(getString(R.string.txt_select));
				spin.setId("");
				arrPromotionUnit.add(0, spin);
				Utils.setDataSpinner(getActivity(), arrPromotionUnit, spn_makm);

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					result = new ArrayList<Spin>();

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									mContext.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mContext.getString(R.string.checkdes);
					}
					arrPromotionUnit = new ArrayList<Spin>();
					Utils.setDataSpinner(getActivity(), arrPromotionUnit,
							spn_makm);

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		// lay danh sach loai hop dong
		private ArrayList<Spin> getLstPromotionUnit(String account,
				String serviceType) {
			ArrayList<Spin> lstPromotionunit = new ArrayList<Spin>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListPromotionPosSaleByMap");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListPromotionPosSaleByMap>");
				rawData.append("<cmFixServiceInput>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");
				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getListPromotionPosSaleByMap>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListPromotionPosSaleByMap");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				// parser

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstPromotionType");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();
						spin.setValue(parse.getValue(e1, "codeName"));

						spin.setId(parse.getValue(e1, "promProgramCode"));

						lstPromotionunit.add(spin);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstPromotionunit;
		}

	}

	android.view.View.OnClickListener onclickUpdate = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Spin spinReason = (Spin) spn_reason_fail.getSelectedItem();
			Spin spinKM = (Spin) spn_makm.getSelectedItem();
			if (spinReason != null && spinKM != null) {
				AsynUpdatePromotion asynUpdatePromotion = new AsynUpdatePromotion(
						getActivity());
				asynUpdatePromotion.execute(infoSub.getAccount(), serviceType,
						spinKM.getId(), prepaidCode, spinReason.getId());
			}
		}
	};

	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:

			if (!CommonActivity.isNullOrEmpty(serviceType)) {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					if (!CommonActivity.isNullOrEmpty(edt_account.getText()
							.toString().trim())) {
						AsynGetInfoSubscriberByAccountAndServiceType asynGetInfoSubscriberByAccountAndServiceType = new AsynGetInfoSubscriberByAccountAndServiceType(
								getActivity());
						asynGetInfoSubscriberByAccountAndServiceType
								.execute(edt_account.getText().toString()
										.trim());
					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getString(R.string.checkaccount),
								getString(R.string.app_name)).show();
					}
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checkserviceType),
						getString(R.string.app_name)).show();
			}

			break;
		case R.id.btn_apply:
			if (CommonActivity.isNetworkConnected(getActivity())) {
				if (validatateUpdate()) {
					CommonActivity.createDialog(getActivity(),
							getString(R.string.confirmKM),
							getString(R.string.app_name),
							getString(R.string.ok), getString(R.string.cancel),
							onclickUpdate, null).show();
				}
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}

			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btn_cancel:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

	private boolean validatateUpdate() {
		Spin khuyenmaimoiw = (Spin) spn_makm.getSelectedItem();
		if (khuyenmaimoiw == null) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkkmmoi),
					getString(R.string.app_name)).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(khuyenmaimoiw.getId())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkkmmoi),
					getString(R.string.app_name)).show();
			return false;
		}

		if (arrPaymentPrePaidPromotionBeans != null
				&& arrPaymentPrePaidPromotionBeans.size() > 1) {
			if (!CommonActivity.isNullOrEmpty(prepaidCode)) {
				if (!CommonActivity.isNullOrEmpty(khuyenmaimoiw.getId())) {
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.checkcuocdongtruoc),
							getString(R.string.app_name)).show();
					return false;
				}
			}else{
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.checkcuocdongtruoc),
						getString(R.string.app_name)).show();
				return false;
			}

		}

		Spin spnReason = (Spin) spn_reason_fail.getSelectedItem();

		if (spnReason == null) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkreasonCN),
					getString(R.string.app_name)).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(spnReason.getId())
				|| spnReason.getId().equals("-1")) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkreasonCN),
					getString(R.string.app_name)).show();
			return false;
		}
		return true;
	}

	// lay thong tin cuoc dong truoc
	private class GetAllListPaymentPrePaidAsyn extends
			AsyncTask<String, Void, ArrayList<PaymentPrePaidPromotionBeans>> {

		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetAllListPaymentPrePaidAsyn(Context context) {
			this.context = context;

			prbCuocdongtruoc.setVisibility(View.VISIBLE);

		}

		@Override
		protected ArrayList<PaymentPrePaidPromotionBeans> doInBackground(
				String... arg0) {
			return getAllListPaymentPrePaid(arg0[0], arg0[1], arg0[2], arg0[3]);
		}

		@Override
		protected void onPostExecute(
				ArrayList<PaymentPrePaidPromotionBeans> result) {

			prbCuocdongtruoc.setVisibility(View.GONE);
			arrPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
			if (errorCode.equals("0")) {

				PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
				paymentPrePaidPromotionBeans
						.setName(getString(R.string.txt_select));
				paymentPrePaidPromotionBeans.setPrePaidCode("");
				result.add(0, paymentPrePaidPromotionBeans);
				arrPaymentPrePaidPromotionBeans.addAll(result);
				if (arrPaymentPrePaidPromotionBeans != null
						&& !arrPaymentPrePaidPromotionBeans.isEmpty()) {
					adapter = new ArrayAdapter<String>(getActivity(),
							android.R.layout.simple_dropdown_item_1line,
							android.R.id.text1);
					for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
						adapter.add(typePaperBeans.getName());
					}
					spn_dongcuocmoi.setAdapter(adapter);
				}
				btn_apply.setVisibility(View.VISIBLE);
			} else {
				btn_apply.setVisibility(View.GONE);
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getActivity(),
							android.R.layout.simple_dropdown_item_1line,
							android.R.id.text1);
					for (PaymentPrePaidPromotionBeans typePaperBeans : arrPaymentPrePaidPromotionBeans) {
						adapter.add(typePaperBeans.getName());
					}
					spn_dongcuocmoi.setAdapter(adapter);
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

		private ArrayList<PaymentPrePaidPromotionBeans> getAllListPaymentPrePaid(
				String promProgramCode, String packageId, String provinceCode,
				String today) {
			ArrayList<PaymentPrePaidPromotionBeans> lstPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
			String original = null;
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getAllListPaymentPrePaid");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getAllListPaymentPrePaid>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<promProgramCode>" + promProgramCode);
				rawData.append("</promProgramCode>");

				rawData.append("<packageId>" + packageId);
				rawData.append("</packageId>");

				rawData.append("<provinceCode>" + provinceCode);
				rawData.append("</provinceCode>");

				rawData.append("<today>" + today);
				rawData.append("</today>");

				rawData.append("</input>");
				rawData.append("</ws:getAllListPaymentPrePaid>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getAllListPaymentPrePaid");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				// parse xml
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodepaymentPrePaidPromotionBeans = null;
				NodeList nodePaymentPrePaidDetailBeans = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
					nodepaymentPrePaidPromotionBeans = e2
							.getElementsByTagName("paymentPrePaidPromotionBeans");
					for (int j = 0; j < nodepaymentPrePaidPromotionBeans
							.getLength(); j++) {
						Element e1 = (Element) nodepaymentPrePaidPromotionBeans
								.item(j);
						PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
						String name = parse.getValue(e1, "name");
						paymentPrePaidPromotionBeans.setName(name);

						String prePaidCode = parse.getValue(e1, "prePaidCode");
						paymentPrePaidPromotionBeans
								.setPrePaidCode(prePaidCode);

						ArrayList<PaymentPrePaidDetailBeans> lstPaymentPrePaidDetailBeans = new ArrayList<PaymentPrePaidDetailBeans>();

						nodePaymentPrePaidDetailBeans = e1
								.getElementsByTagName("paymentPrePaidDetailBeans");
						for (int k = 0; k < nodePaymentPrePaidDetailBeans
								.getLength(); k++) {
							Element e0 = (Element) nodePaymentPrePaidDetailBeans
									.item(k);
							PaymentPrePaidDetailBeans paymentPrePaidDetailBeans = new PaymentPrePaidDetailBeans();
							String moneyUnit = parse.getValue(e0, "moneyUnit");
							paymentPrePaidDetailBeans.setMoneyUnit(moneyUnit);
							String promAmount = parse
									.getValue(e0, "promAmount");
							paymentPrePaidDetailBeans.setPromAmount(promAmount);
							String endMonth = parse.getValue(e0, "endMonth");
							paymentPrePaidDetailBeans.setEndMonth(endMonth);
							String startMonth = parse
									.getValue(e0, "startMonth");
							paymentPrePaidDetailBeans.setStartMonth(startMonth);
							String subMonth = parse.getValue(e0, "subMonth");
							paymentPrePaidDetailBeans.setSubMonth(subMonth);
							lstPaymentPrePaidDetailBeans
									.add(paymentPrePaidDetailBeans);
						}

						paymentPrePaidPromotionBeans
								.setLstDetailBeans(lstPaymentPrePaidDetailBeans);

						lstPaymentPrePaidPromotionBeans
								.add(paymentPrePaidPromotionBeans);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstPaymentPrePaidPromotionBeans;
		}

	}

	private Dialog dialogCuocdongtruoc = null;

	private void showSelectCuocDongTruoc(
			PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans) {

		dialogCuocdongtruoc = new Dialog(getActivity());
		dialogCuocdongtruoc.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogCuocdongtruoc
				.setContentView(R.layout.connection_layout_detail_precharge);

		ListView listdetail = (ListView) dialogCuocdongtruoc
				.findViewById(R.id.listdetail);

		EditText txttencuocdongtruoc = (EditText) dialogCuocdongtruoc
				.findViewById(R.id.txttencuocdongtruoc);
		txttencuocdongtruoc.setText(paymentPrePaidPromotionBeans.getName());

		EditText txtmacuocdongtruoc = (EditText) dialogCuocdongtruoc
				.findViewById(R.id.txtmacuocdongtruoc);
		txtmacuocdongtruoc.setText(paymentPrePaidPromotionBeans
				.getPrePaidCode());
		GetListPaymentDetailChargeAdapter getListPaymentDetailChargeAdapter = new GetListPaymentDetailChargeAdapter(
				paymentPrePaidPromotionBeans.getLstDetailBeans(), getActivity());
		listdetail.setAdapter(getListPaymentDetailChargeAdapter);

		Button btnchon = (Button) dialogCuocdongtruoc
				.findViewById(R.id.btnchon);
		btnchon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogCuocdongtruoc.dismiss();
			}
		});

		dialogCuocdongtruoc.show();

	}
}
