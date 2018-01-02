package com.viettel.bss.viettelpos.v4.sale.asytask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

/**
 * Created by Toancx on 4/18/2017.
 */

public class AsynFindFeeByReasonTeleId extends AsyncTaskCommon<String, Void, ArrayList<ProductPackageFeeDTO>> {

    public AsynFindFeeByReasonTeleId(Activity context, OnPostExecuteListener<ArrayList<ProductPackageFeeDTO>> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected ArrayList<ProductPackageFeeDTO> doInBackground(String... arg0) {
        return getProductSpec(arg0[0], arg0[1], arg0[2]);
    }

    private ArrayList<ProductPackageFeeDTO> getProductSpec(String telecomserviceId, String reasonId, String productCode) {
        String original = "";
        ArrayList<ProductPackageFeeDTO> arrayList = new ArrayList<ProductPackageFeeDTO>();
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_findFeeByReasonTeleId");
            StringBuilder rawData = new StringBuilder();

            rawData.append("<ws:findFeeByReasonTeleId>");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            rawData.append("<telecomServiceId>" + telecomserviceId + "</telecomServiceId>");
            rawData.append("<reasonId>" + reasonId + "</reasonId>");
            rawData.append("<productCode>" + productCode + "</productCode>");
            rawData.append("</input>");
            rawData.append("</ws:findFeeByReasonTeleId>");

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_sendOTPUtil");
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
                description = mActivity.getString(R.string.no_data_return);
            }
        } catch (Exception e) {
            Log.d("getProductSpec", e.toString());
            errorCode = Constant.ERROR_CODE;
            description = mActivity.getString(R.string.no_data_return);
        }
        return arrayList;
    }
}
