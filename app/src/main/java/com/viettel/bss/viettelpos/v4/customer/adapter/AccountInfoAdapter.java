package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.customer.object.AccountBO;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccountInfoAdapter extends BaseAdapter {
	private final ArrayList<AccountBO> lstAccountBOs;
	private final Context mContext;
	private String functionType = "";

	public AccountInfoAdapter(Context mContext,
			ArrayList<AccountBO> lstAccountBOs, String functionType) {
		this.mContext = mContext;
		this.lstAccountBOs = lstAccountBOs;
		this.functionType = functionType;
	}
	
	public AccountInfoAdapter(Context mContext,
			ArrayList<AccountBO> lstAccountBOs) {
		this.mContext = mContext;
		this.lstAccountBOs = lstAccountBOs;
	}

	@Override
	public int getCount() {
		if (CommonActivity.isNullOrEmpty(lstAccountBOs)) {
			return 0;
		}
		return lstAccountBOs.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (CommonActivity.isNullOrEmpty(lstAccountBOs)) {
			return null;
		}
		return lstAccountBOs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		AccountBO item = lstAccountBOs.get(arg0);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_account_info, arg2,
					false);
			holder = new ViewHolder();
			holder.tvContractNo = (TextView) mView.findViewById(R.id.tvContractNo);
			holder.tvIsdnDaiDien = (TextView) mView.findViewById(R.id.tvIsdnDaiDien);
			holder.tvPayMethod = (TextView) mView.findViewById(R.id.tvPayMethod);
			holder.tvDebitStatus = (TextView) mView.findViewById(R.id.tvDebitStatus);
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		holder.tvContractNo.setText(mContext.getResources().getString(R.string.txt_so_hop_dong, CommonActivity.isNullOrEmpty(item.getAccountNo()) ? "" : item.getAccountNo()));
		holder.tvIsdnDaiDien.setText(mContext.getResources().getString(R.string.txt_tb_dai_dien, CommonActivity.isNullOrEmpty(item.getIsdn()) ? "" : item.getIsdn()));
		holder.tvPayMethod.setText(mContext.getResources().getString(R.string.txt_ht_thanh_toan, CommonActivity.isNullOrEmpty(item.getPayMethodName()) ? "" : item.getPayMethodName()));
		if(Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO.equals(functionType)){
			holder.tvDebitStatus.setText(mContext.getResources().getString(R.string.txt_trang_thai_no));
		} else {
			if("1".equals(item.getStatus())){
				holder.tvDebitStatus.setText(mContext.getResources().getString(R.string.txt_status_account_not_active));
			} else if("2".equals(item.getStatus())){
				holder.tvDebitStatus.setText(mContext.getResources().getString(R.string.txt_status_account_active));
			} else if("3".equals(item.getStatus())){
				holder.tvDebitStatus.setText(mContext.getResources().getString(R.string.txt_status_account_cancel));
			} else if("4".equals(item.getStatus())){
				holder.tvDebitStatus.setText(mContext.getResources().getString(R.string.txt_status_account_finish));
			}
		}
		
		return mView;
	}

	class ViewHolder {
		TextView tvContractNo;
		TextView tvIsdnDaiDien;
		TextView tvPayMethod;
		TextView tvDebitStatus;
	}

}
