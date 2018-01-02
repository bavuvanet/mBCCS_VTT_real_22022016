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
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManageRequestBCCS;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerOrderDetailMbccsDTO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;


import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhhq1 on 3/23/2017.
 */

public class GetLstcustomerOrderDetailMbccsDTOAsyn extends AsyncTaskCommon<String,
        Void, List<CustomerOrderDetailMbccsDTO>> {

    public GetLstcustomerOrderDetailMbccsDTOAsyn(Activity context, FragmentManageRequestBCCS.OnPosGetLstCustOrderDetail listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected List<CustomerOrderDetailMbccsDTO> doInBackground(String... params) {
        return getLstCustomerOrderDetailMbccsDTO(params[0], params[1], params[2], params[3], params[4], params[5], params[6],params[7]);
    }

    private List<CustomerOrderDetailMbccsDTO> getLstCustomerOrderDetailMbccsDTO(String custOrderDetailId, String telecomServiceId
            , String isdnAccount, String strCreateDatetime, String limitDate, String status, String createUser,String actionCode) {
        List<CustomerOrderDetailMbccsDTO> listCust = new ArrayList<>();
        String original = "";
        try {
            String methodName = "getLstCustOrderDetail";
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + methodName);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + methodName + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.token + "</token>");
            rawData.append("<customerOrderDetailDTO>");
            if (!CommonActivity.isNullOrEmpty(custOrderDetailId)) {
                rawData.append("<custOrderDetailId>" + custOrderDetailId + "</custOrderDetailId>");
            }
            if (!CommonActivity.isNullOrEmpty(actionCode)) {
                rawData.append("<actionCode>" + actionCode + "</actionCode>");
            }
            if (!CommonActivity.isNullOrEmpty(createUser))
                rawData.append("<devStaffCode>" + createUser + "</devStaffCode>");
            if (!CommonActivity.isNullOrEmpty(telecomServiceId)) {
                rawData.append("<telecomServiceId>" + telecomServiceId + "</telecomServiceId>");
            }
            if (!CommonActivity.isNullOrEmpty(isdnAccount)) {
                rawData.append("<isdnAccount>" + isdnAccount + "</isdnAccount>");
            }
            if (!CommonActivity.isNullOrEmpty(status)) {
                rawData.append("<status>" + status + "</status>");
            }
            if (!CommonActivity.isNullOrEmpty(limitDate)) {
                rawData.append("<limitDate>" + limitDate + "T00:00:00+07:00" + "</limitDate>");
            }
            if (!CommonActivity.isNullOrEmpty(strCreateDatetime)) {
                rawData.append("<effectDate>" + strCreateDatetime + "T00:00:00+07:00" + "</effectDate>");
            }
            rawData.append("</customerOrderDetailDTO>");
            rawData.append("</input>");
            rawData.append("</ws:" + methodName + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.i("envelope", envelope);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_" + methodName);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Responseeeeeeeeee Original", original);
            // parser
            Serializer serializer = new Persister();
            ParseOuput result = serializer.read(ParseOuput.class, original);

            if (result != null) {
                errorCode = result.getErrorCode();
                description = result.getDescription();
                listCust = result.getLstCustomerOrderDetailMbccsDTOs();
            } else {
                description = mActivity.getString(R.string.no_return_from_system);
                errorCode = Constant.ERROR_CODE;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return listCust;
    }


}
