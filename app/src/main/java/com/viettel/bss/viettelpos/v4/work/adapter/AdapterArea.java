package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;

public class AdapterArea extends BaseAdapter {
	private final ArrayList<AreaObj> arrayList;
	private final Context mContext;

	public AdapterArea(Context context, ArrayList<AreaObj> array) {
		this.mContext = context;
		this.arrayList = array;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size() - 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		TextView tvAreaName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_layout_collect_maket, parent, false);
			holder = new ViewHolder();
			holder.tvAreaName = (TextView) mView.findViewById(R.id.tvAreaName);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		AreaObj mObject = arrayList.get(position);
		if (mObject != null) {
			holder.tvAreaName.setText(mObject.getName());
		}
		return mView;
	}

}
