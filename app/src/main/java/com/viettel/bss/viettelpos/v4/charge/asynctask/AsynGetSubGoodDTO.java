package com.viettel.bss.viettelpos.v4.charge.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.SubGoodsDTO;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostSuccessExecute;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinhhq1 on 6/22/2017.
 */
public class AsynGetSubGoodDTO extends AsyncTask<Void, Void, List<SubGoodsDTO>> {

    private Activity context;
    private ProgressDialog progress;
    private String errorCode;
    private String description;
    private OnPostSuccessExecute<List<SubGoodsDTO>> onPostExecute;
    private View.OnClickListener moveLogInAct;
    private List<String> lstSubId;
    private Boolean isReturnEmpty;

    public AsynGetSubGoodDTO(Activity context, OnPostSuccessExecute<List<SubGoodsDTO>> listener
            , List<String> lstSubId,View.OnClickListener moveLogInAct) {
        this.context = context;
        this.onPostExecute = listener;
        this.lstSubId = lstSubId;
        this.moveLogInAct=moveLogInAct;
        this.progress = new ProgressDialog(context);
        this.progress.setCancelable(false);
        this.progress
                .setMessage(context.getString(R.string.searching));
        this.progress.show();

    }

    @Override
    protected List<SubGoodsDTO> doInBackground(Void... params) {
         if(CommonActivity.isNullOrEmpty(lstSubId)){
            Dialog dialog = CommonActivity.createAlertDialog(context,context.getString(R.string. message_check_account),
                    context.getString(R.string.app_name));
            dialog.show();
            return new ArrayList<>();
        } else return getSubgood();
    }

    @Override
    protected void onPostExecute(List<SubGoodsDTO> result) {
        super.onPostExecute(result);
        this.progress.dismiss();
        if (CommonActivity.isNullOrEmpty(result) ) result=new ArrayList<>();
        onPostExecute.onPostSuccess(result);
        if ("0".equals(errorCode)) {

        } else {
            if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                Dialog dialog = CommonActivity.createAlertDialog(context,
                        description,
                        context.getResources().getString(R.string.app_name),
                        moveLogInAct);
                dialog.show();
            } else {
                if (description == null || description.isEmpty()) {
                    description = "Không tìm thấy danh sách hàng hóa" ;
                }
                Dialog dialog = CommonActivity.createAlertDialog(context,
                        description,
                        context.getResources().getString(R.string.app_name));
                dialog.show();
            }
        }

    }

    private List<SubGoodsDTO> getSubgood() {
        String original = "";
        ParseOuput out;
        String func = "getListSubGoodRevoke";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            for (String id : lstSubId) {
                rawData.append("<lstSubId>").append(id)
                        .append("</lstSubId>");
            }
            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    context, "mbccs_" + func);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            // parser
            Serializer serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);
            if (out == null) {
                description = context
                        .getString(R.string.no_return_from_system);
                errorCode = Constant.ERROR_CODE;
                return new ArrayList<>();
            } else {
                description = out.getDescription();
                errorCode = out.getErrorCode();
                return out.getLstSubGoodsDTOs();
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, func, e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }

        return new ArrayList<>();
    }

}


