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
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Toancx on 2/17/2017.
 */

public class AsynTaskGetDetailProfile extends AsyncTaskCommon<String, Void, List<RecordBean>> {

    String actionProfileId;
    String typeStatus;
    Boolean isModify = true;

    public AsynTaskGetDetailProfile(Activity context, OnPostExecuteListener<List<RecordBean>> listener,
                                    View.OnClickListener moveLogInAct, String actionProfileId, String typeStatus, boolean isModify) {
        super(context, listener, moveLogInAct);
        this.actionProfileId = actionProfileId;
        this.typeStatus = typeStatus;
        this.isModify = isModify;
    }

    @Override
    protected List<RecordBean> doInBackground(String... args) {
        return getLstRecordBeans();
    }

    private List<RecordBean> getLstRecordBeans() {
        String original = null;
        try {
            BCCSGateway input = new BCCSGateway();

            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode",
                    "mbccs_getIncorectRecordByActionProfileId");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getIncorectRecordByActionProfileId>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<>();

            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));

            rawData.append("<actionProfileId>").append(actionProfileId).append("</actionProfileId>");
            rawData.append("<typeStatus>").append(typeStatus).append("</typeStatus>");

            if (isModify) {
                rawData.append("<typeProfile>")
                        .append("ACTION_PROFILE")
                        .append("</typeProfile>");
            } else {
                rawData.append("<typeProfile>")
                        .append("ACTION_VERIFICATION")
                        .append("</typeProfile>");
            }


            rawData.append("</input>");
            rawData.append("</ws:getIncorectRecordByActionProfileId>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope,
                    Constant.BCCS_GW_URL, mActivity,
                    "mbccs_getIncorectRecordByActionProfileId");
            Log.i("Responseeeeeeeeee", response);

            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Responseeeeeeeeee", original);

            Serializer serializer = new Persister();
            ParseOuput parseOuput = serializer.read(ParseOuput.class,
                    original);
            if (parseOuput != null) {
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                return parseOuput.getLstRecordBeans();
            }

            return null;
        } catch (Exception e) {
            Log.d("getIncorectRecordByActionProfileId", e.toString(), e);
        }
        return null;
    }
}
