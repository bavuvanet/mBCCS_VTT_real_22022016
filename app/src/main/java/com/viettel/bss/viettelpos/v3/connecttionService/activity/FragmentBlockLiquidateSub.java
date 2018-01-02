package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Network;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListCusAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSub;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.RevokeAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.RevokeAdapter.OnCancelRevoke;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.RevokeAdapter.OnCheckChange;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubStockModelRel;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetServiceDal;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.TaskAssignBO;

import android.app.ActionBar;
import android.app.Activity;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class FragmentBlockLiquidateSub extends Fragment implements
		OnClickListener, OnCancelRevoke, OnCheckChange {

	private View mView = null;

	private Spinner spnService, spn_reason_fail, spn_reason_thuhoi;
	private EditText edt_account;
	private LinearLayout lnItem;
	private ExpandableHeightListView lvItem;
	private Button btn_apply, btn_cancel;
	private ImageButton btn_search;
	private ArrayList<Spin> lstReason = new ArrayList<Spin>();
	private ArrayList<Spin> lstReasonthuhoi = new ArrayList<Spin>();
	private ArrayList<TelecomServiceBeans> arrTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();

	private ProgressBar prbreason, prbreasonthuhoi;
	private ArrayList<SubStockModelRel> lstStockModelRels = new ArrayList<SubStockModelRel>();
	private RevokeAdapter revokeAdapter = null;

	private String serviceType = "";

	private LinearLayout lncheckcharge, lncharge;
	private Button btn_checkcharge, btn_collectioncharge;

	private ArrayList<CustommerByIdNoBean> arrCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();

	private GetListCusAdapter adaGetListCusAdapter;

	private ListView lvCustomer;

	private InfoSub infoSub = null;
	private List<Spin> lstBankBean;

	private ArrayList<SubStockModelRel> lstStockModelRelsNotSerial = new ArrayList<SubStockModelRel>();

	private TaskAssignBO taskAssignBO;

	private LinearLayout lnthuhoi;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Bundle mBundle = getArguments();
		if (mBundle != null) {
			taskAssignBO = (TaskAssignBO) mBundle
					.getSerializable("taskAssignBO");
			if (taskAssignBO != null) {
				serviceType = taskAssignBO.getServiceType();
			}
		}

		if (mView == null) {
			mView = inflater.inflate(R.layout.activity_block_liquidate_sub,
					container, false);
			unitView(mView);
		}
		return mView;
	}

	private void unitView(View v) {

		lnthuhoi = (LinearLayout) v.findViewById(R.id.lnthuhoi);
		lvCustomer = (ListView) v.findViewById(R.id.lvCus);
		lvCustomer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (validateSearch()) {
					lnItem.setVisibility(View.GONE);
					lstStockModelRels = new ArrayList<SubStockModelRel>();
					lstStockModelRelsNotSerial = new ArrayList<SubStockModelRel>();
					if (revokeAdapter != null) {
						revokeAdapter.notifyDataSetChanged();
					}

					lncheckcharge.setVisibility(View.VISIBLE);
					btn_checkcharge.setVisibility(View.VISIBLE);
					btn_collectioncharge.setVisibility(View.GONE);
					lncharge.setVisibility(View.GONE);

					if (CommonActivity.isNetworkConnected(getActivity())) {
						AsynGetReclaimStockModel asGetReclaimStockModel = new AsynGetReclaimStockModel(
								getActivity());
						asGetReclaimStockModel.execute(edt_account.getText()
								.toString().trim(), serviceType);

					} else {
						CommonActivity.createAlertDialog(getActivity(),
								getActivity().getString(R.string.errorNetwork),
								getActivity().getString(R.string.app_name))
								.show();
					}
				}
			}
		});

		lncheckcharge = (LinearLayout) v.findViewById(R.id.lncheckcharge);
		lncharge = (LinearLayout) v.findViewById(R.id.lncharge);
		lncharge.setVisibility(View.GONE);
		btn_checkcharge = (Button) v.findViewById(R.id.btn_checkcharge);
		btn_checkcharge.setOnClickListener(this);
		btn_collectioncharge = (Button) v
				.findViewById(R.id.btn_collectioncharge);
		btn_collectioncharge.setVisibility(View.GONE);
		btn_collectioncharge.setOnClickListener(this);
		btn_search = (ImageButton) v.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		spnService = (Spinner) v.findViewById(R.id.spnService);
		initTelecomService();
		spn_reason_fail = (Spinner) v.findViewById(R.id.spn_reason_fail);
		spn_reason_thuhoi = (Spinner) v.findViewById(R.id.spn_reason_thuhoi);
		edt_account = (EditText) v.findViewById(R.id.edt_account);

		if (taskAssignBO != null) {
			edt_account.setText(taskAssignBO.getAccount());
		}
		edt_account.setEnabled(false);

		lnItem = (LinearLayout) v.findViewById(R.id.lnItem);
		lnItem.setVisibility(View.GONE);
		lvItem = (ExpandableHeightListView) v.findViewById(R.id.lvItem);
		lvItem.setExpanded(true);
		// lvItem.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		//
		//
		// }
		// });
		btn_apply = (Button) v.findViewById(R.id.btn_apply);
		btn_apply.setOnClickListener(this);
		btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);

		prbreason = (ProgressBar) v.findViewById(R.id.prbreason);
		prbreasonthuhoi = (ProgressBar) v.findViewById(R.id.prbreasonthuhoi);

		if (CommonActivity.isNetworkConnected(getActivity())) {

			if (taskAssignBO != null
					&& !CommonActivity.isNullOrEmpty(serviceType)
					&& !CommonActivity.isNullOrEmpty(taskAssignBO.getAccount())) {
				AsynGetInfoSubscriberByAccountAndServiceType getListCustomerAsyn = new AsynGetInfoSubscriberByAccountAndServiceType(
						getActivity());
				getListCustomerAsyn.execute(edt_account.getText().toString()
						.trim(), serviceType, "1");
			}

			AsyntaskGetReasonPos asynGetReason = new AsyntaskGetReasonPos(
					getActivity(), "03");
			asynGetReason.execute();

			AsyntaskGetReasonPosThuhoi asynGetReason2 = new AsyntaskGetReasonPosThuhoi(
					getActivity(), "553");
			asynGetReason2.execute();

		}

		spnService.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arrTelecomServiceBeans != null
						&& !arrTelecomServiceBeans.isEmpty()) {

					serviceType = arrTelecomServiceBeans.get(arg2)
							.getServiceAlias();
					arrCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();
					if (adaGetListCusAdapter != null) {
						adaGetListCusAdapter.notifyDataSetChanged();
					}
					lstStockModelRels = new ArrayList<SubStockModelRel>();
					lstStockModelRelsNotSerial = new ArrayList<SubStockModelRel>();
					if (revokeAdapter != null) {
						revokeAdapter.notifyDataSetChanged();
					}
					lnItem.setVisibility(View.GONE);
					// if (!CommonActivity.isNullOrEmpty(serviceType)
					// && !CommonActivity.isNullOrEmpty(edt_account
					// .getText().toString().trim())) {
					//
					// lstStockModelRels = new ArrayList<SubStockModelRel>();
					// if (revokeAdapter != null) {
					// revokeAdapter.notifyDataSetChanged();
					// }
					//
					// AsynGetReclaimStockModel asGetReclaimStockModel = new
					// AsynGetReclaimStockModel(
					// getActivity());
					// asGetReclaimStockModel.execute(edt_account.getText()
					// .toString().trim(), serviceType);
					// }

					// if (!CommonActivity.isNullOrEmpty(serviceType)) {
					// AsyntaskGetReasonPos asynGetReason = new
					// AsyntaskGetReasonPos(
					// getActivity());
					// asynGetReason.execute(serviceType,"03");
					// }
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

	private class AsynctaskGetListBank extends
			AsyncTask<String, Void, List<Spin>> {

		private Activity mActivity;
		private XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsynctaskGetListBank(Activity mActivity) {
			this.mActivity = mActivity;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected List<Spin> doInBackground(String... params) {
			return getListBanks();
		}

		@Override
		protected void onPostExecute(List<Spin> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.isEmpty()) {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.notnganhang),
							getString(R.string.app_name)).show();
				} else {
					Spin spin = new Spin("", getString(R.string.txt_select));
					lstBankBean = new ArrayList<Spin>();
					lstBankBean.add(spin);
					lstBankBean.addAll(result);

					Utils.setDataSpinner(getActivity(), lstBankBean,
							spnBankCode); // danh sach ngan hang
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private List<Spin> getListBanks() {
			List<Spin> list = new ArrayList<Spin>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListBanks");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListBanks>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<clientIp>" + Network.getLocalIpAddress()
						+ "</clientIp>");
				rawData.append("</input>");
				rawData.append("</ws:getListBanks>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListBanks");
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

					nodechild = doc.getElementsByTagName("lstBankBean");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();
						spin.setId(parse.getValue(e1, "code"));
						spin.setValue(parse.getValue(e1, "name"));

						list.add(spin);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentChargeDelCTV getListBanks", e);
			}
			return list;
		}
	}

	private Dialog dialogPayCharge = null;
	private Spinner spnBankCode = null;

	private void showDialogPayCharge(final InfoSub infoSub, String money) {

		dialogPayCharge = new Dialog(getActivity());
		dialogPayCharge.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogPayCharge.setContentView(R.layout.layout_charge);

		final EditText edtAmountPayment = (EditText) dialogPayCharge
				.findViewById(R.id.edtAmountPayment);

		if (!CommonActivity.isNullOrEmpty(money)) {
			edtAmountPayment.setText(money);
		}

		spnBankCode = (Spinner) dialogPayCharge.findViewById(R.id.spnBankCode);
		if (CommonActivity.isNetworkConnected(getActivity())) {
			AsynctaskGetListBank asynctaskGetListBank = new AsynctaskGetListBank(
					getActivity());
			asynctaskGetListBank.execute();
		}

		Button btnChargeDel = (Button) dialogPayCharge
				.findViewById(R.id.btnChargeDel);

		btnChargeDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String acountPayment = edt_account.getText().toString().trim();
				Log.d(Constant.TAG, "btnChargeDel onClick amountPayment:"
						+ acountPayment);
				final Spin spin = (Spin) spnBankCode.getSelectedItem();

				final String money = edtAmountPayment.getText().toString()
						.trim();

				OnClickListener paymentClick = new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						// private String paymentContractRequest(String
						// contractId,
						// String amountCharge, String bankCode, String
						// isdnContract,
						// String customerName) {

						if (!CommonActivity.isNullOrEmpty(infoSub
								.getTelMobileContract())) {

							AsynctaskPaymentContract asynctaskPaymentContract = new AsynctaskPaymentContract(
									getActivity());
							asynctaskPaymentContract.execute(infoSub
									.getContractId(), money, spin.getId(),
									infoSub.getTelMobileContract(), infoSub
											.getCustommerByIdNoBean()
											.getNameCustomer());
						} else {
							AsynctaskPaymentContract asynctaskPaymentContract = new AsynctaskPaymentContract(
									getActivity());
							asynctaskPaymentContract.execute(infoSub
									.getContractId(), money, spin.getId(),
									infoSub.getTelFaxContract(), infoSub
											.getCustommerByIdNoBean()
											.getNameCustomer());
						}
					}
				};

				if (CommonActivity.isNullOrEmpty(acountPayment)) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.accountempty),
							getString(R.string.app_name)).show();
				} else if (spin == null) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.require_bankplus),
							getActivity().getString(R.string.app_name)).show();
				} else if (CommonActivity.isNullOrEmpty(spin.getId())) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.require_bankplus),
							getActivity().getString(R.string.app_name)).show();
				} else if (CommonActivity.isNullOrEmpty(money)) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.notsotien),
							getActivity().getString(R.string.app_name)).show();
				} else if (Long.parseLong(money) < 0) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.sotienlonhon0),
							getActivity().getString(R.string.app_name)).show();
				} else {
					String amount = StringUtils.formatMoney(money);
					CommonActivity.createDialog(
							getActivity(),
							getActivity().getString(
									R.string.message_confirm_payment)
									+ " "
									+ amount
									+ getActivity().getString(R.string.vnd),
							getActivity().getString(R.string.app_name),
							getActivity().getString(R.string.say_ko),
							getActivity().getString(R.string.say_co), null,
							paymentClick).show();

				}

			}
		});

		Button btnOut = (Button) dialogPayCharge.findViewById(R.id.btnOut);
		btnOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogPayCharge.dismiss();
			}
		});

		dialogPayCharge.show();

	}

	private Dialog dialogInsertSerial = null;
	private EditText edtserial;

	private void showDialogInsertSerial(final SubStockModelRel subStockModelRel) {
		dialogInsertSerial = new Dialog(getActivity());
		dialogInsertSerial.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogInsertSerial.setContentView(R.layout.serial_dialog);
		final TextView texteror = (TextView) dialogInsertSerial
				.findViewById(R.id.texterror);
		edtserial = (EditText) dialogInsertSerial.findViewById(R.id.edtserial);
		edtserial.setText(subStockModelRel.getSerial());
		dialogInsertSerial.findViewById(R.id.btnOk).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (edtserial.getText() != null
								&& !edtserial.getText().toString().isEmpty()) {
							if (StringUtils.CheckCharSpecical(edtserial
									.getText().toString()) == false) {
								// set object hang hoa
								subStockModelRel.setNewSerial(edtserial
										.getText().toString().trim());
								if (revokeAdapter != null) {
									revokeAdapter.notifyDataSetChanged();
								}
								dialogInsertSerial.cancel();
							} else {
								texteror.setVisibility(View.VISIBLE);
								texteror.setText(getString(R.string.checkspecialSerial));
							}
						} else {
							texteror.setVisibility(View.VISIBLE);
							texteror.setText(getString(R.string.checkserial));
						}

					}
				});
		dialogInsertSerial.findViewById(R.id.btnViewSaleTrans)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialogInsertSerial.cancel();
					}
				});
		dialogInsertSerial.show();

	}

	// lay dich vu
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

			if (!CommonActivity.isNullOrEmpty(serviceType)) {

				for (TelecomServiceBeans item : arrTelecomServiceBeans) {
					if (serviceType.equalsIgnoreCase(item.getServiceAlias())) {
						spnService.setSelection(arrTelecomServiceBeans
								.indexOf(item));
						break;
					}
				}

			}
			spnService.setEnabled(false);
		} catch (Exception e) {
			Log.e("initTypePaper", e.toString());
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		addActionBar();
	}

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

	// ham lay thong tin thue bao
	private class AsynGetInfoSubscriberByAccountAndServiceType extends
			AsyncTask<String, Void, ArrayList<InfoSub>> {

		ProgressDialog progress;
		private Context context = null;
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
		protected ArrayList<InfoSub> doInBackground(String... arg0) {
			return getInfoSubscriberByAccountAndServiceType(arg0[0], arg0[1],
					arg0[2]);
		}

		@Override
		protected void onPostExecute(ArrayList<InfoSub> result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (result != null && result.size() > 0) {

					infoSub = result.get(0);

					arrCustommerByIdNoBeans.add(result.get(0)
							.getCustommerByIdNoBean());

					adaGetListCusAdapter = new GetListCusAdapter(
							arrCustommerByIdNoBeans, getActivity());
					lvCustomer.setAdapter(adaGetListCusAdapter);
					// btnnhapmoi.setVisibility(View.GONE);
				} else {
					// btnnhapmoi.setVisibility(View.VISIBLE);

					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), description, getResources()
										.getString(R.string.app_name));
						dialog.show();
					} else {

						arrCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();

						adaGetListCusAdapter = new GetListCusAdapter(
								arrCustommerByIdNoBeans, getActivity());
						lvCustomer.setAdapter(adaGetListCusAdapter);

						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.notkh),
								getResources().getString(R.string.app_name));
						dialog.show();
					}

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

		private ArrayList<InfoSub> getInfoSubscriberByAccountAndServiceType(
				String account, String serviceType, String isChangeFraRequest) {
			ArrayList<InfoSub> arrInfoSubs = new ArrayList<InfoSub>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				// mbccs_getInfoSubscriberByAccountAndServiceType
				input.addValidateGateway("wscode",
						"mbccs_getInfoSubscriberByAccountAndServiceType");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getInfoSubscriberByAccountAndServiceType>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");
				rawData.append("<isGetCust>" + true + "</isGetCust>");
				// check xem co yeu cau chuyen doi cong nghe hay khong
				if ("1".equals(isChangeFraRequest)) {
					rawData.append("<isChangeFraRequest>" + true
							+ "</isChangeFraRequest>");
				} else {
					rawData.append("<isChangeFraRequest>" + false
							+ "</isChangeFraRequest>");
				}

				rawData.append("</input>");
				rawData.append("</ws:getInfoSubscriberByAccountAndServiceType>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);

				// String response = input.sendRequest(envelope,
				// Constant.BCCS_GW_URL, getActivity(),
				// "mbccs_getInfoSubscriberByAccountAndServiceType");
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
				// for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(0);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				// lay ra thong tin thue bao
				nodechild = e2.getElementsByTagName("infoSub");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Node node = nodechild.item(j);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element e4 = (Element) node;
						InfoSub infoSub = new InfoSub();
						String account1 = parse.getValue(e4, "account");
						infoSub.setAccount(account1);

						NodeList nodeList = node.getChildNodes();
						for (int i = 0; i < nodeList.getLength(); i++) {
							Node tmp = nodeList.item(i);

							Element e10 = (Element) tmp;

							if (tmp.getNodeName().equals("contractId")) {
								infoSub.setContractId(tmp.getTextContent()
										.trim());
							}
							if (tmp.getNodeName().equals("telMobileContract")) {
								infoSub.setTelMobileContract(tmp
										.getTextContent().trim());
							}
							if (tmp.getNodeName().equals("telFaxContract")) {
								infoSub.setTelFaxContract(tmp.getTextContent()
										.trim());
							}
						}
						// lay thong tin khach hang doi voi truong hop chi co
						// thue bao cha
						NodeList nodechildCus1 = e4
								.getElementsByTagName("customer");
						for (int k = 0; k < nodechildCus1.getLength(); k++) {
							Element e1 = (Element) nodechildCus1.item(k);
							CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
							String address = parse.getValue(e1, "address");
							Log.d("address", address);
							custommerByIdNoBean.setAddreseCus(address);
							String custId = parse.getValue(e1, "custId");
							Log.d("custId", custId);
							custommerByIdNoBean.setCustId(custId);
							String nameCus = parse.getValue(e1, "name");
							Log.d("nameCus", nameCus);
							custommerByIdNoBean.setNameCustomer(nameCus);
							String idNoCus = parse.getValue(e1, "idNo");
							Log.d("idNo", idNoCus);
							String tin = parse.getValue(e1, "tin");
							custommerByIdNoBean.setTin(tin);
							custommerByIdNoBean.setIdNo(idNoCus);
							String busPermitNo1 = parse.getValue(e1,
									"busPermitNo");
							Log.d("busPermitNo1", busPermitNo1);
							if (CommonActivity.isNullOrEmpty(idNoCus)) {
								custommerByIdNoBean
										.setBusPermitNo(busPermitNo1);
							}
							String birthDate = parse.getValue(e1, "birthDate");
							Log.d("birthDate", birthDate);
							custommerByIdNoBean.setBirthdayCus(StringUtils
									.convertDate(birthDate));

							String busType = parse.getValue(e1, "busType");
							Log.d("busType", busType);
							custommerByIdNoBean.setCusType(busType);

							String custGroupId = parse.getValue(e1,
									"custGroupId");
							Log.d("custGroupId", custGroupId);

							custommerByIdNoBean.setProvince(parse.getValue(e1,
									"province"));
							custommerByIdNoBean.setPrecint(parse.getValue(e1,
									"precinct"));
							custommerByIdNoBean.setDistrict(parse.getValue(e1,
									"district"));

							custommerByIdNoBean.setIdType(parse.getValue(e1,
									"idType"));
							custommerByIdNoBean.setIdIssueDate(StringUtils
									.convertDate(parse.getValue(e1,
											"idIssueDate")));
							custommerByIdNoBean.setIdIssuePlace(parse.getValue(
									e1, "idIssuePlace"));

							custommerByIdNoBean.setCusGroupId(custGroupId);
							NodeList nodeCusAttribute = e1
									.getElementsByTagName("customerAttribute");
							for (int l = 0; l < nodeCusAttribute.getLength(); l++) {
								Element e3 = (Element) nodeCusAttribute.item(l);
								String birthDateAtt = parse.getValue(e3,
										"birthDate");
								custommerByIdNoBean
										.getCustomerAttribute()
										.setBirthDate(
												StringUtils
														.convertDate(birthDateAtt));
								custommerByIdNoBean
										.getCustomerAttribute()
										.setCustId(parse.getValue(e3, "custId"));
								custommerByIdNoBean.getCustomerAttribute()
										.setId(parse.getValue(e3, "id"));
								custommerByIdNoBean
										.getCustomerAttribute()
										.setIdType(parse.getValue(e3, "idType"));
								custommerByIdNoBean.getCustomerAttribute()
										.setIdNo(parse.getValue(e3, "idNo"));
								custommerByIdNoBean.getCustomerAttribute()
										.setIssueDate(
												StringUtils.convertDate(parse
														.getValue(e3,
																"issueDate")));
								custommerByIdNoBean.getCustomerAttribute()
										.setIssuePlace(
												parse.getValue(e3,
														"isssuePlace"));
								custommerByIdNoBean.getCustomerAttribute()
										.setName(parse.getValue(e3, "name"));
							}
							if (custommerByIdNoBean != null) {
								infoSub.setCustommerByIdNoBean(custommerByIdNoBean);
							}
						}

						arrInfoSubs.add(infoSub);
					}

				}
				// }

			} catch (Exception e) {
				e.printStackTrace();
			}
			return arrInfoSubs;
		}

	}

	public class GetListCustomerAsyn extends
			AsyncTask<String, Void, ArrayList<CustommerByIdNoBean>> {
		ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetListCustomerAsyn(Context context) {
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
		protected ArrayList<CustommerByIdNoBean> doInBackground(String... arg0) {
			return getListCustomerIdNo(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<CustommerByIdNoBean> result) {
			CommonActivity.hideKeyboard(edt_account, context);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					arrCustommerByIdNoBeans = result;

					adaGetListCusAdapter = new GetListCusAdapter(
							arrCustommerByIdNoBeans, getActivity());
					lvCustomer.setAdapter(adaGetListCusAdapter);
					// btnnhapmoi.setVisibility(View.GONE);
				} else {
					// btnnhapmoi.setVisibility(View.VISIBLE);

					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), description, getResources()
										.getString(R.string.app_name));
						dialog.show();
					} else {

						arrCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();

						adaGetListCusAdapter = new GetListCusAdapter(
								arrCustommerByIdNoBeans, getActivity());
						lvCustomer.setAdapter(adaGetListCusAdapter);

						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.notkh),
								getResources().getString(R.string.app_name));
						dialog.show();
					}

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

		private ArrayList<CustommerByIdNoBean> getListCustomerIdNo(
				String idsnOrAcc, String serviceType) {
			ArrayList<CustommerByIdNoBean> lisCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getCustomerByIdNo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getCustomerByIdNo>");
				rawData.append("<cmFixServiceInput>");
				HashMap<String, String> paramToken = new HashMap<String, String>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				// add tham so input
				if (serviceType != null && !serviceType.isEmpty()) {
					rawData.append("<serviceType>" + serviceType);
					rawData.append("</serviceType>");
				} else {
					// rawData.append("<serviceType>" + "");
					// rawData.append("</serviceType>");
				}
				if (idsnOrAcc != null && !idsnOrAcc.isEmpty()) {
					rawData.append("<account>" + idsnOrAcc);
					rawData.append("</account>");
				} else {
					// rawData.append("<account>" + "");
					// rawData.append("</account>");
				}

				rawData.append("</cmFixServiceInput>");
				rawData.append("</ws:getCustomerByIdNo>");
				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getCustomerByIdNo");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);
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
					nodechild = doc.getElementsByTagName("lstCustomer");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						CustommerByIdNoBean custommerByIdNoBean = new CustommerByIdNoBean();
						String address = parse.getValue(e1, "address");
						Log.d("address", address);
						custommerByIdNoBean.setAddreseCus(address);
						String custId = parse.getValue(e1, "custId");
						Log.d("custId", custId);
						custommerByIdNoBean.setCustId(custId);
						String nameCus = parse.getValue(e1, "name");
						Log.d("nameCus", nameCus);
						custommerByIdNoBean.setNameCustomer(nameCus);
						String idNoCus = parse.getValue(e1, "idNo");
						Log.d("idNo", idNoCus);
						String tin = parse.getValue(e1, "tin");
						custommerByIdNoBean.setTin(tin);
						custommerByIdNoBean.setIdNo(idNoCus);
						String busPermitNo1 = parse.getValue(e1, "busPermitNo");
						Log.d("busPermitNo1", busPermitNo1);
						custommerByIdNoBean.setBusPermitNo(busPermitNo1);

						String birthDate = parse.getValue(e1, "birthDate");
						Log.d("birthDate", birthDate);
						custommerByIdNoBean.setBirthdayCus(StringUtils
								.convertDate(birthDate));

						String busType = parse.getValue(e1, "busType");
						Log.d("busType", busType);
						custommerByIdNoBean.setCusType(busType);

						String custGroupId = parse.getValue(e1, "custGroupId");
						Log.d("custGroupId", custGroupId);

						custommerByIdNoBean.setProvince(parse.getValue(e1,
								"province"));
						custommerByIdNoBean.setPrecint(parse.getValue(e1,
								"precinct"));
						custommerByIdNoBean.setDistrict(parse.getValue(e1,
								"district"));

						custommerByIdNoBean.setIdType(parse.getValue(e1,
								"idType"));
						custommerByIdNoBean
								.setIdIssueDate(StringUtils.convertDate(parse
										.getValue(e1, "idIssueDate")));
						custommerByIdNoBean.setIdIssuePlace(parse.getValue(e1,
								"idIssuePlace"));

						custommerByIdNoBean.setCusGroupId(custGroupId);

						lisCustommerByIdNoBeans.add(custommerByIdNoBean);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lisCustommerByIdNoBeans;
		}

	}

	private class AsynctaskPaymentContract extends
			AsyncTask<String, Void, String> {

		private Activity mActivity;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ProgressDialog progress;

		public AsynctaskPaymentContract(Activity mActivity) {
			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.processing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			return paymentContractRequest(params[0], params[1], params[2],
					params[3], params[4]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			Log.d(Constant.TAG, "onPostExecute " + errorCode);
			if (errorCode.equals("0")) {
				btn_collectioncharge.setVisibility(View.GONE);
				lncharge.setVisibility(View.VISIBLE);
				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
						description, getString(R.string.app_name));
				dialog.show();

			} else {
				btn_collectioncharge.setVisibility(View.VISIBLE);
				lncharge.setVisibility(View.GONE);
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							mActivity,
							description,
							mActivity.getResources().getString(
									R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mActivity.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private String paymentContractRequest(String contractId,
				String amountCharge, String bankCode, String isdnContract,
				String customerName) {
			Log.d("Log", "begin");

			String original = "";

			try {
				Log.d("Log", "begin try payment");
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_paymentContract");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:paymentContract>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<contractId>" + contractId + "</contractId>");
				rawData.append("<amount>" + amountCharge + "</amount>");
				rawData.append("<clientIp>" + Network.getLocalIpAddress()
						+ "</clientIp>");
				rawData.append("<bankCode>" + bankCode + "</bankCode>");
				rawData.append("<isdnContract>" + isdnContract
						+ "</isdnContract>");
				rawData.append("<customerName>" + customerName
						+ "</customerName>");
				rawData.append("</input>");
				rawData.append("</ws:paymentContract>");

				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Log", "Send evelop " + envelope);
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_paymentContract");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);
				Log.d("Log", "Responseeeeeeeeee Original group " + response);

				// ==== parse xml list ip

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
				}
			} catch (Exception e) {
				Log.e("Log", e.toString(), e);
			}
			Log.d("Log", "end");
			return errorCode;
		}
	}

	private class AsynCollectionHotCharge extends
			AsyncTask<String, Void, String> {

		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ProgressDialog progress;
		String money = "";

		public AsynCollectionHotCharge(Context context) {
			this.context = context;
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
			return collectionHotCharge(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {

				OnClickListener showPopupClick = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						showDialogPayCharge(infoSub, money);
					}
				};

				lncheckcharge.setVisibility(View.GONE);
				btn_checkcharge.setVisibility(View.GONE);
				btn_collectioncharge.setVisibility(View.GONE);
				if (!CommonActivity.isNullOrEmpty(money)) {
					if (Long.parseLong(money) > 0) {
						CommonActivity.createAlertDialog(getActivity(),
								description,
								getActivity().getString(R.string.app_name),
								showPopupClick).show();
					} else {
						lncharge.setVisibility(View.VISIBLE);
						CommonActivity.createAlertDialog(getActivity(),
								description,
								getActivity().getString(R.string.app_name))
								.show();
					}
				} else {
					lncharge.setVisibility(View.VISIBLE);
					CommonActivity.createAlertDialog(getActivity(),
							description,
							getActivity().getString(R.string.app_name)).show();
				}

			} else {
				lncheckcharge.setVisibility(View.VISIBLE);
				btn_checkcharge.setVisibility(View.GONE);
				btn_collectioncharge.setVisibility(View.VISIBLE);
				lncharge.setVisibility(View.GONE);
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

		private String collectionHotCharge(String account, String serviceType) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_collectionHotCharge");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:collectionHotCharge>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");
				rawData.append("</input>");
				rawData.append("</ws:collectionHotCharge>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_collectionHotCharge");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					money = parse.getValue(e2, "money");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return errorCode;

		}
	}

	// ham check thong tin no cuoc
	private class AsyncheckCollectionHotCharge extends
			AsyncTask<String, Void, String> {

		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ProgressDialog progress;
		String money = "";

		public AsyncheckCollectionHotCharge(Context context) {
			this.context = context;
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
			return checkCollectionHotCharge(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {

				btn_checkcharge.setVisibility(View.GONE);
				btn_collectioncharge.setVisibility(View.GONE);
				lncheckcharge.setVisibility(View.GONE);

				OnClickListener showPopupClick = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						showDialogPayCharge(infoSub, money);
					}
				};

				if (!CommonActivity.isNullOrEmpty(money)) {
					if (Long.parseLong(money) > 0) {
						CommonActivity.createAlertDialog(
								getActivity(),
								getActivity().getString(
										R.string.checknocuocsucess)
										+ StringUtils.formatMoney(money),
								getActivity().getString(R.string.app_name),
								showPopupClick).show();
					} else {
						lncharge.setVisibility(View.VISIBLE);
						CommonActivity.createAlertDialog(
								getActivity(),
								getActivity().getString(
										R.string.checkchargesuccess),
								getActivity().getString(R.string.app_name))
								.show();
					}
				} else {
					lncharge.setVisibility(View.VISIBLE);
					CommonActivity.createAlertDialog(
							getActivity(),
							getActivity()
									.getString(R.string.checkchargesuccess),
							getActivity().getString(R.string.app_name)).show();
				}

				// if(!CommonActivity.isNullOrEmpty(money)){
				// if(Long.parseLong(money) > 0){
				// showDialogPayCharge(infoSub);
				// }else{
				// lncharge.setVisibility(View.VISIBLE);
				// CommonActivity.createAlertDialog(getActivity(),
				// getActivity().getString(R.string.checkchargesuccess),
				// getActivity().getString(R.string.app_name)).show();
				// }
				// }else{
				// lncharge.setVisibility(View.VISIBLE);
				// CommonActivity.createAlertDialog(getActivity(),
				// getActivity().getString(R.string.checkchargesuccess),
				// getActivity().getString(R.string.app_name)).show();
				// }
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

					btn_checkcharge.setVisibility(View.GONE);
					btn_collectioncharge.setVisibility(View.VISIBLE);

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

		private String checkCollectionHotCharge(String account,
				String serviceType) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_checkCollectionHotCharge");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:checkCollectionHotCharge>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");
				rawData.append("</input>");
				rawData.append("</ws:checkCollectionHotCharge>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_checkCollectionHotCharge");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					money = parse.getValue(e2, "money");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return errorCode;

		}
	}

	// Asyn thanh ly thue bao

	private class AsynBlockLiquidateSub extends AsyncTask<String, Void, String> {

		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ProgressDialog progress;
		ArrayList<SubStockModelRel> arrStockModelRels = new ArrayList<SubStockModelRel>();

		public AsynBlockLiquidateSub(Context context,
				ArrayList<SubStockModelRel> lstStockModelRels) {
			this.context = context;
			this.arrStockModelRels = lstStockModelRels;
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
			return blockLiquiSub(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equalsIgnoreCase(errorCode)) {
				
				String des = description;
				if(CommonActivity.isNullOrEmpty(description)){
					des = getActivity().getString(R.string.tlysuccess);
				}
				
				CommonActivity.createAlertDialog(getActivity(),
						des,
						getActivity().getString(R.string.app_name)).show();
				btn_apply.setVisibility(View.GONE);
//				edt_account.setText("");
//				if (lstStockModelRels != null && !lstStockModelRels.isEmpty()) {
//					lstStockModelRels = new ArrayList<SubStockModelRel>();
//				}
//				lstStockModelRelsNotSerial = new ArrayList<SubStockModelRel>();
//				if (revokeAdapter != null) {
//					revokeAdapter.notifyDataSetChanged();
//				}
//				lnItem.setVisibility(View.GONE);

			} else {
				btn_apply.setVisibility(View.VISIBLE);
//				lnItem.setVisibility(View.VISIBLE);
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

		private String blockLiquiSub(String account, String serviceType) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_blockLiquidateSub");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:blockLiquidateSub>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");

				Spin item = (Spin) spn_reason_fail.getSelectedItem();
				if (item != null && !CommonActivity.isNullOrEmpty(item.getId())) {
					rawData.append("<reasonId>" + item.getId() + "</reasonId>");
				}

				if (arrStockModelRels != null && !arrStockModelRels.isEmpty()) {
					for (SubStockModelRel itemRel : arrStockModelRels) {
						rawData.append("<lstSubStockModelRels>");
						rawData.append("<subStockModelRelId>"
								+ itemRel.getSubStockModelRelId());
						rawData.append("</subStockModelRelId>");
						rawData.append("<stockModelId>"
								+ itemRel.getStockModelId());
						rawData.append("</stockModelId>");
						rawData.append("<stockModelName>"
								+ itemRel.getStockModelName());
						rawData.append("</stockModelName>");
						rawData.append("<serial>" + itemRel.getSerial());
						rawData.append("</serial>");
						rawData.append("<serialNew>" + itemRel.getNewSerial());
						rawData.append("</serialNew>");
						rawData.append("</lstSubStockModelRels>");
					}
				}

				if (lstStockModelRelsNotSerial != null
						&& !lstStockModelRelsNotSerial.isEmpty()) {
					for (SubStockModelRel itemRel : lstStockModelRelsNotSerial) {
						rawData.append("<lstSubStockModelRelNoLiquit>");
						rawData.append("<subStockModelRelId>"
								+ itemRel.getSubStockModelRelId());
						rawData.append("</subStockModelRelId>");
						rawData.append("<stockModelId>"
								+ itemRel.getStockModelId());
						rawData.append("</stockModelId>");
						rawData.append("<stockModelName>"
								+ itemRel.getStockModelName());
						rawData.append("</stockModelName>");
						rawData.append("<serial>" + itemRel.getSerial());
						rawData.append("</serial>");
						rawData.append("<reasonId>" + itemRel.getReasonId());
						rawData.append("</reasonId>");
						rawData.append("</lstSubStockModelRelNoLiquit>");
					}
				}

				rawData.append("</input>");
				rawData.append("</ws:blockLiquidateSub>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_blockLiquidateSub");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return errorCode;

		}
	}

	// Asyn lay thong tin hang hoa can thanh ly
	private class AsynGetReclaimStockModel extends
			AsyncTask<String, Void, ArrayList<SubStockModelRel>> {

		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ProgressDialog progress;

		@Override
		protected ArrayList<SubStockModelRel> doInBackground(String... arg0) {
			return getLstSubStockModel(arg0[0], arg0[1]);
		}

		public AsynGetReclaimStockModel(Context context) {
			this.context = context;
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
		protected void onPostExecute(ArrayList<SubStockModelRel> result) {

			progress.dismiss();
			if ("0".equals(errorCode)) {
				if (result != null && !result.isEmpty()) {
					lnthuhoi.setVisibility(View.VISIBLE);
					lnItem.setVisibility(View.VISIBLE);
					lstStockModelRels = result;
					revokeAdapter = new RevokeAdapter(getActivity(),
							lstStockModelRels, FragmentBlockLiquidateSub.this,
							FragmentBlockLiquidateSub.this);
					lvItem.setAdapter(revokeAdapter);
				} else {
					lnthuhoi.setVisibility(View.GONE);
					lnItem.setVisibility(View.VISIBLE);
					lstStockModelRels = new ArrayList<SubStockModelRel>();
					lstStockModelRelsNotSerial = new ArrayList<SubStockModelRel>();
					if (revokeAdapter != null) {
						revokeAdapter.notifyDataSetChanged();
					}
					// CommonActivity.createAlertDialog(getActivity(),
					// getActivity().getString(R.string.no_data),
					// getActivity().getString(R.string.app_name)).show();

				}
			} else {
				lnthuhoi.setVisibility(View.GONE);
				lnItem.setVisibility(View.GONE);
				lstStockModelRels = new ArrayList<SubStockModelRel>();
				lstStockModelRelsNotSerial = new ArrayList<SubStockModelRel>();
				if (revokeAdapter != null) {
					revokeAdapter.notifyDataSetChanged();
				}
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {

					lnItem.setVisibility(View.VISIBLE);
					lstStockModelRels = new ArrayList<SubStockModelRel>();
					lstStockModelRelsNotSerial = new ArrayList<SubStockModelRel>();
					if (revokeAdapter != null) {
						revokeAdapter.notifyDataSetChanged();
					}

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

		private ArrayList<SubStockModelRel> getLstSubStockModel(String account,
				String serviceType) {

			ArrayList<SubStockModelRel> arrSubStockModelRels = new ArrayList<SubStockModelRel>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getReclaimStockModelSmartphone");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getReclaimStockModelSmartphone>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<account>" + account + "</account>");
				rawData.append("<serviceType>" + serviceType + "</serviceType>");
				rawData.append("</input>");
				rawData.append("</ws:getReclaimStockModelSmartphone>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getReclaimStockModelSmartphone");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc
							.getElementsByTagName("lstSubStockModelRels");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						SubStockModelRel subStockModelRel = new SubStockModelRel();
						String subStockModelRelId = parse.getValue(e1,
								"subStockModelRelId");
						subStockModelRel
								.setSubStockModelRelId(subStockModelRelId);
						String stockModelName = parse.getValue(e1,
								"stockModelName");
						subStockModelRel.setStockModelName(stockModelName);
						String serial = parse.getValue(e1, "serial");
						subStockModelRel.setSerial(serial);
						String stockModelId = parse
								.getValue(e1, "stockModelId");
						subStockModelRel.setStockModelId(stockModelId);
						arrSubStockModelRels.add(subStockModelRel);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arrSubStockModelRels;
		}
	}

	private class AsyntaskGetReasonPos extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		String actionCode = "";

		public AsyntaskGetReasonPos(Context context, String mactionCode) {
			this.context = context;
			this.actionCode = mactionCode;

			prbreason.setVisibility(View.VISIBLE);

			if (lstReason != null && lstReason.size() > 0) {
				lstReason.clear();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getReasonInfoPos();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {

			prbreason.setVisibility(View.GONE);

			if (errorCode.equalsIgnoreCase("0")) {
				try {
					lstReason.add(new Spin("-1", getActivity().getResources()
							.getString(R.string.txt_select_reason)));
					lstReason.addAll(result);
					Utils.setDataSpinner(getActivity(), lstReason,
							spn_reason_fail);
				} catch (Exception e) {

				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					lstReason.add(new Spin("-1", getActivity().getResources()
							.getString(R.string.txt_select_reason)));
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
					lstReason.add(new Spin("-1", context.getResources()
							.getString(R.string.txt_select_reason)));
					Utils.setDataSpinner(getActivity(), lstReason,
							spn_reason_fail);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Spin> getReasonInfoPos() {

			ArrayList<Spin> lstReason = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListReasonByTelServicePos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReasonByTelServicePos>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<actionCode>" + actionCode + "</actionCode>");
				// rawData.append("<serviceType>" + serviceType +
				// "</serviceType>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListReasonByTelServicePos>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListReasonByTelService");
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
				nodechild = doc.getElementsByTagName("lstReasonPos");
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

	private class AsyntaskGetReasonPosThuhoi extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		String actionCode = "";

		public AsyntaskGetReasonPosThuhoi(Context context, String mactionCode) {
			this.context = context;
			this.actionCode = mactionCode;

			prbreasonthuhoi.setVisibility(View.VISIBLE);

			if (lstReasonthuhoi != null && lstReasonthuhoi.size() > 0) {
				lstReasonthuhoi.clear();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getReasonInfoPos();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {

			prbreasonthuhoi.setVisibility(View.GONE);

			if (errorCode.equalsIgnoreCase("0")) {
				try {
					lstReasonthuhoi.add(new Spin("-1", getActivity()
							.getResources().getString(
									R.string.txt_select_reason)));
					lstReasonthuhoi.addAll(result);
					Utils.setDataSpinner(getActivity(), lstReasonthuhoi,
							spn_reason_thuhoi);
				} catch (Exception e) {

				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					lstReasonthuhoi.add(new Spin("-1", getActivity()
							.getResources().getString(
									R.string.txt_select_reason)));
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
					lstReasonthuhoi.add(new Spin("-1", context.getResources()
							.getString(R.string.txt_select_reason)));
					Utils.setDataSpinner(getActivity(), lstReasonthuhoi,
							spn_reason_fail);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Spin> getReasonInfoPos() {

			ArrayList<Spin> lstReason = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListReasonByTelServicePos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReasonByTelServicePos>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<actionCode>" + actionCode + "</actionCode>");
				// rawData.append("<serviceType>" + serviceType +
				// "</serviceType>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListReasonByTelServicePos>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListReasonByTelService");
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
				nodechild = doc.getElementsByTagName("lstReasonPos");
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
		txtNameActionBar.setText(getResources().getString(
				R.string.thanhlythuebao));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		case R.id.btn_search:
			if (validateSearch()) {

				// lnItem.setVisibility(View.GONE);
				// lstStockModelRels = new ArrayList<SubStockModelRel>();
				// if (revokeAdapter != null) {
				// revokeAdapter.notifyDataSetChanged();
				// }
				//
				// lncheckcharge.setVisibility(View.VISIBLE);
				// btn_checkcharge.setVisibility(View.VISIBLE);
				// btn_collectioncharge.setVisibility(View.GONE);
				// lncharge.setVisibility(View.GONE);
				//
				arrCustommerByIdNoBeans = new ArrayList<CustommerByIdNoBean>();
				if (adaGetListCusAdapter != null) {
					adaGetListCusAdapter.notifyDataSetChanged();
				}

				lnItem.setVisibility(View.GONE);
				lstStockModelRels = new ArrayList<SubStockModelRel>();
				if (revokeAdapter != null) {
					revokeAdapter.notifyDataSetChanged();
				}

				lncheckcharge.setVisibility(View.VISIBLE);
				btn_checkcharge.setVisibility(View.VISIBLE);
				btn_collectioncharge.setVisibility(View.GONE);
				lncharge.setVisibility(View.GONE);
				if (CommonActivity.isNetworkConnected(getActivity())) {
					AsynGetInfoSubscriberByAccountAndServiceType getListCustomerAsyn = new AsynGetInfoSubscriberByAccountAndServiceType(
							getActivity());
					getListCustomerAsyn.execute(edt_account.getText()
							.toString().trim(), serviceType, "1");

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.errorNetwork),
							getActivity().getString(R.string.app_name)).show();
				}
			}
			break;
		case R.id.btn_checkcharge:
			if (validateSearch()) {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					AsyncheckCollectionHotCharge asGetReclaimStockModel = new AsyncheckCollectionHotCharge(
							getActivity());
					asGetReclaimStockModel.execute(edt_account.getText()
							.toString().trim(), serviceType);

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.errorNetwork),
							getActivity().getString(R.string.app_name)).show();
				}
			}
			break;

		case R.id.btn_collectioncharge:
			if (validateSearch()) {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					AsynCollectionHotCharge asGetReclaimStockModel = new AsynCollectionHotCharge(
							getActivity());
					asGetReclaimStockModel.execute(edt_account.getText()
							.toString().trim(), serviceType);

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.errorNetwork),
							getActivity().getString(R.string.app_name)).show();
				}
			}
			break;

		case R.id.btn_apply:

			if (validateBlock()) {
				final ArrayList<SubStockModelRel> lstItem = new ArrayList<SubStockModelRel>();
				if (lstStockModelRels != null && !lstStockModelRels.isEmpty()) {
					for (SubStockModelRel subStockModelRel : lstStockModelRels) {
						if (!CommonActivity.isNullOrEmpty(subStockModelRel
								.getNewSerial())) {
							lstItem.add(subStockModelRel);
						}
					}
				}

				// if (lstItem == null || lstItem.isEmpty()) {
				//
				// CommonActivity.createAlertDialog(getActivity(),
				// getActivity().getString(R.string.checkserialtl),
				// getActivity().getString(R.string.app_name)).show();
				//
				// } else {
				OnClickListener onclickBlockLiquiSub = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						AsynBlockLiquidateSub asynBlockLiquidateSub = new AsynBlockLiquidateSub(
								getActivity(), lstItem);
						asynBlockLiquidateSub.execute(edt_account.getText()
								.toString().trim(), serviceType);
					}
				};

				CommonActivity.createDialog(getActivity(),
						getActivity().getString(R.string.confirmthanhly),
						getActivity().getString(R.string.app_name),
						getActivity().getString(R.string.ok),
						getActivity().getString(R.string.cancel),
						onclickBlockLiquiSub, null).show();

			}
			// }

			break;
		case R.id.btn_cancel:
			getActivity().onBackPressed();
			break;

		default:
			break;
		}

	}

	// validate update

	private boolean validateBlock() {

		if (CommonActivity.isNullOrEmpty(serviceType)) {
			// if (CommonActivity.isNullOrEmpty(edt_account.getText().toString()
			// .trim())) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.checkserviceType),
					getActivity().getString(R.string.app_name)).show();
			return false;
			// }
		}
		if (CommonActivity.isNullOrEmpty(edt_account.getText().toString()
				.trim())) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.accountisempty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		Spin item = (Spin) spn_reason_fail.getSelectedItem();
		if (item == null) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.checkreasontly),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		if (CommonActivity.isNullOrEmpty(item.getId())) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.checkreasontly),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}
		if (item.getId().equals("-1")) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.checkreasontly),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		lstStockModelRelsNotSerial = new ArrayList<SubStockModelRel>();
		
		if (lstStockModelRels != null && !lstStockModelRels.isEmpty()) {
			for (SubStockModelRel subStockModelRel : lstStockModelRels) {
				if (!CommonActivity.isNullOrEmpty(subStockModelRel
						.getNewSerial())) {
				} else {
					lstStockModelRelsNotSerial.add(subStockModelRel);
				}
			}
		}

		Spin spinSerialThuhoi = (Spin) spn_reason_thuhoi.getSelectedItem();

		if (lstStockModelRelsNotSerial != null
				&& !lstStockModelRelsNotSerial.isEmpty()) {
			if (spinSerialThuhoi == null) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.checkreasonthuhoi),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}

			if (CommonActivity.isNullOrEmpty(spinSerialThuhoi.getId())) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.checkreasonthuhoi),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if ("-1".equals(spinSerialThuhoi.getId())) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.checkreasonthuhoi),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			for (SubStockModelRel subStockModelRel : lstStockModelRelsNotSerial) {
				subStockModelRel.setReasonId(spinSerialThuhoi.getId());
			}

		}

		return true;
	}

	// validate tim kiem thong tin account
	private boolean validateSearch() {
		if (CommonActivity.isNullOrEmpty(serviceType)) {
			// if (CommonActivity.isNullOrEmpty(edt_account.getText().toString()
			// .trim())) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.checkserviceType),
					getActivity().getString(R.string.app_name)).show();
			return false;
			// }
		}
		if (CommonActivity.isNullOrEmpty(edt_account.getText().toString()
				.trim())) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.accountisempty),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		return true;
	}

	@Override
	public void onCancelRevoke(SubStockModelRel subStockModelRel) {
		if (lstStockModelRels != null && !lstStockModelRels.isEmpty()) {
			if (subStockModelRel != null
					&& !CommonActivity.isNullOrEmpty(subStockModelRel
							.getNewSerial())) {
				for (SubStockModelRel subItem : lstStockModelRels) {
					if (subItem.getSubStockModelRelId().equals(
							subStockModelRel.getSubStockModelRelId())) {
						subItem.setNewSerial("");
						break;
					}
				}
			}
			if (revokeAdapter != null) {
				revokeAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onCheckChange(SubStockModelRel subStockModelRel) {
		showDialogInsertSerial(subStockModelRel);

	}

}
