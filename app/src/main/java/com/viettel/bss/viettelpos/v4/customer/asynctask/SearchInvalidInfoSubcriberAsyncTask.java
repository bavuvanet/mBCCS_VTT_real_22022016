package com.viettel.bss.viettelpos.v4.customer.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.SubInvalidDTO;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

/**
 * Created by leekien on 11/7/2017.
 */

public class SearchInvalidInfoSubcriberAsyncTask extends AsyncTaskCommon<String, Void, List<SubInvalidDTO>> {
    Activity context;
    OnPostExecute<List<SubInvalidDTO>> listener;
    View.OnClickListener moveLogInAct;
    List<String> lstIsdn;

    public SearchInvalidInfoSubcriberAsyncTask(Activity context, List<String> lstIsdn, OnPostExecuteListener<List<SubInvalidDTO>> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
        this.lstIsdn = lstIsdn;
    }



    @Override
    protected List<SubInvalidDTO> doInBackground(String... params) {
        return searchFileInfo();
    }

    private List<SubInvalidDTO> searchFileInfo() {
        List<SubInvalidDTO> lstSubInvalidDTO;

        String original = "";
        ParseOuput out = null;
        String func = "getInvalidInfoSubscriber";
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            for (int i = 0; i < lstIsdn.size(); i++) {
                rawData.append("<lstIsdn>").append(lstIsdn.get(i)).append("</lstIsdn>");
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
             lstSubInvalidDTO = out.getLstSubInvalidDTO();
        }
//        return out.getLstMapStaffArea();
        return lstSubInvalidDTO;
    }
}

