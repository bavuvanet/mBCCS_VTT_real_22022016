package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionPrepaidDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ChangePrepaidDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OmniAccountPrepaidInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OmniProductInfo;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thuandq on 9/29/2017.
 */

public class GetAllListPaymentPrePaidAsyn extends
        AsyncTaskCommon<String, Void, List<PaymentPrePaidPromotionBeans>> {


    public GetAllListPaymentPrePaidAsyn(Activity context, OnPostExecuteListener<List<PaymentPrePaidPromotionBeans>> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected List<PaymentPrePaidPromotionBeans> doInBackground(String... params) {
        return changePrepaidPromotion(params[0],params[1],params[2]);
    }

    private List<PaymentPrePaidPromotionBeans> changePrepaidPromotion(String province, String productCode, String promotionCode) {
        String original = "";
        ParseOuput out = null;
        String func = "getAllListPaymentPrePaid";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            if (!CommonActivity.isNullOrEmpty(promotionCode))
                rawData.append("<promProgramCode>" + promotionCode).append("</promProgramCode>");
            if (!CommonActivity.isNullOrEmpty(productCode))
                rawData.append("<packageId>" + productCode).append("</packageId>");
            if (!CommonActivity.isNullOrEmpty(province))
                rawData.append("<provinceCode>" + province).append("</provinceCode>");
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
            Log.e(Constant.TAG, func, e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }
        if (CommonActivity.isNullOrEmpty(out)) {
            description = mActivity
                    .getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return new ArrayList<>();
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();
            return out.getLstPaymentPrePaidPromotionBeans();
        }
    }
}
