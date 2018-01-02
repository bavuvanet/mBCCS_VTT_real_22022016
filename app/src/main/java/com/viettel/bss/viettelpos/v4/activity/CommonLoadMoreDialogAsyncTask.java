package com.viettel.bss.viettelpos.v4.activity;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

public class CommonLoadMoreDialogAsyncTask<T> extends AsyncTask<String, Void, ArrayList<T>> {
	
	private Activity mActivity = null;
	private CommonLoadMoreDialog<T> commonLoadMoreDialog;
	private ArrayAdapter<T> adapter;
	private String[] dataArgs;
	protected int pageSize;
	protected String errorCode = "";
	protected String description = "";
	private ProgressDialog progress;


	public CommonLoadMoreDialogAsyncTask(Activity context, int pageSize, ArrayAdapter<T> adapter, String... args) {
		this.mActivity = context;
		this.pageSize = pageSize;
		this.adapter = adapter;
		this.dataArgs = args;
		this.progress = new ProgressDialog(mActivity);
		this.progress.setCancelable(false);
		this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
		if (!this.progress.isShowing()) {
			this.progress.show();
		}
	}
	
	public void setCommonLoadMoreDialog(CommonLoadMoreDialog<T> commonLoadMoreDialog) {
		this.commonLoadMoreDialog = commonLoadMoreDialog;
	}

	@Override
	protected ArrayList<T> doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<T> result) {
		//prb_contract.setVisibility(View.GONE);
		progress.dismiss();
		if ("0".equals(errorCode)) {

			if (result != null && result.size() > 0) {
				commonLoadMoreDialog.addToList(result);
				//initContract();
				//Code thua?????
//				if (commonLoadMoreDialog.validateNotShowing()) {
//					commonLoadMoreDialog.showDiaLogLoadMoreContract();
//				}
				adapter.notifyDataSetChanged();
				commonLoadMoreDialog.completeLoad();
			} 
//			else {
//				if (arrTractBeans != null && arrTractBeans.size() > 0) {
//					arrTractBeans.clear();
//				}
//				initContract();
//			}
		} else {
			if (errorCode.equals(Constant.INVALID_TOKEN2)) {

				Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
						mActivity.getResources().getString(R.string.app_name), commonLoadMoreDialog.getMoveLogInAct());
				dialog.show();
			} else {
				if (description == null || description.isEmpty()) {
					description = mActivity.getString(R.string.checkdes);
				}
				Dialog dialog = CommonActivity.createAlertDialog(mActivity, description,
						mActivity.getResources().getString(R.string.app_name));
				dialog.show();
				commonLoadMoreDialog.completeLoad();
			}
		}
	}
}
