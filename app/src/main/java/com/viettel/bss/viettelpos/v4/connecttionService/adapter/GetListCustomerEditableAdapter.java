package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GetListCustomerEditableAdapter extends ArrayAdapter<CustommerByIdNoBean> {

	private final Activity activity;
	private final View.OnClickListener imageListenner;
	
	public GetListCustomerEditableAdapter(Activity activity, ArrayList<CustommerByIdNoBean> arraylist, View.OnClickListener imageListenner) {
		super(activity, R.layout.connection_customer_editable_item, arraylist);
		this.activity = activity;
		this.imageListenner = imageListenner;
		if(this.imageListenner == null) {
			Log.d(this.getClass().getName(), "this.imageListenner NULL");
		} else {
			
		}
	}

	private class ViewHolder {
		TextView txtname;
		TextView txtsocmt;
		TextView txtdatebirh;
		TextView txtdiachi;
		ImageView imagecheck;
		TextView txtngaycap;
		TextView tvIdIssuePlace;
		ImageView imgEdit;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		CustommerByIdNoBean custommerByIdNoBean = getItem(position);
		
		ViewHolder holder;
		View mView = view;

		if (mView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			mView = inflater.inflate(R.layout.connection_customer_editable_item, viewGroup,false);
			holder = new ViewHolder();
			holder.imagecheck = (ImageView) mView.findViewById(R.id.chk_customer);
			holder.txtname = (TextView) mView.findViewById(R.id.txtnameCus);
			holder.txtsocmt = (TextView) mView.findViewById(R.id.txtsocmt);
			holder.txtdatebirh = (TextView) mView.findViewById(R.id.txtdatebirth);
			holder.txtdiachi = (TextView) mView.findViewById(R.id.txtdiachi);
			holder.txtngaycap = (TextView) mView.findViewById(R.id.txtngaycap);
			holder.tvIdIssuePlace = (TextView) mView.findViewById(R.id.tvIdIssuePlace);
			holder.imgEdit = (ImageView) mView.findViewById(R.id.imgEdit);
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		if (custommerByIdNoBean != null) {
			holder.txtname.setText(custommerByIdNoBean.getNameCustomer());
			if(!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getBusPermitNo())){
				holder.txtsocmt.setText(activity.getResources().getString(R.string.socmtCus)
						+ " " + custommerByIdNoBean.getBusPermitNo());
			} else {
				holder.txtsocmt.setText(activity.getResources().getString(R.string.socmtCus)
						+ " " + custommerByIdNoBean.getIdNo());
			}
			
			if(custommerByIdNoBean.getBirthdayCus() != null && !custommerByIdNoBean.getBirthdayCus().isEmpty()){
				holder.txtdatebirh.setText(activity.getResources().getString(R.string.ngaysinh)
						+ " : "+	custommerByIdNoBean.getBirthdayCus());
			} else {
				if(custommerByIdNoBean.getBirthdayCusNew() != null && !custommerByIdNoBean.getBirthdayCusNew().isEmpty()){
					holder.txtdatebirh.setText(activity.getResources().getString(R.string.ngaysinh)
							+ " : " + custommerByIdNoBean.getBirthdayCusNew());
				}
			}
			if( custommerByIdNoBean.getAddreseCus() != null){
				holder.txtdiachi.setText(activity.getResources().getString(R.string.diachi)
						+ " : " + custommerByIdNoBean.getAddreseCus());
			}
			if(!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getIdIssueDate())){
				holder.txtngaycap.setText(activity.getResources().getString(R.string.ngaycapseach)
						+ " : " + custommerByIdNoBean.getIdIssueDate());
			}
			if(!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getIdIssuePlace())){
				holder.tvIdIssuePlace.setText(activity.getResources().getString(R.string.txt_supply)
						+ " " + custommerByIdNoBean.getIdIssuePlace());
			}
			
			if(imageListenner != null && !CommonActivity.isNullOrEmpty(custommerByIdNoBean.getCustId())) {
				holder.imgEdit.setVisibility(View.VISIBLE);
				holder.imgEdit.setTag(custommerByIdNoBean);
				holder.imgEdit.setOnClickListener(imageListenner);
			} else {
				holder.imgEdit.setVisibility(View.GONE);
			}
		}
		if (custommerByIdNoBean.isIscheckCus()) {
			holder.imagecheck.setBackgroundResource(R.drawable.gachnoselected);
		} else {
			holder.imagecheck.setBackgroundResource(R.drawable.gachnoselect);
		}
//		Log.d(this.getClass().getName(), "position: " + position + " isImageListenner: " + isImageListenner + " custommerByIdNoBean.getCustId(): " + custommerByIdNoBean.getCustId());
		return mView;
	}
}
