package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v3.connecttionService.activity.TabThongTinHopDong;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.customer.object.FormBean;
import com.viettel.bss.viettelpos.v4.customer.object.ProfileUpload;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customer.object.UploadImageInput;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.savelog.SaveLog;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ConnectSubMultiAsyn extends AsyncTask<String, String, SaleOutput> {
    private ProgressDialog progress;
    private Activity context = null;
    private ResultSurveyOnlineForBccs2Form surveyOnline;
    private List<SubscriberDTO> lstSub;
    private AreaObj area;
    private Date timeEnd = null;
    private View btnConnectSub;
    private String oldSubId;
    private String actionCode;
    private String omniProcessId;

    public ConnectSubMultiAsyn(Activity context, List<SubscriberDTO> lstSub,
                               ResultSurveyOnlineForBccs2Form surveyOnline, AreaObj area, View btnConnectSub, String omniProcessId) {
        this.omniProcessId = omniProcessId;
        this.lstSub = lstSub;
        this.area = area;
        this.context = context;
        this.surveyOnline = surveyOnline;
        this.progress = new ProgressDialog(this.context);
        // check font
        this.progress.setCancelable(false);
        this.progress.setMessage(context.getString(R.string.connecting_sub,
                lstSub.get(0).getIsdn()));
        if (!this.progress.isShowing()) {
            this.progress.show();
        }
        this.btnConnectSub = btnConnectSub;
    }

    public ConnectSubMultiAsyn(Activity context, List<SubscriberDTO> lstSub,
                               ResultSurveyOnlineForBccs2Form surveyOnline, AreaObj area, View btnConnectSub, String oldSubId, String actionCode) {
        this.lstSub = lstSub;
        this.area = area;
        this.context = context;
        this.surveyOnline = surveyOnline;
        this.progress = new ProgressDialog(this.context);
        // check font
        this.progress.setCancelable(false);
        this.progress.setMessage(context.getString(R.string.connecting_sub,
                lstSub.get(0).getIsdn()));
        if (!this.progress.isShowing()) {
            this.progress.show();
        }
        this.btnConnectSub = btnConnectSub;
        this.oldSubId = oldSubId;
        this.actionCode = actionCode;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        this.progress.setMessage(context.getString(R.string.connecting_sub,
                values[0]));
        if(!CommonActivity.isNullOrEmpty(oldSubId) && !CommonActivity.isNullOrEmpty(actionCode)){
            this.progress.setMessage(context.getString(R.string.change_tech_sub,
                    values[0]));
        }
    }

    @Override
    protected SaleOutput doInBackground(String... params) {
        publishProgress(lstSub.get(0).getIsdn());
        for (SubscriberDTO sub : lstSub) {
            // Tao ten file
            File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
            if (!folder.exists()) {
                folder.mkdir();
            }
            Log.d("Log", "Folder zip file create: " + folder.getAbsolutePath());

            // Neu co chon ho so thi se dinh kem
            for (ProfileUpload profile : sub.getLstProfile()) {
//                if (CommonActivity.isNullOrEmpty(profile.getParentId())) {
                    String ext = "";
                    if (profile.getLstFile().size() == 1) {
                        ext = ".jpg";
                    } else {
                        ext = ".zip";
                    }
                    String zipFilePath = folder.getPath() + File.separator
                            + (new Date().getTime()) + "_"
//                            + (new Date().getTime() + index++) + "_"
                            + profile.getLstProfile().get(0).getCode() + ext;
                    profile.setZipFilePath(zipFilePath);
                }
//            }
        }
        // lay ra thue bao chinh
        SubscriberDTO subMain = lstSub.get(0);
        // set lai list thue bao dau kem theo
        List<SubscriberDTO> lstExtraService = new ArrayList<>();
        if (!CommonActivity.isNullOrEmptyArray(lstSub)) {
            for (SubscriberDTO item : lstSub) {
                if (subMain.getSubId() != item.getSubId()) {
                    lstExtraService.add(item);
                }
            }
        }
        // set lai lstExternal
        if (!CommonActivity.isNullOrEmptyArray(lstExtraService)) {
            subMain.setListExtraServices(lstExtraService);
        }

        SaleOutput out = doConnectSub(subMain);
        return out;
    }

    @Override
    protected void onPostExecute(SaleOutput result) {
        progress.dismiss();
        if (result == null) {
            CommonActivity.createAlertDialog(
                    context,
                    context.getString(R.string.no_return_from_system),
                    context.getString(R.string.app_name)).show();
            return;
        }
//        result.setErrorCode("0");
//        result.setLstSubSuccess(lstSub);
        // thanh cong thi xu ly cho nay
        if ("0".equals(result.getErrorCode())) {
            // lay ra listsub thanh cong
            List<SubscriberDTO> lstSubSuccess = new ArrayList<>();
            if (!CommonActivity.isNullOrEmptyArray(result.getLstSubSuccess())) {
                lstSubSuccess.addAll(result.getLstSubSuccess());
                for (SubscriberDTO subSuccess : lstSubSuccess) {
                    for (SubscriberDTO subSource : lstSub) {
                        if (subSource.getSubId().equals(subSuccess.getSubId())) {
                            subSuccess.setLstProfile(subSource.getLstProfile());
                            subSuccess.setHthm(subSource.getHthm());
                            break;
                        }
                    }
                }
            }

            // danh dau day file ho so
            Long subIdEnd = lstSubSuccess.get(0).getSubId();
            for (SubscriberDTO sub : lstSubSuccess) {
                subIdEnd = sub.getSubId();
            }

            boolean exitsProfile = false;
            for (SubscriberDTO sub : lstSubSuccess) {
                btnConnectSub.setVisibility(View.GONE);
                boolean isEnd = sub.getSubId().equals(subIdEnd);
                if (!CommonActivity.isNullOrEmpty(sub.getLstProfile())) {
                    exitsProfile = true;
                    AsynZipFile zip = new AsynZipFile(context, sub, isEnd, result.getDescription());
                    zip.execute();
                }

                //truong hop khong ho so nao co hop dong de tai len thi thong bao
                if(!exitsProfile && (subIdEnd == sub.getSubId())){
                    CommonActivity.createAlertDialog(context, result.getDescription(), context.getString(R.string.app_name),onClickListenerFinish).show();
                }
            }
        } else {

            if (Constant.INVALID_TOKEN.equals(result.getErrorCode())) {
                LoginDialog dialog = new LoginDialog(
                        context,
                        ";cm.connect_sub_bccs2;");
                dialog.show();
            } else {
                String description = result.getDescription();
                if (description == null || description.isEmpty()) {
                    description = context.getString(R.string.checkdes);
                }
                Dialog dialog = CommonActivity.createAlertDialog(
                        context, description,
                        context.getResources().getString(R.string.app_name));
                dialog.show();

            }

        }
    }

    // =====method get list service ========================
    public SaleOutput doConnectSub(SubscriberDTO sub) {
        String original = null;
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_doConnectSub");
            StringBuilder rawData = new StringBuilder();

            rawData.append("<ws:doConnectSub>");
            rawData.append("<input>");
            HashMap<String, String> paramToken = new HashMap<String, String>();
            paramToken.put("token", Session.getToken());
            rawData.append(input.buildXML(paramToken));

            rawData.append("<subscriberDTOExtend>");

            if (!CommonActivity.isNullOrEmpty(omniProcessId)) {
//                rawData.append("<omniProcessId>").append(omniProcessId).append("</omniProcessId>");
                sub.setOmniProcessId(omniProcessId);
            }

            rawData.append("<subId>")
                    .append(sub.getSubId())
                    .append("</subId>");


            rawData.append(sub.buildSubXml(context,
                    "subscriberDTO", surveyOnline,
                    TabThongTinHopDong.accountDTOMain,
                    TabThueBaoHopDongManager.instance.custIdentityDTO, area, oldSubId, actionCode).replace(">null<", "><").replaceAll(">nullnullnullnull<","><"));

            if (!CommonActivity.isNullOrEmpty(sub) && !CommonActivity.isNullOrEmptyArray(sub.getListExtraServices())) {
                for (SubscriberDTO subExtra : sub.getListExtraServices()) {
                    rawData.append("<listExtraServicesExtends>");
                    if (!CommonActivity.isNullOrEmpty(omniProcessId)) {
                        rawData.append("<omniProcessId>").append(omniProcessId).append("</omniProcessId>");
                    }
                    rawData.append("<subId>")
                            .append(subExtra.getSubId())
                            .append("</subId>");
                    rawData.append(subExtra.buildSubXml(context,
                            "subscriberDTO", surveyOnline,
                            TabThongTinHopDong.accountDTOMain,
                            TabThueBaoHopDongManager.instance.custIdentityDTO, area, oldSubId, actionCode).replace(">null<", "><").replaceAll(">nullnullnullnull<","><"));
                    // day file ho so len tai day lstFilePath lstRecordName lstRecordCode
                    if (!CommonActivity.isNullOrEmpty(subExtra.getLstProfile())) {
                        for (ProfileUpload item : subExtra.getLstProfile()) {
                            rawData.append("<lstFilePath>")
                                    .append(item.getZipFilePath())
                                    .append("</lstFilePath>");
                            rawData.append("<lstRecordCode>")
                                    .append(item.getLstProfile().get(0).getCode())
                                    .append("</lstRecordCode>");
                            rawData.append("<lstRecordName>")
                                    .append(item.getLstProfile().get(0).getName())
                                    .append("</lstRecordName>");
                        }
                    }
                    rawData.append("</listExtraServicesExtends>");
                }
            }

            // day file ho so len tai day lstFilePath lstRecordName lstRecordCode
            if (!CommonActivity.isNullOrEmpty(sub.getLstProfile())) {
                for (ProfileUpload item : sub.getLstProfile()) {
                    rawData.append("<lstFilePath>")
                            .append(item.getZipFilePath())
                            .append("</lstFilePath>");
                    rawData.append("<lstRecordCode>")
                            .append(item.getLstProfile().get(0).getCode())
                            .append("</lstRecordCode>");
                    rawData.append("<lstRecordName>")
                            .append(item.getLstProfile().get(0).getName())
                            .append("</lstRecordName>");
                }
            }
            rawData.append("</subscriberDTOExtend>");
            rawData.append("</input>");
            rawData.append("</ws:doConnectSub>");

            Log.i("RowData", rawData.toString());

            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequestConnectSub(envelope, Constant.BCCS_GW_URL,
                    context, "mbccs_doConnectSub");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Responseeeeeeeeee", original);

            Serializer serializer = new Persister();
            SaleOutput parseOuput = serializer.read(SaleOutput.class, original);
            if (parseOuput == null) {
                parseOuput = new SaleOutput();
                parseOuput.setDescription(context
                        .getString(R.string.no_return_from_system));
                parseOuput.setErrorCode(Constant.ERROR_CODE);
                return parseOuput;
            } else {
                return parseOuput;
            }

        } catch (Exception e) {
            Log.e("exception", "Exception", e);
            // errorMessage = getResources().getString(R.string.exception)
            // + e.toString();
            SaleOutput parseOuput = new SaleOutput();
            parseOuput.setErrorCode(Constant.ERROR_CODE);
            parseOuput.setDescription(context.getString(R.string.exception));
            return parseOuput;
        }
    }

    private class AsynZipFile extends AsyncTask<String, Void, Void> {

        private Context mContext;
        private ProgressDialog progress;
        private String errorCode = "";
        private String description = "";
        private SubscriberDTO sub;
        Date timeStartZipFile = new Date();
        private boolean isEndSub = false;
        private String mesenge;

        public AsynZipFile(Context context, SubscriberDTO sub, boolean isEndSub, String msg) {
            this.isEndSub = isEndSub;
            this.sub = sub;
            this.mContext = context;
            this.mesenge = msg;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setMessage(mContext.getString(R.string.zip_file,
                    sub.getIsdn()));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... arg0) {
            FileOutputStream out = null;
            try {
                File folder = new File(Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
                if (!folder.exists()) {
                    folder.mkdir();
                }
                for (ProfileUpload profile : sub.getLstProfile()) {
                    if (CommonActivity.isNullOrEmpty(profile.getLstFile())) {
                        continue;
                    }

                    List<File> lstFileSource = profile.getLstFile();
                    if (lstFileSource.size() > 1) {
                        List<File> lstFileDes = new ArrayList<File>();
                        for (File file : lstFileSource) {
                            // doan nay bi outoffmemoty
//							Bitmap bitmap = BitmapFactory.decodeFile(file
//									.getPath());
                            Bitmap bitmap = CommonActivity.decodeFile(file, Constant.SIZE_IMAGE_SCALE, Constant.SIZE_IMAGE_SCALE);

                            Bitmap bitmapImage = CommonActivity
                                    .getResizedBitmap(bitmap,
                                            Constant.SIZE_IMAGE_SCALE);
                            File fileDes = new File(folder.getPath()
                                    + File.separator + "_"
                                    + (new Date().getTime())
//                                    + (new Date().getTime() + index++)
                                    + profile.getLstProfile().get(0).getCode()
                                    + ".jpg");

                            out = new FileOutputStream(fileDes);
                            bitmapImage.compress(Bitmap.CompressFormat.PNG,
                                    100, out);
                            out.close();
                            lstFileDes.add(fileDes);
                        }
                        String zipFilePath = profile.getZipFilePath();
                        FileUtils.zip(lstFileDes, zipFilePath);

                        for (File file : lstFileDes) {
                            file.delete();
                        }

                    } else {
//						Bitmap bitmap = BitmapFactory.decodeFile(lstFileSource
//								.get(0).getPath());
                        Bitmap bitmap = CommonActivity.decodeFile(lstFileSource
                                .get(0), Constant.SIZE_IMAGE_SCALE, Constant.SIZE_IMAGE_SCALE);

                        Bitmap bitmapImage = CommonActivity.getResizedBitmap(
                                bitmap, Constant.SIZE_IMAGE_SCALE);
                        File fileDes = new File(profile.getZipFilePath());
                        out = new FileOutputStream(fileDes);
                        bitmapImage.compress(Bitmap.CompressFormat.PNG, 100,
                                out);
                        // file path cho nay dang check so null
                        profile.setZipFilePath(fileDes.getPath());
                        out.close();
                    }

                }
                errorCode = "0";
            } catch (Exception e) {
                errorCode = "1";
                description = "Error when zip file: " + e.toString();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.dismiss();
            if ("0".equals(errorCode)) {
                try {
                    SaveLog saveLog = new SaveLog(mContext,
                            Constant.SYSTEM_NAME, Session.userName,
                            "mbccs_connectSub_zipFile", CommonActivity
                            .findMyLocation(mContext).getX(),
                            CommonActivity.findMyLocation(mContext).getY());
                    timeEnd = new Date();
                    saveLog.saveLogRequest(
                            errorCode,
                            timeStartZipFile,
                            timeEnd,
                            Session.userName + "_"
                                    + CommonActivity.getDeviceId(mContext)
                                    + "_" + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                UploadFile up = new UploadFile(context, sub, isEndSub, mesenge);
                up.execute();

            } else {
                CommonActivity.createAlertDialog(context, description,
                        context.getString(R.string.app_name)).show();
            }

        }
    }

    private class UploadFile extends AsyncTask<String, Void, ParseOuput> {

        private Context mContext;
        private ProgressDialog progress;
        private String errorCode = "";
        private SubscriberDTO sub;
        private Boolean isEnd = false;
        Date timeStartZipFile = new Date();
        XmlDomParse parse = new XmlDomParse();
        private String message;

        public UploadFile(Context context, SubscriberDTO sub, Boolean isEnd, String mes) {
            this.message = mes;
            this.isEnd = isEnd;
            this.sub = sub;
            this.mContext = context;
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setMessage(mContext.getString(
                    R.string.uploading_profile, sub.getIsdn()));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ParseOuput doInBackground(String... arg0) {
            FileOutputStream out = null;
            try {
                ArrayList<FileObj> arrFileObj = new ArrayList<>();
                for (ProfileUpload profile : sub.getLstProfile()) {
                    if (CommonActivity.isNullOrEmpty(profile.getLstFile())) {
                        break;
                    }
                    FileObj file = new FileObj();
                    file.setIsdn(sub.getIsdn());
                    file.setActionAudit(sub.getActionAuditId());
                    file.setActionCode("00");
                    file.setCodeSpinner(profile.getLstProfile().get(0).getCode());
                    file.setFile(new File(profile.getZipFilePath()));
                    String pageIndex = "0";

                    if (profile.getZipFilePath().endsWith(".zip")) {
                        pageIndex = "1";
                    }
                    file.setPageIndex(pageIndex);
                    file.setName(profile.getLstProfile().get(0).getName());
                    file.setPath(profile.getZipFilePath());
                    file.setReasonId(sub.getHthm().getReasonId());
                    file.setRecodeName(profile.getLstProfile().get(0).getName());
                    file.setRecordTypeId(profile.getLstProfile().get(0).getTypeId()
                            + "");
                    byte[] buffer = FileUtils.fileToBytes(file.getFile());
                    if (buffer == null || buffer.length == 0) {
                        continue;
                    }
                    arrFileObj.add(file);
                }
                ParseOuput parseOuput;
                if (!CommonActivity.isNullOrEmptyArray(arrFileObj)) {
                    parseOuput = doUploadLstImage(arrFileObj);
                    if (parseOuput == null
                            || CommonActivity.isNullOrEmpty(parseOuput.getErrorCode())) {
                        parseOuput = new ParseOuput();
                        parseOuput.setDescription(context
                                .getString(R.string.no_return_from_system));
                        parseOuput.setErrorCode(Constant.ERROR_CODE);
                        for (FileObj file : arrFileObj) {
                            file.setStatus(0);
                            FileUtils.insertFileBackUpToDataBase(file, mContext);
                        }
                        return parseOuput;
                    } else {
                        if (!CommonActivity.isNullOrEmptyArray(parseOuput.getLstImageInputFail())) {
                            // truong hop thanh cong nhung van co 1 so file that bat
                            for (int i = 0; i < parseOuput.getLstImageInputFail().size(); i++) {
                                for (int j = 0; j < arrFileObj.size(); j++) {
                                    if (parseOuput.getLstImageInputFail().get(i).getRecordId()
                                            .equals(arrFileObj.get(j).getRecordId())) {
                                        arrFileObj.get(j).setStatus(0);
                                        FileUtils.insertFileBackUpToDataBase(arrFileObj.get(j), mContext);
                                        arrFileObj.remove(j);
                                        Log.d("MBCCS", "size arrFileObj.remove after remove : " +
                                                j + "");
                                        break;
                                    }
                                }
                            }
                            // cac truong hop con lai sau khi insert db thi xoa di
                            if (!CommonActivity.isNullOrEmptyArray(arrFileObj)) {
                                if(isEnd){
                                    for (FileObj file : arrFileObj) {
                                        File fileDelete = new File(file.getPath());
                                        fileDelete.delete();
                                    }
                                }

                            }
                        } else {
                            // truong hop thanh cong het thi delete all list
                            if(isEnd){
                                for (FileObj file : arrFileObj) {
                                    File fileDelete = new File(file.getPath());
                                    fileDelete.delete();
                                }
                            }

                        }
                        return parseOuput;
                    }
                }
            } catch (Exception e) {
                errorCode = "1";
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ParseOuput result) {
            progress.dismiss();
            if (result != null && "0".equals(result.getErrorCode())) {

                //	if (error.equals("0")) {
//						file.getFile().delete();
//					} else {
//						file.setStatus(0);
//						FileUtils.insertFileBackUpToDataBase(file, mContext);
//					}


                try {
                    SaveLog saveLog = new SaveLog(mContext,
                            Constant.SYSTEM_NAME, Session.userName,
                            "mbccs_connectSub_zipFile", CommonActivity
                            .findMyLocation(mContext).getX(),
                            CommonActivity.findMyLocation(mContext).getY());

                    timeEnd = new Date();
                    saveLog.saveLogRequest(
                            errorCode,
                            timeStartZipFile,
                            timeEnd,
                            Session.userName + "_"
                                    + CommonActivity.getDeviceId(mContext)
                                    + "_" + System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (isEnd) {


                CommonActivity.createAlertDialog((Activity) mContext, message, mContext.getString(R.string.app_name),onClickListenerFinish).show();

//				String success = "";
//				String fail = "";
//				for (SubscriberDTO sub : lstSub) {
//					if ("0".equals(sub.getErrorCode())) {
//						success = success + sub.getIsdn() + ";";
//					} else {
//						fail = fail
//								+ context.getString(R.string.connect_sub_fail,
//										sub.getIsdn(), sub.getDescription())
//								+ "\n";
//					}
//				}
//				CommonActivity
//						.createErrorDialog(
//								context,
//								context.getString(R.string.connect_sub_success,
//										success)
//										+ fail
//										+ "\n"
//										+ context
//												.getString(R.string.profile_is_processing),
//								"1").show();
            }
        }

        private ParseOuput doUploadLstImage(List<FileObj> arrayFileObject) {
            ParseOuput parseOuput = null;
            String original = "";
            com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
                    .findMyLocationGPS(context, "uploadImageLstOffline");
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_uploadImageLstOffline");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:uploadImageLstOffline>");
                rawData.append("<input>");

                rawData.append("<token>" + Session.token + "</token>");
                if (myLocation != null && !myLocation.getX().equals("0")
                        && !myLocation.getY().equals("0")) {
                    rawData.append("<xUpdate>" + myLocation.getX());
                    rawData.append("</xUpdate>");
                    rawData.append("<yUpdate>" + myLocation.getY());
                    rawData.append("</yUpdate>");
                }
                if (!CommonActivity.isNullOrEmptyArray(arrayFileObject)) {

                    for (FileObj fileObj : arrayFileObject) {

                        File isdnZip = new File(fileObj.getPath());
                        byte[] buffer = FileUtils.fileToBytes(isdnZip);
                        String base64 = Base64.encodeToString(buffer,
                                Activity.TRIM_MEMORY_BACKGROUND);

                        rawData.append("<lstUploadImage>");

                        rawData.append("<recordId>" + fileObj.getId()
                                + "</recordId>");

                        rawData.append("<fileContent>" + base64 + "</fileContent>");
                        rawData.append("<isdn>" + fileObj.getIsdn() + "</isdn>");
                        rawData.append("<recordCode>" + fileObj.getCodeSpinner()
                                + "</recordCode>");
                        rawData.append("<actionCode>" + fileObj.getActionCode()
                                + "</actionCode>");
                        rawData.append("<reasonId>" + fileObj.getReasonId()
                                + "</reasonId>");
                        rawData.append("<actionAudit>" + fileObj.getActionAudit()
                                + "</actionAudit>");
                        rawData.append("<pageIndex>" + fileObj.getPageIndex()
                                + "</pageIndex>");
                        rawData.append("</lstUploadImage>");
                    }

                }
                rawData.append("<type>" + "online" + "</type>");
                rawData.append("</input>");
                rawData.append("</ws:uploadImageLstOffline>");
                // Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                // Log.d("uploadImageOffline Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                        context, "mbccs_uploadImageLstOffline");
                // Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();

                // parser
                Serializer serializer = new Persister();
                parseOuput = serializer.read(ParseOuput.class, original);
                if (parseOuput == null
                        || CommonActivity.isNullOrEmpty(parseOuput.getErrorCode())) {
                    parseOuput = new ParseOuput();
                    parseOuput.setDescription(context
                            .getString(R.string.no_return_from_system));
                    parseOuput.setErrorCode(Constant.ERROR_CODE);
                    return parseOuput;
                } else {
                    return parseOuput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "AsyncTaskUploadImageOffline doUploadImage "
                        + e.toString(), e);
                parseOuput = new ParseOuput();
                parseOuput.setDescription(e.getMessage());
                parseOuput.setErrorCode(Constant.ERROR_CODE);

            }
            return parseOuput;
        }
    }

    View.OnClickListener onClickListenerFinish = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance)){
                TabThueBaoHopDongManager.instance.setResult(Activity.RESULT_OK);
                TabThueBaoHopDongManager.instance.finish();
            }else{
                context.setResult(Activity.RESULT_OK);
                context.onBackPressed();
            }

        }
    };

}
