package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterSpinerTTHHBCCS extends BaseAdapter {
	private final ArrayList<ProductOfferingDTO> arrayList;
	private final Context mContext;
	public interface OnChangeSerial {
		void OnChangeSerialSelect(ProductOfferTypeDTO productOfferTypeDTO, ProductOfferingDTO objThongTinHH);

	}


	public AdapterSpinerTTHHBCCS(ArrayList<ProductOfferingDTO> arrayList,
			Context context) {
		this.arrayList = arrayList;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	class ViewHolder {
		TextView txtItemSpinner;
		TextView txtserial;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_spinner_layout, parent,
					false);
			holder = new ViewHolder();
			holder.txtItemSpinner = (TextView) mView
					.findViewById(R.id.txtItemSpinner);
			holder.txtserial = (TextView) mView.findViewById(R.id.txtserial);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.txtItemSpinner.setText(arrayList.get(position)
				.getName());
		if(!CommonActivity.isNullOrEmpty(arrayList.get(position)
				.getSerial())){
			holder.txtserial.setVisibility(View.VISIBLE);
			holder.txtserial.setText(arrayList.get(position)
				.getSerial());
		}else{
			holder.txtserial.setVisibility(View.GONE);
			holder.txtserial.setText("");
			
		}
		
		return mView;
	}

}
