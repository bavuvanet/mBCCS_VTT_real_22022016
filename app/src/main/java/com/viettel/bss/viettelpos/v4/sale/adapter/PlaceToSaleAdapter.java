package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.PlaceToSaleObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PlaceToSaleAdapter extends BaseAdapter {

	private final Activity mActivity;
	private final ArrayList<PlaceToSaleObject> mlistPlace;
	
	public PlaceToSaleAdapter(Activity mActivity,ArrayList<PlaceToSaleObject> mlistPlace) {
		this.mActivity = mActivity;
		this.mlistPlace = mlistPlace;
	}
	
	@Override
	public int getCount() { 
		return mlistPlace.size();
	}

	@Override
	public Object getItem(int position) { 
		return mlistPlace.get(position);
	}

	@Override
	public long getItemId(int position) { 
		return 0;
	}
	
	class ViewHolder {
		TextView tvNamePlace;
		TextView tvCodePlace;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final PlaceToSaleObject placeObject =  mlistPlace.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_place_to_sale, parent, false);
			holder = new ViewHolder();
			holder.tvNamePlace = (TextView) mView.findViewById(R.id.txt_name_place);
			holder.tvCodePlace = (TextView) mView.findViewById(R.id.txt_code_place);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.tvNamePlace.setText(placeObject.getStaffName());
		holder.tvCodePlace.setText(placeObject.getStaffCode());
		
		return mView;
	}

}
