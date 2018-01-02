package com.viettel.gem.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncTaskCommonSupper;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.gem.model.ProductSpecificationModel;
import com.viettel.gem.model.SubscriberModel;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Created by BaVV on 11/23/2017.
 */

public class SearchCustomerAsyncTask extends AsyncTaskCommonSupper<String, Void, SubscriberModel> {

    public SearchCustomerAsyncTask(
            Activity context,
            OnPostExecuteListener<SubscriberModel> listener,
            View.OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);
    }

    @Override
    protected SubscriberModel doInBackground(String... params) {
        return getCusInfoCollectV2(params[0], params[1]);
    }

//    isdn: h004_ftth_thaott6 idNo: 1232342343 token: f3b53334b42b43bdbe5dd74420b09ca3
    private SubscriberModel getCusInfoCollectV2(String isdn, String idno) {

        String original = "";
        SubscriberModel subscriberModel = null;
        String func = "searchSubscriberAll";

        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            if (!CommonActivity.isNullOrEmpty(idno))
                rawData.append("<idNo>").append(idno).append("</idNo>");

            if (!CommonActivity.isNullOrEmpty(isdn))
                rawData.append("<isdn>").append(isdn).append("</isdn>");

            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_" + func);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Original", response);

            Serializer serializer = new Persister();
            subscriberModel = serializer.read(SubscriberModel.class, original);
        } catch (Exception e) {
            Log.e(Constant.TAG, "blockSubForTerminate", e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }

        if (CommonActivity.isNullOrEmpty(subscriberModel)) {
            description = mActivity.getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return null;
        } else {
//            description = subscriberModel.getDescription();
            errorCode = subscriberModel.getErrorCode();

            if (description != null && description.contains("java.lang.String.length()")) {
                description = mActivity.getString(R.string.server_time_out);
            }
        }
        return subscriberModel;
    }
}