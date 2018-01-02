package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hantt47 on 9/27/2017.
 */

public class GetMappingChannelCustTypeAsyn extends AsyncTask<String, Void, ArrayList<CustTypeDTO>> {
    private Context context = null;
    String errorCode = "";
    String description = "";
    private  OnPostExecuteListener<ArrayList<CustTypeDTO>> listener;

    public GetMappingChannelCustTypeAsyn(Context context,  OnPostExecuteListener<ArrayList<CustTypeDTO>> listener ) {
        this.context = context;
        this.listener = listener;

    }

    @Override
    protected ArrayList<CustTypeDTO> doInBackground(String... params) {
        return getMappingChannelCustType(params[0]);
    }


    private ArrayList<CustTypeDTO> getMappingChannelCustType(String payType) {
        ArrayList<CustTypeDTO> lstCustTypeDTOs = new ArrayList<>();
        String original = null;
        try {
            // tra truoc
            if ("2".equals(payType)) {
                lstCustTypeDTOs = new CacheDatabaseManager(context).getListCusTypeDTO("cusTypeDTOPre");
            } else {
                // tra sau
                lstCustTypeDTOs = new CacheDatabaseManager(context).getListCusTypeDTO("cusTypeDTOPos");
            }

            if (lstCustTypeDTOs != null && !lstCustTypeDTOs.isEmpty()) {
                errorCode = "0";
                return lstCustTypeDTOs;
            }

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getMappingChannelCustType");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getMappingChannelCustType>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<>();
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));
            rawData.append("<payType>").append(payType);
            rawData.append("</payType>");
            rawData.append("</input>");
            rawData.append("</ws:getMappingChannelCustType>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context,
                    "mbccs_getMappingChannelCustType");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.d("original", original);
            // ========parser xml get employ from server
            Serializer serializer = new Persister();
            ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
            if (parseOuput != null) {
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
                lstCustTypeDTOs = parseOuput.getLstCustTypeDTO();
            }

        } catch (Exception e) {
            Log.d("exception", e.toString());
        }
        if ("2".equals(payType)) {
            new CacheDatabaseManager(context).insertCusType("cusTypeDTOPre", lstCustTypeDTOs);
        } else {
            new CacheDatabaseManager(context).insertCusType("cusTypeDTOPos", lstCustTypeDTOs);
        }

        return lstCustTypeDTOs;
    }
}