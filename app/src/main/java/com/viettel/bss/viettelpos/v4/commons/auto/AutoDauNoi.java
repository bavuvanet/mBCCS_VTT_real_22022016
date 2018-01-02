package com.viettel.bss.viettelpos.v4.commons.auto;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ReasonFullDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.event.DauNoiCallback;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.HTHMMobileHandler;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.ProductOfferringHanlder;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by diepdc on 19/8/2017.
 */

public class AutoDauNoi implements AutoConst {
    Map<String, String> mMapTemplate = new HashMap<String, String>();

    //package
    private ArrayList<ProductOfferingDTO> lstGoiCuoc = new ArrayList<ProductOfferingDTO>();
    private Long telecomServiceId;
    private ArrayList<ProductOfferingDTO> lstProductOfferingDTOHasAtrr = new ArrayList<ProductOfferingDTO>();
    //subtype
    private ArrayList<SubTypeBeans> lstData = new ArrayList<SubTypeBeans>();
    private String productCode;
    private String serviceType;
    // hthm
    private ArrayList<HTHMBeans> arrHthmBeans = new ArrayList<HTHMBeans>();
    private String offerId;
    private String payType;
    private String subType;
    private String custType;
    private AreaObj area;
    private String technology;

    //promotion
    private ArrayList<PromotionTypeBeans> lstPromotionTypeBeanses = new ArrayList<PromotionTypeBeans>();
    private String province;
    private String regType;

    private Activity mActivity;
    private DauNoiCallback mDauNoiCallback;

    public AutoDauNoi(Activity context, DauNoiCallback callback, Map<String, String> map) {
        mActivity = context;
        mDauNoiCallback = callback;
        mMapTemplate = map;

        for (Map.Entry<String, String> e : mMapTemplate.entrySet()) {
            String key = e.getKey();
            String value = e.getValue();

            System.out.println("12345 " + key + " : " + value);
        }
    }

    public void getPackage(ArrayList<ProductOfferingDTO> lstGC, Long telecomSId) {
        this.lstGoiCuoc = lstGC;
        this.telecomServiceId = telecomSId;
        System.out.println("12345 getPackage");
        if (CommonActivity.isNullOrEmpty(lstGoiCuoc)) {
            GetListPakageAsyn asy = new GetListPakageAsyn(mActivity, false);
            asy.execute();
        } else {
            setPackage();
        }
    }

    public void getSubtype(ArrayList<SubTypeBeans> list, String code, String type) {
        System.out.println("12345 getSubtype");
        lstData = list;
        productCode = code;
        serviceType = type;

        if (CommonActivity.isNullOrEmpty(lstData)) {
            GetListSubType asy = new GetListSubType(mActivity);
            asy.execute();
        } else {
            setSubType();
        }
    }

    public void getHTHM(ArrayList<HTHMBeans> aarrHthmBeans,
                        String aofferId,
                        String aserviceType,
                        String apayType,
                        String asubType,
                        String acustType,
                        AreaObj aarea,
                        String atechnology) {
        arrHthmBeans = aarrHthmBeans;
        offerId = aofferId;
        serviceType = aserviceType;
        payType = apayType;
        subType = asubType;
        custType = acustType;
        area = aarea;
        technology = atechnology;
        System.out.println("12345 getHTHM");
        if (CommonActivity.isNullOrEmpty(arrHthmBeans)) {
            GetHTHMAsyn asy = new GetHTHMAsyn(mActivity);
            asy.execute();
        } else {
            setHthm();
        }
    }

    public void getPromotion(String custType,
                             String province,
                             String regType,
                             String offerId,
                             String serviceType,
                             String subType) {
        GetPromotionAsytask getPromotionAsytask = new GetPromotionAsytask(mActivity);
        getPromotionAsytask.execute(custType, province, regType, offerId, serviceType, subType);
    }

    private void getCDT() {

    }

    private void setPackage() {
        System.out.println("12345 setPackage " + lstGoiCuoc.size());
        if (lstGoiCuoc == null) return;

        for (int i = 0, n = lstGoiCuoc.size(); i < n; i++) {
            String str = lstGoiCuoc.get(i).getCode() + " - " + lstGoiCuoc.get(i).getName();
            System.out.println("12345 str " + str + " == " + mMapTemplate.get(AutoConst.PACKAGE));
            if (str.equals(mMapTemplate.get(AutoConst.PACKAGE))) {
                mDauNoiCallback.packageCallback(lstGoiCuoc.get(i), lstGoiCuoc);
                break;
            }
        }
    }

