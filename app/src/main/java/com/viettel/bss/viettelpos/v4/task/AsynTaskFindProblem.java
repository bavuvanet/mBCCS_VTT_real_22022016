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
import com.viettel.bss.viettelpos.v4.object.ProblemHistory;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Toancx on 2/17/2017.
 */

public class AsynTaskFindProblem extends AsyncTaskCommon<String, Void, List<ProblemHistory>>{
    public AsynTaskFindProblem(Activity context, OnPostExecuteListener<List<ProblemHistory>> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected List<ProblemHistory> doInBackground(String... args) {
        return findProblem(args[0], args[1], args[2], args[3]);
    }

    private List<ProblemHistory> findProblem(String fromDate, String toDate, String isdn, String subId){
        List<ProblemHistory> lstSpin = new ArrayList<>();
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_findProblem");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:findProblem>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            paramToken.put("fromDate", fromDate);
            paramToken.put("toDate", toDate);
            paramToken.put("isdn", isdn);
            paramToken.put("subId", subId);
            rawData.append(input.buildXML(paramToken));
            rawData.append("</input>");
            rawData.append("</ws:findProblem>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_findProblem");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class,
                    original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                lstSpin = parseOuput.getLstProblemHistory();
            }
        }catch (Exception e) {
            Log.d("findProblem", e.toString());
        }
        return lstSpin;
    }
}
