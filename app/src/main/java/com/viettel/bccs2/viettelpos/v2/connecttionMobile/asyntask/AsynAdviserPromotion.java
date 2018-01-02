package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import android.app.Activity;
import android.content.Context;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thinhhq1 on 4/25/2017.
 */

public class AsynAdviserPromotion extends AsyncTaskCommon<String, Void, PromotionOutput> {
    private Activity context;
    public AsynAdviserPromotion(Activity context, OnPostExecuteListener<PromotionOutput> listener,
                             View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
        this.context = context;
    }

    @Override
    protected PromotionOutput doInBackground(String... params) {
        return getPromotions(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]);
    }

    private PromotionOutput getPromotions(String actionCode,String serviceType, String offerId, String province, String district, String precint, String subId , String firstConnect) {
        ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
        PromotionOutput promotionOutput = new PromotionOutput();
        String original = "";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_adviserPromotion");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:adviserPromotion>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));
            rawData.append("<subId>" + subId + "</subId>");
            rawData.append("<offerId>" + offerId + "</offerId>");
            rawData.append("<serviceType>" + serviceType + "</serviceType>");
            rawData.append("<payType>" + 1 + "</payType>");

            rawData.append("<actionCode>" + actionCode + "</actionCode>");
            rawData.append("<firstConnect>" + firstConnect + "</firstConnect>");
            rawData.append("<province>" + province + "</province>");
            rawData.append("<district>" + district + "</district>");
            rawData.append("<precinct>" + precint + "</precinct>");
            rawData.append("</input>");
            rawData.append("</ws:adviserPromotion>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_adviserPromotion");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();

            // ============parse xml in android=========
            Document doc = parse.getDomElement(original);
            NodeList nl = doc.getElementsByTagName("return");
            NodeList nodechild = null;
            for (int i = 0; i < nl.getLength(); i++) {
                Element e2 = (Element) nl.item(i);
                errorCode = parse.getValue(e2, "errorCode");
                Log.d("errorCode", errorCode);
                description = parse.getValue(e2, "description");
                Log.d("description", description);
                String avgSubCharge = parse.getValue(e2,"avgSubCharge");
                promotionOutput.setAvgSubCharge(avgSubCharge);
                nodechild = doc.getElementsByTagName("lstPromotionsBO");
                for (int j = 0; j < nodechild.getLength(); j++) {
                    Element e1 = (Element) nodechild.item(j);
                    PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
                    String codeName = parse.getValue(e1, "codeName");
                    Log.d("codeName", codeName);
                    promotionTypeBeans.setCodeName(codeName);

                    String name = parse.getValue(e1, "name");
                    Log.d("name", name);
                    promotionTypeBeans.setName(name);

                    String promProgramCode = parse.getValue(e1, "promProgramCode");
                    Log.d("code", promProgramCode);
                    promotionTypeBeans.setPromProgramCode(promProgramCode);

                    String descrip = parse.getValue(e1, "description");
                    Log.d("descriponnnnn", descrip);
                    if (CommonActivity.isNullOrEmpty(promotionTypeBeans.getDescription())) {
                        promotionTypeBeans.setDescription(descrip);
                    }

                    String monthAmount = parse.getValue(e1, "monthAmount");
                    promotionTypeBeans.setMonthAmount(monthAmount);
                    String monthCommitment = parse.getValue(e1, "monthCommitment");
                    promotionTypeBeans.setMonthCommitment(monthCommitment);

                    String chargeMonthly = parse.getValue(e1,"chargeMonthly");
                    promotionTypeBeans.setChargeMonthly(chargeMonthly);

                    if(!CommonActivity.isNullOrEmpty(chargeMonthly)){
                            promotionTypeBeans.setChargeMonthlyName(context.getString(R.string.chargeMonth,chargeMonthly));
                    }
                    lisPromotionTypeBeans.add(promotionTypeBeans);
                    promotionOutput.setLstPromotionTypeBeanses(lisPromotionTypeBeans);
                }
            }
        } catch (Exception ex) {
            Log.d("getPromotions", ex.toString());
        }

        return promotionOutput;
    }
}
