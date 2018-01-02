package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.ActivityCreateNewRequestHotLine;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReceiveRequestBean;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetListReceiverequestAdapter extends BaseAdapter {
	private final Context mContext;
	private ArrayList<ReceiveRequestBean> arrRequestBeans = new ArrayList<>();
	
	private boolean isUpdate = false;
	
	public GetListReceiverequestAdapter(ArrayList<ReceiveRequestBean> arr, Context context ) {
		this.arrRequestBeans = arr;
		mContext = context;
	}
	
	public GetListReceiverequestAdapter(ArrayList<ReceiveRequestBean> arr, Context context, boolean isUpdate ) {
		this.arrRequestBeans = arr;
		mContext = context;
		this.isUpdate = isUpdate;
	}

	@Override
	public int getCount() {
		if (arrRequestBeans != null) {
			return arrRequestBeans.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		if (arrRequestBeans != null && !arrRequestBeans.isEmpty()) {
			return arrRequestBeans.get(arg0);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	private class ViewHolder {
		TextView txtnamecus;
		TextView txtservice;
		TextView txtsodienthoai;
		TextView txtdatereceive;
		LinearLayout btnmovescreen;
	}

	@Override
	public View getView(int possition, View view, ViewGroup arg2) {
		
		View mView = view;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater layoutInflater = ((Activity) mContext)
					.getLayoutInflater();
			mView = layoutInflater.inflate(R.layout.connection_item_hotline,
					arg2, false);
			holder = new ViewHolder();
			holder.txtnamecus = (TextView) mView
					.findViewById(R.id.txtnamecus);
			holder.txtservice = (TextView) mView.findViewById(R.id.txtservice);
			holder.txtsodienthoai = (TextView) mView.findViewById(R.id.txtsodienthoai);
			holder.txtdatereceive = (TextView) mView.findViewById(R.id.txtdatereceive);
			holder.btnmovescreen = (LinearLayout) mView.findViewById(R.id.btnmovescreen);
			
			if(isUpdate) {
				holder.btnmovescreen.setVisibility(View.GONE);
			}
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		final ReceiveRequestBean receiveRequestBean = arrRequestBeans.get(possition);
		
		
		holder.txtnamecus.setText(receiveRequestBean.getCustomerName());
		holder.txtservice.setText(mContext.getResources().getString(
				R.string.dichvu)
				+ " : " + receiveRequestBean.getTelecomServiceName());
		holder.txtsodienthoai.setText(mContext.getResources().getString(
				R.string.isdncus)
				+ " " + receiveRequestBean.getTel());
		
		holder.txtdatereceive.setText(mContext.getResources().getString(
				R.string.datereq)
				+ " " + receiveRequestBean.getReciveDate());

		holder.btnmovescreen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				Intent intent = new Intent(mContext , ActivityCreateNewRequestHotLine.class);
				bundle.putSerializable("ReceiveRequestBeanKey", receiveRequestBean);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
				
			}
		});
		
	
//		holder.lnupload.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				if(requestBeans.getActionProfileStatus().equalsIgnoreCase("0")){
//					Bundle mbunBundle = new Bundle();
//					FragmentUpLoadImage fragmentUpLoadImage = new FragmentUpLoadImage();
//					mbunBundle.putSerializable("RequestBeanKey", requestBeans);
//					fragmentUpLoadImage.setArguments(mbunBundle);
//					ReplaceFragment.replaceFragment((Activity)mContext, fragmentUpLoadImage, true);
//				}else{
//					CommonActivity.createAlertDialog(mContext,mContext.getString(R.string.requestisupdate),mContext.getString(R.string.app_name)).show();
//				}
//			
//			}
//		});
		
		return mView;
	}
	
	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(mContext, LoginActivity.class);
			mContext.startActivity(intent);
			((Activity)mContext).finish();
			MainActivity.getInstance().finish();

		}
	};
	
}
