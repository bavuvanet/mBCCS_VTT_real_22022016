package com.viettel.bss.viettelpos.v4.hsdt.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.dialog.DialogSelectProfileFragment;
import com.viettel.bss.viettelpos.v4.hsdt.adapter.ProfileOmniAdapter;
import com.viettel.bss.viettelpos.v4.hsdt.adapter.UploadDocumentAdapter;
import com.viettel.bss.viettelpos.v4.hsdt.asynctask.ExecuteOrderAsyncTask;
import com.viettel.bss.viettelpos.v4.hsdt.asynctask.PrintRequestOrderForSaleAsyncTask;
import com.viettel.bss.viettelpos.v4.hsdt.listener.OnFinishSignature;
import com.viettel.bss.viettelpos.v4.listener.OnImageClickListener;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.AsyncTaskGetImageOrder;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.UnclaimForReceptionistAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OrderInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.omichanel.dao.RecordTypeScanDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.UploadDocumentDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by hantt47 on 10/19/2017.
 */

public class ProfileInfoOmniFragment extends FragmentCommon {

    @BindView(R.id.recListProfile)
    RecyclerView recListProfile;
    @BindView(R.id.linSignatureContentTotal)
    LinearLayout linSignatureContentTotal;

    @BindView(R.id.linSignatureContentOne)
    LinearLayout linSignatureContentOne;
    @BindView(R.id.frlSignatureLayoutOne)
    FrameLayout frlSignatureLayoutOne;
    @BindView(R.id.imgShowSignatureOne)
    ImageView imgShowSignatureOne;
    @BindView(R.id.imgbtEditSignatureOne)
    ImageButton imgbtEditSignatureOne;

