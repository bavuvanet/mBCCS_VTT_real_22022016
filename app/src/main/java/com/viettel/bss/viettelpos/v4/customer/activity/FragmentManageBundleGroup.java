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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleGroupAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleGroupAdapter.OnDeleteGroup;
import com.viettel.bss.viettelpos.v4.customer.adapter.BundleGroupAdapter.OnViewGroupDetail;
import com.viettel.bss.viettelpos.v4.customer.object.BundleGroup;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentManageBundleGroup extends FragmentCommon implements
		OnDeleteGroup, OnViewGroupDetail {
	private ListView listView;
	private BundleGroupAdapter adapter;
	private List<BundleGroup> lstBundleGroup;
    private EditText edtIsdn;
	private EditText edtIdNo;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setTitleActionBar(R.string.manage_bundle_group);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_bundle_group_manage;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void unit(View v) {
		edtIdNo = (EditText) v.findViewById(R.id.edtIdNo);
		edtIsdn = (EditText) v.findViewById(R.id.edtIsdn);
        Button btnSearchBundleGroup = (Button) v
                .findViewById(R.id.btnSearchBundleGroup);
		listView = (ListView) v.findViewById(R.id.lvBundleGroup);
		btnSearchBundleGroup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (edtIdNo.getText().toString().trim().isEmpty()
						&& edtIsdn.getText().toString().trim().isEmpty()) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getString(R.string.input_a_search_condition),
							getString(R.string.app_name));
					dialog.show();
					return;
				}
				AsynctaskGetListBundleGroup asy = new AsynctaskGetListBundleGroup(
						getActivity());
				asy.execute();
			}
		});

        Button btnCreateBundle = (Button) v.findViewById(R.id.btnCreateBundle);
		btnCreateBundle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				FragmentCreateBundleGroup createBundleGroup = new FragmentCreateBundleGroup();
				ReplaceFragment.replaceFragment(getActivity(),
						createBundleGroup, false);
			}
		});
	}

	private class AsynctaskGetListBundleGroup extends
			AsyncTask<Void, Void, ArrayList<BundleGroup>> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;

		public AsynctaskGetListBundleGroup(Activity mActivity) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			String msg = mActivity.getResources().getString(
					R.string.getting_group_bundle);
			this.progress.setMessage(msg);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<BundleGroup> doInBackground(Void... params) {
			return searchBundleGroup();
		}

		@Override
		protected void onPostExecute(ArrayList<BundleGroup> result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				lstBundleGroup = result;
				adapter = new BundleGroupAdapter(lstBundleGroup, getActivity(),
						FragmentManageBundleGroup.this,
						FragmentManageBundleGroup.this);
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

		private ArrayList<BundleGroup> searchBundleGroup() {
			ArrayList<BundleGroup> result = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_searchGroupBundle");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:searchGroupBundle>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<idNo>").append(TextUtils.htmlEncode(edtIdNo.getText().toString()
                        .trim())).append("</idNo>");
				rawData.append("<isdn>").append(TextUtils.htmlEncode(edtIsdn.getText().toString()
                        .trim())).append("</isdn>");
				rawData.append("</input>");
				rawData.append("</ws:searchGroupBundle>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_searchGroupBundle");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);
				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				NodeList nodechild = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);
					nodechild = doc.getElementsByTagName("lstGroup");
					for (int j = 0; j < nodechild.getLength(); j++) {
						Element e1 = (Element) nodechild.item(j);
						BundleGroup item = new BundleGroup();
						item.setCode(parse.getValue(e1, "code"));
						item.setGroupId(parse.getValue(e1, "groupId"));
						item.setIsdn(parse.getValue(e1, "isdn"));
						item.setName(parse.getValue(e1, "name"));
						item.setStatus(parse.getValue(e1, "status"));
						item.setCreateDatetime(parse.getValue(e1,
								"createDatetime"));
						item.setProductBundleGroupsConfigId(parse.getValue(e1,
								"productBundleGroupsConfigId"));
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
	private class AsynctaskDeleteBundleGroup extends
			AsyncTask<Void, Void, Void> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		final BundleGroup item;

		public AsynctaskDeleteBundleGroup(Activity mActivity, BundleGroup item) {
			this.mActivity = mActivity;
			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.item = item;
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.deleteting_bundle));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			deleteBundleGroup();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
				description = getString(R.string.delete_bundle_success);

				final OnClickListener deleteGroupClick = new OnClickListener() {
					public void onClick(View arg0) {
						for (int i = 0; i < lstBundleGroup.size(); i++) {
							if (lstBundleGroup.get(i).getGroupId()
									.equals(item.getGroupId())) {
								lstBundleGroup.remove(i);

								break;
							}
						}
						adapter = new BundleGroupAdapter(lstBundleGroup,
								getActivity(), FragmentManageBundleGroup.this,
								FragmentManageBundleGroup.this);
						listView.setAdapter(adapter);
					}

                };

				Dialog dialog = CommonActivity
						.createAlertDialog(getActivity(), description,
								getResources().getString(R.string.app_name),
								deleteGroupClick);
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

		private void deleteBundleGroup() {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_deleteBundleGroup");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:deleteBundleGroup>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<groupId>").append(item.getGroupId()).append("</groupId>");
				rawData.append("<reasonId>" + "146" + "</reasonId>");

				rawData.append("</input>");
				rawData.append("</ws:deleteBundleGroup>");
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
	public void onViewGroupDetail(int position) {
		FragmentEditBundleGroup createBundleGroup = new FragmentEditBundleGroup();

		Bundle b = new Bundle();
		b.putSerializable("bundleGroup", lstBundleGroup.get(position));
		createBundleGroup.setArguments(b);
		ReplaceFragment
				.replaceFragment(getActivity(), createBundleGroup, false);
	}

	@Override
	public void onDeleteGroup(int position) {
		final BundleGroup group = lstBundleGroup.get(position);
		String confirmMsg = getString(R.string.confirm_delete_bundle_group);
		final OnClickListener deleteGroupClick = new OnClickListener() {
			public void onClick(View arg0) {
				AsynctaskDeleteBundleGroup asy = new AsynctaskDeleteBundleGroup(
						getActivity(), group);
				asy.execute();
			}
        };
		Dialog dialog = CommonActivity.createDialog(getActivity(),
				MessageFormat.format(confirmMsg, group.getCode()),
				getActivity().getString(R.string.app_name), getResources()
						.getString(R.string.ok),
				getResources().getString(R.string.cancel), deleteGroupClick,
				null);
		dialog.show();
	}

	@Override
	public void setPermission() {
		permission = ";bundle.group;";

	}
}
