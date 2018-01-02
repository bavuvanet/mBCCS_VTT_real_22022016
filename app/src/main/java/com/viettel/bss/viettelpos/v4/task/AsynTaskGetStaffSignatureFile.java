package com.viettel.bss.viettelpos.v4.task;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncTaskCommonSupper;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by Toancx on 2/17/2017.
 */

public class AsynTaskGetStaffSignatureFile extends AsyncTaskCommon<String, Void, String> {

    public static final String TYPE_CHECK_SIG = "0";
    public static final String TYPE_GET_SIG = "1";

    private String code;

    public AsynTaskGetStaffSignatureFile(Activity context,
                                         OnPostExecuteListener<String> listener,
                                         View.OnClickListener moveLogInAct,
                                         String code) {

        super(context, listener, moveLogInAct);
        this.code = code;
    }

    @Override
    protected String doInBackground(String... args) {
        return dowloadInfo();
    }

    private String dowloadInfo() {
        String original = "";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getStaffSignatureFile");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getStaffSignatureFile>");

            rawData.append("<input>");
            rawData.append("<token>").append(Session.getToken()).append("</token>");
            if (!CommonActivity.isNullOrEmpty(code)) {
                rawData.append("<code>").append(code).append("</code>");
            }
            rawData.append("</input>");

            rawData.append("</ws:getStaffSignatureFile>");

            Log.i("LOG", "raw data" + rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("LOG", "Send evelop" + envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input
                    .sendRequest(envelope, Constant.BCCS_GW_URL,
                            mActivity, "mbccs_getStaffSignatureFile");
            Log.i("LOG", "Respone:  " + response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class, original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                return parseOuput.getStaffSignatureImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
