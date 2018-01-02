package com.viettel.bss.viettelpos.v4.charge.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.adapter.CustomerSelectObjectAdapter;
import com.viettel.bss.viettelpos.v4.charge.adapter.CustomerSelectObjectAdapter.OnCheckSelectObject;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ParseOuputJson;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FragmentChooseContractFormMngt extends FragmentCommon implements
		OnCheckSelectObject {

	private ListView lvselect;
	private Button btnChoose;
	private Button btnReview;
	private EditText edtSearch;

	public static Map<String, ArrayList<ContractFormMngtBean>> hashMapContractFormMngtBean = new HashMap<String, ArrayList<ContractFormMngtBean>>();

	private CustomerSelectObjectAdapter customerSelectObjectAdapter = null;
	private ContractFormMngtBean contractFormMngtBean;
	private String functionCheckAll;

	private ArrayList<ContractFormMngtBean> lstContractFormMngtBean = new ArrayList<ContractFormMngtBean>();
	private String title = "";
	public static Map<String, ContractFormMngtBean> hashMapContract = new HashMap<String, ContractFormMngtBean>();

	private LinearLayout lnSearch;
	private CheckBox cball;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle mBundle = this.getArguments();
		if (mBundle != null) {
			functionCheckAll = mBundle.getString("functionCheckAll");
			contractFormMngtBean = (ContractFormMngtBean) mBundle
					.getSerializable("contractFormMngtBean");
		}
		idLayout = R.layout.layout_choose_contract_form_mngt;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (title == null || title.isEmpty()) {
			setTitleActionBar(getActivity().getString(
					R.string.selectobjectkd));
		} else {
			setTitleActionBar(title);
		}

	}

	private void onSearchHTHMBeans() {
		String keySearch = edtSearch.getText().toString().trim();
		if (keySearch.length() == 0) {
			// ArrayAdapter<HTHMBeans> itemsAdapter = new
			// ArrayAdapter<HTHMBeans>(this,
			// android.R.layout.simple_list_item_1, arrHthmBeans);
			// lv_hthm.setAdapter(itemsAdapter);
			customerSelectObjectAdapter = new CustomerSelectObjectAdapter(
					lstContractFormMngtBean, getActivity(),
					FragmentChooseContractFormMngt.this);
			lvselect.setAdapter(customerSelectObjectAdapter);
			customerSelectObjectAdapter.notifyDataSetChanged();
		} else {
			ArrayList<ContractFormMngtBean> items = new ArrayList<ContractFormMngtBean>();
			for (ContractFormMngtBean hBeans : lstContractFormMngtBean) {
				if (CommonActivity.convertUnicode2Nosign(
						(hBeans.getName()).toLowerCase()).contains(
						CommonActivity.convertUnicode2Nosign(keySearch
								.toLowerCase()))) {
					items.add(hBeans);
				}
			}
			customerSelectObjectAdapter = new CustomerSelectObjectAdapter(
					items, getActivity(), FragmentChooseContractFormMngt.this);
			lvselect.setAdapter(customerSelectObjectAdapter);
			customerSelectObjectAdapter.notifyDataSetChanged();
		}

	}

	@Override
	public void unit(View v) {

		lnSearch = (LinearLayout) v.findViewById(R.id.lnSearch);
		lnSearch.setVisibility(View.GONE);
		cball = (CheckBox) v.findViewById(R.id.cball);
		cball.setOnClickListener(this);

		edtSearch = (EditText) v.findViewById(R.id.edtSearch);
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				onSearchHTHMBeans();
			}
		});

		lvselect = (ListView) v.findViewById(R.id.lvselect);
		btnChoose = (Button) v.findViewById(R.id.btnLeft);
		btnChoose.setOnClickListener(this);
		btnReview = (Button) v.findViewById(R.id.btnRight);

		lvselect.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (!CommonActivity.isNullOrEmptyArray(lstContractFormMngtBean
						.get(arg2).getLstContractFormMngtBeanPayServ())) {
					Bundle mBundle = new Bundle();
					mBundle.putString("functionCheckAll", "");
					mBundle.putSerializable("contractFormMngtBean",
							lstContractFormMngtBean.get(arg2));
					FragmentChooseContractFormMngt fragmentChooseContractFormMngt = new FragmentChooseContractFormMngt();
					fragmentChooseContractFormMngt.setArguments(mBundle);
					ReplaceFragment.replaceFragment(getActivity(),
							fragmentChooseContractFormMngt, false);
				}
			}
		});

		if (contractFormMngtBean == null) {
			lnSearch.setVisibility(View.GONE);
			AsyncGetContractFormMngtN1N6 asyncGetContractFormMngtN1N6 = new AsyncGetContractFormMngtN1N6(
					getActivity());
			asyncGetContractFormMngtN1N6.execute();
		} else {
			lstContractFormMngtBean = contractFormMngtBean
					.getLstContractFormMngtBeanPayServ();
			lnSearch.setVisibility(View.VISIBLE);
			// if (!CommonActivity.isNullOrEmpty(functionCheckAll)) {
			// if (!CommonActivity.isNullOrEmptyArray(lstContractFormMngtBean))
			// {
			// for (ContractFormMngtBean item : lstContractFormMngtBean) {
			// item.setCheck(true);
			// if(item.isCheck()){
			// hashMapContract.put(item.getCode(), contractFormMngtBean);
			// }
			//
			// }
			// }
			// }else{
			// if (!CommonActivity.isNullOrEmptyArray(lstContractFormMngtBean))
			// {
			// for (ContractFormMngtBean item : lstContractFormMngtBean) {
			// item.setCheck(false);
			// if(item.isCheck()){
			// hashMapContract.put(item.getCode(), contractFormMngtBean);
			// }
			//
			// }
			// }
			// }
			customerSelectObjectAdapter = new CustomerSelectObjectAdapter(
					lstContractFormMngtBean, getActivity(),
					FragmentChooseContractFormMngt.this);
			lvselect.setAdapter(customerSelectObjectAdapter);
			customerSelectObjectAdapter.notifyDataSetChanged();
			title = contractFormMngtBean.getName();
		}
	}

	@Override
	public void setPermission() {

	}

	@Override
	public void onCheckSelectObject(ContractFormMngtBean contractFormMngtBean) {

		for (ContractFormMngtBean item : lstContractFormMngtBean) {
			if (item.getCode().equals(contractFormMngtBean.getCode())) {
				item.setCheck(!item.isCheck());
				hashMapContract.put(item.getCode(), contractFormMngtBean);
				break;
			}
		}
		if (!CommonActivity.isNullOrEmptyArray(contractFormMngtBean
				.getLstContractFormMngtBeanPayServ())) {
			// contractFormMngtBean.setCheck(!contractFormMngtBean.isCheck());
			//
			// if(contractFormMngtBean.isCheck()){
			Bundle mBundle = new Bundle();
			mBundle.putString("functionCheckAll", "1");
			mBundle.putSerializable("contractFormMngtBean",
					contractFormMngtBean);
			FragmentChooseContractFormMngt fragmentChooseContractFormMngt = new FragmentChooseContractFormMngt();
			fragmentChooseContractFormMngt.setArguments(mBundle);
			ReplaceFragment.replaceFragment(getActivity(),
					fragmentChooseContractFormMngt, false);
			// }else{
			// for (ContractFormMngtBean item :
			// contractFormMngtBean.getLstContractFormMngtBeanPayServ()) {
			// item.setCheck(contractFormMngtBean.isCheck());
			// hashMapContract.put(item.getCode(), item);
			// }
			// }

		}
	}

	private ContractFormMngtBean resetValueContractClone(
			ContractFormMngtBean contractFormMngtBean) {
		contractFormMngtBean.setCheck(false);
		if (!CommonActivity.isNullOrEmptyArray(contractFormMngtBean
				.getLstContractFormMngtBeanPayServ())) {
			for (ContractFormMngtBean item : contractFormMngtBean
					.getLstContractFormMngtBeanPayServ()) {
				resetValueContractClone(item);
			}
		}
		return contractFormMngtBean;
	}

	private class AsyncGetContractFormMngtN1N6 extends
			AsyncTask<String, Void, ArrayList<ContractFormMngtBean>> {

		private Context mContext;
		private String errorCode;
		private String description;
		private ProgressDialog progress;

		public AsyncGetContractFormMngtN1N6(Context context) {
			this.mContext = context;
			this.progress = new ProgressDialog(this.mContext);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdatareport));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ContractFormMngtBean> doInBackground(String... arg0) {
			return getListContractFormMngtBean();
		}

		@Override
		protected void onPostExecute(ArrayList<ContractFormMngtBean> result) {
			super.onPostExecute(result);
			this.progress.dismiss();
			if ("0".equals(errorCode)) {
				if (!CommonActivity.isNullOrEmptyArray(result)) {

					lstContractFormMngtBean = result;

					hashMapContractFormMngtBean = new HashMap<String, ArrayList<ContractFormMngtBean>>();
					ArrayList<ContractFormMngtBean> arrCorporationCategoryBO = new ArrayList<ContractFormMngtBean>();
					for (ContractFormMngtBean corporationCategoryBO : lstContractFormMngtBean) {
						arrCorporationCategoryBO
								.add(resetValueContractClone(corporationCategoryBO));
					}
					hashMapContractFormMngtBean.put(Constant.NEW_CONTRACT,
							arrCorporationCategoryBO);

					if (!CommonActivity
							.isNullOrEmptyArray(lstContractFormMngtBean)) {
						customerSelectObjectAdapter = new CustomerSelectObjectAdapter(
								lstContractFormMngtBean, getActivity(),
								FragmentChooseContractFormMngt.this);
						lvselect.setAdapter(customerSelectObjectAdapter);
					}

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.no_data),
							getActivity().getString(R.string.app_name));
				}
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog((Activity) mContext,
									description, mContext.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mContext.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<ContractFormMngtBean> getListContractFormMngtBean() {
			String original = null;
			ArrayList<ContractFormMngtBean> lstCorporation = new ArrayList<ContractFormMngtBean>();
			try {

				if (hashMapContractFormMngtBean != null
						&& hashMapContractFormMngtBean.size() > 0) {
					lstCorporation.addAll(hashMapContractFormMngtBean
							.get(Constant.NEW_CONTRACT));
				}
				if (!CommonActivity.isNullOrEmptyArray(lstCorporation)) {
					errorCode = "0";
					return lstCorporation;
				}

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_getContractFormMngtN1N6V1");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getContractFormMngtN1N6V1>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");

				rawData.append("</input>");
				rawData.append("</ws:getContractFormMngtN1N6V1>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				original = input.sendRequestJson(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getContractFormMngtN1N6V1");

				Log.i("Responseeeeeeeeee", original);

				Gson gson = new Gson();
				ParseOuputJson parseOuput = gson.fromJson(original,
						ParseOuputJson.class);

				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					lstCorporation = parseOuput
							.getLstContractFormMngtBeanPayServs();
				}
			} catch (Exception e) {
				Log.d("getContractFormMngtN1N6V1 : ex", e.toString());
			}

			return lstCorporation;
		}

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btnLeft:
			if (contractFormMngtBean != null
					&& !CommonActivity.isNullOrEmptyArray(contractFormMngtBean
							.getLstContractFormMngtBeanPayServ())) {
				getActivity().onBackPressed();
			} else {
				if (hashMapContract != null && hashMapContract.size() > 0) {
					ArrayList<ContractFormMngtBean> lstContract = new ArrayList<ContractFormMngtBean>();
					lstContract.addAll(hashMapContract.values());
					Intent i = new Intent();
					i.putExtra("lstContract", lstContract);
					getTargetFragment().onActivityResult(
							getTargetRequestCode(), Activity.RESULT_OK, i);
					getActivity().onBackPressed();

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.checkobject),
							getActivity().getString(R.string.app_name)).show();
				}
			}
			break;
		case R.id.cball:

			List<ContractFormMngtBean> items = new ArrayList<ContractFormMngtBean>();
			for (int i = 0; i < customerSelectObjectAdapter.getCount(); i++) {
				items.add(customerSelectObjectAdapter.getItem(i));
			}

			if (!CommonActivity.isNullOrEmptyArray(items)) {
				for (ContractFormMngtBean item : lstContractFormMngtBean) {
					for (ContractFormMngtBean tmp : items) {
						if (item.getCode().equals(tmp.getCode())) {
							item.setCheck(cball.isChecked());
							hashMapContract.put(item.getCode(), item);
						}
					}
				}
			}

			customerSelectObjectAdapter.notifyDataSetChanged();

			break;
		default:
			break;
		}
	}

}
