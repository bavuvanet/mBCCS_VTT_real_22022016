package com.viettel.bss.viettelpos.v4.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentRegisterServiceVas;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.UpdateModifyProfileAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.task.AsynTaskDownloadFile;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.savelog.SaveLog;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Toancx on 6/12/2017.
 */
public class UpdateProfileFragment extends FragmentCommon {

    @BindView(R.id.lvdetail)
    ListView lvdetail;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;


    ActionProfileBean actionProfileBean;
    List<RecordBean> lstRecordBeans;
    UpdateModifyProfileAdapter adapter;
    RecordBean recordBean;
    private final HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.layout_update_modfiy_profile;
    }

    @Override
    protected void unit(View v) {
        Bundle bundle = getArguments();
        lstRecordBeans = (List<RecordBean>) bundle.getSerializable("lstRecordBeans");
        actionProfileBean = (ActionProfileBean) bundle.getSerializable("actionProfileBean");
        adapter = new UpdateModifyProfileAdapter(getActivity(), lstRecordBeans);
        lvdetail.setAdapter(adapter);
        setOnClickDowload();
        setOnClickSelectImage();
    }

    @Override
    protected void setPermission() {

    }

    private void setOnClickDowload() {
        if (adapter != null) {
            adapter
                    .setOnClickDownload(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.tvDownloadImage:
                                    int pos = (Integer) v.getTag();
                                    recordBean = lstRecordBeans.get(pos);
                                    if (CommonActivity
                                            .isNullOrEmpty(recordBean
                                                    .getFilePath())) {
                                        new AsynTaskDownloadFile(getActivity(), new OnPostExecuteListener<String[]>() {
                                            @Override
                                            public void onPostExecute(String[] result, String errorCode, String description) {
                                                try{
                                                    byte[] fileByte = Base64.decode(result[0], Base64.DEFAULT);
                                                    recordBean.setFileName(result[1]);

                                                    File file = new File(
                                                            Environment.getExternalStorageDirectory(),
                                                            recordBean.getFileName());
                                                    FileOutputStream fos = new FileOutputStream(
                                                            file.getPath());
                                                    fos.write(fileByte);
                                                    fos.flush();
                                                    fos.close();
                                                    String filePath = file.getPath();
                                                    recordBean.setFilePath(filePath);
                                                }catch (Exception ex){
                                                    Log.d(Constant.TAG, "AsynTaskDownloadFile", ex);
                                                }

                                            }
                                        }, moveLogInAct, recordBean).execute();
                                    } else {
//                                        openFile(recordBeanCurrent.getFilePath());
                                        FileUtils.openFile(getActivity(), recordBean.getFilePath());
                                    }
                                    break;

                                default:
                                    break;
                            }

                        }
                    });
        }
    }

    private void setOnClickSelectImage() {
        if (adapter != null) {
            adapter.setOnClickSelectImage(imageListenner);
        }
    }

    private final View.OnClickListener imageListenner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(Constant.TAG, "view.getId() : " + view.getId());
            ImagePreviewActivity.pickImage(getActivity(), hashmapFileObj,
                    view.getId());
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG 9", "UpdateProfileFragment onActivityResult requestCode : "
                + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");

                    Parcelable[] parcelableUris = data
                            .getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                    if (parcelableUris == null) {
                        return;
                    }
                    // Java doesn't allow array casting, this is a little hack
                    Uri[] uris = new Uri[parcelableUris.length];
                    System.arraycopy(parcelableUris, 0, uris, 0,
                            parcelableUris.length);

                    int imageId = data.getExtras().getInt("imageId", -1);

                    Log.d(Constant.TAG,
                            "UpdateProfileFragment onActivityResult() imageId: "
                                    + imageId);

                    if (uris != null && uris.length > 0 && imageId >= 0) {
                        com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.UpdateModifyProfileAdapter.ViewHolder holder = null;
                        for (int i = 0; i < lvdetail.getChildCount(); i++) {
                            View rowView = lvdetail.getChildAt(i);
                            com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.UpdateModifyProfileAdapter.ViewHolder h = (com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.UpdateModifyProfileAdapter.ViewHolder) rowView
                                    .getTag();
                            if (h != null) {
                                int id = h.ibUploadImage.getId();
                                if (imageId == id) {
                                    holder = h;
                                    break;
                                }
                            }
                        }

                        if (holder != null) {
                            Picasso.with(getActivity())
                                    .load(new File(uris[0].toString()))
                                    .centerCrop().resize(100, 100)
                                    .into(holder.ibUploadImage);

                            RecordBean recordBean = getRecordBean((long) imageId);

                            String recordCode = recordBean.getRecordCode();

                            Log.i(Constant.TAG, "imageId: " + imageId
                                    + " spinnerCode: " + recordCode + " uris: "
                                    + uris.length);

                            ArrayList<FileObj> fileObjs = new ArrayList<>();
                            for (int i = 0; i < uris.length; i++) {
                                File uriFile = new File(uris[i].getPath());
                                String fileNameServer = recordCode + "-" + (i + 1)
                                        + ".jpg";
                                FileObj obj = new FileObj(recordCode, uriFile,
                                        uris[i].getPath(), fileNameServer);
                                obj.setRecordId(recordBean.getRecordId());
                                fileObjs.add(obj);
                            }
                            hashmapFileObj.put(String.valueOf(imageId), fileObjs);
                        } else {
                            Log.d(Constant.TAG,
                                    "UpdateProfileFragment onActivityResult() holder NULL");
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * getRecordBean
     *
     * @param recordId
     * @return
     */
    private RecordBean getRecordBean(Long recordId) {
        for (RecordBean recordBean : lstRecordBeans) {
            if (recordBean.getRecordId().equals(recordId)) {
                return recordBean;
            }
        }
        return null;
    }

    @OnClick(R.id.btnUpdate)
    public void btnUpdateOnClick(){
        if (CommonActivity.isNetworkConnected(getActivity())) {
            if (validateUpdateModifyProfile()) {
                Dialog confirmDialog = CommonActivity.createDialog(
                        getActivity(),
                        actionProfileBean.getIsModify() ?
                                getResources().getString(
                                        R.string.modify_profile_confirm) :
                                getResources().getString(
                                        R.string.verify_profile_confirm)	,
                        getResources().getString(R.string.app_name_title),
                        getResources().getString(R.string.cancel),
                        getResources().getString(R.string.ok),
                        null, modifyProfileClick);
                confirmDialog.show();
            }
        } else {
            Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                    getResources().getString(R.string.errorNetwork),
                    getResources().getString(R.string.app_name));
            dialog.show();
        }
    }

    private boolean validateUpdateModifyProfile() {
        if (lstRecordBeans.size() != hashmapFileObj.size()) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.modify_profile_thieu_chung_tu),
                    getString(R.string.app_name)).show();
            return false;
        }
        return true;
    }

    private final View.OnClickListener modifyProfileClick = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            new ModifyProfileAsyn(getActivity(), hashmapFileObj).execute();
        }
    };

    private class ModifyProfileAsyn extends AsyncTask<String, Void, ArrayList<FileObj>> {

        private final HashMap<String, ArrayList<FileObj>> mHashMapFileObj;
        private final Context mContext;
        private ProgressDialog progress;
        private String errorCode = "";
        private String description = "";
        private final ArrayList<String> lstFilePath = new ArrayList<>();
        final Date timeStartZipFile = new Date();

        public ModifyProfileAsyn(Context context,
                                 HashMap<String, ArrayList<FileObj>> hasMapFile) {
            this.mContext = context;
            this.mHashMapFileObj = hasMapFile;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress = new ProgressDialog(mContext);
            // check font
            this.progress.setMessage(mContext.getResources().getString(
                    R.string.processModifyProfile));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<FileObj> doInBackground(String... arg0) {
            ArrayList<FileObj> arrFileBackUp1 = null;
            try {
                if (mHashMapFileObj != null && mHashMapFileObj.size() > 0) {
                    File folder = new File(
                            Constant.MBCCS_TEMP_FOLDER_IMG_PROFILE);
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    Log.d("Log",
                            "Folder zip file create: "
                                    + folder.getAbsolutePath());
                    for (java.util.Map.Entry<String, ArrayList<FileObj>> entry : hashmapFileObj
                            .entrySet()) {
                        ArrayList<FileObj> listFileObjs = entry.getValue();
                        String zipFilePath = "";
                        if (listFileObjs.size() > 1) {
                            String spinnerCode = "";
                            for (FileObj fileObj : listFileObjs) {
                                spinnerCode = fileObj.getCodeSpinner();
                            }
                            zipFilePath = folder.getPath()
                                    + File.separator
                                    + "HS"
                                    + actionProfileBean
                                    .getActionProfileId() + "_"
                                    + actionProfileBean.getIsdnAccount()
                                    + "_" + spinnerCode + ".zip";
                            lstFilePath.add(zipFilePath);
                        } else if (listFileObjs.size() == 1) {
                            zipFilePath = folder.getPath()
                                    + File.separator
                                    + "HS"
                                    + actionProfileBean
                                    .getActionProfileId() + "_"
                                    + actionProfileBean.getIsdnAccount()
                                    + "_"
                                    + listFileObjs.get(0).getCodeSpinner()
                                    + ".jpg";
                            lstFilePath.add(zipFilePath);
                        }
                    }
                }

                arrFileBackUp1 = FileUtils.getArrFileBackUp2(mContext,
                        mHashMapFileObj, lstFilePath);
                errorCode = "0";
                return arrFileBackUp1;
            } catch (Exception e) {
                errorCode = "1";
                description = "Error when zip file: " + e.toString();
                return arrFileBackUp1;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<FileObj> result) {
            progress.dismiss();
            if ("0".equals(errorCode)) {
                try {
                    SaveLog saveLog = new SaveLog(mContext,
                            Constant.SYSTEM_NAME, Session.userName,
                            "mbccs_connectSub_zipFile", CommonActivity
                            .findMyLocation(mContext).getX(),
                            CommonActivity.findMyLocation(mContext).getY());

                    Date timeEnd = new Date();

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

                if (result != null && !result.isEmpty()) {
                    for (FileObj fileObj2 : result) {
                        fileObj2.setActionCode("MPHS");
                        fileObj2.setActionProfileId(String
                                .valueOf(actionProfileBean
                                        .getActionProfileId()));
                        fileObj2.setIsdn(actionProfileBean
                                .getIsdnAccount());
                        fileObj2.setPageSize(0 + "");
                    }
                    new AsyncTaskUpdateImageOfline(getActivity(),
                            result, onClick,
                            getString(R.string.modifyProfileSuccess) + "\n",
                            null, "10").execute();
                }
            } else {
                CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getString(R.string.app_name)).show();
            }

        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btnUpdate.setEnabled(false);
        }
    };

    private class AsyncTaskUpdateImageOfline extends
            AsyncTask<Void, Void, Integer> {

        private final Context mActivity;
        private final ArrayList<FileObj> listFileUploadImage;
        private final View.OnClickListener onClick;
        final XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        final ProgressDialog progress;
        final String isdn;
        final String dbType;
        Integer numberSuccess;
        final String noticeSucess;

        public AsyncTaskUpdateImageOfline(Context mActivity,
                                          ArrayList<FileObj> listFileUploadImage,
                                          View.OnClickListener onClick, String description, String mIsdn,
                                          String dbType) {
            this.mActivity = mActivity;
            this.listFileUploadImage = listFileUploadImage;
            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            if (!"10".equals(dbType)) {
                this.progress.setMessage(mActivity.getResources().getString(
                        R.string.progress_uploadImageOffline));
            } else {
                this.progress.setMessage(mActivity.getResources().getString(
                        R.string.progress_uploadChungTu));
            }
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
            this.description = description;
            this.noticeSucess = description;
            this.onClick = onClick;
            this.isdn = mIsdn;
            this.dbType = dbType;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return onProcessDataToUpload(listFileUploadImage);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            progress.dismiss();
            Log.d(Constant.TAG, "setNumberSuccess = " + result);

            setNumberSuccess(result);

            if (result == listFileUploadImage.size()) {

                if (!CommonActivity.isNullOrEmpty(noticeSucess)) {
                    description = noticeSucess;
                }
                Log.d("descriptionnnnnnnnnnnnnnnnnnnnnn", description);

                if (listFileUploadImage != null
                        && listFileUploadImage.size() > 0) {
                    for (FileObj fileObj : listFileUploadImage) {
                        File tmp = new File(fileObj.getPath());
                        tmp.delete();
                    }
                }

                if (!CommonActivity.isNullOrEmpty(isdn) && "2".equals(dbType)) {
                    CommonActivity
                            .createDialog(
                                    (Activity) mActivity,
                                    description
                                            + mActivity
                                            .getString(R.string.upload_all_profile_success)
                                            + "\n"
                                            + mActivity
                                            .getString(R.string.messagetichvas),
                                    mActivity.getString(R.string.app_name),
                                    mActivity.getString(R.string.OK),
                                    mActivity.getString(R.string.cancel),
                                    onclickIsdn, onClick).show();
                } else if ("10".equals(dbType)) {
                    CommonActivity.createAlertDialog((Activity) mActivity,
                            actionProfileBean.getIsModify() ? mActivity.getString(R.string.modifyProfileSuccess)
                                    : mActivity.getString(R.string.verifyProfileSuccess),
                            mActivity.getString(R.string.app_name), onClick)
                            .show();
                } else {
                    CommonActivity
                            .createAlertDialog(
                                    (Activity) mActivity,
                                    description
                                            + mActivity
                                            .getString(R.string.upload_all_profile_success),
                                    mActivity.getString(R.string.app_name),
                                    onClick).show();
                }

            } else {

                // if (listFileUploadImage != null && listFileUploadImage.size()
                // >
                // 0) {
                // for (FileObj fileObj : listFileUploadImage) {
                // File tmp = new File(fileObj.getPath());
                // tmp.delete();
                // }
                // }
                if (!CommonActivity.isNullOrEmpty(noticeSucess)) {
                    description = noticeSucess;
                }
                if (!CommonActivity.isNullOrEmpty(isdn) && "2".equals(dbType)) {
                    CommonActivity
                            .createDialog(
                                    (Activity) mActivity,
                                    description
                                            + mActivity
                                            .getString(R.string.upload_all_profile_success)
                                            + "\n"
                                            + mActivity
                                            .getString(R.string.messagetichvas),
                                    mActivity.getString(R.string.app_name),
                                    mActivity.getString(R.string.OK),
                                    mActivity.getString(R.string.cancel),
                                    onclickIsdn, onClick).show();
                } else if ("10".equals(dbType)) {

                    CommonActivity.createAlertDialog(
                            (Activity) mActivity,
                            actionProfileBean.getIsModify() ?
                                    mActivity.getString(
                                            R.string.modify_profile_fail_try_again,
                                            result, listFileUploadImage.size())
                                    : 	mActivity.getString(
                                    R.string.verify_profile_fail_try_again,
                                    result, listFileUploadImage.size()),
                            mActivity.getString(R.string.app_name), onClick)
                            .show();
                } else {
                    CommonActivity
                            .createAlertDialog(
                                    (Activity) mActivity,
                                    description
                                            + mActivity
                                            .getString(R.string.upload_profile_fail_try_again),
                                    mActivity.getString(R.string.app_name),
                                    onClick).show();
                }

            }
        }

        private int onProcessDataToUpload(ArrayList<FileObj> listFileUploadImage) {
            String messageUpload = "";
            int numberSuccess = 0;

            for (FileObj fileObj2 : listFileUploadImage) {
                try {
                    File isdnZip = new File(fileObj2.getPath());
                    byte[] buffer = FileUtils.fileToBytes(isdnZip);
                    if (buffer != null && buffer.length > 0) {
                        Log.d(Constant.TAG,
                                "FragmentCustomerUpdateCV onProcessDataToUpload service running fileToBytes"
                                        + buffer.length);
                        String base64 = Base64.encodeToString(buffer,
                                Activity.TRIM_MEMORY_BACKGROUND);
                        Log.d(Constant.TAG,
                                "FragmentCustomerUpdateCV onProcessDataToUpload service running encodeToString"
                                        + buffer.length);
                        Log.d(Constant.TAG,
                                "FragmentCustomerUpdateCV onProcessDataToUpload Bat dau tien trinh upload co file");
                        String error = doUploadImage(fileObj2, base64);
                        if (error.equals("0")) {
                            // messageUpload += mActivity
                            // .getString(R.string.message_update_cv_success)
                            // + " " + fileObj2.getCodeSpinner() + " \n";
                            fileObj2.setUploadStatus(1);
                            isdnZip.delete();
                            numberSuccess++;
                        } else {

                            if (!"10".equals(dbType)) {
                                fileObj2.setStatus(0);
                                FileUtils.insertFileBackUpToDataBase(fileObj2,
                                        mActivity.getApplicationContext());
                            } else {
                                isdnZip.delete();
                            }
                            // messageUpload += mActivity
                            // .getString(R.string.message_update_cv_fail)
                            // + " " + fileObj2.getCodeSpinner() + " " + " \n";
                        }
                    } else {
                        Log.d(Constant.TAG,
                                "FragmentCustomerUpdateCV onProcessDataToUpload khong co file");
                    }
                } catch (Exception e) {
                    // messageUpload += mActivity
                    // .getString(R.string.message_update_cv_fail)
                    // + " "
                    // + fileObj2.getCodeSpinner() + " " + " \n";
                }
            }

            description = messageUpload;
            return numberSuccess;
        }

        private String doUploadImage(FileObj fileObject, String fileContent) {
            Log.d("Log",
                    "doUploadImage upload image to server "
                            + fileObject.getPath() + " filecontent:  "
                            + fileContent.length() + " "
                            + fileObject.toString());

            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_uploadImageOffline");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:uploadImageOffline>");
                rawData.append("<input>");
                rawData.append("<token>").append(Session
                        .getToken()).append("</token>");
                rawData.append("<isdn>").append(fileObject.getIsdn()).append("</isdn>");
                rawData.append("<recordCode>").append(fileObject.getCodeSpinner()).append("</recordCode>");
                rawData.append("<actionCode>").append(fileObject.getActionCode()).append("</actionCode>");
                rawData.append("<reasonId>").append(fileObject.getReasonId()).append("</reasonId>");
                rawData.append("<actionAudit>").append(fileObject.getActionAudit()).append("</actionAudit>");
                rawData.append("<pageIndex>").append(fileObject.getPageIndex()).append("</pageIndex>");
                rawData.append("<status>").append(fileObject.getStatus()).append("</status>");
                rawData.append("<filePath>").append(fileObject.getPath()).append("</filePath>");
                rawData.append("<fileLength>").append(fileObject.getFileLength()).append("</fileLength>");
                rawData.append("<fileLengthOrigin>").append(fileObject.getFileLengthOrigin()).append("</fileLengthOrigin>");
                rawData.append("<fileContent>").append(fileContent).append("</fileContent>");
                rawData.append("<recordId>").append(fileObject.getRecordId()).append("</recordId>");
                rawData.append("<fileName>").append(fileObject.getFileName()).append("</fileName>");
                rawData.append("<actionProfileId>").append(fileObject.getActionProfileId()).append("</actionProfileId>");
                if(actionProfileBean.getIsModify()){
                    rawData.append("<typeProfile>")
                            .append("ACTION_PROFILE")
                            .append("</typeProfile>");
                } else {
                    rawData.append("<typeProfile>")
                            .append("ACTION_VERIFICATION")
                            .append("</typeProfile>");
                }

                rawData.append("</input>");
                rawData.append("</ws:uploadImageOffline>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("uploadImageOffline Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, mActivity,
                        "mbccs_uploadImageOffline", 10000, 30000);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("mBCCS", "uploadImageOffline Responseeeeeeeeee Original "+response);

                // parser
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d(Constant.TAG, errorCode);
                }
                return errorCode;
            } catch (Exception e) {
                Log.e("Log", "Upload fail ", e);
            }
            return "1";
        }

        final View.OnClickListener onclickIsdn = new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mActivity.getApplicationContext(),
                        FragmentRegisterServiceVas.class);
                intent.putExtra("isdnKey", isdn);
                mActivity.startActivity(intent);

            }
        };

        public Integer getNumberSuccess() {
            return numberSuccess;
        }

        public void setNumberSuccess(Integer numberSuccess) {
            this.numberSuccess = numberSuccess;
        }


    }
}
