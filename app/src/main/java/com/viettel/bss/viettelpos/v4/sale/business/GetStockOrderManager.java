package com.viettel.bss.viettelpos.v4.sale.business;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentRequestOrderBCCS;
import com.viettel.bss.viettelpos.v4.sale.handler.StockOrderHandler;
import com.viettel.bss.viettelpos.v4.sale.object.StockOrderDetail;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public class GetStockOrderManager extends AsyncTask<Void, Void, String> {
	// get stock order details

	private final ProgressDialog progress;
	private Activity context = null;
	final XmlDomParse parse = new XmlDomParse();
	private final String errorCode = "";
	private String description = "";
	private final String permission = Constant.VSAMenu.SALE_ORDER;

	public GetStockOrderManager(Activity context) {
		this.context = context;
		this.progress = new ProgressDialog(this.context);
		// check font
		this.progress.setMessage(context.getResources().getString(
				R.string.progressOrderItem));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}

	@Override
	protected String doInBackground(Void... arg0) {
		return sendRequestGetListStockOrder();
	}

	@Override
	protected void onPostExecute(String result) {
		progress.dismiss();
		if (result != null) {
			if (result.equals("0")) {
				progress.dismiss();
				FragmentRequestOrderBCCS viewinfoAndOrder = new FragmentRequestOrderBCCS();
				ReplaceFragment.replaceFragment(context,
						viewinfoAndOrder, false);
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					// CacheData.getInstanse().setLisInfoOjectMerges(null);
					// CacheData.getInstanse().setStockOrderCode(null);
					// Dialog dialog = CommonActivity
					// .createAlertDialog(
					// (Activity) context,
					// description,
					// context.getResources().getString(
					// R.string.app_name), moveLogInAct);
					// dialog.show();
					LoginDialog dialog = new LoginDialog(context, permission);
					dialog.show();
				} else {
					CacheData.getInstanse().setLisInfoOjectMerges(null);
					CacheData.getInstanse().setStockOrderCode(null);
					if (description == null || description.isEmpty()) {
						description = context
								.getString(R.string.get_order_fail);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
                            context, description, context
									.getResources()
									.getString(R.string.app_name), null);
					dialog.show();

				}

			}

		}

	}

	// call request getlist stock order
    private String sendRequestGetListStockOrder() {
		String original = null;
		ArrayList<StockOrderDetail> lisOrderDetails = new ArrayList<>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getStockOrderStaffBccs2");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getStockOrderStaff>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			rawData.append("</input>");
			rawData.append("</ws:getStockOrderStaff>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_getStockOrderStaffBccs2");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				return Constant.ERROR_CODE;
			}
			original = output.getOriginal();
			Log.d("originalllllllll", original);


			StockOrderHandler handler = (StockOrderHandler) CommonActivity
					.parseXMLHandler(new StockOrderHandler(), original);
			output = handler.getItem();

			if (output.getToken() != null && !output.getToken().isEmpty()) {
				Session.setToken(output.getToken());
			}
			if (output.getErrorCode().equals("0")) {
				// Constant.SUCCESS_CODE;
				original = output.getErrorCode();
				CacheData.getInstanse().setStockOrderCode(
						handler.getStockOrderCode());
				CacheData.getInstanse().setLisStockOrderDetails(
						handler.getLstData());
			} else {
				original = output.getDescription();
				Log.d("originaloutput", original);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return original;
	}

	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, LoginActivity.class);
			context.startActivity(intent);
			context.finish();

		}
	};
}
