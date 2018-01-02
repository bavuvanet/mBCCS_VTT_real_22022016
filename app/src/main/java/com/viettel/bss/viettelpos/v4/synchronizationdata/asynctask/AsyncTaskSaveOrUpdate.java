package com.viettel.bss.viettelpos.v4.synchronizationdata.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bo.ApParamBO;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuandq on 29/08/2017.
 */

public class AsyncTaskSaveOrUpdate extends AsyncTask<ApParamBO, Void, String> {
    private Activity context;
    private ProgressDialog progress;
    private String errorCode;
    private String description;
    private OnPostSuccessExecute<String> onPostExecute;
    private View.OnClickListener moveLogInAct;

    public AsyncTaskSaveOrUpdate(Activity context, OnPostSuccessExecute<String> onPostExecute, View.OnClickListener moveLogInAct) {
        this.context = context;
        this.onPostExecute = onPostExecute;
        this.moveLogInAct = moveLogInAct;
        this.progress = new ProgressDialog(context);
        this.progress.setCancelable(false);
        this.progress
                .setMessage(context.getString(R.string.processing));
        this.progress.show();
    }

    @Override
    protected String doInBackground(ApParamBO... params) {
        return getLstApParam(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this.progress.dismiss();
        onPostExecute.onPostSuccess(result);
        if ("0".equals(errorCode)) {

        } else {
            if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                Dialog dialog = CommonActivity.createAlertDialog(context,
                        description,
                        context.getResources().getString(R.string.app_name),
                        moveLogInAct);
                dialog.show();
            } else {
                description = context.getString(R.string.checkdes);

                Dialog dialog = CommonActivity.createAlertDialog(context,
                        description,
                        context.getResources().getString(R.string.app_name));
                dialog.show();
            }
        }
    }

    private String getLstApParam(ApParamBO param) {
        String original = "";
        ParseOuput out;
        String func = "saveOrUpdateApParam";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            rawData.append(param.getXML());
            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    context, "mbccs_" + func);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            // parser
            Serializer serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);
            if (out == null) {
                description = context
                        .getString(R.string.no_return_from_system);
                errorCode = Constant.ERROR_CODE;
                return errorCode;
            } else {
                description = out.getDescription();
                errorCode = out.getErrorCode();
                return errorCode;
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, func, e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }

        return errorCode;
    }

}