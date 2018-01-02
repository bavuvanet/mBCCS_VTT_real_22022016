package com.viettel.bss.viettelpos.v4.charge.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.adapter.BankBeanAdapter;
import com.viettel.bss.viettelpos.v4.charge.object.BankBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Network;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentChargeDelBankPlus extends FragmentCommon {

	private Activity activity;

	private EditText edtIsdn;
	private Spinner spnStatus;
	private Button btnSearch;

	private final List<BankBean> lstBankBeanStatus = new ArrayList<>();

	private int isBankPlus = -1;

	private String isdn;
	private String status;

	@Override
	public void onResume() {
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.charge_del_bankplus);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_charge_del_bankplus;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	@Override
	public void unit(View v) {
		edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
		spnStatus = (Spinner) v.findViewById(R.id.spnStatus);
		btnSearch = (Button) v.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);

		AsynctaskCheckDeployBankPlus asynctaskCheckDeployBankPlus = new AsynctaskCheckDeployBankPlus(
				activity);
		asynctaskCheckDeployBankPlus.execute();

		// tuantd7();
	}

	private void tuantd7() {
		edtIsdn.setText("985357385");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		super.onItemClick(arg0, arg1, arg2, arg3);
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.btnSearch:
			if (CommonActivity.isNetworkConnected(activity)) {
				isdn = CommonActivity.checkStardardIsdn(edtIsdn.getText()
						.toString().trim());

				int pos_spnStatus = spnStatus.getSelectedItemPosition();
				String bankplus_value[] = getResources().getStringArray(
						R.array.bankplus_value);
				status = bankplus_value[pos_spnStatus];

				if (isdn.length() == 0) {
					CommonActivity
							.createAlertDialog(
									activity,
									getString(R.string.message_please_insert_phonenumber_contractid),
									getString(R.string.app_name)).show();
				} else {
					CommonActivity.hideKeyboard(btnSearch, activity);
					lstBankBeanStatus.clear();
					AsynctaskGetListRequestBankPlusMbccs async = new AsynctaskGetListRequestBankPlusMbccs(
							activity, isdn, status);
					async.execute();
				}
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}
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

	private class AsynctaskCheckDeployBankPlus extends
			AsyncTask<String, Void, Void> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		public AsynctaskCheckDeployBankPlus(Activity mActivity) {
			this.mActivity = mActivity;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			// this.progress = new ProgressDialog(mActivity);
			// this.progress.setCancelable(false);
			// this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			// if (!this.progress.isShowing()) {
			// this.progress.show();
			// }
		}

		@Override
		protected Void doInBackground(String... params) {
			return checkDeployBankPlus();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (progress != null && progress.isShowing()) {
				progress.dismiss();
			}
			if (errorCode.equals("0")) {
				if (isBankPlus == -1) {
					CommonActivity.createAlertDialog(mActivity,
							getString(R.string.ko_co_dl),
							getString(R.string.app_name)).show();
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

		private Void checkDeployBankPlus() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_checkDeployBankPlus");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:checkDeployBankPlus>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<clientIp>").append(Network.getLocalIpAddress()).append("</clientIp>");
				rawData.append("</input>");
				rawData.append("</ws:checkDeployBankPlus>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_checkDeployBankPlus");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d(this.getClass().getSimpleName(), "errorCode: "
							+ errorCode + " description: " + description);
				}
				if ("1".equalsIgnoreCase(description)) {
					isBankPlus = 1;
					Log.d(this.getClass().getSimpleName(), "isBankPlus: "
							+ isBankPlus + " phai su dung BankPlus: ");
				} else if ("0".equalsIgnoreCase(description)) {
					isBankPlus = 0;
					Log.d(this.getClass().getSimpleName(), "isBankPlus: "
							+ isBankPlus + " khong can BankPlus: ");
				} else {
					Log.d(this.getClass().getSimpleName(), "isBankPlus: "
							+ isBankPlus
							+ " kqt qua tra ve khong thay BankPlus: ");
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentChargeDelCTV checkDeployBankPlus",
						e);
			}
			return null;
		}
	}

	private Dialog dialog;
	private LoadMoreListView loadMoreListView;

	private void showDialogLoadMoreListView(ArrayAdapter adapter) {
		dialog = new Dialog(activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_listview);
		dialog.setCancelable(true);
		dialog.setTitle(getString(R.string.contract_payment_report));

		loadMoreListView = (LoadMoreListView) dialog
				.findViewById(R.id.loadMoreListView);
		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

		loadMoreListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		loadMoreListView
				.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {

					@Override
					public void onLoadMore() {
						Log.i(this.getClass().getSimpleName(),
								"loadMoreListView onLoadMore");
						AsynctaskGetListRequestBankPlusMbccs async = new AsynctaskGetListRequestBankPlusMbccs(
								activity, isdn, status);
						async.execute();
					}
				});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private class AsynctaskGetListRequestBankPlusMbccs extends
			AsyncTask<String, Void, List<BankBean>> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		private final String isdn;
		private final String status;

		public AsynctaskGetListRequestBankPlusMbccs(Activity mActivity,
				String isdn, String status) {
			this.mActivity = mActivity;
			this.isdn = isdn;
			this.status = status;
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
		protected List<BankBean> doInBackground(String... params) {
			return getListRequestBankPlusMbccs();
		}

		@Override
		protected void onPostExecute(List<BankBean> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (result == null || result.isEmpty()) {
					CommonActivity.createAlertDialog(mActivity, description,
							getString(R.string.app_name)).show();
				} else {
					lstBankBeanStatus.addAll(result);
					BankBeanAdapter adapter = new BankBeanAdapter(mActivity,
							lstBankBeanStatus);
					if (dialog == null) {
						showDialogLoadMoreListView(adapter);
					} else if (!dialog.isShowing()) {
						showDialogLoadMoreListView(adapter);
					} else {
						loadMoreListView.setAdapter(adapter);
					}
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

		private List<BankBean> getListRequestBankPlusMbccs() {
			List<BankBean> list = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getListRequestBankPlusMbccs");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListRequestBankPlusMbccs>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<isdn>").append(isdn).append("</isdn>");
				rawData.append("<status>").append(status).append("</status>");
				rawData.append("<pageIndex>").append(lstBankBeanStatus.size()).append("</pageIndex>");
				// rawData.append("<pageSize>" + lstBankBeanStatus.size() + 2 +
				// "</pageSize>");
				rawData.append("<pageSize>").append(lstBankBeanStatus.size()).append(Constant.PAGE_SIZE).append("</pageSize>");
				rawData.append("</input>");
				rawData.append("</ws:getListRequestBankPlusMbccs>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListRequestBankPlusMbccs");
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
						BankBean bankBean = new BankBean();
						bankBean.setBankPlusId(Long.parseLong(parse.getValue(
								e1, "bankPlusId")));
						bankBean.setIsdnBankPlus(parse.getValue(e1,
								"isdnBankPlus"));
						bankBean.setPaymentAmount(parse.getValue(e1,
								"paymentAmount"));
						bankBean.setCreateDate(parse.getValue(e1, "createDate"));
						bankBean.setReponseDate(parse.getValue(e1,
								"reponseDate"));
						bankBean.setBankCode(parse.getValue(e1, "bankCode"));
						bankBean.setTypePayment(parse.getValue(e1,
								"typePayment"));
						bankBean.setStatus(parse.getValue(e1, "status"));
						list.add(bankBean);
					}
				}
			} catch (Exception e) {
				Log.e(Constant.TAG,
						"FragmentChargeDelBankPlus getListRequestBankPlusMbccs",
						e);
			}
			return list;
		}
	}

	@Override
	public void setPermission() {
		permission = ";pm_payment_ctv;";

	}
}
