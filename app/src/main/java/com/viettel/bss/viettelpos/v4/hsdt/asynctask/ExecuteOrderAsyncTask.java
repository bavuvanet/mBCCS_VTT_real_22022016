package com.viettel.bss.viettelpos.v4.hsdt.asynctask;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncTaskCommonSupper;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OrderInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.omichanel.dao.RecordTypeScanDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thuandq on 10/20/2017.
 */

public class ExecuteOrderAsyncTask extends AsyncTaskCommonSupper<String, Void, ArrayList<SaleOutput>> {

    private FileObj fileSignObjOne;
    private FileObj fileSignObjTwo;
    private ArrayList<OrderInfo> arrOrderInfoChuky1;
    private ArrayList<OrderInfo> arrOrderInfoChuky2;

    private ArrayList<String> lstFilePath = new ArrayList<String>();
    private ArrayList<FileObj> arrFileObjZiped = null;// list da zip

    private Map<String, RecordTypeScanDTO> recordTypeScanDTOMap = new HashMap<>();
    private ArrayList<ProfileRecord> profileRecords;
    private HashMap<String, ArrayList<FileObj>> hashmapFileObj;

    public ExecuteOrderAsyncTask(Activity context,
                                 OnPostExecuteListener<ArrayList<SaleOutput>> listener,
                                 View.OnClickListener moveLogInAct,
                                 Map<String, RecordTypeScanDTO> recordTypeScanDTOMap,
                                 HashMap<String, ArrayList<FileObj>> hashmapFileObj,
                                 FileObj fileSignObjOne,
                                 FileObj fileSignObjTwo,
                                 List<ProfileRecord> profileRecords,
                                 ArrayList<OrderInfo> arrOrderInfoChuky1,
                                 ArrayList<OrderInfo> arrOrderInfoChuky2) {

        super(context, listener, moveLogInAct);

        this.fileSignObjOne = fileSignObjOne;
        this.fileSignObjTwo = fileSignObjTwo;
        this.arrOrderInfoChuky1 = arrOrderInfoChuky1;
        this.arrOrderInfoChuky2 = arrOrderInfoChuky2;
        this.profileRecords = new ArrayList<>(profileRecords);

        this.hashmapFileObj = new HashMap<>();
        // kiem tra neu co file status khac -1 thì put vao map day len
        // con neu = -1 thi ko cap nhat gi
        for (String key : hashmapFileObj.keySet()) {
            for (FileObj fileObj : hashmapFileObj.get(key)) {
                if (fileObj.getStatus() != -1) {
                    this.hashmapFileObj.put(key, hashmapFileObj.get(key));
                    break;
                }
            }
        }

        for (String keyId : recordTypeScanDTOMap.keySet()) {
            if (CommonActivity.isNullOrEmpty(this.hashmapFileObj.get(keyId))) {
                this.recordTypeScanDTOMap.put(keyId, recordTypeScanDTOMap.get(keyId));
            }
        }
    }

    @Override
    protected ArrayList<SaleOutput> doInBackground(String... params) {
        if (hashmapFileObj != null && hashmapFileObj.size() > 0) {
            File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
            if (!folder.exists()) {
                folder.mkdir();
            }
            Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());
            for (Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj.entrySet()) {
                ArrayList<FileObj> listFileObjs = entry.getValue();
                String zipFilePath = "";
                if (listFileObjs.size() > 1) {
                    String spinnerCode = "";
                    for (FileObj fileObj : listFileObjs) {
                        spinnerCode = fileObj.getCodeSpinner();
                    }
                    zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                            + spinnerCode + ".zip";
                    lstFilePath.add(zipFilePath);
                } else if (listFileObjs.size() == 1) {
                    zipFilePath = folder.getPath() + File.separator + System.currentTimeMillis() + "_"
                            + listFileObjs.get(0).getCodeSpinner() + ".jpg";
                    lstFilePath.add(zipFilePath);
                }
            }
        }

