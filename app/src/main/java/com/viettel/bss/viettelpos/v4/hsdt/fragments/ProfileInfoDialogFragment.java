package com.viettel.bss.viettelpos.v4.hsdt.fragments;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.dialog.DialogSelectProfileFragment;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.hsdt.adapter.ProfileAdapter;
import com.viettel.bss.viettelpos.v4.hsdt.asynctask.PrintRequestAsyncTask;
import com.viettel.bss.viettelpos.v4.hsdt.listener.OnFinishSignature;
import com.viettel.bss.viettelpos.v4.hsdt.object.ProfileTypeComparator;
import com.viettel.bss.viettelpos.v4.listener.OnFinishDSFListener;
import com.viettel.bss.viettelpos.v4.listener.OnSelectNPFListener;
import com.viettel.bss.viettelpos.v4.listener.OnSelectOPFListener;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 10/18/2017.
 */

public class ProfileInfoDialogFragment extends DialogFragment {

    @BindView(R.id.recListProfile)
    RecyclerView recListProfile;
    @BindView(R.id.linSignatureContentTotal)
    LinearLayout linSignatureContentTotal;

    @BindView(R.id.linSignatureContentOne)
    LinearLayout linSignatureContentOne;
    @BindView(R.id.tvSigCusOne)
    TextView tvSigCusOne;
    @BindView(R.id.frlSignatureLayoutOne)
    FrameLayout frlSignatureLayoutOne;
    @BindView(R.id.imgShowSignatureOne)
    ImageView imgShowSignatureOne;
    @BindView(R.id.imgbtEditSignatureOne)
    ImageButton imgbtEditSignatureOne;

    @BindView(R.id.linSignatureContentTwo)
    LinearLayout linSignatureContentTwo;
    @BindView(R.id.tvSigCusTwo)
    TextView tvSigCusTwo;
    @BindView(R.id.frlSignatureLayoutTwo)
    FrameLayout frlSignatureLayoutTwo;
    @BindView(R.id.imgShowSignatureTwo)
    ImageView imgShowSignatureTwo;
    @BindView(R.id.imgbtEditSignatureTwo)
    ImageButton imgbtEditSignatureTwo;

