package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.PakageVasBean;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GetPakageVasAdapter extends BaseAdapter{
	private final Context mContext;
	private ArrayList<PakageVasBean> arrPakageVasBeans = new ArrayList<>();
	
	public GetPakageVasAdapter(ArrayList<PakageVasBean> arr , Context context){
		this.mContext = context;
		this.arrPakageVasBeans = arr;
	}
	
	@Override
	public int getCount() {
		return arrPakageVasBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrPakageVasBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	private class ViewHolder{
		private TextView textName;
		private ImageView imagecheck;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		if(mView == null){
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_grid_dktt_vas, arg2,
					false);
			holder = new ViewHolder();
			holder.textName = (TextView) mView.findViewById(R.id.textnamePakage);
			holder.imagecheck = (ImageView) mView.findViewById(R.id.checkPakage);
			mView.setTag(holder);
		}else{
			holder = (ViewHolder) mView.getTag();
		}
		final PakageVasBean pakageVasBean = arrPakageVasBeans.get(arg0);
		holder.textName.setText(pakageVasBean.getRelProductName());
		if (pakageVasBean.isCheck()) {
			holder.imagecheck.setBackgroundResource(R.drawable.gachnoselected);
		} else {
			holder.imagecheck.setBackgroundResource(R.drawable.gachnoselect);
		}
		
		return mView;
	}

}
