package com.viettel.bss.viettelpos.v4.login.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.login.object.LocationService;

public class AdapterLocationService extends BaseAdapter {
	private final Context mContext;
	private final ArrayList<LocationService> arrayList;

	public AdapterLocationService(Context mContext,
			ArrayList<LocationService> arrayList) {
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
		TextView txtCountFTTH;
		TextView txtCountNetTV;
		TextView txtADSL;
		TextView txtPSTN;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHorder horder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_location_service_layout,
					arg2, false);
			horder = new ViewHorder();
			horder.txtNameLocation = (TextView) mView
					.findViewById(R.id.txtNameLocation);
			horder.txtCountNetTV = (TextView) mView
					.findViewById(R.id.txtCountNetTV);
			horder.txtCountFTTH = (TextView) mView
					.findViewById(R.id.txtCountFTTH);
			horder.txtADSL = (TextView) mView.findViewById(R.id.txtCountADSL);
			horder.txtPSTN = (TextView) mView.findViewById(R.id.txtCountPSTN);
			horder.txtPSTN.setVisibility(View.GONE);
			horder.txtADSL.setVisibility(View.GONE);
			horder.txtCountFTTH.setVisibility(View.VISIBLE);
			mView.setTag(horder);
		} else {
			horder = (ViewHorder) mView.getTag();
		}
		LocationService mLocationService = arrayList.get(arg0);
		if (mLocationService != null) {
			horder.txtNameLocation.setText(mLocationService.getNameLocation());
			horder.txtCountNetTV.setText(mContext
					.getString(R.string.dialog_inflas_detail_bottom)
					+ " "
					+ mLocationService.getCountNetTV());
			horder.txtCountFTTH.setText(mContext.getString(R.string.odf) + ": "
					+ mLocationService.getCountADSL());
		}
		return mView;
	}

}
