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
import com.viettel.bss.viettelpos.v4.customer.object.Spin;


public class SubscriberInfoRowAdapter extends BaseAdapter {
	private final Context context;
	private List<Spin> subscriberInfos = new ArrayList<>();

	public SubscriberInfoRowAdapter(Context context, List<Spin> lstData) {
		this.context = context;
		this.subscriberInfos = lstData;
	}

	@Override
	public int getCount() {
		if (subscriberInfos == null) {
			return 0;
		} else {
			return subscriberInfos.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return subscriberInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tv_subscriberInfoTitle;
		TextView tv_subscriberInfoValue;
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.layout_subscriber_info_row,
					viewGroup, false);

			holder = new ViewHoder();
			holder.tv_subscriberInfoTitle = (TextView) row.findViewById(R.id.tv_subscriberInfoTitle);
			holder.tv_subscriberInfoValue = (TextView) row.findViewById(R.id.tv_subscriberInfoValue);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		
		final Spin item = subscriberInfos.get(position);
		if (item != null) {
			holder.tv_subscriberInfoTitle.setText(item.getId());
			holder.tv_subscriberInfoValue.setText(item.getValue());
		}
		
		return row;
	}

	
}