    @BindView(R.id.lnCheckBoxSelect)
    LinearLayout lnCheckBoxSelect;
    @BindView(R.id.cbSelectUseSignature)
    CheckBox cbSelectUseSignature;

    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnAccept)
    Button btnAccept;
    @BindView(R.id.imgBtnClose)
    ImageButton imgBtnClose;

    private View mView;
    private String xmlSub;
    private String xmlCus;
    private String orderType;
    private boolean isFullFile = false; //truong check ho so du
    private ProfileBO profileBO;
    private ProfileAdapter profileAdapter;
    private int imgId;
    private DialogSelectProfileFragment dialogSelectProfileFragment;
    private OnFinishDSFListener onFinishDSFListener;
    private boolean isUseSignature;
    private String groupType;
    private Bitmap bitmapSigCurrent;
    private Bitmap bitmapSigCurrentTwo;
    private String numberSig;
    protected String permission = "";
    private boolean isOmni;
    private boolean initFlag;

    //[BaVV] Add 'Task - Ho so dau noi' Start
    List<ArrayList<RecordPrepaid>> recordPrepaidArrayListBackup = new ArrayList<ArrayList<RecordPrepaid>>();
    //[BaVV] Add 'Task - Ho so dau noi' End

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getDialog().setCanceledOnTouchOutside(false);
        if (mView == null) {
            mView = inflater.inflate(R.layout.profile_info_fragment, container, false);
            ButterKnife.bind(this, mView);
            unit();
        }
        return mView;
    }

    protected void unit() {
        CommonActivity.deleteAllFileInDir(Constant.DIR_SAVE_PROFILE_PATH);
        this.isOmni = false;
        this.initFlag = true;

        Bundle bundle = getArguments();
        if (bundle != null) {
            this.profileBO = (ProfileBO) bundle.getSerializable("profileBO");
            this.xmlSub = bundle.getString("xmlSub");
            this.xmlCus = bundle.getString("xmlCus");
            this.numberSig = bundle.getString("numberSig");
            this.xmlSub.replace(">null<", "><");
            this.xmlCus.replace(">null<", "><");
            this.groupType = bundle.getString("groupType");
            this.orderType = bundle.getString("orderType");

            if ("1".equals(bundle.getString("isOmni"))) {
                this.isOmni = true;
            } else {
                this.isOmni = false;
            }

            if (profileBO.isUseSig()) {
                cbSelectUseSignature.setChecked(true);
            } else {
                cbSelectUseSignature.setChecked(false);
            }

            if(CommonActivity.isNullOrEmpty(profileBO.getMapRecordSelected())) {
                initRecordSelected();
            }

            checkShowSignatue();
            initLvUploadImage();

            if (!CommonActivity.isNullOrEmpty(profileBO.getSigImageFullPath())) {
                imgShowSignatureOne.setImageURI(Uri.parse(profileBO.getSigImageFullPath()));
                bitmapSigCurrent = ((BitmapDrawable)imgShowSignatureOne.getDrawable()).getBitmap();
                imgbtEditSignatureOne.setVisibility(View.GONE);
            } else {
                imgbtEditSignatureOne.setVisibility(View.VISIBLE);
            }

            if (!CommonActivity.isNullOrEmpty(profileBO.getSigImageFullPathTwo())) {
                imgShowSignatureTwo.setImageURI(Uri.parse(profileBO.getSigImageFullPathTwo()));
                bitmapSigCurrentTwo = ((BitmapDrawable)imgShowSignatureTwo.getDrawable()).getBitmap();
                imgbtEditSignatureTwo.setVisibility(View.GONE);
            } else {
                imgbtEditSignatureTwo.setVisibility(View.VISIBLE);
            }
        }

        recListProfile.setHasFixedSize(true);
        recListProfile.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (profileBO.isUseSig()) {
            cbSelectUseSignature.setChecked(true);
        } else {
            cbSelectUseSignature.setChecked(false);
        }

        cbSelectUseSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkShowSignatue();
                profileAdapter.setUseSig(isUseSignature);
                profileAdapter.notifyDataSetChanged();
            }
        });

        frlSignatureLayoutOne.setOnClickListener(new View.OnClickListener() {
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

        imgbtEditSignatureOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignatureFragment(1);
            }
        });

        imgbtEditSignatureTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignatureFragment(2);
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFullFile = validateSelectProfile();
                if (isFullFile) {
                    if (!isUseSignature ) {
                        // neu la luong omni thi thuc hien day them danh sach profileRecord vao
                        if(isOmni){
                            profileBO.updateProfileRecords(
                                    CommonActivity.getBase64String(bitmapSigCurrent, Constant.IMG_EXT_PNG),
                                    CommonActivity.getBase64String(bitmapSigCurrentTwo, Constant.IMG_EXT_PNG));
                            profileBO.setSigImageFullPath(null);
                            profileBO.setSigImageFullPathTwo(null);
                            profileBO.setUseSig(false);
                        }else{
                            profileBO.setProfileRecords(null);
                            profileBO.setSigImageFullPath(null);
                            profileBO.setSigImageFullPathTwo(null);
                            profileBO.setUseSig(false);
                        }
                    } else {
                        removeImgReplaceSig();
                        profileBO.setUseSig(true);
                        profileBO.updateProfileRecords(
                                CommonActivity.getBase64String(bitmapSigCurrent, Constant.IMG_EXT_PNG),
                                CommonActivity.getBase64String(bitmapSigCurrentTwo, Constant.IMG_EXT_PNG));
                    }
                    onFinishDSFListener.onFinish(profileBO);
                    dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void checkShowSignatue() {
        if (isOmni || "2".equals(this.groupType) || !checkSigEnable()) {
            cbSelectUseSignature.setChecked(false);
            cbSelectUseSignature.setEnabled(false);
            isUseSignature = false;
            lnCheckBoxSelect.setVisibility(View.GONE);
            if (initFlag) {
                linSignatureContentTotal.setVisibility(View.GONE);
                initFlag = false;
            }
        } else {
            if (cbSelectUseSignature.isChecked()) {
                isUseSignature = true;
                linSignatureContentTotal.setVisibility(View.VISIBLE);
            } else {
                isUseSignature = false;
                linSignatureContentTotal.setVisibility(View.GONE);
            }
        }
        if (!"2".equals(numberSig)) {
            linSignatureContentTwo.setVisibility(View.GONE);
        } else {
            linSignatureContentTwo.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateSelectProfile() {

        //validate ho so bat buoc nhap
        RecordPrepaid recordPrepaidTemp;
        for (Map.Entry<String, RecordPrepaid> entry : profileBO.getMapRecordSelected().entrySet()) {
            recordPrepaidTemp = entry.getValue();
            if ("1".equals(recordPrepaidTemp.getRequire())) {
                if (isOmni && recordPrepaidTemp.getElectronicSign() == 1) {
                    continue;
                }
                if (isUseSignature && recordPrepaidTemp.getElectronicSign() == 1) {
                    continue;
                }
                String imageId = entry.getKey();
                if (!profileBO.getHashmapFileObj().containsKey(imageId)) {
                    CommonActivity.toast(getActivity(), R.string.checkthieuanhhoso);
                    return false;
                }
            }
        }

        // validate chu ky
        if (!isOmni && isUseSignature && CommonActivity.isNullOrEmpty(profileBO.getSigImageFullPath())) {
            CommonActivity.toast(getActivity(), R.string.confirm_sign);
            return false;
        }

        if (!isOmni && isUseSignature && "2".equals(this.numberSig)
                && CommonActivity.isNullOrEmpty(profileBO.getSigImageFullPathTwo())) {
            CommonActivity.toast(getActivity(), R.string.confirm_sign);
            return false;
        }

        return true;
    }

    private void removeImgReplaceSig() {
        RecordPrepaid recordPrepaidTemp;
        for (Map.Entry<String, RecordPrepaid> entry : profileBO.getMapRecordSelected().entrySet()) {
            recordPrepaidTemp = entry.getValue();
            if ("1".equals(recordPrepaidTemp.getRequire())) {
                if (isUseSignature && recordPrepaidTemp.getElectronicSign() == 1) {
                    String imageId = entry.getKey();
                    profileBO.getHashmapFileObj().remove(imageId);
                }
            }
        }
    }

    public View.OnClickListener moveLogInAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), permission);
            dialog.show();
        }
    };

    private void initRecordSelected() {
        List<ArrayList<RecordPrepaid>> arrayOfArrayList =
                new ArrayList<ArrayList<RecordPrepaid>>(profileBO.getMapListRecordPrepaid().values());
        for (ArrayList<RecordPrepaid> lstRecord : arrayOfArrayList) {
            if (lstRecord.size() > 0) {
                profileBO.getMapRecordSelected().put(lstRecord.get(0).getId(),
                        lstRecord.get(getPositionSelected(lstRecord)));
            }
        }
    }

    private int getPositionSelected(final ArrayList<RecordPrepaid> recordPrepaids){
        for(int i =0; i< recordPrepaids.size(); i++){
            if(recordPrepaids.get(i).isSelected()){
                return i;
            }
        }
        return 0;
    }

    private void showSignatureFragment(int index) {
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

    // bam chon anh
    private OnSelectNPFListener onSelectNPFListener = new OnSelectNPFListener() {
        @Override
        public void OnClick(RecordPrepaid item) {
            if(item.isUseOldProfile()){
                profileBO.getHashmapFileObj().remove(item.getId());
            }
            ImagePreviewActivity.pickImage(getActivity(), profileBO.getHashmapFileObj(), imgId);
        }
    };

    // luu chu ky
    private OnFinishSignature onFinishSignature = new OnFinishSignature() {
        @Override
        public void onFinish(Bitmap bitmap, int index) {
            if (index == 1) {
                bitmapSigCurrent = bitmap;
                imgShowSignatureOne.setImageBitmap(bitmap);
                imgbtEditSignatureOne.setVisibility(View.GONE);
                saveSigImage(bitmapSigCurrent, index);
            } else {
                bitmapSigCurrentTwo = bitmap;
                imgShowSignatureTwo.setImageBitmap(bitmap);
                imgbtEditSignatureTwo.setVisibility(View.GONE);
                saveSigImage(bitmapSigCurrentTwo, index);
            }
        }

        @Override
        public void onFinish(Bitmap bitmap) {
            // do nothing
        }
    };

    private OnSelectOPFListener onSelectOPFListener = new OnSelectOPFListener() {
        @Override
        public void OnClick(RecordPrepaid item, boolean isSelect) {
            Log.d(Constant.TAG, "isSelect = " + isSelect);
            ArrayList<RecordPrepaid> lstRecordPrepaids = profileBO.getMapListRecordPrepaid().get(item.getId());
            for (RecordPrepaid recordPrepaid : lstRecordPrepaids) {
                if (item.getCode().equals(recordPrepaid.getCode())) {
                    recordPrepaid.setPathOldProfile(item.getPathOldProfile());
                    recordPrepaid.setIsUseOldProfile(isSelect);
                    recordPrepaid.setIsSelected(isSelect);
                    recordPrepaid.setRecordId(item.getRecordId());
                } else {
                    recordPrepaid.setIsUseOldProfile(false);
                }
            }

            if(isSelect) {
                ArrayList<RecordPrepaid> recordPrepaids = profileBO.getMapListRecordPrepaid().get(item.getId());
                recordPrepaids.get(0).setFullPath(item.getPathOldProfile());

                for (RecordPrepaid record : recordPrepaids) {
                    if (!record.getCode().equals(item.getCode())) {
                        record.setIsSelected(false);
                    }
                }

                String spinnerCode = item.getCode();

                ArrayList<FileObj> fileObjs = new ArrayList<FileObj>();
                File uriFile = new File(item.getPathOldProfile());
                String fileNameServer = spinnerCode + "-1.jpg";
                FileObj obj = new FileObj(spinnerCode, uriFile, item.getPathOldProfile(), fileNameServer);
                obj.setFullPath(item.getPathOldProfile());
                obj.setRecordId(Long.valueOf(item.getRecordId()));
                obj.setIsUseOldProfile(true);
                obj.setActions(item.getActions());
                obj.setRecodeName(item.getName());
                fileObjs.add(obj);

                profileBO.getHashmapFileObj().put(item.getId(), fileObjs);
                String[] lstAction = item.getActions().split(";");
                for (String action : lstAction) {
                    for (FileObj fileObj: fileObjs) {
                        fileObj.setActions(action);
                    }
                    profileBO.getHashmapFileObjDuplicate().put(
                            String.valueOf(item.getId()) + "_" + action, fileObjs);
                }
            } else {
                profileBO.getHashmapFileObj().remove(item.getId());
            }
            profileAdapter.notifyDataSetChanged();
        }
    };

    private void initLvUploadImage() {
        List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>();
        if(!CommonActivity.isNullOrEmpty(profileBO.getMapListRecordPrepaid())){
            arrayOfArrayList.addAll(profileBO.getMapListRecordPrepaid().values());
            Collections.sort(arrayOfArrayList, new ProfileTypeComparator());
        }
        profileAdapter = new ProfileAdapter(getActivity(), profileBO, arrayOfArrayList,
                this, groupType, isUseSignature, isOmni);
        recListProfile.setAdapter(profileAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Define.CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    processImageProfileSelected(data);
                    break;
                default:
                    break;
            }
        }
    }

    private void saveSigImage(Bitmap bitmapSig, int index) {
        // lay ten file
        File pictureFile = getOutputSigFile(index);
        if (index == 1) {
            this.profileBO.setSigImageFullPath(pictureFile.getPath());
        } else {
            this.profileBO.setSigImageFullPathTwo(pictureFile.getPath());
        }
        if (pictureFile == null) {
            Log.d("SAVE REG IMG", "Error creating media file");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmapSig.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("SAVE REG IMG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("SAVE REG IMG", "Error accessing file: " + e.getMessage());
        }
    }

    private  File getOutputSigFile(int index) {
        File mediaStorageDir = new File(Constant.DIR_SAVE_SIG_PATH);
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp + "_" + index + ".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public void setOnFinishDSFListener(OnFinishDSFListener onFinishDSFListener) {
        this.onFinishDSFListener = onFinishDSFListener;
    }

    public void doViewPdfFile(RecordPrepaid recordPrepaid) {
        PrintRequestAsyncTask printRequestAsyncTask = new PrintRequestAsyncTask(
                getActivity(), recordPrepaid.getCode(),
                xmlSub, xmlCus, orderType + "",
                new OnPostExecuteListener<File>() {
                    @Override
                    public void onPostExecute(File result, String errorCode, String description) {
                        if("0".equals(errorCode) && result != null) {
                            CommonActivity.openFile(result, getActivity());
                        } else{
                            CommonActivity.createAlertDialog(getActivity(),
                                    description, getString(R.string.app_name)).show();
                        }
                    }
                }, moveLogInAct
        );
        printRequestAsyncTask.execute();
    }

    public void doProcessImageClick(RecordPrepaid item, Integer imageId) {
        Log.d(Constant.TAG, "view.getId() : " + imageId);
        imgId = imageId;
        profileBO.getMapRecordSelected().put(String.valueOf(imgId), item);
        if ("1".equals(item.getAlowReuser())) {
            //truong hop chung tu cho phep su dung lai ho so cu
            dialogSelectProfileFragment = new DialogSelectProfileFragment(
                    onSelectNPFListener, onSelectOPFListener);
            dialogSelectProfileFragment.setProfileBO(profileBO);
            dialogSelectProfileFragment.setRecordPrepaid(item);
            dialogSelectProfileFragment.show(getFragmentManager(), "Select profile");
        } else {
            ImagePreviewActivity.pickImage(getActivity(), profileBO.getHashmapFileObj(), imgId);
        }
    }

    public void doProcessSpinSelect(RecordPrepaid item, Integer imageId) {
        profileBO.getMapRecordSelected().put(String.valueOf(imageId), item);
        if (!cbSelectUseSignature.isChecked()) {
            return;
        }
        checkShowSignatue();
    }

    private boolean checkSigEnable() {
        for (Map.Entry<String, RecordPrepaid> entry : profileBO.getMapRecordSelected().entrySet()) {
            RecordPrepaid recordPrepaidTemp = entry.getValue();
            if (recordPrepaidTemp.getElectronicSign() == 1) {
                return true;
            }
        }
        return false;
    }

    private void processImageProfileSelected(Intent data) {

        Parcelable[] parcelableUris = data.getParcelableArrayExtra(
                ImagePickerActivity.EXTRA_IMAGE_URIS);
        if (parcelableUris == null) {
            return;
        }
        Uri[] uris = new Uri[parcelableUris.length];
        System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

        int imageId = data.getExtras().getInt("imageId", -1);
        if (uris != null && uris.length > 0 && imageId >= 0) {
            ProfileAdapter.ViewHolder holder = null;
            for (int i = 0; i < recListProfile.getChildCount(); i++) {
                View rowView = recListProfile.getChildAt(i);
                ProfileAdapter.ViewHolder h = (ProfileAdapter.ViewHolder) rowView.getTag();
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
                ArrayList<RecordPrepaid> recordPrepaids = profileBO.getMapListRecordPrepaid()
                        .get(String.valueOf(imageId));
                recordPrepaids.get(0).setFullPath(uris[0].getPath());

                if (recordPrepaids != null) {
                    RecordPrepaid recordPrepaid = recordPrepaids.get(position);
                    recordPrepaid.setIsSelected(true);
                    for (RecordPrepaid record : recordPrepaids) {
                        Log.d("pickImage", "code = " + record.getCode() + ", code = "
                                + recordPrepaid.getCode());
                        Log.d("pickImage", "id = " + record.getId() + ", id = " + recordPrepaid.getId());
                        if (!record.getCode().equals(recordPrepaid.getCode())) {
                            record.setIsSelected(false);
                        }
                        record.setIsUseOldProfile(false); //truong hop chon ho so moi bo ho so cu
                    }

                    String spinnerCode = recordPrepaid.getCode();
                    Log.i(Constant.TAG, "imageId: " + imageId + " spinner position: " + position
                            + " spinnerCode: " + spinnerCode + " uris: " + uris.length);

                    ArrayList<FileObj> fileObjs = new ArrayList<FileObj>();
                    for (int i = 0; i < uris.length; i++) {
                        File uriFile = new File(uris[i].getPath());
                        String fileNameServer = spinnerCode + "-" + (i + 1) + ".jpg";
                        FileObj obj = new FileObj(spinnerCode, uriFile,
                                uris[i].getPath(), fileNameServer);
                        obj.setFullPath(uris[i].getPath());
                        obj.setActions(recordPrepaid.getActions());
                        obj.setRecodeName(recordPrepaid.getName());
                        obj.setFileExtention(CommonActivity.getFileExtension(uriFile.getPath()));
                        fileObjs.add(obj);
                    }
                    profileBO.getHashmapFileObj().put(String.valueOf(imageId), fileObjs);
                    String[] lstAction = recordPrepaid.getActions().split(";");
                    for (String action : lstAction) {
                        for (FileObj item: fileObjs) {
                            item.setActions(action);
                        }
                        profileBO.getHashmapFileObjDuplicate().put(String.valueOf(imageId)
                                + "_" + action, fileObjs);
                    }
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
        if (dialogSelectProfileFragment != null) {
            dialogSelectProfileFragment.dismiss();
        }
    }

    public void setFullFile(boolean fullFile) {
        isFullFile = fullFile;
    }

    public boolean isFullFile() {
        return isFullFile;
    }
}


