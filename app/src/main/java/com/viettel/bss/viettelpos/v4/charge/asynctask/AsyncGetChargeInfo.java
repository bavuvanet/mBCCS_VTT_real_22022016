package com.viettel.bss.viettelpos.v4.charge.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.PaymentOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class AsyncGetChargeInfo extends AsyncTask<Void, Void, PaymentOutput> {
    private Context context;
    private ProgressDialog progress;
    private OnPostSuccessExecute<PaymentOutput> onPostExecute;
    private String contractId;
    private int pageIndex;
    private int pageSize;
    private boolean isDebitSub = true;
    private boolean isUsage = true;

    public AsyncGetChargeInfo(Context context, OnPostSuccessExecute<PaymentOutput> onPostExecute,
                              String contractId, int pageIndex, int pageSize, boolean isDebitSub, boolean isUsage) {
        this.context = context;
        this.onPostExecute = onPostExecute;
        this.contractId = contractId;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.isDebitSub = isDebitSub;
        this.isUsage = isUsage;
        this.progress = new ProgressDialog(context);
        this.progress.setCancelable(false);
        this.progress
                .setMessage(context.getString(R.string.getting_debit_info));
        this.progress.show();

    }

    @Override
    protected PaymentOutput doInBackground(Void... arg0) {
        // TODO Auto-generated method stub
        return getDebitInfo();
    }

    @Override
    protected void onPostExecute(PaymentOutput result) {
        super.onPostExecute(result);
        this.progress.dismiss();
        if (result.getErrorCode().equals("0")) {
            onPostExecute.onPostSuccess(result);
        } else {
            if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                CommonActivity.BackFromLogin((Activity) context,
                        result.getDescription());
            } else {
                String des = result.getDescription();
                if (CommonActivity.isNullOrEmpty(des)) {
                    des = context.getString(R.string.no_data);
                }
                Dialog dialog = CommonActivity.createAlertDialog(context, des,
                        context.getString(R.string.app_name));
                dialog.show();

            }
        }


    }

    private PaymentOutput getDebitInfo() {
        String original = "";
        PaymentOutput out;
        String methodName = "getChargeInfo";
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + methodName);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + methodName + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            rawData.append("<contractId>").append(contractId)
                    .append("</contractId>");

            rawData.append("<pageIndex>").append(pageIndex)

                    .append("</pageIndex>");

            rawData.append("<isDebitSub>").append(isDebitSub)
                    .append("</isDebitSub>");

            rawData.append("<isUsage>").append(isUsage)
                    .append("</isUsage>");

            rawData.append("<pageSize>").append(pageSize)
                    .append("</pageSize>");


            rawData.append("</input>");
            rawData.append("</ws:" + methodName + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    context, "mbccs_" + methodName);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            // parser
            Serializer serializer = new Persister();
            out = serializer.read(PaymentOutput.class, original);
            if (out == null) {
                out = new PaymentOutput();
                out.setDescription(context
                        .getString(R.string.no_return_from_system));
                out.setErrorCode(Constant.ERROR_CODE);
                return out;
            } else {

                return out;
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, methodName, e);
            out = new PaymentOutput();
            out.setDescription(e.getMessage());
            out.setErrorCode(Constant.ERROR_CODE);
        }

        return out;
    }
}
