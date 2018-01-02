package com.viettel.bss.viettelpos.v4.infrastrucure.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.LockBoxInfoObj;

public class LockBoxInfoAdapter extends BaseAdapter {
	private final Context mContext;
	private final ArrayList<LockBoxInfoObj> arrayList;

	public LockBoxInfoAdapter(Context mContext,
			ArrayList<LockBoxInfoObj> arrayList) {
		this.mContext = mContext;
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
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

	static class ViewHorder {
		TextView txtNameLocation;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHorder horder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_lockboxinfo, arg2, false);
			horder = new ViewHorder();
			horder.txtNameLocation = (TextView) mView
					.findViewById(R.id.tvLockBoxInfo);
			mView.setTag(horder);
		} else {
			horder = (ViewHorder) mView.getTag();
		}
		LockBoxInfoObj obj = arrayList.get(arg0);
		String info = obj.getUserLock() + " "
				+ mContext.getResources().getString(R.string.tv_tam_giu) + ": "
				+ obj.getNumberOfPort() + " Port";
		horder.txtNameLocation.setText(info);
		return mView;
	}

}
