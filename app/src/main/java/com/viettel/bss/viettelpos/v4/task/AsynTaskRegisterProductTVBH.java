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
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;

/**
 * Created by hantt47 on 7/19/2017.
 */

public class AsynTaskRegisterProductTVBH extends AsyncTaskCommon<String, Void, CCOutput> {

    public AsynTaskRegisterProductTVBH(Activity context, OnPostExecuteListener<CCOutput> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected CCOutput doInBackground(String... args) {
        return registerProductTVBH(args[0], args[1], args[2], args[3], args[4]);
    }

    private CCOutput registerProductTVBH(String code, String type,
                                         String customer, String prepaid, String offer){
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_doSaleConsult");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:doSaleConsult>");
            rawData.append("<input>");

            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            paramToken.put("code", code);
            paramToken.put("type", type);
            paramToken.put("customer", customer);
            paramToken.put("prepaid", prepaid);
            paramToken.put("offer", offer);

            rawData.append(input.buildXML(paramToken));

            rawData.append("</input>");
            rawData.append("</ws:doSaleConsult>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_doSaleConsult");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class,
                    original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
            }
            return parseOuput;
        }catch (Exception e) {
            Log.d("doSaleConsult", e.toString());
        }
        return null;
    }
}