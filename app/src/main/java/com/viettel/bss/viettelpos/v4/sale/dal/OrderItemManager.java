package com.viettel.bss.viettelpos.v4.sale.dal;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;


import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.sale.object.ViewInfoOjectMerge;


class OrderItemManager extends AsyncTask<ArrayList<ViewInfoOjectMerge>, Void, String>{

	private final ProgressDialog progress;
	private Context context = null;
	private final ArrayList<ViewInfoOjectMerge> lisInfoOjectMerges;
	public OrderItemManager(Context context, ArrayList<ViewInfoOjectMerge> arrObjects) {
		this.context = context;
		this.lisInfoOjectMerges = arrObjects;
		this.progress = new ProgressDialog(this.context);
		//check font 
		
		this.progress.setMessage(context.getResources().getString(R.string.orderitem));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}
	
	@Override
	protected String doInBackground(ArrayList<ViewInfoOjectMerge>... arg0) {
		return sendRequestOrderItem(lisInfoOjectMerges);
	}
	
	
	@Override
	protected void onPostExecute(String result) {
		if(result.equals("0")){
			Log.d("result", result);					
	//		Toast.makeText(context, context.getResources().getString(R.string.saleitemsucess), Toast.LENGTH_LONG);
			progress.dismiss();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setTitle(context.getResources().getString(R.string.orderitem));			
			// set dialog message
			alertDialogBuilder
				.setMessage(context.getResources().getString(R.string.saleitemsucess))
				.setCancelable(false)
				.setPositiveButton(context.getResources().getString(R.string.ok),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
					dialog.dismiss();
					}
				  });
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}else{		
			progress.dismiss();
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setTitle(context.getResources().getString(R.string.orderitem));			
			// set dialog message
			alertDialogBuilder
				.setMessage(result)
				.setCancelable(false)
				.setPositiveButton(context.getResources().getString(R.string.ok),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
					dialog.dismiss();
					}
				  });
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
	}


	private String sendRequestOrderItem(
            ArrayList<ViewInfoOjectMerge> lisInfoOjectMerges) {
		String original = null;
		String errorMessage = null;
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_requestOrder");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:requestOrder>");
			rawData.append("<orderStockInput>");
			HashMap<String, String> param = new HashMap<>();
			HashMap<String, String> paramToken = new HashMap<>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));
			if (lisInfoOjectMerges.size() > 0) {
				for (ViewInfoOjectMerge item : lisInfoOjectMerges) {
					HashMap<String, String> rawDataItem = new HashMap<>();
					rawDataItem.put("quantityOrder", "" + item.get_soluong());
					rawDataItem.put("stockModelId", item.get_stock_model_id());
					param.put("lstStockOrderDetail",
							input.buildXML(rawDataItem));
					rawData.append(input.buildXML(param));
				}
			}		
			rawData.append("</orderStockInput>");
			rawData.append("</ws:requestOrder>");
			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope,
					Constant.BCCS_GW_URL,context,"mbccs_requestOrder");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}
			original = output.getOriginal();
			Log.d("originalllllllll", original);
			VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
					.parseXMLHandler(new VSAMenuHandler(), original);
			output = handler.getItem();
			if (output.getToken() != null && !output.getToken().isEmpty()) {
				Session.setToken(output.getToken());
			}
			original = output.getErrorCode();
//			if (!output.getErrorCode().equals("0")) {
//				errorMessage = output.getDescription();
//				return Constant.ERROR_CODE;
//			}
//			if(output.getErrorCode().equals("0")){
//				original = output.getErrorCode();
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return original;
	}

}
