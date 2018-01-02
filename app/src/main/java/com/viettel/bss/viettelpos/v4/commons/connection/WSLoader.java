package com.viettel.bss.viettelpos.v4.commons.connection;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.Constant;

public class WSLoader<T> extends AsyncTaskLoader<T> {
    public static final String TAG = WSLoader.class.getSimpleName();
    private String mUrl, mEnvelopeRequest, mWsCode;
    private int mOrdinal;
    private T mResult;
    private IWSResponseListener<T> mListener;
    private BCCSGateway mGateway;

    public WSLoader(Context context) {
        super(context);
    }

    public WSLoader setUrl(String url) {
        mUrl = url;
        return this;
    }

    public WSLoader setEnvelope(String envelopeRequest, int ordinal) {
        mEnvelopeRequest = envelopeRequest;
        mOrdinal = ordinal;
        return this;
    }

    public WSLoader setWSCode(String wsCode) {
        mWsCode = wsCode;
        return this;
    }

    public WSLoader setResponseListener(IWSResponseListener<T> listener) {
        mListener = listener;
        return this;
    }

    public void buildRequest() {
        mGateway = new BCCSGateway();
        mGateway.addValidateGateway("wscode", mWsCode);
        mGateway.addValidateGateway("username", Constant.BCCSGW_USER);
        mGateway.addValidateGateway("password", Constant.BCCSGW_PASS);
        mEnvelopeRequest = mGateway.buildInputGatewayWithRawData(mEnvelopeRequest, mOrdinal, true);
    }

    @Override
    public T loadInBackground() {
        if (!TextUtils.isEmpty(mUrl) && !TextUtils.isEmpty(mWsCode) && !TextUtils.isEmpty(mEnvelopeRequest) && mGateway != null) {
            try {
                String response = mGateway.sendRequest(mEnvelopeRequest, mUrl, getContext(), mWsCode);
                if (mListener == null) {
                    return null;
                } else {
                    return mListener.handlerResponse(mGateway.parseGWResponse(response));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Called when there is new data to deliver to the client. The super class
     * will take care of delivering it; the implementation here just adds a
     * little more logic.
     */
    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            // An async query came in while the loader is stopped. We
            // don't need the result.
//            if (data != null) {
            onReleaseResources(data);
//            }
            return;
        }
        T oldData = mResult;
        mResult = data;
        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(data);
        }
        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldData != null && oldData != data) {
            onReleaseResources(oldData);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            deliverResult(mResult);
        }

        // Start watching for changes in the app data.

        if (takeContentChanged() || mResult == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(T data) {
        super.onCanceled(data);
        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(data);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();
        // Ensure the loader is stopped
        onStopLoading();
        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mResult != null) {
            onReleaseResources(mResult);
            mResult = null;
        }
        // Stop monitoring for changes.
    }

    protected void onReleaseResources(T data) {
        // For a simple List<> there is nothing to do. For something
        // like a Cursor, we would close it here.
//        mResult = data;
    }
}
