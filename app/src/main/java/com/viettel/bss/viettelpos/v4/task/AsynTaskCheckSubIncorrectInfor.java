package com.viettel.bss.viettelpos.v4.task;

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
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;

/**
 * Created by toancx on 5/25/2017.
 */
public class AsynTaskCheckSubIncorrectInfor extends AsyncTaskCommon<String, Void , Boolean> {

    public AsynTaskCheckSubIncorrectInfor(Activity context, OnPostExecuteListener<Boolean> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return checkSubIncorrectInfo(params[0]);
    }

    private Boolean checkSubIncorrectInfo(String isdn){
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_checkSubIncorrectInfor");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:checkSubIncorrectInfor>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            paramToken.put("isdn", StringUtils.formatIsdn(isdn));

            rawData.append(input.buildXML(paramToken));
            rawData.append("</input>");
            rawData.append("</ws:checkSubIncorrectInfor>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_checkSubIncorrectInfor");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class,
                    original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                return parseOuput.getIsCVT();
            }
        }catch (Exception e) {
            Log.d("mbccs_checkSubIncorrectInfor", e.toString());
        }
        return false;
    }
}
