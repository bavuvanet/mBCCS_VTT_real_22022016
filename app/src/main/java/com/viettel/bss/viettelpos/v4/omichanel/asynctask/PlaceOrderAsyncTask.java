package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hantt47 on 9/25/2017.
 */

public class PlaceOrderAsyncTask extends AsyncTaskCommon<String, Void, String> {
    private String target = "";
    private List<ProfileRecord> profileRecords;

    public PlaceOrderAsyncTask(Activity context,
                               OnPostExecuteListener<String> listener,
                               View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    public PlaceOrderAsyncTask(Activity context,
                               OnPostExecuteListener<String> listener,
                               View.OnClickListener moveLogInAct,
                               String target, List<ProfileRecord> profileRecords) {

        super(context, listener, moveLogInAct);
        this.target = target;
        this.profileRecords = profileRecords;
    }

    @Override
    protected String doInBackground(String... params) {
        return placeOtherTask(params[0], params[1]);
    }

    private String placeOtherTask(String nameCus, String orderType) {
        String original = "";
        ParseOuput out = null;
        String func = "placeOrder";
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            rawData.append("<orderType>").append(orderType).append("</orderType>");
            rawData.append("<name>").append(nameCus).append("</name>");

            if(!CommonActivity.isNullOrEmpty(target)){
                rawData.append("<target>").append(target).append("</target>");
            }
            if(!CommonActivity.isNullOrEmptyArray(profileRecords)){
                for (ProfileRecord item: profileRecords) {
                    rawData.append("<profileRecords>" + item.toXml());
                    rawData.append("</profileRecords>");
                }
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
            Log.e(Constant.TAG, "blockSubForTerminate", e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }
        if (CommonActivity.isNullOrEmpty(out)) {
            description = mActivity
                    .getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return null;
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();
        }
        return out.getOrderId();
    }
}
