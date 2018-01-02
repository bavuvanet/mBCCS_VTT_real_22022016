package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.adapter.CustomerDebitInfoAdapter;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.customer.object.CustIdentityBO;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class FragmentLookupDebitInfo extends FragmentCommon {

	private ArrayList<CustIdentityBO> lstCustIdentityBOs = new ArrayList<>();
	private CustomerDebitInfoAdapter adapter;
	private CustIdentityBO custIdentityBO;
	private ViewHolder holder;
	private String funtionType = Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO;
	private String idNo = "";
	private String isdnOrAccount = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		idLayout = R.layout.layout_lookup_debit_info;
		Bundle bundle = getArguments();
		if (bundle != null) {
			funtionType = bundle.getString(Constant.FUNCTION,
					Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO);
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		holder = new ViewHolder();
		holder.edtIdNO = (EditText) v.findViewById(R.id.edtIdNO);

		holder.edtIsdnOrAccount = (EditText) v
				.findViewById(R.id.edtIsdnOrAccount);

		holder.btnSearch = (Button) v.findViewById(R.id.btnSearch);
		holder.lnCustomerInfo = (LinearLayout) v
				.findViewById(R.id.lnCustomerInfo);
		holder.lvCustomerInfo = (ListView) v.findViewById(R.id.lvCustomerInfo);
		holder.lvCustomerInfo
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						custIdentityBO = (CustIdentityBO) adapter
								.getItem(position);

						new AsySearchAccountDebitInfo(getActivity()).execute();
					}
				});

		holder.btnSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (CommonActivity.isNullOrEmpty(holder.edtIdNO.getText()
						.toString())
						&& CommonActivity.isNullOrEmpty(holder.edtIsdnOrAccount
								.getText().toString())) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources().getString(
									R.string.txt_idno_isdn_required),
							getResources().getString(R.string.app_name));
					dialog.show();
					return;
				}

				if (StringUtils.CheckCharSpecical(holder.edtIdNO.getText()
						.toString())
						|| StringUtils
								.CheckCharSpecical(holder.edtIsdnOrAccount
										.getText().toString())) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							getResources()
									.getString(R.string.checkcharspecical),
							getResources().getString(R.string.app_name));
					dialog.show();
					return;
				}

				new AsySearchCustomerDebitInfo(getActivity()).execute();

			}
		});

		holder.edtIdNO.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable edt) {
				// TODO Auto-generated method stub
				if (!idNo.equals(edt.toString())) {
					holder.lnCustomerInfo.setVisibility(View.GONE);
				}
				idNo = edt.toString();
			}
		});

		holder.edtIsdnOrAccount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable edt) {
				// TODO Auto-generated method stub
				if (!isdnOrAccount.equals(edt.toString())) {
					holder.lnCustomerInfo.setVisibility(View.GONE);
				}
				isdnOrAccount = edt.toString();
			}
		});

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (funtionType.equals(Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO)) {
            setTitleActionBar(R.string.txt_lookup_debit_info);
		} else {
            setTitleActionBar(R.string.txt_update_isdn_email);
		}

		Log.d(getClass().getSimpleName(), "=>>>>>>>>>>>>>>>>>> onResume");
	}

	class ViewHolder {
		EditText edtIdNO;
		EditText edtIsdnOrAccount;
		Button btnSearch;
		LinearLayout lnCustomerInfo;
		ListView lvCustomerInfo;
	}

	private class AsySearchCustomerDebitInfo extends
			AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;
		final ProgressDialog progress;

		public AsySearchCustomerDebitInfo(Activity mActivity) {

			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

			holder.lnCustomerInfo.setVisibility(View.GONE);
		}

		@Override
		protected BOCOutput doInBackground(String... params) {
			return searchCustomerDebitInfo();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (result.getErrorCode().equals("0")) {
				if (CommonActivity
						.isNullOrEmpty(result.getLstCustIdentityBOs())
						&& CommonActivity.isNullOrEmpty(result
								.getLstAccountDTOs())) {
					lstCustIdentityBOs.clear();
					if (adapter != null) {
						adapter.notifyDataSetChanged();
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							mActivity.getResources().getString(
									R.string.txt_search_invalid_info),
							getResources().getString(R.string.app_name));
					dialog.show();
					return;
				}

				if (!CommonActivity.isNullOrEmptyArray(result
						.getLstCustIdentityBOs())) {
					holder.lnCustomerInfo.setVisibility(View.VISIBLE);
					// hien thi thong tin khach hang
					lstCustIdentityBOs = result.getLstCustIdentityBOs();
					// if (adapter == null) {
					adapter = new CustomerDebitInfoAdapter(mActivity,
							lstCustIdentityBOs);
					holder.lvCustomerInfo.setAdapter(adapter);
				}

				if (!CommonActivity.isNullOrEmpty(result.getLstAccountDTOs())) {
					// chuyen sang fragment chi tiet thong tin account
					FragmentAccountInfo fragmentAccountInfo = new FragmentAccountInfo();
					Bundle bundle = new Bundle();
					bundle.putSerializable("LST_ACCOUNT_INFO",
							result.getLstAccountDTOs());
					bundle.putString(Constant.FUNCTION, funtionType);

					if (!CommonActivity
							.isNullOrEmpty(result.getLstSubBeanBOs())) {
						bundle.putParcelableArrayList("LST_SUB_BEAN",
								result.getLstSubBeanBOs());

					}
					fragmentAccountInfo.setArguments(bundle);

					ReplaceFragment.replaceFragment(getActivity(),
							fragmentAccountInfo, true);
                }

				// } else {
				// adapter.notifyDataSetChanged();
				// }
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

		private BOCOutput searchCustomerDebitInfo() {
			BOCOutput bocOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "doSearchInfoPay";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);

				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				if (!CommonActivity.isNullOrEmpty(holder.edtIdNO.getText()
						.toString())) {
					rawData.append("<documentNo>").append(StringUtils.escapeHtml(holder.edtIdNO.getText()
                            .toString().trim())).append("</documentNo>");
				}

				if (!CommonActivity.isNullOrEmpty(holder.edtIsdnOrAccount
						.getText().toString())) {
					rawData.append("<isdn>").append(StringUtils.escapeHtml(holder.edtIsdnOrAccount
                            .getText().toString().trim())).append("</isdn>");
				}

				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<type>").append(funtionType).append("</type>");

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
				Log.e(Constant.TAG, "doSearchInfoPay", e);
				bocOutput = new BOCOutput();
				bocOutput.setDescription(e.getMessage());
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return bocOutput;
		}

	}

	private class AsySearchAccountDebitInfo extends
			AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;
		final ProgressDialog progress;

		public AsySearchAccountDebitInfo(Activity mActivity) {

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
			return searchAccountDebitInfo();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();

			if (result.getErrorCode().equals("0")) {
				if (CommonActivity.isNullOrEmpty(result.getLstAccountDTOs())) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							mActivity.getResources().getString(
									R.string.txt_search_invalid_info),
							getResources().getString(R.string.app_name));
					dialog.show();
					return;
				}

				FragmentAccountInfo fragmentAccountInfo = new FragmentAccountInfo();
				Bundle bundle = new Bundle();
				bundle.putSerializable("LST_ACCOUNT_INFO",
						result.getLstAccountDTOs());
				bundle.putString(Constant.FUNCTION, funtionType);
				fragmentAccountInfo.setArguments(bundle);

				ReplaceFragment.replaceFragment(getActivity(),
						fragmentAccountInfo, true);

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

		private BOCOutput searchAccountDebitInfo() {
			BOCOutput bocOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "doSearchInfoPay";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);

				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<documentNo>").append(custIdentityBO.getIdNo()).append("</documentNo>");
				rawData.append("<custId>").append(custIdentityBO.getCustomer().getCustId()).append("</custId>");

				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<type>").append(funtionType).append("</type>");

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
				Log.e(Constant.TAG, "doSearchInfoPay", e);
				bocOutput = new BOCOutput();
				bocOutput.setDescription(e.getMessage());
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return bocOutput;
		}

	}

	@Override
	public void setPermission() {
		permission = Constant.VSAMenu.LOOKUP_DEBIT_INFO;

	}

}
