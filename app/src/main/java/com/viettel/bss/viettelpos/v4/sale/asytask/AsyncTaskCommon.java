package com.viettel.bss.viettelpos.v4.sale.asytask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public abstract class AsyncTaskCommon<X, Y, Z> extends AsyncTask<X, Y, Z> {

	protected Activity mActivity = null;
	protected final OnPostExecuteListener<Z> listener;
	protected final OnClickListener moveLogInAct;
	protected final XmlDomParse parse = new XmlDomParse();
	protected String errorCode = "";
	protected String description = "";
	protected String message;
	protected ProgressDialog progress;
	protected View prb;

	AsyncTaskCommon(Activity context, OnPostExecuteListener<Z> listener,
			OnClickListener moveLogInAct, View prb) {
		this.mActivity = context;
		this.listener = listener;
		this.moveLogInAct = moveLogInAct;

		this.prb = prb;
		// check font

		prb.setVisibility(View.VISIBLE);

	}

	public AsyncTaskCommon(Activity context, OnPostExecuteListener<Z> listener,
			OnClickListener moveLogInAct) {
		this.mActivity = context;
		this.listener = listener;
		this.moveLogInAct = moveLogInAct;

		// check font
		this.progress = new ProgressDialog(context);
		this.progress.setMessage(context.getResources().getString(
				R.string.processing));
		this.progress.setCancelable(false);
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}

	public AsyncTaskCommon(Activity context, OnPostExecuteListener<Z> listener,
			OnClickListener moveLogInAct, String message) {
		this.mActivity = context;
		this.listener = listener;
		this.moveLogInAct = moveLogInAct;

		// check font

		this.progress = new ProgressDialog(context);
		this.progress.setMessage(message);
		this.progress.setCancelable(false);
		if (!this.progress.isShowing()) {
			this.progress.show();

		}
	}

	@Override
	protected void onPostExecute(Z result) {
		super.onPostExecute(result);
		if (prb != null) {
			prb.setVisibility(View.GONE);
		}
		if (progress != null) {
			progress.dismiss();
		}

		if ("0".equals(errorCode)) {
            if (progress != null) {
                progress.dismiss();
            }
			listener.onPostExecute(result, errorCode, description);
		} else {
			if (errorCode.equals(Constant.INVALID_TOKEN2)) {
				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
						description,
						mActivity.getResources().getString(R.string.app_name),
						moveLogInAct);
				dialog.show();
			} else {
				if (description == null || description.isEmpty()) {
					description = mActivity.getString(R.string.checkdes);
				}
				if (description != null && description.contains("java.lang.String.length()")) {
					description = mActivity.getString(R.string.server_time_out);
				}
				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
						description,
						mActivity.getResources().getString(R.string.app_name));
				dialog.show();
			}
		}
	}
}
