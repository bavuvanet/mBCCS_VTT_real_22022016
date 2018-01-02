package com.viettel.bss.viettelpos.v4.report.asyn;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.report.object.InputBean;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.greenrobot.eventbus.EventBus;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Created by Toancx on 1/6/2017.
 */

public class AsynGetReportMBcss extends AsyncTask<String, Void, ParseOuput> {
    private static final String TAG = "AsynGetReportMBcss";
    private final Activity mActivity;
    private final ProgressDialog progress;
    private final InputBean inputBean;

    public AsynGetReportMBcss(Activity mActivity, InputBean inputBean) {
        this.mActivity = mActivity;
        this.inputBean = inputBean;

        this.progress = new ProgressDialog(mActivity);
        this.progress.setCancelable(false);
        this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
        if (!this.progress.isShowing()) {
            this.progress.show();
        }
    }

    @Override
    protected ParseOuput doInBackground(String... params) {
        return getReportMBcss();
    }

    @Override
    protected void onPostExecute(ParseOuput result) {
        super.onPostExecute(result);

        progress.dismiss();

        if (result.getErrorCode().equals("0")) {
            if(!CommonActivity.isNullOrEmptyArray(result.getLstItemBeans())) {
                EventBus.getDefault().postSticky(result.getLstItemBeans());

//                ReplaceFragment.replaceFragment(mActivity, new FragmentShowReport(), true);
            } else {
                CommonActivity.createAlertDialog(mActivity, mActivity.getResources().getString(R.string.txt_search_invalid_info),
                        mActivity.getResources().getString(R.string.app_name)).show();
            }
        } else {
            if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                Dialog dialog = CommonActivity.createAlertDialog(mActivity, result.getDescription(),
                        mActivity.getResources().getString(R.string.app_name), moveLogInAct);
                dialog.show();
            } else {
                if (result.getDescription() == null || result.getDescription().isEmpty()) {
                    result.setDescription(mActivity.getResources().getString(R.string.checkdes));
                }

                Dialog dialog = CommonActivity.createAlertDialog(mActivity, result.getDescription(),
                        mActivity.getString(R.string.app_name));
                dialog.show();
            }
        }
    }

    private final View.OnClickListener moveLogInAct = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };

    private ParseOuput getReportMBcss() {
        ParseOuput parseOutput = new ParseOuput();
        String original = "";
        try {
            String methodName = "getReportMBcss";
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);

            input.addValidateGateway("wscode", "mbccs_" + methodName);
            StringBuilder rawData = new StringBuilder();

            rawData.append("<ws:").append(methodName).append(">");
            rawData.append("<input>");

            rawData.append("<token>").append(Session.getToken()).append("</token>");
            rawData.append("<fromDate>").append(inputBean.getFromDate()).append("</fromDate>");
            rawData.append("<toDate>").append(inputBean.getToDate()).append("</toDate>");

            if(!CommonActivity.isNullOrEmpty(inputBean.getUserCall())){
                rawData.append("<userCall>").append(inputBean.getUserCall()).append("</userCall>");
            }

            if(!CommonActivity.isNullOrEmpty(inputBean.getInputValue())){
                rawData.append("<inputValue>").append(inputBean.getInputValue()).append("</inputValue>");
            }

            if(!CommonActivity.isNullOrEmpty(inputBean.getResultValue())){
                rawData.append("<resultValue>").append(inputBean.getResultValue()).append("</resultValue>");
            }

            if(!CommonActivity.isNullOrEmpty(inputBean.getMethodName())){
                rawData.append("<methodName>").append(inputBean.getMethodName()).append("</methodName>");
            }

            if(!CommonActivity.isNullOrEmpty(inputBean.getPage())){
                rawData.append("<page>").append(inputBean.getPage()).append("</page>");
            }

            if(!CommonActivity.isNullOrEmpty(inputBean.getCount())){
                rawData.append("<count>").append(inputBean.getCount()).append("</count>");
            }

            rawData.append("</input>");
            rawData.append("</ws:").append(methodName).append(">");

            Log.i(TAG, rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData.toString());

            Log.i(TAG, Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, mActivity,
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
                parseOutput.setDescription(mActivity.getString(R.string.no_return_from_system));
                parseOutput.setErrorCode(Constant.ERROR_CODE);
                return parseOutput;
            } else {
                return parseOutput;
            }
        } catch (Exception e) {
            Log.e(TAG, "getReportMBcss", e);
            parseOutput = new ParseOuput();
            parseOutput.setDescription(e.getMessage());
            parseOutput.setErrorCode(Constant.ERROR_CODE);
        }

        return parseOutput;
    }

}


