package com.viettel.bss.viettelpos.v4.report.asyn;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerItemClickListener;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.listener.OnLoadMoreListener;
import com.viettel.bss.viettelpos.v4.report.adapter.LogMBCCSAdapter;
import com.viettel.bss.viettelpos.v4.report.object.InputBean;
import com.viettel.bss.viettelpos.v4.report.object.LogMethodBean;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Created by Toancx on 1/8/2017.
 */

public class AsynLookupLogMBCCS extends AsyncTask<String, Void, ParseOuput> {
    private static final String TAG = "AsynGetLstActionMBCCS";
    private final Activity mActivity;
    private ProgressDialog progress;
    private final InputBean inputBean;
    private boolean loadMore = false;
    private final RecyclerView recyclerView;
    private final TextView tvTotalRecord;
    private LogMBCCSAdapter logMBCCSAdapter;
    private final OnItemClickListener onItemClickListener;

    public AsynLookupLogMBCCS(Fragment fragment,
                              RecyclerView recyclerView,
                              TextView tvRecordTotal,
                              InputBean inputBean,
                              OnItemClickListener onItemClickListener) {
        this.mActivity = fragment.getActivity();
        this.recyclerView = recyclerView;
        this.inputBean = inputBean;
        this.tvTotalRecord = tvRecordTotal;
        this.onItemClickListener = onItemClickListener;

        this.progress = new ProgressDialog(mActivity);
        this.progress.setCancelable(false);
        this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
        if (!this.progress.isShowing()) {
            this.progress.show();
        }
    }

    private AsynLookupLogMBCCS(Activity mActivity,
                               RecyclerView recyclerView,
                               TextView tvRecordTotal,
                               InputBean inputBean,
                               LogMBCCSAdapter logMBCCSAdapter,
                               OnItemClickListener onItemClickListener,
                               boolean loadMore) {
        this.mActivity = mActivity;
        this.recyclerView = recyclerView;
        this.inputBean = inputBean;
        this.tvTotalRecord = tvRecordTotal;
        this.loadMore = loadMore;
        this.onItemClickListener = onItemClickListener;
        this.logMBCCSAdapter = logMBCCSAdapter;

        if (!loadMore) {
            this.progress = new ProgressDialog(mActivity);
            this.progress.setCancelable(false);
            this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }
    }

    @Override
    protected ParseOuput doInBackground(String... params) {
        return lookupLogMBCCS();
    }

    @Override
    protected void onPostExecute(ParseOuput result) {
        super.onPostExecute(result);

        if (!loadMore) {
            progress.dismiss();
        }

        if (result.getErrorCode().equals("0")) {
            if (!loadMore) {
                if (!CommonActivity.isNullOrEmptyArray(result.getLstLogMethodBeans())) {
                    logMBCCSAdapter = new LogMBCCSAdapter(mActivity, result.getLstLogMethodBeans(), recyclerView);
                    logMBCCSAdapter.setOnLoadMoreListener(onLoadMoreListener);

                    recyclerView.setAdapter(logMBCCSAdapter);
                    recyclerView.setVisibility(View.VISIBLE);

                    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mActivity, recyclerView, new com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Log.d(TAG, "onItemClick position = " + position);
                            LogMethodBean logMethodBean = logMBCCSAdapter.getLstData().get(position);
                            if (onItemClickListener != null) {
                                onItemClickListener.onItemClick(logMethodBean);
                            }
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {

                        }
                    }));

                    tvTotalRecord.setText(mActivity.getString(R.string.txt_total_record, StringUtils.formatMoney(String.valueOf(result.getTotalRecord()))));
                    tvTotalRecord.setVisibility(View.VISIBLE);
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity, mActivity.getResources().getString(R.string.txt_search_invalid_info),
                            mActivity.getResources().getString(R.string.app_name), moveLogInAct);
                    dialog.show();
                }
            } else {

                if (!CommonActivity.isNullOrEmptyArray(result.getLstLogMethodBeans())) {
                    Log.d(TAG, "lstLogMethodBean size = " + result.getLstLogMethodBeans().size());

                    logMBCCSAdapter.getLstData().remove(logMBCCSAdapter.getLstData().size() - 1);
                    logMBCCSAdapter.notifyItemRemoved(logMBCCSAdapter.getLstData().size());

                    for (LogMethodBean logMethodBean : result.getLstLogMethodBeans()) {
                        logMBCCSAdapter.getLstData().add(logMethodBean);
                    }

                    logMBCCSAdapter.notifyDataSetChanged();

                    if (result.getLstLogMethodBeans().size() == logMBCCSAdapter.getCount()) {
                        logMBCCSAdapter.setLoaded();
                    }
                }
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

    Integer page = 1;
    Integer count = 10;

    private final OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {

        @Override
        public void onLoadMore() {

            Log.d(TAG, "onLoadMore with page = " + page + ", count = " + count);
            logMBCCSAdapter.getLstData().add(null);
            logMBCCSAdapter.notifyItemInserted(logMBCCSAdapter.getLstData().size() - 1);

            inputBean.setPage(page);
            inputBean.setCount(count);

            new AsynLookupLogMBCCS(mActivity, recyclerView, tvTotalRecord, inputBean, logMBCCSAdapter, onItemClickListener, true).execute();

        }
    };

    private ParseOuput lookupLogMBCCS() {
        ParseOuput parseOutput = new ParseOuput();
        String original = "";
        try {
            String methodName = "lookupLogMBCCS";
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

            if (!CommonActivity.isNullOrEmpty(inputBean.getUserCall())) {
                rawData.append("<userCall>").append(inputBean.getUserCall()).append("</userCall>");
            }

            if (!CommonActivity.isNullOrEmpty(inputBean.getInputValue())) {
                rawData.append("<inputValue>").append(inputBean.getInputValue()).append("</inputValue>");
            }

            if (!CommonActivity.isNullOrEmpty(inputBean.getResultValue())) {
                rawData.append("<resultValue>").append(inputBean.getResultValue()).append("</resultValue>");
            }

            if (!CommonActivity.isNullOrEmpty(inputBean.getMethodName())) {
                rawData.append("<methodName>").append(inputBean.getMethodName()).append("</methodName>");
            }

            if (!CommonActivity.isNullOrEmpty(inputBean.getPage())) {
                rawData.append("<page>").append(inputBean.getPage()).append("</page>");
            }

            if (!CommonActivity.isNullOrEmpty(inputBean.getCount())) {
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
