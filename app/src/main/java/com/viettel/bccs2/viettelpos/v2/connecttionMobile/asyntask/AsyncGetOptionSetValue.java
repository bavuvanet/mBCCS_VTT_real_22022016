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
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thinhhq1 on 4/25/2017.
 */

public class AsyncGetOptionSetValue extends AsyncTaskCommon<String, Void, ArrayList<Spin>> {
    private Activity context;
    private String type;
    public AsyncGetOptionSetValue(Activity context, OnPostExecuteListener<ArrayList<Spin>> listener,
                                  View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
        this.context = context;
    }
    public AsyncGetOptionSetValue(Activity context, OnPostExecuteListener<ArrayList<Spin>> listener,
                                  View.OnClickListener moveLogInAct, String typecheck) {
        super(context, listener, moveLogInAct);
        this.context = context;
        this.type = typecheck;
    }
    @Override
    protected ArrayList<Spin> doInBackground(String... params) {
        return getOptionSetValue(params[0]);
    }

    private ArrayList<Spin> getOptionSetValue(String code) {
        ArrayList<Spin> lstNoticeMethod = new ArrayList<Spin>();
        String original = "";
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getLsOptionSetValueByCode");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getLsOptionSetValueByCode>");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            //code "IDTYPE_FIELD_USAGE"
            rawData.append("<code>" + code + "</code>");

            rawData.append("</input>");
            rawData.append("</ws:getLsOptionSetValueByCode>");

            Log.i("LOG", "raw data" + rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("LOG", "Send evelop" + envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context,
                    "mbccs_getLsOptionSetValueByCode");
            Log.i("LOG", "Respone:  " + response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("LOG", "Responseeeeeeeeee Original  " + response);

            // parser

            lstNoticeMethod = parserListGroup(original);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstNoticeMethod;
    }
    public ArrayList<Spin> parserListGroup(String original) {
        ArrayList<Spin> lstReason = new ArrayList<Spin>();
        Document doc = parse.getDomElement(original);
        NodeList nl = doc.getElementsByTagName("return");
        NodeList nodechild = null;
        for (int i = 0; i < nl.getLength(); i++) {
            Element e2 = (Element) nl.item(i);
            errorCode = parse.getValue(e2, "errorCode");
            description = parse.getValue(e2, "description");
            Log.d("errorCode", errorCode);
            nodechild = doc.getElementsByTagName("lstOptionSetValueDTOs");
            for (int j = 0; j < nodechild.getLength(); j++) {
                Element e1 = (Element) nodechild.item(j);
                Spin spin = new Spin();
                spin.setValue(parse.getValue(e1, "name"));

                spin.setId(parse.getValue(e1, "value"));

                lstReason.add(spin);
            }
        }
        return lstReason;
    }
}
