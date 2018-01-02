package com.viettel.bss.viettelpos.v4.bankplus.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.bankplus.object.BankPlusOutput;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Created by hantt47 on 7/25/2017.
 */

public class GetLstPinCodeTransAsyncTask extends
        AsyncTaskCommon<String, String, BankPlusOutput> {

    public GetLstPinCodeTransAsyncTask(Activity context,
                                       OnPostExecuteListener<BankPlusOutput> listener,
                                       View.OnClickListener moveLogInAct, String message) {

        super(context, listener, moveLogInAct, message);
    }

    @Override
    protected BankPlusOutput doInBackground(String... arg) {
        // TODO Auto-generated method stub
        return getLstPinCodeTrans(arg[0], arg[1], arg[2]);
    }

    private BankPlusOutput getLstPinCodeTrans(String isdn, String fromDate, String toDate) {
        BankPlusOutput result = null;
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getLstPinCodeTrans");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getLstTppTransDTOs>");
            rawData.append("<input>");

            rawData.append("<isdnAccount>" + isdn + "</isdnAccount>");
            rawData.append("<fromDate>" + fromDate + "</fromDate>");
            rawData.append("<toDate>" + toDate + "</toDate>");

            rawData.append("</input>");
            rawData.append("</ws:destroyTransBankplus>");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.i("envelope", envelope);
            String response = input.sendRequest(envelope,
                    Constant.BCCS_GW_URL, mActivity, "mbccs_getLstPinCodeTrans");

            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            String original = output.getOriginal();
            Log.i("Responseeeeeeeeee Original", original);

            // parser
            Serializer serializer = new Persister();
            result = serializer.read(BankPlusOutput.class, original);
            if (result != null) {
                errorCode = result.getErrorCode();
                description = result.getDescription();
            } else {
                description = mActivity.getString(R.string.no_return_from_system);
                errorCode = Constant.ERROR_CODE;
            }
        } catch (Exception e) {
            Log.e("exception ", "Exception ", e);
        }
        return result;
    }
}