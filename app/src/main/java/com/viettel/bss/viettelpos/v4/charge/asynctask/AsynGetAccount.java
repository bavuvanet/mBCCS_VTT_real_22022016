package com.viettel.bss.viettelpos.v4.charge.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountBankDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.AccountBO;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thinhhq1 on 6/22/2017.
 */
public class AsynGetAccount extends AsyncTask<String, Void, AccountDTO> {

    private Activity activity;
    private ProgressDialog progress;
    private OnPostSuccessExecute<AccountDTO> onPostExecute;
    private View.OnClickListener moveLogInAct;
    private String errorCode;
    private String description;

    public AsynGetAccount(Activity activity, OnPostSuccessExecute<AccountDTO> listener,View.OnClickListener moveLogInAct) {
        this.activity = activity;
        this.onPostExecute = listener;
        this.progress = new ProgressDialog(activity);
        this.progress.setCancelable(false);
        this.progress
                .setMessage(activity.getString(R.string.searching));
        this.progress.show();
        this.moveLogInAct = moveLogInAct;
    }

    @Override
    protected AccountDTO doInBackground(String... params) {
        return getAcount(params[0]);
    }

    @Override
    protected void onPostExecute(AccountDTO result) {
        super.onPostExecute(result);
        this.progress.dismiss();
        if ("0".equals(errorCode)) {
            onPostExecute.onPostSuccess(result);
        } else {

            if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                Dialog dialog = CommonActivity.createAlertDialog(activity,
                        description,
                        activity.getResources().getString(R.string.app_name),
                        moveLogInAct);
                dialog.show();
            } else {
                if (description == null || description.isEmpty()) {
                    description = "Không tìm thấy account";
                }
                Dialog dialog = CommonActivity.createAlertDialog(activity,
                        description,
                        activity.getResources().getString(R.string.app_name));
                dialog.show();
            }

        }
    }


    private Boolean validate(String subId) {
        if (CommonActivity.isNullOrEmpty(subId)) {
            Dialog dialog = CommonActivity.createAlertDialog(activity, activity.getString(R.string.checkSubscriber),
                    activity.getString(R.string.app_name));
            dialog.show();
            return false;
        }
        return true;
    }

    private AccountDTO getAcount(String subId) {
        if (!validate(subId)) {
            errorCode = "-1";
            return null;
        }
        String original = "";
        ParseOuput out;
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_findBySubIdAccount");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:findBySubIdAccount>");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            rawData.append("<subId>").append(subId)
                    .append("</subId>");
            rawData.append("</input>");
            rawData.append("</ws:findBySubIdAccount>");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    activity, "mbccs_findBySubIdAccount");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            // parser
            Serializer serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);
            if (CommonActivity.isNullOrEmpty(out)) {
                out = new ParseOuput();
//                description = activity
//                        .getString(R.string.no_return_from_system);
                errorCode = Constant.ERROR_CODE;
                return out.getAccountDTO();
            } else {
                errorCode = out.getErrorCode();
                description = out.getDescription();
                return out.getAccountDTO();
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, "findBySubIdAccount", e);
            out = new ParseOuput();
            out.setDescription(e.getMessage());
            out.setErrorCode(Constant.ERROR_CODE);
        }
        errorCode = out.getErrorCode();
        description = out.getDescription();
        return out.getAccountDTO();
    }


}
