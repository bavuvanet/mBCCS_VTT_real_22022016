package com.viettel.bss.viettelpos.v4.charge.asynctask;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.SubGoodsDTO;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

/**
 * Created by thuandq on 6/27/2017.
 */

// thanh ly
public class AsynBlockSubForTerminate extends AsyncTask<String, Void, String> {
    private Activity activity;
    private ProgressDialog progress;
    private String errorCode;
    private String description;
    private String subId;
    private SubscriberDTO subscriberDTO;
    private String reasonId;
    private List<String> listMapTransferSub;
    private List<SubGoodsDTO> lstSerialRetrieve;
    private OnPostSuccessExecute<String> onPostExecute;
    protected View.OnClickListener moveLogInAct;

    public AsynBlockSubForTerminate(Activity activity, String subId, String reasonId,
                                    List<String> listMapTransferSub, List<SubGoodsDTO> lstSerialRetrieve
            , SubscriberDTO subscriberDTO, OnPostSuccessExecute<String> onPostExecute,
                                    View.OnClickListener moveLogInAct) {
        this.activity = activity;
        this.subId = subId;
        this.listMapTransferSub = listMapTransferSub;
        this.moveLogInAct = moveLogInAct;
        this.lstSerialRetrieve = lstSerialRetrieve;
        this.subscriberDTO = subscriberDTO;
        this.reasonId = reasonId;
        this.onPostExecute = onPostExecute;
        this.moveLogInAct = moveLogInAct;
        this.progress = new ProgressDialog(activity);
        this.progress.setCancelable(false);
        this.progress
                .setMessage(activity.getString(R.string.processing));
        this.progress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        if (validate()) {
            return block();
        }
        return "";

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(errorCode);
        this.progress.dismiss();
        onPostExecute.onPostSuccess(errorCode);
        if ("0".equals(errorCode)) {
            if (CommonActivity.isNullOrEmpty(description)) {
                description = "Thực hiện thành công!";
            }
            Dialog dialog = CommonActivity.createAlertDialog(activity, description,
                    activity.getString(R.string.app_name));
            dialog.show();
        } else {
            if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                Dialog dialog = CommonActivity.createAlertDialog(activity,
                        description,
                        activity.getResources().getString(R.string.app_name),
                        moveLogInAct);
                dialog.show();
            } else {
                if (description == null || description.isEmpty()) {
                    description = activity.getString(R.string.checkdes);
                }
                Dialog dialog = CommonActivity.createAlertDialog(activity,
                        description,
                        activity.getResources().getString(R.string.app_name));
                dialog.show();
            }
        }

    }

    private String block() {
        String original = "";
        ParseOuput out;
        String func = "blockSubForTerminate";
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
            if (!CommonActivity.isNullOrEmpty(reasonId))
                rawData.append("<reasonId>").append(reasonId).append("</reasonId>");
            if (!CommonActivity.isNullOrEmpty(listMapTransferSub))
                for (String sub : listMapTransferSub) {
                    rawData.append("<listMapTransferSub>")
                            .append(sub)
                            .append("</listMapTransferSub>");
                }
            if (!CommonActivity.isNullOrEmpty(lstSerialRetrieve))
                for (SubGoodsDTO subGoodsDTO : lstSerialRetrieve) {
                    rawData.append("<lstSerialRetrieve>")
                            .append("<subGoodsId>").append(subGoodsDTO.getSubGoodsId()).append("</subGoodsId>")
                            .append("<serialToRetrieve>").append(subGoodsDTO.getSerial()).append("</serialToRetrieve>")
                            .append("</lstSerialRetrieve>");
                }
            if (!CommonActivity.isNullOrEmpty(subscriberDTO) && !CommonActivity.isNullOrEmpty(subscriberDTO.getRawData())) {
                rawData.append("<bocInput>");
                rawData.append(subscriberDTO.getRawData());
                rawData.append("</bocInput>");
            }
            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    activity, "mbccs_" + func);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            // parser
            Serializer serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);
            if (out == null) {
//                description = context
//                        .getString(R.string.no_return_from_system);
                errorCode = Constant.ERROR_CODE;
                return null;
            } else {
                description = out.getDescription();
                errorCode = out.getErrorCode();
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, "blockSubForTerminate", e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }

        return null;
    }

    private Boolean validate() {
        if (CommonActivity.isNullOrEmpty(subId)) {
            Dialog dialog = CommonActivity.createAlertDialog(activity, activity.getString(R.string.checkSubscriber),
                    activity.getString(R.string.app_name));
            dialog.show();
            return false;
        }
        if (CommonActivity.isNullOrEmpty(reasonId)) {
            Dialog dialog = CommonActivity.createAlertDialog(activity, activity.getString(R.string.checklydothanhly),
                    activity.getString(R.string.app_name));
            dialog.show();
            return false;
        }

        return true;
    }
}
