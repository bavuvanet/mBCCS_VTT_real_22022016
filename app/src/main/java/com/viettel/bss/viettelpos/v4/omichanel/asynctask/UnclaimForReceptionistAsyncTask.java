package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncTaskCommonSupper;
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

/**
 * Created by Toancx on 10/10/2017.
 */

public class UnclaimForReceptionistAsyncTask extends AsyncTaskCommonSupper<String, Void, String> {

    public UnclaimForReceptionistAsyncTask(
            Activity context,
            OnPostExecuteListener<String> listener,
            View.OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);
    }

    @Override
    protected String doInBackground(String... params) {

        return claim(params[0]);
    }

    private String claim(String processId) {

        String original = "";
        ParseOuput out = null;
        String func = "unclaimForReceptionist";
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
            description = mActivity.getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return "";
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();

            if (description != null && description.contains("java.lang.String.length()")) {
                description = mActivity.getString(R.string.server_time_out);
            }
        }
        return out.getUnclaimForReceptionist();
    }
}