package com.viettel.bss.viettelpos.v4.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.adapter.ProductSpecCharAdapter;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.customer.object.ProductSpecCharObj;
import com.viettel.bss.viettelpos.v4.customer.object.ProductSpecCharValueObj;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class FragmentCollectCusInfo extends FragmentCommon implements
		OnClickListener {
	private static final String TAG = "FragmentCollectCusInfo";
	private ViewHolder holder;
	private ProductSpecCharAdapter adapter;
	private String custCollectId = "";
//    private static final String OTHER = "OTHER";
	private String idNo = "";

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		idLayout = R.layout.layout_collect_customer_info;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
		holder = new ViewHolder();
		holder.edtIdNo = (EditText) v.findViewById(R.id.edtIdNo);
		holder.edtIdNo.addTextChangedListener(new TextWatcher() {

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
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				holder.lnCollect.setVisibility(View.GONE);
			}
		});

		holder.edtIsdnOrAccount = (EditText) v
				.findViewById(R.id.edtIsdnOrAccount);
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
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				holder.lnCollect.setVisibility(View.GONE);
			}
		});

		holder.btnSearch = (Button) v.findViewById(R.id.btnSearch);
		holder.btnSearch.setOnClickListener(this);

		holder.btnCollect = (Button) v.findViewById(R.id.btnCollect);
		holder.btnCollect.setOnClickListener(this);

		holder.lnCollect = (LinearLayout) v.findViewById(R.id.lnCollect);

		// holder.spnCollect = (Spinner) v.findViewById(R.id.spnCollect);
		holder.lvCollectInfo = (ListView) v.findViewById(R.id.lvCollectInfo);
		holder.lvCollectInfo
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						showDialogInput(adapter.getLstData().get(position));

					}
				});
		holder.tvHeader = (TextView) v.findViewById(R.id.tvHeader);

		holder.cbCancel = (CheckBox) v.findViewById(R.id.cbCancel);
		holder.cbCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (holder.cbCancel.isChecked()) {
					holder.lvCollectInfo.setVisibility(View.GONE);
					// holder.cbCancel.setTextColor(getResources().getColor(R.color.red));
				} else {
					holder.lvCollectInfo.setVisibility(View.VISIBLE);
				}
			}
		});

		// initSpinner(holder.spnCollect);

	}

	/**
	 * Ham khoi tao spinner
	 * 
	 * @param spinner
	 */
	private void initSpinner(Spinner spinner) {
		try {
			ApParamDAL apDal = new ApParamDAL(getActivity());
			apDal.open();

			List<Spin> lstSpins = apDal
					.getAppParam(Constant.PAR_NAME.COLLECT_CUS_INFO);
			Utils.setDataSpinner(getActivity(), lstSpins, spinner);

			apDal.close();
		} catch (Exception ex) {
			Log.d(getClass().getSimpleName(), "Error when initSpinner: " + ex);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);

		switch (v.getId()) {
		case R.id.btnSearch:

			if (CommonActivity.isNullOrEmpty(holder.edtIdNo.getText()
					.toString().trim())
					&& CommonActivity.isNullOrEmpty(holder.edtIsdnOrAccount
							.getText().toString().trim())) {
				CommonActivity.createAlertDialog(getActivity(),
						R.string.checktt, R.string.app_name).show();
				return;
			}

			holder.cbCancel.setChecked(false);
			new AsyGetCusInfoCollect(getActivity()).execute();
			break;
		case R.id.btnCollect:
			if (!validateCusInfoCollect()) {
				return;
			}

			CommonActivity.createDialog(
					getActivity(),
					getString(R.string.txt_collect_cus_info_confirm,
							holder.edtIdNo.getText().toString().trim()),
					getString(R.string.app_name), getString(R.string.ok),
					getString(R.string.cancel), collectListener, null).show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MainActivity.getInstance().setTitleActionBar(R.string.title_collect_cus_info);
	}

	class ViewHolder {
		EditText edtIdNo;
		EditText edtIsdnOrAccount;
		// Spinner spnCollect;
		Button btnSearch;
		Button btnCollect;
		CheckBox cbCancel;

		ListView lvCollectInfo;
		LinearLayout lnCollect;
		TextView tvHeader;
	}

	private class AsyGetCusInfoCollect extends
			AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;
		final ProgressDialog progress;

		public AsyGetCusInfoCollect(Activity mActivity) {

			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

			holder.lnCollect.setVisibility(View.GONE);
			custCollectId = "";
		}

		@Override
		protected BOCOutput doInBackground(String... params) {
			return getCusInfoCollect();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (result.getErrorCode().equals("0")) {
				if (CommonActivity.isNullOrEmpty(result
						.getLstProductSpecCharDTOs())) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(),
							mActivity.getResources().getString(
									R.string.txt_search_invalid_info),
							getResources().getString(R.string.app_name));
					dialog.show();
					return;
				}

				custCollectId = result.getCustCollectId();

                String isCompany = result.getIsCompany();

				if (!CommonActivity.isNullOrEmpty(result.getIdNo())) {
					idNo = result.getIdNo();
				} else {
					idNo = holder.edtIdNo.getText().toString().trim();
				}

                String isdnOrAccount = "";
                if (!CommonActivity.isNullOrEmpty(result.getAccountOrIsdn())) {
					isdnOrAccount = result.getAccountOrIsdn();
				} else {
					isdnOrAccount = holder.edtIsdnOrAccount.getText()
							.toString();
				}

				holder.tvHeader.setText(getString(
						R.string.txt_collect_cus_info, holder.edtIdNo.getText()
								.toString()));

				adapter = new ProductSpecCharAdapter(mActivity,
						result.getLstProductSpecCharDTOs());
				holder.lvCollectInfo.setAdapter(adapter);
				holder.lnCollect.setVisibility(View.VISIBLE);

				if (result.isRefuseCollect()) {
					holder.cbCancel.setChecked(true);
					holder.lvCollectInfo.setVisibility(View.GONE);
				} else {
					holder.cbCancel.setChecked(false);
					holder.lvCollectInfo.setVisibility(View.VISIBLE);
				}

				if (!CommonActivity.isNullOrEmpty(custCollectId)) {
					holder.btnCollect
							.setText(getString(R.string.button_update));
				}

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

		private BOCOutput getCusInfoCollect() {
			BOCOutput saleOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "getCusInfoCollect";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);

				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				if (!CommonActivity.isNullOrEmpty(holder.edtIdNo.getText()
						.toString())) {
					rawData.append("<documentNo>").append(StringUtils.escapeHtml(holder.edtIdNo.getText()
                            .toString())).append("</documentNo>");
				}

				if (!CommonActivity.isNullOrEmpty(holder.edtIsdnOrAccount
						.getText().toString())) {
					String isdn = StringUtils.formatMsisdn(holder.edtIsdnOrAccount.getText().toString().trim());
					if(StringUtils.isViettelMobile(isdn)){
						rawData.append("<isdn>").append(StringUtils.formatIsdn(holder.edtIsdnOrAccount
                                .getText().toString().trim())).append("</isdn>");
					} else {
						rawData.append("<isdn>").append(StringUtils.escapeHtml(holder.edtIsdnOrAccount
                                .getText().toString())).append("</isdn>");
					}
				}

				// rawData.append("<cusType>"
				// + ((Spin) holder.spnCollect.getSelectedItem()).getId()
				// + "</cusType>");

				rawData.append("<token>").append(Session.getToken()).append("</token>");

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
				saleOutput = serializer.read(BOCOutput.class, original);
				if (saleOutput == null) {
					saleOutput = new BOCOutput();
					saleOutput
							.setDescription(getString(R.string.no_return_from_system));
					saleOutput.setErrorCode(Constant.ERROR_CODE);
					return saleOutput;
				} else {
					return saleOutput;
				}
			} catch (Exception e) {
				Log.e(TAG, "getCusInfoCollect", e);
				saleOutput = new BOCOutput();
				saleOutput.setDescription(CommonActivity.getDescription(
						mActivity, e));
				saleOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return saleOutput;
		}

	}

	private void showDialogInput(final ProductSpecCharObj productSpecCharObj) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_update_collect_cus_info);
		dialog.setCancelable(false);

		LinearLayout lnSelect = (LinearLayout) dialog
				.findViewById(R.id.lnSelect);
		final LinearLayout lnOther = (LinearLayout) dialog
				.findViewById(R.id.lnOther);
		final Spinner spnSelect = (Spinner) dialog
				.findViewById(R.id.spnCollect);
		final EditText edtOther = (EditText) dialog.findViewById(R.id.edtOther);
		if("2".equals(productSpecCharObj.getValueType())){
			edtOther.setInputType(InputType.TYPE_CLASS_NUMBER);
		} else {
			edtOther.setInputType(InputType.TYPE_CLASS_TEXT);
		}
		
		spnSelect
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						if (DataUtils.safeToInteger(((Spin) spnSelect.getSelectedItem())
								.getCode()) < 0) {
							lnOther.setVisibility(View.VISIBLE);
						} else {
							lnOther.setVisibility(View.GONE);
							edtOther.setText("");
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		TextView tvHeader = (TextView) dialog.findViewById(R.id.tvHeader);
		tvHeader.setText(productSpecCharObj.getName());

		if (CommonActivity.isNullOrEmptyArray(productSpecCharObj
				.getLstProductSpecCharValueObjs())) {
			lnSelect.setVisibility(View.GONE);

			if (!CommonActivity
					.isNullOrEmpty(productSpecCharObj.getValueName())) {
				edtOther.setText(productSpecCharObj.getValueName());
			}

		} else {
			lnOther.setVisibility(View.GONE);
			List<Spin> lstSpins = new ArrayList<>();
			int selectSpn = -1;
			int defaultSelectSpn = -1;
			String defaultValue = "";

			for (int i = 0; i < productSpecCharObj
					.getLstProductSpecCharValueObjs().size(); i++) {
				ProductSpecCharValueObj productSpecCharValueObj = productSpecCharObj
						.getLstProductSpecCharValueObjs().get(i);

				if (productSpecCharValueObj.getIsDefault() == 1) {
					defaultValue = productSpecCharValueObj.getValue();
					defaultSelectSpn = i;
				}
				
				Spin spin = new Spin();
				spin.setValue(productSpecCharValueObj.getName());
				spin.setCode(productSpecCharValueObj.getValue());

				lstSpins.add(spin);

				if (!CommonActivity
						.isNullOrEmpty(productSpecCharObj.getValue())) {
					if (productSpecCharObj.getValue().startsWith(spin.getCode() + "_")
							|| productSpecCharObj.getValue().equals(productSpecCharValueObj.getValue())) {
						selectSpn = i;
					}
				}
			}

//			Spin spinOther = new Spin();
//			spinOther.setValue(getString(R.string.tvOtherToolShop));
//			spinOther.setCode(OTHER);
//
//			lstSpins.add(spinOther);

			Utils.setDataSpinner(getActivity(), lstSpins, spnSelect);
			if (selectSpn != -1) { //truong hop khac
				spnSelect.setSelection(selectSpn);

				if (!CommonActivity.isNullOrEmpty(productSpecCharObj
						.getValueName()) && DataUtils.safeToInteger(((Spin) spnSelect.getSelectedItem())
						.getCode()) < 0) {
					String value = productSpecCharObj.getValueName();
					if(value!= null && value.contains("_")){
						value = value.substring(value.indexOf("_") + 1);
						edtOther.setText(value);
					}
				}
			} else {
				if (!CommonActivity.isNullOrEmpty(productSpecCharObj
						.getValueName())) {
					edtOther.setText(productSpecCharObj.getValueName());
					lnOther.setVisibility(View.GONE);
					spnSelect.setSelection(lstSpins.size() - 1);
				}

				if (defaultSelectSpn != -1) {
					spnSelect.setSelection(defaultSelectSpn);
				}
			}
		}

		Button btnDongY = (Button) dialog.findViewById(R.id.btnOK);
		btnDongY.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				if (!CommonActivity.isNullOrEmpty(spnSelect)
//						&& !CommonActivity.isNullOrEmpty(spnSelect
//								.getSelectedItem())) {
//					if (DataUtils.safeToInteger(((Spin) spnSelect.getSelectedItem())
//							.getCode()) < 0) {
//						if (CommonActivity.isNullOrEmpty(edtOther.getText()
//								.toString().trim())) {
//							CommonActivity.createAlertDialog(getActivity(),
//									R.string.txt_must_input_value_detail,
//									R.string.app_name).show();
//							return;
//						}
//					}
//				}

				if (!CommonActivity
						.isNullOrEmpty(edtOther.getText().toString())) {
					productSpecCharObj.setValue(DataUtils.safeToInteger(((Spin) spnSelect.getSelectedItem())
							.getCode()) + "_" + edtOther.getText().toString());
					productSpecCharObj.setValueName(DataUtils.safeToInteger(((Spin) spnSelect.getSelectedItem())
							.getCode()) + "_" + edtOther.getText().toString());
				} else {
					if (!CommonActivity.isNullOrEmpty(spnSelect)
							&& !CommonActivity.isNullOrEmpty(spnSelect
									.getSelectedItem())) {
						productSpecCharObj.setValue(((Spin) spnSelect
								.getSelectedItem()).getCode());
						productSpecCharObj.setValueName(((Spin) spnSelect
								.getSelectedItem()).getValue());
					} else {
						productSpecCharObj.setValue("");
						productSpecCharObj.setValueName("");
					}
				}

				adapter.notifyDataSetChanged();

				dialog.dismiss();
				CommonActivity.hideSoftKeyboard(getActivity());
			}
		});

		Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				CommonActivity.hideSoftKeyboard(getActivity());
			}
		});

		dialog.getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		dialog.show();
	}

	private class AsyCusInfoCollect extends AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;
		final ProgressDialog progress;

		public AsyCusInfoCollect(Activity mActivity) {

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
			return cusInfoCollect();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();

			if (result.getErrorCode().equals("0")) {
				Dialog dialog = CommonActivity.createAlertDialog(
						getActivity(),
						mActivity.getResources().getString(
								R.string.title_collect_cus_info_success),
						getResources().getString(R.string.app_name));
				dialog.show();

				holder.lnCollect.setVisibility(View.GONE);
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

		private BOCOutput cusInfoCollect() {
			BOCOutput bocOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "cusInfoCollect";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);

				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<documentNo>").append(StringUtils.escapeHtml(holder.edtIdNo.getText()
                        .toString().trim())).append("</documentNo>");

				if (!CommonActivity.isNullOrEmpty(custCollectId)) {
					rawData.append("<actionType>"
							+ Constant.ACTION_TYPE.EDIT_COLLECT_CUS_INFO
							+ "</actionType>");
				} else {
					rawData.append("<actionType>"
							+ Constant.ACTION_TYPE.ADD_COLLECT_CUS_INFO
							+ "</actionType>");
				}

				if (!CommonActivity.isNullOrEmpty(idNo)) {
					rawData.append("<lstCusInfoCollectors>");
					if (!CommonActivity.isNullOrEmpty(custCollectId)) {
						rawData.append("<custCollectId>").append(custCollectId).append("</custCollectId>");
					}

					rawData.append("<infoCode>CUS_INFO_IDNO</infoCode>");
					rawData.append("<infoValue>").append(StringUtils.escapeHtml(idNo.trim())).append("</infoValue>");
					rawData.append("</lstCusInfoCollectors>");
				}

