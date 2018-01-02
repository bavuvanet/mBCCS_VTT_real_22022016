package com.viettel.bss.viettelpos.v4.customer.asynctask;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.object.ProgramBO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

public class AsyncGetProgramBOC extends
		AsyncTaskCommon<String, Void, List<ProgramBO>> {

	public AsyncGetProgramBOC(Activity context,
			OnPostExecuteListener<List<ProgramBO>> listener,
			OnClickListener moveLogInAct) {
		super(context, listener, moveLogInAct);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<ProgramBO> doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return getProgramBO();
	}

	private List<ProgramBO> getProgramBO() {
		String original = "";
		List<ProgramBO> lstProgramBOs = new ArrayList<ProgramBO>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getPrograms");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getPrograms>");
			rawData.append("<input>");
			rawData.append("<token>" + Session.getToken() + "</token>");
			rawData.append("</input>");
			rawData.append("</ws:getPrograms>");
			Log.i("RowData", rawData.toString());
			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					mActivity, "mbccs_getPrograms");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);
			original = output.getOriginal();
			Log.i("Responseeeeeeeeee Original", original);

			Serializer serializer = new Persister();
			BOCOutput parseOuput = serializer.read(BOCOutput.class, original);
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				lstProgramBOs = parseOuput.getLstProgramBOs();
			}

		} catch (Exception e) {
			Log.d("mbccs_getPrograms", e.toString() + "description error", e);
		}
		return lstProgramBOs;
	}

}
