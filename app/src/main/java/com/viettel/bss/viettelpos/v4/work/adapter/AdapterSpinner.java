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
import com.viettel.bss.viettelpos.v4.work.object.TaskObject;

class AdapterSpinner extends BaseAdapter{
	private final ArrayList<TaskObject> arrayList;
	private final Context mContext;
	public AdapterSpinner (Context context,ArrayList<TaskObject> array){
		this.mContext = context;
		this.arrayList = array;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size()-1;
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

	static class ViewHolder{
		TextView txtTask;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if(mView == null){
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_spinner_layout, parent, false);
			holder = new ViewHolder();
			holder.txtTask = (TextView)mView.findViewById(R.id.txtItemSpinner);
			mView.setTag(holder);
		}else{
			holder = (ViewHolder) mView.getTag();
		}
		TaskObject mObject = arrayList.get(position);
		if(mObject!=null){
			holder.txtTask.setText(mObject.getNameTask());
		}
		return mView;
	}

}
