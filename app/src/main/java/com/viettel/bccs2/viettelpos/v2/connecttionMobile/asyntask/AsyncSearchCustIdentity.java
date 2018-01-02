package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PromotionOutput;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thinhhq1 on 4/25/2017.
 */

public class AsyncSearchCustIdentity extends AsyncTaskCommon<String, Void, ParseOuput> {
    private Activity context;
    public AsyncSearchCustIdentity(Activity context, OnPostExecuteListener<ParseOuput> listener,
                                   View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
        this.context = context;
    }

    @Override
    protected ParseOuput doInBackground(String... params) {
        return searchCus(params[0],params[1],params[2]);
    }

    private ParseOuput searchCus(String idNo, String custType, String idType) {
        String original = null;
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_searchCustIdentity");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:searchCustIdentity>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<>();
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));
            rawData.append("<custIdentitySearchDTO>");
            rawData.append("<idNo>").append(idNo);
            rawData.append("</idNo>");
            if (!CommonActivity.isNullOrEmpty(custType)) {
                rawData.append("<custType>").append(custType);
                rawData.append("</custType>");
            }
            if (!CommonActivity.isNullOrEmpty(idType)) {
                rawData.append("<idType>").append(idType);
                rawData.append("</idType>");
            }
            rawData.append("</custIdentitySearchDTO>");
            rawData.append("</input>");
            rawData.append("</ws:searchCustIdentity>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context,
                    "mbccs_searchCustIdentity");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Responseeeeeeeeee", original);

            Serializer serializer = new Persister();
            ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
            if (parseOuput != null) {
                errorCode = parseOuput.getErrorCode();
                description = parseOuput.getDescription();
            }

            return parseOuput;
        } catch (Exception e) {
            Log.i("SearchCustIdentity", e.toString());
        }
        return null;
    }
}
