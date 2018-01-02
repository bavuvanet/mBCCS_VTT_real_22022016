package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.commons.ViewConfig;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.ui.image.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterStaff extends BaseAdapter {
    public static final int TYPE_LOCATION = 1;
	public static final int TYPE_VISIT = 2;
	public static final int TYPE_SALE = 3;
	public static final int TYPE_CUSCARE_BY_DAY = 4;
	private final ArrayList<Staff> arrayStaff;
	private final Context mContext;

	private int adapterType = TYPE_LOCATION;

	public AdapterStaff(ArrayList<Staff> arrayDiemban, Context mContext,
			int adapterType) {
		this.arrayStaff = arrayDiemban;
		this.mContext = mContext;
		this.adapterType = adapterType;
        ImageLoader mImageLoader = new ImageLoader(mContext);
	}

	public int getAdapterType() {
		return adapterType;
	}

	public void setAdapterType(int adapterType) {
		this.adapterType = adapterType;
	}

	@Override
	public int getCount() {
		return arrayStaff.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayStaff.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	static class ViewHoder {
		TextView txtNameStaff;
		TextView txt_id_staff;
		TextView txtDistanceStaff;
		ImageView imv_staff;
	}

	@Override
	public View getView(int position, View v, ViewGroup viewGroup) {
		ViewHoder hoder = null;
		if (v == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			v = inflater.inflate(R.layout.item_staff_layout, viewGroup, false);
			hoder = new ViewHoder();

			hoder.txtNameStaff = (TextView) v.findViewById(R.id.txtNameStaff);
			hoder.txtDistanceStaff = (TextView) v.findViewById(R.id.txtDistanceStaff);

			hoder.txt_id_staff = (TextView) v.findViewById(R.id.txt_id_staff);
			hoder.imv_staff = (ImageView) v.findViewById(R.id.imv_staff);
			v.setTag(hoder);
		} else {
			hoder = (ViewHoder) v.getTag();
		}
		
		if(CommonActivity.isNullOrEmptyArray(arrayStaff)){
			return null;
		}

		Staff mStaff = arrayStaff.get(position);
		
		if (mStaff != null) {
			hoder.txtNameStaff.setText(mStaff.getNameStaff());
			hoder.txtDistanceStaff.setVisibility(View.VISIBLE);
			hoder.txt_id_staff.setText(mStaff.getStaffCode());
			switch (adapterType) {
				case TYPE_LOCATION:
				case TYPE_CUSCARE_BY_DAY:
					if (mStaff.getDistance() != null && mStaff.getDistance() >= 0) {
						hoder.txtDistanceStaff.setVisibility(View.VISIBLE);
						hoder.txtDistanceStaff.setText(convertLocationView(Math.round(mStaff.getDistance())));
					} else {
						hoder.txtDistanceStaff.setVisibility(View.GONE);
					}
					break;
					
				case TYPE_SALE:
					if (mStaff.getTotalRevenue() != null) {
						hoder.txtDistanceStaff.setVisibility(View.VISIBLE);
						hoder.txtDistanceStaff.setText(StringUtils.formatMoney(mStaff
								.getTotalRevenue().toString()));
					} else {
						hoder.txtDistanceStaff.setVisibility(View.GONE);
					}
					break;
					
				case TYPE_VISIT:
					if (mStaff.getVisitNum() != null) {
						hoder.txtDistanceStaff.setVisibility(View.VISIBLE);
						hoder.txtDistanceStaff.setText(StringUtils.formatMoney(mStaff
								.getVisitNum().toString()));
					} else {
						hoder.txtDistanceStaff.setVisibility(View.GONE);
					}
					break;
					
				default :
					break;
			}

//			boolean b = mImageLoader.displayImage(mStaff.getLink_photo(), hoder.imv_staff, 0);
			Boolean b = false;
			if (!b) {
				ViewConfig.setStaffBitmap(mStaff, hoder.imv_staff);
			}
		}
		return v;
	}

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
