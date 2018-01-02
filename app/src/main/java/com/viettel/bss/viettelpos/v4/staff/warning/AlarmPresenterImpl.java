package com.viettel.bss.viettelpos.v4.staff.warning;

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

import java.util.List;

/**
 * Created by NHAT on 01/06/2017.
 */

public class AlarmPresenterImpl implements AlarmPresenter {
    public static final String TAG = AlarmPresenterImpl.class.getSimpleName();
    private AppCompatActivity mContext;
    private BaseView mView;

    public AlarmPresenterImpl(AppCompatActivity activity, BaseView view) {
        mContext = activity;
        mView = view;
    }

    @Override
    public void requestAlarmForStaff(String staffCode, int rowStart) {
        if (TextUtils.isEmpty(staffCode)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("staffCode", staffCode);
        bundle.putInt("rowStart", rowStart);
        mContext.getSupportLoaderManager().destroyLoader(Constant.LOAD_ALARM_ID);
        mContext.getSupportLoaderManager().initLoader(Constant.LOAD_ALARM_ID, bundle, mCallback);
    }

    private LoaderManager.LoaderCallbacks mCallback = new LoaderManager.LoaderCallbacks<AlarmObj>() {
        @Override
        public Loader onCreateLoader(int id, Bundle bundle) {
            WSLoader<AlarmObj> ws = null;
            if (bundle != null) {
                mView.showProgressDialog();
                String staffCode = bundle.getString("staffCode");
                int rowStart = bundle.getInt("rowStart");
                String envelopeRequest = createEnvelopeRequest(staffCode, rowStart);
                ws = new WSLoader<>(mContext)
                        .setUrl(Constant.BCCS_GW_URL)
                        .setEnvelope(envelopeRequest, 1)
                        .setWSCode("mbccs_getAlarmForStaff")
                        .setResponseListener(mHandleResponse);
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

    private IWSResponseListener<AlarmObj> mHandleResponse = new IWSResponseListener<AlarmObj>() {
        @Override
        public AlarmObj handlerResponse(CommonOutput data) {
            if (data != null && !TextUtils.isEmpty(data.getOriginal())) {
                try {
                    Serializer serializer = new Persister();
                    AlarmObj obj = serializer.read(AlarmObj.class, data.getOriginal(), false);
                    Log.out(TAG, "Original : " + data.getOriginal());
                    return obj;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };

    private String createEnvelopeRequest(String staffCode, int rowStart) {
        StringBuilder result = new StringBuilder();
        result.append("<ws:getAlarmForStaff>");
        result.append("<input>");
        result.append("<token>");
        result.append(Session.getToken());
        result.append("</token>");
        if (!TextUtils.isEmpty(staffCode)) {
            result.append("<staffCode>");
            result.append(staffCode);
            result.append("</staffCode>");
        }
        result.append("<rowStart>");
        result.append(rowStart);
        result.append("</rowStart>");
        result.append("<maxRow>");
        result.append(Constant.LOADMORE_MAX_ROW);
        result.append("</maxRow>");
        result.append("<system>");
        result.append(Constant.SURVEY_DOMAIN);
        result.append("</system>");
        result.append("</input>");
        result.append("</ws:getAlarmForStaff>");
        return result.toString();
    }
}