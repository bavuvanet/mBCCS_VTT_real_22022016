package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidDetailBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public class GetAllListPaymentPrePaidAsyn extends
        AsyncTask<Void, Void, ArrayList<PaymentPrePaidPromotionBeans>> {

    private Activity context = null;
    XmlDomParse parse = new XmlDomParse();
    String errorCode = "";
    String description = "";
    private SubscriberDTO sub;
    private Spinner spnCuocDongTruoc;
    private View prb;
    private String province;
    private Map<String, ArrayList<PaymentPrePaidPromotionBeans>> mapPrepaid = new HashMap<String, ArrayList<PaymentPrePaidPromotionBeans>>();

    public GetAllListPaymentPrePaidAsyn(Activity context,
                                        SubscriberDTO currentSub, Spinner spnCuocDongTruoc, View prb,
                                        String province,
                                        Map<String, ArrayList<PaymentPrePaidPromotionBeans>> mapPrepaid) {
        this.context = context;
        this.sub = currentSub;
        this.province = province;
        this.spnCuocDongTruoc = spnCuocDongTruoc;
        this.prb = prb;
        this.mapPrepaid = mapPrepaid;
        if (prb != null) {
            prb.setVisibility(View.GONE);
        }

    }

    @Override
    protected ArrayList<PaymentPrePaidPromotionBeans> doInBackground(
            Void... arg0) {
        return getAllListPaymentPrePaid();
    }

    @Override
    protected void onPostExecute(ArrayList<PaymentPrePaidPromotionBeans> result) {
        try {
            prb.setVisibility(View.GONE);
            if (errorCode.equals("0")) {
                sub.setGetCDT(true);

                if ("-1".equals(sub.getPromotion().getPromProgramCode()) || CommonActivity.isNullOrEmptyArray(result)) {
                    PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
                    paymentPrePaidPromotionBeans.setName(context
                            .getString(R.string.khong_chon_cdt));
                    paymentPrePaidPromotionBeans.setPrePaidCode("-1");
                    result.add(0, paymentPrePaidPromotionBeans);
                }
//			PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
//			paymentPrePaidPromotionBeans.setName(context
//					.getString(R.string.txt_select));
//			paymentPrePaidPromotionBeans.setPrePaidCode("");
//			result.add(0, paymentPrePaidPromotionBeans);
                mapPrepaid.put(sub.getCuocDongTruocKey(), result);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        R.layout.spinner_item, R.id.spinner_value);
                for (PaymentPrePaidPromotionBeans typePaperBeans : result) {
                    adapter.add(typePaperBeans.getName());
                }
                spnCuocDongTruoc.setAdapter(adapter);
            } else {
                sub.setGetCDT(false);
                if (errorCode.equals(Constant.INVALID_TOKEN2)
                        && description != null && !description.isEmpty()) {
                    CommonActivity.BackFromLogin(context, description);
                } else {
                    result = new ArrayList<PaymentPrePaidPromotionBeans>();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            context, R.layout.spinner_item, R.id.spinner_value);
                    for (PaymentPrePaidPromotionBeans typePaperBeans : result) {
                        adapter.add(typePaperBeans.getName());
                    }
                    spnCuocDongTruoc.setAdapter(adapter);
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(context,
                            description, context.getString(R.string.app_name));
                    dialog.show();
                }
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
    }

    private ArrayList<PaymentPrePaidPromotionBeans> getAllListPaymentPrePaid() {
//		ArrayList<PaymentPrePaidPromotionBeans> lstPaymentPrePaidPromotionBeans = mapPrepaid
//				.get(sub.getCuocDongTruocKey());
//		if (!CommonActivity.isNullOrEmpty(lstPaymentPrePaidPromotionBeans)) {
//			errorCode = "0";
////			lstPaymentPrePaidPromotionBeans.remove(0);
//			return lstPaymentPrePaidPromotionBeans;
//		}
        ArrayList<PaymentPrePaidPromotionBeans> lstPaymentPrePaidPromotionBeans = new ArrayList<PaymentPrePaidPromotionBeans>();
        String original = null;
        try {

            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getAllListPaymentPrePaid");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getAllListPaymentPrePaid>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));

            String promotionCode = sub.getPromotion().getPromProgramCode();
            if(!CommonActivity.isNullOrEmpty(sub.getPromotion().getPromProgramCode()) && "-1".equals(sub.getPromotion().getPromProgramCode())){
                promotionCode = "";
            }

            rawData.append("<promProgramCode>"
                    + promotionCode);
            rawData.append("</promProgramCode>");

            rawData.append("<packageId>" + sub.getProductCode());
            rawData.append("</packageId>");

            rawData.append("<provinceCode>" + province);
            rawData.append("</provinceCode>");

            // rawData.append("<today>" + today);
            // rawData.append("</today>");

            rawData.append("</input>");
            rawData.append("</ws:getAllListPaymentPrePaid>");
            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    context, "mbccs_getAllListPaymentPrePaid");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Responseeeeeeeeee", original);

            // parse xml
            Document doc = parse.getDomElement(original);
            NodeList nl = doc.getElementsByTagName("return");
            NodeList nodepaymentPrePaidPromotionBeans = null;
            NodeList nodePaymentPrePaidDetailBeans = null;
            for (int i = 0; i < nl.getLength(); i++) {
                Element e2 = (Element) nl.item(i);
                errorCode = parse.getValue(e2, "errorCode");
                Log.d("errorCode", errorCode);
                description = parse.getValue(e2, "description");
                Log.d("description", description);
                nodepaymentPrePaidPromotionBeans = e2
                        .getElementsByTagName("paymentPrePaidPromotionBeans");
                for (int j = 0; j < nodepaymentPrePaidPromotionBeans
                        .getLength(); j++) {
                    Element e1 = (Element) nodepaymentPrePaidPromotionBeans
                            .item(j);
                    PaymentPrePaidPromotionBeans paymentPrePaidPromotionBeans = new PaymentPrePaidPromotionBeans();
                    String name = parse.getValue(e1, "name");
                    paymentPrePaidPromotionBeans.setName(name);

                    String prePaidCode = parse.getValue(e1, "prePaidCode");
                    paymentPrePaidPromotionBeans.setPrePaidCode(prePaidCode);

                    String id = parse.getValue(e1, "id");
                    paymentPrePaidPromotionBeans.setId(id);

                    ArrayList<PaymentPrePaidDetailBeans> lstPaymentPrePaidDetailBeans = new ArrayList<PaymentPrePaidDetailBeans>();

                    nodePaymentPrePaidDetailBeans = e1
                            .getElementsByTagName("paymentPrePaidDetailBeans");
                    for (int k = 0; k < nodePaymentPrePaidDetailBeans
                            .getLength(); k++) {
                        Element e0 = (Element) nodePaymentPrePaidDetailBeans
                                .item(k);
                        PaymentPrePaidDetailBeans paymentPrePaidDetailBeans = new PaymentPrePaidDetailBeans();
                        String moneyUnit = parse.getValue(e0, "moneyUnit");
                        paymentPrePaidDetailBeans.setMoneyUnit(moneyUnit);
                        String promAmount = parse.getValue(e0, "promAmount");
                        paymentPrePaidDetailBeans.setPromAmount(promAmount);
                        String endMonth = parse.getValue(e0, "endMonth");
                        paymentPrePaidDetailBeans.setEndMonth(endMonth);
                        String startMonth = parse.getValue(e0, "startMonth");
                        paymentPrePaidDetailBeans.setStartMonth(startMonth);
                        String subMonth = parse.getValue(e0, "subMonth");
                        paymentPrePaidDetailBeans.setSubMonth(subMonth);
                        lstPaymentPrePaidDetailBeans
                                .add(paymentPrePaidDetailBeans);
                    }
                    paymentPrePaidPromotionBeans
                            .setLstDetailBeans(lstPaymentPrePaidDetailBeans);

                    lstPaymentPrePaidPromotionBeans
                            .add(paymentPrePaidPromotionBeans);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstPaymentPrePaidPromotionBeans;
    }

}
