package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentUpLoadImage;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.RequestBeans;

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

public class GetListRequestAdapter extends BaseAdapter {
	private final Context mContext;
	private ArrayList<RequestBeans> arrRequestBeans = new ArrayList<>();
	
	public interface  OnCancelRequest{
		void onCancelRequest(RequestBeans requestBeans);
	}
	private final OnCancelRequest onCancelRequest;
	public GetListRequestAdapter(ArrayList<RequestBeans> arr, Context context , OnCancelRequest onCancelRequest) {
		this.arrRequestBeans = arr;
		mContext = context;
		this.onCancelRequest = onCancelRequest;
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
		TextView txtrequestId;
		TextView txtservice;
		TextView txtAccount;
		TextView txtdate;
		TextView txttrangthai;
		LinearLayout lndelete;
		LinearLayout lnupload;
		TextView txtscan;
	}

	@Override
	public View getView(int possition, View view, ViewGroup arg2) {
		
		View mView = view;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater layoutInflater = ((Activity) mContext)
					.getLayoutInflater();
			mView = layoutInflater.inflate(R.layout.connection_details_item,
					arg2, false);
			holder = new ViewHolder();
			holder.txtrequestId = (TextView) mView
					.findViewById(R.id.txtrequestId);
			holder.txtservice = (TextView) mView.findViewById(R.id.txtservice);
			holder.txtAccount = (TextView) mView.findViewById(R.id.txtAccount);
			holder.txtdate = (TextView) mView.findViewById(R.id.txtdate);
			holder.txttrangthai = (TextView) mView
					.findViewById(R.id.txttrangthai);
			holder.lndelete = (LinearLayout) mView.findViewById(R.id.btndelete);
			holder.lnupload = (LinearLayout) mView.findViewById(R.id.btnupload);
			holder.txtscan = (TextView) mView.findViewById(R.id.txtscan);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		final RequestBeans requestBeans = arrRequestBeans.get(possition);
		
		
		holder.txtrequestId.setText(mContext.getResources().getString(
				R.string.reqId)
				+ " " + requestBeans.getIdRequest());
		holder.txtservice.setText(mContext.getResources().getString(
				R.string.dichvu)
				+ " : " + requestBeans.getServiceRequest());
		holder.txtAccount.setText(mContext.getResources().getString(
				R.string.taikhoan)
				+ " : " + requestBeans.getAccount());
		
		holder.txtdate.setText(mContext.getResources().getString(
				R.string.datereq)
				+ " " + requestBeans.getDateRequest());
		holder.txttrangthai.setText(mContext.getResources().getString(
				R.string.statusRe)
				+ " " + requestBeans.getStausRequest());
		
		
		if(requestBeans.getStausRequest().equals(mContext.getResources().getString(R.string.status3)) ||
				requestBeans.getStausRequest().equals(mContext.getResources().getString(R.string.status4))){
			
				if(requestBeans.getStausRequest().equals(mContext.getResources().getString(R.string.status3))
						||requestBeans.getStausRequest().equals(mContext.getResources().getString(R.string.status4))){
					if(!CommonActivity.isNullOrEmpty(requestBeans.getActionProfileId()) && !CommonActivity.isNullOrEmpty(requestBeans.getActionProfileStatus())){
						holder.lnupload.setVisibility(View.GONE);
						if("0".equalsIgnoreCase(requestBeans.getActionProfileStatus())){
							holder.txtscan.setText(mContext.getString(R.string.chuascan));
						}else{
							holder.txtscan.setText(mContext.getString(R.string.dascan));
						}
					}else{
						holder.txtscan.setText("");
						holder.lnupload.setVisibility(View.GONE);
					}
				}else{
					holder.txtscan.setText("");
					holder.lnupload.setVisibility(View.GONE);
				}
			
				
				
			holder.lndelete.setVisibility(View.GONE);
		}else{
			holder.lnupload.setVisibility(View.GONE);
			holder.lndelete.setVisibility(View.VISIBLE);
		}
		holder.lndelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onCancelRequest.onCancelRequest(requestBeans);
//				Log.d("posstionLClick", posstionL+"");
//				Log.d("Idrequestttttttttt", arrRequestBeans.get(posstionL).getIdRequest()+"");

			}
		});
		
		holder.lnupload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(requestBeans.getActionProfileStatus().equalsIgnoreCase("0")){
					Bundle mbunBundle = new Bundle();
					FragmentUpLoadImage fragmentUpLoadImage = new FragmentUpLoadImage();
					mbunBundle.putSerializable("RequestBeanKey", requestBeans);
					fragmentUpLoadImage.setArguments(mbunBundle);
					ReplaceFragment.replaceFragment((Activity)mContext, fragmentUpLoadImage, true);
				}else{
					CommonActivity.createAlertDialog(mContext,mContext.getString(R.string.requestisupdate),mContext.getString(R.string.app_name)).show();
				}
			
			}
		});
		
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
