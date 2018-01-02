package com.viettel.bss.viettelpos.v4.customer.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.CommonParseOutput;
import com.viettel.bss.viettelpos.v4.customer.object.DeviceWarrantyBO;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

/**
 * Created by huypq15 on 6/29/2017.
 */

public class AsyncCancelWarrantymBCCS extends
        AsyncTaskCommon<Void, Void, Void> {
    private DeviceWarrantyBO deviceWarrantyBO;
    public AsyncCancelWarrantymBCCS(Activity context,
                                    OnPostExecuteListener<Void> listener,
                                    View.OnClickListener moveLogInAct, DeviceWarrantyBO deviceWarrantyBO) {
        super(context, listener, moveLogInAct);
        this.deviceWarrantyBO = deviceWarrantyBO;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        return cancelWarrantymBCCS();
    }

    private Void cancelWarrantymBCCS() {
        String original = "";

        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_cancelWarrantymBCCS");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:cancelWarrantymBCCS>");
            rawData.append("<input>");
            rawData.append("<token>").append(Session.getToken()).append("</token>");

            rawData.append("<receiptNo>").append(deviceWarrantyBO.getReceiptNomBccs()).append("</receiptNo>");

            rawData.append("</input>");
            rawData.append("</ws:cancelWarrantymBCCS>");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_cancelWarrantymBCCS");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Responseeeeeeeeee Original", original);

            // parser
            if (!output.getError().equals("0")) {
                return null;
            }

            Serializer serializer = new Persister();
            CommonParseOutput smartphoneOutput = serializer.read(CommonParseOutput.class, original);
            errorCode = smartphoneOutput.getErrorCode();
            description = smartphoneOutput.getDescription();
            return null;

        } catch (Exception e) {
            Log.d("mbccs_getListApproveFinance", e.toString()
                    + "description error", e);
        }

        return null;

    }

}
