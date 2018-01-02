package com.viettel.bss.viettelpos.v4.commons;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

/**
 * Created by thinhhq1 on 8/26/2017.
 * lay chi tiet khuyen mai cho view chi tiet khuyen mai
 */
public class GetAllDetailPromotionAysn extends AsyncTaskCommon<String, Void, ArrayList<BillingPromotionDetailDTO>> {


    ProgressDialog progress;
    OnPostExecuteListener<ArrayList<BillingPromotionDetailDTO>> listener;

    public  GetAllDetailPromotionAysn(Activity context, OnPostExecuteListener<ArrayList<BillingPromotionDetailDTO>> listener,
                                     View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
        this.listener = listener;
        this.progress = super.progress;

    }

    @Override
    protected ArrayList<BillingPromotionDetailDTO> doInBackground(String... params) {
        return getAllDetailByBillingPromotionCode(params[0], params[1]);
    }

    private ArrayList<BillingPromotionDetailDTO> getAllDetailByBillingPromotionCode(String billingPromotionCode, String productCode) {
        String original = "";
        ParseOuput out = null;
        String func = "getAllDetailByBillingPromotionCode";
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            if (!CommonActivity.isNullOrEmpty(billingPromotionCode)) {
                rawData.append("<billingPromotionCode>").append(billingPromotionCode).append("</billingPromotionCode>");
            }

            if (!CommonActivity.isNullOrEmpty(billingPromotionCode)) {
                rawData.append("<productCode>").append(productCode).append("</productCode>");
            }

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
            Log.e(Constant.TAG, func, e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
            this.progress.dismiss();
        }

        if (out == null) {
            errorCode = Constant.ERROR_CODE;
            return null;
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();
        }
        this.progress.dismiss();
        return out.getListBillingPromotionDetailDTO();
    }


    @Override
    protected void onPostExecute(ArrayList<BillingPromotionDetailDTO> result) {
        if (prb != null) {
            prb.setVisibility(View.GONE);
        }
        if (progress != null) {
            progress.dismiss();
        }

        if ("0".equals(errorCode) ) {
            if(CommonActivity.isNullOrEmpty(result)) result = new ArrayList<>();
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

