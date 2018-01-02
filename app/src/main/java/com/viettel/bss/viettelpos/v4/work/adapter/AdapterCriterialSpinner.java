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
import com.viettel.bss.viettelpos.v4.work.object.OjectSpinerCriterial;

public class AdapterCriterialSpinner extends BaseAdapter{
	private final ArrayList<OjectSpinerCriterial> arrayList;
	private final Context mContext;

    public AdapterCriterialSpinner (Context context,ArrayList<OjectSpinerCriterial> array){
		this.mContext = context;
		this.arrayList = array;
		
	}
	private interface onSelectListenner {
		void onSelectBox(int positionTask, int positonSalePoint);
	}

	public void setOnMySelectListenner(onSelectListenner listenner) {
        onSelectListenner selectListenner = listenner;
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
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder{
		TextView txtItemSpinner;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if(mView == null){
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_spinner_layout, parent, false);
			holder = new ViewHolder();
			holder.txtItemSpinner = (TextView)mView.findViewById(R.id.txtItemSpinner);
		
			mView.setTag(holder);
		}else{
			holder = (ViewHolder) mView.getTag();
		}
		OjectSpinerCriterial mObject = arrayList.get(position);
		if(mObject!=null){
			holder.txtItemSpinner.setText(mObject.getName());
		}
		return mView;
	}

}
