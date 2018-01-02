package com.viettel.bss.viettelpos.v4.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.AgeSubscriberDTO;
import com.viettel.bss.viettelpos.v4.bo.SubPreChargeDTO;
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
import java.util.List;
import java.util.Random;

/**
 * Created by thuandq on 10/22/2017.
 */

public class GetSubscriberAgeAsyncTask extends AsyncTaskCommon<String, Void, AgeSubscriberDTO> {
    public GetSubscriberAgeAsyncTask(Activity context, OnPostExecuteListener<AgeSubscriberDTO> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    public GetSubscriberAgeAsyncTask(Activity context, OnPostExecuteListener<AgeSubscriberDTO> listener, View.OnClickListener moveLogInAct, String message) {
        super(context, listener, moveLogInAct, message);
    }

    @Override
    protected AgeSubscriberDTO doInBackground(String... params) {
        return getSubscriberAge(params[0]);
    }


    private AgeSubscriberDTO getSubscriberAge(String subId) {

        String original = "";
        ParseOuput out = null;
        String func = "getSubscriberAge";
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            if (!CommonActivity.isNullOrEmpty(subId))
                rawData.append("<subId>").append(subId).append("</subId>");
            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response =
                    input.sendRequest(envelope, Constant.BCCS_GW_URL,
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
//            AgeSubscriberDTO ageSubscriberDTO = new AgeSubscriberDTO();
//            ageSubscriberDTO.setAge(9l);
//            ageSubscriberDTO.setAvgCharge(900000d);
//            ageSubscriberDTO.setTotalCharge(1900000d);
//            List<SubPreChargeDTO> list = new ArrayList();
//            for (int i = 0; i < 10; i++) {
//                SubPreChargeDTO preChargeDTO = new SubPreChargeDTO();
//                double s = new Random().nextDouble() * 1000000;
//                preChargeDTO.setTotalCharge(s);
//                preChargeDTO.setMonthId(String.format("%02d", i + 1) + "2017");
//                list.add(preChargeDTO);
//            }
//            ageSubscriberDTO.setSubPreChargeDTOList(list);
            return null;
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();
        }

        return out.getAgeSubscriberDTO();
    }


}
