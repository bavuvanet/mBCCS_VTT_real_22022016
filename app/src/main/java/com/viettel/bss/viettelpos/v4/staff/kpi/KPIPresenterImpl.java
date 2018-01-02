package com.viettel.bss.viettelpos.v4.staff.kpi;

import com.viettel.bss.viettelpos.v4.activity.BaseView;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.connection.IWSResponseListener;
import com.viettel.bss.viettelpos.v4.commons.connection.WSLoader;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by NHAT on 01/06/2017.
 */

public class KPIPresenterImpl implements KPIPresenter {
    public static final String TAG = KPIPresenterImpl.class.getSimpleName();
    private AppCompatActivity mContext;
    private BaseView mView;

    public KPIPresenterImpl(AppCompatActivity activity, BaseView view) {
        mContext = activity;
        mView = view;
    }

    @Override
    public void requestKpiForStaff(String staffCode, String month, String kpiCode, int rowStart) {
        if (TextUtils.isEmpty(month)) {
            return;
        }
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(staffCode)) {
            bundle.putString("staffCode", staffCode);
        }
        bundle.putString("month", month);
        if (!TextUtils.isEmpty(kpiCode)) {
            bundle.putString("kpiCode", kpiCode);
        }
        bundle.putInt("rowStart", rowStart);
        mContext.getSupportLoaderManager().destroyLoader(Constant.LOAD_KPI_ID);
        mContext.getSupportLoaderManager().initLoader(Constant.LOAD_KPI_ID, bundle, mCallback);
    }

    private LoaderManager.LoaderCallbacks mCallback = new LoaderManager.LoaderCallbacks<KPIObj>() {
        @Override
        public Loader onCreateLoader(int id, Bundle bundle) {
            WSLoader<KPIObj> ws = null;
            if (bundle != null) {
                mView.showProgressDialog();
                String staffCode = bundle.getString("staffCode");
                String month = bundle.getString("month");
                String kpiCode = bundle.getString("kpiCode");
                int rowStart = bundle.getInt("rowStart");
                String envelopeRequest = createEnvelopeRequest(staffCode, month, kpiCode, rowStart);
                ws = new WSLoader<>(mContext)
                        .setUrl(Constant.BCCS_GW_URL)
                        .setWSCode("mbccs_getKpiForStaff")
                        .setEnvelope(envelopeRequest, 1)
                        .setResponseListener(mHandleResponse);
                ws.buildRequest();
            }
            return ws;
        }

        @Override
        public void onLoadFinished(Loader loader, KPIObj data) {
            if(mContext == null || mContext.isFinishing()){
                return;
            }
            if (data != null) {
                List<KPIObj.Data> list = data.getData();
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

    private IWSResponseListener<KPIObj> mHandleResponse = new IWSResponseListener<KPIObj>() {
        @Override
        public KPIObj handlerResponse(CommonOutput data) {
            if (data != null && !TextUtils.isEmpty(data.getOriginal())) {
                try {
                    Serializer serializer = new Persister();
                    KPIObj obj = serializer.read(KPIObj.class, data.getOriginal());
                    return obj;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    };

    private String createEnvelopeRequest(String staffCode, String month, String kpiCode, int rowStart) {
        StringBuilder result = new StringBuilder();
        result.append("<ws:getKpiForStaff>");
        result.append("<input>");
        result.append("<token>");
        result.append(Session.getToken());
        result.append("</token>");
        if (!TextUtils.isEmpty(staffCode)) {
            result.append("<staffCode>");
            result.append(staffCode);
            result.append("</staffCode>");
        }
        result.append("<month>");
        result.append(month);
        result.append("</month>");
        if (!TextUtils.isEmpty(kpiCode)) {
            result.append("<kpiCode>");
            result.append(kpiCode);
            result.append("</kpiCode>");
        }
        result.append("<kpiCode>");
        result.append("");
        result.append("</kpiCode>");
        result.append("<rowStart>");
        result.append(rowStart);
        result.append("</rowStart>");
        result.append("<maxRow>");
        result.append(Constant.LOADMORE_MAX_ROW);
        result.append("</maxRow>");
        result.append("</input>");
        result.append("</ws:getKpiForStaff>");
        return result.toString();
    }
}