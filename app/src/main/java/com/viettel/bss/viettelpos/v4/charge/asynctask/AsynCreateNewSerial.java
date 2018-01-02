package com.viettel.bss.viettelpos.v4.charge.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.Contract;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Created by thuandq on 6/28/2017.
 */

public class AsynCreateNewSerial extends AsyncTask<String, Void, String> {
    private Activity context;
    private OnPostSuccessExecute<String> onPostExecute;
    private String errorCode;
    private String description;
    private ProgressDialog progress;
    private View.OnClickListener moveLogInAct;


    public AsynCreateNewSerial(Activity context, OnPostSuccessExecute<String> listener
            , View.OnClickListener moveLogInAct) {
        this.context = context;
        this.onPostExecute = listener;
        this.progress = new ProgressDialog(context);
        this.moveLogInAct = moveLogInAct;
        this.progress.setCancelable(false);
        this.progress
                .setMessage(context.getString(R.string.processing));
        this.progress.show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this.progress.dismiss();
        if ("0".equals(errorCode)) {
            onPostExecute.onPostSuccess(result);
        } else {
            if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                Dialog dialog = CommonActivity.createAlertDialog(context,
                        description,
                        context.getResources().getString(R.string.app_name),
                        moveLogInAct);
                dialog.show();
            } else {
                if (description == null || description.isEmpty()) {
                    description = context.getString(R.string.no_data);
                }
                Dialog dialog = CommonActivity.createAlertDialog(context,
                        description,
                        context.getResources().getString(R.string.app_name));
                dialog.show();
            }
        }


    }

    @Override
    protected String doInBackground(String... params) {
        return genarateSerial(params[0]);
    }

    private String genarateSerial(String stockModelId) {
        String original = "";
        ParseOuput out;
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_generateSerial");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:generateSerial>");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            rawData.append("<stockModelId>").append(stockModelId)
                    .append("</stockModelId>");
            rawData.append("</input>");
            rawData.append("</ws:generateSerial>");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    context, "mbccs_generateSerial");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            // parser
            Serializer serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);
            if (CommonActivity.isNullOrEmpty(out)) {
                description = context
                        .getString(R.string.no_return_from_system);
                errorCode = Constant.ERROR_CODE;
                return "";
            } else {
                errorCode = out.getErrorCode();
                description = out.getDescription();
                return out.getDescription();
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, "findBySubIdAccount", e);
            out = new ParseOuput();
            out.setDescription(e.getMessage());
            out.setErrorCode(Constant.ERROR_CODE);
        }
        errorCode = out.getErrorCode();
        description = out.getDescription();
        return "";
    }

}

