package com.viettel.bss.viettelpos.v4.staff.work;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.BaseView;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.connection.IWSResponseListener;
import com.viettel.bss.viettelpos.v4.commons.connection.WSLoader;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

/**
 * Created by NHAT on 01/06/2017.
 */

public class WorkPresenterImpl implements WorkPresenter {
    public static final String TAG = WorkPresenterImpl.class.getSimpleName();
    private AppCompatActivity mContext;
    private BaseView mView;

    public WorkPresenterImpl(AppCompatActivity activity, BaseView view) {
        mContext = activity;
        mView = view;
    }

    @Override
    public void requestUpdateWork(String woId, String status, String reason, String solution, String note) {
        if (TextUtils.isEmpty(woId) || TextUtils.isEmpty(status)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("woid", woId);
        bundle.putString("status", status);
        bundle.putString("reason", reason);
        bundle.putString("solution", solution);
        bundle.putString("note", note);
        mContext.getSupportLoaderManager().destroyLoader(Constant.LOAD_WORK_UPDATE_ID);
        mContext.getSupportLoaderManager().initLoader(Constant.LOAD_WORK_UPDATE_ID, bundle, mUpdateCallback);
    }

    private LoaderManager.LoaderCallbacks mUpdateCallback = new LoaderManager.LoaderCallbacks<WorkListObj>() {
        @Override
        public Loader onCreateLoader(int id, Bundle bundle) {
            WSLoader<WorkListObj> ws = null;
            if (bundle != null) {
                mView.showProgressDialog();
                String woId = bundle.getString("woid");
                String status = bundle.getString("status");
                String reason = bundle.getString("reason");
                String solution = bundle.getString("solution");
                String note = bundle.getString("note");
                String envelopeRequest = createEnvelopeUploadRequest(woId, status, reason, solution, note);
                ws = new WSLoader<>(mContext)
                        .setUrl(Constant.BCCS_GW_URL)
                        .setEnvelope(envelopeRequest, 1)
                        .setWSCode("mbccs_updateWoForOtherSystem")
                        .setResponseListener(mUpdateHandleResponse);
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
                if("0".equals(data.getErrorCode())){
                    mView.onSuccess(data);
                } else {
                    if(!CommonActivity.isNullOrEmpty(data.getDescription())){
                        mView.onError(data.getDescription());
                    } else {
                        mView.onError(R.string.msg_cause_update_fail);
                    }
                }
            } else {
                mView.onError(R.string.msg_cause_update_fail);
            }
            mView.dismissProgressDialog();
        }

        @Override
        public void onLoaderReset(Loader loader) {
        }
    };

    private IWSResponseListener<WorkListObj> mUpdateHandleResponse = new IWSResponseListener<WorkListObj>() {
        @Override
        public WorkListObj handlerResponse(CommonOutput data) {
            if (data != null && !TextUtils.isEmpty(data.getOriginal())) {
                try {
                    Serializer serializer = new Persister();
                    WorkListObj obj = serializer.read(WorkListObj.class, data.getOriginal());
                    return obj;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };

    private String createEnvelopeUploadRequest(String woId, String status, String reason, String solution, String note) {
        StringBuilder result = new StringBuilder();
        result.append("<ws:updateWoForOtherSystem>");
        result.append("<input>");
        result.append("<token>");
        result.append(Session.getToken());
        result.append("</token>");
        result.append("<woid>");
        result.append(woId);
        result.append("</woid>");
        result.append("<status>");
        result.append(status);
        result.append("</status>");
        result.append("<reason>");
        result.append(reason);
        result.append("</reason>");
        result.append("<solution>");
        result.append(solution);
        result.append("</solution>");
        result.append("<note>");
        result.append(note);
        result.append("</note>");
        result.append("</input>");
        result.append("</ws:updateWoForOtherSystem>");
        return result.toString();
    }
}