        try {
            arrFileObjZiped = FileUtils.getArrFileBackUp2(mActivity, hashmapFileObj, lstFilePath);
            if (!CommonActivity.isNullOrEmpty(fileSignObjOne)) {
                arrFileObjZiped.add(fileSignObjOne);
            }
            if (!CommonActivity.isNullOrEmpty(fileSignObjTwo)) {
                arrFileObjZiped.add(fileSignObjTwo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeOrder();
    }

    private ArrayList<SaleOutput> executeOrder() {
        String original = "";
        ParseOuput out = null;
        String func = "executeMultiOrder";
        mergeProfile();
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            if (!CommonActivity.isNullOrEmpty(arrFileObjZiped)) {
                for (FileObj fileObj : arrFileObjZiped) {
                    rawData.append("<profileRecords>");
                    if (!CommonActivity.isNullOrEmpty(fileObj.getCodeSpinner())) {
                        rawData.append("<code>").append(fileObj.getCodeSpinner()).append("</code>");
                    }

                    if (!CommonActivity.isNullOrEmpty(fileObj.getPath()) && fileObj.getStatus() != -1) {
                        File fileCompress = new File(fileObj.getPath());
                        byte[] buffer = FileUtils.fileToBytes(fileCompress);
                        String base64 = Base64.encodeToString(buffer, Activity.TRIM_MEMORY_BACKGROUND);
                        rawData.append("<content>").append(base64).append("</content>");
                    }

                    // set
                    if (!CommonActivity.isNullOrEmpty(fileObj.getUrl())) {
                        rawData.append("<url>").append(fileObj.getUrl()).append("</url>");
                    }

                    if (Constant.PROFILE.CHUKY.equals(fileObj.getCodeSpinner())
                            || Constant.PROFILE.CHUKY2.equals(fileObj.getCodeSpinner())) {
                        rawData.append("<fileExtension>").append(Constant.IMG_EXT_PNG).append("</fileExtension>");
                        rawData.append("<type>").append("CHUKY").append("</type>");
                    } else {
                        if (fileObj.isZip()) {
                            rawData.append("<fileExtension>").append(Constant.ZIP).append("</fileExtension>");
                        } else {
                            rawData.append("<fileExtension>").append(Constant.IMG_EXT_JPG).append("</fileExtension>");
                        }
                    }

                    if(Constant.PROFILE.CHUKY.equals(fileObj.getCodeSpinner())){
                        for(OrderInfo orderInfo: arrOrderInfoChuky1){
                            rawData.append("<lstOrderInfo>");

                            if (!CommonActivity.isNullOrEmpty(orderInfo.getOrderId()))
                                rawData.append("<orderId>").append(orderInfo.getOrderId()).append("</orderId>");
                            if (!CommonActivity.isNullOrEmpty(orderInfo.getTaskId()))
                                rawData.append("<taskId>").append(orderInfo.getTaskId()).append("</taskId>");

                            rawData.append("</lstOrderInfo>");
                        }
                    }else{
                        if(Constant.PROFILE.CHUKY2.equals(fileObj.getCodeSpinner())){
                            for(OrderInfo orderInfo: arrOrderInfoChuky2){
                                rawData.append("<lstOrderInfo>");
                                if (!CommonActivity.isNullOrEmpty(orderInfo.getOrderId()))
                                    rawData.append("<orderId>").append(orderInfo.getOrderId()).append("</orderId>");
                                if (!CommonActivity.isNullOrEmpty(orderInfo.getTaskId()))
                                    rawData.append("<taskId>").append(orderInfo.getTaskId()).append("</taskId>");
                                rawData.append("</lstOrderInfo>");
                            }
                        }else{
                            for(OrderInfo orderInfo: fileObj.getArrOrderInfo()){
                                rawData.append("<lstOrderInfo>");
                                if (!CommonActivity.isNullOrEmpty(orderInfo.getOrderId()))
                                    rawData.append("<orderId>").append(orderInfo.getOrderId()).append("</orderId>");
                                if (!CommonActivity.isNullOrEmpty(orderInfo.getTaskId()))
                                    rawData.append("<taskId>").append(orderInfo.getTaskId()).append("</taskId>");
                                rawData.append("</lstOrderInfo>");
                            }
                        }
                    }
                    rawData.append("</profileRecords>");
                }
            }

            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_" + func);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i(" Original", response);

            // parser
            Serializer serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);

        } catch (Exception e) {
            Log.e(Constant.TAG, "blockSubForTerminate", e);
            out = new ParseOuput();
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
            out.setErrorCode(errorCode);
            out.setDescription(description);
        }
        if (CommonActivity.isNullOrEmpty(out)) {
            out = new ParseOuput();
            description = mActivity
                    .getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            out.setErrorCode(errorCode);
            out.setDescription(description);
            out.setTaskId("");
        } else {
            description = out.getDescription();
            errorCode = out.getErrorCode();
        }
        return out.getLstSaleOutPut();
    }

    private void mergeProfile() {
        FileObj fileObj;
        for (ProfileRecord profileRecord : profileRecords) {
            if (!isExistFileObj(profileRecord)) {
                fileObj = new FileObj();
                fileObj.setUrl(profileRecord.getUrl());
                fileObj.setCodeSpinner(profileRecord.getCode());
                fileObj.getArrOrderInfo().addAll(profileRecord.getArrOrderInfo());
                arrFileObjZiped.add(fileObj);
            }
        }
    }

    private boolean isExistFileObj(ProfileRecord profileRecord) {
        for (FileObj fileObj : arrFileObjZiped) {
            if (profileRecord.getCode().equals(fileObj.getCodeSpinner())) {
                return true;
            }
        }
        return false;
    }
}