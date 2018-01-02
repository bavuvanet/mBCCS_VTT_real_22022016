package com.viettel.bss.viettelpos.v4.charge.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.SubGoodsDTO;
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
 * Created by thinhhq1 on 6/23/2017.
 */
public class AsynGetFeeRevoke extends AsyncTask<String, Void, List<ProductPackageFeeDTO>> {
    private Context mContext;
    private String errorCode;
    private String description;
    private ProgressDialog progress;
    private View.OnClickListener moveLogInAct;
    private OnPostSuccessExecute<List<ProductPackageFeeDTO>> onPostExecute;

    public AsynGetFeeRevoke(Context context, OnPostSuccessExecute<List<ProductPackageFeeDTO>> listener,
                            View.OnClickListener moveLogInAct) {
        this.mContext = context;
        this.progress = new ProgressDialog(this.mContext);
        // check font
        this.moveLogInAct = moveLogInAct;
        this.onPostExecute = listener;
        this.progress.setCancelable(false);
        this.progress.setMessage(context.getResources().getString(R.string.getfee));
        if (!this.progress.isShowing()) {
            this.progress.show();
        }
    }

    @Override
    protected List<ProductPackageFeeDTO> doInBackground(String... arg0) {
        return getProductSpec(arg0[0], arg0[1], arg0[2]);
    }

    @Override
    protected void onPostExecute(List<ProductPackageFeeDTO> result) {
        super.onPostExecute(result);
        this.progress.dismiss();
        if ("0".equals(errorCode)) {
            onPostExecute.onPostSuccess(result);
        } else {
            onPostExecute.onPostSuccess(new ArrayList<ProductPackageFeeDTO>());
        }
    }

    private ArrayList<ProductPackageFeeDTO> getProductSpec(String telecomserviceId, String reasonId,
                                                           String productCode) {
        String original = "";
        ArrayList<ProductPackageFeeDTO> arrayList = new ArrayList<ProductPackageFeeDTO>();
        if (CommonActivity.isNullOrEmpty(reasonId)) return arrayList;
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_findFeeByReasonTeleId");
            StringBuilder rawData = new StringBuilder();

            rawData.append("<ws:findFeeByReasonTeleId>");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            if (!CommonActivity.isNullOrEmpty(telecomserviceId))
                rawData.append("<telecomServiceId>" + telecomserviceId + "</telecomServiceId>");
            rawData.append("<reasonId>" + reasonId + "</reasonId>");
            if (!CommonActivity.isNullOrEmpty(productCode))
                rawData.append("<productCode>" + productCode + "</productCode>");

            rawData.append("</input>");
            rawData.append("</ws:findFeeByReasonTeleId>");
            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mContext,
                    "mbccs_findFeeByReasonTeleId");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);

            original = output.getOriginal();
            Log.d("originalllllllll", original);

            Serializer serializer = new Persister();
            ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
            if (parseOuput != null) {
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                arrayList = parseOuput.getLstProductPackageFeeDTO();
            } else {
                errorCode = Constant.ERROR_CODE;
                description = mContext.getString(R.string.no_data_return);
            }
        } catch (Exception e) {
            Log.d("getProductSpec", e.toString());
            errorCode = Constant.ERROR_CODE;
            description = mContext.getString(R.string.no_data_return);
        }
        return arrayList;
    }
}
