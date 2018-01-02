package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDelCTV;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReflectionUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.adapter.AccountInfoAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.SubBeanAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.UpdateAccountInfoAdapter;
import com.viettel.bss.viettelpos.v4.customer.object.AccountBO;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SubBeanBO;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.object.DataMapping;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

public class FragmentAccountInfo extends FragmentCommon {

	private ListView lvAccount;
	private ArrayList<AccountBO> lstAccountBOs = new ArrayList<>();
	private AccountBO accountBO;
	private AccountInfoAdapter adapter;
	private String funtionType = Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO;
    private Toolbar toolbar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		idLayout = R.layout.layout_account_info;

		lstAccountBOs = (ArrayList<AccountBO>)getArguments().getSerializable("LST_ACCOUNT_INFO");

		Bundle bundle = getArguments();
		if (bundle != null) {
			funtionType = bundle.getString(Constant.FUNCTION,
					Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO);
		}

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        setTitleActionBar(R.string.contractInfo);
	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		adapter = new AccountInfoAdapter(getActivity(), lstAccountBOs,
				funtionType);
		lvAccount = (ListView) v.findViewById(R.id.lvAccount);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

		lvAccount.setAdapter(adapter);
		lvAccount.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				accountBO = (AccountBO) adapter.getItem(position);
				if (Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO
						.equals(funtionType)) {
					new AsyViewDetailDebitInfo(getActivity()).execute();
				} else {
					if (!"2".equals(accountBO.getStatus())) {
						CommonActivity
								.createAlertDialog(
										getActivity(),
										getResources()
												.getString(
														R.string.txt_update_account_status_invalid),
										getResources().getString(
												R.string.app_name)).show();
						return;
					}
					showDialogUpdateAccountInfo(accountBO);
				}
			}
		});

		ArrayList<SubBeanBO> lstSubBeanBOs = getArguments()
				.getParcelableArrayList("LST_SUB_BEAN");
		if (!CommonActivity.isNullOrEmpty(lstSubBeanBOs)) {
			showDetailDebitInfo(lstSubBeanBOs);
		}
	}

	private class AsyViewDetailDebitInfo extends
			AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;
		final ProgressDialog progress;

		public AsyViewDetailDebitInfo(Activity mActivity) {

			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected BOCOutput doInBackground(String... params) {
			return viewDetailDebitInfo();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();

			if (result.getErrorCode().equals("0")) {
				if (CommonActivity.isNullOrEmpty(result.getLstSubBeanBOs())) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							mActivity.getResources().getString(
									R.string.txt_search_invalid_info),
							getResources().getString(R.string.app_name));
					dialog.show();
					return;
				}

				showDetailDebitInfo(result.getLstSubBeanBOs());
				// hien thi thong tin khach hang
			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(mActivity, result
									.getDescription(), mActivity.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (result.getDescription() == null
							|| result.getDescription().isEmpty()) {
						result.setDescription(mActivity.getResources()
								.getString(R.string.checkdes));
					}

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), result.getDescription(),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private BOCOutput viewDetailDebitInfo() {
			BOCOutput bocOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "getDebitInfoList";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);

				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<accountId>").append(accountBO.getAccountId()).append("</accountId>");

				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");

				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());

				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), "mbccs_"
								+ methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Serializer serializer = new Persister();
				bocOutput = serializer.read(BOCOutput.class, original);
				if (bocOutput == null) {
					bocOutput = new BOCOutput();
					bocOutput
							.setDescription(getString(R.string.no_return_from_system));
					bocOutput.setErrorCode(Constant.ERROR_CODE);
					return bocOutput;
				} else {
					return bocOutput;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "viewDetailDebitInfo", e);
				bocOutput = new BOCOutput();
				bocOutput.setDescription(e.getMessage());
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return bocOutput;
		}

	}

	private SubBeanAdapter subAdapter;

	private void showDetailDebitInfo(ArrayList<SubBeanBO> lstSubBeanBOs) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.item_debit_info);
		dialog.setCancelable(true);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(lp);

		TextView tvHeader = (TextView) dialog.findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(
				R.string.txt_chi_tiet_cong_no, accountBO.getAccountNo()));

		ListView lvDebit = (ListView) dialog.findViewById(R.id.lvDebit);
		subAdapter = new SubBeanAdapter(getActivity(), lstSubBeanBOs);
		lvDebit.setAdapter(subAdapter);

		LinearLayout lnClose = (LinearLayout) dialog.findViewById(R.id.lnClose);
		lnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		lvDebit.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				SubBeanBO subBeanBO = (SubBeanBO) subAdapter.getItem(position);
				Intent intent = new Intent(getActivity(),FragmentChargeDelCTV.class);
				intent.putExtra("SUB_BEAN_ISDN", subBeanBO.getIsdn());
				startActivity(intent);