    private void setSubType() {
        if (lstData == null) return;

        for (int i = 0, n = lstData.size(); i < n; i++) {
            System.out.println("12345 setSubType " + lstData.get(i).getName() + " == " + mMapTemplate.get(AutoConst.LOAITB));
            if (lstData.get(i).getName().equals(mMapTemplate.get(AutoConst.LOAITB))) {
                mDauNoiCallback.subTypeCallback(lstData.get(i), lstData);
                break;
            }
        }
    }

    private void setHthm() {
        if (arrHthmBeans == null) return;

        for (int i = 0, n = arrHthmBeans.size(); i < n; i++) {
            String str = arrHthmBeans.get(i).getCode() + " - " + arrHthmBeans.get(i).getName();
            if (str.equals(mMapTemplate.get(AutoConst.HTHM))) {
                mDauNoiCallback.hthmCallback(arrHthmBeans.get(i));
                break;
            }
        }
    }

    private void setPromotion() {
        if (lstPromotionTypeBeanses == null) return;

        for (int i = 0, n = lstPromotionTypeBeanses.size(); i < n; i++) {
            System.out.println("12345 setPromotion: " + lstPromotionTypeBeanses.get(i).getCodeName() + " = " + mMapTemplate.get(AutoConst.KM));
            if (lstPromotionTypeBeanses.get(i).getCodeName().equals(mMapTemplate.get(AutoConst.KM))) {
                mDauNoiCallback.promotionCallback(lstPromotionTypeBeanses.get(i));
                break;
            }
        }
    }

    public class GetListPakageAsyn extends AsyncTask<String, Void, ArrayList<ProductOfferingDTO>> {

        ProgressDialog progress;
        private Context context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        boolean isSmartSim = false;

