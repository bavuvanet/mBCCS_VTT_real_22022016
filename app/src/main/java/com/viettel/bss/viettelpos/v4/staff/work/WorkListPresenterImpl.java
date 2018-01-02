package com.viettel.bss.viettelpos.v4.staff.work;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.viettel.bss.viettelpos.v4.activity.BaseView;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.connection.IWSResponseListener;
import com.viettel.bss.viettelpos.v4.commons.connection.WSLoader;
import com.viettel.bss.viettelpos.v4.utils.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by NHAT on 01/06/2017.
 */

public class WorkListPresenterImpl implements WorkListPresenter {
    public static final String TAG = WorkListPresenterImpl.class.getSimpleName();
    private AppCompatActivity mContext;
    private BaseView mView;

    public WorkListPresenterImpl(AppCompatActivity activity, BaseView view) {
        mContext = activity;
        mView = view;
    }

    @Override
    public void requestWorkList(int rowStart, int status, long fromDate, long toDate, String woCode) {
        Log.out(TAG, "status : " + status + " fromDate : " + fromDate + " todate : " + toDate);
        Bundle bundle = new Bundle();
        bundle.putString("rowStart", rowStart + "");
        bundle.putString("status", status + "");
        bundle.putString("fromDate", fromDate + "");
        bundle.putString("toDate", toDate + "");
        bundle.putString("woCode", woCode);
        mContext.getSupportLoaderManager().destroyLoader(Constant.LOAD_WORK_LIST_ID);
        mContext.getSupportLoaderManager().initLoader(Constant.LOAD_WORK_LIST_ID, bundle, mCallback);
    }

    private LoaderManager.LoaderCallbacks mCallback = new LoaderManager.LoaderCallbacks<WorkListObj>() {
        @Override
        public Loader onCreateLoader(int id, Bundle bundle) {
            WSLoader<WorkListObj> ws = null;
            if (bundle != null) {
                mView.showProgressDialog();
                String status = bundle.getString("status");
                String fromDate = bundle.getString("fromDate");
                String toDate = bundle.getString("toDate");
                String rowStart = bundle.getString("rowStart");
                String woCode = bundle.getString("woCode");
                String envelopeRequest = createEnvelopeRequest(rowStart, status, fromDate, toDate, woCode);
                ws = new WSLoader<>(mContext)
                        .setUrl(Constant.BCCS_GW_URL)
                        .setEnvelope(envelopeRequest, 1)
                        .setWSCode("mbccs_getListWoForOtherSystem")
                        .setResponseListener(mHandleResponse);
                ws.buildRequest();
            }
            return ws;
        }

        @Override
        public void onLoadFinished(Loader loader, WorkListObj data) {
            if (mContext == null || mContext.isFinishing()) {
                return;
            }
            if (data != null) {
                List<WorkListObj.Data> list = data.getData();
                if (list != null) {
                    mView.onSuccess(list);
                } else {
                    mView.onSuccess(null);
                }
            } else {
                mView.onError(null);
            }
            mView.dismissProgressDialog();
        }

        @Override
        public void onLoaderReset(Loader loader) {
        }
    };

    private IWSResponseListener<WorkListObj> mHandleResponse = new IWSResponseListener<WorkListObj>() {
        @Override
        public WorkListObj handlerResponse(CommonOutput data) {
            if (data != null && !TextUtils.isEmpty(data.getOriginal())) {
                try {
                    Serializer serializer = new Persister();
                    WorkListObj obj = serializer.read(WorkListObj.class, data.getOriginal(), false);
                    Log.out(TAG, "Original : " + data.getOriginal());
                    return obj;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };

    private String createEnvelopeRequest(String rowStart, String status, String fromDate, String toDate, String woCode) {
        StringBuilder result = new StringBuilder();
        result.append("<ws:getListWoForOtherSystem>");
        result.append("<input>");
        result.append("<token>");
        result.append(Session.getToken());
        result.append("</token>");
        result.append("<rowStart>");
        result.append(rowStart);
        result.append("</rowStart>");
        result.append("<status>");
        result.append(status);
        result.append("</status>");
        result.append("<fromDate>");
        result.append(fromDate);
        result.append("</fromDate>");
        result.append("<toDate>");
        result.append(toDate);
        result.append("</toDate>");
        result.append("<woCode>");
        result.append(woCode);
        result.append("</woCode>");
        result.append("</input>");
        result.append("</ws:getListWoForOtherSystem>");
        return result.toString();
    }
}