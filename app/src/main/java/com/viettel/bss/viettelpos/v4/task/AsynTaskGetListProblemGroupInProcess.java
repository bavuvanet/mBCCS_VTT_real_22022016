package com.viettel.bss.viettelpos.v4.task;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
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
 * Created by Toancx on 2/17/2017.
 */

public class AsynTaskGetListProblemGroupInProcess extends AsyncTaskCommon<String, Void, List<ComplainDTO>>{
    public AsynTaskGetListProblemGroupInProcess(Activity context, OnPostExecuteListener<List<ComplainDTO>> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected List<ComplainDTO> doInBackground(String... args) {
        return getProblemPriority(args[0], args[1]);
    }

    private List<ComplainDTO> getProblemPriority(String payType, String telecomServiceId){
        List<ComplainDTO> lstSpin = new ArrayList<>();
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getListProblemGroupInProcess");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getListProblemGroupInProcess>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            paramToken.put("payType", payType);
            paramToken.put("telecomServiceId", telecomServiceId);
            rawData.append(input.buildXML(paramToken));
            rawData.append("</input>");
            rawData.append("</ws:getListProblemGroupInProcess>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_getListProblemGroupInProcess");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class,
                    original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                lstSpin = parseOuput.getLstProblemGroupDTOs();
            }
        }catch (Exception e) {
            Log.d("getListProblemGroupIn", e.toString());
        }
        return lstSpin;
    }
}
