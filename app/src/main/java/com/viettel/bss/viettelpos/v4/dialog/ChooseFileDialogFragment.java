package com.viettel.bss.viettelpos.v4.dialog;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.RecordPrepaidAdapter;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.UI;
import com.viettel.bss.viettelpos.v4.customer.manage.OmiRegisterNewFragment;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.listener.OnFinishDSFListener;
import com.viettel.bss.viettelpos.v4.listener.OnImageClickListener;
import com.viettel.bss.viettelpos.v4.listener.OnSelectNPFListener;
import com.viettel.bss.viettelpos.v4.listener.OnSelectOPFListener;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.RecordTypeScanDTO;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListRecordProfile;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetOldProfile;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Toancx on 5/12/2017.
 */
public class ChooseFileDialogFragment extends DialogFragment {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.lvUploadImage)
    ListView lvUploadImage;
    @BindView(R.id.btnAccept)
    Button btnAccept;
    LinearLayout lnSelectProfile;

    View mView;
    ProfileBO input;
    RecordPrepaidAdapter adapter;
    RecordPrepaid recordPrepaid;
    int imgId;
    DialogSelectProfileFragment dialogSelectProfileFragment;
    OnFinishDSFListener onFinishDSFListener;
    boolean isFullFile = false; //truong check ho so du
    private Boolean isMoreThan =null;
    private String CHECK_REGISTER_INFO_OMNICHANNEL=null;
    private ConnectionOrder connectionOrder=null;

    public static ChooseFileDialogFragment newInstance(ProfileBO input) {
        ChooseFileDialogFragment fragment = new ChooseFileDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("profileInput", input);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ChooseFileDialogFragment newInstance(ProfileBO input,String CHECK_REGISTER_INFO_OMNICHANNEL, Boolean isMoreThan, ConnectionOrder connectionOrder) {
        ChooseFileDialogFragment fragment = new ChooseFileDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("profileInput", input);
        bundle.putString("CHECK_REGISTER_INFO_OMNICHANNEL", CHECK_REGISTER_INFO_OMNICHANNEL);
        bundle.putSerializable("isMoreThan", isMoreThan);
        bundle.putSerializable("connectionOrder", connectionOrder);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Light_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_choose_file, container, false);
            ButterKnife.bind(this, mView);

            getListRecordProfile();
            setCancelable(false);
        }
        return mView;
    }

    private void getListRecordProfile() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            input = (ProfileBO) bundle.getSerializable("profileInput");
            connectionOrder= (ConnectionOrder) bundle.getSerializable("connectionOrder");
            isMoreThan = (Boolean) bundle.getSerializable("isMoreThan");
            CHECK_REGISTER_INFO_OMNICHANNEL = bundle.getString("CHECK_REGISTER_INFO_OMNICHANNEL","");
            initLvUploadImage();
            if(CommonActivity.isNullOrEmpty(input.getMapRecordSelected())) {
                initRecordSelected();
            }
        }
    }

    private void initLvUploadImage() {
        List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>();
        if(!CommonActivity.isNullOrEmpty(input.getMapListRecordPrepaid())){
            arrayOfArrayList.addAll(input.getMapListRecordPrepaid().values());
        }
//        if(!CommonActivity.isNullOrEmptyArray(CHECK_REGISTER_INFO_OMNICHANNEL)){
//            List<ProfileRecord>  lstProfileOmi = connectionOrder.getProfileRecords();
//
//            List<ArrayList<RecordPrepaid>> lstToRemove = new ArrayList<>();
//            for(ProfileRecord profileOmi: lstProfileOmi){
//                for (ArrayList<RecordPrepaid> lstProfileRegister: arrayOfArrayList){
//                for (RecordPrepaid profileRegister: lstProfileRegister){
//                    if(profileRegister.getCode().equals(profileOmi.getCode())){
//                        lstToRemove.add(lstProfileRegister);
//                        break;
//                    }
//                }
//            }
//        }
//            arrayOfArrayList.removeAll(lstToRemove);
//        }

        adapter = new RecordPrepaidAdapter(getActivity(), arrayOfArrayList, imageListenner);
        lvUploadImage.setAdapter(adapter);

        UI.setListViewHeightBasedOnChildren(lvUploadImage);
    }

    private void initRecordSelected() {
        List<ArrayList<RecordPrepaid>> arrayOfArrayList = new ArrayList<ArrayList<RecordPrepaid>>(
                input.getMapListRecordPrepaid().values());
        for (ArrayList<RecordPrepaid> lstRecord : arrayOfArrayList) {
            input.getMapRecordSelected().put(lstRecord.get(0).getId(), lstRecord.get(getPositionSelected(lstRecord)));
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

    OnImageClickListener imageListenner = new OnImageClickListener() {
        @Override
        public void onClick(RecordPrepaid item, Integer imageId) {
            Log.d(Constant.TAG, "view.getId() : " + imageId);
            recordPrepaid = item;
            imgId = imageId;
            input.getMapRecordSelected().put(String.valueOf(imgId), item);

            if ("1".equals(item.getAlowReuser())) { //truong hop chung tu cho phep su dung lai ho so cu
                dialogSelectProfileFragment = new DialogSelectProfileFragment(onSelectNPFListener, onSelectOPFListener);

                dialogSelectProfileFragment.setProfileBO(input);
                dialogSelectProfileFragment.setRecordPrepaid(recordPrepaid);

                dialogSelectProfileFragment.show(getFragmentManager(), "Select profile");
            } else {
                ImagePreviewActivity.pickImage(getActivity(), input.getHashmapFileObj(), imgId);
            }
        }

        @Override
        public void onClickNew(RecordTypeScanDTO item, Integer imageId) {

        }
    };

    OnSelectOPFListener onSelectOPFListener = new OnSelectOPFListener() {
        @Override
        public void OnClick(RecordPrepaid item, boolean isSelect) {
            Log.d(Constant.TAG, "isSelect = " + isSelect);
            ArrayList<RecordPrepaid> lstRecordPrepaids = input.getMapListRecordPrepaid().get(item.getId());
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
                ArrayList<RecordPrepaid> recordPrepaids = input.getMapListRecordPrepaid().get(item.getId());
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

                input.getHashmapFileObj().put(item.getId(), fileObjs);
                String[] lstAction = item.getActions().split(";");
                for (String action : lstAction) {
                    for (FileObj fileObj: fileObjs) {
                        fileObj.setActions(action);
                    }
                    input.getHashmapFileObjDuplicate().put(String.valueOf(item.getId()) + "_" + action, fileObjs);
                }
            } else {
                input.getHashmapFileObj().remove(item.getId());
            }
            adapter.notifyDataSetChanged();
        }
    };


    private void openFile(String fileContent, String fileName) {
        Log.d("openFile", "fileName = " + fileName);
        try {
            byte[] fileByte = Base64.decode(fileContent, Base64.DEFAULT);
            File file = new File(
                    Environment.getExternalStorageDirectory(),
                    fileName);
            FileOutputStream fos = new FileOutputStream(
                    file.getPath());
            fos.write(fileByte);
            fos.flush();
            fos.close();
            String filePath = file.getPath();

            openFile(filePath);
        } catch (Exception ex) {
            Log.d(Constant.TAG, "openFile", ex);
            CommonActivity.toast(getActivity(), R.string.error_open_file);
        }
    }

    private void openFile(String fileName) {
        File file = new File(fileName);
        Log.d("LOG", "Link file: " + fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        String extension = android.webkit.MimeTypeMap
                .getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(extension);
        if ("".equals(extension) || mimetype == null) {
        } else {
            intent.setDataAndType(Uri.fromFile(file), mimetype);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent2 = Intent.createChooser(intent, "Open File");
        try {
            startActivity(intent2);
        } catch (ActivityNotFoundException ex) {
            Log.d(Constant.TAG, "openFile", ex);
            CommonActivity.toast(getActivity(), R.string.error_open_file);
        }
    }

    View.OnClickListener moveLogInAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), "");
            dialog.show();

        }
    };

    OnSelectNPFListener onSelectNPFListener = new OnSelectNPFListener() {
        @Override
        public void OnClick(RecordPrepaid item) {
            if(item.isUseOldProfile()){
                input.getHashmapFileObj().remove(item.getId());
            }

            ImagePreviewActivity.pickImage(getActivity(), input.getHashmapFileObj(), imgId);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Constant.TAG, "ChooseFileFragment onActivityResult requestCode : " + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Define.CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");
                    Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                    if (parcelableUris == null) {
                        return;
                    }

                    // Java doesn't allow array casting, this is a little hack
                    Uri[] uris = new Uri[parcelableUris.length];
                    System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

                    int imageId = data.getExtras().getInt("imageId", -1);
                    Log.d(Constant.TAG, "ChooseFileFragment onActivityResult() imageId: " + imageId);
                    if (uris != null && uris.length > 0 && imageId >= 0) {
                        RecordPrepaidAdapter.ViewHolder holder = null;
                        for (int i = 0; i < lvUploadImage.getChildCount(); i++) {
                            View rowView = lvUploadImage.getChildAt(i);
                            RecordPrepaidAdapter.ViewHolder h = (RecordPrepaidAdapter.ViewHolder) rowView.getTag();
                            if (h != null) {
                                int id = h.ibUploadImage.getId();
                                if (imageId == id) {
                                    holder = h;
                                    break;
                                }
                            }
                        }

                        if (holder != null) {
//                            Picasso.with(getActivity()).load(new File(uris[0].toString())).centerCrop().resize(100, 100)
//                                    .into(holder.ibUploadImage);

                            Picasso.with(getActivity()).load(new File(uris[0].toString())).centerCrop().resize(holder.ibUploadImage.getWidth(), holder.ibUploadImage.getHeight())
                                    .into(holder.ibUploadImage);

                            int position = holder.spUploadImage.getSelectedItemPosition();
                            if (position < 0) {
                                position = 0;
                            }

                            ArrayList<RecordPrepaid> recordPrepaids = input.getMapListRecordPrepaid().get(String.valueOf(imageId));
                            recordPrepaids.get(0).setFullPath(uris[0].toString());

                            if (recordPrepaids != null) {
                                RecordPrepaid recordPrepaid = recordPrepaids.get(position);
                                recordPrepaid.setIsSelected(true);

                                for (RecordPrepaid record : recordPrepaids) {
                                    Log.d("pickImage", "code = " + record.getCode() + ", code = " + recordPrepaid.getCode());
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
                                    FileObj obj = new FileObj(spinnerCode, uriFile, uris[i].getPath(), fileNameServer);
                                    obj.setFullPath(uris[i].toString());
                                    obj.setActions(recordPrepaid.getActions());
                                    obj.setRecodeName(recordPrepaid.getName());
                                    fileObjs.add(obj);

                                }
                                input.getHashmapFileObj().put(String.valueOf(imageId), fileObjs);

                                String[] lstAction = recordPrepaid.getActions().split(";");
                                for (String action : lstAction) {
                                    for (FileObj item: fileObjs) {
                                        item.setActions(action);
                                    }
                                    input.getHashmapFileObjDuplicate().put(String.valueOf(imageId) + "_" + action, fileObjs);
                                }
                            } else {
                                Log.d(Constant.TAG,
                                        "ChooseFileFragment onActivityResult() RecordPrepaid NULL mapListRecordPrepaid:"
                                                + input.getMapListRecordPrepaid().size() + " " + input.getMapListRecordPrepaid());
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
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick(R.id.btnAccept)
    public void btnAcceptOnClick() {
        isFullFile = validateSelectProfile();
        if (isFullFile) {
            onFinishDSFListener.onFinish(input);
            getDialog().dismiss();
        }
    }

    @OnClick(R.id.imgClose)
    public void imgCloseOnClick() {
        isFullFile = validateSelectProfile();
        getDialog().dismiss();
    }


    private boolean validateSelectProfile() {
        //validate ho so bat buoc nhap
        for (Map.Entry<String, RecordPrepaid> entry : input.getMapRecordSelected().entrySet()) {
            if ("1".equals(entry.getValue().getRequire())) {
                String imageId = entry.getKey();
                if (!input.getHashmapFileObj().containsKey(imageId)) {
                    CommonActivity.toast(getActivity(), R.string.checkthieuanhhoso);
                    return false;
                }
            }
        }
        initRecordSelected();
        return true;
    }

    public void setOnFinishDSFListener(OnFinishDSFListener onFinishDSFListener) {
        this.onFinishDSFListener = onFinishDSFListener;
    }

    public boolean isFullFile() {
        return isFullFile;
    }

    public void setIsFullFile(boolean isFullFile) {
        this.isFullFile = isFullFile;
    }
}
