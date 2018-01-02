package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.sale.object.IsdnOwnerObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AdapterIsdnOwnerObject extends BaseAdapter {
	private final ArrayList<IsdnOwnerObject> listIsdnOwnerObject;
	private final Activity mActivity;

	private OnclickLockIsdn onclickLockIsdn;

	public interface OnclickLockIsdn {
		void onclickLock(IsdnOwnerObject isdnOwnerObject);
	}

	public AdapterIsdnOwnerObject(Activity mActivity,
			ArrayList<IsdnOwnerObject> listIsdnOwnerObject,
			OnclickLockIsdn onclickLockIsdn) {

		this.listIsdnOwnerObject = listIsdnOwnerObject;
		this.mActivity = mActivity;
		this.onclickLockIsdn = onclickLockIsdn;

	}
	public AdapterIsdnOwnerObject(Activity mActivity,
			ArrayList<IsdnOwnerObject> listIsdnOwnerObject) {
		
		this.listIsdnOwnerObject = listIsdnOwnerObject;
		this.mActivity = mActivity;	
	}

	@Override
	public int getCount() {
		return listIsdnOwnerObject.size();
	}

	@Override
	public Object getItem(int position) {
		return listIsdnOwnerObject.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHoder {
		TextView tv_phoneNumber;
		TextView tv_status_stockmodel;
		TextView tv_container;
		TextView tvStockmodelName;
		AppCompatImageButton btn_lockIsdn;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final ViewHoder holder;
		final IsdnOwnerObject item = listIsdnOwnerObject.get(position);
		if (row == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			row = inflater.inflate(R.layout.item_isdn_owner, parent, false);

			holder = new ViewHoder();
			holder.tv_phoneNumber = (TextView) row
					.findViewById(R.id.tv_phoneNumber);
			holder.tv_status_stockmodel = (TextView) row
					.findViewById(R.id.tv_status_stockmodel);
			holder.tv_container = (TextView) row
					.findViewById(R.id.tv_container);
			holder.btn_lockIsdn = (AppCompatImageButton) row.findViewById(R.id.btn_lockIsdn);
			holder.tvStockmodelName = (TextView) row.findViewById(R.id.tvStockmodelName);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		
		if (item != null) {
			if(!CommonActivity.isNullOrEmpty(item.getStockModelName())){
				holder.tvStockmodelName.setText(item.getStockModelName());
			}
			holder.tv_phoneNumber.setText(item.getIsdn());
			holder.tv_container.setText(item.getOwnerCode() + "-"
					+ item.getOwnerName());
			String statusName = item.getStatus();
			if (statusName.equalsIgnoreCase("1")) {
				holder.tv_status_stockmodel.setText(mActivity.getResources()
						.getString(R.string.isdn_new));				
			} else if (statusName.equalsIgnoreCase("2")) {
				holder.tv_status_stockmodel.setText(mActivity.getResources()
						.getString(R.string.isdn_using));
				
			} else if (statusName.equalsIgnoreCase("3")) {
				holder.tv_status_stockmodel.setText(mActivity.getResources()
						.getString(R.string.isdn_end_use));			
				
			} else if (statusName.equalsIgnoreCase("4")) {
				holder.tv_status_stockmodel.setText(mActivity.getResources()
						.getString(R.string.isdn_start_kit));
				
			} else if (statusName.equalsIgnoreCase("5")) {
				holder.tv_status_stockmodel.setText(mActivity.getResources()
						.getString(R.string.isdn_lock));
				
			}
			SharedPreferences preferences = mActivity.getSharedPreferences(
					Define.PRE_NAME, Activity.MODE_PRIVATE);
			String name = preferences.getString(Define.KEY_MENU_NAME, "");
			if (statusName.equalsIgnoreCase("1") || statusName.equalsIgnoreCase("3")) {
				if (name.contains(Constant.VSAMenu.BCCS2IM_QLSO_TRACUUSO_GIUSO)) {
					holder.btn_lockIsdn.setVisibility(View.VISIBLE);
					holder.btn_lockIsdn.setBackgroundResource(R.drawable.ic_lock);
				} else {
					holder.btn_lockIsdn.setVisibility(View.GONE);
				}
			} else {
				if("5".equalsIgnoreCase(statusName)){
					holder.btn_lockIsdn.setVisibility(View.VISIBLE);
					holder.btn_lockIsdn.setBackgroundResource(R.drawable.ic_lock_open);
				}else{
					holder.btn_lockIsdn.setVisibility(View.GONE);
				}
			}
		}

		holder.btn_lockIsdn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onclickLockIsdn.onclickLock(item);
			}
		});

		return row;
	}

}
