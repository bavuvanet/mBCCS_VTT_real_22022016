package com.viettel.bss.viettelpos.v4.dialog;


import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.listener.OnSelectNPFListener;
import com.viettel.bss.viettelpos.v4.listener.OnSelectOPFListener;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetOldProfile;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogSelectProfileFragment extends DialogFragment {

    @BindView(R.id.imgClose)
    ImageView imgClose;
    @BindView(R.id.lnHSCu)
    LinearLayout lnHSCu;
    @BindView(R.id.lnHSMoi)
    LinearLayout lnHSMoi;
    @BindView(R.id.tvViewProfile)
    TextView tvViewProfile;
    @BindView(R.id.cbSelect)
    CheckBox cbSelect;

    View mView;
    OnSelectOPFListener onSelectOPFListener;
    OnSelectNPFListener onSelectNPFListener;
    ProfileBO profileBO;
    RecordPrepaid recordPrepaid;

    public DialogSelectProfileFragment(OnSelectNPFListener onSelectNPFListener, OnSelectOPFListener onSelectOPFListener){
        this.onSelectNPFListener = onSelectNPFListener;
        this.onSelectOPFListener = onSelectOPFListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, DialogFragment.STYLE_NORMAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_dialog_select_profile, container, false);
            ButterKnife.bind(this, mView);

            if(!CommonActivity.isNullOrEmpty(recordPrepaid.getPathOldProfile())){
                tvViewProfile.setVisibility(View.VISIBLE);
                cbSelect.setVisibility(View.VISIBLE);
                if(recordPrepaid.isUseOldProfile()){
                    cbSelect.setChecked(true);
                }
            }

            cbSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectOPFListener.OnClick(recordPrepaid, cbSelect.isChecked());
                    if (cbSelect.isChecked()) {
                        getDialog().dismiss();
                    }
                }
            });
        }
        return mView;
    }

    @OnClick(R.id.imgClose)
    public void imgCloseClick() {
        dismiss();
    }

    @OnClick(R.id.lnHSCu)
    public void lnHSCuClick(){
        if(CommonActivity.isNullOrEmpty(recordPrepaid.getPathOldProfile())) {
            profileBO.setRecordCode(recordPrepaid.getCode());

            new AsynTaskGetOldProfile(getActivity(), new OnPostExecuteListener<ProfileBO>() {
                @Override
                public void onPostExecute(ProfileBO result, String errorCode, String description) {
                    if (CommonActivity.isNullOrEmpty(result.getFileContent())) {
                        CommonActivity.toast(getActivity(), R.string.old_profile_not_found);
                    } else {
                        //hien thi thong tin ho so cu de nguoi dung chon
                        tvViewProfile.setVisibility(View.VISIBLE);
                        cbSelect.setVisibility(View.VISIBLE);
                        recordPrepaid.setRecordId(result.getRecordId());
                        openFile(result.getFileContent(), result.getFileName());
                    }
                }
            }, moveLogInAct, profileBO).execute();
        } else {
            openFile(recordPrepaid.getPathOldProfile());
        }
    }

    View.OnClickListener moveLogInAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), "");
            dialog.show();
        }
    };

    private void openFile(String fileContent, String fileName){
        Log.d("openFile", "fileName = " + fileName);
        try{
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
            recordPrepaid.setPathOldProfile(filePath);

            openFile(filePath);
        } catch (Exception ex){
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

    @OnClick(R.id.lnHSMoi)
    public void lnHSMoiClick(){
        cbSelect.setChecked(false);
        onSelectNPFListener.OnClick(recordPrepaid);
    }

    public ProfileBO getProfileBO() {
        return profileBO;
    }

    public void setProfileBO(ProfileBO profileBO) {
        this.profileBO = profileBO;
    }

    public RecordPrepaid getRecordPrepaid() {
        return recordPrepaid;
    }

    public void setRecordPrepaid(RecordPrepaid recordPrepaid) {
        this.recordPrepaid = recordPrepaid;
    }
}
