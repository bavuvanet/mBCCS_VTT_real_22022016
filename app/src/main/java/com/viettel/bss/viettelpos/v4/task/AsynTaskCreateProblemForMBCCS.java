package com.viettel.bss.viettelpos.v4.task;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.object.ComplainDTO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Toancx on 2/17/2017.
 */

public class AsynTaskCreateProblemForMBCCS extends AsyncTaskCommon<Object, Void, String>{
    public AsynTaskCreateProblemForMBCCS(Activity context, OnPostExecuteListener<String> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected String doInBackground(Object... args) {
        return createProblemForMBCCS((ComplainDTO) args[0], (HashMap<String, ArrayList<FileObj>>) args[1]);
    }

    private String createProblemForMBCCS(ComplainDTO complainDTO, HashMap<String, ArrayList<FileObj>> hashmapFileObj){
        String original = "";
        try{
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_createProblemForMBCCS");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:createProblemForMBCCS>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            if(!CommonActivity.isNullOrEmpty(complainDTO.getCustAppointDate())){
                paramToken.put("custAppointDateStr", complainDTO.getCustAppointDate());
            }
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));

            paramToken.clear();
            rawData.append("<problemDTO>");
            if(!CommonActivity.isNullOrEmpty(complainDTO.getIsdn())){
                paramToken.put("isdn", complainDTO.getIsdn());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getProbPriorityId())){
                paramToken.put("probPriorityId", String.valueOf(complainDTO.getProbPriorityId()));
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getParentGroupId())){
                paramToken.put("parentGroupId", String.valueOf(complainDTO.getParentGroupId()));
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getProbGroupId())){
                paramToken.put("probGroupId", String.valueOf(complainDTO.getProbGroupId()));
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getProbChannelId())){
                paramToken.put("probChannelId", complainDTO.getProbChannelId());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getProbTypeId())){
                paramToken.put("probTypeId", String.valueOf(complainDTO.getProbTypeId()));
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getProbAcceptTypeId())){
                paramToken.put("probAcceptTypeId", complainDTO.getProbAcceptTypeId());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getProblemLevelId())){
                paramToken.put("problemLevelId", complainDTO.getProblemLevelId());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getProblemContent())){
                paramToken.put("problemContent", complainDTO.getProblemContent());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getComplainerName())){
                paramToken.put("complainerName", complainDTO.getComplainerName());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getComplainerEmail())){
                paramToken.put("complainerEmail", complainDTO.getComplainerEmail());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getComplainerAddress())){
                paramToken.put("complainerAddress", complainDTO.getComplainerAddress());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getComplainerPhone())){
                paramToken.put("complainerPhone", complainDTO.getComplainerPhone());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getAreaCode())){
                paramToken.put("areaCode", complainDTO.getAreaCode());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getProvince())){
                paramToken.put("province", complainDTO.getProvince());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getDistrict())){
                paramToken.put("district", complainDTO.getProvince() + complainDTO.getDistrict());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getPrecinct())){
                paramToken.put("precinct", complainDTO.getProvince() + complainDTO.getDistrict() + complainDTO.getPrecinct());
            }

            if(!CommonActivity.isNullOrEmpty(complainDTO.getCustomerText())){
                paramToken.put("customerText", complainDTO.getCustomerText());
            }
            rawData.append(input.buildXML(paramToken));

            rawData.append("</problemDTO>");

            if(hashmapFileObj != null && !hashmapFileObj.isEmpty()){
                ArrayList<FileObj> lstFileObj = FileUtils.getArrFileBackUp(mActivity, hashmapFileObj);
                if (!CommonActivity.isNullOrEmptyArray(lstFileObj)) {
//                    int index = 1;
                    for (FileObj fileObj : lstFileObj) {
                        File isdnZip = new File(fileObj.getPath());
                        byte[] buffer = FileUtils.fileToBytes(isdnZip);
                        String base64 = Base64.encodeToString(buffer,
                                Activity.TRIM_MEMORY_BACKGROUND);

                        rawData.append("<lstFileAttachmentDTOs>");

//                        rawData.append("<fileAttachmentId>" + (index++) + "</fileAttachmentId>");
                        rawData.append("<status>1</status>");
                        rawData.append("<fileContent>" + base64 + "</fileContent>");
                        rawData.append("<fileName>" + fileObj.getName() + "</fileName>");

                        rawData.append("</lstFileAttachmentDTOs>");
                    }
                }
            }
            rawData.append("</input>");
            rawData.append("</ws:createProblemForMBCCS>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_createProblemForMBCCS");
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
        }catch (Exception e) {
            Log.d("getProblemPriority", e.toString());
        }
        return errorCode;
    }
}
