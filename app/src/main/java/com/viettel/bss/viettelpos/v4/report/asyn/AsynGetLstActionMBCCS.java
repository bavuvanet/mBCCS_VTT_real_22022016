package com.viettel.bss.viettelpos.v4.report.asyn;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.AutocompleteAdapter;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toancx on 1/7/2017.
 */

public class AsynGetLstActionMBCCS extends AsyncTask<String, Void, ParseOuput> {
    private static final String TAG = "AsynGetLstActionMBCCS";
    private final Activity mActivity;
    private final ProgressDialog progress;
    private final Object objControl;

    public AsynGetLstActionMBCCS(Activity mActivity, Object objControl) {
        this.mActivity = mActivity;
        this.objControl = objControl;
        this.progress = new ProgressDialog(mActivity);
        this.progress.setCancelable(false);
        this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
        if (!this.progress.isShowing()) {
            this.progress.show();
        }

    }

    @Override
    protected ParseOuput doInBackground(String... params) {
        return getLstActionMBCCS();
    }

    @Override
    protected void onPostExecute(ParseOuput result) {
        super.onPostExecute(result);

        progress.dismiss();

        if (result.getErrorCode().equals("0")) {
            if(!CommonActivity.isNullOrEmptyArray(result.getLstSpin())) {
                if(objControl instanceof Spinner) {
                    List<Spin> lstSpin = new ArrayList<>();
                    Spin spin = new Spin();
                    spin.setName(mActivity.getString(R.string.select_action_type));
                    spin.setCode("-1");

                    lstSpin.add(spin);
                    lstSpin.addAll(result.getLstSpin());
                    Utils.setDataSpinner(mActivity, lstSpin, (Spinner) objControl);
                } else if(objControl instanceof AutoCompleteTextView){
                    AutocompleteAdapter<Spin> adapter = new AutocompleteAdapter<>(mActivity, result.getLstSpin());
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ((AutoCompleteTextView) objControl).setAdapter(adapter);
                    ((AutoCompleteTextView) objControl).setThreshold(1);
                    ((AutoCompleteTextView) objControl).setDropDownWidth(mActivity.getResources().getDisplayMetrics().widthPixels);
                }
            } else {
                Dialog dialog = CommonActivity.createAlertDialog(mActivity, mActivity.getResources().getString(R.string.txt_search_invalid_info),
                        mActivity.getResources().getString(R.string.app_name), moveLogInAct);
                dialog.show();
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

    private ParseOuput getLstActionMBCCS() {
        ParseOuput parseOutput = new ParseOuput();
        String original = "";
        try {
            String methodName = "getLstActionMBCCS";
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);

            input.addValidateGateway("wscode", "mbccs_" + methodName);
            StringBuilder rawData = new StringBuilder();

            rawData.append("<ws:").append(methodName).append(">");
            rawData.append("<input>");

            rawData.append("<token>").append(Session.getToken()).append("</token>");

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
            Log.e(TAG, "getLstActionMBCCS", e);
            parseOutput = new ParseOuput();
            parseOutput.setDescription(e.getMessage());
            parseOutput.setErrorCode(Constant.ERROR_CODE);
        }

        return parseOutput;
    }

    private final View.OnClickListener moveLogInAct = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }
    };
}