        public GetListPakageAsyn(Context context, boolean isSmart) {
            this.isSmartSim = isSmart;
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<ProductOfferingDTO> doInBackground(String... params) {
            return sendRequestGetListService();
        }

        @Override
        protected void onPostExecute(ArrayList<ProductOfferingDTO> result) {
            progress.dismiss();
            if (errorCode.equals("0")) {
                if (!CommonActivity.isNullOrEmpty(result)) {
                    lstGoiCuoc = result;
                    setPackage();
                } else {
                    lstGoiCuoc = new ArrayList<>();
                    // th ko co goi cuoc
                }
            } else {
                lstGoiCuoc = new ArrayList<>();
                if (Constant.INVALID_TOKEN2.equals(errorCode)) {
                    CommonActivity.BackFromLogin(mActivity,
                            description);
                } else {
                    if (CommonActivity.isNullOrEmpty(description)) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(context,
                            description,
                            mActivity.getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        public ArrayList<ProductOfferingDTO> sendRequestGetListService() {
            String original = null;
            FileInputStream in = null;
            lstProductOfferingDTOHasAtrr = new ArrayList<ProductOfferingDTO>();
            ArrayList<ProductOfferingDTO> lisPakageChargeBeans = new ArrayList<>();
            try {
                BCCSGateway input = new BCCSGateway();
                StringBuilder rawData = new StringBuilder();
                rawData.append(
                        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");

                rawData.append("<soapenv:Header/>");
                rawData.append("<soapenv:Body>");
                rawData.append("<ws:getProductCodeByMapActiveInfo>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<telecomServiceId>" + telecomServiceId);
                rawData.append("</telecomServiceId>");
                rawData.append("<payType>" + "1");
                rawData.append("</payType>");

                rawData.append("<actionCode>" + "00");
                rawData.append("</actionCode>");

                rawData.append("</input>");
                rawData.append("</ws:getProductCodeByMapActiveInfo>");
                rawData.append("</soapenv:Body>");
                rawData.append("</soapenv:Envelope>");
                Log.i("RowData", rawData.toString());
                String envelope = rawData.toString();
                Log.e("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);

                String fileName = input.sendRequestWriteToFile(envelope, Constant.WS_PAKAGE_DATA,
                        Constant.PAKAGE_FILE_NAME);
                if (fileName != null) {

                    try {

                        File file = new File(fileName);
//                        if (!file.mkdirs()) {
//                            file.createNewFile();
//                        }
                        in = new FileInputStream(file);
                        ProductOfferringHanlder handler = (ProductOfferringHanlder) CommonActivity
                                .parseXMLHandler(new ProductOfferringHanlder(), new InputSource(in));
//                        file.delete();
                        if (handler != null) {
                            if (handler.getItem().getToken() != null && !handler.getItem().getToken().isEmpty()) {
                                Session.setToken(handler.getItem().getToken());
                            }

                            if (handler.getItem().getErrorCode() != null) {
                                errorCode = handler.getItem().getErrorCode();
                            }
                            if (handler.getItem().getDescription() != null) {
                                description = handler.getItem().getDescription();
                            }
                            lisPakageChargeBeans = handler.getLstData();

                            if (!CommonActivity.isNullOrEmpty(handler) && !CommonActivity.isNullOrEmptyArray(handler.getLstProductOfferingDTOHasAtrr())) {
                                lstProductOfferingDTOHasAtrr = handler.getLstProductOfferingDTOHasAtrr();
                            } else {
                                lstProductOfferingDTOHasAtrr = new ArrayList<ProductOfferingDTO>();
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Exception", e.toString(), e);
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                Log.e("Exception", e.toString(), e);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                Log.e("Exception", e.toString(), e);
            }

            return lisPakageChargeBeans;
        }
    }

    ////////////////////////// GET SUBTYPE //////////////////////////////////
    public class GetListSubType extends AsyncTask<String, Void, ArrayList<SubTypeBeans>> {

        ProgressDialog progress;
        private Context context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        public GetListSubType(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<SubTypeBeans> doInBackground(String... arg0) {
            return getListSubTypeBeans();
        }

        @Override
        protected void onPostExecute(ArrayList<SubTypeBeans> result) {
            progress.dismiss();
            if (errorCode.equals("0")) {
                if (result != null && result.size() > 0) {
                    lstData = result;
                    setSubType();
                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin(mActivity, description);
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity, description, mActivity.getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        private ArrayList<SubTypeBeans> getListSubTypeBeans() {
            ArrayList<SubTypeBeans> lisSubTypeBeans = new ArrayList<SubTypeBeans>();
            String original = "";
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_getLsSubTypesByTelService");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getLsSubTypesByTelService>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<telService>" + serviceType);
                rawData.append("</telService>");
                rawData.append("<productCode>" + productCode);
                rawData.append("</productCode>");

                rawData.append("</input>");
                rawData.append("</ws:getLsSubTypesByTelService>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, context,
                        "mbccs_getLsSubTypesByTelService");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();

                // ===============parse xml =========================
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild = null;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                    nodechild = doc.getElementsByTagName("lstSubTypeDTOs");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        SubTypeBeans subTypeBeans = new SubTypeBeans();
                        String name = parse.getValue(e1, "name");
                        Log.d("name", name);
                        subTypeBeans.setName(name);
                        String subType = parse.getValue(e1, "subType");
                        Log.d("subType", subType);
                        subTypeBeans.setSubType(subType);

                        lisSubTypeBeans.add(subTypeBeans);
                    }
                }

            } catch (Exception e) {
                Log.d("getListSubTypeBeans", e.toString());
            }
            return lisSubTypeBeans;
        }
    }

    ////////////////////////////// LAY DANH SACH GOI CUOC
    public class GetHTHMAsyn extends
            AsyncTask<String, Void, ArrayList<HTHMBeans>> {

        ProgressDialog progress;
        private Context context = null;
        String errorCode = "";
        String description = "";

        public GetHTHMAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<HTHMBeans> doInBackground(String... arg0) {
            //	return getReasonFullIncludeSpec();
            return getListHTHM();
        }

        @Override
        protected void onPostExecute(ArrayList<HTHMBeans> result) {

            // TODO get hinh thuc hoa mang
            progress.dismiss();
            if (errorCode.equals("0")) {
                if (arrHthmBeans != null && !arrHthmBeans.isEmpty()) {
                    arrHthmBeans = new ArrayList<HTHMBeans>();
                }
                if (result != null && result.size() > 0) {
                    arrHthmBeans = result;
                    setHthm();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(context,
                            mActivity.getResources().getString(R.string.noththm),
                            mActivity.getResources().getString(R.string.app_name));
                    dialog.show();
                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin(mActivity,
                            description);
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(context,
                            description,
                            mActivity.getResources().getString(R.string.app_name));
                    dialog.show();

                }
            }

        }

        // ==input String offerId, String serviceType, String province ,String
        // district, String precinct)
        private ArrayList<HTHMBeans> getListHTHM() {
            ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<HTHMBeans>();
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_getListRegTypeConnectSP");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListRegTypeConnectSP>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<offerId>" + offerId);
                rawData.append("</offerId>");
                rawData.append("<serviceType>" + serviceType);
                rawData.append("</serviceType>");
                rawData.append("<payType>" + payType);
                rawData.append("</payType>");

                rawData.append("<subType>").append(subType);
                rawData.append("</subType>");

                rawData.append("<custType>").append(custType);
                rawData.append("</custType>");

                rawData.append("<province>").append(area.getProvince());
                rawData.append("</province>");

                rawData.append("<district>").append(area.getDistrict());
                rawData.append("</district>");

                rawData.append("<precinct>").append(area.getPrecinct());
                rawData.append("</precinct>");
                rawData.append("</input>");
                rawData.append("</ws:getListRegTypeConnectSP>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, context,
                        "mbccs_getListRegTypeConnectSP");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ====== parse xml ===================

                HTHMMobileHandler handler = (HTHMMobileHandler) CommonActivity
                        .parseXMLHandler(new HTHMMobileHandler(), original);
                output = handler.getItem();
                if (output.getToken() != null && !output.getToken().isEmpty()) {
                    Session.setToken(output.getToken());
                }

                if (output.getErrorCode() != null) {
                    errorCode = output.getErrorCode();
                }
                if (output.getDescription() != null) {
                    description = output.getDescription();
                }
                lstHthmBeans = handler.getLstData();
                // ====== parse xml ===================
            } catch (Exception e) {
                Log.d("getListHTHM", e.toString());
            }

            return lstHthmBeans;
        }

        private ArrayList<HTHMBeans> getReasonFullIncludeSpec() {
            ArrayList<HTHMBeans> lstHthmBeans = new ArrayList<HTHMBeans>();
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode",
                        "mbccs_getReasonFullIncludeSpec");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getReasonFullIncludeSpec>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<offerId>" + offerId);
                rawData.append("</offerId>");
                rawData.append("<serviceType>" + serviceType);
                rawData.append("</serviceType>");
                rawData.append("<payType>" + payType);
                rawData.append("</payType>");

                rawData.append("<subType>").append(subType);
                rawData.append("</subType>");

                rawData.append("<custType>").append(custType);
                rawData.append("</custType>");

                rawData.append("<province>").append(area.getProvince());
                rawData.append("</province>");

                rawData.append("<district>").append(area.getDistrict());
                rawData.append("</district>");

                rawData.append("<precinct>").append(area.getPrecinct());
                rawData.append("</precinct>");

                rawData.append("<technology>" + technology);
                rawData.append("</technology>");

                if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.groupsDTO) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.groupsDTO.getGroupId())) {
                    rawData.append("<groupId>").append(TabThueBaoHopDongManager.instance.groupsDTO.getGroupId());
                    rawData.append("</groupId>");
                }

                if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit)) {
                    if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode())) {
                        rawData.append("<stationCodes>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode());
                        rawData.append("</stationCodes>");
                    }
                }


                if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm())) {
                    if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getVendorCode())) {
                        rawData.append("<vendor>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getVendorCode());
                        rawData.append("</vendor>");
                    }
                    if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getConnectorCode())) {
                        rawData.append("<nodeCode>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getConnectorCode());
                        rawData.append("</nodeCode>");
                    }


                }
                rawData.append("</input>");
                rawData.append("</ws:getReasonFullIncludeSpec>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, context,
                        "mbccs_getReasonFullIncludeSpec");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("original", original);
                // ====== parse xml ===================
                Serializer serializer = new Persister();
                ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput != null) {
                    errorCode = parseOuput.getErrorCode();
                    description = parseOuput.getDescription();
                    ArrayList<ReasonFullDTO> arrayList = parseOuput.getLstReasonFullDTOs();
                    if (!CommonActivity.isNullOrEmpty(arrayList)) {
                        for (ReasonFullDTO item : arrayList) {
                            HTHMBeans hthmBeans = new HTHMBeans();
                            hthmBeans.setName(item.getReasonDTO().getName());
                            hthmBeans.setCode(item.getReasonDTO().getReasonCode());
                            hthmBeans.setReasonId(item.getReasonDTO().getReasonId());
                            lstHthmBeans.add(hthmBeans);
                        }
                    }
                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = mActivity.getString(R.string.no_data_return);
                }
