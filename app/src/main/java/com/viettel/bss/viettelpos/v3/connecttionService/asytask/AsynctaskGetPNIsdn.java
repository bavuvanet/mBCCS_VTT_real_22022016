package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferPriceDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.StockNumberDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.GetStockNumberAdapter;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
public class AsynctaskGetPNIsdn extends AsyncTask<Void, Void, ParseOuput> {
	private Activity mActivity = null;
	private EditText edtIsdn;
	private SubscriberDTO sub;
	private String stationId;
	private String province;
	private ProgressDialog progress;

	public AsynctaskGetPNIsdn(Activity mActivity, EditText edtIsdn,
			SubscriberDTO sub, String stationId, String province) {
		super();

		this.province = province;
		this.mActivity = mActivity;
		this.edtIsdn = edtIsdn;
		this.sub = sub;
		this.stationId = stationId;

		this.progress = new ProgressDialog(this.mActivity);
		this.progress.setCancelable(false);
		this.progress.setMessage(mActivity.getString(
				R.string.getting_stock_isdn, sub.getServiceTypeName()));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}

	@Override
	protected ParseOuput doInBackground(Void... params) {
		return getListIsdn();
	}

	@Override
	protected void onPostExecute(ParseOuput result) {
		super.onPostExecute(result);
		this.progress.dismiss();
		if (result.getErrorCode().equals("0")) {
			List<StockNumberDTO> lst = result.getLstStockNumberDTO();
			boolean getRecord = true;

			if (lst == null || lst.isEmpty()) {
				String msg = "";
				if ("N".equals(sub.getServiceType())) {
					msg = mActivity.getString(R.string.list_isdn_ngn_empty,
							sub.getProductCode(), sub.getHthm().getCode(),
							province);
				} else if ("P".equals(sub.getServiceType())) {
					msg = mActivity
							.getString(
									R.string.list_isdn_pntn_empty,
									sub.getProductCode(),
									sub.getHthm().getCode(),
									TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
											.getStationId());
				}
				CommonActivity.createErrorDialog(mActivity, msg, "1").show();
				sub.setLstIsdn(null);
				return;

			}
			sub.setLstIsdn(result.getLstStockNumberDTO());
			this.progress.dismiss();
			showDialogIsdn(sub.getLstIsdn());

		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity
						.BackFromLogin(mActivity, result.getDescription());
			} else {
				if (result.getDescription() == null
						|| result.getDescription().isEmpty()) {
					result.setDescription(mActivity
							.getString(R.string.checkdes));
				}
				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
						result.getDescription(), mActivity.getResources()
								.getString(R.string.app_name));
				dialog.show();
			}
		}
	}

	private ParseOuput getListIsdn() {
		ParseOuput result;
		String original = "";
		String methodName = "";
		try {

			if ("N".equals(sub.getServiceType())) {
				methodName = "getLstNgnIsdnByAreaCode";
			} else if ("P".equals(sub.getServiceType())) {
				methodName = "searchIsdnPstn";
			}
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<regType>" + sub.getHthm().getCode() + "</regType>");
			rawData.append("<regReasonId>" + sub.getHthm().getReasonId()
					+ "</regReasonId>");
			rawData.append("<serviceType>" + sub.getServiceType()
					+ "</serviceType>");
			rawData.append("<productCode>" + sub.getProductCode()
					+ "</productCode>");
			rawData.append("<stationId>"
					+ TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit
							.getStationId() + "</stationId>");

			rawData.append("<province>" + province + "</province>");

			rawData.append("<status>" + 1 + "</status>");
			rawData.append("<telecomServiceId>" + sub.getTelecomServiceId()
					+ "</telecomServiceId>");
			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_" + methodName);
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);
			// parser
			Serializer serializer = new Persister();
			result = serializer.read(ParseOuput.class, original);
			if (result == null) {
				result = new ParseOuput();
				result.setDescription(mActivity
						.getString(R.string.no_return_from_system));
				result.setErrorCode(Constant.ERROR_CODE);
			}
		} catch (Exception e) {
			Log.e(methodName, e.toString() + "description error", e);
			result = new ParseOuput();
			result.setDescription(mActivity.getString(R.string.exception)
					+ " - " + e);
			result.setErrorCode(Constant.ERROR_CODE);
		}

		return result;
	}

	public void showDialogIsdn(final List<StockNumberDTO> lst) {

		final GetStockNumberAdapter adapter = new GetStockNumberAdapter(lst,mActivity);
		final Dialog dialog = new Dialog(mActivity);
		dialog.setContentView(R.layout.layout_single_listview);
		dialog.setTitle(mActivity.getString(R.string.choose_isdn));
		dialog.findViewById(R.id.toolbar).setVisibility(View.GONE);
		dialog.findViewById(R.id.lnButton).setVisibility(View.GONE);
		dialog.findViewById(R.id.tv1).setVisibility(View.GONE);
		dialog.setCancelable(true);
		final ListView lv = (ListView) dialog.findViewById(R.id.lvItem);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				StockNumberDTO stockNumberDTO = (StockNumberDTO) arg0.getItemAtPosition(arg2);
				edtIsdn.setText(stockNumberDTO.getIsdn());
				sub.setIsdn(edtIsdn.getText().toString());
				dialog.dismiss();
			}
		});
		final EditText edtsearch = (EditText) dialog.findViewById(R.id.edtsearch);
		edtsearch.setVisibility(View.VISIBLE);

		edtsearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());

				if (adapter != null) {
					// lstGoiCuoc = adapter.SearchInput(input);
					// lvpakage.setAdapter(adapter);
					adapter.SearchInput(input);
					// adapter.notifyDataSetChanged();
				}


			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		dialog.show();
	}

	// ham lay thong tin cam ket so dep cho dich vu P hoac N
	private class GetPriceInServicesMultiplePledgeAsyn extends AsyncTask<String, Void, ArrayList<ProductOfferPriceDTO>> {

		private Context mContext;
		private String errorCode;
		private String description;
		private ProgressDialog progress;
		private EditText txtisdn;
		private SubscriberDTO subscriberDTO;
		public GetPriceInServicesMultiplePledgeAsyn(Context context, EditText edtIsdn) {
			this.mContext = context;
			this.txtisdn = edtIsdn;
			this.progress = new ProgressDialog(mContext);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		public GetPriceInServicesMultiplePledgeAsyn(Context context, EditText edtIsdn , SubscriberDTO subscriberDTO) {
			this.mContext = context;
			this.txtisdn = edtIsdn;
			this.progress = new ProgressDialog(mContext);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<ProductOfferPriceDTO> doInBackground(String... arg0) {
			return getDataStock(arg0[0], arg0[1], arg0[2], arg0[3]);
		}

		@Override
		protected void onPostExecute(ArrayList<ProductOfferPriceDTO> result) {
			super.onPostExecute(result);
			progress.dismiss();

			if ("0".equals(errorCode)) {
				if (!CommonActivity.isNullOrEmpty(result)) {

					if (!CommonActivity.isNullOrEmpty(result.get(0).getPrice())
							&& !CommonActivity.isNullOrEmpty(result.get(0).getPledgeTime())) {

//						showDialogViewDataStock(result.get(0), txtisdn.getText().toString().trim());
					} else {
//						CommonActivity.createAlertDialog(mContext, mContext.getString(R.string.notviewcamket),
//								mContext.getString(R.string.app_name)).show();
					}
				} else {
//					CommonActivity.createAlertDialog(mContext, mContext.getString(R.string.notviewcamket),
//							mContext.getString(R.string.app_name)).show();
				}

			} else {

				if (Constant.INVALID_TOKEN2.equals(errorCode)) {

					CommonActivity
							.BackFromLogin(mActivity, description);
				} else {
					if (description == null || description.isEmpty()) {
						description = mContext.getString(R.string.checkdes);
					}

				}
				Dialog dialog = CommonActivity.createAlertDialog(mContext, description,
						mContext.getResources().getString(R.string.app_name));
				dialog.show();

			}

		}

		private ArrayList<ProductOfferPriceDTO> getDataStock(String telecomServiceId, String reasonId, String productCode, String isdn) {
			String original = "";
			ArrayList<ProductOfferPriceDTO> stockNumberDTO = new ArrayList<>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getPriceInServicesMultiplePledge");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getPriceInServicesMultiplePledge>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");

				rawData.append("<telecomServiceId>" + telecomServiceId + "</telecomServiceId>");
				rawData.append("<isdn>" + CommonActivity.getStardardIsdnBCCS(isdn) + "</isdn>");
				rawData.append("<reasonId>" + reasonId + "</reasonId>");
				rawData.append("<productCode>" + productCode + "</productCode>");
				rawData.append("</input>");
				rawData.append("</ws:getPriceInServicesMultiplePledge>");
				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext,
						"mbccs_getPriceInServicesMultiplePledge");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					stockNumberDTO = parseOuput.getLstProductOfferPriceDTOs();
				}

				return stockNumberDTO;

			} catch (Exception e) {
				Log.d("getDataStock", e.toString());
			}

			return stockNumberDTO;
		}

	}
	
}
