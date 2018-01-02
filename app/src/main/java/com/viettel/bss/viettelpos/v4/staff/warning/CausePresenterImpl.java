package com.viettel.bss.viettelpos.v4.staff.warning;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.BaseView;
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

public class CausePresenterImpl implements CausePresenter {
    public static final String TAG = CausePresenterImpl.class.getSimpleName();
    private AppCompatActivity mContext;
    private BaseView mView;

    public CausePresenterImpl(AppCompatActivity activity, BaseView view) {
        mContext = activity;
        mView = view;
    }

    @Override
    public void requestCauseForStaff() {
        mContext.getSupportLoaderManager().destroyLoader(Constant.LOAD_CAUSE_ID);
        mContext.getSupportLoaderManager().initLoader(Constant.LOAD_CAUSE_ID, null, mCauseCallback);
    }

    @Override
    public void requestUpdateAlarmForStaff(String alarmId, String causeId, String causeName, String contentResponse) {
        if (TextUtils.isEmpty(alarmId) || TextUtils.isEmpty(causeId) || TextUtils.isEmpty(causeName) || TextUtils.isEmpty(contentResponse)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("alarmId", alarmId);
        bundle.putString("causeId", causeId);
        bundle.putString("causeName", causeName);
        bundle.putString("contentResponse", contentResponse);
        mContext.getSupportLoaderManager().destroyLoader(Constant.LOAD_UPLOAD_ALARM_ID);
        mContext.getSupportLoaderManager().initLoader(Constant.LOAD_UPLOAD_ALARM_ID, bundle, mUpdateCallback);
    }

    private LoaderManager.LoaderCallbacks mUpdateCallback = new LoaderManager.LoaderCallbacks<AlarmObj>() {
        @Override
        public Loader onCreateLoader(int id, Bundle bundle) {
            WSLoader<AlarmObj> ws = null;
            if (bundle != null) {
                mView.showProgressDialog();
                String alarmId = bundle.getString("alarmId");
                String causeId = bundle.getString("causeId");
                String causeName = bundle.getString("causeName");
                String contentResponse = bundle.getString("contentResponse");
                String envelopeRequest = createEnvelopeUploadRequest(alarmId, causeId, causeName, contentResponse);
                ws = new WSLoader<>(mContext)
                        .setUrl(Constant.BCCS_GW_URL)
                        .setEnvelope(envelopeRequest, 1)
                        .setWSCode("mbccs_getAlarmForStaff")
                        .setResponseListener(mUpdateHandleResponse);
                ws.buildRequest();
            }
            return ws;
        }

        @Override
        public void onLoadFinished(Loader loader, AlarmObj data) {
            if (mContext == null || mContext.isFinishing()) {
                return;
            }
            if (data != null) {
                List<AlarmObj.Data> list = data.getData();
                if (list != null && list.size() > 0) {
                    mView.onSuccess(list);
                } else {
                    mView.onError(R.string.msg_cause_update_fail);
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

    private IWSResponseListener<AlarmObj> mUpdateHandleResponse = new IWSResponseListener<AlarmObj>() {
        @Override
        public AlarmObj handlerResponse(CommonOutput data) {
            if (data != null && !TextUtils.isEmpty(data.getOriginal())) {
                try {
                    Serializer serializer = new Persister();
                    AlarmObj obj = serializer.read(AlarmObj.class, data.getOriginal());
                    return obj;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };

    private LoaderManager.LoaderCallbacks mCauseCallback = new LoaderManager.LoaderCallbacks<CauseObj>() {
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            mView.showProgressDialog();
            String envelopeRequest = createEnvelopeCauseRequest();
            WSLoader<CauseObj> ws = new WSLoader<>(mContext)
                    .setUrl(Constant.BCCS_GW_URL)
                    .setEnvelope(envelopeRequest, 1)
                    .setWSCode("mbccs_getCauseForStaff")
                    .setResponseListener(mCauseHandleResponse);
            ws.buildRequest();
            return ws;
        }

        @Override
        public void onLoadFinished(Loader loader, CauseObj data) {
            if (mContext == null || mContext.isFinishing()) {
                return;
            }
            if (data != null) {
                List<CauseObj.Data> list = data.getData();
                if (list != null && list.size() > 0) {
                    mView.onSuccess(list);
                } else {
                    mView.onError(R.string.msg_cause_reason_fail);
                }
            } else {
                mView.onError(R.string.msg_cause_reason_fail);
            }
            mView.dismissProgressDialog();
        }

        @Override
        public void onLoaderReset(Loader loader) {
        }
    };

    private IWSResponseListener<CauseObj> mCauseHandleResponse = new IWSResponseListener<CauseObj>() {
        @Override
        public CauseObj handlerResponse(CommonOutput data) {
            if (data != null && !TextUtils.isEmpty(data.getOriginal())) {
                try {
                    Serializer serializer = new Persister();
                    CauseObj obj = serializer.read(CauseObj.class, data.getOriginal());
                    return obj;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };

    private String createEnvelopeUploadRequest(String alarmID, String causeID, String causeName, String contentResponse) {
        StringBuilder result = new StringBuilder();
        result.append("<ws:getAlarmForStaff>");
        result.append("<input>");
        result.append("<token>");
        result.append(Session.getToken());
        result.append("</token>");
        result.append("<alarmId>");
        result.append(alarmID);
        result.append("</alarmId>");
        result.append("<causeId>");
        result.append(causeID);
        result.append("</causeId>");
        result.append("<causeName>");
        result.append(causeName);
        result.append("</causeName>");
        result.append("<contentResponse>");
        result.append(contentResponse);
        result.append("</contentResponse>");
        result.append("<system>");
        result.append(Constant.SURVEY_DOMAIN);
        result.append("</system>");
        result.append("</input>");
        result.append("</ws:getAlarmForStaff>");
        return result.toString();
    }

    private String createEnvelopeCauseRequest() {
        StringBuilder result = new StringBuilder();
        result.append("<ws:getCauseForStaff>");
        result.append("<input>");
        result.append("<token>");
        result.append(Session.getToken());
        result.append("</token>");
        result.append("<system>");
        result.append(Constant.SURVEY_DOMAIN);
        result.append("</system>");
        result.append("</input>");
        result.append("</ws:getCauseForStaff>");
        return result.toString();
    }
}