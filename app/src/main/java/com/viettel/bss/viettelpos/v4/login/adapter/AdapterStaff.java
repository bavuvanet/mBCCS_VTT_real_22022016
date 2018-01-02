package com.viettel.bss.viettelpos.v4.login.adapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.viettel.bss.viettelpos.v4.image.ImageLoader;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

class AdapterStaff extends BaseAdapter {

	private final ArrayList<Staff> arrayStaff;
	private final Context mContext;

    public AdapterStaff(ArrayList<Staff> arrayDiemban, Context mContext) {
		this.arrayStaff = arrayDiemban;
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
		ImageView imvStaff;
	}

	@Override
	public View getView(int arg0, View convertview, ViewGroup arg2) {
		View mView = convertview;
		ViewHoder hoder = null;
		if (mView == null) {

			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_staff_layout, arg2, false);
			hoder = new ViewHoder();
			hoder.txtNameStaff = (TextView) mView
					.findViewById(R.id.txtNameStaff);
			hoder.txtDestance = (TextView) mView
					.findViewById(R.id.txtDistanceStaff);
			hoder.txtIdStaff = (TextView) mView.findViewById(R.id.txt_id_staff);
			hoder.imvStaff = (ImageView) mView.findViewById(R.id.imv_staff);
			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		Staff mStaff = arrayStaff.get(arg0);
		if (mStaff != null) {
			hoder.txtNameStaff.setText(mStaff.getNameStaff());
			hoder.txtIdStaff.setText(mStaff.getStaffCode());
			hoder.txtDestance
					.setText(convertLocationView(mStaff.getDistance()));
//			if (!mImageLoader.DisplayImage(mStaff.getLink_photo(),
//					hoder.imvStaff, 0)) {
//				Log.e(tag, "cant display");
//				// hoder.imvStaff.setImageResource(R.drawable.logo_vt);
//				ViewConfig.setStaffBitmap(mStaff, hoder.imvStaff);
//
//			}
		}
		return mView;
	}
	String tag = "adapter staff";
	private String convertLocationView(float value) {
		if (value > 1000) {
			double tmp = value / 1000;
			return (round(tmp, 1)) + "Km";
		} else {
			return value + "m";
		}
	}

	private double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
