package com.viettel.bss.viettelpos.v4.hsdt.asynctask;

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
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.Date;

/**
 * Created by hantt47 on 10/31/2017.
 */

public class CancelOrderAsyn extends AsyncTaskCommon<String, Void, Void> {

    private Activity context;
    private OnPostExecuteListener<Void> listener;

    public CancelOrderAsyn(Activity context,
                           OnPostExecuteListener<Void> listener,
                           View.OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);
        this.context = context;
        this.listener = listener;

    }

    @Override
    protected Void doInBackground(String... params) {
        return cancelOrder(params[0]);
    }

    private Void cancelOrder(String processId) {
        Date date;
        String original = "";
        ParseOuput out = null;
        String func = "cancelOrderHsdt";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<ws:" + func + ">");
            stringBuilder.append("<input>");
            stringBuilder.append("<token>" + Session.getToken() + "</token>");

            stringBuilder.append("<processId>");
            stringBuilder.append(processId);
            stringBuilder.append("</processId>");


            stringBuilder.append("</input>");
            stringBuilder.append("</ws:" + func + ">");
            Log.i("RowData", stringBuilder.toString());
            String envelope = input.buildInputGatewayWithRawData(stringBuilder.toString());
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

        description = out.getDescription();
        errorCode = out.getErrorCode();
        return null;
    }
}