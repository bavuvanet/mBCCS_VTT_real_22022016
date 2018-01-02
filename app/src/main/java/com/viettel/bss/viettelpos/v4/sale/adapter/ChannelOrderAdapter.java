package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOrderChannel;

public class ChannelOrderAdapter extends BaseAdapter {

	private final Context context;
	private List<SaleOrderChannel> data = new ArrayList<>();

	public ChannelOrderAdapter(Context context, List<SaleOrderChannel> data) {
		super();
		this.context = context;
		this.data = data;
	}


	@Override
	public int getCount() {
		
		if(data != null){
			return data.size();
		}else{
			return 0;
		}
		
		
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	static class ViewHoder {
		TextView tvOrderCode;
		TextView orderOwner;
		TextView tvOrderDate;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		final ViewHoder hoder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.layout_channel_order_item,
					parent, false);
			hoder = new ViewHoder();
			hoder.tvOrderCode = (TextView) row.findViewById(R.id.tvOrderCode);
			hoder.orderOwner = (TextView) row.findViewById(R.id.orderOwner);
			hoder.tvOrderDate = (TextView) row.findViewById(R.id.tvOrderDate);
			row.setTag(hoder);
		} else {
			hoder = (ViewHoder) row.getTag();
		}

		SaleOrderChannel item = data.get(position);
		if (item != null) {
			hoder.tvOrderCode.setText(item.getSaleOrderCode());
			hoder.orderOwner.setText(item.getStaffCode());
			hoder.tvOrderDate.setText(DateTimeUtils.convertDate(item.getCreateDate()));
		}
		return row;
	}

}