//				HTHMMobileHandler handler = (HTHMMobileHandler) CommonActivity
//						.parseXMLHandler(new HTHMMobileHandler(), original);
//				output = handler.getItem();
//				if (output.getToken() != null && !output.getToken().isEmpty()) {
//					Session.setToken(output.getToken());
//				}
//
//				if (output.getErrorCode() != null) {
//					errorCode = output.getErrorCode();
//				}
//				if (output.getDescription() != null) {
//					description = output.getDescription();
//				}
//				lstHthmBeans = handler.getLstData();
                // ====== parse xml ===================
            } catch (Exception e) {
                Log.d("getListHTHM", e.toString());
            }

            return lstHthmBeans;
        }
    }

    //////////////// GET PROMOTION
    public class GetPromotionAsytask extends
            AsyncTask<String, Void, ArrayList<PromotionTypeBeans>> {

        private Activity context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        private ProgressDialog progress;

        public GetPromotionAsytask(Activity context) {

            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<PromotionTypeBeans> doInBackground(String... arg0) {
            return getPromotionTypeBeans(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4], arg0[5]);
        }

        @Override
        protected void onPostExecute(ArrayList<PromotionTypeBeans> result) {
            this.progress.dismiss();
            if (errorCode.equals("0")) {
                if (result != null && result.size() > 0) {
                    lstPromotionTypeBeanses = result;
                    setPromotion();
                } else {
                    // nothing
                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    CommonActivity.BackFromLogin((Activity) context, description);
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(context,
                            context.getString(R.string.error_get_promotion,
                                    description), context
                                    .getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        /**
         *
         * @return
         */

//		bundle.putString("custType",custType + "");
//		bundle.putString("province",area.getProvince() + "");
//		bundle.putString("regType", sub.getHthm().getCode());
//		bundle.putString("offerId",sub.getOfferId() + "");
//		bundle.putString("serviceType",sub.getSubType());
        private ArrayList<PromotionTypeBeans> getPromotionTypeBeans(String custType, String province, String regType, String offerId, String serviceType, String subType) {
            ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_getPromotions");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getPromotions>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<reasonId>" + regType + "</reasonId>");
                rawData.append("<offerId>" + offerId + "</offerId>");
                rawData.append("<serviceType>" + serviceType + "</serviceType>");
                rawData.append("<custType>" + custType + "</custType>");
                rawData.append("<payType>" + 1 + "</payType>");
                rawData.append("<province>" + province + "</province>");

                rawData.append("<actionCode>" + "00" + "</actionCode>");

                if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm())) {

                    if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getConnectorCode())) {
                        rawData.append("<nodeCode>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getConnectorCode());
                        rawData.append("</nodeCode>");
                    }


                }

                rawData.append("</input>");
                rawData.append("</ws:getPromotions>");

//			input.addValidateGateway("wscode", "mbccs_getListPromotionByMapSP");
//			StringBuilder rawData = new StringBuilder();
//			rawData.append("<ws:getListPromotionByMapSP>");
//			rawData.append("<input>");
//			HashMap<String, String> paramToken = new HashMap<String, String>();
//			paramToken.put("token", Session.getToken());
//			rawData.append(input.buildXML(paramToken));
//			rawData.append("<regType>" + sub.getHthm().getCode() + "</regType>");
//			rawData.append("<offerId>" + sub.getOfferId() + "</offerId>");
//			rawData.append("<serviceType>" + sub.getServiceType()
//					+ "</serviceType>");
//			rawData.append("<custType>" + custType + "</custType>");
//			rawData.append("<payType>" + 1 + "</payType>");
//			rawData.append("<subType>" + sub.getSubType() + "</subType>");
//			rawData.append("<province>" + province + "</province>");
//			rawData.append("<district>" + "-1" + "</district>");
//			rawData.append("<precinct>" + "-1" + "</precinct>");
//			// rawData.append("<actionCode>" + "00" + "</actionCode>");
//			rawData.append("</input>");
//			rawData.append("</ws:getListPromotionByMapSP>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                        context, "mbccs_getPromotions");
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
                        lisPromotionTypeBeans.add(promotionTypeBeans);
                    }
                }
            } catch (Exception ex) {
                Log.d("getPromotionTypeBeans", ex.toString());
            }
            return lisPromotionTypeBeans;
        }
    }
}
