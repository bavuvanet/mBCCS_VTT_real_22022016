package com.viettel.bss.viettelpos.v4.charge.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

/**
 * Created by thinhhq1 on 6/26/2017.
 */
public class AsynTaskLiquidationRecovery extends AsyncTask<String, Void, String> {
    private Context context;
    private ProgressDialog progress;
    private OnPostSuccessExecute<String> onPostExecute;
    private View.OnClickListener moveLogInAct;
    private String errorCode;
    private String description;

    public AsynTaskLiquidationRecovery(Context context) {
        this.context = context;
        this.onPostExecute = new OnPostSuccessExecute<String>() {
            @Override
            public void onPostSuccess(String result) {

            }
        };
        this.progress = new ProgressDialog(context);
        this.progress.setCancelable(false);
        this.progress
                .setMessage(context.getString(R.string.searching));
        this.progress.show();
        this.moveLogInAct = moveLogInAct;
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }

    private String get(String subId, String reseanId, List<String> listSubId, List<String> listSerial) {
        String original = "";
        ParseOuput parseOuput = new ParseOuput();
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_findListSubscriberByAccountId");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:findListSubscriberByAccountId>");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            rawData.append("<subId>").append(subId)
                    .append("</subId>");
            rawData.append("<reasonId>").append(reseanId)
                    .append("</reasonId>");
            for (String sub : listSubId)
                rawData.append("<listMapTransferSub>").append(sub)
                        .append("</listMapTransferSub>");
            for (String serial : listSerial)
                rawData.append("<listSerialRetrieve>").append(serial)
                        .append("</listSerialRetrieve>");
            rawData.append("</input>");
            rawData.append("</ws:findListSubscriberByAccountId>");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    context, "mbccs_findListSubscriberByAccountId");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            Serializer serializer = new Persister();
            parseOuput = serializer.read(ParseOuput.class,
                    original);
        } catch (Exception e) {
            Log.e(Constant.TAG, "findBySubIdAccount", e);
            parseOuput.setDescription(e.getMessage());
            parseOuput.setErrorCode(Constant.ERROR_CODE);
        }
        errorCode = parseOuput.getErrorCode();
        description = parseOuput.getDescription();
        return null;
    }
}
