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
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.gem.model.ProductSpecificationModel;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Created by BaVV on 11/23/2017.
 */

public class GetCustomerInfoCollectAsyncTask extends AsyncTaskCommonSupper<String, Void, ProductSpecificationModel> {

    public GetCustomerInfoCollectAsyncTask(
            Activity context,
            OnPostExecuteListener<ProductSpecificationModel> listener,
            View.OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);
    }

    @Override
    protected ProductSpecificationModel doInBackground(String... params) {
        return getCusInfoCollectV2(params[0], params[1]);
    }

    private ProductSpecificationModel getCusInfoCollectV2(String isdn, String idno) {

        String original = "";
        ProductSpecificationModel productSpecificationModel = null;
        String func = "getCusInfoCollectV2";

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

            if (!CommonActivity.isNullOrEmpty(idno))
                rawData.append("<idno>").append(idno).append("</idno>");

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
            productSpecificationModel = serializer.read(ProductSpecificationModel.class, original);
        } catch (Exception e) {
            Log.e(Constant.TAG, "blockSubForTerminate", e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }

        if (CommonActivity.isNullOrEmpty(productSpecificationModel)) {
            description = mActivity.getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return null;
        } else {
//            description = productSpecificationModel.getDescription();
            errorCode = productSpecificationModel.getErrorCode();

            if (description != null && description.contains("java.lang.String.length()")) {
                description = mActivity.getString(R.string.server_time_out);
            }
        }
        return productSpecificationModel;
    }
}