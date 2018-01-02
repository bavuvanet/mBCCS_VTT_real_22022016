package com.viettel.bss.viettelpos.v4.task;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.bo.ReportProfileBO;
import com.viettel.bss.viettelpos.v4.bo.StationBO;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Toancx on 2/17/2017.
 */

public class AsynTaskReportProfileDetail extends AsyncTaskCommon<String, Void, List<ReportProfileBO>>{

    String fromDate;
    String toDate;
    String actionCode;
    List<String> lstStatus;

    public AsynTaskReportProfileDetail(Activity context, OnPostExecuteListener<List<ReportProfileBO>> listener,
                                       View.OnClickListener moveLogInAct, String fromDate, String toDate, String actionCode, List<String> lstStatus) {
        super(context, listener, moveLogInAct);
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.actionCode = actionCode;
        this.lstStatus = lstStatus;
    }

    @Override
    protected List<ReportProfileBO> doInBackground(String... args) {
        return getReportRegisterDetail();
    }

    private List<ReportProfileBO> getReportRegisterDetail(){
        List<ReportProfileBO> lstReportProfileBOs = new ArrayList<>();
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_reportProfileDetail");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:reportProfileDetail>");
            rawData.append("<input>");

            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            paramToken.put("fromDate", fromDate);
            paramToken.put("toDate", toDate);
            paramToken.put("actionCode", actionCode);
            rawData.append(input.buildXML(paramToken));

            if(!CommonActivity.isNullOrEmpty(lstStatus)){
                for(String status : lstStatus){
                    rawData.append("<lstStatus>").append(status).append("</lstStatus>");
                }
            }

            rawData.append("</input>");
            rawData.append("</ws:reportProfileDetail>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_reportProfileDetail");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class,
                    original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                lstReportProfileBOs = parseOuput.getLstReportProfileBOs();
            }
        }catch (Exception e) {
            Log.d("getReportRegisterDetail", "error", e);
        }
        return lstReportProfileBOs;
    }
}
