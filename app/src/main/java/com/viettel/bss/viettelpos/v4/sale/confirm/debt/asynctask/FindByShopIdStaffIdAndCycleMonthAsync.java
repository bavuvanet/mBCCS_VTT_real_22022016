package com.viettel.bss.viettelpos.v4.sale.confirm.debt.asynctask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
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

public class FindByShopIdStaffIdAndCycleMonthAsync extends AsyncTaskCommon<String, Void, List<ConfirmDebitTransDTO>> {

    private String cycleDate;
    private Long status;

    public FindByShopIdStaffIdAndCycleMonthAsync(Activity context,
                                                 OnPostExecuteListener<List<ConfirmDebitTransDTO>> listener,
                                                 View.OnClickListener moveLogInAct, String cycleDate, Long status) {
        super(context, listener, moveLogInAct,context.getString(R.string.getting_debt_staff));
        this.cycleDate = cycleDate;
        this.status = status;
    }

    @Override
    protected List<ConfirmDebitTransDTO> doInBackground(String... params) {
        {
            String original = "";
            SaleOutput result;
            String methodName = "findByShopIdStaffIdAndCycleMonth";
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:" + methodName + ">");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<fromDate >" + this.cycleDate + "</fromDate>");
                if(status!=null){
                    rawData.append("<status >" + this.status + "</status>");
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
                return result.getLstConfirmDebitTransDTOs();
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
