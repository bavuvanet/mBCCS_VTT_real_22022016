package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OmniOrder;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Created by thuandq on 9/27/2017.
 */

public class GetOrderPrepaidInfoAsyncTask extends AsyncTaskCommon<String, Void, OmniOrder> {
    public GetOrderPrepaidInfoAsyncTask(Activity context, OnPostExecuteListener<OmniOrder> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    public GetOrderPrepaidInfoAsyncTask(Activity context, OnPostExecuteListener<OmniOrder> listener, View.OnClickListener moveLogInAct, String message) {
        super(context, listener, moveLogInAct, message);
    }

    @Override
    protected OmniOrder doInBackground(String... params) {

        String jsonString = getOrderPrepaidInfo(params[0], params[1]);
        if ("0".equals(errorCode))
            try {
                OmniOrder omniOrder = new Gson().fromJson(jsonString, OmniOrder.class);
                return omniOrder;
            } catch (Exception e) {
                Log.e("getOrderPrepaidInfo", e.getMessage());
                errorCode = Constant.ERROR_CODE;
            }
        return new OmniOrder();
    }

    private String getOrderPrepaidInfo(String processId, String payType) {
        String original = "";
        ParseOuput out = null;
        String func = "getOrderPrepaidInfo";
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            if (!CommonActivity.isNullOrEmpty(processId))
                rawData.append("<processId>").append(processId).append("</processId>");
            if (!CommonActivity.isNullOrEmpty(payType))
                rawData.append("<payType>").append(payType).append("</payType>");

            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_" + func);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            // parser
            Serializer serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);

        } catch (Exception e) {
            Log.e(Constant.TAG, func, e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }
        if (CommonActivity.isNullOrEmpty(out)) {
            description = mActivity
                    .getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return "";
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();
            return out.getJsonResult();
        }

    }
}
