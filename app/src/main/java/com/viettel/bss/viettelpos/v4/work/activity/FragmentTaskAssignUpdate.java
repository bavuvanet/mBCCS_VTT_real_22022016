package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentBlockLiquidateSub;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.InfoSub;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.TaskAssignBO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class FragmentTaskAssignUpdate extends FragmentCommon {

	private Activity activity;

	private Spinner spnStatus;
	private Spinner spnReason;
	private EditText edtNote;

	private String status;
	private TaskAssignBO taskAssignBO;
	private String reasonId;

	private Button btnTaskAssignUpdate;

    private TextView taskCode;
	private TextView taskName;
	private TextView account;
	private TextView serviceName;
	private TextView mngtCode;
	private TextView staffCode;
	private TextView assignDate;
	private TextView endAssignDate;
	private TextView description;
	private TextView tvStatus;
	private TextView tvReasonName;

	private String[] task_assign_value_update;

    @Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.task_assign_update);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_task_assign_update;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

		mBundle = getArguments();
		if (mBundle != null) {
			taskAssignBO = (TaskAssignBO) mBundle
					.getSerializable("taskAssignBO");
		}
	}

	@Override
	public void unit(View v) {
		spnStatus = (Spinner) v.findViewById(R.id.spnStatus);
		spnReason = (Spinner) v.findViewById(R.id.spnReason);
		edtNote = (EditText) v.findViewById(R.id.edtNote);

		btnTaskAssignUpdate = (Button) v.findViewById(R.id.btnTaskAssignUpdate);
		btnTaskAssignUpdate.setOnClickListener(this);

        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);

		List<Spin> lstSpin = (new ApParamDAL(act)).getAppParam("REASON_CODE");
		Spin spinNon = new Spin("", getString(R.string.txt_select));
		lstSpin.add(0, spinNon);

		Utils.setDataSpinner(act, lstSpin, spnReason);

		taskCode = (TextView) v.findViewById(R.id.taskCode);
		taskName = (TextView) v.findViewById(R.id.taskName);
		account = (TextView) v.findViewById(R.id.account);
		serviceName = (TextView) v.findViewById(R.id.serviceName);
		mngtCode = (TextView) v.findViewById(R.id.mngtCode);
		staffCode = (TextView) v.findViewById(R.id.staffCode);
		assignDate = (TextView) v.findViewById(R.id.assignDate);
		endAssignDate = (TextView) v.findViewById(R.id.endAssignDate);
		description = (TextView) v.findViewById(R.id.description);
		tvStatus = (TextView) v.findViewById(R.id.status);
		tvReasonName = (TextView) v.findViewById(R.id.tvReasonName);

		taskCode.setText(taskAssignBO.getTaskCode());
		taskName.setText(taskAssignBO.getTaskName());
		account.setText(taskAssignBO.getAccount());
		serviceName.setText(taskAssignBO.getServiceName());
		mngtCode.setText(taskAssignBO.getMngtCode());
		staffCode.setText(taskAssignBO.getStaffCode());
		assignDate.setText(taskAssignBO.getAssignDate());
		endAssignDate.setText(taskAssignBO.getEndAssignDate());
		description.setText(taskAssignBO.getDescription());

		for (Spin spin : lstSpin) {
			if (spin.getId().equalsIgnoreCase(taskAssignBO.getReasonId())) {
				if (!taskAssignBO.getReasonId().isEmpty()
						&& !taskAssignBO.getReasonId().equalsIgnoreCase("-1")) {
					tvReasonName.setText(spin.getValue());
				}
				break;
			}
		}

        String[] task_assign_update = getResources().getStringArray(
                R.array.task_assign_update);
		task_assign_value_update = getResources().getStringArray(
				R.array.task_assign_value_update);

		String[] task_assign = getResources().getStringArray(
				R.array.task_assign);
		int iStatus = Integer.parseInt(taskAssignBO.getStatus());
		tvStatus.setText(task_assign[iStatus + 1]);

        LinearLayout lnStatus = (LinearLayout) v.findViewById(R.id.lnStatus);
        LinearLayout lnReason = (LinearLayout) v.findViewById(R.id.lnReason);
        LinearLayout lnNote = (LinearLayout) v.findViewById(R.id.lnNote);
		if ("0".equalsIgnoreCase(taskAssignBO.getStatus())
				|| "3".equalsIgnoreCase(taskAssignBO.getStatus())) {
			lnStatus.setVisibility(View.VISIBLE);
			lnReason.setVisibility(View.VISIBLE);
			lnNote.setVisibility(View.VISIBLE);
			btnTaskAssignUpdate.setVisibility(View.VISIBLE);
		} else {
			lnStatus.setVisibility(View.GONE);
			lnReason.setVisibility(View.GONE);
			lnNote.setVisibility(View.GONE);

			if ("2".equals(taskAssignBO.getStatus()) && "2".equals(taskAssignBO.getTaskCode())) {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					AsynGetInfoSubscriberByAccountAndServiceType getListCustomerAsyn = new AsynGetInfoSubscriberByAccountAndServiceType(
							getActivity());
					getListCustomerAsyn.execute(taskAssignBO.getAccount(), taskAssignBO.getServiceType(), "1");

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.errorNetwork),
							getActivity().getString(R.string.app_name)).show();
				}
			} else {
				btnTaskAssignUpdate.setVisibility(View.GONE);
			}

		}

		// bo trang thai qua han chua lam
		// spnStatus.setOnItemSelectedListener(new
		// AdapterView.OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> parent, View view, int
		// position, long id) {
		// String iStatus = task_assign_value[position];
		// if("1".equalsIgnoreCase(iStatus)) {
		// spnReason.setVisibility(View.GONE);
		// } else {
		// spnReason.setVisibility(View.VISIBLE);
		// }
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> parent) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);

		switch (arg0.getId()) {
		case R.id.btnCancel:
			Log.d(Constant.TAG, "FragmentTaskAssignUpdate onClick btnCancel");
			act.onBackPressed();
			break;
		case R.id.btnTaskAssignUpdate:
			if (CommonActivity.isNetworkConnected(activity)) {

				if ("2".equals(taskAssignBO.getStatus())) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("taskAssignBO", taskAssignBO);

					FragmentBlockLiquidateSub fragment = new FragmentBlockLiquidateSub();
					fragment.setArguments(bundle);
					// fragment.setTargetFragment(FragmentTaskAssignStaff.this,
					// 100);
					ReplaceFragment.replaceFragment(activity, fragment, true);

				} else {
					int pos_spnStatus = spnStatus.getSelectedItemPosition();
					status = task_assign_value_update[pos_spnStatus];

					if (StringUtils.CheckCharSpecical(edtNote.getText()
							.toString())) {
						Toast.makeText(getActivity(),
								getString(R.string.checkcharspecical),
								Toast.LENGTH_SHORT).show();
						return;
					}

					if (pos_spnStatus == 0) {
						CommonActivity.createAlertDialog(activity,
								getString(R.string.please_input_status),
								getString(R.string.app_name)).show();
						return;
					}

					Spin spin = (Spin) spnReason.getSelectedItem();
					reasonId = (spin == null) ? "" : spin.getId();

					if (pos_spnStatus == 2 && reasonId.isEmpty()) {
						CommonActivity
								.createAlertDialog(
										activity,
										getString(R.string.message_not_sellect_reasoon),
										getString(R.string.app_name)).show();
						return;
					}

					Log.d(Constant.TAG,
							"FragmentTaskAssign lookUpTaskAssign pos_spnStatus: "
									+ pos_spnStatus + " status: " + status
									+ " reasonId: " + reasonId);

					CommonActivity.hideKeyboard(btnTaskAssignUpdate, activity);

					CommonActivity.createDialog(activity,
							getString(R.string.message_confirm_update),
							getString(R.string.app_name),
							getString(R.string.say_ko),
							getString(R.string.say_co), null, confirmChargeAct)
							.show();
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

	// confirm charge
	private final OnClickListener confirmChargeAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (CommonActivity.isNetworkConnected(activity)) {
				AsyncUpdateTask async = new AsyncUpdateTask(activity,
						taskAssignBO.getTaskAssignId(), reasonId, status);
				async.execute();
			} else {
				CommonActivity.createAlertDialog(activity,
						getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
		}
	};

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

	private final OnClickListener onclickMoveBlockScreen = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			Bundle bundle = new Bundle();
			bundle.putSerializable("taskAssignBO", taskAssignBO);

			FragmentBlockLiquidateSub fragment = new FragmentBlockLiquidateSub();
			fragment.setArguments(bundle);
			// fragment.setTargetFragment(FragmentTaskAssignStaff.this, 100);
			ReplaceFragment.replaceFragment(activity, fragment, true);

			// ReplaceFragment.replaceFragment(getActivity(),
			// fragment, true);
		}
	};

	private class AsyncUpdateTask extends AsyncTask<String, Void, Void> {

		private final Activity mActivity;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;

		private final String _status;
		private final String _taskAssignId;
		private final String _reasonId;

		public AsyncUpdateTask(Activity mActivity, String taskAssignId,
				String reasonId, String status) {
			this.mActivity = mActivity;
			this._status = status;
			this._taskAssignId = taskAssignId;
			this._reasonId = reasonId;
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
		protected Void doInBackground(String... params) {
			return updateTaskAssign();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if ("2".equals(taskAssignBO.getTaskCode()) && "2"
								.equals(status)) {
					CommonActivity.createDialog(getActivity(),
							getActivity().getString(R.string.checkthanhly),
							getActivity().getString(R.string.app_name),
							getActivity().getString(R.string.ok),
							getActivity().getString(R.string.cancel),
							onclickMoveBlockScreen, null).show();

				} else {
					CommonActivity.createAlertDialog(mActivity, description,
							getString(R.string.app_name)).show();
				}

			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					if (description == null || description.isEmpty()) {
						description = getResources().getString(
								R.string.updatesucess);
					}
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

		private Void updateTaskAssign() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_updateTaskAssign");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateTaskAssign>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<taskAssignId>").append(_taskAssignId).append("</taskAssignId>");
				rawData.append("<reasonId>").append(_reasonId).append("</reasonId>");
				rawData.append("<status>").append(_status).append("</status>");
				rawData.append("<description>").append(edtNote.getText().toString().trim()).append("</description>");
				rawData.append("</input>");
				rawData.append("</ws:updateTaskAssign>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_updateTaskAssign");
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
					// nodechild = doc.getElementsByTagName("lstTaskAssignBO");
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "FragmentTaskAssign updateTask", e);
			}
			return null;
		}
	}

	// ham lay thong tin thue bao
	private class AsynGetInfoSubscriberByAccountAndServiceType extends
			AsyncTask<String, Void, ArrayList<InfoSub>> {

		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
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

					btnTaskAssignUpdate.setVisibility(View.VISIBLE);
					btnTaskAssignUpdate.setText(getActivity().getString(
							R.string.buttonthanhly));
				} else {
					btnTaskAssignUpdate.setVisibility(View.GONE);

					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), description, getResources()
										.getString(R.string.app_name));
						dialog.show();
					} else {

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
			ArrayList<InfoSub> arrInfoSubs = new ArrayList<>();
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
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<account>").append(account).append("</account>");
				rawData.append("<serviceType>").append(serviceType).append("</serviceType>");
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

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}

}
