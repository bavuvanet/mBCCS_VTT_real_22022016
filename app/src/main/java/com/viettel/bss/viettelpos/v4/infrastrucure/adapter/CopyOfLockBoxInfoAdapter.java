package com.viettel.bss.viettelpos.v4.infrastrucure.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.LockBoxInfoObj;

@SuppressLint("ViewHolder")
class CopyOfLockBoxInfoAdapter extends BaseAdapter {
	private final ArrayList<LockBoxInfoObj> listLockBoxInfo;
	private final Context context;

	public CopyOfLockBoxInfoAdapter(Context context,
			ArrayList<LockBoxInfoObj> listLockBoxInfo) {
		this.context = context;
		this.listLockBoxInfo = listLockBoxInfo;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
        return listLockBoxInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_lockboxinfo, parent, false);
		}
		TextView tvName = (TextView) convertView
				.findViewById(R.id.tvLockBoxInfo);
		LockBoxInfoObj obj = listLockBoxInfo.get(position);
		String info = obj.getUserLock() + " "
				+ context.getResources().getString(R.string.tv_tam_giu) + " "
				+ obj.getNumberOfPort() + " Port";
		tvName.setText(info);
		return convertView;
	}

}
