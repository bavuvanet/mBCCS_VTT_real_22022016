package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
 * Created by thinhhq1 on 8/19/2017.
 */
public class GetHotChargeAsyn extends AsyncTaskCommon<String, Void, String> {


    ProgressDialog progress;
    OnPostExecuteListener<String> listener;

    public GetHotChargeAsyn(Activity context, OnPostExecuteListener<String> listener,
                            View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
        this.listener = listener;
        this.progress = super.progress;

    }


    @Override
    protected String doInBackground(String... params) {
        return getHotCharge(params[0], params[1]);
    }

    private String getHotCharge(String subId, String telecomServiceId) {
        java.lang.String original = "";
        ParseOuput out = null;
        java.lang.String func = "getHotChargeMultiBySubId";
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            if (!CommonActivity.isNullOrEmpty(subId)) {
                rawData.append("<subId>").append(subId).append("</subId>");
            }
            if (!CommonActivity.isNullOrEmpty(subId)) {
                rawData.append("<telecomServiceId>").append(telecomServiceId).append("</telecomServiceId>");
            }

            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            java.lang.String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            java.lang.String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_" + func);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            // parser
            Serializer serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);
            if (out == null) {
                errorCode = Constant.ERROR_CODE;
                return null;
            } else {
                description = out.getDescription();
                errorCode = out.getErrorCode();
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, func, e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
            return null;
        }

        return out.getHotCharge();
    }


    @Override
    protected void onPostExecute(String result) {
        if (prb != null) {
            prb.setVisibility(View.GONE);
        }
        if (progress != null) {
            progress.dismiss();
        }

        if ("0".equals(errorCode)) {
            if (CommonActivity.isNullOrEmpty(result)) {
                if (CommonActivity.isNullOrEmpty(description)) {
                    description = "Không tìm thấy thông tin cước phát sinh";
                }
                Dialog dialog = CommonActivity.createAlertDialog(mActivity,
                        description,
                        mActivity.getResources().getString(R.string.app_name));
                dialog.show();
                return;
            }
            this.listener.onPostExecute(result, errorCode, description);
        } else {
            if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                CommonActivity.BackFromLogin(mActivity, description, ";cm.connect_sub_bccs2;");

            } else {
                if (description == null || description.isEmpty()) {
                    description = mActivity.getString(R.string.checkdes);
                }
                Dialog dialog = CommonActivity.createAlertDialog(mActivity,
                        description,
                        mActivity.getResources().getString(R.string.app_name));
                dialog.show();
            }
        }

    }


}

