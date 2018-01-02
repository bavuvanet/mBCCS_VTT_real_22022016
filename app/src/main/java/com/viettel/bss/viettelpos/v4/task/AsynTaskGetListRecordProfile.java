package com.viettel.bss.viettelpos.v4.task;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.CMMobileOutput;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Toancx on 5/10/2017.
 */
public class AsynTaskGetListRecordProfile extends AsyncTaskCommon<String, Void, Map<String, ArrayList<RecordPrepaid>>> {

    ProfileBO input;

    public AsynTaskGetListRecordProfile(Activity context,
                                        OnPostExecuteListener<Map<String, ArrayList<RecordPrepaid>>> listener,
                                        View.OnClickListener moveLogInAct,
                                        ProfileBO input) {
        super(context, listener, moveLogInAct);
        this.input = input;
    }

    @Override
    protected Map<String, ArrayList<RecordPrepaid>> doInBackground(String... params) {
        return getListRecordProfile();
    }

    @Override
    protected void onPostExecute(Map<String, ArrayList<RecordPrepaid>> result) {
        try{
        if (prb != null) {
            prb.setVisibility(View.GONE);
        }
        if (progress != null) {
            progress.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        if ("0".equals(errorCode)) {
            listener.onPostExecute(result, errorCode, description);
        } else {
            if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                Dialog dialog = CommonActivity.createAlertDialog(mActivity,
                        description,
                        mActivity.getResources().getString(R.string.app_name),
                        moveLogInAct);
                dialog.show();
            } else {
                if (description == null || description.isEmpty()) {
                    description = mActivity.getString(R.string.checkdes);
                }

                Dialog dialog = CommonActivity.createAlertDialog(mActivity,
                        description,
                        mActivity.getResources().getString(R.string.app_name));
                dialog.show();
            }
        }
    }

    private Map<String, ArrayList<RecordPrepaid>> getListRecordProfile() {
        Map<String, ArrayList<RecordPrepaid>> result = new HashMap<>();
        String original = "";
        try {
            BCCSGateway bccsGateway = new BCCSGateway();
            bccsGateway.addValidateGateway("username", Constant.BCCSGW_USER);
            bccsGateway.addValidateGateway("password", Constant.BCCSGW_PASS);
            bccsGateway.addValidateGateway("wscode", "mbccs_getAllListRecordProfile");

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getAllListRecordProfile>");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            if(!CommonActivity.isNullOrEmpty(input.getLstActionCode())){
                for(Object actionCode : input.getLstActionCode()){
                    rawData.append("<lstActionCode>" + actionCode + "</lstActionCode>");
                }
            }

            if(!CommonActivity.isNullOrEmpty(input.getLstReasonId())){
                for(Object reasonId : input.getLstReasonId()){
                    rawData.append("<lstReasonId>" + reasonId + "</lstReasonId>");
                }
            }

            if(!CommonActivity.isNullOrEmpty(input.getCustType())){
                rawData.append("<custType>" + input.getParValue() + "</custType>");
            }

            if(!CommonActivity.isNullOrEmpty(input.getParValue())){
                rawData.append("<parValue>" + input.getParValue() + "</parValue>");
            }

            if (!CommonActivity.isNullOrEmpty(input.getServiceType())) {
                rawData.append("<serviceType>" + input.getServiceType() + "</serviceType>");
            }

            if (!CommonActivity.isNullOrEmpty(input.getPayType())) {
                rawData.append("<payType>" + input.getPayType() + "</payType>");
            }

            rawData.append("</input>");
            rawData.append("</ws:getAllListRecordProfile>");

            Log.i("RowData", rawData.toString());
            String envelope = bccsGateway.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("BCCS_GW_URL", Constant.BCCS_GW_URL);
            String response = bccsGateway.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_getAllListRecordProfile");
            Log.i("response", response);
            CommonOutput output = bccsGateway.parseGWResponse(response);
            original = output.getOriginal();

            Serializer serializer = new Persister();
            CMMobileOutput parseOuput = serializer.read(CMMobileOutput.class,
                    original);
            if (parseOuput != null) {
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();

                if (!CommonActivity.isNullOrEmpty(parseOuput.getLstProfileRecord())) {
                    String actionCodeMain = "";
                    if(!CommonActivity.isNullOrEmpty(input.getLstActionCode())){
                        if(input.getLstActionCode().size() == 1){
                            actionCodeMain = input.getLstActionCode().get(0).toString();
                        }else{
                            for(Object actionCode : input.getLstActionCode()){
                                actionCodeMain = actionCodeMain +";" + actionCode.toString();
                            }
                        }

                    }

                    for (RecordPrepaid item:
                         parseOuput.getLstProfileRecord()) {
                        item.setActions(actionCodeMain);
                    }
                    result = convertToMapRecord(parseOuput.getLstProfileRecord());
                }
            }
        } catch (Exception ex) {
            Log.d("AsynTaskGetListRecordProfile", "getAllListRecordProfile", ex);
        }
        return result;
    }

    private Map<String, ArrayList<RecordPrepaid>> convertToMapRecord(List<RecordPrepaid> lstData) {
        Map<String, ArrayList<RecordPrepaid>> result = new HashMap<>();
        for (RecordPrepaid record : lstData) {
            if (result.containsKey(record.getId())) {
                result.get(record.getId()).add(record);
            } else {
                ArrayList<RecordPrepaid> lstRecordPrepaids = new ArrayList<>();
                lstRecordPrepaids.add(record);
                result.put(record.getId(), lstRecordPrepaids);
            }
        }
        return result;
    }
}
