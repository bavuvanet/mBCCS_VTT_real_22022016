package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

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
 * Created by Toancx on 9/15/2017.
 */

public class ClaimAsyncTask extends AsyncTaskCommon<String, Void, String> {

    public ClaimAsyncTask(
            Activity context,
            OnPostExecuteListener<String> listener,
            View.OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);
    }

    @Override
    protected String doInBackground(String... params) {

        return claim(params[0],params[1], params[2]);
    }

    private String claim(String taskId, String staffId, String forStaffOwner) {
        String original = "";
        ParseOuput out = null;
        String func = "claim";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            if (!CommonActivity.isNullOrEmpty(taskId))
                rawData.append("<taskId>").append(taskId).append("</taskId>");
            if (!CommonActivity.isNullOrEmpty(staffId))
                rawData.append("<staffId>").append(staffId).append("</staffId>");
            if (!CommonActivity.isNullOrEmpty(forStaffOwner))
                rawData.append("<forStaffOwner>").append(forStaffOwner).append("</forStaffOwner>");

            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
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
        }
        return out.getJsonResult();
    }
}