package com.viettel.bss.viettelpos.v4.task;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.bo.StationBO;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Toancx on 2/17/2017.
 */

public class AsynTaskDownloadFile extends AsyncTaskCommon<String, Void, String[]> {
    RecordBean recordBean;
    String fileName;

    public AsynTaskDownloadFile(Activity context, OnPostExecuteListener<String[]> listener, View.OnClickListener moveLogInAct, RecordBean recordBean) {
        super(context, listener, moveLogInAct);
        this.recordBean = recordBean;
    }

    @Override
    protected String[] doInBackground(String... args) {
        return dowloadInfo();
    }

    private String[] dowloadInfo() {
        String strResult = "";
        String original = "";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getSoftLink");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getSoftLink>");
            rawData.append("<input>");
            rawData.append("<token>").append(Session.getToken()).append("</token>");
            rawData.append("<strHardLink>").append(recordBean.getRecordPath()).append(recordBean.getRecordNameScan()).append("</strHardLink>");
            rawData.append("<strSymbolicLink>").append(recordBean.getRecordNameScan()).append("</strSymbolicLink>");
            rawData.append("</input>");
            rawData.append("</ws:getSoftLink>");

            Log.i("LOG", "raw data" + rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("LOG", "Send evelop" + envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input
                    .sendRequest(envelope, Constant.BCCS_GW_URL,
                            mActivity, "mbccs_getSoftLink");
            Log.i("LOG", "Respone:  " + response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("LOG", "Responseeeeeeeeee Original  " + response);
            strResult = parserDowloadFile(original);
            return new String[]{strResult, fileName};
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String parserDowloadFile(String original) {
        String strFile = "";
        Document doc = parse.getDomElement(original);
        NodeList nl = doc.getElementsByTagName("return");
        for (int i = 0; i < nl.getLength(); i++) {
            Element e2 = (Element) nl.item(i);
            errorCode = parse.getValue(e2, "errorCode");
            description = parse.getValue(e2, "description");
            Log.d("LOG", "errorCode:  " + errorCode);
            Log.d("LOG", "description: " + description);
            strFile = parse.getValue(e2, "fileContent");
            Log.d("LOG", "fileContent: " + strFile);
            fileName = parse.getValue(e2, "fileName");
        }

        return strFile;
    }
}
