package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import android.app.Activity;
import android.util.Log;
import android.view.View;

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
 * Created by thinhhq1 on 8/26/2017.
 */
public class GetFilterByPricePromotionAsyn extends AsyncTaskCommon<String, Void, ArrayList<PromotionTypeBeans>> {


    public GetFilterByPricePromotionAsyn(Activity context, OnPostExecuteListener<ArrayList<PromotionTypeBeans>> listener,
                             View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);
    }

    @Override
    protected ArrayList<PromotionTypeBeans> doInBackground(String... arg0) {
        return getFilterByPricePromotion(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4], arg0[5], arg0[6], arg0[7]);
    }

    private ArrayList<PromotionTypeBeans> getFilterByPricePromotion(String actionCode, String serviceType, String offerId, String telecomServiceId, String promotionCode , String province, String district, String precint) {
        ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
        String original = "";
        String func = "filterByPricePromotionMbccs";
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_"+func );
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:"+func+">");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));

            rawData.append("<serviceType>" + serviceType + "</serviceType>");
            rawData.append("<actionCode>" + actionCode + "</actionCode>");
            rawData.append("<offerId>" + offerId + "</offerId>");
            rawData.append("<telecomServiceId>" + telecomServiceId + "</telecomServiceId>");
            rawData.append("<oldPromotion>" + promotionCode + "</oldPromotion>");

            rawData.append("<district>" + district + "</district>");
            rawData.append("<precinct>" + precint + "</precinct>");
            rawData.append("<payType>1</payType>");
            rawData.append("</input>");
            rawData.append("</ws:"+func+">");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity, "mbccs_" + func);
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
                nodechild = doc.getElementsByTagName("listPromotionTypeDTO");
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

                    String expireDate = parse.getValue(e1, "expireDate");
                    promotionTypeBeans.setExpireDate(expireDate);

                    String startDate = parse.getValue(e1, "startDate");
                    promotionTypeBeans.setStartDate(startDate);

                    String effectDate = parse.getValue(e1, "effectDate");
                    promotionTypeBeans.setEffectDate(effectDate);

                    lisPromotionTypeBeans.add(promotionTypeBeans);
                }
            }
        } catch (Exception ex) {
            Log.d("getPromotions", ex.toString());
        }

        return lisPromotionTypeBeans;
    }

}
