package com.viettel.bss.viettelpos.v4.customer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleConfigDetailAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleConfigDetailAdapter.OnAddSub;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleConfigDetailAdapter.OnChangeSub;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleConfigDetailAdapter.OnDeleteSub;
import com.viettel.bss.viettelpos.v4.customer.business.BundleBusiness;
import com.viettel.bss.viettelpos.v4.customer.object.ProductBundleGroupsConfigDetail;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentCreateBundleGroup extends FragmentCommon implements
		OnChangeSub, OnDeleteSub, OnAddSub {
	private Spinner spnNhomChinhSach;
	private Spinner spnChinhSach;
	private final int GET_DATA_TYPE_GROUP = 0;
	private final int GET_DATA_TYPE_ITEM = 1;
	private ListView listView;
	private BundleConfigDetailAdapter adapter;
	private List<ProductBundleGroupsConfigDetail> lstConfigDetail;
	private Boolean checkPOP = false;
	private View lnPop;
    private EditText edtPOP;
	private EditText edtGroupName;
	private String bundleGroupsConfigId;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(R.string.config_bundle);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_bundle_group;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void unit(View v) {
		spnNhomChinhSach = (Spinner) v.findViewById(R.id.spnNhomChinhSach);
		spnChinhSach = (Spinner) v.findViewById(R.id.spnChinhSach);
        Button btnCreateBundle = (Button) v.findViewById(R.id.btnCreateBundle);
		edtGroupName = (EditText) v.findViewById(R.id.edtGroupName);
		lnPop = v.findViewById(R.id.lnPop);
		edtPOP = (EditText) v.findViewById(R.id.edtPop);
		AsynctaskGetListBundle asy = new AsynctaskGetListBundle(getActivity(),
				GET_DATA_TYPE_GROUP, "");
		asy.execute();
		spnNhomChinhSach
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						new CacheDatabaseManager(MainActivity.getInstance())
								.insertCacheBundleGroup(null, "bundleGroup");
						AsynctaskGetListBundle asy = new AsynctaskGetListBundle(
								getActivity(), GET_DATA_TYPE_ITEM, ((Spin) arg0
										.getSelectedItem()).getId());
						asy.execute();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		spnChinhSach.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// if (arg2 == 0) {
				// return;
				// }
				bundleGroupsConfigId = ((Spin) arg0.getSelectedItem()).getId();
				new CacheDatabaseManager(MainActivity.getInstance())
						.insertCacheBundleGroup(null, "bundleGroup"
								+ bundleGroupsConfigId);
				AsynctaskGetDetailBundleConfig asy = new AsynctaskGetDetailBundleConfig(
						getActivity(), bundleGroupsConfigId);
				asy.execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		listView = (ListView) v.findViewById(R.id.lvGroupDetail);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				onChangeSub(arg2);
			}
		});
		v.findViewById(R.id.btnNhomChinhSach).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						new CacheDatabaseManager(getActivity())
								.insertCacheBundleGroup(null, "");
						AsynctaskGetListBundle asy = new AsynctaskGetListBundle(
								getActivity(), GET_DATA_TYPE_GROUP, "");
						asy.execute();
					}
				});
		v.findViewById(R.id.btnChinhSach).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String parentId = ((Spin) spnNhomChinhSach
								.getSelectedItem()).getId();
						new CacheDatabaseManager(getActivity())
								.insertCacheBundleGroup(null, parentId);
						AsynctaskGetListBundle asy = new AsynctaskGetListBundle(
								getActivity(), GET_DATA_TYPE_ITEM, parentId);
						asy.execute();
					}
				});
		final OnClickListener createGroupClick = new OnClickListener() {
			public void onClick(View arg0) {
				AsynctaskCreateBundleGroup asy = new AsynctaskCreateBundleGroup(
						getActivity(), lstConfigDetail);
				asy.execute();
			}
        };
		btnCreateBundle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (checkPOP
						&& CommonActivity.isNullOrEmpty(edtPOP.getText()
								.toString())) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.pop_is_require),
							getString(R.string.app_name)).show();
					edtPOP.requestFocus();
					return;
				}
				if (CommonActivity.isNullOrEmpty(edtGroupName.getText()
						.toString())) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.group_name_is_require),
							getString(R.string.app_name)).show();
					edtGroupName.requestFocus();
					return;
				}
				boolean isChooice = false;
				boolean isMain = false;
				// boolean isNeed = false;
				for (ProductBundleGroupsConfigDetail item : lstConfigDetail) {
					if (!CommonActivity.isNullOrEmpty(item.getIsdnAccount())) {
						isChooice = true;

					}
					if (item.getIsBoss()) {
						isMain = true;
					}
				}

				if (!isChooice) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.input_at_least_isdn),
							getString(R.string.app_name)).show();
					return;
				}
				if (!isMain) {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.no_main_sub),
							getString(R.string.app_name)).show();
					return;
				}
				if (CommonActivity.isNetworkConnected(getActivity())) {
					Dialog dialog = CommonActivity.createDialog(
							getActivity(),
							getActivity().getString(
									R.string.confirm_create_bundle),
							getActivity().getString(R.string.app_name),
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
	private class AsynctaskGetListBundle extends
			AsyncTask<Void, Void, ArrayList<Spin>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private final int getDataType;
		private String parentId = "";

		public AsynctaskGetListBundle(Activity mActivity, int getDataType,
				String parentId) {
			this.getDataType = getDataType;
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.parentId = parentId;
			String msg = "";
			if (getDataType == GET_DATA_TYPE_GROUP) {
				msg = mActivity.getResources().getString(
						R.string.getting_bundle_group);
			} else {
				msg = mActivity.getResources().getString(
						R.string.getting_bundle);
			}
			this.progress.setMessage(msg);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(Void... params) {
			return getBundleConfigByParentId();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (getDataType == GET_DATA_TYPE_GROUP) {
					Spin first = new Spin();
					first.setId("-1");
					first.setValue(mActivity
							.getString(R.string.select_one_group));
					result.add(0, first);
					spnNhomChinhSach
							.setAdapter(new ArrayAdapter<>(
                                    mActivity,
                                    android.R.layout.simple_dropdown_item_1line,
                                    result));
				} else {
					Spin first = new Spin();
					first.setId("-1");
					first.setValue(mActivity
							.getString(R.string.select_one_bundle));
					result.add(0, first);
					spnChinhSach
							.setAdapter(new ArrayAdapter<>(
                                    mActivity,
                                    android.R.layout.simple_dropdown_item_1line,
                                    result));
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

		private ArrayList<Spin> getBundleConfigByParentId() {
			ArrayList<Spin> result = new ArrayList<>();
			if ("-1".equals(parentId)) {
				errorCode = "0";
				return result;
			}
			result = new CacheDatabaseManager(MainActivity.getInstance())
					.getListBundleGroupCache(parentId);
			if (result != null && !result.isEmpty()) {
				errorCode = "0";
				return result;
			}

			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getBundleConfigByParentId");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getBundleConfigByParentId>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<bundleGroupsConfigId>").append(parentId).append("</bundleGroupsConfigId>");
				rawData.append("</input>");
				rawData.append("</ws:getBundleConfigByParentId>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getBundleConfigByParentId");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);
				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodechildListSerial = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc
							.getElementsByTagName("lstProductBundleGroupsConfigDTO");

					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);

						Spin item = new Spin();

						String bundleGroupsConfigId = parse.getValue(e1,
								"bundleGroupsConfigId");

						if (bundleGroupsConfigId != null
								&& !bundleGroupsConfigId.isEmpty()) {
							item.setId(bundleGroupsConfigId);
						}

						String code = parse.getValue(e1, "description");

						if (code != null && !code.isEmpty()) {
							item.setCode(code);
						}
						String name = parse.getValue(e1, "name");

						if (name != null && !name.isEmpty()) {
							item.setValue(name);
						}
						result.add(item);
					}
				}
			} catch (Exception e) {
				Log.e("getListIP", e.toString() + "description error", e);
			}
			new CacheDatabaseManager(MainActivity.getInstance())
					.insertCacheBundleGroup(result, parentId);
			return result;
		}
	}

	@SuppressWarnings("unused")
	private class AsynctaskGetDetailBundleConfig extends
			AsyncTask<Void, Void, ArrayList<ProductBundleGroupsConfigDetail>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private String bundleGroupId = "";

		public AsynctaskGetDetailBundleConfig(Activity mActivity,
				String bundleGroupId) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.bundleGroupId = bundleGroupId;
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getting_program));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ProductBundleGroupsConfigDetail> doInBackground(
				Void... params) {
			return getDetailBundleConfig();
		}

		@Override
		protected void onPostExecute(
				ArrayList<ProductBundleGroupsConfigDetail> result) {
			super.onPostExecute(result);
			lnPop.setVisibility(View.GONE);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (!result.isEmpty()) {
					int i = 0;
					while (i < result.size()) {
						ProductBundleGroupsConfigDetail item = result.get(i);

						int fromValue = 0;
						if (item.getPkType().equals("1")) {
							result.remove(i);
							if (!item.getCheckPOP().equals("0")) {
								checkPOP = true;
								lnPop.setVisibility(View.VISIBLE);
							} else {
								checkPOP = false;
							}
							break;
						}
						if (item.getFromValue() != null
								&& !item.getFromValue().isEmpty()) {
							fromValue = Integer.parseInt(item.getFromValue());
						}

						if (fromValue > 1) {
							for (int j = 1; j < fromValue; j++) {
								i++;
								ProductBundleGroupsConfigDetail tmp = BundleBusiness
										.cloneProductBundleGroupsConfigDetail(item);
								if (i == result.size()) {
									result.add(tmp);
								} else {
									result.add(i, tmp);
								}

							}
						}
						i++;
					}

				}
				lstConfigDetail = result;
				Collections.sort(lstConfigDetail,
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
				adapter = new BundleConfigDetailAdapter(lstConfigDetail,
						mActivity, FragmentCreateBundleGroup.this,
						FragmentCreateBundleGroup.this,
						FragmentCreateBundleGroup.this);
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

		private ArrayList<ProductBundleGroupsConfigDetail> getDetailBundleConfig() {
			ArrayList<ProductBundleGroupsConfigDetail> result = new ArrayList<>();
			if ("-1".equals(bundleGroupsConfigId)) {
				errorCode = "0";
				return result;
			}
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getDetailBundleConfig");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getDetailBundleConfig>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<bundleGroupsConfigId>").append(bundleGroupId).append("</bundleGroupsConfigId>");
				rawData.append("</input>");
				rawData.append("</ws:getDetailBundleConfig>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getDetailBundleConfig");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);
				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodechildListSerial = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc
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
						result.add(item);
					}
				}
			} catch (Exception e) {
				Log.e("getListIP", e.toString() + "description error", e);
			}
			return result;

		}
	}

	@SuppressWarnings("unused")
	private class AsynctaskCreateBundleGroup extends
			AsyncTask<Void, Void, Void> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		final List<ProductBundleGroupsConfigDetail> lstBundleGroupConfig;

		public AsynctaskCreateBundleGroup(Activity mActivity,
				List<ProductBundleGroupsConfigDetail> lstBundleGroupConfig) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.lstBundleGroupConfig = lstBundleGroupConfig;
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.creating_bundle));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			createBundleGroup();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				if (description == null || description.isEmpty()) {
					description = getString(R.string.create_bundle_success);
				} else {
					description = getString(R.string.create_bundle_success)
							+ "-" + description;
				}
				edtGroupName.setText("");
				edtPOP.setText("");
				final OnClickListener deleteGroupClick = new OnClickListener() {
					public void onClick(View arg0) {
						for (int i = 0; i < lstConfigDetail.size(); i++) {
							ProductBundleGroupsConfigDetail item = lstConfigDetail
									.get(i);
							item.setIsdnAccount("");
							item.setIsBoss(false);
						}
						adapter.notifyDataSetChanged();

					}

                };

				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
						description, getResources()
								.getString(R.string.app_name));
				dialog.show();
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

		private void createBundleGroup() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_createBundleGroup");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:createBundleGroup>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<bundleGroupsConfigId>").append(bundleGroupsConfigId).append("</bundleGroupsConfigId>");
				rawData.append("<groupName>").append(TextUtils.htmlEncode(edtGroupName.getText()
                        .toString().trim())).append("</groupName>");
				
				
				rawData.append("<reasonId>").append("146").append("</reasonId>");

				for (ProductBundleGroupsConfigDetail item : lstConfigDetail) {
					if (!CommonActivity.isNullOrEmpty(item.getIsdnAccount())) {
						rawData.append("<lstMember>");
						rawData.append("<shortNumber>")
								.append(item.getShortNumberInput())
								.append("</shortNumber>");
						rawData.append("<payType>").append(item.getSubType())
								.append("</payType>");
						rawData.append("<subType>").append(item.getSubType())
								.append("</subType>");
						String isdn = item.getIsdnAccount();
						if (item.getTelecomeServiceId().equals("1")
								|| item.getTelecomeServiceId().equals("2")) {
							isdn = CommonActivity.checkStardardIsdn(item
									.getIsdnAccount());
						}
						rawData.append("<isdn>")
								.append(TextUtils.htmlEncode(isdn))
								.append("</isdn>");
						rawData.append("<idPopNo>")
								.append(TextUtils.htmlEncode(edtPOP.getText()
										.toString().trim()))
								.append("</idPopNo>");
						rawData.append("<telecomServiceId>")
								.append(item.getTelecomeServiceId())
								.append("</telecomServiceId>");
						rawData.append("<main>")
								.append(item.getIsBoss() ? "1" : "0")
								.append("</main>");
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
				rawData.append("</ws:createBundleGroup>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_createBundleGroup");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);
				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				NodeList nodechildListSerial = null;
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
	public void onAddSub(int position) {
		if (!BundleBusiness.checkAdd(lstConfigDetail,
				lstConfigDetail.get(position))) {
			CommonActivity.createAlertDialog(
					getActivity(),
					MessageFormat.format(getString(R.string.canot_add_bundle),
							lstConfigDetail.get(position).getToValue()),
					getString(R.string.app_name_title)).show();
			return;
		}
		ProductBundleGroupsConfigDetail item = lstConfigDetail.get(position);
		ProductBundleGroupsConfigDetail addItem = BundleBusiness
				.cloneProductBundleGroupsConfigDetail(item);
		addItem.setIsdnAccount("");
		lstConfigDetail.add(position, addItem);
		// adapter = new BundleConfigDetailAdapter(lstConfigDetail,
		// MainActivity.getInstance(), FragmentBundleGroup.this,
		// FragmentBundleGroup.this, FragmentBundleGroup.this);
		// listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onDeleteSub(int position) {
		if (!BundleBusiness.checkDel(lstConfigDetail,
				lstConfigDetail.get(position))) {

			CommonActivity.createAlertDialog(
					getActivity(),
					MessageFormat.format(getString(R.string.canot_del_bundle),
							lstConfigDetail.get(position).getFromValue()),
					getString(R.string.app_name_title)).show();
			return;
		}
		ProductBundleGroupsConfigDetail item = lstConfigDetail.get(position);
		int fromValue = Integer.parseInt(item.getFromValue());
		int countSub = BundleBusiness.countSub(lstConfigDetail, item);

		if (countSub == 1 || countSub == fromValue) {
			item.setIsdnAccount("");
			item.setShortNumberInput("");
			item.setBoss("0");
		} else {
			lstConfigDetail.remove(position);
		}
		// adapter = new BundleConfigDetailAdapter(lstConfigDetail,
		// MainActivity.getInstance(),
		// FragmentBundleGroup.this, FragmentBundleGroup.this,
		// FragmentBundleGroup.this);
		// listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onChangeSub(int position) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.bundle_input_sub_dialog);
		final ProductBundleGroupsConfigDetail item = lstConfigDetail
				.get(position);
		final EditText edtSub = (EditText) dialog.findViewById(R.id.edtSub);
		edtSub.setText(item.getIsdnAccount());

		final EditText edtShortNumber = (EditText) dialog
				.findViewById(R.id.edtShortNumber);
		edtShortNumber.setText(item.getShortNumberInput());
		final CheckBox cbBoss = (CheckBox) dialog.findViewById(R.id.cbBoss);
		cbBoss.setChecked(item.getIsBoss());
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

						if (cbBoss.isChecked()) {
							for (ProductBundleGroupsConfigDetail tmp : lstConfigDetail) {
								tmp.setIsBoss(false);
							}
						}
						item.setIsBoss(cbBoss.isChecked());
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

	@Override
	public void setPermission() {
		permission = ";bundle.group;";
		
	}
}
