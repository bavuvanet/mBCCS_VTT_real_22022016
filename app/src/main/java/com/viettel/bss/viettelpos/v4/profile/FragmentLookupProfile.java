package com.viettel.bss.viettelpos.v4.profile;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.LookupProfileAdapter;
import com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerItemClickListener;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;
import com.viettel.bss.viettelpos.v4.message.ProfileMsg;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.greenrobot.eventbus.EventBus;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLookupProfile extends FragmentCommon {

    @BindView(R.id.edtIdNo)
    EditText edtIdNo;
    @BindView(R.id.edtContractNo)
    EditText edtContractNo;
    @BindView(R.id.edtIsdnOrAccount)
    EditText edtIsdnOrAccount;
    @BindView(R.id.edtFromDate)
    EditText edtFromDate;
    @BindView(R.id.edtToDate)
    EditText edtToDate;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.recyclerViewTrans)
    RecyclerView recyclerViewTrans;

    private LookupProfileAdapter profileAdapter;
    private ActionProfileBean actionProfileBean;
    private final String TAG = "FragmentLookupProfile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.fragment_lookup_profile;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void unit(View v) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        edtFromDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(), "dd/MM/yyyy"));
        edtToDate.setText(DateTimeUtils.convertDateTimeToString(cal.getTime(), "dd/MM/yyyy"));

        recyclerViewTrans.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewTrans, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick");
                actionProfileBean = profileAdapter.getLstData().get(position);
                new AsyGetRecordByActionProfileId(getActivity()).execute();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void setPermission() {

    }

    @OnClick(R.id.btnSearch)
    public void lookupProfile() {
        recyclerViewTrans.setVisibility(View.GONE);
        if(!validateLookupProfile()){
            return;
        }
        new AsyGetActionProfileForMBCCS(getActivity()).execute();
    }

    private boolean validateLookupProfile(){
        if(CommonActivity.isNullOrEmpty(edtContractNo.getText().toString())
                && CommonActivity.isNullOrEmpty(edtIdNo.getText().toString())
                && CommonActivity.isNullOrEmpty(edtIsdnOrAccount.getText().toString())){
            Dialog dialog = CommonActivity.createAlertDialog(getActivity(), getResources().getString(R.string.notify_lookup_input_required),
                    getResources().getString(R.string.app_name));
            dialog.show();
            return false;
        }

        return validateDate();
    }

    private boolean validateDate() {
        java.util.Calendar calFromDate = java.util.Calendar.getInstance();
        calFromDate.setTime(DateTimeUtils.convertStringToTime(
                edtFromDate.getText().toString(), "dd/MM/yyyy"));

        java.util.Calendar calToDate = java.util.Calendar.getInstance();
        calToDate.setTime(DateTimeUtils.convertStringToTime(edtToDate
                .getText().toString(), "dd/MM/yyyy"));

        if (calFromDate.getTime().after(calToDate.getTime())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.checktimeupdatejob),
                    getString(R.string.app_name)).show();
            return false;
        }

        int DURATION_MAX = 31;
        if (DateTimeUtils.calculateDays2Date(calFromDate.getTime(),
                calToDate.getTime()) > DURATION_MAX) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.duration_over_load, DURATION_MAX),
                    getString(R.string.app_name)).show();
            return false;
        }

        return true;
    }

    @OnClick(R.id.edtFromDate)
    public void onClickFromDate() {
        CommonActivity.showDatePickerDialog(getActivity(), edtFromDate);
    }


    @OnClick(R.id.edtToDate)
    public void onClickToDate() {
        CommonActivity.showDatePickerDialog(getActivity(), edtToDate);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.title_lookup_profile_transaction);
    }

    private class AsyGetActionProfileForMBCCS extends AsyncTask<String, Void, ParseOuput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyGetActionProfileForMBCCS(Activity mActivity) {

            this.mActivity = mActivity;

            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ParseOuput doInBackground(String... params) {
            return getActionProfileForMBCCS();
        }

        @Override
        protected void onPostExecute(ParseOuput result) {
            super.onPostExecute(result);
            progress.dismiss();

            if (result.getErrorCode().equals("0")) {
                if (CommonActivity.isNullOrEmpty(result.getLstActionProfileBeans())) {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), mActivity.getResources().getString(R.string.txt_search_invalid_info),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                    return;
                }
                recyclerViewTrans.setVisibility(View.VISIBLE);
                recyclerViewTrans.setHasFixedSize(true);
                recyclerViewTrans.setLayoutManager(new LinearLayoutManager(getActivity()));

                profileAdapter = new LookupProfileAdapter(getActivity(), result.getLstActionProfileBeans());
                recyclerViewTrans.setAdapter(profileAdapter);
            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity, result.getDescription(),
                            mActivity.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (result.getDescription() == null || result.getDescription().isEmpty()) {
                        result.setDescription(mActivity.getResources().getString(R.string.checkdes));
                    }

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }

        }

        private ParseOuput getActionProfileForMBCCS() {
            ParseOuput parseOutput = new ParseOuput();
            String original = "";
            try {
                String methodName = "getActionProfileForMBCCS";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");

                if (!CommonActivity.isNullOrEmpty(edtIdNo.getText().toString())) {
                    rawData.append("<idNo>").append(StringUtils.escapeHtml(edtIdNo.getText().toString().trim())).append("</idNo>");
                }

                if (!CommonActivity.isNullOrEmpty(edtIsdnOrAccount.getText().toString())) {
                    if(StringUtils.isViettelMobile(StringUtils.formatMsisdn(edtIsdnOrAccount.getText().toString().trim()))){
                        rawData.append("<isdnAccount>").append(StringUtils.formatIsdn(edtIsdnOrAccount.getText().toString().trim())).append("</isdnAccount>");
                    } else {
                        rawData.append("<isdnAccount>").append(StringUtils.escapeHtml(edtIsdnOrAccount.getText().toString().trim())).append("</isdnAccount>");
                    }
                }

                if (!CommonActivity.isNullOrEmpty(edtContractNo.getText().toString())) {
                    rawData.append("<contractNo>").append(StringUtils.escapeHtml(edtContractNo.getText().toString().trim())).append("</contractNo>");
                }

                rawData.append("<fromDate>").append(StringUtils.escapeHtml(edtFromDate.getText().toString())).append("</fromDate>");
                rawData.append("<toDate>").append(StringUtils.escapeHtml(edtToDate.getText().toString())).append("</toDate>");

                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");

                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());

                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_" + methodName);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                // parser
                Serializer serializer = new Persister();
                parseOutput = serializer.read(ParseOuput.class, original);
                if (parseOutput == null) {
                    parseOutput = new ParseOuput();
                    parseOutput.setDescription(getString(R.string.no_return_from_system));
                    parseOutput.setErrorCode(Constant.ERROR_CODE);
                    return parseOutput;
                } else {
                    return parseOutput;
                }
            } catch (Exception e) {
                Log.e(Constant.TAG, "AsyGetActionProfileForMBCCS", e);
                parseOutput = new ParseOuput();
                parseOutput.setDescription(e.getMessage());
                parseOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return parseOutput;
        }

    }

    private class AsyGetRecordByActionProfileId extends AsyncTask<String, Void, ParseOuput> {

        private final Activity mActivity;
        final ProgressDialog progress;

        public AsyGetRecordByActionProfileId(Activity mActivity) {

            this.mActivity = mActivity;

            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ParseOuput doInBackground(String... params) {
            return getRecordByActionProfileId();
        }

        @Override
        protected void onPostExecute(ParseOuput result) {
            super.onPostExecute(result);
            progress.dismiss();

            if (result.getErrorCode().equals("0")) {
                if (CommonActivity.isNullOrEmpty(result.getLstRecordBeans())) {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), mActivity.getResources().getString(R.string.txt_search_invalid_info),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                    return;
                }

                FragmentDetailProfile fragment = new FragmentDetailProfile();
                ReplaceFragment.replaceFragment(getActivity(), fragment,
                        true);

                ProfileMsg msg = new ProfileMsg(actionProfileBean, result.getLstRecordBeans());
                EventBus.getDefault().postSticky(msg);
            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity, result.getDescription(),
                            mActivity.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                } else {
                    if (result.getDescription() == null || result.getDescription().isEmpty()) {
                        result.setDescription(mActivity.getResources().getString(R.string.checkdes));
                    }

                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), result.getDescription(),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
            }

        }

        private ParseOuput getRecordByActionProfileId() {
            ParseOuput parseOutput = new ParseOuput();
            String original = "";
            try {
                String methodName = "getRecordByActionProfileId";
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_" + methodName);
                StringBuilder rawData = new StringBuilder();

                rawData.append("<ws:").append(methodName).append(">");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");

                rawData.append("<actionProfileId>").append(actionProfileBean.getActionProfileId()).append("</actionProfileId>");

                rawData.append("</input>");
                rawData.append("</ws:").append(methodName).append(">");

                Log.i(TAG, rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());

                Log.i(TAG, Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_" + methodName);
                Log.i(TAG, response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i(TAG, original);

                // parser
                Serializer serializer = new Persister();
                parseOutput = serializer.read(ParseOuput.class, original);
                if (parseOutput == null) {
                    parseOutput = new ParseOuput();
                    parseOutput.setDescription(getString(R.string.no_return_from_system));
                    parseOutput.setErrorCode(Constant.ERROR_CODE);
                    return parseOutput;
                } else {
                    return parseOutput;
                }
            } catch (Exception e) {
                Log.e(TAG, "AsyGetRecordByActionProfileId", e);
                parseOutput = new ParseOuput();
                parseOutput.setDescription(e.getMessage());
                parseOutput.setErrorCode(Constant.ERROR_CODE);
            }

            return parseOutput;
        }

    }
}
