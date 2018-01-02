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
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;

/**
 * Created by toancx on 5/25/2017.
 */
public class AsynTaskGetOldProfile extends AsyncTaskCommon<String, Void , ProfileBO> {
    ProfileBO profileBO;

    public AsynTaskGetOldProfile(Activity context, OnPostExecuteListener<ProfileBO> listener, View.OnClickListener moveLogInAct, ProfileBO input) {
        super(context, listener, moveLogInAct);
        this.profileBO = input;
    }

    @Override
    protected ProfileBO doInBackground(String... params) {
        return getOldProfile();
    }

    private ProfileBO getOldProfile(){
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getOldProfileByRecordCode");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getOldProfileByRecordCode>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            if(!CommonActivity.isNullOrEmpty(profileBO.getIdNo())) {
                paramToken.put("idNo", profileBO.getIdNo());
            }
            if(!CommonActivity.isNullOrEmpty(profileBO.getIsdnAccount())) {
                paramToken.put("isdnAccount", profileBO.getIsdnAccount());
            }
            if(!CommonActivity.isNullOrEmpty(profileBO.getRecordCode())) {
                paramToken.put("recordCode", profileBO.getRecordCode());
            }
            if(!CommonActivity.isNullOrEmpty(profileBO.getServiceType())) {
                paramToken.put("serviceType", profileBO.getServiceType());
            }
            if(!CommonActivity.isNullOrEmpty(profileBO.getType())) {
                paramToken.put("type", profileBO.getType());
            }

            rawData.append(input.buildXML(paramToken));
            rawData.append("</input>");
            rawData.append("</ws:getOldProfileByRecordCode>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_getOldProfileByRecordCode");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CCOutput parseOuput = serializer.read(CCOutput.class,
                    original);
            if(parseOuput != null){
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                profileBO.setFileContent(parseOuput.getFileContent());
                profileBO.setFileName(parseOuput.getFileName());
                profileBO.setRecordId(parseOuput.getRecordId());
                Log.d(Constant.TAG, "recordId = " + parseOuput.getRecordId());
                return profileBO;
            }
        }catch (Exception e) {
            Log.d("mbccs_getOldProfileByRecordCode", e.toString());
        }
        return null;
    }
}
