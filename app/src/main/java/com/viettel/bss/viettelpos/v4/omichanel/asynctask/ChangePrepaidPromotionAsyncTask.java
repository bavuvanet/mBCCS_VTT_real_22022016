package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.omichanel.dao.AccountPrepaidInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ChangePrepaidDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OmniAccountPrepaidInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OmniOrder;
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
 * Created by thuandq on 9/28/2017.
 */

public class ChangePrepaidPromotionAsyncTask extends AsyncTaskCommon<Void, Void, List<ChangePrepaidDTO>> {
    private OmniOrder omniOrder;
    private String effectDate;
    private String taskId;
    List<OmniAccountPrepaidInfo> accountPrepaidInfoList;

    public ChangePrepaidPromotionAsyncTask(Activity context, List<OmniAccountPrepaidInfo> accountPrepaidInfoList, OmniOrder omniOrder, String effectDate, String taskId, OnPostExecuteListener<List<ChangePrepaidDTO>> listener, View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
        this.omniOrder = omniOrder;
        this.effectDate = effectDate;
        this.taskId = taskId;
        this.accountPrepaidInfoList = accountPrepaidInfoList;
    }

    @Override
    protected List<ChangePrepaidDTO> doInBackground(Void... params) {
        return changePrepaidPromotion();
    }

    private List<ChangePrepaidDTO> changePrepaidPromotion() {
        String original = "";
        ParseOuput out = null;
        String func = "changePrepaidPromotion";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            if (!CommonActivity.isNullOrEmpty(omniOrder)) {
                // reasonId
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                rawData.append("<currentDate>")
                        .append(dateFormat.format(new Date(System.currentTimeMillis())))
                        .append("</currentDate>");
                if (!CommonActivity.isNullOrEmpty(omniOrder.getFixReasonId()))
                    rawData.append("<reasonId>").append(omniOrder.getFixReasonId()).append("</reasonId>");

                // list ChangePrepaidDTO
                if (!CommonActivity.isNullOrEmpty(accountPrepaidInfoList)) {
                    for (OmniAccountPrepaidInfo accountPrepaidInfo : accountPrepaidInfoList) {
                        rawData.append("<changes>");
                        rawData.append("<isdn>").append(accountPrepaidInfo.getAccount()).append("</isdn>");
                        OmniProductInfo productInfo = accountPrepaidInfo.getProductInfo();
                        if (!CommonActivity.isNullOrEmpty(productInfo)) {
                            if (!CommonActivity.isNullOrEmpty(effectDate))
                                rawData.append("<effectDate>").append(effectDate).append("</effectDate>");

                            if (!CommonActivity.isNullOrEmpty(productInfo.getPrepaidCode()))
                                rawData.append("<prepaidCode>")
                                        .append(accountPrepaidInfo.getProductInfo().getPrepaidCode())
                                        .append("</prepaidCode>");
                            if (!CommonActivity.isNullOrEmpty(productInfo.getPromotionCode()))
                                rawData.append("<promotionCode>")
                                        .append(productInfo.getPromotionCode())
                                        .append("</promotionCode>");
                            if (!CommonActivity.isNullOrEmpty(productInfo.getPrepaidId()))
                                rawData.append("<promId>")
                                        .append(productInfo.getPrepaidId())
                                        .append("</promId>");
                        }
                        rawData.append("</changes>");
                    }
                }
                // omniOrder
                rawData.append("<omniOrder>");
                if (!CommonActivity.isNullOrEmpty(omniOrder.getProcessInstanceId()))
                    rawData.append("<processInstanceId>")
                            .append(omniOrder.getProcessInstanceId())
                            .append("</processInstanceId>");


                if (!CommonActivity.isNullOrEmpty(taskId))
                    rawData.append("<taskId>")
                            .append(taskId)
                            .append("</taskId>");
                rawData.append("</omniOrder>");
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
            return out.getLstChangePrepaidDTOs();
        }

    }
}
