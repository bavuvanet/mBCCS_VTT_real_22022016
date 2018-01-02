package com.viettel.bss.viettelpos.v4.infrastrucure.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BlankWhiteAdapter extends BaseAdapter {

	private final ArrayList<AreaObj> arrayList;
	private final Activity mContext;

	public BlankWhiteAdapter(ArrayList<AreaObj> arrayList, Activity context) {
		this.arrayList = arrayList;
		this.mContext = context;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated me thod stub
		return 0;
	}

	class ViewHolder {
		TextView txtBlankName;
		TextView txtBlankCode;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater =  (mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_spinner_blank_white_layout, parent,
					false);
			holder = new ViewHolder();
			holder.txtBlankName = (TextView) mView
					.findViewById(R.id.txtName);
			holder.txtBlankCode = (TextView) mView.findViewById(R.id.txtCode);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		AreaObj mObject = arrayList.get(position);
		if (mObject != null) {
			holder.txtBlankName.setText(mObject.getName());
			holder.txtBlankCode.setText(mObject.getAreaCode());
		}
		return mView;
	}

}
