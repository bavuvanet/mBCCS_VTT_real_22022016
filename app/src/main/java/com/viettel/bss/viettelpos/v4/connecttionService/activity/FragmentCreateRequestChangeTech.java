package com.viettel.bss.viettelpos.v4.connecttionService.activity;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListReqCreateSubChildAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetListReqCreateSubChildAdapter.OnCancelInfoSubChild;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSub;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSubChild;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class FragmentCreateRequestChangeTech extends Fragment implements
		OnClickListener, OnCancelInfoSubChild {

	private Spinner spn_technew, spn_reason_fail;

    private View mView = null;

	private ProgressBar prbreason, prbProunit;
	private ArrayList<Spin> lstReason = new ArrayList<>();

	private ArrayList<Spin> lstTech = new ArrayList<>();

	private String account = "";
	private String serviceType = "";

	private InfoSub infoSub = null;

	private ArrayList<InfoSubChild> arrInfoSubChilds = new ArrayList<>();

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        Bundle mBundle = getArguments();
		if (mBundle != null) {
			InfoSub item = (InfoSub) mBundle.getSerializable("INFOSUBKEY");
			serviceType = mBundle.getString("serviceTypeKey", "");
			account = mBundle.getString("accountKey", "");
			if (item != null) {
				arrInfoSubChilds = item.getLstInfoSubChilds();
			}
		}

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_change_technology,
					container, false);
			unitView(mView);
		}
		return mView;
	}

	private void unitView(View mView2) {

		prbProunit = (ProgressBar) mView2.findViewById(R.id.prbProunit);
		prbreason = (ProgressBar) mView2.findViewById(R.id.prbreason);

		spn_technew = (Spinner) mView2.findViewById(R.id.spn_technew);
		spn_reason_fail = (Spinner) mView2.findViewById(R.id.spn_reason_fail);
        Button btn_apply = (Button) mView2.findViewById(R.id.btn_apply);
		btn_apply.setOnClickListener(this);

		if (CommonActivity.isNetworkConnected(getActivity())) {

			if (!CommonActivity.isNullOrEmpty(account)
					&& !CommonActivity.isNullOrEmpty(serviceType)) {
				AsyntaskGetInfraTypeList asyntaskGetInfraTypeList = new AsyntaskGetInfraTypeList(
						getActivity());
				asyntaskGetInfraTypeList.execute(account, serviceType);
			}

			if (!CommonActivity.isNullOrEmpty(serviceType)) {
				AsyntaskGetReasonPos asyntaskGetReasonPos = new AsyntaskGetReasonPos(
						getActivity());
				asyntaskGetReasonPos.execute(serviceType);
			}

		} else {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}

	}

	private boolean validateCreateRequest() {

		Spin itemInfra = (Spin) spn_technew.getSelectedItem();

		if (itemInfra == null) {

			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkinfra),
					getString(R.string.app_name)).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(itemInfra.getId())
				|| "-1".equals(itemInfra.getId())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkinfra),
					getString(R.string.app_name)).show();
			return false;
		}

		Spin itemReason = (Spin) spn_reason_fail.getSelectedItem();
		if (itemReason == null) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkreasonCN),
					getString(R.string.app_name)).show();
			return false;
		}

		if (CommonActivity.isNullOrEmpty(itemReason.getId())
				|| "-1".equals(itemReason.getId())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.checkreasonCN),
					getString(R.string.app_name)).show();
			return false;
		}

		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.changeTechnology);
	}

	private class AsyntaskGetInfraTypeList extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetInfraTypeList(Context context) {
			this.context = context;
			prbProunit.setVisibility(View.VISIBLE);
			if (lstTech != null && lstTech.size() > 0) {
				lstTech.clear();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getInfraTypeList(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			prbProunit.setVisibility(View.GONE);
			if (errorCode.equalsIgnoreCase("0")) {

				try {
					lstTech = new ArrayList<>();
					lstTech.add(new Spin("-1", getActivity().getResources()
							.getString(R.string.selecttech)));
					lstTech.addAll(result);
					Utils.setDataSpinner(getActivity(), lstTech, spn_technew);

					if (infoSubChild.getHotlineForm() != null
							&& !CommonActivity.isNullOrEmpty(infoSubChild
									.getHotlineForm().getInfraCode())) {
						Log.d("ifracccccodddddddd", infoSubChild
									.getHotlineForm().getInfraCode());
						if (lstTech != null && lstTech.size() > 1) {
							for (Spin spin : lstTech) {
								if (infoSubChild.getHotlineForm()
										.getInfraCode().equals(spin.getId())) {
									spn_technew.setSelection(lstTech
											.indexOf(spin));
									break;
								}
							}
						}
					}

				} catch (Exception e) {
					Log.i("Exception", e.toString());
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					lstTech.add(new Spin("-1", getActivity().getResources()
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
					lstTech.add(new Spin("-1", getActivity().getResources()
							.getString(R.string.txt_select_reason)));
					Utils.setDataSpinner(getActivity(), lstTech, spn_technew);
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getActivity()
									.getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Spin> getInfraTypeList(String account,
				String serviceType) {

			ArrayList<Spin> lstInfraType = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getInfraTypeList");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getInfraTypeList>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<account>").append(account).append("</account>");
				rawData.append("<serviceType>").append(serviceType).append("</serviceType>");
				rawData.append("</input>");
				rawData.append("</ws:getInfraTypeList>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getInfraTypeList");
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
					nodechild = doc.getElementsByTagName("arrayInfra");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						Spin spin = new Spin();
						spin.setValue(parse.getValue(e1, "name"));
						Log.d("LOG", "value: " + spin.getValue());
						spin.setId(parse.getValue(e1, "code"));
						Log.d("LOG", "Idddd: " + spin.getId());
						lstInfraType.add(spin);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstInfraType;
		}
	}

	// lay danh l� do
	private class AsyntaskGetReasonPos extends
			AsyncTask<String, Void, ArrayList<Spin>> {
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetReasonPos(Context context) {
			this.context = context;
			prbreason.setVisibility(View.VISIBLE);
			if (lstReason != null && lstReason.size() > 0) {
				lstReason.clear();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getReasonInfoPos(params[0]);
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
				} catch (Exception ignored) {

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
					lstReason = new ArrayList<>();
					if (lstReason != null && spn_reason_fail != null) {
						lstReason.add(new Spin("-1", context.getResources()
								.getString(R.string.txt_select_reason)));
						Utils.setDataSpinner(getActivity(), lstReason,
								spn_reason_fail);
					}

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getActivity()
									.getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Spin> getReasonInfoPos(String serviceType) {

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
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<actionCode>" + 594 + "</actionCode>");
				rawData.append("<serviceType>").append(serviceType).append("</serviceType>");
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
			ArrayList<Spin> lstReason = new ArrayList<>();
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

	private final OnClickListener onclickChangeTech = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			showDialogChangeTech(arrInfoSubChilds);
			// Bundle bundle = new Bundle();
			// Intent intent = new Intent(getActivity() ,
			// ActivityCreateNewRequestHotLine.class);
			// bundle.putSerializable("infoSubKey", infoSub);
			// intent.putExtras(bundle);
			// startActivity(intent);

		}
	};

	// ham chuyen doi chuong trinh khuyen mai
	private class AsynCreateRequestChangeInfra extends
			AsyncTask<String, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		private int type;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynCreateRequestChangeInfra(Context mContext) {
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
			return createRequestChangeInfra(arg0[0], arg0[1], arg0[2], arg0[3]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				CommonActivity.createAlertDialog(
						getActivity(),
						context.getResources().getString(
								R.string.changeTechSuccess),
						context.getResources().getString(R.string.app_name),
						onclickChangeTech).show();

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

		private String createRequestChangeInfra(String account,
				String serviceType, String infraType, String reasonId) {
			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_createRequestChangeInfra");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:createRequestChangeInfra>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				// rawData.append("<account>" + account + "</account>");
				// rawData.append("<serviceType>" + serviceType +
				// "</serviceType>");
				rawData.append("<infraType>").append(infraType).append("</infraType>");
				rawData.append("<reasonId>").append(reasonId).append("</reasonId>");

				if (arrInfoSubChilds != null && !arrInfoSubChilds.isEmpty()) {
					for (InfoSubChild infoSubChild : arrInfoSubChilds) {
						rawData.append("<lsChangeInfraReq>");
						rawData.append("<account>").append(infoSubChild.getAccount()).append("</account>");
						rawData.append("<serviceType>").append(infoSubChild.getServiceType()).append("</serviceType>");

						if (infoSubChild.getHotlineForm() != null
								&& !CommonActivity
										.isNullOrEmpty(infoSubChild
												.getHotlineForm()
												.getRequestHotlineId())) {
							rawData.append("<requestHotlineId>").append(infoSubChild.getHotlineForm()
                                    .getRequestHotlineId()).append("</requestHotlineId>");
						}

						rawData.append("</lsChangeInfraReq>");
					}
				}
				rawData.append("</input>");
				rawData.append("</ws:createRequestChangeInfra>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_createRequestChangeInfra");
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

	private final OnClickListener backClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (dialogRequest != null) {
				dialogRequest.dismiss();
			}
			getActivity().onBackPressed();

		}
	};

	// ham huy tao yeu cau chuyen doi
	private class AsynDeleteRequestChangeInfra extends
			AsyncTask<String, Void, String> {

		final ProgressDialog progress;
		private Context context = null;
		private int type;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsynDeleteRequestChangeInfra(Context mContext) {
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
			return delRequestChangeInfra(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				CommonActivity.createAlertDialog(
						getActivity(),
						context.getResources().getString(
								R.string.cancelrequestsuccess),
						context.getResources().getString(R.string.app_name),
						backClick).show();

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

		private String delRequestChangeInfra(String account, String serviceType) {
			String original = "";

			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_deleteChangeInfraReq");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:deleteChangeInfraReq>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				// rawData.append("<account>" + account + "</account>");
				// rawData.append("<serviceType>" + serviceType +
				// "</serviceType>");
				// rawData.append("<infraType>" + infraType + "</infraType>");
				// rawData.append("<reasonId>" + reasonId + "</reasonId>");

				// if (arrInfoSubChilds != null && !arrInfoSubChilds.isEmpty())
				// {
				// for (InfoSubChild infoSubChild : arrInfoSubChilds) {
				rawData.append("<lsChangeInfraReq>");
				rawData.append("<account>").append(account).append("</account>");
				rawData.append("<serviceType>").append(serviceType).append("</serviceType>");
				rawData.append("</lsChangeInfraReq>");
				// }
				// }
				rawData.append("</input>");
				rawData.append("</ws:deleteChangeInfraReq>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_deleteChangeInfraReq");
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_apply:

			if (validateCreateRequest()) {

				CommonActivity
						.createDialog(
								getActivity(),
								getActivity().getResources().getString(
										R.string.confirmchuyendoicongnghe),
								getActivity().getResources().getString(
										R.string.app_name),
								getString(R.string.ok),
								getActivity().getResources().getString(
										R.string.cancel), chuyendoiClick, null)
						.show();

			}

			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

	private final OnClickListener chuyendoiClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Spin itemSelect = (Spin) spn_technew.getSelectedItem();
			Spin itemReason = (Spin) spn_reason_fail.getSelectedItem();
			AsynCreateRequestChangeInfra asynCreateRequestChangeInfra = new AsynCreateRequestChangeInfra(
					getActivity());
			asynCreateRequestChangeInfra.execute(account, serviceType,
					itemSelect.getId(), itemReason.getId());

		}
	};

	private Dialog dialogRequest = null;
	private GetListReqCreateSubChildAdapter getListReqCreateSubChildAdapter = null;

	private ArrayList<InfoSubChild> lstInfoSubChilds = new ArrayList<>();
	private InfoSubChild infoSubChild = null;

	private void showDialogChangeTech(
			final ArrayList<InfoSubChild> arrInfoSubChilds) {

		lstInfoSubChilds = arrInfoSubChilds;

		dialogRequest = new Dialog(getActivity());
		dialogRequest.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogRequest.setContentView(R.layout.connection_layout_request_change);

		ListView lvrequest = (ListView) dialogRequest
				.findViewById(R.id.lvrequest);

		getListReqCreateSubChildAdapter = new GetListReqCreateSubChildAdapter(
				lstInfoSubChilds, getActivity(),
				FragmentCreateRequestChangeTech.this);
		lvrequest.setAdapter(getListReqCreateSubChildAdapter);

		lvrequest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				infoSubChild = lstInfoSubChilds.get(arg2);

				Bundle bundle = new Bundle();
				Intent intent = new Intent(getActivity(),
						ActivityCreateNewRequestHotLine.class);
				bundle.putSerializable("InfoSubKey", infoSubChild);

				Spin itemInfra = (Spin) spn_technew.getSelectedItem();

				if (itemInfra != null) {
					bundle.putString("technologyKey", itemInfra.getId());
					bundle.putString("technologyNameKey", itemInfra.getValue());
				}
				intent.putExtras(bundle);
				startActivity(intent);

			}
		});

		dialogRequest.findViewById(R.id.btnCancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialogRequest.cancel();
					}
				});

		dialogRequest.show();

	}

	@Override
	public void onCancelInfoSubChild(InfoSubChild infoSub) {
		infoSubChild = infoSub;
		OnClickListener removeRequestChild = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AsynDeleteRequestChangeInfra asynDeleteRequestChangeInfra = new AsynDeleteRequestChangeInfra(
						getActivity());
				asynDeleteRequestChangeInfra.execute(infoSubChild.getAccount(),
						infoSubChild.getServiceType());
			}
		};

		CommonActivity.createDialog(
				getActivity(),
				getActivity().getResources()
						.getString(R.string.checkconfirmhuy),
				getActivity().getResources().getString(R.string.app_name),
				getActivity().getResources().getString(R.string.ok),
				getActivity().getResources().getString(R.string.cancel),
				removeRequestChild, null).show();

	}

	OnClickListener removeClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			if (infoSubChild != null
					&& !CommonActivity.isNullOrEmpty(infoSubChild.getAccount())) {
				if (lstInfoSubChilds != null && !lstInfoSubChilds.isEmpty()) {
					if (infoSubChild != null) {
						for (InfoSubChild item : lstInfoSubChilds) {
							if (item.getAccount().equals(
									infoSubChild.getAccount())) {
								lstInfoSubChilds.remove(item);
								break;
							}
						}
						if (getListReqCreateSubChildAdapter != null) {
							getListReqCreateSubChildAdapter
									.notifyDataSetChanged();
						}
					}
				}
			}
		}
	};
}