    @BindView(R.id.linSignatureContentTwo)
    LinearLayout linSignatureContentTwo;
    @BindView(R.id.frlSignatureLayoutTwo)
    FrameLayout frlSignatureLayoutTwo;
    @BindView(R.id.imgShowSignatureTwo)
    ImageView imgShowSignatureTwo;
    @BindView(R.id.imgbtEditSignatureTwo)
    ImageButton imgbtEditSignatureTwo;
    @BindView(R.id.recListDocument)
    RecyclerView recListDocument;

    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnAccept)
    Button btnAccept;

    private boolean isUseSignature;
    private boolean isSuccess;
    private int numberSig;
    private int imgId;
    private boolean isFullFile = false; //truong check ho so du
    private ProfileBO profileBO;
    private ProfileOmniAdapter profileOmniAdapter;
    private Bitmap bitmapSigCurrent;
    private Bitmap bitmapSigCurrentTwo;
    private FileObj fileObjSignOne;
    private FileObj fileObjSignTwo;

    private List<ProfileRecord> profileRecords = new ArrayList<>();
    private List<UploadDocumentDTO> uploadFiles = new ArrayList<>();

    private ArrayList<ConnectionOrder> arrConnectionOrder = null;
    private List<UploadDocumentDTO>  uploadFilesNotSign = new ArrayList<>();
    private List<UploadDocumentDTO> uploadFilesSign = new ArrayList<>();
    private ArrayList<OrderInfo> arrOrderInfoChuky1 = new ArrayList<>();
    private ArrayList<OrderInfo> arrOrderInfoChuky2 = new ArrayList<>();
    private ArrayList<ProfileRecord> arrProfileRecord = new ArrayList<>();

    OnImageClickListener imageListenner = new OnImageClickListener() {
        @Override
        public void onClick(RecordPrepaid item, Integer imageId) {

        }
        @Override
        public void onClickNew(RecordTypeScanDTO item, Integer imageId) {

            imgId = imageId;
            profileBO.getMapRecordTypeScanDTOSelected().put(String.valueOf(imgId), item);

            // check other file
            ArrayList<FileObj> fileObjArr = profileBO.getHashmapFileObj().get(imageId + "");
            if (!CommonActivity.isNullOrEmpty(fileObjArr)) {
                FileObj fileObj = fileObjArr.get(0);
                if (!Constant.IMG_EXT_JPG.equals(fileObj.getFileExtention().toLowerCase())
                        && !Constant.IMG_EXT_PNG.equals(fileObj.getFileExtention().toLowerCase())) {
                    showSelectOptionDialog(fileObj);
                    return;
                }
            }
            ImagePreviewActivity.pickImage(getActivity(), profileBO.getHashmapFileObj(), imgId);
        }
    };

    private OnFinishSignature onFinishSignature = new OnFinishSignature() {
        @Override
        public void onFinish(Bitmap bitmap, int index) {
            if (index == 1) {
                bitmapSigCurrent = bitmap;
                imgShowSignatureOne.setImageBitmap(bitmap);
                imgbtEditSignatureOne.setVisibility(View.GONE);
                //luu vao file co san
                fileObjSignOne = new FileObj();
                fileObjSignOne.setFileContent(CommonActivity
                        .getBase64String(bitmap, Constant.IMG_EXT_PNG));
                fileObjSignOne.setCodeSpinner(Constant.PROFILE.CHUKY);
                fileObjSignOne.setFileExtention(Constant.IMG_EXT_PNG);
                fileObjSignOne.setPath(savePathImageSig(bitmap));
            } else {
                bitmapSigCurrentTwo = bitmap;
                imgShowSignatureTwo.setImageBitmap(bitmap);
                imgbtEditSignatureTwo.setVisibility(View.GONE);
                //luu vao file co san
                fileObjSignTwo = new FileObj();
                fileObjSignTwo.setFileContent(CommonActivity
                        .getBase64String(bitmap, Constant.IMG_EXT_PNG));
                fileObjSignTwo.setCodeSpinner(Constant.PROFILE.CHUKY2);
                fileObjSignTwo.setFileExtention(Constant.IMG_EXT_PNG);
                fileObjSignTwo.setPath(savePathImageSig(bitmap));
            }
        }

        @Override
        public void onFinish(Bitmap bitmap) {
            // donothing
        }
    };

    private View.OnClickListener okClickSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // copy cac chung tu ky vao profilerecords de day len order
            // copyProfileSigToProfileRecords();

            ExecuteOrderAsyncTask executeOrderAsyncTask =
                    new ExecuteOrderAsyncTask(getActivity(), new OnPostExecuteListener<ArrayList<SaleOutput>>() {
                        @Override
                        public void onPostExecute(ArrayList<SaleOutput> result, String errorCode, String description) {
                            // check xem nhung thang order nao thanh cong
                            if(!CommonActivity.isNullOrEmptyArray(result)){
                                String orderSuccess = "";
                                String orderFail = "";
                                ArrayList<String> arrOrderFail = new ArrayList<>();
                                for(SaleOutput saleOutput: result){
                                    if("0".equals(saleOutput.getErrorCode())){
                                        orderSuccess += "; " + saleOutput.getOrderId();
                                    }else{
                                        //lay ra order va ly do that bai
                                        if(!CommonActivity.isNullOrEmpty(saleOutput.getDescription())
                                                && saleOutput.getDescription().contains("Exception")){
                                            saleOutput.setDescription(act.getString(R.string.fail_invalid));
                                        }
                                        arrOrderFail.add(saleOutput.getOrderId() + ": " + saleOutput.getDescription());
                                    }
                                }
                                orderSuccess = orderSuccess.replaceFirst(";", "");
                                for(String order: arrOrderFail){
                                    orderFail +=  "\n\t" + "- " + order;
                                }
                                String descriptionAll =  "";
                                if(!CommonActivity.isNullOrEmpty(orderSuccess)) {
                                    descriptionAll += getActivity().getString(R.string.order_success, orderSuccess);
                                }
                                if(!CommonActivity.isNullOrEmpty(orderFail)){
                                    descriptionAll += "\n" + getActivity().getString(R.string.order_fail, orderFail);
                                }
                                CommonActivity.showConfirmValidate(getActivity(), descriptionAll);
                                isSuccess = true;
                                btnAccept.setEnabled(false);
                            } else {
                                //khong co ket qua tra ve tu he thong
                                CommonActivity.showConfirmValidate(getActivity(), getActivity().getString(R.string.no_return_from_system));
                            }

//                            } else if (Constant.INVALID_TOKEN2.equals(errorCode)) {
//                                Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
//                                        description,
//                                        getActivity().getResources().getString(R.string.app_name),
//                                        moveLogInAct);
//                                dialog.show();
//                            } else {
//
//                                if (CommonActivity.isNullOrEmpty(description)) {
//                                    description = getActivity().getString(R.string.exception);
//                                }
//                                    CommonActivity.createAlertDialog(getActivity(), description, getActivity().getResources().getString(R.string.app_name)).show();
//
//                            }
                        }
                    }, moveLogInAct, profileBO.getMapRecordTypeScanDTOSelected(),
                            profileBO.getHashmapFileObj(), fileObjSignOne, fileObjSignTwo,
                            arrProfileRecord, arrOrderInfoChuky1, arrOrderInfoChuky2);

            executeOrderAsyncTask.execute();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(getString(R.string.profile_info));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.profile_info_fragment_omni;
    }

    @Override
    protected void unit(View v) {
        this.profileBO = new ProfileBO();
        this.profileBO.clearData();
        this.isSuccess = false;
        CommonActivity.deleteAllFileInDir(Constant.DIR_SAVE_PROFILE_PATH);
        Bundle bundle = getArguments();

        if (bundle != null) {
            arrConnectionOrder = (ArrayList<ConnectionOrder>) bundle.getSerializable("arrConnectionOrder");
            if (!CommonActivity.isNullOrEmpty(arrConnectionOrder)) {
                // init number sig
                initNumberSig();

                //ham for dung chung
                for (ConnectionOrder connectionOrder : arrConnectionOrder) {
                    // gop chung tu su dung ho so cu
					connectionOrder.mergeFileOldToUploadFiles();
                    // update Type cho profileRecords va remove chung tu khac chung tu duoc chon
                    connectionOrder.updateTypeForProfileRecords();

                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setIsdn(connectionOrder.getIsdn());
                    orderInfo.setOrderId(connectionOrder.getProcessInstanceId());
                    orderInfo.setTaskId(connectionOrder.getTaskId());
                    orderInfo.setOrderTypeDesc(connectionOrder.getOrderTypeDesc());

                    //set orderInfo vao ho so
                    for(UploadDocumentDTO uploadDocumentDTO: connectionOrder.getUploadFiles()){
                        for(RecordTypeScanDTO recordTypeScanDTO: uploadDocumentDTO.getListRecord()) {
                            recordTypeScanDTO.getArrOrderInfo().clear();
                            recordTypeScanDTO.getArrOrderInfo().add(orderInfo);
                        }
                    }

                    //set orderInfo vao ho so da duoc chon ben SALE va gop profileRecords
                    for(ProfileRecord profileRecord: connectionOrder.getProfileRecords()){
                        profileRecord.getArrOrderInfo().clear();
                        profileRecord.getArrOrderInfo().add(orderInfo);
                        addProfileRecords(profileRecord);
                    }

                    //map url anh da co vao ho so
                    for (UploadDocumentDTO uploadDocumentDTO : connectionOrder.getUploadFiles()) {
                        for (ProfileRecord profileRecord : connectionOrder.getProfileRecords()) {
                            if (profileRecord.getType().equals(uploadDocumentDTO.getSourceId() + "")) {
                                if (!CommonActivity.isNullOrEmpty(profileRecord.getUrl())) {
                                    uploadDocumentDTO.setUrlImage(profileRecord.getUrl());
                                    break;
                                }
                            }
                        }
                    }
                }

                // gop danh sach ho so tra ve
                uploadFiles = new ArrayList<>();
                for (ConnectionOrder connectionOrder : arrConnectionOrder) {
                    for (UploadDocumentDTO uploadDocumentDTO : connectionOrder.getUploadFiles()) {
                        uploadDocumentDTO.setProcessInstanceId(connectionOrder.getProcessInstanceId());
                        if ("1".equals(uploadDocumentDTO.getListRecord().get(0).getElectronicSign())) {
                            // neu la ky thi add luon
                            uploadFiles.add(uploadDocumentDTO);
                        } else {
                            addUploadDocument(uploadDocumentDTO);
                        }
                    }
                }

                // de hien thi anh da co trong adapter phai map record voi ho so
                for (ProfileRecord profileRecord : arrProfileRecord) {
                    if (CommonActivity.isNullOrEmpty(profileRecord.getCode())) {
                        continue;
                    }
                    for (UploadDocumentDTO uploadDocumentDTO : uploadFiles) {
                        for (RecordTypeScanDTO recordTypeScanDTO : uploadDocumentDTO.getListRecord()) {
                            if (recordTypeScanDTO.getRecordCode().equals(profileRecord.getCode())) {
                                // cap nhat full info cho record ko phai chung tu ky
                                if (!"1".equals(recordTypeScanDTO.getElectronicSign())) {
                                    profileRecord.setArrOrderInfo(recordTypeScanDTO.getArrOrderInfo());
                                }
                                recordTypeScanDTO.getProfileRecordMap().put(profileRecord.getCode(), profileRecord);
                            }
                        }
                    }
                }

                //chia danh sach ho so thanh 2 loai: co chu ky va khong co chu ky
                for (UploadDocumentDTO uploadDocumentDTO : uploadFiles) {
                    if ("1".equals(uploadDocumentDTO.getListRecord().get(0).getElectronicSign())) {
                        uploadFilesSign.add(uploadDocumentDTO);
                    } else {
                        uploadFilesNotSign.add(uploadDocumentDTO);
                    }
                }

                //save cac order co chu ky 1 vao list arrOrderInfoChuky1
                arrOrderInfoChuky1.add(uploadFilesSign.get(0).getListRecord().get(0).getArrOrderInfo().get(0));
                for(UploadDocumentDTO uploadDocumentDTO: uploadFilesSign){
                    //chung tu ky khong duoc gop, chi tuong ung voi 1 order
                    OrderInfo orderInfo = uploadDocumentDTO.getListRecord().get(0).getArrOrderInfo().get(0);
                    String orderId = orderInfo.getOrderId();
                    boolean isExistOrderInfo = false;
                    for(OrderInfo orderInfo1: arrOrderInfoChuky1){
                        if(orderInfo1.getOrderId().equals(orderId)){
                            isExistOrderInfo = true;
                        }
                    }
                    if(!isExistOrderInfo) {
                        arrOrderInfoChuky1.add(orderInfo);
                    }
                }

                //khoi tao spinner chon ho so
                initLvUploadImage();
                checkShowSignatue();
            }
        }

        recListProfile = (RecyclerView) v.findViewById(R.id.recListProfile);
        recListProfile.setHasFixedSize(true);
        recListProfile.setLayoutManager(new LinearLayoutManager(getActivity()));

        recListDocument.setHasFixedSize(true);
        recListDocument.setNestedScrollingEnabled(false);
        recListDocument.setLayoutManager(new LinearLayoutManager(getActivity()));

        frlSignatureLayoutOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignatureFragment(1);
            }
        });

        imgbtEditSignatureOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignatureFragment(1);
            }
        });

        frlSignatureLayoutTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignatureFragment(2);
            }
        });

        imgbtEditSignatureTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignatureFragment(2);
            }
        });

        btnCancel.setVisibility(View.GONE);
        btnAccept.setText(R.string.btn_apply);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSuccess) {
                    return;
                }
                isFullFile = validateSelectProfile();
                if (isFullFile) {
                    if (isUseSignature) {
                        //remove doi tuong xem
                        for (Map.Entry<String, ArrayList<FileObj>> entry : profileBO.getHashmapFileObj().entrySet()) {
                            ArrayList<FileObj> listRemove = new ArrayList<>();
                            for (FileObj fileObj : entry.getValue()) {
                                if ("1".equals(fileObj.getElectronicSign())) {
                                    listRemove.add(fileObj);
                                }
                            }
                            entry.getValue().removeAll(listRemove);
                        }
                    }

                    Dialog dialog = CommonActivity.createDialog(getActivity(),
                            getActivity().getString(R.string.confirm_submit),
                            getActivity().getString(R.string.checkin_order),
                            getActivity().getString(R.string.cancel),
                            getActivity().getString(R.string.ok)
                            , null, okClickSubmit);
                    dialog.show();
                }
            }
        });
    }

    private boolean validateSelectProfile() {
        //validate ho so bat buoc nhap
        RecordTypeScanDTO recordTypeScanDTOTemp;
        for (String imageId : profileBO.getMapRecordTypeScanDTOSelected().keySet()) {
            recordTypeScanDTOTemp = profileBO.getMapRecordTypeScanDTOSelected().get(imageId);
            if (1 == recordTypeScanDTOTemp.getReqScan()) {
                // bo check nhung thang dung ky
                if ("1".equals(recordTypeScanDTOTemp.getElectronicSign())) {
                    continue;
                }

                // bo check nhung thang co anh roi
                if (!CommonActivity.isNullOrEmpty(recordTypeScanDTOTemp
                        .getProfileRecordMap().get(recordTypeScanDTOTemp.getRecordCode()))) {
                    continue;
                }

                for (RecordTypeScanDTO e : profileBO.getMapListRecordTypeScanDTO().get(imageId)) {
                    if (e.isSelected() && e.getProfileRecordMap().size() > 0) continue;
                }
                if (!profileBO.getHashmapFileObj().containsKey(imageId)) {
                    CommonActivity.toast(getActivity(), R.string.checkthieuanhhoso);
                    return false;
                }
            }
        }

        //validate anh chu ky
        if (isUseSignature && CommonActivity.isNullOrEmpty(imgShowSignatureOne.getDrawable())) {
            CommonActivity.toast(getActivity(), R.string.confirm_sign);
            return false;
        }

        if (isUseSignature && "2".equals(this.numberSig)
                && CommonActivity.isNullOrEmpty(imgShowSignatureTwo.getDrawable())) {
            CommonActivity.toast(getActivity(), R.string.confirm_sign);
            return false;
        }

        return true;
    }

    private void showSignatureFragment(int index) {
        if (isSuccess) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("index", index + "");
        SignatureDialogFragment signatureDialogFragment;
        if (index == 1) {
            signatureDialogFragment = new SignatureDialogFragment(bitmapSigCurrent);
        } else {
            signatureDialogFragment = new SignatureDialogFragment(bitmapSigCurrentTwo);
        }
        signatureDialogFragment.setArguments(bundle);
        signatureDialogFragment.setOnFinishSignature(onFinishSignature);
        signatureDialogFragment.show(getFragmentManager(), "show sig");
    }

    private void initRecordSelected(List<ArrayList<RecordTypeScanDTO>> listOfArrRecord) {
        for (ArrayList<RecordTypeScanDTO> lstRecord : listOfArrRecord) {
            profileBO.getMapRecordTypeScanDTOSelected().put(
                    lstRecord.get(0).getSourceId() + "",
                    lstRecord.get(getPositionSelected(lstRecord)));
        }
    }

    private int getPositionSelected(final ArrayList<RecordTypeScanDTO> recordPrepaids) {
        for (int i = 0; i < recordPrepaids.size(); i++) {
            if (recordPrepaids.get(i).isSelected()) {
                return i;
            }
        }
        return 0;
    }

    private void initLvUploadImage() {
        List<ArrayList<RecordTypeScanDTO>> arrayOfArrayList = new ArrayList<>();
        List<ArrayList<RecordTypeScanDTO>> arrayOfArrayListNotSign = new ArrayList<>();

        for (UploadDocumentDTO uploadDocumentDTO : uploadFilesNotSign) {
            uploadDocumentDTO.proccessProfileRecordSelect();
            arrayOfArrayList.add(uploadDocumentDTO.getListRecord());
            arrayOfArrayListNotSign.add(uploadDocumentDTO.getListRecord());
        }

        for (UploadDocumentDTO uploadDocumentDTO : uploadFilesSign) {
            uploadDocumentDTO.proccessProfileRecordSelect();
            arrayOfArrayList.add(uploadDocumentDTO.getListRecord());
        }

        initRecordSelected(arrayOfArrayList);

        //khoi tao map trong profileBO
        for (int i = 0; i < uploadFilesNotSign.size(); i++) {
            profileBO.getMapListRecordTypeScanDTO().put(
                    String.valueOf(uploadFilesNotSign.get(i).getListRecord().get(0).getSourceId()),
                    uploadFilesNotSign.get(i).getListRecord());
        }
        for (int i = 0; i < uploadFilesSign.size(); i++) {
            profileBO.getMapListRecordTypeScanDTO().put(
                    String.valueOf(uploadFilesSign.get(i).getListRecord().get(0).getSourceId()),
                    uploadFilesSign.get(i).getListRecord());
        }

        // set adapter chung tu anh
        profileOmniAdapter = new ProfileOmniAdapter(getActivity(),
                arrayOfArrayListNotSign, imageListenner, this, moveLogInAct);
        recListProfile.setAdapter(profileOmniAdapter);

        // Set adapter chung tu ky
        UploadDocumentAdapter uploadDocumentAdapter =
                new UploadDocumentAdapter(getActivity(), uploadFilesSign, moveLogInAct);
        recListDocument.setAdapter(uploadDocumentAdapter);
    }

    @Override
    protected void setPermission() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    processImageProfileSelected(data);
                    break;
                default:
                    break;
            }
        }
    }

    private void processImageProfileSelected(Intent data) {
        Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");
        Parcelable[] parcelableUris = data.getParcelableArrayExtra(
                ImagePickerActivity.EXTRA_IMAGE_URIS);
        if (parcelableUris == null) {
            return;
        }
        Uri[] uris = new Uri[parcelableUris.length];
        System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);
        int imageId = data.getExtras().getInt("imageId", -1);
        if (uris != null && uris.length > 0 && imageId >= 0) {
            ProfileOmniAdapter.ViewHolder holder = null;
            for (int i = 0; i < recListProfile.getChildCount(); i++) {
                View rowView = recListProfile.getChildAt(i);
                ProfileOmniAdapter.ViewHolder h = (ProfileOmniAdapter.ViewHolder) rowView.getTag();
                if (h != null) {
                    int id = h.ibUploadImage.getId();
                    if (imageId == id) {
                        holder = h;
                        break;
                    }
                }
            }
            if (holder != null) {
                Picasso.with(getActivity()).load(new File(uris[0].getPath()))
                        .centerCrop().resize(holder.ibUploadImage.getWidth(), holder.ibUploadImage.getHeight())
                        .into(holder.ibUploadImage);
                int position = holder.spUploadImage.getSelectedItemPosition();
                if (position < 0) {
                    position = 0;
                }
                ArrayList<RecordTypeScanDTO> recordPrepaids =
                        profileBO.getMapListRecordTypeScanDTO().get(String.valueOf(imageId));

                if (recordPrepaids != null) {
                    RecordTypeScanDTO recordPrepaid = recordPrepaids.get(position);
                    recordPrepaid.setSelected(true);
                    for (RecordTypeScanDTO record : recordPrepaids) {
                        if (!record.getRecordCode().equals(recordPrepaid.getRecordCode())) {
                            record.setSelected(false);
                        }
                    }
                    String spinnerCode = recordPrepaid.getRecordCode();
                    ArrayList<FileObj> fileObjs = new ArrayList<FileObj>();
                    for (int i = 0; i < uris.length; i++) {
                        File uriFile = new File(uris[i].getPath());
                        String fileNameServer = spinnerCode + "-" + (i + 1) + ".jpg";
                        FileObj obj = new FileObj(spinnerCode, uriFile,
                                uris[i].getPath(), fileNameServer);
                        obj.setFullPath(uris[i].getPath());
                        obj.setRecodeName(recordPrepaid.getRecordName());
                        obj.setElectronicSign(recordPrepaid.getElectronicSign());
                        obj.setFileExtention(CommonActivity.getFileExtension(uriFile.getPath()));
                        //add list order vao ho so
                        obj.getArrOrderInfo().addAll(recordPrepaid.getArrOrderInfo());
                        fileObjs.add(obj);
                    }
                    profileBO.getHashmapFileObj().put(String.valueOf(imageId), fileObjs);
                } else {
                    Log.d(Constant.TAG,
                            "ChooseFileFragment onActivityResult() RecordPrepaid NULL mapListRecordPrepaid:"
                                    + profileBO.getMapListRecordPrepaid().size() + " "
                                    + profileBO.getMapListRecordPrepaid());
                }
            } else {
                Log.d(Constant.TAG, "ChooseFileFragment onActivityResult() holder NULL");
            }
        } else {
            Log.d(Constant.TAG, "ChooseFileFragment onActivityResult() uris NULL");
        }
    }

    private String savePathImageSig(Bitmap bitmapSig) {
        File pictureFile = getOutputSigFile();
        if (pictureFile == null) {
            Log.d("SAVE REG IMG", "Error creating media file");
            return null;
        }
        // luu noi dung anh vao file
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmapSig.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("SAVE REG IMG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("SAVE REG IMG", "Error accessing file: " + e.getMessage());
        }
        return pictureFile.getPath();
    }

    private File getOutputSigFile() {
        File mediaStorageDir = new File(Constant.DIR_SAVE_SIG_PATH);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + "_" + ".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public void doViewPdfFile(RecordTypeScanDTO recordTypeScanDTO) {
        PrintRequestOrderForSaleAsyncTask printRequestAsyncTask = new PrintRequestOrderForSaleAsyncTask(
                getActivity(),
                recordTypeScanDTO.getRecordCode(),
                recordTypeScanDTO.getArrOrderInfo().get(0).getOrderId(),
                new OnPostExecuteListener<File>() {
                    @Override
                    public void onPostExecute(File result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            CommonActivity.openFile(result, getActivity());
                        } else {
                            CommonActivity.createAlertDialog(getActivity(),
                                    description, getString(R.string.app_name)).show();
                        }
                    }
                }, moveLogInAct
        );
        printRequestAsyncTask.execute();
    }

    private boolean checkSigEnable() {
        for (Map.Entry<String, RecordTypeScanDTO> entry : profileBO.getMapRecordTypeScanDTOSelected().entrySet()) {
            RecordTypeScanDTO recordPrepaidTemp = entry.getValue();
            if ("1".equals(recordPrepaidTemp.getElectronicSign())) {
                return true;
            }
        }
        return false;
    }

    private void checkShowSignatue() {
        if (!checkSigEnable()) {
            isUseSignature = false;
            linSignatureContentTotal.setVisibility(View.GONE);
        } else {
            isUseSignature = true;
            linSignatureContentTotal.setVisibility(View.VISIBLE);
        }
        if (this.numberSig != 2) {
            linSignatureContentTwo.setVisibility(View.GONE);
        } else {
            linSignatureContentTwo.setVisibility(View.VISIBLE);
        }
    }

    public void doProcessSpinSelect(RecordTypeScanDTO recordTypeScanDTO, Integer imageId) {
        profileBO.getMapRecordTypeScanDTOSelected().put(imageId + "", recordTypeScanDTO);
        checkShowSignatue();
    }

    // kiem tra co phai chuyen nhuong
    private void initNumberSig() {
        this.numberSig = 1;
        for (ConnectionOrder connectionOrder : arrConnectionOrder) {
            for (ProfileRecord profileRecord : connectionOrder.getProfileRecords()) {
                if (Constant.PROFILE.CHUKY2.equals(profileRecord.getCode())) {
                    this.numberSig = 2;

                    //save orderInfo co chu ky 2 vao listOrderInfoChuky2
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setIsdn(connectionOrder.getIsdn());
                    orderInfo.setOrderId(connectionOrder.getProcessInstanceId());
                    orderInfo.setTaskId(connectionOrder.getTaskId());
                    orderInfo.setOrderTypeDesc(connectionOrder.getOrderTypeDesc());
                    arrOrderInfoChuky2.add(orderInfo);
                    break;
                }
            }
        }
    }

    public void putHashmapFileObj(String imageId, ArrayList<FileObj> fileObjs) {
        profileBO.getHashmapFileObj().put(imageId, fileObjs);
    }

    private void showSelectOptionDialog(final FileObj fileObj) {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle(getString(R.string.option));
        String[] pictureDialogItems = {
                getString(R.string.view_file),
                getString(R.string.choose_image_new)};

        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        CommonActivity.openFileFromLink(
                                fileObj.getUrl(), getActivity());
                        break;
                    case 1:
                        ImagePreviewActivity.pickImage(getActivity(),
                                profileBO.getHashmapFileObj(), imgId);
                        break;
                    default:
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    //gop cac chung tu vao cung 1 list
    private void addUploadDocument(UploadDocumentDTO uploadDocumentDTO) {
        ArrayList<UploadDocumentDTO> uploadFilesTmp = new ArrayList<>();
        uploadFilesTmp.addAll(uploadFiles);

        UploadDocumentDTO uploadDocumentDTOAdded = null;
        boolean isExistProfile = false;
        for (int index = 0; index < uploadFilesTmp.size(); index++) {
            uploadDocumentDTOAdded = uploadFilesTmp.get(index);
            long sourId = uploadDocumentDTO.getListRecord().get(0).getSourceId(); //sourceId cua 1 spinner
            long sourIdAdded = uploadDocumentDTOAdded.getListRecord().get(0).getSourceId(); //sourceId cua 1 spinner da duoc them

            //neu sourceId co roi, thi check xem co thay the khong
            //neu sourceId chua co, thi add them vao
            if (sourId == sourIdAdded) {
                isExistProfile = true;
                ArrayList<OrderInfo> arrOrderInfoMerge = meargeListOrderInfo(uploadDocumentDTOAdded.getListRecord().get(0).getArrOrderInfo(),
                        uploadDocumentDTO.getListRecord().get(0).getArrOrderInfo());

                //neu duoc replace thi thay the ho so moi vao ho so cu,day list orderInfo sang ho so moi
                //neu khong duoc thay the thi khong lam gi
                if (isReplaceProfile(uploadDocumentDTO)) {
                    //add arrOrderInfoMerge vao ho so duoc replace
                    for (RecordTypeScanDTO recordTypeScanDTO : uploadDocumentDTO.getListRecord()) {
                        recordTypeScanDTO.getArrOrderInfo().clear();
                        recordTypeScanDTO.getArrOrderInfo().addAll(arrOrderInfoMerge);
                    }
                    uploadFiles.add(uploadDocumentDTO);
                    uploadFiles.remove(uploadDocumentDTOAdded);
                    return;
                } else {
                    //add arrOrderInfoMerge vao ho so cu
                    for (RecordTypeScanDTO recordTypeScanDTOAdded : uploadFiles.get(index).getListRecord()) {
                        recordTypeScanDTOAdded.getArrOrderInfo().clear();
                        recordTypeScanDTOAdded.getArrOrderInfo().addAll(arrOrderInfoMerge);
                    }
                    return;
                }
            }
        }
        if (!isExistProfile) uploadFiles.add(uploadDocumentDTO);
    }

    private boolean isReplaceProfile(UploadDocumentDTO uploadDocumentDTO) {
        if (!CommonActivity.isNullOrEmpty(uploadDocumentDTO.getUrlImage())) {
            return true;
        }
        return false;
    }

    //merge orderInfo vao OrderInfo da co
    private ArrayList<OrderInfo> meargeListOrderInfo(ArrayList<OrderInfo> arrOrderInfoAdded, ArrayList<OrderInfo> arrOrderInfo){
        ArrayList<OrderInfo> result = (ArrayList<OrderInfo>) arrOrderInfoAdded.clone();
        for(OrderInfo orderInfo: arrOrderInfo){
            if (!isExitsInfo(orderInfo, arrOrderInfoAdded)) {
                result.add(orderInfo);
            }
        }
        return result;
    }

    private boolean isExitsInfo(OrderInfo orderInfo, ArrayList<OrderInfo> arrOrderInfo) {
        for(OrderInfo orderInfoOld: arrOrderInfo){
            if(orderInfoOld.getOrderId().equals(orderInfo.getOrderId())){
                return true;
            }
        }
        return false;
    }

    private void addProfileRecords(ProfileRecord profileRecord){
        for(ProfileRecord profileRecorAdded : arrProfileRecord){
            if (CommonActivity.isNullOrEmpty(profileRecorAdded.getCode())
                    || CommonActivity.isNullOrEmpty(profileRecord.getCode())) {
                CommonActivity.createAlertDialog(act, R.string.app_name, R.string.miss_record_code).show();
                return;
            }
            //neu co roi thi add orderId cua thang moi vao thang cu
            if (profileRecorAdded.getCode().equals(profileRecord.getCode())){
                if(!isExistOrderId(profileRecorAdded, profileRecord)) {
                    profileRecorAdded.getArrOrderInfo().addAll(profileRecord.getArrOrderInfo());
                    return;
                } else {
                    return;
                }
            }
        }
        arrProfileRecord.add(profileRecord);
    }

    private boolean isExistOrderId(ProfileRecord profileRecorAdded, ProfileRecord profileRecord){
        boolean check = false;
        String orderId = profileRecord.getArrOrderInfo().get(0).getOrderId();// doi tuong moi' chi co 1 orderId
        for(OrderInfo orderInfo: profileRecorAdded.getArrOrderInfo()){
            if(orderId.equals(orderInfo.getOrderId())){
                check = true;
            }
        }
        return check;
    }
}
