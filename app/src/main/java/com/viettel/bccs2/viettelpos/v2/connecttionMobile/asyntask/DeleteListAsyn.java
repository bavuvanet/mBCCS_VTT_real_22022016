package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v3.connecttionService.model.CustomerOrderDetailDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

/**
 * Created by thinhhq1 on 3/23/2017.
 */

public class DeleteListAsyn extends AsyncTaskCommon<String , Void , ParseOuput> {
    private ArrayList<CustomerOrderDetailDTO> lstCustomerOrderDetailDTOs = new ArrayList<>();

    public DeleteListAsyn(Activity context, OnPostExecuteListener<ParseOuput> listener, View.OnClickListener moveLogInAct,ArrayList<CustomerOrderDetailDTO> arrCustomerDetailDTOs) {
        super(context, listener, moveLogInAct);
        this.lstCustomerOrderDetailDTOs = arrCustomerDetailDTOs;
    }

    @Override
    protected ParseOuput doInBackground(String... params) {
        return deleteList(params[0],params[1]);
    }

    private ParseOuput deleteList(String regReasonId,String actionCode){
        String original = "";
        try{
            String methodName = "deleteList";
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + methodName);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + methodName + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.token + "</token>");
            rawData.append("<regReasonId>" + regReasonId + "</regReasonId>");
            rawData.append("<actionCode>" + actionCode + "</actionCode>");
            if(!CommonActivity.isNullOrEmptyArray(lstCustomerOrderDetailDTOs)){
                for (CustomerOrderDetailDTO item: lstCustomerOrderDetailDTOs) {
                    rawData.append("<lstValue>" + item.getCustOrderDetailId() + "</lstValue>");
                }
            }
            rawData.append("</input>");
            rawData.append("</ws:" + methodName + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.i("envelope", envelope);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_deleteList");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Responseeeeeeeeee Original", original);
            // parser
            Serializer serializer = new Persister();
            ParseOuput result = serializer.read(ParseOuput.class, original);

            if(result != null){
                errorCode = result.getErrorCode();
                description = result.getDescription();
            }else{
                description = mActivity.getString(R.string.no_return_from_system);
                errorCode = Constant.ERROR_CODE;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
