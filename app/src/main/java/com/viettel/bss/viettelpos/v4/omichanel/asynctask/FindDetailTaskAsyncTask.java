package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.Order;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuandq on 11/09/2017.
 */

public class FindDetailTaskAsyncTask extends AsyncTaskCommon<String, Void, List<ConnectionOrder>> {

    String func;
    private ArrayList<Staff> arrStaff;

    public FindDetailTaskAsyncTask(
            String func, Activity context,
            OnPostExecuteListener<List<ConnectionOrder>> listener,
            View.OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);
        this.func = func;
    }

    public FindDetailTaskAsyncTask(
            String func, Activity context,
            OnPostExecuteListener<List<ConnectionOrder>> listener,
            View.OnClickListener moveLogInAct, ArrayList<Staff> arrStaff) {

        super(context, listener, moveLogInAct);
        this.func = func;
        this.arrStaff = arrStaff;
    }

    @Override
    protected List<ConnectionOrder> doInBackground(String... params) {

        List<ConnectionOrder> result = new ArrayList<>();

        String jsonString = findDetailTask(params[0], params[1], params[2],
                params[3], params[4], params[5], params[6], params[7], params[8], params[9]
        );

        if ("0".equals(errorCode))
            try {
                JsonParser parser = new JsonParser();
                JsonArray jsonObj = parser.parse(jsonString).getAsJsonArray();
                for (int i = 0; i < jsonObj.size(); i++) {
                    ConnectionOrder e = new Gson().fromJson(jsonObj.get(i), ConnectionOrder.class);
                    result.add(e);
                }
                errorCode = "0";
            } catch (Exception e) {
                Log.e(func, e.getMessage());
                errorCode = Constant.ERROR_CODE;
            }
        return result;
    }

    private String findDetailTask(String contactIsdn, String custIsdn, String idNo,
                                  String isdn, String orderCode,
                                  String status, String shopId, String orderTypeCode,
                                  String createUser, String createDate) {
        String original = "";
        ParseOuput out = null;
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            if (!CommonActivity.isNullOrEmpty(contactIsdn))
                rawData.append("<contactIsdn>").append(contactIsdn).append("</contactIsdn>");
            if (!CommonActivity.isNullOrEmpty(custIsdn))
                rawData.append("<custIsdn>").append(custIsdn).append("</custIsdn>");
            if (!CommonActivity.isNullOrEmpty(idNo))
                rawData.append("<idNo>").append(idNo).append("</idNo>");
            if (!CommonActivity.isNullOrEmpty(isdn))
                rawData.append("<isdn>").append(isdn).append("</isdn>");
            if (!CommonActivity.isNullOrEmpty(orderCode))
                rawData.append("<processId>").append(orderCode).append("</processId>");
            if (!CommonActivity.isNullOrEmpty(status))
                rawData.append("<status>").append(status).append("</status>");
            if (!CommonActivity.isNullOrEmpty(shopId))
                rawData.append("<shopId>").append(shopId).append("</shopId>");
            if (!CommonActivity.isNullOrEmpty(orderTypeCode))
                rawData.append("<orderTypeCode>").append(orderTypeCode).append("</orderTypeCode>");
            if (!CommonActivity.isNullOrEmpty(createUser))
                rawData.append("<createUser>").append(createUser).append("</createUser>");
            if (!CommonActivity.isNullOrEmpty(createDate))
                rawData.append("<createDate>").append(createDate).append("</createDate>");

            if(!CommonActivity.isNullOrEmpty(arrStaff)){
                for (Staff staff: arrStaff){
                    if(staff.isCheck()){
                        rawData.append("<lstStaffId>").append(staff.getStaffId()).append("</lstStaffId>");
                    }
                }
            }

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
            Log.e(Constant.TAG, "blockSubForTerminate", e);
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
        }
        return out.getJsonResult();
    }
}
