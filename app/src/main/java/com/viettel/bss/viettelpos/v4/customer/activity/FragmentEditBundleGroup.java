package com.viettel.bss.viettelpos.v4.customer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleGroupEditAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleGroupEditAdapter.OnAddSub;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleGroupEditAdapter.OnChangeSub;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleGroupEditAdapter.OnDeleteSub;
import com.viettel.bss.viettelpos.v4.customer.business.BundleBusiness;
import com.viettel.bss.viettelpos.v4.customer.object.BundleGroup;
import com.viettel.bss.viettelpos.v4.customer.object.ProductBundleGroupsConfigDetail;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentEditBundleGroup extends FragmentCommon implements
		OnChangeSub, OnDeleteSub, OnAddSub {
	private ListView listView;
	private BundleGroupEditAdapter adapter;
	private List<ProductBundleGroupsConfigDetail> lstBundleGroup = new ArrayList<>();
    private BundleGroup bundleGroup;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(R.string.config_bundle);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_bundle_group_edit;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void unit(View v) {
		Bundle bundle = getArguments();
		if (bundle != null) {
			bundleGroup = (BundleGroup) bundle.getSerializable("bundleGroup");
		}
        Button btnAddDeleteSub = (Button) v.findViewById(R.id.btnAddDelSub);
        EditText edtGroupName = (EditText) v.findViewById(R.id.edtGroupName);
		edtGroupName.setText(bundleGroup.getName());

		AsynctaskGetGroupBundledetail asy = new AsynctaskGetGroupBundledetail();
		asy.execute();

		listView = (ListView) v.findViewById(R.id.lvGroupDetail);
		final OnClickListener createGroupClick = new OnClickListener() {
			public void onClick(View arg0) {
				AsynctaskAddDelSub asy = new AsynctaskAddDelSub();
				asy.execute();
			}
        };

		btnAddDeleteSub.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				boolean isChooice = false;
				// boolean isNeed = false;
				for (ProductBundleGroupsConfigDetail item : lstBundleGroup) {
					if (item.getIsAdd()
							|| item.getIsDelete()
							|| (!item.getIsOld() && !CommonActivity
									.isNullOrEmpty(item.getIsdnAccount()))) {
						isChooice = true;
						break;
					}
				}
				if (!isChooice) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.change_atlest_isdn),
							getString(R.string.app_name)).show();
					return;
				}

				if (CommonActivity.isNetworkConnected(getActivity())) {
					String msg = getActivity().getString(
							R.string.confirm_delete_sub);
					for (ProductBundleGroupsConfigDetail item : lstBundleGroup) {
						if (item.getIsAdd()) {
							msg = getActivity().getString(
									R.string.confirm_add_sub);
							break;
						}
					}

					Dialog dialog = CommonActivity.createDialog(getActivity(),
							msg, getActivity().getString(R.string.app_name),
							getResources().getString(R.string.ok),
							getResources().getString(R.string.cancel),
							createGroupClick, null);
					dialog.show();
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}
			}
		});
	}

	@SuppressWarnings("unused")
	private class AsynctaskGetGroupBundledetail extends
			AsyncTask<Void, Void, Void> {
		private final Activity mActivity = MainActivity.getInstance();
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsynctaskGetGroupBundledetail() {
			this.progress = new ProgressDialog(MainActivity.getInstance());
			this.progress.setCancelable(false);
			this.progress.setMessage(MainActivity.getInstance().getResources()
					.getString(R.string.getting_program));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			getDetailBundleGroup();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (lstBundleGroup == null || lstBundleGroup.isEmpty()) {
					return;
				}
				Collections.sort(lstBundleGroup,
						new Comparator<ProductBundleGroupsConfigDetail>() {
							@Override
							public int compare(
									ProductBundleGroupsConfigDetail arg0,
									ProductBundleGroupsConfigDetail arg1) {
								// TODO Auto-generated method stub
								int fromVal1 = Integer.parseInt(arg0
										.getFromValue());
								int fromVal2 = Integer.parseInt(arg1
										.getFromValue());
								if (fromVal1 >= fromVal2) {
									return -1;
								} else {
									return 1;
								}
							}
						});
				adapter = new BundleGroupEditAdapter(lstBundleGroup, mActivity,
						FragmentEditBundleGroup.this,
						FragmentEditBundleGroup.this,
						FragmentEditBundleGroup.this);
				listView.setAdapter(adapter);

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

		private void getDetailBundleGroup() {
			ArrayList<ProductBundleGroupsConfigDetail> lstConfig = new ArrayList<>();
			ArrayList<ProductBundleGroupsConfigDetail> lstSub = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getGroupBundledetail");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getGroupBundledetail>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<bundleGroupsConfigId>").append(bundleGroup.getProductBundleGroupsConfigId()).append("</bundleGroupsConfigId>");
				rawData.append("<groupId>").append(bundleGroup.getGroupId()).append("</groupId>");
				rawData.append("</input>");
				rawData.append("</ws:getGroupBundledetail>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getGroupBundledetail");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);
				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				// NodeList nodechild = null;
				// NodeList nodechildListSerial = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					if (!errorCode.equals("0")) {
						return;
					}
					Log.d("errorCode", errorCode);
					NodeList nodechild = e2
							.getElementsByTagName("lstBundleConfigDetailBeans");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						ProductBundleGroupsConfigDetail item = new ProductBundleGroupsConfigDetail();
						item.setPkType(parse.getValue(e1, "pkType"));
						item.setPkId(parse.getValue(e1, "pkId"));
						// item.setBoss(parse.getValue(e1, "boss"));
						item.setCheckPOP(parse.getValue(e1, "checkPOP"));
						item.setFromValue(parse.getValue(e1, "fromValue"));
						item.setSubType(parse.getValue(e1, "subType"));
						item.setTelecomeServiceId(parse.getValue(e1,
								"telecomeServiceId"));
						item.setToValue(parse.getValue(e1, "toValue"));
						item.setShortNumber(parse.getValue(e1, "shortNumber"));

						if ("2".equals(item.getPkType())) {

							lstConfig.add(item);
						}
					}
					Log.e("xxxxxxxxxxxxxx", lstConfig.size() + "");
					NodeList nodechildListSerial = e2
							.getElementsByTagName("lstGroupMemberDTO");
					for (int j = 0; j < nodechildListSerial.getLength(); j++) {
						Element e1 = (Element) nodechildListSerial.item(j);
						ProductBundleGroupsConfigDetail item = new ProductBundleGroupsConfigDetail();
						item.setIsdnAccount(parse.getValue(e1, "isdn"));
						item.setShortNumberInput(parse.getValue(e1,
								"shortNumber"));
						item.setGroupMemberId(parse.getValue(e1,
								"groupMemberId"));
						// item.setBoss(parse.getValue(e1, "boss"));
						item.setTelecomeServiceId(parse.getValue(e1,
								"telecomServiceId"));
						item.setSubType(parse.getValue(e1, "payType"));
						item.setSubId(parse.getValue(e1, "subId"));
						String boss = parse.getValue(e1, "main");
						if ("1".equals(boss)) {
							item.setIsBoss(true);
						} else {
							item.setIsBoss(false);
						}
						item.setIsOld(true);
						lstSub.add(item);
					}
				}
				if (lstConfig != null && !lstConfig.isEmpty() && lstSub != null
						&& !lstSub.isEmpty()) {
					for (ProductBundleGroupsConfigDetail member : lstSub) {
						for (int i = 0; i < lstConfig.size(); i++) {
							ProductBundleGroupsConfigDetail config = lstConfig
									.get(i);
							if (member.getTelecomeServiceId().equals(
									config.getTelecomeServiceId())
									&& member.getSubType().equals(
											config.getSubType())) {
								if (config.getIsdnAccount() != null
										&& !config.getIsdnAccount().isEmpty()) {
									ProductBundleGroupsConfigDetail tmp = BundleBusiness
											.cloneProductBundleGroupsConfigDetail(config);
									tmp.setIsdnAccount(member.getIsdnAccount());
									tmp.setShortNumberInput(member
											.getShortNumberInput());
									tmp.setIsBoss(member.getIsBoss());
									tmp.setSubId(member.getSubId());
									tmp.setGroupMemberId(member
											.getGroupMemberId());
									lstConfig.add(i, tmp);
								} else {
									config.setIsdnAccount(member
											.getIsdnAccount());
									config.setShortNumberInput(member
											.getShortNumberInput());
									config.setIsBoss(member.getIsBoss());
									config.setGroupMemberId(member
											.getGroupMemberId());
									config.setSubId(member.getSubId());
								}
								break;
							}
						}
					}
					for (ProductBundleGroupsConfigDetail config : lstConfig) {
						if (config.getIsdnAccount() != null
								&& !config.getIsdnAccount().isEmpty()) {
							config.setIsOld(true);
						}
					}
					lstBundleGroup = lstConfig;
					errorCode = "0";
				}

			} catch (Exception e) {

				Log.e("getListIP", e.toString() + "description error", e);
				errorCode = "1";
				description = e.getMessage();
			}

		}
	}

	@Override
	public void onAddSub(int position) {

		for (ProductBundleGroupsConfigDetail member : lstBundleGroup) {
			if (member.getIsDelete()) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.canot_add_bundle_when_delete),
						getString(R.string.app_name_title)).show();
				return;
			}
		}

		if (!BundleBusiness.checkAdd(lstBundleGroup,
				lstBundleGroup.get(position))) {
			CommonActivity.createAlertDialog(
					getActivity(),
					MessageFormat.format(getString(R.string.canot_add_bundle),
							lstBundleGroup.get(position).getToValue()),
					getString(R.string.app_name_title)).show();
			return;
		}
		ProductBundleGroupsConfigDetail item = lstBundleGroup.get(position);
		ProductBundleGroupsConfigDetail addItem = BundleBusiness
				.cloneProductBundleGroupsConfigDetail(item);
		addItem.setIsAdd(true);
		addItem.setIsdnAccount("");
		addItem.setIsOld(false);
		if (position == lstBundleGroup.size() - 1) {
			lstBundleGroup.add(addItem);
		} else {
			lstBundleGroup.add(position + 1, addItem);
		}

		adapter.notifyDataSetChanged();
	}

	@Override
	public void onDeleteSub(int position) {

		ProductBundleGroupsConfigDetail item = lstBundleGroup.get(position);
		if (!item.getIsOld()) {
			int countSub = BundleBusiness.countSub(lstBundleGroup, item);
			int fromValue = Integer.parseInt(item.getFromValue());
			if (countSub == 1 || countSub == fromValue) {
				item.setIsdnAccount("");
				item.setShortNumberInput("");
				item.setBoss("0");
				item.setIsAdd(false);
				item.setIsDelete(false);
			} else {
				lstBundleGroup.remove(position);
			}
			for (ProductBundleGroupsConfigDetail member : lstBundleGroup) {
				if (member.getIsAdd()) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.canot_delete_bundle_when_add),
							getString(R.string.app_name_title)).show();
					return;
				}
			}
			adapter.notifyDataSetChanged();
			return;
		}

		item.setIsDelete(!item.getIsDelete());
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onChangeSub(int position) {
		final ProductBundleGroupsConfigDetail item = lstBundleGroup
				.get(position);
		if (!item.getIsAdd() && item.getIsdnAccount() != null
				&& !item.getIsdnAccount().isEmpty()) {
			return;
		}
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.bundle_input_sub_dialog);

		final EditText edtSub = (EditText) dialog.findViewById(R.id.edtSub);
		edtSub.setText(item.getIsdnAccount());

		final EditText edtShortNumber = (EditText) dialog
				.findViewById(R.id.edtShortNumber);
		edtShortNumber.setText(item.getShortNumberInput());
		final CheckBox cbBoss = (CheckBox) dialog.findViewById(R.id.cbBoss);
		cbBoss.setVisibility(View.GONE);

		int shortNumber = 0;
		try {
			shortNumber = Integer.parseInt(item.getShortNumber());
		} catch (Exception e) {
			Log.e("XXXXXX", "XXXXXXXXXXX", e);
		}
		if (shortNumber == 0) {
			dialog.findViewById(R.id.lnShortNumber).setVisibility(View.GONE);
		} else {
			dialog.findViewById(R.id.lnShortNumber).setVisibility(View.VISIBLE);
		}
		dialog.findViewById(R.id.TextView02).setVisibility(View.GONE);
		dialog.findViewById(R.id.btnOk).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if (edtSub.getText().toString().trim().isEmpty()) {
							CommonActivity
									.createAlertDialog(
											getActivity(),
											getString(R.string.must_input_isdn_account),
											getString(R.string.app_name))
									.show();
							return;
						}
						int shortNum = 0;
						try {
							shortNum = Integer.parseInt(item.getShortNumber());
						} catch (Exception e) {
							Log.e("XXXXXX", "XXXXXXXXXXX", e);
						}
						if (shortNum == 1
								&& edtShortNumber.getText().toString().trim()
										.isEmpty()) {
							CommonActivity
									.createAlertDialog(
											getActivity(),
											getString(R.string.must_input_short_number),
											getString(R.string.app_name))
									.show();
							return;
						}

						item.setIsdnAccount(edtSub.getText().toString().trim());
						item.setShortNumberInput(edtShortNumber.getText()
								.toString().trim());
						item.setIsAdd(true);
						adapter.notifyDataSetChanged();
						CommonActivity.hideKeyboard(MainActivity.getInstance());
						dialog.dismiss();
					}
				});
		dialog.findViewById(R.id.btnCancel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						CommonActivity.hideKeyboard(MainActivity.getInstance());
						dialog.dismiss();
					}
				});
		dialog.show();
	}

	private class AsynctaskAddDelSub extends AsyncTask<Void, Void, Void> {

		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private Boolean isAdd = false;
		private String wsCode = "mbccs_deleteMemberBundleGroup";

		public AsynctaskAddDelSub() {
			this.progress = new ProgressDialog(MainActivity.getInstance());

			for (ProductBundleGroupsConfigDetail item : lstBundleGroup) {
				if (item.getIsAdd()) {
					isAdd = true;
					wsCode = "mbccs_addMemberBundleGroup";
					break;
				}
			}
			String msg = MainActivity.getInstance().getResources()
					.getString(R.string.deleting_member);
			if (isAdd) {
				msg = MainActivity.getInstance().getResources()
						.getString(R.string.adding_member);
			}
			this.progress.setMessage(msg);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			addDelSub();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (isAdd) {
					description = getString(R.string.add_member_bundle_success);
				} else {
					description = getString(R.string.delete_member_bundle_success)
							+ "-" + description;
				}
				OnClickListener backOnClick = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						MainActivity.getInstance().onBackPressed();

					}
				};
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						description, getResources()
								.getString(R.string.app_name), backOnClick);
				dialog.show();
				// AsynctaskGetGroupBundledetail asy = new
				// AsynctaskGetGroupBundledetail();
				// asy.execute();
			} else {

				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(MainActivity.getInstance(),
									description,
									MainActivity.getInstance().getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = MainActivity.getInstance().getString(
								R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private void addDelSub() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", wsCode);
				StringBuilder rawData = new StringBuilder();
				if (isAdd) {
					rawData.append("<ws:addMemberBundleGroup>");
				} else {
					rawData.append("<ws:deleteMemberBundleGroup>");
				}

				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<groupId>").append(bundleGroup.getGroupId())
						.append("</groupId>");
				rawData.append("<bundleGroupsConfigId>").append(bundleGroup.getProductBundleGroupsConfigId()).append("</bundleGroupsConfigId>");
				rawData.append("<reasonId>").append("146")
						.append("</reasonId>");
				for (ProductBundleGroupsConfigDetail item : lstBundleGroup) {
					if (item.getIsAdd() || item.getIsDelete()) {
						rawData.append("<lstMember>");
						rawData.append("<shortNumber>")
								.append(item.getShortNumberInput())
								.append("</shortNumber>");
						String isdn = item.getIsdnAccount();
						if (item.getTelecomeServiceId().equals("1")
								|| item.getTelecomeServiceId().equals("2")) {
							isdn = CommonActivity.checkStardardIsdn(item
									.getIsdnAccount());
						}
						rawData.append("<isdn>").append(isdn).append("</isdn>");
						rawData.append("<telecomServiceId>")
								.append(item.getTelecomeServiceId())
								.append("</telecomServiceId>");
						rawData.append("<main>").append("0").append("</main>");
						rawData.append("<groupMemberId>")
								.append(item.getGroupMemberId())
								.append("</groupMemberId>");
						int fromValue = Integer.parseInt(item.getFromValue());
						if (fromValue >= 1) {
							rawData.append("<mandatory>").append("1")
									.append("</mandatory>");
						} else {
							rawData.append("<mandatory>").append("0")
									.append("</mandatory>");
						}

						rawData.append("</lstMember>");
					}
				}
				rawData.append("</input>");
				if (isAdd) {
					rawData.append("</ws:addMemberBundleGroup>");
				} else {
					rawData.append("</ws:deleteMemberBundleGroup>");
				}
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), wsCode);
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
				}
			} catch (Exception e) {
				Log.e("getListIP", e.toString() + "description error", e);
			}

		}
	}

	@Override
	public void setPermission() {
		permission = ";bundle.group;";

	}
}
