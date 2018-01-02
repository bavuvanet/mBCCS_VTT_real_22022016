package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;

public class AsyncGenerateAccounts extends AsyncTask<Void, Void, ParseOuput> {
	private Activity act = null;
	private ProgressDialog progress;
	private CustIdentityDTO cus;
	private OnPostExecute<ParseOuput> onPost;
	private String infraType;
	private List<ProductCatalogDTO> lstProductCatalog;
	private String province;

	@Override
	protected ParseOuput doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		return generateAccount();
	}

	public AsyncGenerateAccounts(Activity act, String infraType,
			List<ProductCatalogDTO> lstProductCatalog, String province,
			CustIdentityDTO cus, OnPostExecute<ParseOuput> onPost) {
		super();
		this.cus = cus;
		this.infraType = infraType;
		this.lstProductCatalog = lstProductCatalog;
		this.onPost = onPost;
		this.act = act;
		this.progress = new ProgressDialog(this.act);
		this.progress.setCancelable(false);
		this.progress.setMessage(act.getString(R.string.generating_account));
		this.province = province;
		this.progress.show();
	}

	@Override
	protected void onPostExecute(ParseOuput result) {
		// TODO Auto-generated method stub
		progress.dismiss();
		super.onPostExecute(result);
		onPost.onPostExecute(result);
	}

	private ParseOuput generateAccount() {
		ParseOuput result;
		String original = "";
		String methodName = "generateAccount";
		try {

			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_" + methodName);
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:" + methodName + ">");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("<custName>")
					.append(StringUtils.escapeHtml(cus.getCustomer().getName()))
					.append("</custName>");

			// 0, ko phai gpon, 1: gpon

			String gpon = infraType;
			if ("4".equalsIgnoreCase(infraType)) {
				gpon = "4";
			}
			String idNo = cus.getIdNo();
			if(!"4".equals(infraType)){

			}else{
				rawData.append("<technology>").append(gpon).append("</technology>");
			}

			rawData.append("<idNo>").append(idNo).append("</idNo>");
			rawData.append("<busPermitNo>").append(idNo)
					.append("</busPermitNo>");

			for (ProductCatalogDTO item : lstProductCatalog) {
				if (!item.isPNService()) {
					rawData.append("<lstMoreTelecomService>");
					rawData.append("<numOfSub>").append(item.getQuantity())
							.append("</numOfSub>");

					rawData.append("<telecomServiceId>")
							.append(item.getTelecomServiceId())
							.append("</telecomServiceId>");

					rawData.append("<province>").append(province)
							.append("</province>");

					rawData.append("</lstMoreTelecomService>");
				}

			}

			rawData.append("</input>");
			rawData.append("</ws:" + methodName + ">");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					act, "mbccs_" + methodName);
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);
			// parser
			Serializer serializer = new Persister();
			result = serializer.read(ParseOuput.class, original);
			if (result == null) {
				result = new ParseOuput();
				result.setDescription(act
						.getString(R.string.no_return_from_system));
				result.setErrorCode(Constant.ERROR_CODE);
			}
		} catch (Exception e) {
			Log.e(methodName, e.toString() + "description error", e);
			result = new ParseOuput();
			result.setDescription(act.getString(R.string.exception) + " - " + e);
			result.setErrorCode(Constant.ERROR_CODE);
		}

		return result;
	}
}
