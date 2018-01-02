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
import com.viettel.bss.viettelpos.v4.omichanel.dao.PoCatalogOutsideDTO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

/**
 * Created by Toancx on 9/14/2017.
 */

public class WsProductCatalogAsyncTask extends AsyncTaskCommon<String, Void, List<PoCatalogOutsideDTO>> {

    public WsProductCatalogAsyncTask(Activity activity,
                                   OnPostExecuteListener<List<PoCatalogOutsideDTO>> listener,
                                   View.OnClickListener moveLogInAct) {
        super(activity, listener, moveLogInAct);
    }

    @Override
    protected List<PoCatalogOutsideDTO> doInBackground(String... params) {
        String functionName = params[0];
        String code = params[1];
        String value = params[2];
        String operator = params[3];
        return callWsProductCatalog(functionName, code, value, operator);
    }

    private List<PoCatalogOutsideDTO> callWsProductCatalog(
            String functionName, String code, String value, String operator) {

        String original = "";
        ParseOuput out = null;
        String func = "wsProductCatalog";

        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();

            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");

            rawData.append("<token>" + Session.getToken() + "</token>");
            if (!CommonActivity.isNullOrEmpty(functionName))
                rawData.append("<functionName>").append(functionName).append("</functionName>");

            rawData.append("<lstPoCatalogDTOs>");
            if (!CommonActivity.isNullOrEmpty(code))
                rawData.append("<code>").append(code).append("</code>");
            if (!CommonActivity.isNullOrEmpty(value))
                rawData.append("<value>").append(value).append("</value>");
            if (!CommonActivity.isNullOrEmpty(operator))
                rawData.append("<operator>").append(operator).append("</operator>");
            rawData.append("</lstPoCatalogDTOs>");

            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");

            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope,
                    Constant.BCCS_GW_URL, mActivity, "mbccs_" + func);
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
            description = mActivity.getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return null;
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();
        }
        return out.getLstPoCatalogOutsideDTOs();
    }
}