package com.viettel.bss.viettelpos.v4.connecttionService.asyn;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncTaskCommonSupper;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceGroupDetailDTO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

/**
 * Created by thinhhq1 on 8/21/2017.
 */
public class GetListCustomerOrderDetailNokByStaffCodeAsyn extends AsyncTaskCommonSupper<String, Void, ParseOuput> {


    ProgressDialog progress;
    OnPostExecuteListener<ParseOuput> listener;

    public GetListCustomerOrderDetailNokByStaffCodeAsyn(Activity context, OnPostExecuteListener<ParseOuput> listener,
                                                        View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
        this.listener = listener;
        this.progress = super.progress;

    }

    @Override
    protected ParseOuput doInBackground(String... params) {
        return getServicePackage();
    }

    private ParseOuput getServicePackage() {
        String original = "";
        ParseOuput out = null;
        String func = "getListCustomerOrderDetailNokByStaffCodeMbccs";
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");


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

            if (out == null) {
                out = new ParseOuput();
                errorCode = Constant.ERROR_CODE;
                out.setErrorCode(Constant.ERROR_CODE);
            }else{
                errorCode = out.getErrorCode();
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, func, e);
            description = e.getMessage();
            out = new ParseOuput();
            errorCode = Constant.ERROR_CODE;
            out.setErrorCode(Constant.ERROR_CODE);
            out.setDescription(description);
        }
        return out;
    }


}

