package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ModifyProfileAdapter extends BaseAdapter {
	private final Context mContext;
	private List<ActionProfileBean> lstActionProfileBeans = new ArrayList<>();

	public ModifyProfileAdapter(Context context, List<ActionProfileBean> lstActionProfileBeans){
		this.mContext = context;
		this.lstActionProfileBeans = lstActionProfileBeans;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstActionProfileBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lstActionProfileBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		ActionProfileBean actionProfileBean = lstActionProfileBeans.get(position);
		if(v == null){
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			v = inflater
					.inflate(R.layout.layout_modify_profile_row, parent, false);
			
			holder = new ViewHolder();
			holder.tvIsdnAccount = (TextView) v.findViewById(R.id.tvIsdnAccount);
			holder.tvReason = (TextView) v.findViewById(R.id.tvReason);
			holder.tvStatus = (TextView) v.findViewById(R.id.tvStatus);
			holder.tvActionDate = (TextView) v.findViewById(R.id.tvActionDate);
			holder.tvActionName = (TextView) v.findViewById(R.id.tvActionName);
			holder.tvPosition = (TextView) v.findViewById(R.id.tvPosition);

			v.setTag(holder);
		}else {
			holder = (ViewHolder) v.getTag();
		}
		
		
		if(actionProfileBean != null){
			holder.tvIsdnAccount.setText(mContext.getResources().getString(R.string.cus_phone_number) + " " + DataUtils.safeToString(actionProfileBean.getIsdnAccount()));
			holder.tvReason.setText(mContext.getResources().getString(R.string.txt_reason) + " " + DataUtils.safeToString(actionProfileBean.getReaSon()));
			holder.tvStatus.setText(mContext.getResources().getString(R.string.txt_status_name) + " "+ DataUtils.safeToString(actionProfileBean.getDescTypeStatus()));
			holder.tvActionDate.setText(mContext.getResources().getString(R.string.txt_ngay_tao_hs) + " "+ DataUtils.safeToString(actionProfileBean.getActionDateStr()));
			holder.tvActionName.setText(mContext.getResources().getString(R.string.txt_action_name_param, DataUtils.safeToString(actionProfileBean.getActionName())));
			holder.tvPosition.setText("" + (position + 1) + ".");
		}
		return v;
	}
	
	private class ViewHolder {
		TextView tvIsdnAccount;
		TextView tvReason;
		TextView tvStatus;
		TextView tvActionDate;
		TextView tvActionName;
		TextView tvPosition;
	}

}