//				FragmentChargeDelCTV fragment = new FragmentChargeDelCTV();
//				Bundle bundler = new Bundle();
//				bundler.putString("SUB_BEAN_ISDN", subBeanBO.getIsdn());
//
//				fragment.setArguments(bundler);
//
//				ReplaceFragment.replaceFragment(getActivity(), fragment, true);

				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private UpdateAccountInfoAdapter updateAccountAdapter;
	private Dialog dialogUpdateAccount;

	private void showDialogUpdateAccountInfo(final AccountBO accountBO) {
		dialogUpdateAccount = new Dialog(getActivity());
		dialogUpdateAccount.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogUpdateAccount.setContentView(R.layout.dialog_update_account_info);
		dialogUpdateAccount.setCancelable(false);

		TextView tvHeader = (TextView) dialogUpdateAccount
				.findViewById(R.id.tvTitle);
		tvHeader.setText(getResources().getString(
				R.string.title_update_account, accountBO.getAccountNo()));

		ListView lvProperty = (ListView) dialogUpdateAccount
				.findViewById(R.id.lvUpdate);
		lvProperty.setItemsCanFocus(true);

		updateAccountAdapter = new UpdateAccountInfoAdapter(getActivity(),
				initDataMapping(accountBO));
		lvProperty.setAdapter(updateAccountAdapter);

		Button btnDongY = (Button) dialogUpdateAccount.findViewById(R.id.btnOK);
		btnDongY.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonActivity
						.createDialog(
								act,
								getResources().getString(
										R.string.txt_update_account_confirm,
										accountBO.getAccountNo()),
								getString(R.string.app_name),
								getString(R.string.cancel),
								getString(R.string.ok),
								null,updateAccountListener ).show();
			}
		});

		Button btnCancel = (Button) dialogUpdateAccount
				.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialogUpdateAccount.dismiss();
			}
		});

		dialogUpdateAccount.show();
	}

	private final OnClickListener updateAccountListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			boolean isUpdate = false;
			for (DataMapping mapping : updateAccountAdapter.getLstData()) {
				if (!CommonActivity.isNullOrEmpty(mapping.getValue())) {
					isUpdate = true;
					break;
				}
			}

			if (!isUpdate) {
				CommonActivity.createAlertDialog(
						getActivity(),
						getResources().getString(
								R.string.txt_update_account_required),
						getResources().getString(R.string.app_name)).show();
				return;
			}

			new AsyUpdateAccountInfo(getActivity()).execute();
		}
	};

	private class AsyUpdateAccountInfo extends
			AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;
		final ProgressDialog progress;

		public AsyUpdateAccountInfo(Activity mActivity) {

			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected BOCOutput doInBackground(String... params) {
			return updateAccountInfo();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();

			if (result.getErrorCode().equals("0")) {
				CommonActivity.createAlertDialog(
						mActivity,
						getResources().getString(
								R.string.txt_update_account_success,
								accountBO.getAccountNo()),
						getResources().getString(R.string.app_name)).show();

				resetValue(accountBO, updateAccountAdapter.getLstData());

				dialogUpdateAccount.dismiss();

				// getActivity().onBackPressed();

			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(mActivity, result
									.getDescription(), mActivity.getResources()
									.getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (result.getDescription() == null
							|| result.getDescription().isEmpty()) {
						result.setDescription(mActivity.getResources()
								.getString(R.string.checkdes));
					}

					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), result.getDescription(),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private BOCOutput updateAccountInfo() {
			BOCOutput bocOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "updateAccountInfo";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);

				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<accountId>").append(accountBO.getAccountId()).append("</accountId>");

				for (DataMapping mapping : updateAccountAdapter.getLstData()) {
					if (!CommonActivity.isNullOrEmpty(mapping.getValue())) {
						rawData.append("<").append(mapping.getCode()).append(">").append(StringUtils.escapeHtml(mapping.getValue()
                                .trim())).append("</").append(mapping.getCode()).append(">");
					}
				}

				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");

				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());

				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), "mbccs_"
								+ methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Serializer serializer = new Persister();
				bocOutput = serializer.read(BOCOutput.class, original);
				if (bocOutput == null) {
					bocOutput = new BOCOutput();
					bocOutput
							.setDescription(getString(R.string.no_return_from_system));
					bocOutput.setErrorCode(Constant.ERROR_CODE);
					return bocOutput;
				} else {
					return bocOutput;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "updateAccountInfo", e);
				bocOutput = new BOCOutput();
				bocOutput.setDescription(e.getMessage());
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return bocOutput;
		}

	}

	private List<DataMapping> initDataMapping(AccountBO accountBO) {
		List<DataMapping> lstDataMappings = new ArrayList<>();
		ApParamDAL apParamDAL = new ApParamDAL(getActivity());
		List<Spin> lstSpins = apParamDAL
				.getAppParam(Constant.PAR_NAME.UPDATE_ACCOUNT_INFO);

		if (!CommonActivity.isNullOrEmptyArray(lstSpins)) {
			for (Spin spin : lstSpins) {
				DataMapping mapping = new DataMapping();
				mapping.setCode(spin.getId());
				mapping.setCodeReflect(spin.getValue());
				mapping.setName(spin.getName());
				mapping.setValue(ReflectionUtils.getValue(accountBO,
						mapping.getCodeReflect()));

				lstDataMappings.add(mapping);
			}
		}
		return lstDataMappings;
	}

	private void resetValue(AccountBO accountBO,
			List<DataMapping> lstDataMappings) {
		for (DataMapping dataMapping : lstDataMappings) {
			ReflectionUtils.setValue(accountBO, dataMapping.getCodeReflect(),
					dataMapping.getValue());
		}
	}

	@Override
	public void setPermission() {
		permission = Constant.VSAMenu.LOOKUP_DEBIT_INFO;

	}
}
