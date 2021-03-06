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
import com.viettel.bss.viettelpos.v4.omichanel.dao.VStockNumberOmniDTO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.util.List;

/**
 * Created by Toancx on 9/13/2017.
 */

public class SearchIsdnOmniAsyncTask extends AsyncTaskCommon<String, Void, List<VStockNumberOmniDTO>> {

    public SearchIsdnOmniAsyncTask(Activity activity,
                                   OnPostExecuteListener<List<VStockNumberOmniDTO>> listener,
                                   View.OnClickListener moveLogInAct) {
        super(activity, listener, moveLogInAct);
    }

    @Override
    protected List<VStockNumberOmniDTO> doInBackground(String... params) {
        String isdn = params[0];
        String telecomServiceId = params[1];
        String areaCode = params[2];
        String startRow = params[3];
        String endRow = params[4];
        return searchIsdnOmni(isdn, telecomServiceId, areaCode, startRow, endRow);
    }

    private List<VStockNumberOmniDTO> searchIsdnOmni(String isdn, String telecomServiceId,
                                  String areaCode, String startRow, String endRow) {
        String original = "";
        ParseOuput out = null;
        String func = "searchIsdnOmni";

        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();

            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            if (!CommonActivity.isNullOrEmpty(isdn))
                rawData.append("<isdn>").append(isdn).append("</isdn>");
            if (!CommonActivity.isNullOrEmpty(telecomServiceId))
                rawData.append("<telecomServiceId>").append(telecomServiceId).append("</telecomServiceId>");
            if (!CommonActivity.isNullOrEmpty(areaCode))
                rawData.append("<areaCode>").append(areaCode).append("</areaCode>");
            if (!CommonActivity.isNullOrEmpty(startRow))
                rawData.append("<startRow>").append(startRow).append("</startRow>");
            if (!CommonActivity.isNullOrEmpty(endRow))
                rawData.append("<endRow>").append(endRow).append("</endRow>");
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
        return out.getvStockNumberOmniDTOs();
    }
}