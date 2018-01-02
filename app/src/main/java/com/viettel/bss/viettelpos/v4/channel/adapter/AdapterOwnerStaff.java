package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.ImageLoader;

public class AdapterOwnerStaff extends BaseAdapter {

	private final ArrayList<Staff> arrayStaff;
	private final Context mContext;

    public AdapterOwnerStaff(ArrayList<Staff> arrStaff, Context mContext) {
		this.arrayStaff = arrStaff;
		this.mContext = mContext;
        ImageLoader mImageLoader = new ImageLoader(mContext);
	}

	@Override
	public int getCount() {
		return arrayStaff.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrayStaff.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHoder {
		TextView txtNameStaff;
		TextView txtIdStaff;
		TextView txtDestance;
		
	}

	@Override
	public View getView(int arg0, View convertview, ViewGroup arg2) {
		View mView = convertview;
		ViewHoder hoder = null;
		if (mView == null) {

			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_staff_list_owner, arg2, false);
			hoder = new ViewHoder();
			hoder.txtNameStaff = (TextView) mView
					.findViewById(R.id.txtNameStaff);
			hoder.txtDestance = (TextView) mView
					.findViewById(R.id.txtDistanceStaff);
			hoder.txtIdStaff = (TextView) mView
					.findViewById(R.id.txt_id_staff);
		
			mView.setTag(hoder);
		}else {
			hoder = (ViewHoder) mView.getTag();
		}
		Staff mStaff = arrayStaff.get(arg0);
		if(mStaff != null){
			hoder.txtNameStaff.setText(mStaff.getNameStaff());
			hoder.txtIdStaff.setText(mStaff.getStaffCode());
		}
		return mView;
	}
	public String convertLocationView(float value){
		if(value > 1000){
			double tmp = value/1000;
			return (round(tmp,1)) + "Km";
		}else{
			return value + "m"; 
		}
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
