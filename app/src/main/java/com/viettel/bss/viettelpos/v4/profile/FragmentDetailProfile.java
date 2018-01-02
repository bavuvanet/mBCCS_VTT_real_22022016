package com.viettel.bss.viettelpos.v4.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.DetailProfileAdapter;
import com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerItemClickListener;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;
import com.viettel.bss.viettelpos.v4.message.ProfileMsg;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Toancx on 12/27/2016.
 */

public class FragmentDetailProfile extends FragmentCommon{

    private static final String TAG = "FragmentDetailProfile";
    @BindView(R.id.btnInfoGeneral)
    Button btnInfoGeneral;
    @BindView(R.id.expanInfoGeneral)
    ExpandableLinearLayout expanInfoGeneral;

    @BindView(R.id.btnLstProfile)
    Button btnLstProfile;
    @BindView(R.id.expanLstProfile)
    ExpandableLinearLayout expanLstProfile;

    @BindView(R.id.tvCustomerName)
    TextView tvCustomerName;
    @BindView(R.id.tvIsdnAccount)
    TextView tvIsdnAccount;
    @BindView(R.id.tvIdNo)
    TextView tvIdNo;
    @BindView(R.id.tvService)
    TextView tvService;
    @BindView(R.id.tvStatusSub)
    TextView tvStatusSub;
    @BindView(R.id.tvStatusProfile)
    TextView tvStatusProfile;
    @BindView(R.id.tvDateActive)
    TextView tvDateActive;
    @BindView(R.id.recyclerViewProfile)
    RecyclerView recyclerViewProfile;

    private ActionProfileBean actionProfileBean;
    private DetailProfileAdapter detailProfileAdapter;
    private RecordBean recordBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.dialog_detail_profile;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void unit(View v) {
        recyclerViewProfile.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewProfile, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick");
                recordBean = detailProfileAdapter.getLstData().get(position);

                if(!CommonActivity.isNullOrEmpty(recordBean.getRecordNameScan())) {
                    if (CommonActivity
                            .isNullOrEmpty(recordBean
                                    .getFilePath())) {
                        AsyntaskDowloadFile dowloadFile = new AsyntaskDowloadFile(
                                getActivity());
                        dowloadFile.execute();
                    } else {
                        openFile(recordBean.getFilePath());
                    }
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void setPermission() {
        permission = Constant.VSAMenu.LOOKUP_PROFILE_TRANSACTION;
    }

    @OnClick(R.id.btnInfoGeneral)
    public void onBtnInfoGeneralClick(){
        expanInfoGeneral.toggle();
        if(expanInfoGeneral.isExpanded()){
            btnInfoGeneral.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_down_float,0,0,0);
        } else {
            btnInfoGeneral.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_up_float,0,0,0);
        }
    }

    @OnClick(R.id.btnLstProfile)
    public void onBtnLstProfileClick(){
        expanLstProfile.toggle();
        if(expanLstProfile.isExpanded()){
            btnLstProfile.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_down_float,0,0,0);
        } else {
            btnLstProfile.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_up_float,0,0,0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageProfile(ProfileMsg msg){
        initInfo(msg.actionProfileBean);
        setTitleActionBar(getString(R.string.title_detail_profile));
        setSubTitleActionBar(msg.actionProfileBean.getCusName());

        detailProfileAdapter = new DetailProfileAdapter(getActivity(), msg.lstRecordBeans, msg.actionProfileBean.getCusName());
        recyclerViewProfile.setAdapter(detailProfileAdapter);

        recyclerViewProfile.setHasFixedSize(true);
        recyclerViewProfile.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initInfo(ActionProfileBean profileBean){
        tvCustomerName.setText(profileBean.getCusName());
        tvIsdnAccount.setText(profileBean.getIsdnAccount());
        tvIdNo.setText(profileBean.getIdNo());
        tvService.setText(profileBean.getSerTypeName());
        tvStatusSub.setText(profileBean.getSubStatus());
        tvStatusProfile.setText(profileBean.getProfileStatus());
        tvDateActive.setText(profileBean.getActionDateStr());
    }

    private class AsyntaskDowloadFile extends AsyncTask<String, Void, String> {
        final ProgressDialog progress;
        private Context context = null;
        final XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        String fileName = "";

        public AsyntaskDowloadFile(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.waitting));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return dowloadInfo();
        }

        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            if (errorCode.equalsIgnoreCase("0")) {
                try {
                    if (!CommonActivity.isNullOrEmpty(result)) {
                        byte[] fileByte = Base64.decode(result, Base64.DEFAULT);
                        recordBean.setFileName(fileName);

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

                        openFile(filePath);
                    } else {
                        Dialog dialog = CommonActivity
                                .createAlertDialog(
                                        getActivity(),
                                        context.getString(R.string.error_download_file_from_profile),
                                        getResources().getString(
                                                R.string.app_name));
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity
                            .createAlertDialog(
                                    getActivity(),
                                    description,
                                    context.getResources().getString(
                                            R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getResources()
                                    .getString(R.string.app_name));
                    dialog.show();

                }
            }
        }

        private String dowloadInfo() {
            String strResult = "";
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getSoftLink");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getSoftLink>");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");
                rawData.append("<strHardLink>").append(recordBean.getRecordPath()).append(recordBean.getRecordNameScan()).append("</strHardLink>");
                rawData.append("<strSymbolicLink>").append(recordBean.getRecordNameScan()).append("</strSymbolicLink>");
                rawData.append("</input>");
                rawData.append("</ws:getSoftLink>");

                Log.i("LOG", "raw data" + rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("LOG", "Send evelop" + envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input
                        .sendRequest(envelope, Constant.BCCS_GW_URL,
                                getActivity(), "mbccs_getSoftLink");
                Log.i("LOG", "Respone:  " + response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("LOG", "Responseeeeeeeeee Original  " + response);

                // parser

                strResult = parserDowloadFile(original);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return strResult;
        }

        public String parserDowloadFile(String original) {
            String strFile = "";
            Document doc = parse.getDomElement(original);
            NodeList nl = doc.getElementsByTagName("return");
            for (int i = 0; i < nl.getLength(); i++) {
                Element e2 = (Element) nl.item(i);
                errorCode = parse.getValue(e2, "errorCode");
                description = parse.getValue(e2, "description");
                Log.d("LOG", "errorCode:  " + errorCode);
                Log.d("LOG", "description: " + description);
                strFile = parse.getValue(e2, "fileContent");
                Log.d("LOG", "fileContent: " + strFile);
                fileName = parse.getValue(e2, "fileName");
            }

            return strFile;
        }

    }

    // open file_start
    private void openFile(String fileName) {
        File file = new File(fileName);
        Log.d(TAG, "Link file: " + fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        String extension = android.webkit.MimeTypeMap
                .getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
        } else {
            intent.setDataAndType(Uri.fromFile(file), mimetype);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent2 = Intent.createChooser(intent, "Open File");
        try {
            startActivity(intent2);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