//				if (!CommonActivity.isNullOrEmpty(isdnOrAccount)) {
//					rawData.append("<lstCusInfoCollectors>");
//					if (!CommonActivity.isNullOrEmpty(custCollectId)) {
//						rawData.append("<custCollectId>" + custCollectId
//								+ "</custCollectId>");
//					}
//
//					rawData.append("<infoCode>CUS_INFO_COLLECT_ISDN</infoCode>");
//					rawData.append("<infoValue>"
//							+ StringUtils.escapeHtml(isdnOrAccount.trim())
//							+ "</infoValue>");
//					rawData.append("</lstCusInfoCollectors>");
//				}

				if (holder.cbCancel.isChecked()) {
					rawData.append("<lstCusInfoCollectors>");
					if (!CommonActivity.isNullOrEmpty(custCollectId)) {
						rawData.append("<custCollectId>").append(custCollectId).append("</custCollectId>");
					}

					rawData.append("<infoCode>REFUSE_COLLECT</infoCode>");
					rawData.append("<infoValue>1</infoValue>");
					rawData.append("</lstCusInfoCollectors>");
				} else {
					rawData.append("<lstCusInfoCollectors>");
					if (!CommonActivity.isNullOrEmpty(custCollectId)) {
						rawData.append("<custCollectId>").append(custCollectId).append("</custCollectId>");
					}

					rawData.append("<infoCode>REFUSE_COLLECT</infoCode>");
					rawData.append("<infoValue>0</infoValue>");
					rawData.append("</lstCusInfoCollectors>");
					
					for (ProductSpecCharObj prCharObj : adapter.getLstData()) {
						rawData.append("<lstCusInfoCollectors>");

						if (!CommonActivity.isNullOrEmpty(custCollectId)) {
							rawData.append("<custCollectId>").append(custCollectId).append("</custCollectId>");
						}

						rawData.append("<infoCode>").append(prCharObj.getCode()).append("</infoCode>");
						if (!CommonActivity.isNullOrEmpty(prCharObj.getValue())) {
							rawData.append("<infoValue>").append(StringUtils.escapeHtml(prCharObj
                                    .getValue().trim())).append("</infoValue>");
						}
						rawData.append("</lstCusInfoCollectors>");
					}
				}

				rawData.append("<token>").append(Session.getToken()).append("</token>");

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
				Log.e(TAG, "getCusInfoCollect", e);
				bocOutput = new BOCOutput();
				bocOutput.setDescription(CommonActivity.getDescription(
						mActivity, e));
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return bocOutput;
		}

	}

	private boolean validateCusInfoCollect() {
		if(holder.cbCancel.isChecked()){
			return true;
		}
		
		boolean existValue = false;
		for (ProductSpecCharObj productSpecCharObj : adapter.getLstData()) {
			if (!CommonActivity.isNullOrEmpty(productSpecCharObj.getValue())) {
				existValue = true;
				break;
			}
		}

		if (!existValue) {
			CommonActivity.createAlertDialog(getActivity(),
					R.string.txt_must_input_value, R.string.app_name).show();
			return false;
		}
		return true;
	}

	private final OnClickListener collectListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			new AsyCusInfoCollect(getActivity()).execute();
		}
	};

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}

}
