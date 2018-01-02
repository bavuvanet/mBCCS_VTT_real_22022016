package com.viettel.bss.viettelpos.v4.report.asynctask;

import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.report.dialog.DialogBonusSubDetail;
import com.viettel.bss.viettelpos.v4.report.object.AttributeObjectBO;
import com.viettel.bss.viettelpos.v4.report.object.AttributeObjectBOArray;
import com.viettel.bss.viettelpos.v4.report.object.BonusOutput;
import com.viettel.bss.viettelpos.v4.sale.adapter.AdapterListServiceDetail;

public class GetListCommTransaction extends
		AsyncTask<String, Void, BonusOutput> {

	private Context context;
	private final ProgressDialog progress;
	private final String billCycle;
	private final String reportType;

	public GetListCommTransaction(Context context, String billCycle,
			String reportType) {
		super();
		this.context = context;
		this.progress = new ProgressDialog(context);
		this.progress.setCancelable(false);
		this.progress.setMessage(context.getResources().getString(
				R.string.getting_trans_bonus_detail));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}

		this.context = context;
		this.billCycle = billCycle;
		this.reportType = reportType;
	}

	@Override
	protected BonusOutput doInBackground(String... params) {

		return getListCommTransaction();
	}

	@Override
	protected void onPostExecute(final BonusOutput result) {
		super.onPostExecute(result);
		progress.dismiss();
		if (result.getErrorCode().equals("0")) {
			try {
				final Dialog dialog = new Dialog(context,
						android.R.style.Theme_Light_NoTitleBar_Fullscreen);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.layout_list_info_report_money_having);
				dialog.findViewById(R.id.edtSearch).setVisibility(View.GONE);
				ListView lvService = (ListView) dialog
						.findViewById(R.id.lvInfoReportMoney);

				List<AttributeObjectBOArray> lstData = result
						.getLstAttributeObjectBO();
				if (!CommonActivity.isNullOrEmpty(lstData)) {

					for (AttributeObjectBOArray item : lstData) {
						for (int i = 0; i < item.getItem().size(); i++) {
							AttributeObjectBO bo = item.getItem().get(i);
							if (bo.getColumnName().equalsIgnoreCase("KEY")) {
								item.getItem().remove(i);
								item.setKey(bo);
								break;
							}

						}
					}
				}
				lvService.setAdapter(new AdapterListServiceDetail(context,
						result.getLstAttributeObjectBO()));
				Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
				btnClose.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
				lvService.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						AttributeObjectBOArray item = result
								.getLstAttributeObjectBO().get(arg2);
						// GetListCommRpDetail asy = new
						// GetListCommRpDetail(context, billCycle, reportType);
						DialogBonusSubDetail detail = new DialogBonusSubDetail(
								context, billCycle, reportType, item.getKey()
										.getColumnValue());
						detail.show();
						detail.getListData();
					}
				});
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else {
			if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin((Activity) context,
						result.getDescription(), ";report_revenue;");
			} else {
				Dialog dialog = CommonActivity.createAlertDialog(context,
						result.getDescription(),
						context.getString(R.string.app_name));
				dialog.show();

			}
		}

	}

	private BonusOutput getListCommTransaction() {

		String original = "";
		BonusOutput bonusOutput;
		String methodName = "getListCommTransaction";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:").append(methodName).append(">");
			rawData.append("<input>");
			rawData.append("<token>").append(Session.getToken()).append("</token>");

			rawData.append("<billCycle>").append(billCycle).append("</billCycle>");
			rawData.append("<reportType>").append(reportType).append("</reportType>");

			rawData.append("</input>");
			rawData.append("</ws:").append(methodName).append(">");
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
			bonusOutput = serializer.read(BonusOutput.class, original);
			if (bonusOutput == null) {
				bonusOutput = new BonusOutput();
				bonusOutput.setDescription(context
						.getString(R.string.no_return_from_system));
				bonusOutput.setErrorCode(Constant.ERROR_CODE);
				return bonusOutput;
			} else {
				return bonusOutput;
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, methodName, e);
			bonusOutput = new BonusOutput();
			bonusOutput.setDescription(e.getMessage());
			bonusOutput.setErrorCode(Constant.ERROR_CODE);
		}

		return bonusOutput;
	}

}
