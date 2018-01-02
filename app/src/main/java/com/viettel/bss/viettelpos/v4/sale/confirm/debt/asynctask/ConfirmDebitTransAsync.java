package com.viettel.bss.viettelpos.v4.sale.confirm.debt.asynctask;

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
import com.viettel.bss.viettelpos.v4.sale.object.ConfirmDebitTransDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

/**
 * Created by huypq15 on 4/25/2017.
 */

public class ConfirmDebitTransAsync extends AsyncTaskCommon<String, Void, Void> {

    private ConfirmDebitTransDTO viettelDebt;
    private List<ConfirmDebitTransDTO> lstStaffDebt;

    public ConfirmDebitTransAsync(Activity context,
                                  OnPostExecuteListener<Void> listener,
                                  View.OnClickListener moveLogInAct, List<ConfirmDebitTransDTO> lstStaffDebt, ConfirmDebitTransDTO viettelDebt) {
        super(context, listener, moveLogInAct, context.getString(R.string.confirmming_debt));
        this.viettelDebt = viettelDebt;
        this.lstStaffDebt = lstStaffDebt;
    }

    @Override
    protected Void doInBackground(String... params) {
        {
            String original = "";
            SaleOutput result;
            String methodName = "confirmDebitTrans";
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:" + methodName + ">");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                if (!CommonActivity.isNullOrEmpty(lstStaffDebt)) {

                    for (ConfirmDebitTransDTO bo : lstStaffDebt) {
                        if(bo.getDebitTypeDetail().compareTo(-1L) !=0){
                            rawData.append("<confirmDebitTransDTOs>");
                            rawData.append("<id>").append(bo.getId()).append("</id>");
                            rawData.append("<debitType>").append(bo.getDebitType()).append("</debitType>");
                            rawData.append("<confirmPay>").append(bo.getConfirmPay()).append("</confirmPay>");
                            rawData.append("<payDifferent>").append(bo.getPayDifferent()).append("</payDifferent>");
                            rawData.append("<payDifferentReason>").append(bo.getPayDifferentReason()).append("</payDifferentReason>");
                            rawData.append("<debitTypeDetail >").append(bo.getDebitTypeDetail()).append("</debitTypeDetail >");
                            rawData.append("<endCycleAmount>").append(bo.getEndCycleAmount()).append("</endCycleAmount >");
                            rawData.append("<status>").append(bo.getStatus()).append("</status>");
                            rawData.append("<ownerType>").append(bo.getOwnerType()).append("</ownerType>");
                            rawData.append("<ownerId>").append(bo.getOwnerId()).append("</ownerId>");
                            rawData.append("</confirmDebitTransDTOs>");
                        }
                    }

                }


                if(viettelDebt!= null){
                    rawData.append("<confirmDebitTransDTOs>");
                    rawData.append("<id>").append(viettelDebt.getId()).append("</id>");
                    rawData.append("<debitType>").append(viettelDebt.getDebitType()).append("</debitType>");
                    rawData.append("<confirmBalance>").append(viettelDebt.getConfirmBalance()).append("</confirmBalance>");
                    rawData.append("<balanceDifferent>").append(viettelDebt.getBalanceDifferent()).append("</balanceDifferent>");
                    rawData.append("<balanceDifferentReason>").append(viettelDebt.getBalanceDifferentReason()).append("</balanceDifferentReason>");

                    rawData.append("<ownerType>").append(viettelDebt.getOwnerType()).append("</ownerType>");
                    rawData.append("<ownerId>").append(viettelDebt.getOwnerId()).append("</ownerId>");
                    rawData.append("<status>").append(viettelDebt.getStatus()).append("</status>");
                    rawData.append("<debitTypeDetail >").append(viettelDebt.getDebitTypeDetail()).append("</debitTypeDetail >");
                    rawData.append("<endCycleBalance>").append(viettelDebt.getEndCycleBalance()).append("</endCycleBalance>");
                    rawData.append("</confirmDebitTransDTOs>");
                }
                rawData.append("</input>");
                rawData.append("</ws:" + methodName + ">");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                        mActivity, "mbccs_" + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();

                // parser
                Serializer serializer = new Persister();
                result = serializer.read(SaleOutput.class, original);
                if (result == null) {
                    errorCode = Constant.ERROR_CODE;
                    description =
                            mActivity.getString(R.string.no_return_from_system);
                    return null;
                }
                errorCode = result.getErrorCode();
                description =
                        result.getDescription();
                return null;
            } catch (Exception e) {
                Log.e(Constant.TAG, methodName, e);
                result = new SaleOutput();
                result.setDescription(e.getMessage());
                result.setErrorCode(Constant.ERROR_CODE);
            }
            return null;
        }
    }
}
