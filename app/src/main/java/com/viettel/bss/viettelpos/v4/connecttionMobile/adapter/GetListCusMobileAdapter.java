package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustommerByIdNoBean;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class GetListCusMobileAdapter extends BaseAdapter {

	private final Context mContext;
	private ArrayList<CustommerByIdNoBean> arrCustommerByIdNoBeans = new ArrayList<>();

	public GetListCusMobileAdapter(ArrayList<CustommerByIdNoBean> arr, Context context) {
		this.arrCustommerByIdNoBeans = arr;
		mContext = context;
	}

	@Override
	public int getCount() {
		return arrCustommerByIdNoBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	private class ViewHolder {
		TextView txtname;
		TextView txtsocmt;
		TextView txtdatebirh;
		TextView txtdiachi,txtngaycap;
		ImageView imagecheck;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		final CustommerByIdNoBean custommerByIdNoBean = this.arrCustommerByIdNoBeans
				.get(arg0);
		ViewHolder holder;
		View mView = arg1;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connectionmobile_customer_item, arg2,
					false);
			holder = new ViewHolder();
			holder.imagecheck = (ImageView) mView
					.findViewById(R.id.chk_customer);
			holder.txtname = (TextView) mView.findViewById(R.id.txtnameCus);
			holder.txtsocmt = (TextView) mView.findViewById(R.id.txtsocmt);
			holder.txtdatebirh = (TextView) mView
					.findViewById(R.id.txtdatebirth);
			holder.txtdiachi = (TextView) mView.findViewById(R.id.txtdiachi);
			holder.txtngaycap = (TextView) mView.findViewById(R.id.txtngaycap);
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		if (custommerByIdNoBean != null) {
			holder.txtname.setText(custommerByIdNoBean.getNameCustomer());
			if(custommerByIdNoBean.getIdNo() != null && !custommerByIdNoBean.getIdNo().isEmpty()){
				holder.txtsocmt.setText(mContext.getResources().getString(
						R.string.socmtCusMobile)
						+ " " + custommerByIdNoBean.getIdNo());
			}
				if(custommerByIdNoBean.getBusPermitNo() != null && !custommerByIdNoBean.getBusPermitNo().isEmpty()){
					holder.txtsocmt.setText(mContext.getResources().getString(
							R.string.soGPKD)
							+ " " + custommerByIdNoBean.getBusPermitNo());
				}
			if(custommerByIdNoBean.getBirthdayCus() != null && !custommerByIdNoBean.getBirthdayCus().isEmpty()){
				
				holder.txtdatebirh
				.setText(mContext.getResources().getString(
						R.string.ngaysinh)
						+ " : "+	custommerByIdNoBean.getBirthdayCus());
			}else{
				if(custommerByIdNoBean.getBirthdayCusNew() != null && !custommerByIdNoBean.getBirthdayCusNew().isEmpty()){
					holder.txtdatebirh
					.setText(mContext.getResources().getString(
							R.string.ngaysinh)
							+ " : " + custommerByIdNoBean.getBirthdayCusNew());
				}
			}
			if( custommerByIdNoBean.getAddreseCus() != null){
				holder.txtdiachi.setText(mContext.getResources().getString(
						R.string.diachi)
						+ " : " + custommerByIdNoBean.getAddreseCus());
			}
			
			if(!CommonActivity.isNullOrEmpty(custommerByIdNoBean.getIdIssueDate())){
				holder.txtngaycap
				.setText(mContext.getResources().getString(
						R.string.ngaycapseach)
						+ " : " + custommerByIdNoBean.getIdIssueDate());
			}
			
			
		
		}
		if (custommerByIdNoBean.isIscheckCus()) {
			holder.imagecheck.setBackgroundResource(R.drawable.gachnoselected);
		} else {
			holder.imagecheck.setBackgroundResource(R.drawable.gachnoselect);
		}

		return mView;
	}

}
