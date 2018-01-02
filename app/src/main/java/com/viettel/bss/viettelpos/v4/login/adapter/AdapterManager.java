package com.viettel.bss.viettelpos.v4.login.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.login.object.Manager;

public class AdapterManager extends BaseAdapter{
	private final ArrayList<Manager> arrayListManager;
	private final Context mContext;
	public AdapterManager (ArrayList<Manager> arrayList,Context context){
		this.arrayListManager = arrayList;
		this.mContext = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayListManager.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * 
	 * @author os_chinhnq
	 * luu cac view de khi cuon listview ko phai findviewid nhieu lan 
	 */
	static class ViewHolder{
		ImageView imvIcon;
		TextView txtName;
		TextView txtCount;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;
		if(mView == null){
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_layout_list_manager, arg2,false);
			holder = new ViewHolder();
			holder.imvIcon = (ImageView) mView.findViewById(R.id.imvIconManager);
			holder.txtName = (TextView) mView.findViewById(R.id.txtNameManager);
			holder.txtCount = (TextView) mView.findViewById(R.id.txt_notification_manager);
			mView.setTag(holder);
		}else {
			holder = (ViewHolder) mView.getTag();
		}
		Manager manager = arrayListManager.get(arg0);
		if(manager != null){
			holder.imvIcon.setImageResource(manager.getResIcon());
			holder.txtName.setText(manager.getNameManager());
			if(manager.getCountManager() > 0){
				holder.txtCount.setText(manager.getCountManager()+"");
				holder.txtCount.setVisibility(View.VISIBLE);
			}else {
				holder.txtCount.setVisibility(View.INVISIBLE);
			}
		}
		return mView;
	}

}
