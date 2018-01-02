package com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

public abstract class AsyncTaskCommonSupper<X, Y, Z> extends AsyncTask<X, Y, Z> {

	protected Activity mActivity = null;
	protected OnPostExecuteListener<Z> listener;
	protected OnClickListener moveLogInAct;
	protected XmlDomParse parse = new XmlDomParse();
	protected String errorCode = "";
	protected String description = "";
	protected ProgressDialog progress;
	protected View prb;

	public AsyncTaskCommonSupper(Activity context, OnPostExecuteListener<Z> listener,
			OnClickListener moveLogInAct, View prb) {
		this.mActivity = context;
		this.listener = listener;
		this.moveLogInAct = moveLogInAct;
		this.prb = prb;

		// check font
		prb.setVisibility(View.VISIBLE);
	}

	public AsyncTaskCommonSupper(Activity context, OnPostExecuteListener<Z> listener,
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

	@Override
	protected void onPostExecute(Z result) {
		super.onPostExecute(result);
		if (prb != null) {
			prb.setVisibility(View.GONE);
		}
		if (progress != null) {
			progress.dismiss();
		}

//		if (errorCode.equals("0")) {
			listener.onPostExecute(result, errorCode, description);
//		} else {
//			if (errorCode.equals(Constant.INVALID_TOKEN2)) {
//				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
//						description,
//						mActivity.getResources().getString(R.string.app_name),
//						moveLogInAct);
//				dialog.show();
//			} else {
//				if (description == null || description.isEmpty()) {
//					description = mActivity.getString(R.string.checkdes);
//				}
//				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
//						description,
//						mActivity.getResources().getString(R.string.app_name));
//				dialog.show();
//			}
//		}
	}
}
