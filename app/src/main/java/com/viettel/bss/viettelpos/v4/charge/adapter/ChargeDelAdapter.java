package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeItemObjectDel;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChargeDelAdapter extends BaseAdapter {
	private ArrayList<ChargeItemObjectDel> lisChargeItemObjectDels = new ArrayList<>();
	private final Context mContext;

	public ChargeDelAdapter(ArrayList<ChargeItemObjectDel> arr, Context context) {
		this.lisChargeItemObjectDels = arr;
		mContext = context;
	}

	@Override
	public int getCount() {
		return lisChargeItemObjectDels.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	static class ViewHolder {
	//	RadioButton checkBox;
		ImageView checkBox;
		TextView txtDelItemInfo;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ChargeItemObjectDel chaItemObjectDel = lisChargeItemObjectDels
				.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_charge_customer, parent,
					false);
			holder = new ViewHolder();
			holder.checkBox = (ImageView) mView
					.findViewById(R.id.chk_customer);
			holder.txtDelItemInfo = (TextView) mView
					.findViewById(R.id.txt_customer_info);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.txtDelItemInfo.setText(mContext.getResources().getString(
				R.string.sohopdong)
				+ "  "
				+ chaItemObjectDel.getContractId()
				+ "\n\n"
				+ mContext.getResources().getString(R.string.tenkhachhang)
				+ "  "
				+ chaItemObjectDel.getNameCustomer()
				+ "\n\n"
				+mContext.getResources().getString(R.string.trangthaikh)
				+ "  " + chaItemObjectDel.getStatus());
		if(chaItemObjectDel.isCheck()){
			holder.checkBox.setBackgroundResource(R.drawable.gachnoselected);
		}else{
			holder.checkBox.setBackgroundResource(R.drawable.gachnoselect);
		}
		return mView;
	}

}
