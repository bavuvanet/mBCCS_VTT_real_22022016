package com.viettel.bss.viettelpos.v4.charge.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.work.object.FamilyInforBean;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhhq1 on 6/22/2017.
 */
public class AsynGetSubscribersByAccountId extends AsyncTask<String, Void, List<SubscriberDTO>> {

    private Activity context;
    private ProgressDialog progress;
    private OnPostSuccessExecute<List<SubscriberDTO>> onPostExecute;
    private String description;
    private String errorCode;
    private View.OnClickListener moveLogInAct;

    public AsynGetSubscribersByAccountId(Activity context, OnPostSuccessExecute<List<SubscriberDTO>> listener
            , View.OnClickListener moveLogInAct) {
        this.context = context;
        this.onPostExecute = listener;
        this.progress = new ProgressDialog(context);
        this.progress.setCancelable(false);
        this.moveLogInAct = moveLogInAct;
        this.progress
                .setMessage(context.getString(R.string.searchingSubcriber));
        this.progress.show();
    }

    @Override
    protected List<SubscriberDTO> doInBackground(String... params) {
        return getAcount(params[0]);
    }

    @Override
    protected void onPostExecute(List<SubscriberDTO> result) {
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
                    description = context.getString(R.string.searchingSubcribernot);
                }
                Dialog dialog = CommonActivity.createAlertDialog(context,
                        description,
                        context.getResources().getString(R.string.app_name));
                dialog.show();
            }
        }


    }

    private List<SubscriberDTO> getAcount(String accountId) {
        if (CommonActivity.isNullOrEmpty(accountId)) {
            Dialog dialog = CommonActivity.createAlertDialog(context, context.getString(R.string.searchingSubcribernot),
                    context.getString(R.string.app_name));
            dialog.show();
            return new ArrayList<>();
        }
        String original = "";
        String function = "getListSubForTransferPrepaid";
        ParseOuput parseOuput = new ParseOuput();
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + function);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + function + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            rawData.append("<subId>").append(accountId).append("</subId>");
            rawData.append("</input>");
            rawData.append("</ws:" + function + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    context, "mbccs_" + function);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            Serializer serializer = new Persister();
            parseOuput = serializer.read(ParseOuput.class,
                    original);
        } catch (Exception e) {
            Log.e(Constant.TAG, function, e);
            parseOuput.setDescription(e.getMessage());
            parseOuput.setErrorCode(Constant.ERROR_CODE);
        }
        errorCode = parseOuput.getErrorCode();
        description = parseOuput.getDescription();
        return parseOuput.getSubscriberDTOList();
    }
}
