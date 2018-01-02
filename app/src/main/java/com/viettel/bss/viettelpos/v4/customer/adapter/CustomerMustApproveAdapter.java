package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerApproveObj;

public class CustomerMustApproveAdapter extends BaseAdapter implements OnClickListener{
	private final Context mContext;
	private final List<CustomerApproveObj> lstCustomer;
	private View.OnClickListener onClickDownload;
	private String statusName;
	
	
	
	 public View.OnClickListener getOnClickDownload() {
		return onClickDownload;
	}

	public void setOnClickDownload(View.OnClickListener onClickDownload) {
		this.onClickDownload = onClickDownload;
	}

	public CustomerMustApproveAdapter(Context mContext,List<CustomerApproveObj> lstCustomer ) {
		this.mContext = mContext;
		this.lstCustomer = lstCustomer;
	}
	
	public CustomerMustApproveAdapter(Context mContext,List<CustomerApproveObj> lstCustomer, String statusName ) {
		this.mContext = mContext;
		this.lstCustomer = lstCustomer;
		this.statusName = statusName;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstCustomer.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lstCustomer.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holder = null;
		CustomerApproveObj obj = lstCustomer.get(position);
		if(v == null){
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			v = inflater
					.inflate(R.layout.item_customer_must_approve, parent, false);
			holder = new ViewHolder();
			holder.txtIsdn = (TextView) v.findViewById(R.id.vl_item_isdn);
			holder.txtCreator = (TextView) v.findViewById(R.id.vl_item_staff_created);
			holder.txtCreatedDate = (TextView) v.findViewById(R.id.vl_item_date_created);
			holder.linkFile = (TextView) v.findViewById(R.id.vl_item_download_image);
			holder.txtStatus = (TextView) v.findViewById(R.id.vl_item_status);
			holder.cbCus = (CheckBox) v.findViewById(R.id.cb_item_approve);

			holder.cbCus.setChecked(obj.isChecked());
			v.setTag(holder);
			
			holder.cbCus.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 CheckBox cb = (CheckBox) v ; 
					 CustomerApproveObj chargeCustomerOJ = (CustomerApproveObj) cb.getTag();
					 chargeCustomerOJ.setChecked(cb.isChecked());
				}
			});
		}else {
			holder = (ViewHolder) v.getTag();
		}
		
		
		if(obj != null){
			holder.txtIsdn.setText(obj.getIsdn());
			holder.txtCreator.setText("Người tạo: " + obj.getCreator());
			holder.txtCreatedDate.setText("Ngày tạo: " + DateTimeUtils.convertDateSoap(obj.getDateCreated()));
			if(CommonActivity.isNullOrEmpty(statusName)){
				holder.txtStatus.setText("Trạng thái: " + obj.getStatus());
			} else {
				holder.txtStatus.setText("Trạng thái: " + statusName);
			}
			
			String file = "";
			if(obj.getFileName() != null && !obj.getFileName().equals("")){
				file = obj.getFileName();
			}else {
				file = mContext.getResources().getString(R.string.txt_link_file);
			}
			
			SpannableString content = new SpannableString(file);
			content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
			holder.linkFile.setText(content);
		}
		holder.cbCus.setChecked(obj.isChecked());
		holder.cbCus.setTag(obj);
		holder.linkFile.setTag(position);
		holder.linkFile.setOnClickListener(this);
		
		return v;
	}
	
	private class ViewHolder {
		TextView txtIsdn;
		TextView txtCreator;
		TextView txtCreatedDate;
		TextView linkFile;
		TextView txtStatus;
		CheckBox cbCus;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.vl_item_download_image:
			Log.d("LOG", "onClickDownload: "+ onClickDownload);
			Log.d("LOG", "view: "+ v);
			if(onClickDownload != null){
				onClickDownload.onClick(v);
			}
			break;

		default:
			break;
		}
		
	}

}
