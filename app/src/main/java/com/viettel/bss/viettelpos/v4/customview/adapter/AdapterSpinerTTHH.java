package com.viettel.bss.viettelpos.v4.customview.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ObjAPStockModelBeanBO;

public class AdapterSpinerTTHH extends BaseAdapter {
	private final ArrayList<ObjAPStockModelBeanBO> arrayList;
	private final Context mContext;

	// private String mLoaiThueBao[];

	public AdapterSpinerTTHH(ArrayList<ObjAPStockModelBeanBO> arrayList,
			Context context) {
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
		TextView txtItemSpinner;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_spinner_layout, parent,
					false);
			holder = new ViewHolder();
			holder.txtItemSpinner = (TextView) mView
					.findViewById(R.id.txtItemSpinner);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.txtItemSpinner.setText(arrayList.get(position)
				.getStockModelName());
		return mView;
	}

}
