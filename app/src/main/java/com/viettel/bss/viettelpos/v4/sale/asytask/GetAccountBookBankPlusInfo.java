package com.viettel.bss.viettelpos.v4.sale.asytask;

import java.util.Date;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.dialog.DialogTransHis;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;

public class GetAccountBookBankPlusInfo extends
		AsyncTask<String, Void, SaleOutput> {
	private Context context;
	private ProgressDialog progress;
	private List<FormBean> lstBankTransType;
	private Boolean isView = true;
	private Date fromDate;
	private Date toDate;
	private TextView tv;

	public GetAccountBookBankPlusInfo(Context context,
									  List<FormBean> lstBankTransType, Boolean isView, Date fromDate,
									  Date toDate, TextView tv) {
		this.tv = tv;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.lstBankTransType = lstBankTransType;
		this.isView = isView;
		this.context = context;
		this.progress = new ProgressDialog(context);
		this.progress.setCancelable(false);
		String msg = context.getString(R.string.getting_trans_his);
		if (isView) {
			msg = context.getString(R.string.getting_account_bccs_bankplus);
		}
		this.progress.setMessage(msg);
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}

	@Override
	protected SaleOutput doInBackground(String... arg0) {
		return getAccountBookBankPlusInfo();
	}

	private SaleOutput getAccountBookBankPlusInfo() {
		String original = "";
		SaleOutput out;
		String methodName = "getAccountBookBankPlusInfo";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");

			rawData.append("<pageIndex>0");
			rawData.append("</pageIndex>");
			rawData.append("<pageSize>100");
			rawData.append("</pageSize>");

			rawData.append("<isView>");
			rawData.append(isView);
			rawData.append("</isView>");
			if (!isView) {
				for (FormBean item : lstBankTransType) {
					if (item.getChecked()) {
						rawData.append("<lstValueLong>");
						rawData.append(item.getId());
						rawData.append("</lstValueLong>");
					}
				}

				rawData.append("<fromDate>");
				rawData.append(DateTimeUtils.convertDateTimeToString(fromDate,
						"ddMMyyyy") + "000000");
				rawData.append("</fromDate>");
				rawData.append("<toDate>");
				rawData.append(DateTimeUtils.convertDateTimeToString(toDate,
						"ddMMyyyy") + "235959");
				rawData.append("</toDate>");
			}

			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_" + methodName);
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", response);

			// parser
			Serializer serializer = new Persister();
			out = serializer.read(SaleOutput.class, original);
			if (out == null) {
				out = new SaleOutput();
				out.setDescription(context
						.getString(R.string.no_return_from_system));
				out.setErrorCode(Constant.ERROR_CODE);
				return out;
			} else {
				return out;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, methodName, e);
			out = new SaleOutput();
			out.setDescription(e.getMessage());
			out.setErrorCode(Constant.ERROR_CODE);
		}

		return out;
	}

	@Override
	protected void onPostExecute(SaleOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		if ("0".equals(result.getErrorCode())) {

			if (!isView) {
				if (!CommonActivity.isNullOrEmpty(result
						.getLstAccountBookBankplusDTO())) {
					DialogTransHis dialog = new DialogTransHis(context,
							result.getLstAccountBookBankplusDTO());
					dialog.show();
				} else {
					CommonActivity.createAlertDialog((Activity) context,
							R.string.no_trans_in_time, R.string.app_name)
							.show();
				}
			} else {
				String money = result.getRealBalance();
				if (CommonActivity.isNullOrEmpty(money)) {
					money = "0";
				}

				tv.setText(StringUtils.formatMoney(money));
			}
		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin((Activity) context,
						result.getDescription());
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(context,
						result.getDescription(),
						context.getString(R.string.app_name));
				dialog.show();

			}
		}

	}
}
