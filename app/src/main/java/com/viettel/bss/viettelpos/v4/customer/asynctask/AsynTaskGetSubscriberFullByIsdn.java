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
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberFullDTO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;

/**
 * Created by Toancx on 10/5/2017.
 */

public class AsynTaskGetSubscriberFullByIsdn extends AsyncTaskCommon<String, Void, SubscriberFullDTO> {

    public AsynTaskGetSubscriberFullByIsdn(Activity context, OnPostExecuteListener<SubscriberFullDTO> listener,
                                    View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected SubscriberFullDTO doInBackground(String... strings) {
        return searchSubscriber(strings[0].trim(), strings[1].trim());
    }

    private SubscriberFullDTO searchSubscriber(String isdn, String status) {
        SubscriberFullDTO subscriberFullDTO = null;
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getSubscriberFullByIsdn");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getSubscriberFullByIsdn>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));
            if(StringUtils.isViettelMobile(isdn)){
                isdn = StringUtils.formatIsdn(isdn);
            }
            rawData.append("<isdn>" + isdn + "</isdn>");

            if (CommonActivity.isNullOrEmpty(status)) {
                rawData.append("<isdn>" + "2" + "</isdn>");
            } else {
                rawData.append("<status>" + status + "</status>");
            }

            rawData.append("</input>");
            rawData.append("</ws:getSubscriberFullByIsdn>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_getSubscriberFullByIsdn");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class, original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                subscriberFullDTO = parseOuput.getSubscriberFullDTO();
            }
        }catch (Exception e) {
            Log.d("getSubscriberFullByIsdn", e.toString());
        }
        return subscriberFullDTO;
    }
}
