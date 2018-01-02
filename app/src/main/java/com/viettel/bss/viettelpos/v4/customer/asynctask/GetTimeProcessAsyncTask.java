package com.viettel.bss.viettelpos.v4.customer.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.object.ComplainDTO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Toancx on 10/7/2017.
 */

public class GetTimeProcessAsyncTask extends AsyncTaskCommon<String, Void, String> {

    public GetTimeProcessAsyncTask(Activity context, OnPostExecuteListener<String> listener,
                                   View.OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);
    }

    @Override
    protected String doInBackground(String... args) {
        return getProblemTypeInProcess(args[0], args[1]);
    }

    private String getProblemTypeInProcess(String probTypeId, String probPriorityId){
        String timeStringResult = "";
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getTimeProcess");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getTimeProcess>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());

            if (!CommonActivity.isNullOrEmpty(probTypeId)) {
                paramToken.put("probTypeId", probTypeId);
            }

            if (!CommonActivity.isNullOrEmpty(probPriorityId)) {
                paramToken.put("probPriorityId", probPriorityId);
            }

            rawData.append(input.buildXML(paramToken));
            rawData.append("</input>");
            rawData.append("</ws:getTimeProcess>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_getTimeProcess");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class, original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                timeStringResult = parseOuput.getTimeDeadline();
            }
        }catch (Exception e) {
            Log.d("getTimeProcess", e.toString());
        }
        return timeStringResult;
    }
}
